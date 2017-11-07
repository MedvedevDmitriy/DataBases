package db.dao.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import db.dao.PersonDAO;
import db.model.Person;

public class PersonDAO_NetSocket implements PersonDAO
{
	Socket				socket	= null;
	DataInputStream		input	= null;
	DataOutputStream	output	= null;
	ReadThread			rth		= null;

	public PersonDAO_NetSocket()
	{
		connect();
	}

	private void connect()
	{
		try
		{
			socket = new Socket(InetAddress.getByName("127.0.0.1"), 2222);
			if (socket.isConnected())
			{
				input = new DataInputStream(socket.getInputStream());
				output = new DataOutputStream(socket.getOutputStream());
				rth = new ReadThread();				
				rth.start();
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void create(Person p)
	{
		writeJSON("create", p);
	}

	@Override
	public ArrayList<Person> read()
	{
		writeJSON("read", null);
		return rth.pp;
	}

	@Override
	public void update(Person p)
	{
		writeJSON("update", p);
	}

	@Override
	public void delete(Person p)
	{
		writeJSON("delete", p);
	}

	class ReadThread extends Thread
	{		
		ArrayList<Person> pp = new ArrayList<Person>();
		
		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			super.run();

			try
			{
				writeJSON("read", null);
				while (!socket.isClosed())
				{
					if (socket.getInputStream().available() > 0)
					{
						String str = input.readUTF();
						readJSON(str);
					}
				}
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void readJSON(String str)
		{
			pp.clear();
			int cnt = 0;
			StringTokenizer strTk = new StringTokenizer(str, "{[:, \"]}");
			String[] strParsed = new String[strTk.countTokens()];
			while (strTk.hasMoreTokens())
			{
				strParsed[cnt++] = strTk.nextToken();
			}
			Person p = new Person();
			for (int i = 0; i < strParsed.length; i++)
			{
				if (strParsed[i].equals("id")) p.id = Integer.parseInt(strParsed[i + 1]);
				else if (strParsed[i].equals("fname")) p.fname = strParsed[i + 1];
				else if (strParsed[i].equals("lname")) p.lname = strParsed[i + 1];
				else if (strParsed[i].equals("age"))
				{
					p.age = Integer.parseInt(strParsed[i + 1]);
					pp.add(p);
					p = new Person();
				}
			}
		}
	}

	private void writeJSON(String command, Person p)
	{		
		try
		{
			String str = "{\"Command:\"" + command;
			if (p == null)
				str += "\"}";
			else
				str += ",\"id\":" + p.id + ", \"fname\":\""+ p.fname + "\", \"lname\":\""+ p.lname + "\",\"age\": " + p.age + "}";
			output.writeUTF(str);
			output.flush();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
