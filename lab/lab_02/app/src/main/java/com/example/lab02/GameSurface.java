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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

	public GameThread GameThread_;
	public List<ChibiCharacter> Chibies = new ArrayList<>();
	public List<Explosion> Explosions = new ArrayList<>();

	public GameSurface(Context context) {
		super(context);

		this.setFocusable(true);
		this.getHolder().addCallback(this);
	}

	public void update() {
		for (ChibiCharacter chibi: this.Chibies) {
			chibi.Update();
		}

		for (Explosion explosion: this.Explosions) {
			explosion.Update();
		}

		this.Explosions.removeIf(explosion -> explosion.Finished);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);

		for (ChibiCharacter chibi: this.Chibies) {
			chibi.Draw(canvas);
		}

		for (Explosion explosion: this.Explosions) {
			explosion.Draw(canvas);
		}
	}

	@Override
	public void surfaceCreated(@NonNull SurfaceHolder holder) {
		Bitmap chibi1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.chibi1);
		this.Chibies.add(new ChibiCharacter(this, chibi1, 100, 50));
		this.Chibies.add(new ChibiCharacter(this, chibi1, 300, 150));

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

			Iterator<ChibiCharacter> it = this.Chibies.iterator();
			while (it.hasNext()) {
				ChibiCharacter chibi = it.next();

				if ((x > chibi.X) && (x < chibi.X + chibi.CharacterWidth) && (y > chibi.Y) && (y < chibi.Y + chibi.CharacterHeight)) {
					it.remove();

					Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.explosion);
					this.Explosions.add(new Explosion(this, bitmap, chibi.X, chibi.Y));
				}
			}

			for (ChibiCharacter chibi: this.Chibies) {
				chibi.MovingVectorX = x - chibi.X;
				chibi.MovingVectorY = y - chibi.Y;
			}

			return true;
		}

		return false;
	}
}
