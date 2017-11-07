package db.dao.impl;

import db.dao.PersonDAO;
import db.model.Person;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javenue.csv.*;

public class PersonDAO_CSV_LIB implements PersonDAO
{	
	@Override
	public void create(Person p)
	{
		ArrayList<Person> pp = read();
		pp.add(p);
		write(pp);
	}
	
	private void write(ArrayList<Person> pp)
	{
		File csvFile = new File("D:\\data_base\\csv\\person_lib.csv");
		try
		{ 
			Csv.Writer writer = new Csv.Writer(csvFile).delimiter(',');
			for (int i = 0; i < pp.size(); i++)
			{
				Person p = pp.get(i);
				writer.value(String.valueOf(p.id)).value(p.fname).value(p.lname).value(String.valueOf(p.age)).newLine();
			}
			writer.flush();
			writer.close();
		}
		catch(Exception ex)
		{
			System.err.println(ex.getMessage());
			System.err.println(ex.getStackTrace());
		}
	}
	
	@Override
	public ArrayList<Person> read()
	{
		ArrayList<Person> pp = new ArrayList<Person>();
		File csvFile = new File("D:\\data_base\\csv\\person_lib.csv");		
		try
		{
			Csv.Reader reader = new Csv.Reader(new FileReader(csvFile)).delimiter(',').ignoreComments(true);

			for (int i = 0; true; i++)
			{
				List<String> lines = reader.readLine();

				if (lines == null) break;
				
				Person p = new Person();
				p.id = Integer.parseInt(lines.get(0));
				p.fname = lines.get(1);
				p.lname = lines.get(2);
				p.age = Integer.parseInt(lines.get(3));
				pp.add(p);	
			}
			reader.close();
		}
		catch(Exception ex)
		{
			System.err.println(ex.getMessage());
			System.err.println(ex.getStackTrace());
		}
		return pp;
	}

	@Override
	public void update(Person p)
	{
		ArrayList<Person> pp = read();	
		for (int i = 0; i < pp.size(); i++)
		{
			if (pp.get(i).id == p.id)
			{
				pp.set(i, p); break;
			}
		}
		write(pp);
	}

	@Override
	public void delete(Person p)
	{
		ArrayList<Person> pp = read();
		for (int i = 0; i < pp.size(); i++)
		{		
			if (pp.get(i).id == p.id)
			{
				pp.remove(i); break;
			}
		}
		write(pp);
	}
}