package com.example.lab07;

public class User {
	public String ID, FirstName, LastName, Patronymic, Email;

	public User() {
	}

	public User(String id, String firstName, String lastName, String patronymic, String email) {
		this.ID = id;
		this.FirstName = firstName;
		this.LastName = lastName;
		this.Patronymic = patronymic;
		this.Email = email;
	}
}
