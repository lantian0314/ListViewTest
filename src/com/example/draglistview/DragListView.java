package com.example.draglistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.draglistview.DragListActivity.DragListAdapter;
import com.example.listviewtest.R;

public class DragListView extends ListView {

	private int scaledTouchSlop;// �жϻ����ľ���
	private int upScrollBounce;// �϶���ʱ�򣬿�ʼ���Ϲ����ı߽�
	private int downScrollBounce;// �϶���ʱ�򣬿�ʼ���¹����ı߽�

	private int dragSrcPosition;// ��ָ�϶���ԭʼ���б��е�λ��
	private int dragPosition;// ��ָ�϶���ʱ�򣬵�ǰ�϶������б��е�λ��

	private int dragPoint;// �ڵ�ǰ�������е�λ��
	private int dragOffset;// ��ǰ��ͼ����Ļ�ľ���(����ֻʹ����y������)

	private WindowManager windowManager;// windows���ڿ�����
	private WindowManager.LayoutParams windowParams;// ���ڿ�����ק�����ʾ�Ĳ���

	private ImageView dragImageView;// ����ק���Ӱ����ʵ����һ��ImageView

	public DragListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// ����Down�¼�
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			// ѡ�е�������λ�ã�ʹ��ListView�Դ���pointToPosition(x, y)����
			dragSrcPosition = dragPosition = pointToPosition(x, y);
			// �������Чλ��(�����߽磬�ָ��ߵ�λ��)������
			if (dragPosition == AdapterView.INVALID_POSITION) {
				return super.onInterceptTouchEvent(ev);
			}

			// ��ȡѡ����View
			// getChildAt(int position)��ʾdisplay�ڽ����positionλ�õ�View
			// getFirstVisiblePosition()���ص�һ��display�ڽ����view��adapter��λ��position��������0��Ҳ������4
			ViewGroup iteamView = (ViewGroup) getChildAt(dragPosition
					- getFirstVisiblePosition());

			// dragPoint���λ���ڵ��View�ڵ����λ��
			// dragOffset��Ļλ�ú͵�ǰListViewλ�õ�ƫ����������ֻ�õ�y�����ϵ�ֵ
			// �������������ں����϶��Ŀ�ʼλ�ú��ƶ�λ�õļ���
			dragPoint = y - iteamView.getTop();
			dragOffset = (int) (ev.getRawY() - y);

			// ��ȡ�ұߵ��϶�ͼ�꣬����Ժ��������ק������
			View dragger = iteamView.findViewById(R.id.Drag_list_iteam_Image);

			// ������ұ�λ�ã���קͼƬ��ߵ�20px���ұ�����
			if (dragger != null && x > dragger.getLeft() - 20) {
				// ׼���϶�
				// ��ʼ���϶�ʱ��������
				// scaledTouchSlop�������϶���ƫ��λ(һ��+-10)
				// upScrollBounce������Ļ���ϲ�(����1/3����)���߸��ϵ�����ִ���϶��ı߽磬downScrollBounceͬ����
				upScrollBounce = Math.min(y - scaledTouchSlop, getHeight() / 3);
				downScrollBounce = Math.max(y + scaledTouchSlop,
						getHeight() * 2 / 3);

				// ����DrawingcacheΪtrue�����ѡ�����Ӱ��bm�����Ǻ��������϶����ĸ�ͷ��
				iteamView.setDrawingCacheEnabled(true);
				Bitmap bm = Bitmap.createBitmap(iteamView.getDrawingCache());

				// ׼���϶�Ӱ��(��Ӱ����뵽��ǰ���ڣ���û���϶����϶��������Ƿ���onTouchEvent()��move��ִ��)
				startDrag(bm, y);
			}
			return false;
		}
		return super.onInterceptTouchEvent(ev);
	}

	private void startDrag(Bitmap bm, int y) {
		// �ͷ�Ӱ����׼��Ӱ���ʱ�򣬷�ֹӰ��û�ͷţ�ÿ�ζ�ִ��һ��
		stopDrag();

		windowParams = new WindowManager.LayoutParams();
		windowParams.gravity = Gravity.TOP;
		windowParams.x = 0;
		windowParams.y = y - dragPoint + dragOffset;
		windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		// ������Щ�����ܹ�����׼ȷ��λ��ѡ������λ�ã��ճ�����
		windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

		windowParams.format = PixelFormat.TRANSLUCENT;
		windowParams.windowAnimations = 0;

		// ��Ӱ��ImagView��ӵ���ǰ��ͼ��
		ImageView imageView = new ImageView(getContext());
		imageView.setImageBitmap(bm);

		windowManager = (WindowManager) getContext().getSystemService("window");
		windowManager.addView(imageView, windowParams);
		// ��Ӱ��ImageView���õ�����drawImageView�����ں�������(�϶����ͷŵȵ�)
		dragImageView = imageView;
	}

	private void stopDrag() {
		if (dragImageView != null) {
			windowManager.removeView(dragImageView);
			dragImageView = null;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// ���dragmageViewΪ�գ�˵�������¼����Ѿ��ж������ǵ���������϶�������
		// ������������Чλ�ã����أ���Ҫ�����ж�
		if (dragImageView != null && dragPosition != INVALID_POSITION) {
			int action = ev.getAction();
			switch (action) {
			case MotionEvent.ACTION_UP:
				int upY = (int) ev.getY();

				// �ͷ��϶�Ӱ��
				stopDrag();
				// ���º��ж�λ�ã�ʵ����Ӧ��λ��ɾ���Ͳ���
				onDrop(upY);
				break;
			case MotionEvent.ACTION_MOVE:
				int moveY = (int) ev.getY();
				// �϶�Ӱ��
				onDrag(moveY);
				break;
			default:
				break;
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}

	private void onDrag(int y) {
		if (dragImageView != null) {

			windowParams.alpha = 0.8f;
			windowParams.y = y - dragPoint + dragOffset;
			windowManager.updateViewLayout(dragImageView, windowParams);
		}

		// Ϊ�˱��⻬�����ָ��ߵ�ʱ�򣬷���-1������
		int tempPosition = pointToPosition(0, y);
		if (tempPosition != INVALID_POSITION) {

		}

		// ����
		int scrollHeight = 0;
		if (y < upScrollBounce) {
			scrollHeight = 8;// �������Ϲ���8�����أ�����������Ϲ����Ļ�
		} else if (y > downScrollBounce) {
			scrollHeight = -8;// �������¹���8�����أ�������������Ϲ����Ļ�
		}

		if (scrollHeight != 0) {
			// ���������ķ���setSelectionFromTop()
			setSelectionFromTop(dragPosition,
					getChildAt(dragPosition - getFirstVisiblePosition())
							.getTop() + scrollHeight);
		}

	}

	private void onDrop(int y) {
		// ��ȡ����λ�������ݼ�����position
		// ������ʱλ�ñ���Ϊ�˱��⻬�����ָ��ߵ�ʱ�򣬷���-1�����⣬���Ϊ-1�����޸�dragPosition��ֵ������ִ�У��ﵽ������Чλ�õ�Ч��

		int tempPosition = pointToPosition(0, y);
		if (tempPosition != INVALID_POSITION) {
			dragPosition = tempPosition;
		}

		// �����߽紦��
		if (y < getChildAt(0).getTop()) {
			// �����ϱ߽磬��Ϊ��Сֵλ��0
			dragPosition = 0;
		} else if (y > getChildAt(getChildCount() - 1).getBottom()) {
			// �����±߽磬��Ϊ���ֵλ�ã�ע��Ŷ��������ڿ��ӽ���������View�ĵײ�����Խ�½磬�����ж�����getChildCount()����
			// �������һ�������ݼ����е�position��getAdapter().getCount()-1�����Ҫ�������
			dragPosition = getAdapter().getCount() - 1;
		}

		// ���ݸ���
		if (dragPosition > 0 && dragPosition < getAdapter().getCount()) {
			@SuppressWarnings("unchecked")
			DragListAdapter adapter = (DragListAdapter)getAdapter();
			String dragItem = adapter.getItem(dragSrcPosition);
			// ɾ��ԭλ��������
			adapter.remove(dragItem);
			// ����λ�ò����϶���
			adapter.insert(dragItem, dragPosition);
			adapter.notifyDataSetChanged();
			Toast.makeText(getContext(), adapter.getList().toString(), Toast.LENGTH_SHORT).show();
		}

	}
}
