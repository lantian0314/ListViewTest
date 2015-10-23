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

	private List<String> list=null;
	
	//��ŷ����ǩ
	private static List<String> groupKey=new ArrayList<String>();
	
	//����һ
	private List<String> firstList=new ArrayList<String>();
	//�����
	private List<String> secondList=new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drag_list_main);
		
		//��ʼ������
		initData();
		
		
	}

	private void initData() {
		list=new ArrayList<String>();
		
		//groupKey��ŷ����ǩ
		groupKey.add("A��");
		groupKey.add("B��");
		
		for (int i = 0; i < 3; i++) {
			firstList.add("Aѡ��"+i);
		}
		list.add("A��");
		list.addAll(firstList);
		
		for (int i = 0; i < 3; i++) {
			secondList.add("Bѡ��"+i);
		}
		list.add("B��");
		list.addAll(secondList);
	}
	
	private class DragListAdapter extends ArrayAdapter<String>{

		public DragListAdapter(Context context, int resource,
				List<String> objects) {
			super(context, resource, objects);
			
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view=convertView;
			if (view==null) {
				view=LayoutInflater.from(getContext()).inflate(R.layout.drag_list_iteam, null);
				TextView iteamText=(TextView) view.findViewById(R.id.Drag_list_iteam_text);
				iteamText.setText(list.get(position));
			}
			return view;
		}
	}
}
