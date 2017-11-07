package db.dao.impl;

import db.dao.PersonDAO;
import db.model.Person;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PersonDAO_XML implements PersonDAO
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
		File xmlFile = new File("D:\\data_base\\xml\\person.xml");
		try
		{	
			String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<persons>";
			final int size = pp.size();
			
			for (int i = 0; i < size; i++)
			{
				Person p = pp.get(i);
				
				xmlString += "\r\n\t<person>\r\n\t\t";
				
				xmlString += "<id>" + p.id + "</id>\r\n\t\t";
				xmlString += "<fname>" + p.fname + "</fname>\r\n\t\t";
				xmlString += "<lname>" + p.lname + "</lname>\r\n\t\t";
				xmlString += "<age>" + p.age + "</age>\r\n\t";
				
				xmlString += "</person>";
			}
				xmlString += "\r\n</persons>";
				
				FileWriter writer = new FileWriter(xmlFile);
				writer.write(xmlString);
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
		File xmlFile = new File("D:\\data_base\\xml\\person.xml");
		try
		{
			Person p = new Person();
			FileInputStream fis = new FileInputStream(xmlFile);
			InputStreamReader isw = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isw);
			String str = "";
			while (br.ready())
			{
				str += br.readLine();
				str += "\r\n\t";
			}
			br.close();
			
			str = str.substring(38);
			StringTokenizer st = new StringTokenizer(str, "\r\n\t<>");
			
			int cnt = 0;
			String[] arrayStr = new String[st.countTokens()];
			
			while (st.hasMoreTokens())
			{
				arrayStr[cnt++] = st.nextToken();
			}

			if (!(arrayStr[0].equals("persons")) && !(arrayStr[arrayStr.length - 1].equals("/persons"))) 
				throw new IllegalArgumentException();

			for (int i = 0; i < arrayStr.length; i++)
			{
				if (arrayStr[i].equals("person"))
				{
					p = new Person();
				}
				
				if (arrayStr[i].equals("id") && arrayStr[i + 2].equals("/id"))
				{
					p.id = Integer.parseInt(arrayStr[i + 1]);
				}
				else if (arrayStr[i].equals("fname") && arrayStr[i + 2].equals("/fname"))
				{
					p.fname = arrayStr[i + 1];
				}
				else if (arrayStr[i].equals("lname") && arrayStr[i + 2].equals("/lname"))
				{
					p.lname = arrayStr[i + 1];
				}
				else if (arrayStr[i].equals("age") && arrayStr[i + 2].equals("/age"))
				{
					p.age = Integer.parseInt(arrayStr[i + 1]);
				}
				else if (arrayStr[i].equals("/person"))
				{      
					pp.add(p);
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