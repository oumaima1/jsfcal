package tr.richfacesext.components.jsfcal.month;

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
				String[] restParts = viewRootId.split("/");
				doAction(restParts);
			}
			catch (StringIndexOutOfBoundsException e) {
				logger.error("action request is not a proper one! viewRootId: " + viewRootId, e);
			}
		}
	}

	private void doAction(String[] restParts) {
		if (ACTION_MOVE.equals(restParts[0])) {
			
		}
	}
}
