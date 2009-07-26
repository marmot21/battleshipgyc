package battleship;

import java.util.ArrayList;
import java.util.List;

public class EventManager
{
	public List<Event> events = new ArrayList<Event>();
	
	public synchronized void trigger(Event e)
	{
		events.add(e);
	}
	
	public synchronized Event getEvent(int i)
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
