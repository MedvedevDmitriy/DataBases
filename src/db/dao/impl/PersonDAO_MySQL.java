package db.dao.impl;

import db.dao.PersonDAO;
import db.model.Person;
import java.sql.*;
import java.util.ArrayList;

public class PersonDAO_MySQL implements PersonDAO
{
	private Connection connection;

	public void connect()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "root");
		}
		catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}	
	}
	
	public void close()
	{
		try
		{
			connection.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void create(Person p) 
	{
		try
		{
			connect();
			String command = "INSERT INTO MAIN.PERSON (id, fname, lname, age) VALUES (?, ?, ?, ?)";
			PreparedStatement st = connection.prepareStatement(command);
			st.setInt(1, p.id);
			st.setString(2, p.fname);
			st.setString(3, p.lname);
			st.setInt(4, p.age);
			st.execute();
			close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Person> read() 
	{
		ArrayList<Person> pp = new ArrayList<Person>();
		try
		{
			connect();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT * from MAIN.PERSON");
			while(rs.next())
			{			
				pp.add(new Person(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
			}
			close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pp;
	}

	@Override
	public void update(Person p) 
	{
		try
		{
			connect();
			String command = "UPDATE MAIN.PERSON SET fname=?, lname=?, age=? WHERE id=?";
			PreparedStatement st = connection.prepareStatement(command);
			st.setString(1, p.fname);
			st.setString(2, p.lname);
			st.setInt(3, p.age);
			st.setInt(4, p.id);
			st.execute();
			close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Person p) 
	{
		try
		{
			connect();
			String command = "DELETE FROM MAIN.PERSON WHERE id=?";
			PreparedStatement st = connection.prepareStatement(command);
			st.setInt(1, p.id);
			st.execute();
			close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}