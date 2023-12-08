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
	public Sensor SensorMagnet;

	public float[] ValuesAccel = new float[3];
	public float[] ValuesMagnet = new float[3];
	public float[] Gravity = new float[9];
	public float[] OutGravity = new float[9];
	public float[] Values = new float[3];

	public float[] Degree = new float[1];

	public SensorEventListener Listener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent event) {
			switch (event.sensor.getType()) {
				case Sensor.TYPE_ACCELEROMETER:
					System.arraycopy(event.values, 0, ValuesAccel, 0, 3);
					break;
				case Sensor.TYPE_MAGNETIC_FIELD:
					System.arraycopy(event.values, 0, ValuesMagnet, 0, 3);
					break;
			}

			SensorManager.getRotationMatrix(Gravity, null, ValuesAccel, ValuesMagnet);
			SensorManager.remapCoordinateSystem(Gravity, SensorManager.AXIS_X, SensorManager.AXIS_Z, OutGravity);
			SensorManager.getOrientation(OutGravity, Values);
			Degree[0] = (float) (Values[2] * 180 / Math.PI) + 90;
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(new Protactor(this, Degree));

		this.SensorManager_ = (SensorManager) getSystemService(SENSOR_SERVICE);
		this.SensorAccel = this.SensorManager_.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		this.SensorMagnet = this.SensorManager_.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
	}

	@Override
	protected void onResume() {
		super.onResume();

		this.SensorManager_.registerListener(this.Listener, this.SensorAccel, SensorManager.SENSOR_DELAY_NORMAL);
		this.SensorManager_.registerListener(this.Listener, this.SensorMagnet, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();

		this.SensorManager_.unregisterListener(this.Listener);
	}
}
