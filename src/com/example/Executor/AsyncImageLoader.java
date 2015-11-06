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

	// Ϊ�˼ӿ��ٶȣ����ڴ��п������棨��ҪӦ�����ظ�ͼƬ�϶�ʱ������ͬһ��ͼƬҪ��α����ʣ�������ListViewʱ���ع�����
	private Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();

	private Handler mHandler = new Handler();

	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	public Drawable loadDrawable(final String url,final imageCallBack callBack) {
		// ���������������
		if (imageCache.containsKey(url)) {
			SoftReference<Drawable> softReference = imageCache.get(url);
			if (softReference != null) {
				return softReference.get();
			}
		}

		// ������û�У�������
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
	 * ������������Image
	 * @param url ���ӵ�ַ
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
	 * Image������ɺ�Ļص�
	 * @author xingyatong
	 *
	 */
	public interface imageCallBack {
		public void imageLoad(Drawable imageDrawable);
	}
}
