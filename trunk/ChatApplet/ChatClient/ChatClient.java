package ChatClient;

//		Application/Applet providing the client half of a client/server chat program.
//			<APPLET CODE="ChatClient.class" WIDTH="700" HEIGHT="250">
//			<PARAM NAME="Host1" VALUE="localhost">
//			<PARAM NAME="Host2" VALUE="127.0.0.1">
//			<PARAM NAME="Host3" VALUE="192.168.0.1">
//			</APPLET>
import java.applet.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

		//ChatClient attempts to establish a connection with ChatServer, and then exchanges
		//messages with ChatServer.
public class ChatClient extends Applet implements Runnable,ItemListener,ActionListener
{
	Socket sock;
	BufferedReader bReader;
	PrintWriter pWriter;
	String theHost="localHost",name;
	int thePort=5926;		//The communications port on the server.
	boolean isApplet=true;
	
	Thread chatThread = null;
	
	TextField inputField;
	TextArea outputArea;
	Button commandB,quitB;
	java.awt.List chatList;
	String[] sayTo;
	Panel lower,upper;
	
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
	public void init() 
	{
		setLayout( new BorderLayout() );
		
		lower = new Panel();
		upper = new Panel();
		
		lower.setLayout( new FlowLayout() );
		
		add("North",upper);
		add("South",lower);
		
		inputField = new TextField(58);
		inputField.addActionListener(this);
		
		outputArea = new TextArea(10, 60);
		outputArea.setText("'Say' to everyone\n'Murmur' to a few\n'Whisper' to one\n");
		outputArea.setEditable(false);
		outputArea.setBackground(Color.cyan);
		
		chatList = new java.awt.List(10,true);			//Stores and displays the participants
		chatList.addItemListener(this);
		chatList.add("All Participants",0);				//The default entry
		
		commandB = new Button("login");
		commandB.addActionListener(this);
		
		quitB = new Button("logout");
		quitB.addActionListener(this);
		quitB.setEnabled(false);							//Disabled, until login.

		lower.add(inputField );
		upper.add(outputArea);
		lower.add(commandB);
		lower.add(quitB);
		upper.add(chatList);

		chatList.select(0);
		inputField.requestFocus();
	}
	
										//The event listener for the List of connected users.
	public void itemStateChanged(ItemEvent ievt)
	{
		sayTo=chatList.getSelectedItems();	//Array of chatList items currently selected 
		int i=sayTo.length;						//Number of items selected
		if(i==0)chatList.select(0);			//Must talk to someone!
		if(chatList.isIndexSelected(0)) commandB.setLabel("say");
		else
			if(i==1)commandB.setLabel("whisper");
			else commandB.setLabel("murmur");
		lower.doLayout();
		inputField.requestFocus();
	}
	
										//The event listener for the Buttons and the input TextField.
	public void actionPerformed(ActionEvent evt) 
	{
		if( evt.getSource()==inputField || evt.getSource()==commandB)
										//<Enter> and Command button generate same response.
		{
			if(inputField.getText().trim().length()>0)
			{
				if(commandB.getLabel()=="login")
				{
					name = inputField.getText().trim();		//The user's chatList 'name'.
					inputField.setText("");
					outputArea.append("Logging in...\n");
					commandB.setLabel("say");
					quitB.setEnabled(true);
					chatThread = new Thread(this);
					chatThread.start();
				}
				else if(commandB.getLabel()=="say") 
				{
					output("say||"+inputField.getText().trim());
					inputField.selectAll();
				}
				else
				if(commandB.getLabel()=="whisper") 
				{
			  		outputArea.append("You whisper to "+chatList.getSelectedItem().trim()+": "
					  						+inputField.getText()+"\n");
					output("whisper||"+inputField.getText()+"||"+chatList.getSelectedItem().trim());
					inputField.selectAll();
				}
				else
				if(commandB.getLabel()=="murmur") 
				{
			  		outputArea.append("You murmur to ");
					for(int i=0;i<sayTo.length;i++) outputArea.append(sayTo[i]+" ");
					outputArea.append(": "+inputField.getText()+"\n");
					for(int i=0;i<sayTo.length;i++) output("murmur||"+inputField.getText()+"||"+sayTo[i] );
					inputField.selectAll();
				}
			}
		}
		else if(evt.getSource()==quitB) 
		{
			outputArea.append("Logging out...\n");
			output("logout||"+name);
			try 
			{
				bReader.close();
				pWriter.close();
				sock.close();
			} catch (Exception e){};
			sock = null;
			outputArea.append("Logged out from server.\n");
			chatThread = null;
			quitB.setEnabled(false);
			commandB.setLabel("login");
		}
		inputField.requestFocus();
	}

	public void run() 
	{
		int count=0;
		while (sock == null && chatThread != null) 
		{
			if(isApplet)
			{
				theHost=getCodeBase().toString();
				switch(count)			//Cycle through the applet host parameters
				{
					case 0:theHost=getParameter("Host1");
								break;
					case 1:theHost=getParameter("Host2");
								break;
					case 2:theHost=getParameter("Host3");
								break;
				}
				count=(count+1)%3;
			}	
			try 
			{
				sock = new Socket(theHost,thePort);
				bReader = new BufferedReader(new InputStreamReader(sock.getInputStream()) );
				pWriter = new PrintWriter(new BufferedWriter(
								new OutputStreamWriter(sock.getOutputStream())),true);
			} catch (Exception e) 
			{
				outputArea.append("Unable to contact "+theHost+" - Retrying "+count+"\n");
				sock = null;
			}
			try{ Thread.sleep( 100 ); } catch(InterruptedException e){};
		}

		if(chatThread!=null)
		{
			output("login||"+name);
			outputArea.append("Logged in to server successfully.\n");
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
					if(cmd.equals("list")) 
					{
						chatList.removeAll();
						chatList.add("All Participants");
						StringTokenizer st2 = new StringTokenizer(val,"&");
						while(st2.hasMoreTokens()) chatList.add(st2.nextToken());
						chatList.select(0);
					}
					else
					if(cmd.equals("logout")) 
					{
						int x;
						for(x=0;x< chatList.getItemCount();x++)
						if( val.startsWith( chatList.getItem(x) ) ) chatList.remove(x);
						outputArea.append(val+"\n");
						validate();
					}
					else
					if(cmd.equals("login")) 
					{
						outputArea.append(st.nextToken()+"\n");
					}
					else 
					{
						outputArea.append( val + "\n" );
					}
				}
				else 
				{
					outputArea.append(str + "\n");
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
	public void stop()
	{
		outputArea.append("Logging out...\n");
		output("logout||"+name);
	}

	//The application part!
	public static void main(String[] args) 
	{
		ChatClient applet = new ChatClient();
		Frame frame = new Frame("Chat");
		frame.addWindowListener(new WClose()); 
		frame.add(applet);
		frame.setSize(700, 250);
		applet.init();
		applet.start();
		frame.setVisible(true);
		if(args.length>0)applet.theHost=args[0];
		applet.isApplet=false;
	}
}

