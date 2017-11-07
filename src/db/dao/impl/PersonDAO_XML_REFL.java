package db.dao.impl;

import db.dao.PersonDAO;
import db.model.Person;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PersonDAO_XML_REFL implements PersonDAO
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
		File xmlFile = new File("D:\\data_base\\xml\\person_refl.xml");
		try
		{
			String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<persons>";
			for (Person p: pp)
			{
				xmlString += "\r\n\t<person>\r\n";
				Field[] ff = p.getClass().getDeclaredFields();
				for (Field f : ff)
				{
					xmlString += "\t\t<" + f.getName() + ">" + f.get(p) + "</" + f.getName() + ">\r\n";
				}
				xmlString += "\t</person>";
			}	
			xmlString += "\r\n</persons>";

			FileWriter writer = new FileWriter(xmlFile);
			writer.write(xmlString);
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
		File xmlFile = new File("D:\\data_base\\xml\\person_refl.xml");
		try
		{
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

			//if (!(arrayStr[1].equals("persons") && arrayStr[arrayStr.length - 1].equals("/persons"))) 
				//throw new IllegalArgumentException();

			for (int i = 0; i < arrayStr.length; i++)
			{	
				if (arrayStr[i].equals("person"))
				{
					Person p = new Person();
					Field[] ff = p.getClass().getDeclaredFields();	
					for (Field f : ff)
					{
						f.setAccessible(true);
					}		
					ff[0].set(p, Integer.parseInt(arrayStr[i+2]));
					ff[1].set(p, arrayStr[i+5]);
					ff[2].set(p, arrayStr[i+8]);
					ff[3].set(p, Integer.parseInt(arrayStr[i+11]));
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