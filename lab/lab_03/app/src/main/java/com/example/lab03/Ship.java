package com.example.lab03;

import android.content.Context;

public class Ship extends SpaceBody {
	public Ship(Context context) {
		this.BitmapID = R.drawable.ship;
		this.Size = 5;

		this.X = 7;
		this.Y = GameView.MaxY - this.Size - 1;
		this.Speed = 0.2f;

		this.Init(context);
	}

	public void Update() {
		if ((MainActivity.LeftPressed) && (this.X >= 0)) {
			this.X -= this.Speed;
		}
		if ((MainActivity.RightPressed) && (this.X <= GameView.MaxX - 5)) {
			this.X += this.Speed;
		}
	}
}
