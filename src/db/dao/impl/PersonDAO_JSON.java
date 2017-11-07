package db.dao.impl;

import db.dao.PersonDAO;
import db.model.Person;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PersonDAO_JSON implements PersonDAO
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
		File jsonFile = new File("D:\\data_base\\json\\person.json");
		try
		{	
			String jsonString = "{\"persons\":[";
			final int size = pp.size();
			
			for (int i = 0; i < size; i++)
			{
				Person p = pp.get(i);
				
				jsonString += "{";
				
				jsonString += "\"id\":" + p.id + ",";
				jsonString += "\"fname\":\"" + p.fname + "\",";
				jsonString += "\"lname\":\"" + p.lname + "\",";
				jsonString += "\"age\":" + p.age;
				
				jsonString += "}";
				
				if(i != size - 1)
				{
					jsonString += ",";
				}
			}
				jsonString += "]}";
				
				FileWriter writer = new FileWriter(jsonFile);
				writer.write(jsonString);
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
		File jsonFile = new File("D:\\data_base\\json\\person.json");
		try
		{
			Person p = new Person();
			FileInputStream fis = new FileInputStream(jsonFile);
			InputStreamReader isw = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isw);
			String str = br.readLine();
			br.close();
			StringTokenizer st = new StringTokenizer(str, " \",:{}[]");
			
			while(st.hasMoreTokens())
			{
				if (st.nextToken().equals("id"))
				{
					p.id = Integer.parseInt(st.nextToken());
					if (st.nextToken().equals("fname"))
					{
						p.fname = st.nextToken();
						if (st.nextToken().equals("lname"))
						{
							p.lname = st.nextToken();
							if (st.nextToken().equals("age"))
							{
								p.age = Integer.parseInt(st.nextToken());
								pp.add(p);
								p = new Person();
							}
						}	
					}
				}
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