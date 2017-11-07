package db.dao.impl;

import db.dao.PersonDAO;
import db.model.Person;
import java.util.ArrayList;

import com.datastax.driver.core.*;

public class PersonDAO_CassandraDB implements PersonDAO
{	
	private Cluster cluster = null;
	private Session session = null;
	
	private void connect()
	{
		cluster = Cluster.builder().withClusterName("Test Cluster").addContactPoint("127.0.0.1").build();
		session = cluster.connect();
		session.execute("USE \"HomeWork\"");	
	}
	
	private void close()
	{
		session.close();
		cluster.close();
	}
	
	@Override
	public void create(Person p)
	{
		connect();
		SimpleStatement statement = new SimpleStatement("INSERT INTO \"Person\" (\"id\", \"fname\", \"lname\", age)"
		+ "VALUES(" + p.id + " ,'" + p.fname + "', '" + p.lname + "', " + p.age + ")");
		session.execute(statement);
		close();
	}

	@Override
	public ArrayList<Person> read()
	{
		ArrayList<Person> pp = new ArrayList<Person>();	
		connect();
		SimpleStatement statement = new SimpleStatement("SELECT * FROM \"Person\"");
		ResultSet results = session.execute(statement);
		for (Row row : results)
		{
			Person p = new Person
				(
					row.getInt("id"),
					row.getString("fname"),
					row.getString("lname"),
					row.getInt("age")
				);
			pp.add(p);
		}
		close();		
		return pp;
	}

	@Override
	public void update(Person p)
	{
		connect();
		SimpleStatement statement = new SimpleStatement("UPDATE \"Person\" SET \"fname\" = '" + p.fname +
		"', \"lname\" = '" + p.lname + "', age = " + p.age + " WHERE \"id\" = " + p.id + ";");
		session.execute(statement);
		close();
	}

	@Override
	public void delete(Person p)
	{
		connect();
		SimpleStatement statement = new SimpleStatement("DELETE FROM \"Person\" WHERE \"id\" = " + p.id + ";");
		session.execute(statement);
		close();
	}
}