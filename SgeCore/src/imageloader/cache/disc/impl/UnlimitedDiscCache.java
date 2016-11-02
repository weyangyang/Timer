package imageloader.cache.disc.impl;

import imageloader.cache.disc.BaseDiscCache;
import imageloader.cache.disc.DiscCacheAware;
import imageloader.cache.disc.naming.FileNameGenerator;
import imageloader.core.DefaultConfigurationFactory;

import java.io.File;

/**
 * Default implementation of {@linkplain DiscCacheAware disc cache}. Cache size is unlimited.
 *
 * @see BaseDiscCache
 */
public class UnlimitedDiscCache extends BaseDiscCache {

	/** @param cacheDir Directory for file caching */
	public UnlimitedDiscCache(File cacheDir) {
		this(cacheDir, DefaultConfigurationFactory.createFileNameGenerator());
	}

	/**
	 * @param cacheDir          Directory for file caching
	 * @param fileNameGenerator Name generator for cached files
	 */
	public UnlimitedDiscCache(File cacheDir, FileNameGenerator fileNameGenerator) {
		super(cacheDir, fileNameGenerator);
	}

	@Override
	public void put(String key, File file) {
		// Do nothing
	}
}
