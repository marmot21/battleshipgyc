package battleship;

public class Event
{
	public String event;
	public Object param;
	
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
