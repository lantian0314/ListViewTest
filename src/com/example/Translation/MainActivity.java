package com.example.Translation;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.listviewtest.R;
import com.example.utils.Tools;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	protected static final int MSG_DOWNLOAD = 10;
	private EditText edit_trans;
	private TextView text_result;
	private Button btn_trans;
	private String shuru;
	
	private JSONObject object;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.translation);
		edit_trans = (EditText) findViewById(R.id.edit_trans);
		btn_trans = (Button) findViewById(R.id.btn_trans);
		text_result=(TextView) findViewById(R.id.text_result);
		doClick();
	}

	/**
	 * 点击监听事件
	 */
	private void doClick() {
		btn_trans.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				shuru=edit_trans.getText().toString().trim();
				shuru=URLEncoder.encode(shuru);//实现编码格式化
				if (TextUtils.isEmpty(shuru)) {
					edit_trans.setText("内容不能为空");
				}else {
					new Thread(new Runnable() {	
						@Override
						public void run() {
							try {
								object=new JSONObject(readParse("http://fanyi.youdao.com/openapi.do?keyfrom=woainixiaomao&key=1491298559&type=data&doctype=json&version=1.1&q="+shuru));
							} catch (Exception e) {
								e.printStackTrace();
							}
							mHandler.sendEmptyMessage(MSG_DOWNLOAD);//发送消息，加载成功
						}
					}).start();
				}
			}
		});
	}
	
	private  String readParse(String urlPath) throws Exception {  
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
		byte[] data = new byte[1024];  
		int len = 0;  
		URL url = new URL(urlPath);  
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
		InputStream inStream = conn.getInputStream();  
		while ((len = inStream.read(data)) != -1) {  
			outStream.write(data, 0, len);  
		}  
		inStream.close();  
		return new String(outStream.toByteArray());
	}
	
	private void show() {
		try {
			JSONArray array=object.getJSONArray("translation");
			for (int i = 0; i < array.length(); i++) {
				text_result.setText(array.getString(i));
				text_result.setTextColor(Color.BLACK);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	 
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_DOWNLOAD:
				try {
				show();	
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		}
	};
	
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	};
}
