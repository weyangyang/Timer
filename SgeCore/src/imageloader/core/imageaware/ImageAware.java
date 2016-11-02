package imageloader.core.imageaware;

import imageloader.core.assist.ViewScaleType;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
public interface ImageAware {
	/**
	 * Returns width of image aware view. This value is used to define scale size for original image.
	 * Can return 0 if width is undefined.<br />
	 * Called on UI thread.
	 */
	int getWidth();

	/**
	 * Returns height of image aware view. This value is used to define scale size for original image.
	 * Can return 0 if height is undefined.<br />
	 * Called on UI thread.
	 */
	int getHeight();

	/**
	 * Returns {@linkplain imageloader.core.assist.ViewScaleType scale type} which is used for
	 * scaling image for this image aware view.
	 */
	ViewScaleType getScaleType();

	/**
	 * Returns wrapped Android {@link android.view.View View}. Can return <b>null</b> if no view is wrapped.<br />
	 * Called on UI thread.
	 */
	View getWrappedView();

	/**
	 * Returns a flag whether image aware view is collected by GC or whatsoever. If so then ImageLoader stop processing
	 * of task for this image aware view and fires
	 * {@link imageloader.core.listener.ImageLoadingListener#onLoadingCancelled(String,
	 * android.view.View) ImageLoadingListener#onLoadingCancelled(String, View)} callback.<br />
	 * May be called on UI thread.
	 *
	 * @return <b>true</b> - if view is collected by GC and ImageLoader should stop processing this image aware view;
	 * <b>false</b> - otherwise
	 */
	boolean isCollected();

	/**
	 * Returns ID of image aware view. Point of ID is similar to Object's hashCode. This ID should be unique for every
	 * image view instance and should be the same for same instances. This ID identifies processing task in ImageLoader
	 * so ImageLoader won't process two image aware views with the same ID in one time. When ImageLoader get new task
	 * it cancels old task with this ID (if any) and starts new task.
	 * <p/>
	 * It's reasonable to return hash code of wrapped view (if any) to prevent displaying non-actual images in view
	 * because of view re-using.
	 */
	int getId();

	/**
	 * Sets image drawable into this image aware view.<br />
	 * Called on UI thread to display drawable in this image aware view
	 * {@linkplain imageloader.core.DisplayImageOptions.Builder#showImageForEmptyUri(
	 *android.graphics.drawable.Drawable) for empty Uri},
	 * {@linkplain imageloader.core.DisplayImageOptions.Builder#showImageOnLoading(
	 *android.graphics.drawable.Drawable) on loading} or
	 * {@linkplain imageloader.core.DisplayImageOptions.Builder#showImageOnFail(
	 *android.graphics.drawable.Drawable) on loading fail}. These drawables can be specified in
	 * {@linkplain imageloader.core.DisplayImageOptions display options}.<br />
	 * Also can be called in {@link imageloader.core.display.BitmapDisplayer BitmapDisplayer}.
	 *
	 * @return <b>true</b> if drawable was set successfully; <b>false</b> - otherwise
	 */
	boolean setImageDrawable(Drawable drawable);

	/**
	 * Sets image bitmap into this image aware view.<br />
	 * Called on UI thread to display loaded and decoded image {@link android.graphics.Bitmap} in this image view aware.
	 * Actually it's used only in
	 * {@link imageloader.core.display.BitmapDisplayer BitmapDisplayer}.
	 *
	 * @return <b>true</b> if bitmap was set successfully; <b>false</b> - otherwise
	 */
	boolean setImageBitmap(Bitmap bitmap);
}