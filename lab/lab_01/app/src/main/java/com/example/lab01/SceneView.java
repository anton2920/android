package com.example.lab01;

import android.content.Context;
import android.graphics.*;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.*;

import static android.content.Context.WINDOW_SERVICE;

public class SceneView extends View {
	public static Rect Src, Dst;
	public static Bitmap Background, SpritePalette, Sprite;
	public static int MaxAnimationStep = 300;

	public List<PointF> Points = new ArrayList<>();
	public Path Path = new Path();

	public float SegmentLen;
	public int CurrentStep = 0;

	public PathMeasure PathMeasure_;
	public Paint Paint_;

	public SceneView(Context context) {
		super(context);

		Display display = ((WindowManager) context.getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		Dst = new Rect(0, 0, width, height);

		if (Background == null) {
			Background = BitmapFactory.decodeResource(getResources(), R.drawable.background_image);
			Src = new Rect(0, 0, Background.getWidth(), Background.getHeight());
		}

		if (SpritePalette == null) {
			SpritePalette = BitmapFactory.decodeResource(getResources(), R.drawable.sprite1);
			Sprite = Bitmap.createBitmap(SpritePalette, 0, 0, 75, 110);
		}

		this.Points.add(new PointF(10, 260));
		this.Points.add(new PointF(500, 20));
		this.Points.add(new PointF(700, 500));
		this.Points.add(new PointF(900, 300));
		this.Points.add(new PointF(1200, 100));
		this.Points.add(new PointF(2000, 800));
		this.Points.add(new PointF(2800, 200));

		PointF point, nextPoint;
		point = this.Points.get(0);
		this.Path.moveTo(point.x, point.y);
		for (int i = 1; i < this.Points.size() - 1; ++i) {
			point = this.Points.get(i);
			nextPoint = this.Points.get(i + 1);

			this.Path.quadTo(point.x, point.y, (nextPoint.x + point.x) / 2, (nextPoint.y + point.y) / 2);
		}

		this.PathMeasure_ = new PathMeasure(this.Path, false);

		this.SegmentLen = this.PathMeasure_.getLength() / MaxAnimationStep;

		this.Paint_ = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.Paint_.setStyle(Paint.Style.STROKE);
		this.Paint_.setStrokeWidth(3);
		this.Paint_.setColor(Color.rgb(0, 148, 255));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(Background, Src, Dst, null);
		canvas.drawPath(this.Path, this.Paint_);

		this.Paint_.setColor(Color.RED);
		this.Paint_.setStrokeWidth(5);
		for (int i = 0; i < this.Points.size(); ++i) {
			PointF point = this.Points.get(i);
			canvas.drawCircle(point.x, point.y, 10, this.Paint_);
		}

		this.Paint_.setColor(Color.GREEN);

		Matrix transform = new Matrix();
		if (this.CurrentStep > MaxAnimationStep) {
			this.CurrentStep = 0;
		}

		this.PathMeasure_.getMatrix(this.SegmentLen * this.CurrentStep, transform, PathMeasure.POSITION_MATRIX_FLAG|PathMeasure.TANGENT_MATRIX_FLAG);
		transform.preTranslate(-Sprite.getWidth(), -Sprite.getHeight());
		canvas.drawBitmap(Sprite, transform, null);

		++this.CurrentStep;
		invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			invalidate();
			return true;
		}

		return false;
	}
}
