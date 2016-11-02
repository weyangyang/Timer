package imageloader.core;

import imageloader.core.assist.LoadedFrom;
import imageloader.core.display.BitmapDisplayer;
import imageloader.core.imageaware.ImageAware;
import imageloader.core.listener.ImageLoadingListener;
import imageloader.utils.L;
import android.graphics.Bitmap;

/**
 * Displays bitmap in {@link imageloader.core.imageaware.ImageAware}. Must be called on UI thread.
 *
 * @see ImageLoadingListener
 * @see BitmapDisplayer
 */
final class DisplayBitmapTask implements Runnable {

	private static final String LOG_DISPLAY_IMAGE_IN_IMAGEAWARE = "Display image in ImageAware (loaded from %1$s) [%2$s]";
	private static final String LOG_TASK_CANCELLED_IMAGEAWARE_REUSED = "ImageAware is reused for another image. Task is cancelled. [%s]";
	private static final String LOG_TASK_CANCELLED_IMAGEAWARE_COLLECTED = "ImageAware was collected by GC. Task is cancelled. [%s]";

	private final Bitmap bitmap;
	private final String imageUri;
	private final ImageAware imageAware;
	private final String memoryCacheKey;
	private final BitmapDisplayer displayer;
	private final ImageLoadingListener listener;
	private final ImageLoaderEngine engine;
	private final LoadedFrom loadedFrom;

	private boolean loggingEnabled;

	public DisplayBitmapTask(Bitmap bitmap, ImageLoadingInfo imageLoadingInfo, ImageLoaderEngine engine,
							 LoadedFrom loadedFrom) {
		this.bitmap = bitmap;
		imageUri = imageLoadingInfo.uri;
		imageAware = imageLoadingInfo.imageAware;
		memoryCacheKey = imageLoadingInfo.memoryCacheKey;
		displayer = imageLoadingInfo.options.getDisplayer();
		listener = imageLoadingInfo.listener;
		this.engine = engine;
		this.loadedFrom = loadedFrom;
	}

	public void run() {
		if (imageAware.isCollected()) {
			if (loggingEnabled) L.d(LOG_TASK_CANCELLED_IMAGEAWARE_COLLECTED, memoryCacheKey);
			listener.onLoadingCancelled(imageUri, imageAware.getWrappedView());
		} else if (isViewWasReused()) {
			if (loggingEnabled) L.d(LOG_TASK_CANCELLED_IMAGEAWARE_REUSED, memoryCacheKey);
			listener.onLoadingCancelled(imageUri, imageAware.getWrappedView());
		} else {
			if (loggingEnabled) L.d(LOG_DISPLAY_IMAGE_IN_IMAGEAWARE, loadedFrom, memoryCacheKey);
			displayer.display(bitmap, imageAware, loadedFrom);
			listener.onLoadingComplete(imageUri, imageAware.getWrappedView(), bitmap);
			engine.cancelDisplayTaskFor(imageAware);
		}
	}

	/** Checks whether memory cache key (image URI) for current ImageAware is actual */
	private boolean isViewWasReused() {
		String currentCacheKey = engine.getLoadingUriForView(imageAware);
		return !memoryCacheKey.equals(currentCacheKey);
	}

	void setLoggingEnabled(boolean loggingEnabled) {
		this.loggingEnabled = loggingEnabled;
	}
}
