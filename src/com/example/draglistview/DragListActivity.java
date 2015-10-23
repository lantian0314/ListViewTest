package com.example.draglistview;

import java.util.ArrayList;
import java.util.List;

import com.example.listviewtest.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DragListActivity extends Activity {

	private List<String> list = null;

	// ��ŷ����ǩ
	private static List<String> groupKey = new ArrayList<String>();

	// ����һ
	private List<String> firstList = new ArrayList<String>();
	// �����
	private List<String> secondList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drag_list_main);

		// ��ʼ������
		initData();

		DragListView dragListView = (DragListView) findViewById(R.id.DragGroupList);
		DragListAdapter madapter = new DragListAdapter(this, 0, list);
		dragListView.setAdapter(madapter);
	}

	private void initData() {
		list = new ArrayList<String>();

		// groupKey��ŷ����ǩ
		groupKey.add("A��");
		groupKey.add("B��");

		for (int i = 0; i < 3; i++) {
			firstList.add("Aѡ��" + i);
		}
		list.add("A��");
		list.addAll(firstList);

		for (int i = 0; i < 3; i++) {
			secondList.add("Bѡ��" + i);
		}
		list.add("B��");
		list.addAll(secondList);
	}

	public class DragListAdapter extends ArrayAdapter<String> {

		private List<String> mList;

		public DragListAdapter(Context context, int resource, List<String> list) {
			super(context, resource, list);
			mList = list;
		}

		public List<String> getList() {
			return list;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				view = LayoutInflater.from(getContext()).inflate(
						R.layout.drag_list_iteam, null);
				
			}else {
				view=convertView;
			}
			TextView iteamText = (TextView) view
					.findViewById(R.id.Drag_list_iteam_text);
			iteamText.setText(getItem(position));
			return view;
		}
	}
}
