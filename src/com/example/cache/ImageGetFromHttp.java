package com.example.cache;

import java.io.FilterInputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.utils.Tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageGetFromHttp {

	public static Bitmap downloadBitmap(String url) {
		HttpClient client = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(url);
		try {
			HttpResponse response = client.execute(getRequest);
			int code = response.getStatusLine().getStatusCode();
			if (code != HttpStatus.SC_OK) {
				Tools.printLog("Error:" + code + "..........." + "url:" + url);
				return null;
			}

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream is = null;
				try {
					is = entity.getContent();
					FilterInputStream fit = new FlushInputStream(is);
					return BitmapFactory.decodeStream(fit);
				} catch (Exception e) {
					Tools.e(e);
				} finally {
					if (is != null) {
						is.close();
						is = null;
					}
					//entity.consumeContent();
				}
			}
		} catch (Exception e) {
			Tools.e(e);
		} finally {
			client.getConnectionManager().shutdown();
		}
		return null;
	}

}
