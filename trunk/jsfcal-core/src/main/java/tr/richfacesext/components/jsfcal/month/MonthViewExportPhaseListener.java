package tr.richfacesext.components.jsfcal.month;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.richfacesext.components.ComponentUtils;
import tr.richfacesext.components.jsfcal.Event;
import tr.richfacesext.components.jsfcal.ICalHelper;

/**
 * 
 * @author mert
 *
 */
@SuppressWarnings("serial")
public class MonthViewExportPhaseListener implements PhaseListener {
	
	private static final String KEYVAL_CALENDAR_TYPE = "type";
	private static final String CALENDAR_ICAL = "ical";
	private static final String CALENDAR_OUTLOOK = "outlook";
	
	private static final String KEYVAL_SELECTED_MONTH = "month";
	private static final String KEYVAL_SELECTED_YEAR = "year";
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public void beforePhase(PhaseEvent event) {
		// No-op
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	@SuppressWarnings("unchecked")
	public void afterPhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();
		String viewRootId = context.getViewRoot().getViewId();
		Map params = context.getExternalContext().getRequestParameterMap();
		
		if (ComponentUtils.viewRootContainsPLKey(MonthViewConstants.PL_EXPORT_ACTIONS, viewRootId)) {
			try {
				String elParam = (String) params.get(MonthViewConstants.KEY_EL);
				if (StringUtils.isNotEmpty(elParam)) {
					ValueBinding vb = context.getApplication().createValueBinding("#{" + elParam + "}");
					Object events = vb.getValue(context);
					
					if (events instanceof Collection) {
						Collection eventsCol = (Collection) events;
						String month = (String) params.get(KEYVAL_SELECTED_MONTH);
						String year = (String) params.get(KEYVAL_SELECTED_YEAR);
						
						HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
						ServletOutputStream outputStream = response.getOutputStream();
						
						response.setContentType("text/calendar");
						response.setStatus(200);
						response.setHeader("Expires", "0");
						response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
						response.setHeader("Pragma", "public");
						response.setHeader("Content-Disposition","attachment;filename=\"" + year + "-"+ (Integer.parseInt(month)+1) + ".ics" +  "\"");
						
						byte[] generatedCal = generateCal(eventsCol, (String) params.get(KEYVAL_CALENDAR_TYPE), month, year);
						
						outputStream.write(generatedCal);
						outputStream.flush();
						outputStream.close();
						
						context.responseComplete();
					}
				}
				else {
					throw new FacesException("component value should be an instance of " + Collection.class.getName());
				}
			}				
			catch (StringIndexOutOfBoundsException e) {
				logger.error("export request is not a proper one! viewRootId: " + viewRootId, e);
			} 
			catch (IOException e) {
				logger.error("error occured while exporting calendar " + viewRootId, e);
			}
		}
	}

	private byte[] generateCal(Collection eventsCol, String type, String month, String year) {
		logger.debug("Filtering calendar events for month: " + (Integer.parseInt(month)+1));
		List<VEvent> filteredEvents = getCalEventsForGivenMonth(eventsCol, Integer.parseInt(month), Integer.parseInt(year));
		if (CALENDAR_ICAL.equals(type)) {
			return generateICal(filteredEvents);
		}
		if (CALENDAR_OUTLOOK.equals(type)) {
			
		}
		return null;
	}

	private byte[] generateICal(List<VEvent> filteredEvents) {
		logger.debug("Exporting calendar with number of filtered events: " + filteredEvents.size());
		Calendar cal = ICalHelper.createCalendar();
		for (VEvent event : filteredEvents) {
			ICalHelper.addEventToCalendar(cal, event);
		}
		return ICalHelper.getCalAsByteArr(cal);
	}

	private List<VEvent> getCalEventsForGivenMonth(Collection eventsCol, int month, int year) {
		List<VEvent> calEvents = new ArrayList<VEvent>(); 
			
		for (Iterator iterator = eventsCol.iterator(); iterator.hasNext();) {
			Event event = (Event) iterator.next();
			
			java.util.Calendar cal = java.util.Calendar.getInstance();
			cal.setTime(event.getStartDate());
			
			if (cal.get(java.util.Calendar.MONTH) == month && cal.get(java.util.Calendar.YEAR) == year) {
				logger.debug("adding event to calendar: " + event.getTitle());
				VEvent calEvent = ICalHelper.createEvent(event);
				calEvents.add(calEvent);
			}
		}
		
		return calEvents;
	}
}