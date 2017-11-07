package db.dao;

import db.dao.impl.PersonDAO_Binary;
import db.dao.impl.PersonDAO_CSV;
import db.dao.impl.PersonDAO_CSV_LIB;
import db.dao.impl.PersonDAO_CSV_REFL;
import db.dao.impl.PersonDAO_CassandraDB;
import db.dao.impl.PersonDAO_H2;
import db.dao.impl.PersonDAO_Hibernate;
import db.dao.impl.PersonDAO_Hypergraph;
import db.dao.impl.PersonDAO_JSON;
import db.dao.impl.PersonDAO_JSON_LIB;
import db.dao.impl.PersonDAO_JSON_REFL;
import db.dao.impl.PersonDAO_Mock;
import db.dao.impl.PersonDAO_MongoDB;
import db.dao.impl.PersonDAO_MySQL;
import db.dao.impl.PersonDAO_NetSocket;
import db.dao.impl.PersonDAO_Redis;
import db.dao.impl.PersonDAO_VoltDB;
import db.dao.impl.PersonDAO_XML;
import db.dao.impl.PersonDAO_XML_LIB;
import db.dao.impl.PersonDAO_XML_REFL;
import db.dao.impl.PersonDAO_YAML;
import db.dao.impl.PersonDAO_YAML_LIB;
import db.dao.impl.PersonDAO_YAML_REFL;

public class PersonDAO_impl
{
	public static String[] getDBNames()
	{
		String[] data = {"Mock", "H2", "MySQL", "Hibernate", "VoltDB", "Binary",
						 "XML", "XML_LIB", "XML_REFL", "JSON", "JSON_LIB", "JSON_REFL", 
						 "CSV", "CSV_LIB", "CSV_REFL", "YAML", "YAML_LIB", "YAML_REFL", 
						 "MongoDB", "CassandraDB", "Redis", "Hypergraph", "NetSocket"};
		return data;
	}
	
	public static PersonDAO getImpl(String item)
	{
		PersonDAO pd = null;
		switch (item)
		{
		case "H2": 			pd = new PersonDAO_H2(); 		  break;
		case "MySQL": 		pd = new PersonDAO_MySQL();		  break;
		case "Hibernate": 	pd = new PersonDAO_Hibernate();   break;
		case "VoltDB": 		pd = new PersonDAO_VoltDB();	  break;
		case "Binary": 		pd = new PersonDAO_Binary();	  break;
		case "XML": 		pd = new PersonDAO_XML(); 		  break;
		case "XML_LIB": 	pd = new PersonDAO_XML_LIB(); 	  break;
		case "XML_REFL": 	pd = new PersonDAO_XML_REFL(); 	  break;
		case "JSON":	 	pd = new PersonDAO_JSON(); 		  break;
		case "JSON_LIB":	pd = new PersonDAO_JSON_LIB(); 	  break;
		case "JSON_REFL": 	pd = new PersonDAO_JSON_REFL();   break;
		case "CSV": 		pd = new PersonDAO_CSV(); 		  break;
		case "CSV_LIB": 	pd = new PersonDAO_CSV_LIB(); 	  break;
		case "CSV_REFL": 	pd = new PersonDAO_CSV_REFL(); 	  break;
		case "YAML": 		pd = new PersonDAO_YAML(); 		  break;
		case "YAML_LIB": 	pd = new PersonDAO_YAML_LIB(); 	  break;
		case "YAML_REFL":	pd = new PersonDAO_YAML_REFL();	  break;
		case "MongoDB":		pd = new PersonDAO_MongoDB(); 	  break;
		case "CassandraDB": pd = new PersonDAO_CassandraDB(); break;
		case "Redis": 		pd = new PersonDAO_Redis(); 	  break;
		case "Hypergraph": 	pd = new PersonDAO_Hypergraph();  break;
		case "NetSocket": 	pd = new PersonDAO_NetSocket();  break;
		default: 			pd = new PersonDAO_Mock(); 		  break;
		}
		return pd;
	}	
}