package db.dao.impl;

import db.dao.PersonDAO;
import db.model.Person;
import java.util.ArrayList;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class PersonDAO_Redis implements PersonDAO
{
	@Override
	public void create(Person p)
	{
		Jedis jedis = new Jedis("localhost");
		jedis.lpush("" + p.id, p.fname);
		jedis.lpush("" + p.id, p.lname);
		jedis.lpush("" + p.id,"" + p.age);
		jedis.close();
	}

	@Override
	public ArrayList<Person> read()
	{
		ArrayList<Person> pp = new ArrayList<Person>();
		Jedis jedis = new Jedis("localhost");
		Set<String> jKeys = jedis.keys("*");
		for (String str : jKeys)
		{
			ArrayList<String> tmpList = (ArrayList<String>) jedis.lrange(str, 0, 2);
			Person p = new Person
				(
					Integer.parseInt(str),
					tmpList.get(2),
					tmpList.get(1),
					Integer.parseInt(tmpList.get(0))							
				);
			pp.add(p);
		}
		jedis.close();
		return pp;
	}

	@Override
	public void update(Person p)
	{
		Jedis jedis = new Jedis("localhost");
		jedis.lset("" + p.id, 2L, p.fname);
		jedis.lset("" + p.id, 1L, p.lname);
		jedis.lset("" + p.id, 0L, "" + p.age);
		jedis.close();
	}

	@Override
	public void delete(Person p)
	{
		Jedis jedis = new Jedis("localhost");
		jedis.del("" + p.id);
		jedis.close();
	}
}