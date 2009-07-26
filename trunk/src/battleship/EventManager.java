package battleship;

import java.util.List;
import java.util.Vector;

public class EventManager
{
	private List<Event> events = new Vector<Event>(); //Vector is thread-safe
	
	//needs to be synchronized. Unless you want exceptions/deadlocks left right and centre :D
	public synchronized void add(Event e)
	{
		events.add(e);
	}
	
	public synchronized Event get(int i)
	{
		return events.get(i);
	}
	
	public synchronized void flush()
	{
		for(int i = 0; i < events.size(); i++)
		{
			if(events.get(i).event.startsWith("mouse") || events.get(i).event.startsWith("key"))
				events.remove(i);
		}
	}
	
	public synchronized void consume(int i)
	{
		events.remove(i);
	}
	
	public synchronized void clear()
	{
		events.clear();
	}
	
	public synchronized int size()
	{
		return events.size();
	}
	
	public synchronized void print()
	{
		for(Event e : events)
			System.out.println(e.event);
	}
}
