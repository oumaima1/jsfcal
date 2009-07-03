package tr.richfacesext.components.jsfcal;

import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

/**
 * 
 * @author mert
 *
 */
public abstract class ICalHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(ICalHelper.class);

	public static Calendar createCalendar() {
		Calendar calendar = new Calendar();
		calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);
		
		return calendar;
	}
	
	public static VEvent createEvent(Event event) {
		DateTime start = new DateTime(event.getStartDate().getTime());
		DateTime end = new DateTime(event.getEndDate().getTime());
		VEvent iCalEvent = new VEvent(start,end,event.getTitle());
		
		return iCalEvent;
	}
	
	public static void writeICalOut(Calendar calendar, OutputStream output) {
		CalendarOutputter outputter = new CalendarOutputter();
		try {
			outputter.output(calendar, output);
		} 
		catch (IOException e) {
			logger.error("Error occured while writing to ical stream", e);
		} 
		catch (ValidationException e) {
			logger.error("Error occured while validating iCal", e);
		}
	}
}
