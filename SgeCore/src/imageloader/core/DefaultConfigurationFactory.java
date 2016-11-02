package imageloader.core;

import android.content.Context;
import android.graphics.Bitmap;
import imageloader.cache.disc.DiscCacheAware;
import imageloader.cache.disc.impl.FileCountLimitedDiscCache;
import imageloader.cache.disc.impl.TotalSizeLimitedDiscCache;
import imageloader.cache.disc.impl.UnlimitedDiscCache;
import imageloader.cache.disc.naming.FileNameGenerator;
import imageloader.cache.disc.naming.HashCodeFileNameGenerator;
import imageloader.cache.memory.MemoryCacheAware;
import imageloader.cache.memory.impl.LruMemoryCache;
import imageloader.core.assist.QueueProcessingType;
import imageloader.core.assist.deque.LIFOLinkedBlockingDeque;
import imageloader.core.decode.BaseImageDecoder;
import imageloader.core.decode.ImageDecoder;
import imageloader.core.display.BitmapDisplayer;
import imageloader.core.display.SimpleBitmapDisplayer;
import imageloader.core.download.BaseImageDownloader;
import imageloader.core.download.ImageDownloader;
import imageloader.utils.StorageUtils;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Factory for providing of default options for {@linkplain ImageLoaderConfiguration configuration}
 *
 */
public class DefaultConfigurationFactory {

	/** Creates default implementation of task executor */
	public static Executor createExecutor(int threadPoolSize, int threadPriority, QueueProcessingType tasksProcessingType) {
		boolean lifo = tasksProcessingType == QueueProcessingType.LIFO;
		BlockingQueue<Runnable> taskQueue = lifo ? new LIFOLinkedBlockingDeque<Runnable>() : new LinkedBlockingQueue<Runnable>();
		return new ThreadPoolExecutor(threadPoolSize, threadPoolSize, 0L, TimeUnit.MILLISECONDS, taskQueue, createThreadFactory(threadPriority));
	}

	/** Creates {@linkplain HashCodeFileNameGenerator default implementation} of FileNameGenerator */
	public static FileNameGenerator createFileNameGenerator() {
		return new HashCodeFileNameGenerator();
	}

	/** Creates default implementation of {@link DiscCacheAware} depends on incoming parameters */
	public static DiscCacheAware createDiscCache(Context context, FileNameGenerator discCacheFileNameGenerator, int discCacheSize, int discCacheFileCount) {
		if (discCacheSize > 0) {
			File individualCacheDir = StorageUtils.getIndividualCacheDirectory(context);
			return new TotalSizeLimitedDiscCache(individualCacheDir, discCacheFileNameGenerator, discCacheSize);
		} else if (discCacheFileCount > 0) {
			File individualCacheDir = StorageUtils.getIndividualCacheDirectory(context);
			return new FileCountLimitedDiscCache(individualCacheDir, discCacheFileNameGenerator, discCacheFileCount);
		} else {
			File cacheDir = StorageUtils.getCacheDirectory(context);
			return new UnlimitedDiscCache(cacheDir, discCacheFileNameGenerator);
		}
	}

	/** Creates reserve disc cache which will be used if primary disc cache becomes unavailable */
	public static DiscCacheAware createReserveDiscCache(File cacheDir) {
		File individualDir = new File(cacheDir, "uil-images");
		if (individualDir.exists() || individualDir.mkdir()) {
			cacheDir = individualDir;
		}
		return new TotalSizeLimitedDiscCache(cacheDir, 2 * 1024 * 1024); // limit - 2 Mb
	}

	/**
	 * Creates default implementation of {@link MemoryCacheAware} - {@link LruMemoryCache}<br />
	 * Default cache size = 1/8 of available app memory.
	 */
	public static MemoryCacheAware<String, Bitmap> createMemoryCache(int memoryCacheSize) {
		if (memoryCacheSize == 0) {
			memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 8);
		}
		return new LruMemoryCache(memoryCacheSize);
	}

	/** Creates default implementation of {@link ImageDownloader} - {@link BaseImageDownloader} */
	public static ImageDownloader createImageDownloader(Context context) {
		return new BaseImageDownloader(context);
	}

	/** Creates default implementation of {@link ImageDecoder} - {@link BaseImageDecoder} */
	public static ImageDecoder createImageDecoder(boolean loggingEnabled) {
		return new BaseImageDecoder(loggingEnabled);
	}

	/** Creates default implementation of {@link BitmapDisplayer} - {@link SimpleBitmapDisplayer} */
	public static BitmapDisplayer createBitmapDisplayer() {
		return new SimpleBitmapDisplayer();
	}

	/** Creates default implementation of {@linkplain ThreadFactory thread factory} for task executor */
	private static ThreadFactory createThreadFactory(int threadPriority) {
		return new DefaultThreadFactory(threadPriority);
	}

	private static class DefaultThreadFactory implements ThreadFactory {

		private static final AtomicInteger poolNumber = new AtomicInteger(1);

		private final ThreadGroup group;
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String namePrefix;
		private final int threadPriority;

		DefaultThreadFactory(int threadPriority) {
			this.threadPriority = threadPriority;
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
			namePrefix = "uil-pool-" + poolNumber.getAndIncrement() + "-thread-";
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
			if (t.isDaemon()) t.setDaemon(false);
			t.setPriority(threadPriority);
			return t;
		}
	}
}
