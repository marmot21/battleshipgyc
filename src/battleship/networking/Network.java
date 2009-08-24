package battleship.networking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


//NEEDS MOAR ELEGANCE
public class Network
{
	private static Network mInstance = new Network();
	private boolean client;
	private Socket mSock;
	private BufferedReader mReader;
	private PrintWriter mWriter;
	private String mHostName = "";
	private String mName;
	private int mPort;// = 25142;
	
	public static Network get()
	{
		return mInstance;
	}
	
	public boolean isClient()
	{
		return client;
	}
	
	public void connect(String host, int port)
	{
		mHostName = host;
		mPort = port;
		try
		{
			mSock = new Socket(mHostName, mPort);
			mReader = new BufferedReader(new InputStreamReader(mSock.getInputStream()));
			mWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSock.getOutputStream())), true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void sendData(String data)
	{
		
	}
}
