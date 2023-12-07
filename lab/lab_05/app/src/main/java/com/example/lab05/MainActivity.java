package com.example.lab05;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
	public TextView Text;

	public SensorManager SensorManager_;
	public Sensor SensorAccel;
	public Sensor SensorLinAccel;
	public Sensor SensorGravity;

	public StringBuilder SB = new StringBuilder();

	public Timer Timer_;

	float[] ValuesAccel = new float[3];
	float[] ValuesAccelMotion = new float[3];
	float[] ValuesAccelGravity = new float[3];
	float[] ValuesLinAccel = new float[3];
	float[] ValuesGravity = new float[3];

	public SensorEventListener Listener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent event) {
			switch (event.sensor.getType()) {
				case Sensor.TYPE_ACCELEROMETER:
					for (int i = 0; i < 3; ++i) {
						ValuesAccel[i] = event.values[i];
						ValuesAccelGravity[i] = (float) (0.1 * event.values[i] + 0.9 * ValuesAccelGravity[i]);
						ValuesAccelMotion[i] = event.values[i] - ValuesAccelGravity[i];
					}
					break;
				case Sensor.TYPE_LINEAR_ACCELERATION:
					System.arraycopy(event.values, 0, ValuesLinAccel, 0, 3);
					break;
				case Sensor.TYPE_GRAVITY:
					System.arraycopy(event.values, 0, ValuesGravity, 0, 3);
					break;
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		this.Text = (TextView) findViewById(R.id.Text);

		this.SensorManager_ = (SensorManager) getSystemService(SENSOR_SERVICE);

		this.SensorAccel = this.SensorManager_.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		this.SensorLinAccel = this.SensorManager_.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		this.SensorGravity = this.SensorManager_.getDefaultSensor(Sensor.TYPE_GRAVITY);
	}

	@Override
	protected void onResume() {
		super.onResume();

		this.SensorManager_.registerListener(this.Listener, this.SensorAccel, SensorManager.SENSOR_DELAY_NORMAL);
		this.SensorManager_.registerListener(this.Listener, this.SensorLinAccel, SensorManager.SENSOR_DELAY_NORMAL);
		this.SensorManager_.registerListener(this.Listener, this.SensorGravity, SensorManager.SENSOR_DELAY_NORMAL);

		this.Timer_ = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						ShowInfo();
					}
				});
			}
		};
		this.Timer_.schedule(task, 0, 400);
	}

	@Override
	protected void onPause() {
		super.onPause();

		this.SensorManager_.unregisterListener(this.Listener);
		this.Timer_.cancel();
	}

	public String Format(float[] values) {
		assert values.length == 3;
		return String.format("%1$.1f\t\t%2$.1f\t\t%3$.1f", values[0], values[1], values[2]);
	}

	public void ShowInfo() {
		this.SB.setLength(0);
		this.SB.append("Acceleromter: ").append(this.Format(this.ValuesAccel)).append("\n\nAccel motion: ").append(this.Format(this.ValuesAccelMotion)).append("\nAccel gravity: ").append(this.Format(this.ValuesAccelGravity)).append("\n\nLin accel: ").append(this.Format(this.ValuesLinAccel)).append("\nGravity: ").append(this.Format(this.ValuesGravity));
		this.Text.setText(this.SB);
	}
}
