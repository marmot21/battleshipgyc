package battleship;

/**
 * A simple wrapper for events. with a change here!
 * @author Amec
 * @author OBi
 */

public class Event
{
	/**
	 * the actual event name
	 */
	public String m_Event = "";
	/**
	 * the object passed as parameters
	 */
	public Object m_Param = null;
	
	/**
	 * The target object
	 */
	public String m_Target = "";
	
	/**
	 * 
	 */
	public boolean m_ToRemove = false;
	
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
		m_Event = str;
	}
	
	/**
	 * Constructor to use if you want to define parameters
	 * @param str The event string
	 * @param obj An object that is to be passed
	 */
	public Event(String str, Object obj)
	{
		m_Event = str;
		m_Param = obj;
	}
	
	/**
	 * Constructor to use if you wish to define a destination object
	 * @param str The event string
	 * @param obj An object that is to be passed
	 * @param target The target object that the event will be sent too
	 */
	public Event(String str, Object obj, String target)
	{
		m_Event = str;
		m_Param = obj;
		m_Target = target;
	}
}
