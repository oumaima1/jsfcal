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
public class MonthViewExportPhaseListener implements PhaseListener {
	
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

		if (ComponentUtils.viewRootContainsPLKey(MonthViewConstants.PL_EXPORT_ACTIONS, viewRootId)) {
			try {
				doExport();
			}
			catch (StringIndexOutOfBoundsException e) {
				logger.error("export request is not a proper one! viewRootId: " + viewRootId, e);
			}
		}
	}

	private void doExport() {
		// TODO Auto-generated method stub
	}

}
