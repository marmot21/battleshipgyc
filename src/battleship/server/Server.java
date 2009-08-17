package battleship.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import battleship.Event;

//public interface Server {
	
//	public void sockOpen(); 
	//public void sockClose();
//	public void messageReceived(Event e);
//}
public class Server extends Thread
{
	ServerSocket servSock=null;
	ClientGroup group;
	static Vector<Server> listener;
	
	/**
	 * Constructor
	 * Uses port 25142 - random one that works at school
	 */
	public Server()
	{
		try
		{
			servSock=new ServerSocket(25142); //create socket
			//for(Server go : listener)
				//go.sockOpen();
		} 
		catch (Exception e)
		{	
			System.out.println("Could not create socket. Exiting.");
			System.exit(1);
		}
		System.out.println("Socket created. Waiting for clients.");
		group=new ClientGroup();
		group.start();
	}
	
	public static void addNetworkListener(Server obj)
	{
		listener.add(obj);
	}
	
	public void run()
	{
		while (servSock!=null)
		{
			Socket tempSock;
			try
			{
				tempSock=servSock.accept(); 	//listen for new connections
				group.addClient(tempSock);
			}
			catch (Exception e)
			{
				System.out.println("New Connection Failure. Exiting.");
				System.exit(1);
			}
		}
	}
	
	public void finalize()
	{
		try
		{
			servSock.close();
		}
		catch (Exception e){};
		servSock=null;
	}
}
