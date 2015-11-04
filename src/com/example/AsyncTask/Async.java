package com.example.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.listviewtest.R;
import com.example.utils.Tools;

public class Async extends Activity {

	private Button execute;
	private Button cancel;
	private TextView textView;
	private myTask mytask;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.async);
		execute = (Button) findViewById(R.id.execute);
		cancel = (Button) findViewById(R.id.cancel);
		execute.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mytask = new myTask();
				Tools.printLog("execute....");
				mytask.execute("https://www.baidu.com");
				execute.setEnabled(false);
				cancel.setEnabled(true);
			}
		});

		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mytask.cancel(true);
			}
		});

		progressBar = (ProgressBar) findViewById(R.id.progress);
		textView = (TextView) findViewById(R.id.textview);
	}

	public class myTask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Tools.printLog("Loading....");
			textView.setText("Loading...");
		}

		@Override
		protected String doInBackground(String... params) {
			Tools.printLog("doInBackground....");
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet get = new HttpGet(params[0]);
				HttpResponse response = httpClient.execute(get);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					InputStream is = entity.getContent();
					long total = entity.getContentLength();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] data = new byte[1024];
					int count = 0;
					int length = 0;
					while ((length = is.read(data)) != -1) {
						baos.write(data, 0, length);
						count += length;
						// 调用publishProgress公布进度,最后onProgressUpdate方法将被执行
						publishProgress((int) ((count / (float) total) * 100));
						// 为了演示进度,休眠500毫秒
						Thread.sleep(5000);
					}
					return new String(baos.toByteArray(),"UTF-8");
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			Tools.printLog("onProgressUpdate....");
			progressBar.setProgress(values[0]);
			textView.setText("Loading..."+values[0]+"%");
			
		}
		
		@Override
		protected void onPostExecute(String result) {
			Tools.printLog("onPostExecute....");
			textView.setText(result);
			execute.setEnabled(true);
			cancel.setEnabled(false);
			
		}
		
		@Override
		protected void onCancelled() {
			Tools.printLog("onCancelled....");
			textView.setText("cancel");
			progressBar.setProgress(0);
			
			execute.setEnabled(true);
			cancel.setEnabled(false);
		}
	}
}
