package battleship;

import java.util.Vector;

/**
 * Singleton class
 * @author Obi
 * @author Amec
 *
 */
public class Events
{
	private static Events mInstance;
	private Vector<Event> mEvents = new Vector<Event>();
	
	private Events()
	{
		//stuff
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		throw new CloneNotSupportedException(); 
		//prevent any cloning
	}

	/**
	 * Return a pointer to the singleton class
	 * @return The class
	 */
	public static Events get()
	{
		if (mInstance == null)
			mInstance = new Events();
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
			System.out.println(mEvents.get(i).m_Event);
		System.out.println();
	}
}
