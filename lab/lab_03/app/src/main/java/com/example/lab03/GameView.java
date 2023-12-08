package com.example.lab03;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements Runnable {
	public static int MaxX = 20;
	public static int MaxY = 28;

	public static float UnitW = 0;
	public static float UnitH = 0;
	public static boolean FirstTime = true;

	public static Ship Ship_;
	public static Paint Paint_;

	public boolean GameRunning = true;
	public Thread GameThread;
	public Canvas Canvas_;
	public SurfaceHolder SurfaceHolder_;

	public List<Asteroid> Asteroids = new ArrayList<>();
	public int AsteroidInterval = 50;

	public int CurrentTime = 0;

	public GameView(Context context) {
		super(context);

		this.SurfaceHolder_ = getHolder();
		Paint_ = new Paint();
		this.GameThread = new Thread(this);
		this.GameThread.start();
	}

	public void CheckCollision() {
		for (Asteroid asteroid: this.Asteroids) {
			if (asteroid.Collided(Ship_.X, Ship_.Y, Ship_.Size)) {
				this.GameRunning = false;
			}
		}
	}

	public void CreateNewAsteroids() {
		if (this.CurrentTime >= this.AsteroidInterval) {
			this.Asteroids.add(new Asteroid(getContext()));
			this.CurrentTime = 0;
		} else {
			++this.CurrentTime;
		}
	}

	@Override
	public void run() {
		while (this.GameRunning) {
			this.Update();
			this.Draw();
			this.CheckCollision();
			this.CreateNewAsteroids();
			this.Control();
		}
	}

	public void Update() {
		if (!FirstTime) {
			Ship_.Update();

			for (Asteroid asteroid: this.Asteroids) {
				asteroid.Update();
			}
		}
	}

	public void Draw() {
		if (this.SurfaceHolder_.getSurface().isValid()) {
			if (FirstTime) {
				FirstTime = false;
				UnitW = (float) this.SurfaceHolder_.getSurfaceFrame().width() / MaxX;
				UnitH = (float) this.SurfaceHolder_.getSurfaceFrame().height() / MaxY;
				Ship_ = new Ship(getContext());
			}

			this.Canvas_ = this.SurfaceHolder_.lockCanvas();
			this.Canvas_.drawColor(Color.BLACK);

			Ship_.Draw(Paint_, this.Canvas_);

			for (Asteroid asteroid: this.Asteroids) {
				asteroid.Draw(Paint_, Canvas_);
			}

			this.SurfaceHolder_.unlockCanvasAndPost(this.Canvas_);
		}
	}

	public void Control() {
		try {
			Thread.sleep(17);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
