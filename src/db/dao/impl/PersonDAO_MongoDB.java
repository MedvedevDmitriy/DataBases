package db.dao.impl;

import db.dao.PersonDAO;
import db.model.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class PersonDAO_MongoDB implements PersonDAO
{
	public PersonDAO_MongoDB()
	{
		Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
		mongoLogger.setLevel(Level.SEVERE);
	}
	
	@Override
	public void create(Person p)
	{
		try
		{
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			MongoDatabase db = mongoClient.getDatabase("hw6");
			MongoCollection<BasicDBObject> persons = db.getCollection("persons", BasicDBObject.class);
			BasicDBObject person = p.toDBObject();
			persons.insertOne(person);
			mongoClient.close();
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
			MongoClient mongo = new MongoClient("localhost", 27017);
			MongoDatabase db = mongo.getDatabase("hw6");
			MongoCollection<BasicDBObject> personsCollection = db.getCollection("persons", BasicDBObject.class);		
			List<BasicDBObject> mas = (List<BasicDBObject>) personsCollection.find().into(new ArrayList<BasicDBObject>());
			
			for (BasicDBObject obj: mas)
			{
				pp.add(Person.fromDBObject(obj));
			}	
			mongo.close();
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
		try
		{
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			MongoDatabase db = mongoClient.getDatabase("hw6");
			MongoCollection<BasicDBObject> persons = db.getCollection("persons", BasicDBObject.class);
			
			persons.updateOne(new Document("id", new Document("$eq", p.id)),
					new Document("$set", new Document("lname", p.lname)));
			
			persons.updateOne(new Document("id", new Document("$eq", p.id)),
					new Document("$set", new Document("fname", p.fname)));
			
			persons.updateOne(new Document("id", new Document("$eq", p.id)),
					new Document("$set", new Document("age", p.age)));
			
			mongoClient.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void delete(Person p)
	{
		try
		{
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			MongoDatabase db = mongoClient.getDatabase("hw6");
			MongoCollection<BasicDBObject> persons = db.getCollection("persons", BasicDBObject.class);		
			persons.deleteOne(new Document("id", new Document("$eq", p.id)));		
			mongoClient.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}