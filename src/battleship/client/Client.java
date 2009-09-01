package battleship.client;

//		Application/Applet providing the client half of a client/server chat program.
//			<APPLET CODE="ChatClient.class" WIDTH="700" HEIGHT="250">
//			<PARAM NAME="Host1" VALUE="localhost">
//			<PARAM NAME="Host2" VALUE="127.0.0.1">
//			<PARAM NAME="Host3" VALUE="192.168.0.1">
//			</APPLET>

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import battleship.Event;

public interface Client{

	String cName = null;
	
	public void error(Event e);
	public void connected();
	public void clientMsg(Event e);
	public void shipsRecieve(String str);
	
/**
 * Singleton class to handle client stuff
 * @author Obi
 *
 */
public class cClient extends Thread
{
	//pointer to this class
	private static cClient thisClass;
	
	private Vector<Client> listener = new Vector<Client>();
	private Socket sock;
	private BufferedReader bReader;
	private PrintWriter pWriter;
	private String theHost="localHost";
	private String mName;
	private int thePort=25142;		//The communications port on the server.

	
	private static Thread chatThread = null;
	//java.awt.List chatList;

	/**
	 * Class is a singleton
	 */
	private cClient()
	{
		
	}
	
	/**
	 * Returns the pointer to the class
	 * @return pointer to this class
	 */
	public static cClient getClient()
	{
		if (thisClass == null)
			thisClass = new cClient();
		return thisClass;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		throw new CloneNotSupportedException(); 
	    //prevent any cloning
	}
	
	private boolean output(String str) 
	{
		try 
		{
			pWriter.println(str);
			return true;
		} catch(Exception e) 
		{
			return false;
		}
	}
	
	public void addClientListener(Client obj)
	{
		if (!listener.contains(obj))
			listener.add(obj);
	}
	
	/**
	 * Wrapper for output
	 * @param str The message to send
	 */
	public void sendMessage(String str)
	{
		output("message||"+str);
	}
	
	public void login(String host, String name)
	{//Login to server
		theHost=host;
		mName = name;
		chatThread = new Thread();
		chatThread.start();
	}
	
	public void logout()
	{
		output("logout");
		try 
		{
			bReader.close();
			pWriter.close();
			sock.close();
		} catch (Exception e){};
		sock = null;
		chatThread = null;
	}
	
		//output("say||"+"stuff to be sent");

	public void run() 
	{
		while (sock == null && chatThread != null) 
		{				
			try 
			{
				sock = new Socket(theHost,thePort);
				bReader = new BufferedReader(new InputStreamReader(sock.getInputStream()) );
				pWriter = new PrintWriter(new BufferedWriter(
								new OutputStreamWriter(sock.getOutputStream())),true);
			} catch (Exception e) 
			{
				for(Client go : listener)
					go.error(new Event("clientError","connection"));
				sock = null;
			}
			try{ Thread.sleep( 100 ); } catch(InterruptedException e){};
		}

		if(chatThread!=null)
		{//connect to the server
			System.out.println("message sent");
			output("request||"+mName);
		}
		while (sock != null && bReader != null && chatThread != null) 
		{
			try 
			{
				String str = bReader.readLine();
				if(str != null)
				if(str.indexOf("||") != -1) 
				{
					StringTokenizer st = new StringTokenizer(str,"||");
					String cmd = st.nextToken();
					String val = st.nextToken();

					if(cmd.equals("logout")) 
					{
						for(Client go : listener)
							go.clientMsg(new Event("client", /*val+*/cmd));
					}
					else
					if(cmd.equals("granted")) 
					{
						//outputArea.append(st.nextToken()+"\n");
						for(Client go : listener)
							go.connected();
					}
					else if(cmd.equals("joined"))
					{
						for(Client go : listener)
							go.clientMsg(new Event("client", cmd));
						//outputArea.append( val + "\n" );
					}
					else if(cmd.equals("message"))
					{
						for(Client go : listener)
							go.shipsRecieve(val);
					}
				}
				else 
				{
					//for(Client go : listener)
					//	go.clientMsg(new Event("client", str));
					//outputArea.append(str + "\n");
				}
			} catch (IOException e) 
			{
				chatThread=null;
			}
			try
			{
				Thread.sleep(100);
			}
			catch(InterruptedException e){}
		}
	}


}
}
