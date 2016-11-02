package imageloader.core.imageaware;

import imageloader.core.assist.ImageSize;
import imageloader.core.assist.ViewScaleType;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
public class ImageNonViewAware implements ImageAware {

	protected final String imageUri;
	protected final ImageSize imageSize;
	protected final ViewScaleType scaleType;

	public ImageNonViewAware(ImageSize imageSize, ViewScaleType scaleType) {
		this(null, imageSize, scaleType);
	}

	public ImageNonViewAware(String imageUri, ImageSize imageSize, ViewScaleType scaleType) {
		this.imageUri = imageUri;
		this.imageSize = imageSize;
		this.scaleType = scaleType;
	}

	@Override
	public int getWidth() {
		return imageSize.getWidth();
	}

	@Override
	public int getHeight() {
		return imageSize.getHeight();
	}

	@Override
	public ViewScaleType getScaleType() {
		return scaleType;
	}

	@Override
	public View getWrappedView() {
		return null;
	}

	@Override
	public boolean isCollected() {
		return false;
	}

	@Override
	public int getId() {
		return TextUtils.isEmpty(imageUri) ? super.hashCode() : imageUri.hashCode();
	}

	@Override
	public boolean setImageDrawable(Drawable drawable) { // Do nothing
		return true;
	}

	@Override
	public boolean setImageBitmap(Bitmap bitmap) { // Do nothing
		return true;
	}
}