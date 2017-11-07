package db.dao.impl;

import db.dao.PersonDAO;
import db.model.Person;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PersonDAO_CSV implements PersonDAO
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
		File csvFile = new File("D:\\data_base\\csv\\person.csv");
		try
		{	
			String csvString = "\"_id_\",\"_fname_\",\"_lname_\",\"_age_\"\r\n";
			final int size = pp.size();
			
			for (int i = 0; i < size; i++)
			{
				Person p = pp.get(i);
				csvString += p.id + ",";
				csvString += "\"" + p.fname + "\",";
				csvString += "\"" + p.lname + "\",";
				csvString += p.age + "\r\n";
			}	
			FileWriter writer = new FileWriter(csvFile);
			writer.write(csvString);
			writer.flush();
			writer.close();	
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public ArrayList<Person> read()
	{
		ArrayList<Person> pp = new ArrayList<Person>();
		File csvFile = new File("D:\\data_base\\csv\\person.csv");
		try
		{
			Person p = new Person();
			
			FileInputStream fis = new FileInputStream(csvFile);
			InputStreamReader isw = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isw);
			
			String str = "";
			while (br.ready())
			{
			    str += br.readLine();
			    str += "\r\n";
			}
			br.close();
			
			str = str.substring(34);
			StringTokenizer st = new StringTokenizer(str, ",\"\r\n");
			while(st.hasMoreTokens())
			{
				p.id = Integer.parseInt(st.nextToken());
				p.fname = st.nextToken();
				p.lname = st.nextToken();	
				p.age = Integer.parseInt(st.nextToken());
				pp.add(p);
				p = new Person();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
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