package imageloader.core.listener;

import android.graphics.Bitmap;
import android.view.View;
public class SyncImageLoadingListener extends SimpleImageLoadingListener {

	private Bitmap loadedImage;

	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		this.loadedImage = loadedImage;
	}

	public Bitmap getLoadedBitmap() {
		return loadedImage;
	}
}