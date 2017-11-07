package server;


import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Person")
@XmlAccessorType (XmlAccessType.FIELD)
public class Person
{
	@XmlElement
	public int id;
	@XmlElement
	public String fname;
	@XmlElement
	public String lname;
	@XmlElement
	public int age;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getFname()
	{
		return fname;
	}

	public void setFname(String fname)
	{
		this.fname = fname;
	}

	public String getLname()
	{
		return lname;
	}

	public void setLname(String lname)
	{
		this.lname = lname;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public Person()
	{
		//
	}
	
	public Person(int id, String fname, String lname, int age)
	{
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.age = age;
	}

	@Override
	public String toString()
	{
		return "Person [id = " + id + ", fname = " + fname + ", lname = " + lname + ", age = " + age + "]";
	}	
}