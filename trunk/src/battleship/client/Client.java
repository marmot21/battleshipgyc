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
	
public static class cClient extends Thread
{
	private static Vector<Client> listener = new Vector<Client>();
	private static Socket sock;
	private static BufferedReader bReader;
	private static PrintWriter pWriter;
	private static String theHost="localHost";
	private static String mName;
	private static int thePort=25142;		//The communications port on the server.

	
	private static Thread chatThread = null;
	//java.awt.List chatList;


	
	private static boolean output(String str) 
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
	
	public static void addClientListener(Client obj)
	{
		listener.add(obj);
	}
	
	/**
	 * Wrapper for output
	 * @param str The message to send
	 */
	public static void sendMessage(String str)
	{
		output("message||"+str);
	}
	
	public static void login(String host, String name)
	{//Login to server
		theHost=host;
		mName = name;
		chatThread = new Thread();
		chatThread.start();
	}
	
	public static void logout()
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
					go.error(new Event("error","Unable to connect"));
				sock = null;
			}
			try{ Thread.sleep( 100 ); } catch(InterruptedException e){};
		}

		if(chatThread!=null)
		{//connect to the server
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
							go.clientMsg(new Event("client", val+cmd));
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
