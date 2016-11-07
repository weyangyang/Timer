package com.qsvxin.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.qsvxin.R;
import com.sge.imageloader.cache.disc.naming.Md5FileNameGenerator;
import com.sge.imageloader.cache.memory.impl.LruMemoryCache;
import com.sge.imageloader.core.DisplayImageOptions;
import com.sge.imageloader.core.ImageLoader;
import com.sge.imageloader.core.ImageLoaderConfiguration;
import com.sge.imageloader.core.assist.ImageScaleType;
import com.sge.imageloader.core.assist.QueueProcessingType;
import com.sge.imageloader.core.display.SimpleBitmapDisplayer;

/**
 * 第三方库ImageLoader的一个包装类，主要用于创建ImageLoader对象和DisplayImageOptions对象
 * @author carter
 *
 */
public class ImageLoaderManager {
	private static ImageLoader imageLoader;
	private static DisplayImageOptions displayImageOptions;
	
	public static ImageLoader getImageLoader(Context context) {
		if (imageLoader == null) {
			imageLoader = getImageLoad(context);
		}
		return imageLoader;
	}
	
	public static DisplayImageOptions getImageOptions(Context context) {
		if (displayImageOptions == null) {
			displayImageOptions = getImageOption(context);
		}
		return displayImageOptions;
	}
	/**
	 * 创建ImageLoader对象，并设置其各项参数
	 * @param context
	 * @return
	 */
	private static ImageLoader getImageLoad(Context context) {
		ImageLoaderConfiguration.Builder builder =
				new ImageLoaderConfiguration.Builder(context);
		builder.threadPriority(Thread.NORM_PRIORITY)
		.denyCacheImageMultipleSizesInMemory()
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
		.memoryCacheSize(2 * 1024 * 1024)
		.discCacheSize(50 * 1024 * 1024)
		.discCacheFileCount(100);
		ImageLoaderConfiguration config = builder.build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
		return imageLoader;
	}
	
	/**
	 * 创建DisplayImageOptions对象，并设置其各项参数
	 * @return
	 */
	private static DisplayImageOptions getImageOption(Context context) {
		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
		builder.showImageOnLoading(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.resetViewBeforeLoading(true)
//		.delayBeforeLoading(1000)
		.cacheInMemory(true)
		.cacheOnDisc(true)
//		.preProcessor(new BitmapProcessor() {
//			
//			@Override
//			public Bitmap process(Bitmap arg0) {
//				return ImageUtil.toRoundBitmap(arg0);
//			}
//		})
//		.postProcessor(postProcessor)
//		.extraForDownloader(extra)
		.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
		.bitmapConfig(Bitmap.Config.ARGB_8888)
//		.decodingOptions(decodingOptions)
		.displayer(new SimpleBitmapDisplayer())
		.handler(new Handler());
		return builder.build();
	}
}
