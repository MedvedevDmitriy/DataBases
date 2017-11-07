package db.dao.impl;

import db.dao.PersonDAO;
import db.model.Person;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PersonDAO_JSON_REFL implements PersonDAO
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
		File jsonFile = new File("D:\\data_base\\json\\person_refl.json");
		try
		{	
			String jsonString = "{\"persons\":[";
			int counter = pp.size();
			for (Person p: pp)
			{
				jsonString += "{";
				Field[] ff = p.getClass().getDeclaredFields();
				for (Field f : ff)
				{
					if (f.getName().equals("id"))
					{
						jsonString += "\"" + f.getName() + "\":" + f.get(p) + ",";
					}
					else if (f.getName().equals("age"))
					{
						jsonString += "\"" + f.getName() + "\":" + f.get(p);
					}
					else
					{
						jsonString += "\"" + f.getName() + "\":\"" + f.get(p) + "\",";
					}		
				}
				jsonString += "}";
				counter--;
				if (counter != 0)
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
		catch (IOException | IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public ArrayList<Person> read()
	{
		ArrayList<Person> pp = new ArrayList<Person>();
		File jsonFile = new File("D:\\data_base\\json\\person_refl.json");
		try
		{
			FileInputStream fis = new FileInputStream(jsonFile);
			InputStreamReader isw = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isw);
			String str = br.readLine();
			br.close();
			
			StringTokenizer st = new StringTokenizer(str, " \",:{}[]");
			int cnt = 0;
			String[] arrayStr = new String[st.countTokens()];
			while (st.hasMoreTokens())
			{
				arrayStr[cnt++] = st.nextToken();
			}
			for (int i = 0; i < arrayStr.length; i++)
			{	
				if (arrayStr[i].equals("id"))
				{
					Person p = new Person();
					Field[] ff = p.getClass().getDeclaredFields();	
					for (Field f : ff)
					{
						f.setAccessible(true);
					}		
					ff[0].set(p, Integer.parseInt(arrayStr[i+1]));
					ff[1].set(p, arrayStr[i+3]);
					ff[2].set(p, arrayStr[i+5]);
					ff[3].set(p, Integer.parseInt(arrayStr[i+7]));
					pp.add(p);
				}				
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