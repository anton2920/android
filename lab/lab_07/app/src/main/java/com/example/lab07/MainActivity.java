package com.example.lab07;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends Activity {
	public EditText FirstName, LastName, Patronymic, Email;

	public DatabaseReference DB;
	public String UserKey = "User";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		this.FirstName = findViewById(R.id.FirstName);
		this.LastName = findViewById(R.id.LastName);
		this.Patronymic = findViewById(R.id.Patronymic);
		this.Email = findViewById(R.id.Email);

		this.DB = FirebaseDatabase.getInstance().getReference(UserKey);
	}

	public void onClickSave(View view) {
		String id = this.DB.getKey();

		String firstName = this.FirstName.getText().toString();
		String lastName = this.LastName.getText().toString();
		String patronymic = this.Patronymic.getText().toString();
		String email = this.Email.getText().toString();

		if ((TextUtils.isEmpty(firstName)) || (TextUtils.isEmpty(lastName)) || (TextUtils.isEmpty(patronymic)) || (TextUtils.isEmpty(email))) {
			Toast.makeText(this, "One of the fields is empty", Toast.LENGTH_SHORT).show();
			return;
		}

		this.DB.push().setValue(new User(id, firstName, lastName, patronymic, email));
		Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
	}

	public void onClickRead(View view) {
	}
}
