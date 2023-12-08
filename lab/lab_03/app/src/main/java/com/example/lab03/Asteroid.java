package com.example.lab03;

import android.content.Context;

import java.util.Random;

public class Asteroid extends SpaceBody {
	public int Radius = 2;
	public float MinSpeed = 0.1f;
	public float MaxSpeed = 0.5f;

	public Asteroid(Context context) {
		Random random = new Random();

		this.BitmapID = R.drawable.asteroid;

		this.Y = 0;
		this.X = random.nextInt(GameView.MaxX) - this.Radius;

		this.Size = this.Radius * 2;
		this.Speed = this.MinSpeed + (this.MaxSpeed - this.MinSpeed) * random.nextFloat();

		this.Init(context);
	}

	@Override
	public void Update() {
		this.Y += this.Speed;
	}

	public boolean Collided(float shipX, float shipY, float shipSize) {
		return !((shipX > (this.X + this.Size)) || ((shipX + shipSize) < this.X) || (shipY > (this.Y + this.Size)) || ((shipY + shipSize) < this.Y));
	}
}
