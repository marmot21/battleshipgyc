package battleship;

/**
 * A simple wrapper for events.
 * @author Amec
 * @author OBi
 */

public class Event
{
	/**
	 * the actual event name
	 */
	public String mEvent;
	/**
	 * the object passed as parameters
	 */
	public Object mParam;
	
	/**
	 * Default constructor
	 */
	public Event()
	{
		
	}
	
	/**
	 * Constructor to use if you have no other parameters
	 * @param str The event string
	 */
	public Event(String str)
	{
		mEvent = str;
	}
	
	/**
	 * Constructor to use if you want to define parameters
	 * @param str The event string
	 * @param obj An object that is to be passed
	 */
	public Event(String str, Object obj)
	{
		mEvent = str;
		mParam = obj;
	}
}
