package imageloader.core.display;

import imageloader.core.assist.LoadedFrom;
import imageloader.core.imageaware.ImageAware;
import android.graphics.Bitmap;

public interface BitmapDisplayer {
	/**
	 * Displays bitmap in {@link imageloader.core.imageaware.ImageAware}.
	 * <b>NOTE:</b> This method is called on UI thread so it's strongly recommended not to do any heavy work in it.
	 *
	 * @param bitmap     Source bitmap
	 * @param imageAware {@linkplain imageloader.core.imageaware.ImageAware Image aware view} to
	 *                   display Bitmap
	 * @param loadedFrom Source of loaded image
	 * ImageAware}
	 */
	void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom);
}
