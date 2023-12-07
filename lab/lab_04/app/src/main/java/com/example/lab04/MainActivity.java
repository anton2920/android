package com.example.lab04;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public String Tag = "Life cycle";
	public TextView Info;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		this.Info = (TextView) findViewById(R.id.Info);
		Toast.makeText(getApplicationContext(), "onCreate()", Toast.LENGTH_SHORT).show();
		Log.i(this.Tag, "onCreate()");
	}

	@Override
	public void onStart() {
		super.onStart();

		Toast.makeText(getApplicationContext(), "onStart()", Toast.LENGTH_SHORT).show();
		Log.i(this.Tag, "onStart()");
	}

	@Override
	public void onResume() {
		super.onResume();

		Toast.makeText(getApplicationContext(), "onResume()", Toast.LENGTH_SHORT).show();
		Log.i(this.Tag, "onResume()");
	}

	@Override
	public void onPause() {
		super.onPause();

		Toast.makeText(getApplicationContext(), "onPause()", Toast.LENGTH_SHORT).show();
		Log.i(this.Tag, "onPause()");
	}

	@Override
	public void onStop() {
		super.onStop();

		Toast.makeText(getApplicationContext(), "onStop()", Toast.LENGTH_SHORT).show();
		Log.i(this.Tag, "onStop()");
	}

	@Override
	public void onRestart() {
		super.onRestart();

		Toast.makeText(getApplicationContext(), "onRestart()", Toast.LENGTH_SHORT).show();
		Log.i(this.Tag, "onRestart()");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT).show();
		Log.i(this.Tag, "onDestroy()");
	}

	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.TouchMe) {
			this.Info.setText("Application has been launched already!");
		} else if (id == R.id.Exit) {
			finish();
		}
	}
}
