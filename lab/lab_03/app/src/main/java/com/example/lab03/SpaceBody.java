package com.example.lab03;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class SpaceBody {
	public float X, Y;

	public float Size;

	public float Speed;
	public int BitmapID;
	public Bitmap Bitmap_;

	void Init(Context context) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), this.BitmapID);
		this.Bitmap_ = Bitmap.createScaledBitmap(bitmap, (int) (this.Size * GameView.UnitW), (int) (this.Size * GameView.UnitH), false);

		bitmap.recycle();
	}

	void Update() {

	}

	void Draw(Paint paint, Canvas canvas) {
		canvas.drawBitmap(this.Bitmap_, this.X * GameView.UnitW, this.Y * GameView.UnitH, paint);
	}
}
