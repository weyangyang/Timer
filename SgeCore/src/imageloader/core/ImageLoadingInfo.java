package imageloader.core;

import imageloader.core.assist.ImageSize;
import imageloader.core.imageaware.ImageAware;
import imageloader.core.listener.ImageLoadingListener;
import imageloader.core.listener.ImageLoadingProgressListener;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Information for load'n'display image task
 *
 * @see imageloader.utils.MemoryCacheUtils
 * @see DisplayImageOptions
 * @see ImageLoadingListener
 * @see imageloader.core.listener.ImageLoadingProgressListener
 */
final class ImageLoadingInfo {

	final String uri;
	final String memoryCacheKey;
	final ImageAware imageAware;
	final ImageSize targetSize;
	final DisplayImageOptions options;
	final ImageLoadingListener listener;
	final ImageLoadingProgressListener progressListener;
	final ReentrantLock loadFromUriLock;

	public ImageLoadingInfo(String uri, ImageAware imageAware, ImageSize targetSize, String memoryCacheKey,
			DisplayImageOptions options, ImageLoadingListener listener,
			ImageLoadingProgressListener progressListener, ReentrantLock loadFromUriLock) {
		this.uri = uri;
		this.imageAware = imageAware;
		this.targetSize = targetSize;
		this.options = options;
		this.listener = listener;
		this.progressListener = progressListener;
		this.loadFromUriLock = loadFromUriLock;
		this.memoryCacheKey = memoryCacheKey;
	}
}
