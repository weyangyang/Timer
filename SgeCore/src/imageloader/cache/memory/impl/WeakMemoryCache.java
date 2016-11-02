package imageloader.cache.memory.impl;

import android.graphics.Bitmap;
import imageloader.cache.memory.BaseMemoryCache;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Memory cache with {@linkplain WeakReference weak references} to {@linkplain android.graphics.Bitmap bitmaps}<br />
 * <br />
 * <b>NOTE:</b> This cache uses only weak references for stored Bitmaps.
 */
public class WeakMemoryCache extends BaseMemoryCache<String, Bitmap> {
	@Override
	protected Reference<Bitmap> createReference(Bitmap value) {
		return new WeakReference<Bitmap>(value);
	}
}
