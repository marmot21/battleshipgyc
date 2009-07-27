package battleship;

import java.util.List;
import java.util.Vector;

public class EventManager
{
	private List<Event> events = new Vector<Event>(); //Vector is thread-safe
	
	//all methods accessing 'events' needs to be synchronised.
	//(unless you want exceptions, etc)
	
	//add new event
	public synchronized void add(Event e)
	{
		events.add(e);
	}
	
	//get certain event
	public synchronized Event get(int i)
	{
		return events.get(i);
	}
	
	//flush 'events' which are only needed when they're triggered
	public synchronized void flush()
	{
		for(int i = 0; i < events.size(); i++)
		{
			if(events.get(i).event.startsWith("mouse") || events.get(i).event.startsWith("key"))
				events.remove(i);
		}
	}
	
	//remove the event (usually called after the event is used)
	public synchronized void consume(int i)
	{
		events.remove(i);
	}
	
	//totally clear 'events'
	public synchronized void clear()
	{
		events.clear();
	}
	
	//return the size
	public synchronized int size()
	{
		return events.size();
	}
	
	//prints all the events that are currently in 'events'
	public synchronized void print()
	{
		for(Event e : events)
			System.out.println(e.event);
	}
}
