package tr.richfacesext.components.jsfcal.month;

import tr.richfacesext.components.ComponentConstants;

/**
 * 
 * @author mert
 *
 */
public interface MonthViewConstants {

	String COMPONENT_TYPE 	= "tr.richfacesext.components.jsfcal.Month";
	String COMPONENT_FAMILY = "tr.richfacesext.components.jsfcal";
	String DEFAULT_RENDERER = "tr.richfacesext.components.jsfcal.month.MonthViewRenderer";

	String STYLE_FULLCALENDAR 	= ComponentConstants.FACES_PREFIX  + ComponentConstants.RICHFACESEXT_RESOURCE_LOADER_VIEW_ID + "/month/" + "fullcalendar.css";
	
	String SCRIPT_JQUERY 		= ComponentConstants.FACES_PREFIX  + ComponentConstants.RICHFACESEXT_RESOURCE_LOADER_VIEW_ID + "/month/" + "jquery.js";
	String SCRIPT_UI_CORE 		= ComponentConstants.FACES_PREFIX  + ComponentConstants.RICHFACESEXT_RESOURCE_LOADER_VIEW_ID + "/month/" +  "ui.core.js";
	String SCRIPT_UI_DRAGGABLE 	= ComponentConstants.FACES_PREFIX  + ComponentConstants.RICHFACESEXT_RESOURCE_LOADER_VIEW_ID + "/month/" + "ui.draggable.js";
	String SCRIPT_FULLCALENDAR 	= ComponentConstants.FACES_PREFIX  + ComponentConstants.RICHFACESEXT_RESOURCE_LOADER_VIEW_ID + "/month/" +  "fullcalendar.js";
}
