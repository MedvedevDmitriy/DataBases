package db.dao.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.yaml.snakeyaml.Yaml;

import db.model.Person;

public class PersonDAO_YAML_LIB2
{	
	public static void main(String[] args)
	{
//		String yaml = "";
//		try
//		{
//			InetAddress ip = InetAddress.getByName("::1");
//			
//			Person person = new Person(1, "Vasya", "Pupkin", 23);
//
//			Yaml yamlProcessor = new Yaml(new ApplicationConstructor(), new ApplicationRepresenter());
//			
//			yaml += yamlProcessor.dump(person);
//		}
//        catch (UnknownHostException e)
//		{
//			e.printStackTrace();
//		}
// 
//        System.out.println(yaml);
        
		Person primary = new Person(1, "Vasya", "Pupkin", 23);

		Yaml yamlProcessor = new Yaml();
		String yaml = yamlProcessor.dump(primary);

		System.out.println(yaml);
        
 
//        Cyborg cyborgCopy = yamlProcessor.loadAs(yaml, Cyborg.class);
// 
//        Assert.assertFalse(cyborg == cyborgCopy);
//        Assert.assertEquals(cyborg, cyborgCopy);
	}
}