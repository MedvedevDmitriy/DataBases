package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ReadOnlyBufferException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Server
{
	ServerSocket					serverSocket	= null;							//	����� �������
	int								port			= 0;							//	���� �������
	CommandReader					cReader			= new CommandReader();			//	����� ���������� ������ � ���������� �� ������� �������
	ClientAccepter					cAccepter		= new ClientAccepter();			//	��������� �����
	PersonDAO_Mock					pd				= new PersonDAO_Mock();
	public ArrayList<ClientCore>	clientsList		= null;							//	������ ��������

	/**
	 * @param port
	 * ����������� �������. ���������� ���� ��� �� Main
	 */
	public Server(int port)
	{
		try
		{
			this.port = port;
			serverSocket = new ServerSocket(this.port);
			System.out.println("CREATING NEW SERVER ON PORT " + this.port + "...");
			cReader.start();														//	��������� "���������" ����������
			startServer();															//	��������� ������

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * ����� �������/����������� �������
	 */
	public void startServer()
	{
		clientsList		= new ArrayList<ClientCore>();								//	��������� ������ ��������
		cAccepter.start();															//	������ ��������� �����
	}

	/**
	 * ����� �������� �������
	 */
	public void stopServer()
	{
		try
		{
			cAccepter.interrupt();													//	���������� ������ ��������� �����			
			serverSocket.close();													//	�������� �������
			clientsList		= null;													//	��������� ������ ��������
			System.out.println("STOPPING SERVER ON PORT " + port + "...");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ����� ���������� �������
	 */
	public void quitServer()
	{
		try
		{
			cAccepter.interrupt();													//	���������� ������ ��������� �����
			cReader.interrupt();													//	���������� ������ ��������� ����������
			serverSocket.close();													//	�������� �������													
			clientsList		= null;													//	��������� ������ ��������
			System.out.println("SUCCESSFULLY QUITED...");
		} catch (Throwable e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ����� ��������� �����
	 *
	 */
	class ClientAccepter extends Thread
	{
		@Override
		public void run()
		{
			super.run();
			// TODO Auto-generated method stub
			try
			{
				while (!Thread.currentThread().isInterrupted())						//	���� ����� �� �������, ���������
				{
					Socket newSocket = serverSocket.accept();						//	������� ����, ��������� ����� ����� �������
					clientsList.add(new ClientCore(newSocket, pd));
					System.out.println("ACCEPTING NEW CLIENT...");
				}
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(this.getState());
			}
		}
	}

	/**
	 * ����� ��������� ��������� ���������� ��� �������
	 *
	 */
	class CommandReader extends Thread
	{
		@Override
		public void run()
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	//	����� ������ � ����������
			String command = "";														//	������, ��������� � ����������
			// TODO Auto-generated method stub
			super.run();
			try
			{
				while (!command.equalsIgnoreCase("QUIT"))								//	���� ����� �� ������ "�����", ���������...
				{
					command = br.readLine();											//	������� ������ � ����������
					switch (command)
					{
					case "start":
						if (!cAccepter.getState().equals(Thread.State.RUNNABLE))		//	���� ������ �� �������, ���������
							startServer();
						break;
					case "stop":
						stopServer();													//	���������� ������
						break;
					case "quit":
						quitServer();													//	��������� ������ �������
						break;
					}
				}
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
