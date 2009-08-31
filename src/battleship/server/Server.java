package battleship.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import battleship.Event;

/**
 * Server which the other player connects to.
 * Make must add the network listener - under cServer.
 * @author Obi
 *
 */
public interface Server {

	public void sockOpen(); 
	public void sockClose();
	public void messageReceived(Event e);
	public void error(Event e);
	
/**
 * Singleton server to be a server and do server stuff
 * @author Obi
 *
 */
public class cServer extends Thread
{
	private ServerSocket servSock=null;
	private ClientGroup group;
	private static cServer thisClass;
	private boolean running = false;
	/**
	 * List of objects that are using this server
	 */
	private Vector<Server> listener = new Vector<Server>();
	
	/**
	 * Constructor
	 * Uses port 25142 - random one that works at school
	 */
	private cServer()
	{
		try
		{
			servSock=new ServerSocket(25142); //create socket

		} 
		catch (Exception e)
		{	
			//System.out.println("Could not create socket. Exiting.");
			//System.exit(1);
		}
		System.out.println("Socket created. Waiting for clients.");
		running = true;
		group=new ClientGroup();
		group.setName("ClientGroup");
		group.start();
	}
	
	/**
	 * Returns a pointer to the server
	 * @return The server
	 */
	public static cServer getServer()
	{
		if (thisClass == null)
			thisClass = new cServer();
		thisClass.running = true;
		return thisClass;
	}
	
	public void end()
	{
		running = false;
	}
	
	/**
	 * Send events to the object 'obj'
	 * @param obj The object to have messages sent to
	 */
	public void addNetworkListener(Server obj)
	{
		if (!listener.contains(obj))
			listener.add(obj);
	}
	
	public void run()
	{
		if (thisClass.servSock != null)
			for(Server go : thisClass.listener)
				go.sockOpen();
		else
			for(Server go : listener)
				go.error(new Event("socket", "Error"));
		setName("Server");
		while (servSock!=null && running)
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
		try {
			servSock.close();
		} catch (Exception e) {}
		servSock = null;
		group = null;
		thisClass = null;
	}
	
	public void finalize()
	{
		//System.out.println("exiting");
		try
		{
			servSock.close();
		}
		catch (Exception e){};
		servSock=null;
		listener.clear();
	}
}
}