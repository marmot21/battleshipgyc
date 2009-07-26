package battleship;

import java.util.ArrayList;
import java.util.List;

public class Event
{
	public String event;
	public List<Object> params = new ArrayList<Object>();
	
	public Event(String s)
	{
		event = s;
	}
	
	public Event(String s, List<Object> o)
	{
		event = s;
		params = o;
	}
}
