package imageloader.utils;

import imageloader.cache.disc.DiscCacheAware;

import java.io.File;
public final class DiscCacheUtils {

	private DiscCacheUtils() {
	}

	/** Returns {@link File} of cached image or <b>null</b> if image was not cached in disc cache */
	public static File findInCache(String imageUri, DiscCacheAware discCache) {
		File image = discCache.get(imageUri);
		return image.exists() ? image : null;
	}

	/**
	 * Removed cached image file from disc cache (if image was cached in disc cache before)
	 *
	 * @return <b>true</b> - if cached image file existed and was deleted; <b>false</b> - otherwise.
	 */
	public static boolean removeFromCache(String imageUri, DiscCacheAware discCache) {
		File image = discCache.get(imageUri);
		return image.delete();
	}
}
