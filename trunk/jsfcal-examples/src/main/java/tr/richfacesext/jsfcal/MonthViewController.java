package tr.richfacesext.jsfcal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tr.richfacesext.components.jsfcal.Event;

/**
 * 
 * @author mert
 *
 */
public class MonthViewController {

	List<Event> events = new ArrayList<Event>();
	
	public List<Event> getEvents() {
		if (events.isEmpty()) {
			events.add(new CalEvent(1, "Birthday Party", today1Pm(), today6Pm(), false));
		}
		return events;
	}
	
	private Date today1Pm() {
		return null;
	}
	
	private Date today6Pm() {
		return null;
	}
}