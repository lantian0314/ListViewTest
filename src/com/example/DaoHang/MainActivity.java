package com.example.DaoHang;

import java.util.ArrayList;
import java.util.List;

import com.example.listviewtest.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private List<Integer> listImage = new ArrayList<Integer>();
	private List<ImageView> listmb = new ArrayList<ImageView>();// 方蒙版布局的list
	private ImageView ivImageView;
	private TextView textView;
	private Button btn_ok;
	private ListView listView;
	
	private myAdapter myadapter=new myAdapter();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daohang);
		//设置数据
		setData();
		//初始化控件
		initView();
		
		setView();
	}

	private void setView() {
		listView.setAdapter(myadapter);
		listView.setOnScrollListener(new myScroll());
		
	}

	private void initView() {
		btn_ok=(Button) findViewById(R.id.btn_skill);
		ivImageView=(ImageView) findViewById(R.id.ivgstp);
		listView=(ListView) findViewById(R.id.listView);
		textView=(TextView) findViewById(R.id.tvzj);
	}

	private void setData() {
		listImage.add(R.drawable.img1);
		listImage.add(R.drawable.img2);
		listImage.add(R.drawable.img3);
		listImage.add(R.drawable.img4);
	}

	private static final int MSG_OK_ = 10;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_OK_:
				myadapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
		};
	};

	private class myAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return listImage.size();
		}

		@Override
		public Object getItem(int position) {
			return listImage.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = LayoutInflater.from(getApplicationContext()).inflate(
					R.layout.list_item, null);
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.imageView1);
			ImageView ivmb = (ImageView) convertView.findViewById(R.id.ivmb);

			if (listmb.size() - 1 >= position) {
				ivmb.setVisibility(TRIM_MEMORY_BACKGROUND);
			} else {
				listmb.add(ivmb);
			}
			imageView.setImageResource(listImage.get(position));
			return convertView;
		}
	}

	private class myScroll implements OnScrollListener {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			ivImageView.setImageResource(listImage.get(firstVisibleItem
					+ visibleItemCount / 2));
			textView.setText("第"
					+ (firstVisibleItem + visibleItemCount / 2 + 1) + "张");
			for (int i = 0; i < listmb.size(); i++) {
				if (i != (firstVisibleItem + visibleItemCount / 2)) {
					ivImageView.setVisibility(View.VISIBLE);
				} else {
					ivImageView.setVisibility(View.INVISIBLE);
				}
			}
			mHandler.sendEmptyMessage(MSG_OK_);
		}

	}
}
