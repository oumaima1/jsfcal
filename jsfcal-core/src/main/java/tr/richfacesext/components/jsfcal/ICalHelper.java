package tr.richfacesext.components.jsfcal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		VEvent vEvent = new VEvent();
		
		vEvent.getProperties().add(new Uid(String.valueOf(event.getEventId())));
		vEvent.getProperties().add(new Summary(event.getTitle()));
		vEvent.getProperties().add(new Description(event.getDescription()));
		vEvent.getProperties().add(new DtStart(new DateTime(event.getStartDate().getTime())));
		vEvent.getProperties().add(new DtEnd(new DateTime(event.getEndDate().getTime())));
		
		return vEvent;
	}
	
	public static void addEventToCalendar(Calendar calendar, VEvent event) {
		calendar.getComponents().add(event);
	}
	
	public static byte[] getCalAsByteArr(Calendar calendar) {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		CalendarOutputter outputter = new CalendarOutputter();
		try {
			outputter.output(calendar, bout);
			return bout.toByteArray();
		} 
		catch (IOException e) {
			logger.error("Error occured while writing to ical stream", e);
		} 
		catch (ValidationException e) {
			logger.error("Error occured while validating iCal", e);
		}
		return null;
	}
}