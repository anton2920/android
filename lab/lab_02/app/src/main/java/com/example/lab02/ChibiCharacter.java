package com.example.lab02;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Vector;

public class ChibiCharacter extends GameObject {
	public enum ChibiDirection {
		TOP_TO_BOTTOM,
		RIGHT_TO_LEFT,
		LEFT_TO_RIGHT,
		BOTTOM_TO_TOP
	}

	public ChibiDirection RowInUse = ChibiDirection.LEFT_TO_RIGHT;
	public int ColInUse;

	public Bitmap[] LeftToRights, RightToLefts, TopToBottoms, BottomToTops;

	public static float Velocity = 0.1f;

	public int MovingVectorX = 10, MovingVectorY = 5;

	public long LastDrawTime = -1;

	public GameSurface GameSurface_;

	public ChibiCharacter(GameSurface gameSurface, Bitmap image, int x, int y) {
		super(image, 4, 3, x, y);

		this.GameSurface_ = gameSurface;

		this.TopToBottoms = new Bitmap[this.Ncols];
		this.RightToLefts = new Bitmap[this.Ncols];
		this.LeftToRights = new Bitmap[this.Ncols];
		this.BottomToTops = new Bitmap[this.Ncols];

		for (int col = 0; col < this.Ncols; ++col) {
			this.TopToBottoms[col] = this.CreateSubImageAt(ChibiDirection.TOP_TO_BOTTOM.ordinal(), col);
			this.RightToLefts[col] = this.CreateSubImageAt(ChibiDirection.RIGHT_TO_LEFT.ordinal(), col);
			this.LeftToRights[col] = this.CreateSubImageAt(ChibiDirection.LEFT_TO_RIGHT.ordinal(), col);
			this.BottomToTops[col] = this.CreateSubImageAt(ChibiDirection.BOTTOM_TO_TOP.ordinal(), col);
		}
	}

	public Bitmap[] GetMoveBitmaps() {
		switch (this.RowInUse) {
			case BOTTOM_TO_TOP:
				return this.BottomToTops;
			case LEFT_TO_RIGHT:
				return this.LeftToRights;
			case RIGHT_TO_LEFT:
				return this.RightToLefts;
			case TOP_TO_BOTTOM:
				return this.TopToBottoms;
			default:
				return null;
		}
	}

	public Bitmap GetCurrentMoveBitmap() {
		return this.GetMoveBitmaps()[this.ColInUse];
	}

	public void Update() {
		this.ColInUse = (this.ColInUse + 1) % this.Ncols;

		long now = System.nanoTime();

		if (this.LastDrawTime < 0) {
			this.LastDrawTime = now;
		}

		int deltaTime = (int) ((now - this.LastDrawTime) / (1000 * 1000));
		float distance = Velocity * deltaTime;

		double movingVectorLength = Math.sqrt(this.MovingVectorX * this.MovingVectorX + this.MovingVectorY * this.MovingVectorY);

		this.X += (int) (distance * this.MovingVectorX / movingVectorLength);
		this.Y += (int) (distance * this.MovingVectorY / movingVectorLength);

		if (this.X < 0) {
			this.X = 0;
			this.MovingVectorX = -this.MovingVectorX;
		} else if (this.X > this.GameSurface_.getWidth() - this.Width) {
			this.X = this.GameSurface_.getWidth() - this.Width;
			this.MovingVectorX = -this.MovingVectorX;
		}

		if (this.Y < 0) {
			this.Y = 0;
			this.MovingVectorY = -this.MovingVectorY;
		} else if (this.Y > this.GameSurface_.getHeight() - this.Height) {
			this.Y = this.GameSurface_.getHeight() - this.Height;
			this.MovingVectorY = -this.MovingVectorY;
		}

		if (this.MovingVectorX > 0) {
			if ((this.MovingVectorY > 0) && (Math.abs(this.MovingVectorX) < Math.abs(this.MovingVectorY))) {
				this.RowInUse = ChibiDirection.TOP_TO_BOTTOM;
			} else if ((this.MovingVectorY < 0) && (Math.abs(this.MovingVectorX) < Math.abs(this.MovingVectorY))) {
				this.RowInUse = ChibiDirection.BOTTOM_TO_TOP;
			} else {
				this.RowInUse = ChibiDirection.LEFT_TO_RIGHT;
			}
		} else {
			if ((this.MovingVectorY > 0) && (Math.abs(this.MovingVectorX) < Math.abs(this.MovingVectorY))) {
				this.RowInUse = ChibiDirection.TOP_TO_BOTTOM;
			} else if ((this.MovingVectorY < 0) && (Math.abs(this.MovingVectorX) < Math.abs(this.MovingVectorY))) {
				this.RowInUse = ChibiDirection.BOTTOM_TO_TOP;
			} else {
				this.RowInUse = ChibiDirection.RIGHT_TO_LEFT;
			}
		}
	}

	public void Draw(Canvas canvas) {
		canvas.drawBitmap(this.GetCurrentMoveBitmap(), this.X, this.Y, null);
		this.LastDrawTime = System.nanoTime();
	}
}
