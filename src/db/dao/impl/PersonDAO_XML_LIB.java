package db.dao.impl;

import db.dao.PersonDAO;
import db.model.Person;

import java.util.ArrayList;
import java.awt.List;
import java.io.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.extended.EncodedByteArrayConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class PersonDAO_XML_LIB implements PersonDAO
{
	String nameXmlFile = "D:\\data_base\\xml\\person_lib.xml";
	@Override
	public void create(Person p) 
	{
		ArrayList<Person> pp = read();
		pp.add(p);
		write(pp);
	}
	
	private void write(ArrayList<Person> pp)
	{
		
			XStream xStream = new XStream(new DomDriver());
			xStream.alias("persons", List.class);
            xStream.processAnnotations(Person.class);
        try 
    	{
            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + xStream.toXML(pp);
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(nameXmlFile)));
			bufferedWriter.write(xml);
			bufferedWriter.close();
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
			XStream xStream = new XStream(new DomDriver());
			xStream.alias("persons", List.class);
			xStream.alias("person", Person.class);
			xStream.aliasField("id", Person.class, "id");
			xStream.aliasField("fname", Person.class, "fname");
			xStream.aliasField("lname", Person.class, "lname");
			xStream.aliasField("age", Person.class, "age");
			xStream.registerConverter((Converter) new EncodedByteArrayConverter());

			pp = (ArrayList<Person>) xStream.fromXML(new File(nameXmlFile));
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
		ArrayList <Person> pp = read();
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