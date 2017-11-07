package db.dao.impl;

import db.dao.PersonDAO;
import db.model.Person;
import java.io.*;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PersonDAO_JSON_LIB implements PersonDAO
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
		try
		{
			Gson gs = new Gson();
			String str = gs.toJson(pp);
			FileWriter writer = new FileWriter("D:\\data_base\\json\\person_lib.json");		
			writer.write(str);
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
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("D:\\data_base\\json\\person_lib.json"));
			String line = br.readLine();
			Gson gs = new Gson(); 
			pp = (ArrayList<Person>) gs.fromJson(line, new TypeToken<ArrayList<Person>>(){}.getType());
			br.close();	
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