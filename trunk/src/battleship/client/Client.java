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

	public void error(Event e);
	public void connected();
	public void clientMsg(Event e);
	
public class cClient extends Thread
{
	static Vector<Client> listener = new Vector<Client>();
	static Socket sock;
	static BufferedReader bReader;
	static PrintWriter pWriter;
	static String theHost="localHost";
	String name;
	int thePort=5926;		//The communications port on the server.

	
	static Thread chatThread = null;
	//java.awt.List chatList;


	
	public boolean output(String str) 
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
	
	public static void logon(String host)
	{//Login to server
		theHost=host;
		chatThread = new Thread();
		chatThread.start();
	}
	
	public static void logout()
	{
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
					go.error(new Event("sockError","unable to connect"));
				sock = null;
			}
			try{ Thread.sleep( 100 ); } catch(InterruptedException e){};
		}

		if(chatThread!=null)
		{//connect to the server
			output("login||"+name);
			for(Client go : listener)
				go.connected();
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
						
					}
					else
					if(cmd.equals("login")) 
					{
						//outputArea.append(st.nextToken()+"\n");
					}
					else 
					{
						//outputArea.append( val + "\n" );
					}
				}
				else 
				{
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
