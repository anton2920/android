package com.example.lab01;

import android.graphics.*;
import java.util.*;

public class DrawContext {
	public static int MaxAnimationStep = 300;

	public Bitmap Sprite;
	public List<PointF> Points;
	public android.graphics.Path Path = new Path();

	public float SegmentLen;
	public int CurrentStep = 0;

	public PathMeasure PathMeasure_;
	public Paint Paint_;

	public DrawContext(Bitmap sprite, List<PointF> points) {
		this.Sprite = sprite;
		this.Points = points;

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

	public void Draw(Canvas canvas) {
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
	}
}
