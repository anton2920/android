package com.example.lab09;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab09.Contatant;

public class ShowActivity extends AppCompatActivity {
	public TextView FirstName, LastName, Patronymic, Email;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.show_layout);

		this.FirstName = findViewById(R.id.FirstName);
		this.LastName = findViewById(R.id.LastName);
		this.Patronymic = findViewById(R.id.Patronymic);
		this.Email = findViewById(R.id.Email);

		Intent intent = getIntent();
		if (intent != null) {
			this.FirstName.setText(intent.getStringExtra(Contatant.FirstName));
			this.LastName.setText(intent.getStringExtra(Contatant.LastName));
			String patronymic = intent.getStringExtra(Contatant.Patronymic);
			if (TextUtils.isEmpty(patronymic)) {
				patronymic = "(patronymic is not specified)";
			}
			this.Patronymic.setText(patronymic);
			this.Email.setText(intent.getStringExtra(Contatant.Email));
		}
	}
}
