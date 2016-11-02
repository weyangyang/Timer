package imageloader.core.display;

import imageloader.core.assist.LoadedFrom;
import imageloader.core.imageaware.ImageAware;
import android.graphics.Bitmap;
public final class SimpleBitmapDisplayer implements BitmapDisplayer {
	@Override
	public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
		imageAware.setImageBitmap(bitmap);
	}
}