package com.example.lab05;

import static android.content.Context.WINDOW_SERVICE;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public class Protactor extends View {
	public static Bitmap ProtactorBitmap;
	public Bitmap WorkingBitmap;

	public float[] Degree;

	public Paint DegreePaint, LinePaint;

	public Protactor(Context context, float[] degree) {
		super(context);

		this.Degree = degree;

		Display display = ((WindowManager) context.getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int screenWidth = size.x;
		int screenHeight = size.y;

		if (ProtactorBitmap == null) {
			ProtactorBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.protactor);
			Matrix matrix = new Matrix();
			matrix.postRotate(180);
			ProtactorBitmap = Bitmap.createBitmap(ProtactorBitmap, 0, 0, ProtactorBitmap.getWidth(), ProtactorBitmap.getHeight(), matrix, true);
			this.Resize(screenWidth, screenHeight);
		}
	}

	public void Resize(int w, int h) {
		int scaledWidth = (int) (ProtactorBitmap.getWidth() * ((float) h / ProtactorBitmap.getHeight()));
		int scaledHeight = (int) (ProtactorBitmap.getHeight() * ((float) w / ProtactorBitmap.getWidth()));
		if (scaledHeight < h) {
			this.WorkingBitmap = Bitmap.createScaledBitmap(ProtactorBitmap, w, scaledHeight, true);
		} else {
			this.WorkingBitmap = Bitmap.createScaledBitmap(ProtactorBitmap, scaledWidth, h, true);
		}

		this.DegreePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.DegreePaint.setColor(Color.RED);
		this.DegreePaint.setTextSize(100);

		this.LinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.LinePaint.setColor(Color.BLUE);
		this.LinePaint.setStrokeWidth(10);
	}

	@Override
	public void onDraw(@NonNull Canvas canvas) {
		float startX = (getWidth() - this.WorkingBitmap.getWidth()) / 2.0f;
		startX = Math.max(0, startX);
		canvas.drawBitmap(this.WorkingBitmap, startX, 0, null);

		float centerX = getWidth()/2.0f;
		float centerY = getHeight()/2.0f;
		String text = String.format("%.1f", this.Degree[0]);
		float textWidth = this.DegreePaint.measureText(text);
		canvas.drawText(text, centerX-textWidth/2.0f, centerY, this.DegreePaint);

		float offset = this.WorkingBitmap.getHeight()*0.075f;
		canvas.save();
		canvas.rotate(this.Degree[0]+180, centerX, offset);
		canvas.drawLine(centerX, offset, startX, offset, this.LinePaint);
		canvas.restore();

		invalidate();
	}

	@Override
	public void onSizeChanged(int w, int h, int oldW, int oldH) {
		this.Resize(w, h);
	}
}
