package db.dao.impl;

import db.dao.PersonDAO;
import db.model.Person;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PersonDAO_Binary implements PersonDAO
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
			FileOutputStream f_out = new FileOutputStream("D:\\data_base\\binary\\data.data");
			ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
			if (pp == null) pp = new ArrayList<Person>();
			obj_out.writeObject (pp);	
			obj_out.close();
			f_out.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Override
	public ArrayList<Person> read()
	{
		ArrayList<Person> pp = new ArrayList<Person>();
		try
		{
			FileInputStream fis = new FileInputStream("D:\\data_base\\binary\\data.data");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();

			if (obj instanceof ArrayList)
			{
				pp = (ArrayList<Person>) obj;
			}
			ois.close();
			fis.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
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