package com.example.listviewtest;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private List<String> list = new ArrayList<String>();

	private GroupListAdapter mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setData();
		mAdapter = new GroupListAdapter(this, 0, list);
		ListView listView = (ListView) findViewById(R.id.GroupList);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				switch (position) {
				case 0:
					Toast.makeText(getApplicationContext(), "Clicked", 1).show();
					break;

				default:
					break;
				}
			}
			
		});
	}

	private void setData() {
		list.add("A");
		for (int i = 0; i < 3; i++) {
			list.add("阿凡达" + i);
		}

		list.add("B");
		for (int i = 0; i < 3; i++) {
			list.add("比特风暴" + i);
		}
	}

	private class GroupListAdapter extends ArrayAdapter<String> {

		private List<String> mlist = null;

		public GroupListAdapter(Context context, int resource, List<String> list) {
			super(context, resource, list);
			this.mlist = list;
		}

		@Override
		public boolean isEnabled(int position) {
			// if (mlistTag.contains(getItem(position))) {
			// return false;
			// }
			return super.isEnabled(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			view = LayoutInflater.from(getContext()).inflate(
					R.layout.list_iteam, null);
			TextView ItemText = (TextView) view.findViewById(R.id.ItemText);
			TextView Iteam_Tag_Text = (TextView) findViewById(R.id.Iteam_Tag_Text);
			if (position == 0) {
				Iteam_Tag_Text.setText("标签一");
			}
			ItemText.setText(list.get(position));

			return view;
		}
	}
}
