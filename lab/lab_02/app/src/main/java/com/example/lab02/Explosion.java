package com.example.lab02;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Explosion extends GameObject {
	public int RowInUse = 0, ColInUse = -1;

	public boolean Finished = false;
	public GameSurface GameSurface_;

	public Explosion(GameSurface gameSurface, Bitmap image, int x, int y) {
		super(image, 4, 4, x, y);

		this.GameSurface_ = gameSurface;
	}

	public void Update() {
		++this.ColInUse;

		if (this.ColInUse >= this.Ncols) {
			this.ColInUse = 0;
			++this.RowInUse;

			if (this.RowInUse >= this.Nrows) {
				this.Finished = true;
			}
		}
	}

	public void Draw(Canvas canvas) {
		if (!this.Finished) {
			canvas.drawBitmap(this.CreateSubImageAt(this.RowInUse, this.ColInUse), this.X, this.Y, null);
		}
	}
}
