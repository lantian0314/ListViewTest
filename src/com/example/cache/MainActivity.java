package com.example.cache;

import com.example.listviewtest.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ImageMemoryCache memoryCache;
	private ImageFileCache fileCache;
	private ImageView imageView;
	private static Bitmap result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cache_main);
		memoryCache = new ImageMemoryCache(getApplicationContext());
		fileCache = new ImageFileCache();
		imageView = (ImageView) findViewById(R.id.cache_img);
		Bitmap bitmap = getBitmap("http://f.hiphotos.baidu.com/album/w%3D2048/sign=7aa167f79f2f07085f052d00dd1cb999/472309f7905298228f794c7bd6ca7bcb0b46d4c4.jpg");
		imageView.setImageBitmap(bitmap);
	}

	private Bitmap getBitmap(final String url) {
		result = memoryCache.getBitmapFromCache(url);
		if (result == null) {
			result = fileCache.getImage(url);
			if (result == null) {
				new  AsyncTask<String, Void, Bitmap>(){
					@Override
					protected Bitmap doInBackground(String... params) {
						result = ImageGetFromHttp.downloadBitmap(url);
						return result;
					}
					
					@Override
					protected void onPostExecute(Bitmap result) {
						if (result != null) {
							fileCache.saveBitmap(result, url);
							memoryCache.addBitmapToCache(url, result);
							imageView.setImageBitmap(result);
						}
					}
				}.execute(url);

			} else {
				memoryCache.addBitmapToCache(url, result);
			}

		}
		return result;
	}

}
