package tr.richfacesext.jsfcal;

import java.util.ArrayList;
import java.util.Calendar;
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
			events.add(new CalEvent(1, "Birthday Party", "nice party", today1Pm(), today6Pm(), false));
			events.add(new CalEvent(2, "Breakfast at Tiffanys", "great brunch", nextDay9Am(), nextDay11Am(), false));
			events.add(new CalEvent(3, "Plant the new garden stuff", "meditation time", today3Pm(), theDayAfter3pm(), false));
		}
		return events;
	}
	
	private Calendar today() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 24);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);

		return calendar;
	}
	
	private Date today1Pm() {
		Calendar t = (Calendar) today().clone();
		t.set(Calendar.HOUR, 13);
		
		return t.getTime();
	}
	
	private Date today3Pm() {
		Calendar t = (Calendar) today().clone(); 
		t.set(Calendar.HOUR, 15);
		
		return t.getTime();
	}

	private Date today6Pm() {
		Calendar t = (Calendar) today().clone(); 
		t.set(Calendar.HOUR, 18);
		
		return t.getTime();
	}
	
	private Date nextDay9Am() {
		Calendar t = (Calendar) today().clone();
		t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
		t.set(Calendar.HOUR, 9);
		
		return t.getTime();
	}
	
	private Date nextDay11Am() {
		Calendar t = (Calendar) today().clone();
		t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
		t.set(Calendar.HOUR, 11);
		
		return t.getTime();
	}
	
	private Date theDayAfter3pm() {
		Calendar t = (Calendar) today().clone(); 
		t.set(Calendar.DATE, t.get(Calendar.DATE) + 2);		
		t.set(Calendar.HOUR, 15);
		
		return t.getTime();
	}	
}