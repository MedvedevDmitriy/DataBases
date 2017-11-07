package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientCore
{
	Socket 					client			= null;									//	Сокет управления клиентом, подключенного к данному серверу
	DataOutputStream		output			= null;									//	Выходной поток данных "к клиенту"
	DataInputStream			input			= null;									//	Входной поток данных "от клиента"
	PersonDAO_Mock			pd				= null;

	/**
	 * @param client - полученный сокет от accept()
	 * @param pd 
	 */
	public ClientCore(Socket client, PersonDAO_Mock pd)
	{
		this.client = client;
		this.pd = pd;
		try
		{
			output	=	new DataOutputStream(this.client.getOutputStream());		// Создаем выходной поток данных - "к клиенту"
			input	=	new DataInputStream(this.client.getInputStream());			//	Создаем входной поток данных - "от клиента"
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		new ReadThread().start();													//	Запуск потока чтения данных из сети
	}

	/**
	 * Закрытие соединения с клиентом
	 */
	public void close()
	{
		try
		{
			client.shutdownInput();													//	Останов выходного потока
			client.shutdownOutput();												//	Останов входноо потока
			client.close();															//	Закрытие сокета соединения
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Поток чтения данных из сети
	 *
	 */
	class ReadThread extends Thread
	{
		@Override
		public void run()
		{

			try
			{
				while (!client.isInputShutdown())										//	Пока входной поток не закрыт...
				{
					if (client.getInputStream().available() > 0)
					{
						String line = input.readUTF();
						readJSON(line);
						output.writeUTF(writeJSON());
						output.flush();
					}
				}
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		
		private void readJSON(String line)
		{
			// TODO Auto-generated method stub
			int cnt = 0;
			StringTokenizer strTk = new StringTokenizer(line, "{[:, \"]}");
			String[] strParsed = new String[strTk.countTokens()];
			while (strTk.hasMoreTokens())
			{
				strParsed[cnt++] = strTk.nextToken();
			}
			if (strParsed[1].equalsIgnoreCase("read"))
			{
				return;
			}
			else
			{
				Person p = new Person();
				for (int i = 2; i < strParsed.length; i++)
				{
					if (strParsed[i].equals("id")) p.id = Integer.parseInt(strParsed[i + 1]);
					else if (strParsed[i].equals("fname")) p.fname = strParsed[i + 1];
					else if (strParsed[i].equals("lname")) p.lname = strParsed[i + 1];
					else if (strParsed[i].equals("age"))
					{
						p.age = Integer.parseInt(strParsed[i + 1]);
					}
				}
				switch(strParsed[1])
				{
				case "create":
					pd.create(p);			break;
				case "update":
					pd.update(p);			break;
				case "delete":
					pd.delete(p);			break;
				}
			}
		}

		private String writeJSON()
		{
			String str = "[";
			for (int i = 0; i < pd.pp.size(); i++)
			{
				Person p = pd.pp.get(i);
				str += "{\"id\":" + p.id + ", \"fname\":\""+ p.fname + "\", \"lname\":\""+ p.lname + "\",\"age\": " + p.age + "}";
				if (i < pd.pp.size() - 1) str += ",";
			}
			str +="]";
			return str;
		}
	}

}