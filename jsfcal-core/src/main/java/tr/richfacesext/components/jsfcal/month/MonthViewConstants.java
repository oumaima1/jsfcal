package tr.richfacesext.components.jsfcal.month;

import java.util.HashMap;
import java.util.Map;

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

	String LOCALE_ENG = "en"; // default
	String LOCALE_FR = "fr";
	String LOCALE_DE = "de";
	String LOCALE_TR = "tr";
	
	Map<String, Integer> weekStarts = new HashMap<String, Integer>() {
		{
			put(LOCALE_ENG, 0);
			put(LOCALE_DE, 0);
			put(LOCALE_TR, 0);
			put(LOCALE_FR, 1);
		}
	};
	
	String SCRIPT_LOCALE_PREFIX	= ComponentConstants.RICHFACES_RESOURCE_FOLDER + "/" + ComponentConstants.SUBFOLDER_JS + "/month/" + "fullcalendar.locale.";
	String SCRIPT_LOCALE_SUFFIX	= ".js";
	
	String STYLE_FULLCALENDAR 	= ComponentConstants.FACES_PREFIX  + ComponentConstants.RICHFACESEXT_RESOURCE_LOADER_VIEW_ID + "/month/" + "fullcalendar.css";
	
	String SCRIPT_JQUERY 		= ComponentConstants.FACES_PREFIX  + ComponentConstants.RICHFACESEXT_RESOURCE_LOADER_VIEW_ID + "/core/" + "jquery.js";
	String SCRIPT_UI_CORE 		= ComponentConstants.FACES_PREFIX  + ComponentConstants.RICHFACESEXT_RESOURCE_LOADER_VIEW_ID + "/core/" + "ui.core.js";
	String SCRIPT_UI_DRAGGABLE 	= ComponentConstants.FACES_PREFIX  + ComponentConstants.RICHFACESEXT_RESOURCE_LOADER_VIEW_ID + "/core/" + "ui.draggable.js";
	String SCRIPT_FULLCALENDAR 	= ComponentConstants.FACES_PREFIX  + ComponentConstants.RICHFACESEXT_RESOURCE_LOADER_VIEW_ID + "/month/" + "fullcalendar.js";
	
	String GIF_ICAL 			= ComponentConstants.FACES_PREFIX  + ComponentConstants.RICHFACESEXT_RESOURCE_LOADER_VIEW_ID + "/month/" + "ical.gif";
	String GIF_OUTLOOK 			= ComponentConstants.FACES_PREFIX  + ComponentConstants.RICHFACESEXT_RESOURCE_LOADER_VIEW_ID + "/month/" + "outlook.gif";
	String GIF_PREVMONTH 		= ComponentConstants.FACES_PREFIX  + ComponentConstants.RICHFACESEXT_RESOURCE_LOADER_VIEW_ID + "/month/" + "prev-month.png";
	String GIF_NEXTMONTH 		= ComponentConstants.FACES_PREFIX  + ComponentConstants.RICHFACESEXT_RESOURCE_LOADER_VIEW_ID + "/month/" + "next-month.png";
	String GIF_TODAY 			= ComponentConstants.FACES_PREFIX  + ComponentConstants.RICHFACESEXT_RESOURCE_LOADER_VIEW_ID + "/month/" + "today.png";

	String PL_MONTH_ACTIONS 	= ".jsfcalMonthAction";
	String PL_EXPORT_ACTIONS 	= ".jsfcalExportAction";
	
	String KEY_EL 				= "el";
	String KEY_ACTION 			= "action";
	String KEY_ID 				= "id";
	String KEY_DAYDELTA 		= "dayDelta";
}