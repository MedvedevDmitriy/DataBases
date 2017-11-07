package db.dao.impl;

import db.dao.PersonDAO;
import db.model.Person;
import java.util.ArrayList;
import java.util.List;

import org.hypergraphdb.HGHandle;
import org.hypergraphdb.HGQuery.hg;
import org.hypergraphdb.HyperGraph;

public class PersonDAO_Hypergraph implements PersonDAO
{
	String databaseLocation = "D:\\data_base\\hypergraphdb-1.3\\hypergraph_db";

	@Override
	public void create(Person p)
	{
		HyperGraph graph = new HyperGraph(databaseLocation);
		graph.add(p);
		graph.close();
	}

	@Override
	public ArrayList<Person> read()
	{
		ArrayList<Person> pp = new ArrayList<Person>();
		try
		{
			HyperGraph graph = new HyperGraph(databaseLocation);
			List<HGHandle> persons = hg.findAll(graph, hg.typePlus(Person.class));
			for (HGHandle p : persons)
			{
				pp.add((Person)graph.get(p));       
			}
			graph.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return pp;
	}

	@Override
	public void update(Person p)
	{
		try
		{
			HyperGraph graph = new HyperGraph(databaseLocation);
			List<HGHandle> persons = hg.findAll(graph, hg.typePlus(Person.class));
			for (HGHandle person : persons)
			{
				Person tmp = (Person)graph.get(person);  
				if(tmp.id == p.id)
				{
					graph.replace(person, p);
					break;
				}
			}
			graph.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void delete(Person p)
	{
		try
		{
			HyperGraph graph = new HyperGraph(databaseLocation);
			List<HGHandle> persons = hg.findAll(graph, hg.typePlus(Person.class));
			for (HGHandle person : persons)
			{
				Person tmp = (Person)graph.get(person);   	
				if(tmp.id == p.id)
				{
					graph.remove(person);
					break;
				}
			}
			graph.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
}