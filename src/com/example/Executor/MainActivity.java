package com.example.Executor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.Executor.AsyncImageLoader.imageCallBack;
import com.example.listviewtest.R;
import com.example.listviewtest.R.drawable;
import com.example.utils.Tools;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cache_main);
		loadImage("http://www.baidu.com/img/baidu_logo.gif", R.id.cache_img);
	}

	private Handler mHandler = new Handler();
//
//	private void loadImage(final String url, final int id) {
//		mHandler.post(new Runnable() {
//			@Override
//			public void run() {
//				
//				new AsyncTask<String, Void, Drawable>(){
//					Drawable mDrawable = null;
//					@Override
//					protected Drawable doInBackground(String... params) {
//						try {
//							mDrawable = Drawable.createFromStream(
//									new URL(url).openStream(), "image.gif");
//							return mDrawable;
//						} catch (Exception e) {
//							Tools.e(e);
//						}
//						return null;
//					}
//					
//					@Override
//					protected void onPostExecute(Drawable result) {
//						// 测试缓存模拟网络延迟
//						SystemClock.sleep(2000);
//						((ImageView) MainActivity.this.findViewById(id))
//								.setImageDrawable(mDrawable);
//					}
//				}.execute(url);
//				
//			}
//		});
//	}
	
//	private ExecutorService executorService=Executors.newFixedThreadPool(5);
//	
//	private void loadImage(final String url,final int id){
//		executorService.submit(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					final Drawable mDrawable=Drawable.createFromStream(new URL(url).openStream(), "image.png");
//					SystemClock.sleep(1000);
//					mHandler.post(new Runnable() {
//						
//						@Override
//						public void run() {
//							 ((ImageView) MainActivity.this.findViewById(id))
//                             .setImageDrawable(mDrawable);
//							
//						}
//					});
//				} catch (Exception e) {
//					Tools.e(e);
//				}
//				
//			}
//		});
//	}
	private AsyncImageLoader loader=new AsyncImageLoader();
	
	// 引入线程池，并引入内存缓存功能,并对外部调用封装了接口，简化调用过程
	private void loadImage(final String url,final int id){
		 // 如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
		Drawable cacheImage=loader.loadDrawable(url, new imageCallBack() {	
			@Override
			public void imageLoad(Drawable imageDrawable) {		
                        ((ImageView) findViewById(id))
                                        .setImageDrawable(imageDrawable);
                        
                }
		});
		if (cacheImage!=null) {
			 ((ImageView) findViewById(id))
             .setImageDrawable(cacheImage);
		}
	}
}
