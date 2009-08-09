package battleship;

import java.util.List;
import java.util.Vector;

/**
 * A manager of events
 * @author Amec
 */

public class EventManager implements Cloneable
{
	private List<Event> mEvents = new Vector<Event>(); //Vector is thread-safe
	
	/**
	 * Adds a new event to be processed.
	 * @param event The actual event to be added.
	 */
	public synchronized void add(Event event)
	{
		mEvents.add(event);
	}
	
	/**
	 * Adds an EventManager to another.
	 * @param eventMgr The EventManager which is to be added to this.
	 */
	public synchronized void addAll(EventManager eventMgr)
	{
		if(eventMgr != null)
		{
			for(int i = 0; i < eventMgr.size(); i++)
				mEvents.add(eventMgr.get(i));
		}
	}
	
	/**
	 * 
	 * @param eventNum The event number to return.
	 * @return
	 */
	public synchronized Event get(int eventNum)
	{
		return mEvents.get(eventNum);
	}
	
	/**
	 * 'Flushes' the EventManager of all one time events.
	 */
	public synchronized void flush()
	{
		for(int i = 0; i < mEvents.size(); i++)
		{
			if(mEvents.get(i).mEvent.startsWith("mouse") || mEvents.get(i).mEvent.startsWith("key"))
				mEvents.remove(i);
		}
	}
	
	/**
	 * Removes the event corresponding with eventNum from the event pool.
	 * @param eventNum
	 */
	public synchronized void consume(int eventNum)
	{
		mEvents.remove(eventNum);
	}
	
	/**
	 * Clears all events.
	 */
	public synchronized void clear()
	{
		mEvents.clear();
	}
	
	/**
	 * Returns the amount of events currently in the pool.
	 * @return
	 */
	public synchronized int size()
	{
		return mEvents.size();
	}
	
	/**
	 * Prints all events.
	 */
	public synchronized void print()
	{
		for(Event e : mEvents)
			System.out.println(e.mEvent);
	}
	
	/**
	 * Returns a copy of the EventManager
	 * @return
	 */
	@Override
	public EventManager clone() throws CloneNotSupportedException
	{
		EventManager tmp = new EventManager();
		tmp.addAll(this);
		return tmp;
	}
}
