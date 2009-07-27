package battleship;

public class Event
{
	public String event; //the actual event name
	public Object param; //the object passed as parameters
	
	public Event(String s)
	{
		event = s;
	}
	
	public Event(String s, Object o)
	{
		event = s;
		param = o;
	}
}
