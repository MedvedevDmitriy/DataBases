package db.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@SuppressWarnings("serial")
@Entity
@Table(name="person")
@XStreamAlias("person")
public class Person implements Serializable
{	
	@Id
	@Column(name="id")
	@XStreamAlias("id")
	public int id;
	
	@Column(name="FirstName")
	@XStreamAlias("fname")
	public String fname;
	
	@Column(name="LastName")
	@XStreamAlias("lname")
	public String lname;
	
	@Column(name="Age")
	@XStreamAlias("age")
	public int age;
	
	public Person () {}
	
	public Person (int id, String fname, String lname, int age)
	{
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.age = age;
	}
	
	@Override
	public String toString()
	{
		return "Person [id=" + id + ", fname=" + fname + ", lname=" + lname + ", age=" + age + "]";
	}
	
	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getFName()
	{
		return fname;
	}

	public void setFName(String fname)
	{
		this.fname = fname;
	}

	public String getLName() 
	{
		return lname;
	}

	public void setLName(String lname) 
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
	
	//==============
	// FOR MongoDB
	//==============
	public BasicDBObject toDBObject()
	{
		BasicDBObject document = new BasicDBObject();

		document.put("id", this.id);
		document.put("fname", this.fname);
		document.put("lname", this.lname);
		document.put("age", this.age);

		return document;
	}
	
	public static Person fromDBObject(DBObject document)
	{
		Person p = new Person();

		p.id = (int) Double.parseDouble( document.get("id").toString() );
		p.fname = document.get("fname").toString();
		p.lname = document.get("lname").toString();
		p.age = (int) Double.parseDouble( document.get("age").toString() );

		return p;
	}
	//==============
	// FOR RedisDB
	//==============
//		public String toRedisString()
//		{
//			String result = this.id + "," + this.fname + "," + this.lname + "," + this.age;
//			return result;
//		}
//		public static Person fromRedisString(String record)
//		{
//			String[] fields = record.split(",");
//			
//			Person p = new Person();
//			
//			p.id = Integer.parseInt( fields[0] );
//			p.fname = fields[1];
//			p.lname = fields[2];
//			p.age = Integer.parseInt( fields[3] );
//			
//			return p;
//		}
	
	// for PersonDAO_YAML_LIB2
//	@Override
//    public int hashCode() 
//	{
//        return Objects.hash(id);
//    }
//
//    @Override
//    public boolean equals(Object obj) 
//    {
//        if (this == obj) 
//        {
//            return true;
//        }
//        if (obj == null) 
//        {
//            return false;
//        }
//        if (getClass() != obj.getClass()) 
//        {
//            return false;
//        }
//        final Person other = (Person) obj;
//        return (this.id == other.id);
//        //return (this.id == other.id) && Objects.equals(this.model, other.model);
//    }

}