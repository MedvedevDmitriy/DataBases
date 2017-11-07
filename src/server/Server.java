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
	ServerSocket					serverSocket	= null;							//	Сокет сервера
	int								port			= 0;							//	Порт сервера
	CommandReader					cReader			= new CommandReader();			//	Класс считывания команд с клавиатуры на стороне сервера
	ClientAccepter					cAccepter		= new ClientAccepter();			//	Слушатель порта
	PersonDAO_Mock					pd				= new PersonDAO_Mock();
	public ArrayList<ClientCore>	clientsList		= null;							//	Список клиентов

	/**
	 * @param port
	 * Конструктор сервера. Вызывается один раз из Main
	 */
	public Server(int port)
	{
		try
		{
			this.port = port;
			serverSocket = new ServerSocket(this.port);
			System.out.println("CREATING NEW SERVER ON PORT " + this.port + "...");
			cReader.start();														//	Запускаем "слушатель" клавиатуры
			startServer();															//	Запускаем сервер

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Метод запуска/перезапуска сервера
	 */
	public void startServer()
	{
		clientsList		= new ArrayList<ClientCore>();								//	Обнуление списка клиентов
		cAccepter.start();															//	Запуск слушателя порта
	}

	/**
	 * Метод останова сервера
	 */
	public void stopServer()
	{
		try
		{
			cAccepter.interrupt();													//	Прерывание потока слушателя порта			
			serverSocket.close();													//	Закрытие сервера
			clientsList		= null;													//	Обнуление списка клиентов
			System.out.println("STOPPING SERVER ON PORT " + port + "...");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Метод завершения сервера
	 */
	public void quitServer()
	{
		try
		{
			cAccepter.interrupt();													//	Прерывание потока слушателя порта
			cReader.interrupt();													//	Прерывание потока слушателя клавиатуры
			serverSocket.close();													//	Закрытие сервера													
			clientsList		= null;													//	Обнуление списка клиентов
			System.out.println("SUCCESSFULLY QUITED...");
		} catch (Throwable e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Поток слушателя порта
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
				while (!Thread.currentThread().isInterrupted())						//	Пока поток не прерван, выполнять
				{
					Socket newSocket = serverSocket.accept();						//	Слушать порт, принимать новый сокет клиента
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
	 * Поток обработки сообщений клавиатуры для сервера
	 *
	 */
	class CommandReader extends Thread
	{
		@Override
		public void run()
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	//	Буфер чтения с клавиатуры
			String command = "";														//	Строка, считанная с клавиатуры
			// TODO Auto-generated method stub
			super.run();
			try
			{
				while (!command.equalsIgnoreCase("QUIT"))								//	Пока админ не набрал "ВЫХОД", выполнять...
				{
					command = br.readLine();											//	Считать строку с клавиатуры
					switch (command)
					{
					case "start":
						if (!cAccepter.getState().equals(Thread.State.RUNNABLE))		//	Если сервер не запущен, запустить
							startServer();
						break;
					case "stop":
						stopServer();													//	Остановить сервер
						break;
					case "quit":
						quitServer();													//	Завершить работу сервера
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
