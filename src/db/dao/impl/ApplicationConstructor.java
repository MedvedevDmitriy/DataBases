package db.dao.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

public class ApplicationConstructor extends Constructor
{
	private class ConstructUUID extends AbstractConstruct 
	{
		@Override
		public Object construct(Node node) 
		{
			Object o = constructScalar((ScalarNode) node);
			String[] uuidBits = o.toString().split(" ");
			return new UUID(Long.parseLong(uuidBits[0]), Long.parseLong(uuidBits[1]));
		}
	}

	private class ConstructInetAddress extends AbstractConstruct {

		@Override
		public Object construct(Node node) 
		{
			Object o = constructScalar((ScalarNode) node);
			try 
			{
				return InetAddress.getByName(o.toString());
			} 
			catch (UnknownHostException ex) 
			{
				throw new IllegalArgumentException("Bad IP address format!", ex);
			}
		}
	}

	public ApplicationConstructor() 
	{
		this.yamlConstructors.put(new Tag("!uuid"), new ConstructUUID());
		this.yamlConstructors.put(new Tag("!inet"), new ConstructInetAddress());
	}
}
