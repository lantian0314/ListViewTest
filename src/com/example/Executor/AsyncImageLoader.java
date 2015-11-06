package com.example.Executor;

import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.drawable.Drawable;
import android.os.Handler;

public class AsyncImageLoader {

	// 为了加快速度，在内存中开启缓存（主要应用于重复图片较多时，或者同一个图片要多次被访问，比如在ListView时来回滚动）
	private Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();

	private Handler mHandler = new Handler();

	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	public Drawable loadDrawable(final String url,final imageCallBack callBack) {
		// 如果缓存中有数据
		if (imageCache.containsKey(url)) {
			SoftReference<Drawable> softReference = imageCache.get(url);
			if (softReference != null) {
				return softReference.get();
			}
		}

		// 缓存中没有，就下载
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				final Drawable mDrawable = loadImageFromUrl(url);
				imageCache.put(url, new SoftReference<Drawable>(mDrawable));

				mHandler.post(new Runnable() {
					@Override
					public void run() {
					callBack.imageLoad(mDrawable);
					}
				});
			}
		});
		return null;
	}

	/**
	 * 根据链接下载Image
	 * @param url 链接地址
	 * @return
	 */
	private Drawable loadImageFromUrl(String url) {
		try {
			return Drawable.createFromStream(new URL(url).openStream(),
					"image.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Image加载完成后的回掉
	 * @author xingyatong
	 *
	 */
	public interface imageCallBack {
		public void imageLoad(Drawable imageDrawable);
	}
}
