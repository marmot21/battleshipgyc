package battleship;

import java.util.LinkedList;
import java.util.Queue;

public class EventManager
{
	public Queue<Event> eventqueue = new LinkedList<Event>();
	
	public void triggerEvent(Event e)
	{
		eventqueue.offer(e);
	}
}
