package db.dao.impl;

import db.dao.PersonDAO;
import db.model.Person;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PersonDAO_CSV_REFL implements PersonDAO
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
		File csvFile = new File("D:\\data_base\\csv\\person_refl.csv");
		try
		{	
			String csvString = "\"_id_\",\"_fname_\",\"_lname_\",\"_age_\"\r\n";
			for (Person p: pp)
			{
				Field[] ff = p.getClass().getDeclaredFields();			
				for (Field f : ff)
				{
					if (f.getName().equals("id"))
					{
						csvString += f.get(p) + ",";
					}
					else if (f.getName().equals("age"))
					{
						csvString += f.get(p);
					}
					else
					{
						csvString += "\"" + f.get(p) + "\",";
					}	
				}
				csvString += "\r\n";	
			}
			FileWriter writer = new FileWriter(csvFile);
			writer.write(csvString);
			writer.flush();
			writer.close();	
		}
		catch (IOException | IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Person> read()
	{
		ArrayList<Person> pp = new ArrayList<Person>();
		File csvFile = new File("D:\\data_base\\csv\\person_refl.csv");
		try
		{
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
				Person p = new Person();
				Field[] ff = p.getClass().getDeclaredFields();
				for (Field f : ff)
				{
					f.setAccessible(true);
				}
				ff[0].set(p, Integer.parseInt(st.nextToken()));
				ff[1].set(p, st.nextToken());
				ff[2].set(p, st.nextToken());
				ff[3].set(p, Integer.parseInt(st.nextToken()));
				pp.add(p);
			}
		}
		catch (IOException | IllegalArgumentException | IllegalAccessException e)
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