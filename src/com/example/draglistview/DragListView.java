package com.example.draglistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewConfiguration;
import android.widget.ListView;

public class DragListView extends ListView {

	private int scaledTouchSlop;//ÅÐ¶Ï»¬¶¯µÄ¾àÀë
	public DragListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		scaledTouchSlop=ViewConfiguration.get(context).getScaledTouchSlop();
	}

}
