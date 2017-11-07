package db.dao.impl;

import db.dao.PersonDAO;
import db.model.Person;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PersonDAO_YAML_LIB implements PersonDAO
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
		File yamlFile = new File("D:\\HTML\\yaml\\person.yaml");
		try
		{	
			String yamlString = "persons:" + "\r\n\t";
			final int size = pp.size();
			
			for (int i = 0; i < size; i++)
			{
				Person p = pp.get(i);
	
				yamlString += "-id: " + p.id + "\r\n\t";
				yamlString += " fname: " + p.fname + "\r\n\t";
				yamlString += " lname: " + p.lname + "\r\n\t";
				yamlString += " age: " + p.age + "\r\n\t";

			}
				FileWriter writer = new FileWriter(yamlFile);
				writer.write(yamlString);
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
		File yamlFile = new File("D:\\HTML\\yaml\\person.yaml");
		try
		{
			Person p = new Person();
			
			FileInputStream fis = new FileInputStream(yamlFile);
			InputStreamReader isw = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isw);
			String str = "";
			while (br.ready())
			   {
			    str += br.readLine();
			    str += "\r\n\t";
			   }
			br.close();
			
			StringTokenizer st = new StringTokenizer(str, " ,\"-:\r\n\t");
			
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