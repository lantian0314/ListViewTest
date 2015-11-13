package com.example.Translation;

import java.util.Random;

import com.example.listviewtest.R;
import com.example.utils.Tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("DrawAllocation")
public class GuaGuaLe extends View {

	private Path mPath = null;
	private String mText = null;

	private Paint mPaint = null;// 画笔
	private Paint mOutPaint = null;
	private Rect mRect = new Rect();

	private Canvas canvas;

	private boolean isComplete = false;

	private Bitmap mBitmap;

	private int mLastX;
	private int mLastY;
	
	public GuaGuaLe(Context context) {
		this(context, null);
	}

	public GuaGuaLe(Context context, AttributeSet attrs) {
		this(context, null, 0);
	}

	public GuaGuaLe(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mPath = new Path();// 记录用户绘画的路径
		String[] strings = { "￥50", "￥500", "谢谢参与", "$5,000", "谢谢参与",
				"$80,000", "谢谢参与" };
		Random random = new Random();
		int index = random.nextInt(strings.length);
		mText = strings[index];

		initBackPaints();
		initOutPaints();
	}

	private void initOutPaints() {
		mOutPaint = new Paint();
		mOutPaint.setColor(Color.parseColor("#c0c0c0"));
		mOutPaint.setAntiAlias(true);
		mOutPaint.setDither(true);
		mOutPaint.setStyle(Paint.Style.STROKE);
		mOutPaint.setStrokeJoin(Paint.Join.ROUND); // 圆角
		mOutPaint.setStrokeCap(Paint.Cap.ROUND); // 圆角
		// 设置画笔宽度
		mOutPaint.setStrokeWidth(20);
	}

	/**
	 * 初始化绘制用的画笔
	 */
	private void initBackPaints() {
		mPaint = new Paint();
		mPaint.setStyle(Style.FILL);
		mPaint.setTextScaleX(2f);
		mPaint.setColor(Color.DKGRAY);
		mPaint.setTextSize(32);
		mPaint.getTextBounds(mText, 0, mText.length(), mRect);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// 绘制奖项
		canvas.drawText(mText, (getWidth() - mRect.width()) / 2,
				(getHeight() - mRect.height()) / 2, mPaint);
		if (!isComplete) {
			drawOutCanves();
			canvas.drawBitmap(mBitmap, 0, 0, null);
		}
	}

	private void drawOutCanves() {
		mOutPaint.setStyle(Paint.Style.STROKE);
		mOutPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
		canvas.drawPath(mPath, mOutPaint);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		// 初始化Bitmap
		mBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		canvas = new Canvas(mBitmap);

		// 绘制遮盖层
		mOutPaint.setStyle(Paint.Style.FILL);
		canvas.drawRoundRect(new RectF(0, 0, width, height), 30, 30, mOutPaint);
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.s_title), null, new RectF(0, 0, width, height),
				null);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action=event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastX=x;
			mLastY=y;
			mPath.moveTo(x, y);
			break;
		case MotionEvent.ACTION_MOVE:
			int dx = Math.abs(x - mLastX);
			int dy = Math.abs(y - mLastY);
			if (dx > 3 || dy > 3)
				mPath.lineTo(x, y);

			mLastX = x;
			mLastY = y;
			break;
		case MotionEvent.ACTION_UP:
			new Thread(mRunnable).start();
			break;
		default:
			break;
		}
		invalidate();
		return true;
	}
	
	private Runnable mRunnable=new Runnable() {	
		private int[] mPixels;
		@Override
		public void run() {
			int w = getWidth();
			int h = getHeight();

			float wipeArea = 0;
			float totalArea = w * h;

			Bitmap bitmap = mBitmap;

			mPixels = new int[w * h];

			/**
			 * 拿到所有的像素信息
			 */
			bitmap.getPixels(mPixels, 0, w, 0, 0, w, h);

			/**
			 * 遍历统计擦除的区域
			 */
			for (int i = 0; i < w; i++)
			{
				for (int j = 0; j < h; j++)
				{
					int index = i + j * w;
					if (mPixels[index] == 0)
					{
						wipeArea++;
					}
				}
			}

			/**
			 * 根据所占百分比，进行一些操作
			 */
			if (wipeArea > 0 && totalArea > 0)
			{
				int percent = (int) (wipeArea * 100 / totalArea);
				//Log.e("TAG", percent + "");
				Tools.printLog("percent:"+percent);

				if (percent >= 70)
				{
					//Log.e("TAG", "清除区域达到70%，下面自动清除");
					Tools.printLog(">=70 auto clean");
					isComplete = true;
					postInvalidate();
				}
			}
		}
	};
}
