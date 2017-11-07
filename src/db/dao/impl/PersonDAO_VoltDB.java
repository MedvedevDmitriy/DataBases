package db.dao.impl;

import db.dao.PersonDAO;
import db.model.Person;
import java.sql.*;
import java.util.ArrayList;

public class PersonDAO_VoltDB implements PersonDAO
{
	ArrayList<Person> pp = new ArrayList<Person>();
	
	@Override
	public void create(Person p) 
	{
		try
		{
			Class.forName("org.voltdb.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:voltdb://192.168.100.150:21212");
			String command = "INSERT INTO PERSON (id, fname, lname, age) VALUES (?, ?, ?, ?)";
			PreparedStatement st = con.prepareStatement(command);
			st.setInt(1, p.id);
			st.setString(2, p.fname);
			st.setString(3, p.lname);
			st.setInt(4, p.age);
			st.execute();
		} 
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Person> read() 
	{
		try
		{
			Class.forName("org.voltdb.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:voltdb://192.168.100.150:21212");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * from PERSON");

			while(rs.next())
			{			
				Person p = ( new Person ( rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4) ) );
				pp.add(p);
			}	
		} 
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
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
			Class.forName("org.voltdb.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:voltdb://192.168.100.150:21212");
			String command = "UPDATE PERSON SET fname=?, lname=?, age=? WHERE id=?";
			PreparedStatement st = con.prepareStatement(command);
			st.setString(1, p.fname);
			st.setString(2, p.lname);
			st.setInt(3, p.age);
			st.setInt(4, p.id);
			st.execute();	
		} 
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
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
			Class.forName("org.voltdb.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:voltdb://192.168.100.150:21212");
			String command = "DELETE FROM PERSON WHERE id=?";
			PreparedStatement st = con.prepareStatement(command);
			st.setInt(1, p.id);
			st.execute();
		} 
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}