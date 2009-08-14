package ChatServer;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class ClientGroup extends Thread 
{
	Vector chatGroup;
	ClientGroup() 
	{
		chatGroup = new Vector();	//A dynamic array to handle the chat clients
	}
	public void addClient(Socket sock) 
	{
		ClientThread tempThread;
		tempThread = new ClientThread(sock, this);
		chatGroup.addElement(tempThread);
		tempThread.start();
	}
	
	public void sendMessage(String msg, String type) 
	{										// send a message "msg", of type "type", to all Clients
		for(int x=0; x<chatGroup.size(); x++)
			((ClientThread)chatGroup.elementAt(x)).message(type+"||"+msg);
											// The format for messages is "type||message"
	}
	 
	public void sendMessage(String msg, String target, String type) 
	{										// Send a message "msg", of type "type",
											// to the Client with alias "target"
		int x;
		ClientThread tempThread;
		
		for(x=0; x<chatGroup.size(); x++) 
		{
			tempThread=(ClientThread)chatGroup.elementAt(x);
			if( tempThread.getAlias().equals(target) )
				tempThread.message(type+"||"+msg);
		}
	}
	
	public boolean handleInput(String str, ClientThread client) 
	{											// Handle any input received from a Client
												//    - called by ClientThread directly
		StringTokenizer st;
										
		if( str.startsWith("bye") )	// command to disconnect = "bye"
		{
			sendMessage(client.getAlias()+" has left the room.", "logout");
			client=null;
			cleanHouse();
			return false;
		}
		st = new StringTokenizer( str, "||");
		if(st != null ) 
		{
			String cmd, val;
			cmd = st.nextToken();
			val = st.nextToken();
			if(cmd.equals("login"))		// "login" = a new person is logging in. 
			{									// Set the alias, send a welcome message, and
												// send everyone an updated list of Client names 		
				client.setAlias( val );
				sendMessage(client.getAlias()+"||"+client.getAlias()+" has entered the room.", cmd);
				sendMessage(calcList(), "list");
				return true;
			}
			if(cmd.equals("logout")) 	// "logout" = one of the clients is disconnecting.
			{									// Inform everyone, and stop the connection.
				sendMessage(client.getAlias()+" has left the room.", cmd);
				client=null;
				cleanHouse();
				return false;
			}
			if(cmd.equals("say")) 		// send a message to the whole 'room'
			{
				sendMessage(client.getAlias()+" says: "+ val, cmd);
				return true;
			}
			if(cmd.equals("whisper")) 	// say something to a specific person
			{
				sendMessage(client.getAlias()+" whispers to you: "+val,st.nextToken(),cmd);
				return true;
			}
			if(cmd.equals("murmur")) 	// say something to more than one person, 
			{									// but not everyone
				sendMessage(client.getAlias()+" murmurs: "+val,st.nextToken(),cmd);
				return true;
			}
		}
		return true;
	}
									
	public String calcList() 		// return a list of all currently connected users
	{
		int x;
		StringBuffer buf = new StringBuffer();
		String temp;
		for(x=0; x<chatGroup.size(); x++) 
		{
			temp = ((ClientThread) (chatGroup.elementAt(x))).getAlias();
			if(temp != null) buf.append(temp).append('&');
		}
		if (buf.length() >0 ) buf.setLength(buf.length()-1);
		return buf.toString();
	}
								
	public void cleanHouse() 		// Search the vector for disconnected Threads
	{										// and then remove them from the list
		int x;
		ClientThread tempThread;
		
		for (x=0; x<chatGroup.size(); x++) 
		{
			tempThread = (ClientThread)chatGroup.elementAt(x);
			if( tempThread==null ||  ! tempThread.isAlive() )
			{
				chatGroup.removeElement( tempThread );
			}
		}
	}
	
	public void run() 
	{
		while( true ) 
		{
			try
			{ 
				sleep(100); 
			}  catch (InterruptedException e){};
			cleanHouse();
		}
	}
	
	// Class ClientThread is created as an inner class to facilitate compilation. Otherwise we have 
	// the difficult situation of both ClientGroup and ClientThread needing the other class to be 
	// compiled before they can themselves be compiled. 
	class ClientThread extends Thread 	
	{
		ClientGroup parent;
		Socket theSock;
		BufferedReader bReader;
		PrintWriter pWriter;
		String alias;
		boolean inRoom;
		
		ClientThread(Socket s, ClientGroup p) 		// Constructor (called by parent)
		{ 
			theSock = s;
			parent = p;
		}
		
		public void run() 								// try to create new data streams
		{
			try 
			{
				bReader = new BufferedReader(new InputStreamReader(theSock.getInputStream()) );
				pWriter = new PrintWriter(new BufferedWriter(
									new OutputStreamWriter(theSock.getOutputStream())),true);
			}  catch (Exception e){};
			inRoom = true;
			while (theSock !=null && inRoom) 
			{
				String input = null;
				try 
				{
					input = bReader.readLine().trim();
					if(input != null) inRoom = parent.handleInput(input, this);
				}  catch (Exception e){};
			}
			try 
			{
				pWriter.close();
				bReader.close();
				theSock.close();
			}  catch(Exception e){};
			theSock = null;
		}
		
		public boolean message(String str) 
		{
			try 
			{
				pWriter.println(str);
			}  catch (Exception e) 
			{
				return false;
			}
			return true;
		}
		
		public void setAlias(String str) 
		{
			alias = str;
		}
		
		public String getAlias() 
		{
			return alias;
		}
	}
}
