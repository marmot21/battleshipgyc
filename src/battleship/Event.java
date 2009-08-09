package battleship;

/**
 * A simple wrapper for events.
 * @author Amec
 */

public class Event
{
	public String mEvent; //the actual event name
	public Object mParam; //the object passed as parameters
	
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
