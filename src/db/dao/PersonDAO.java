package db.dao;

import java.util.ArrayList;

import db.model.Person;

public interface PersonDAO 
{
	void create(Person p);
	ArrayList<Person> read();
	void update(Person p);
	void delete(Person p);
}