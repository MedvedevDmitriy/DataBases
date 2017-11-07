package db.dao.impl;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.UUID;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

public class ApplicationRepresenter extends Representer
{
	private class RepresentUUID implements Represent 
	{
		@Override
		public Node representData(Object o) 
		{
			UUID uuid = (UUID) o;
			return representScalar(new Tag("!uuid"), uuid.getMostSignificantBits() + " " + uuid.getLeastSignificantBits());
		}
	}

	private class RepresentInetAddress implements Represent 
	{
		@Override
		public Node representData(Object o) 
		{
			InetAddress inetAddr = (InetAddress) o;
			return representScalar(new Tag("!inet"), inetAddr.getHostAddress());
		}
	}

	public ApplicationRepresenter() 
	{
		this.representers.put(UUID.class, new RepresentUUID());

		this.representers.put(Inet4Address.class, new RepresentInetAddress());
		
		this.representers.put(Inet6Address.class, new RepresentInetAddress());
	}
}