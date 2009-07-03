package tr.richfacesext.components.jsfcal.month;

import java.util.Collection;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.richfacesext.components.ComponentUtils;
import tr.richfacesext.components.jsfcal.Event;

/**
 * 
 * @author mert
 *
 */
@SuppressWarnings("serial")
public class MonthViewActionPhaseListener implements PhaseListener {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String ACTION_MOVE = "move";
	
	public void beforePhase(PhaseEvent event) {
		// No-op
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	@SuppressWarnings("unchecked")
	public void afterPhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		String viewRootId = facesContext.getViewRoot().getViewId();

		if (ComponentUtils.viewRootContainsPLKey(MonthViewConstants.PL_MONTH_ACTIONS, viewRootId)) {
			try {
				Map params = facesContext.getExternalContext().getRequestParameterMap();
				doAction(facesContext, params);
			}
			catch (StringIndexOutOfBoundsException e) {
				logger.error("action request is not a proper one! viewRootId: " + viewRootId, e);
			} 
		}
	}

	private void doAction(FacesContext context, Map params) {
		String paramAction = (String) params.get(MonthViewConstants.KEY_ACTION);
		String elParam = (String) params.get(MonthViewConstants.KEY_EL);
		
		if (StringUtils.isNotEmpty(elParam)) {
			ValueBinding vb = context.getApplication().createValueBinding("#{" + elParam + "}");
			Object events = vb.getValue(context);
			
			if (events instanceof Collection) {
				Collection eventsCol = (Collection) events;
				if (ACTION_MOVE.equals(paramAction)) {
					moveEvent(eventsCol, (String) params.get(MonthViewConstants.KEY_ID),(String) params.get(MonthViewConstants.KEY_DAYDELTA));
				}
			}
			else {
				throw new FacesException("component value should be an instance of " + Collection.class.getName());
			}
		}
	}

	private void moveEvent(Collection events, String id, String daydelta) {
		for (Object obj : events) {
			Event event = (Event) obj;
				
			if (event.getEventId().equals(new Long(id))) {
				event.getStartDate().setTime(new DateTime(event.getStartDate().getTime()).plusDays(Integer.parseInt(daydelta)).getMillis());
				event.getEndDate().setTime(new DateTime(event.getEndDate().getTime()).plusDays(Integer.parseInt(daydelta)).getMillis());
			}
		}
	}
}