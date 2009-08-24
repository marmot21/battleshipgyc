package battleship;

import java.util.Vector;

public class Events
{
	private static Events mInstance = new Events();
	private Vector<Event> mEvents = new Vector<Event>();
	
	
	public static Events get()
	{
		return mInstance;
	}
	
	public void add(Event event)
	{
		mEvents.add(event);
	}
	
	public void remove(int event)
	{
		mEvents.remove(event);
	}
	
	public Event get(int event)
	{
		return mEvents.get(event);
	}
	
	public void flush()
	{
		
	}
	
	public int size()
	{
		return mEvents.size();
	}
	
	public void print()
	{
		for(int i = 0; i < mEvents.size(); i++)
			System.out.println(mEvents.get(i).mEvent);
		System.out.println();
	}
}
