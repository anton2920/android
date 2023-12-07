package com.example.lab02;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

	public GameThread GameThread_;
	public ChibiCharacter Chibi1;

	public GameSurface(Context context) {
		super(context);

		this.setFocusable(true);
		this.getHolder().addCallback(this);
	}

	public void update() {
		this.Chibi1.Update();
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		this.Chibi1.Draw(canvas);
	}

	@Override
	public void surfaceCreated(@NonNull SurfaceHolder holder) {
		Bitmap chibi1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.chibi1);
		this.Chibi1 = new ChibiCharacter(this, chibi1, 100, 50);

		this.GameThread_ = new GameThread(this, holder);
		this.GameThread_.Running = true;
		this.GameThread_.start();
	}

	@Override
	public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
		while (true) {
			try {
				this.GameThread_.Running = false;
				this.GameThread_.join();
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			int x = (int) event.getX();
			int y = (int) event.getY();

			this.Chibi1.MovingVectorX = x - this.Chibi1.X;
			this.Chibi1.MovingVectorY = y - this.Chibi1.Y;

			return true;
		}

		return false;
	}
}
