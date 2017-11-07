package server;

import java.util.ArrayList;

public class PersonDAO_Mock
{
	public ArrayList<Person> pp = new ArrayList<Person>();

	public PersonDAO_Mock()
	{
		pp.add( new Person(10, "Vasia", "Pupkin", 23) );
		pp.add( new Person(12, "Kasia", "Lupkin", 21) );
		pp.add( new Person(13, "Masia", "Gupkin", 25) );
		pp.add( new Person(17, "Gasia", "Supkin", 36) );
		pp.add( new Person(21, "Zasia", "Fupkin", 99) );
		pp.add( new Person(25, "Jasia", "Dupkin", 90) );
		pp.add( new Person(30, "Basia", "Wupkin", 88) );
	}


	public void create(Person p)
	{
		pp.add(p);
	}


	public ArrayList<Person> read()
	{
		return pp;
	}


	public void update(Person p)
	{
		for (int i = 0; i < pp.size(); i++)
		{
			if(pp.get(i).id == p.id)
			{
				pp.set(i, p); break;
			}
		}
	}

	public void delete(Person p)
	{
		for (int i = 0; i < pp.size(); i++)
		{
			if(pp.get(i).id == p.id)
			{
				pp.remove(i); break;
			}
		}
	}
}