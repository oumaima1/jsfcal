package tr.richfacesext.components.jsfcal.month;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.richfacesext.components.ComponentUtils;

/**
 * 
 * @author mert
 *
 */
public class MonthViewExportPhaseListener implements PhaseListener {
	
	private static final String KEYVAL_CALENDAR_TYPE = "type";
	private static final String CALENDAR_ICAL = "ical";
	private static final String CALENDAR_OUTLOOK = "outlook";
	
	private static final String KEYVAL_SELECTED_MONTH = "month";
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
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
		Map params = facesContext.getExternalContext().getRequestParameterMap();
		
		if (ComponentUtils.viewRootContainsPLKey(MonthViewConstants.PL_EXPORT_ACTIONS, viewRootId)) {
			try {
				doExport((String) params.get(KEYVAL_CALENDAR_TYPE), (String) params.get(KEYVAL_SELECTED_MONTH));
			}
			catch (StringIndexOutOfBoundsException e) {
				logger.error("export request is not a proper one! viewRootId: " + viewRootId, e);
			}
		}
		facesContext.responseComplete();
	}

	private void doExport(String type, String month) {
		System.out.println("---" + type);
		System.out.println("---" + month);
	}
}
