package com.example.lab02;

import android.graphics.Bitmap;

public class GameObject {
	public Bitmap Image;
	public int Nrows, Ncols;
	public int Width, Height;
	public int CharacterWidth, CharacterHeight;
	public int X, Y;

	public GameObject(Bitmap image, int nrows, int ncols, int x, int y) {
		this.Image = image;
		this.Nrows = nrows;
		this.Ncols = ncols;

		this.X = x;
		this.Y = y;

		this.Width = image.getWidth();
		this.Height = image.getHeight();

		this.CharacterWidth = this.Width/this.Ncols;
		this.CharacterHeight = this.Height/this.Nrows;
	}

	public Bitmap CreateSubImageAt(int row, int col) {
		return Bitmap.createBitmap(this.Image, col * this.CharacterWidth, row * this.CharacterHeight, this.CharacterWidth, this.CharacterHeight);
	}
}
