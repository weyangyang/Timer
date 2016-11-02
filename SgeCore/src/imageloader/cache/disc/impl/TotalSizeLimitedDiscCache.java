package imageloader.cache.disc.impl;

import imageloader.cache.disc.LimitedDiscCache;
import imageloader.cache.disc.naming.FileNameGenerator;
import imageloader.core.DefaultConfigurationFactory;
import imageloader.utils.L;

import java.io.File;

/**
 * Disc cache limited by total cache size. If cache size exceeds specified limit then file with the most oldest last
 * usage date will be deleted.
 *
 * @see LimitedDiscCache
 */
public class TotalSizeLimitedDiscCache extends LimitedDiscCache {

	private static final int MIN_NORMAL_CACHE_SIZE_IN_MB = 2;
	private static final int MIN_NORMAL_CACHE_SIZE = MIN_NORMAL_CACHE_SIZE_IN_MB * 1024 * 1024;

	/**
	 * @param cacheDir     Directory for file caching. <b>Important:</b> Specify separate folder for cached files. It's
	 *                     needed for right cache limit work.
	 * @param maxCacheSize Maximum cache directory size (in bytes). If cache size exceeds this limit then file with the
	 *                     most oldest last usage date will be deleted.
	 */
	public TotalSizeLimitedDiscCache(File cacheDir, int maxCacheSize) {
		this(cacheDir, DefaultConfigurationFactory.createFileNameGenerator(), maxCacheSize);
	}

	/**
	 * @param cacheDir          Directory for file caching. <b>Important:</b> Specify separate folder for cached files. It's
	 *                          needed for right cache limit work.
	 * @param fileNameGenerator Name generator for cached files
	 * @param maxCacheSize      Maximum cache directory size (in bytes). If cache size exceeds this limit then file with the
	 *                          most oldest last usage date will be deleted.
	 */
	public TotalSizeLimitedDiscCache(File cacheDir, FileNameGenerator fileNameGenerator, int maxCacheSize) {
		super(cacheDir, fileNameGenerator, maxCacheSize);
		if (maxCacheSize < MIN_NORMAL_CACHE_SIZE) {
			L.w("You set too small disc cache size (less than %1$d Mb)", MIN_NORMAL_CACHE_SIZE_IN_MB);
		}
	}

	@Override
	protected int getSize(File file) {
		return (int) file.length();
	}
}
