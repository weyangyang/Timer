package imageloader.cache.disc;

import imageloader.cache.disc.naming.FileNameGenerator;
import imageloader.core.DefaultConfigurationFactory;

import java.io.File;

/**
 * Base disc cache. Implements common functionality for disc cache.
 *
 * @see DiscCacheAware
 * @see FileNameGenerator
 */
public abstract class BaseDiscCache implements DiscCacheAware {

	private static final String ERROR_ARG_NULL = "\"%s\" argument must be not null";

	protected File cacheDir;

	private FileNameGenerator fileNameGenerator;

	public BaseDiscCache(File cacheDir) {
		this(cacheDir, DefaultConfigurationFactory.createFileNameGenerator());
	}

	public BaseDiscCache(File cacheDir, FileNameGenerator fileNameGenerator) {
		if (cacheDir == null) {
			throw new IllegalArgumentException(String.format(ERROR_ARG_NULL, "cacheDir"));
		}
		if (fileNameGenerator == null) {
			throw new IllegalArgumentException(String.format(ERROR_ARG_NULL, "fileNameGenerator"));
		}

		this.cacheDir = cacheDir;
		this.fileNameGenerator = fileNameGenerator;
	}

	@Override
	public File get(String key) {
		String fileName = fileNameGenerator.generate(key);
		return new File(cacheDir, fileName);
	}

	@Override
	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files != null) {
			for (File f : files) {
				f.delete();
			}
		}
	}
}