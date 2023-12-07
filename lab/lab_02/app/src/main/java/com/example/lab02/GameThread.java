package com.example.lab02;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
	public boolean Running;
	public GameSurface GameSurface_;
	public SurfaceHolder SurfaceHolder_;

	public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder) {
		this.GameSurface_ = gameSurface;
		this.SurfaceHolder_ = surfaceHolder;
	}

	@Override
	public void run() {
		long startTime = System.nanoTime();

		while (this.Running) {
			Canvas canvas = null;

			try {
				canvas = this.SurfaceHolder_.lockCanvas();

				synchronized (canvas) {
					this.GameSurface_.update();
					this.GameSurface_.draw(canvas);
				}
			} catch (Exception e) {
				/* NOTE(anton2920): do nothing. */
			} finally {
				if (canvas != null) {
					this.SurfaceHolder_.unlockCanvasAndPost(canvas);
				}
			}

			long now = System.nanoTime();
			long waitTime = (now - startTime) / (1000 * 1000);
			if (waitTime < 10) {
				waitTime = 10;
			}
			System.out.print(" Wait time=" + waitTime);

			try {
				sleep(waitTime);
			} catch (InterruptedException e) {

			}
			startTime = System.nanoTime();
		}
	}
}
