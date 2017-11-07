package db.dao.impl;

import db.dao.PersonDAO;
import db.model.Person;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

public class PersonDAO_Hibernate implements PersonDAO
{
	@Override
	public void create(Person p)
	{
		Session session = null;

		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(p);
			session.getTransaction().commit();
		}
		catch(Exception ex)
		{
			System.err.println(ex.getMessage());
		}
		finally
		{
			if (session != null && session.isOpen())
				session.close();
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<Person> read()
	{
		ArrayList<Person> pp = new ArrayList<Person>();
		List<Person> list = null;
		Session session = null;

		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			list = session.createCriteria(Person.class).list();
			for (Person person : list)
				pp.add(person);
		}
		catch(Exception ex)
		{
			System.err.println(ex.getMessage());
		}
		finally
		{
			if (session != null && session.isOpen())
				session.close();
		}
		
		return pp;
	}

	@Override
	public void update(Person p)
	{
		Session session = null;

		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.update(p);
			session.getTransaction().commit();
		}
		catch(Exception ex)
		{
			System.err.println(ex.getMessage());
		}
		finally
		{
			if (session != null && session.isOpen())
				session.close();
		}
	}

	@Override
	public void delete(Person p)
	{
		Session session = null;

		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(getPerson(p.id));
			session.getTransaction().commit();
		}
		catch(Exception ex)
		{
			System.err.println(ex.getMessage());
		}
		finally
		{
			if (session != null && session.isOpen())
				session.close();
		}
	}

	private Person getPerson(int id)
	{
		Session session = null;
		Person p = null;

		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			p = (Person) session.load(Person.class, id);
		}
		catch(Exception ex)
		{
			System.err.println(ex.getMessage());
		}
		finally
		{
			if (session != null && session.isOpen())
				session.close();
		}
		
		return p;
	}
}