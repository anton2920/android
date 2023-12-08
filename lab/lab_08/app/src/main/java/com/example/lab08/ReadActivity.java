package com.example.lab08;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity {
	public ListView ListView_;
	public ArrayAdapter<String> Adapter;
	public List<String> ListData;

	public DatabaseReference DB;
	public String UserKey = "User";

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read_layout);

		this.ListView_ = findViewById(R.id.ListView);
		this.ListData = new ArrayList<>();

		this.Adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.ListData);
		this.ListView_.setAdapter(this.Adapter);

		this.DB = FirebaseDatabase.getInstance().getReference(this.UserKey);

		ValueEventListener listener = new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				if (ListData.size() > 0) {
					ListData.clear();
				}

				for (DataSnapshot ds: snapshot.getChildren()) {
					User user = ds.getValue(User.class);
					if (user != null) {
						ListData.add(user.FirstName);
					}
				}

				Adapter.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		};

		this.DB.addValueEventListener(listener);
	}
}
