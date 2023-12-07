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
	public static Bitmap Background, SpritePalette, Sprite1, Sprite2;

	public DrawContext DrawContext1, DrawContext2;

	public SceneView(Context context) {
		super(context);

		Display display = ((WindowManager) context.getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int screenWidth = size.x;
		int screenHeight = size.y;
		
		Dst = new Rect(0, 0, screenWidth, screenHeight);
		if (Background == null) {
			Background = BitmapFactory.decodeResource(getResources(), R.drawable.background_image);
			Src = new Rect(0, 0, Background.getWidth(), Background.getHeight());
		}

		if (SpritePalette == null) {
			SpritePalette = BitmapFactory.decodeResource(getResources(), R.drawable.sprite1);
			Sprite1 = Bitmap.createBitmap(SpritePalette, 0, 0, 80, 120);
			Sprite2 = Bitmap.createBitmap(SpritePalette, 80, 120, 80, 120);
		}

		List<PointF> points1 = new ArrayList<>();
		points1.add(new PointF(10, 260));
		points1.add(new PointF(500, 20));
		points1.add(new PointF(700, 500));
		points1.add(new PointF(900, 300));
		points1.add(new PointF(1200, 100));
		points1.add(new PointF(2000, 800));
		points1.add(new PointF(2800, 200));

		List<PointF> points2 = new ArrayList<>();
		for (int i = 0; i < points1.size(); ++i) {
			PointF point = points1.get(i);
			points2.add(new PointF(point.x, screenHeight - point.y));
		}

		this.DrawContext1 = new DrawContext(Sprite1, points1);
		this.DrawContext2 = new DrawContext(Sprite2, points2);


	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(Background, Src, Dst, null);

		this.DrawContext1.Draw(canvas);
		this.DrawContext2.Draw(canvas);

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
