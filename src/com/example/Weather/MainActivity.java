package com.example.Weather;

import java.net.URLEncoder;

import com.example.listviewtest.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private EditText editText;
	private Button btn_search;
	private LinearLayout layout_result;
	private Weather weather;
	private search search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather);
		editText = (EditText) findViewById(R.id.editText);
		btn_search = (Button) findViewById(R.id.btn_search);
		layout_result = (LinearLayout) findViewById(R.id.layout_result);
		search = new search();
		setOnclickListener();
	}

	private void setOnclickListener() {
		btn_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				layout_result.removeAllViews();
				String text = editText.getText().toString().trim();
				// String text = "����";
				if (TextUtils.isEmpty(text)) {
					editText.setText("��ѯ���ݲ���Ϊ��");
				} else {
					final String city = URLEncoder.encode(text);
					new Thread(new Runnable() {
						@Override
						public void run() {
							weather=search.getWeather(city);
							mHandler.sendEmptyMessage(MSG_SEARCH_OK);
						}
					}).start();
				}
			}
		});
	}

	private void show() {
		TextView result = new TextView(getApplicationContext());
		result.setText("����:" + weather.getCity() + "\n" + "����:"
				+ weather.getDate() + "\n" + "����:" + weather.getWeather()
				+ "\n" + "�¶�:" + weather.getTemperature() + "\n" + "���:"
				+ weather.getWind() + "\n" + "����ָ��:"
				+ weather.getDressing_advice());
		layout_result.addView(result);
	};

	private static final int MSG_SEARCH_OK = 10;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_SEARCH_OK:
				show();
				break;

			default:
				break;
			}
		}
	};
}
