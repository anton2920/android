package com.example.lab03;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MainActivity extends Activity implements View.OnTouchListener {
	public static boolean LeftPressed = false;
	public static boolean RightPressed = false;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		GameView gameView = new GameView(this);

		LinearLayout gameLayout = findViewById(R.id.GameLayout);
		gameLayout.addView(gameView);

		Button leftButton = findViewById(R.id.LeftButton);
		Button rightButton = findViewById(R.id.RightButton);

		leftButton.setOnTouchListener(this);
		rightButton.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v.getId() == R.id.LeftButton) {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					LeftPressed = true;
					break;
				case MotionEvent.ACTION_UP:
					LeftPressed = false;
					break;
			}
		} else if (v.getId() == R.id.RightButton) {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					RightPressed = true;
					break;
				case MotionEvent.ACTION_UP:
					RightPressed = false;
					break;
			}
		}
		return true;
	}
}
