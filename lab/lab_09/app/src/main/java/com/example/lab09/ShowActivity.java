package com.example.lab09;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab09.Contatant;

public class ShowActivity extends AppCompatActivity {
	public TextView FirstName, LastName, Email;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.show_layout);

		this.FirstName = findViewById(R.id.FirstName);
		this.LastName = findViewById(R.id.LastName);
		this.Email = findViewById(R.id.Email);

		Intent intent = getIntent();
		if (intent != null) {
			this.FirstName.setText(intent.getStringExtra(Contatant.FirstName));
			this.LastName.setText(intent.getStringExtra(Contatant.LastName));
			this.Email.setText(intent.getStringExtra(Contatant.Email));
		}
	}
}
