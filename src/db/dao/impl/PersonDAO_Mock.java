package db.dao.impl;

import db.dao.PersonDAO;
import db.model.Person;
import java.util.ArrayList;

public class PersonDAO_Mock implements PersonDAO
{
	ArrayList<Person> pp = new ArrayList<Person>();
	
	public PersonDAO_Mock()
	{
		pp.add( new Person(1,  "Maksym",	"Zima", 		30) );
		pp.add( new Person(2,  "Valeriy",	"Nosenko", 		31) );
		pp.add( new Person(3,  "Dmitriy",	"Medvedev", 	27) );
		pp.add( new Person(4,  "Maksym",	"Havrylov", 	39) );
		pp.add( new Person(5,  "Egor",		"Zhukov", 		26) );
		pp.add( new Person(6,  "Alexandr", 	"Bezrukov", 	39) );
		pp.add( new Person(7,  "Alexandr", 	"Makovoz", 		37) );
		pp.add( new Person(8,  "Maksym",	"Kosyak",		24) );
		pp.add( new Person(9,  "Bohdan",	"Makohon",		27) );
		pp.add( new Person(10, "Tatyana",	"Palamarchuk",	26) );
		pp.add( new Person(11, "Alexandr",	"Izyumchenko", 	22) );
		pp.add( new Person(12, "Vladislav", "Honchenko", 	21) );
		pp.add( new Person(13, "Dmitriy", 	"Chmul",		29) );
		pp.add( new Person(14, "Stanislav", "Turanskyi",	36) );
		pp.add( new Person(15, "Pavlo", 	"Mykhailenko", 	21) );
		pp.add( new Person(16, "Alexandr", 	"Necvetov",		21) );
		pp.add( new Person(17, "Alexandr", 	"Anikin", 		31) );
		pp.add( new Person(18, "Oleh", 		"Shcherbyna",	32) );
		pp.add( new Person(19, "Roman", 	"Kozak", 		34) );
		pp.add( new Person(20, "Andriy", 	"Bilopoliy", 	22) );
	}
	
	@Override
	public void create(Person p) 
	{
		pp.add(p);
	}

	@Override
	public ArrayList<Person> read() 
	{
		return pp;
	}

	@Override
	public void update(Person p) 
	{
		for (int i = 0; i < pp.size(); i++)
		{
			if (pp.get(i).id == p.id)
			{
				pp.set(i, p); break;
			}
		}
	}

	@Override
	public void delete(Person p) 
	{
		for (int i = 0; i < pp.size(); i++)
		{		
			if (pp.get(i).id == p.id)
			{
				pp.remove(i); break;
			}
		}
	}
}