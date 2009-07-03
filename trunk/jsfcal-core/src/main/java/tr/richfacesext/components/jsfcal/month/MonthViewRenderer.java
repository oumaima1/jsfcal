package tr.richfacesext.components.jsfcal.month;

import java.io.IOException;
import java.util.Collection;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import tr.richfacesext.components.ComponentConstants;
import tr.richfacesext.components.ComponentUtils;
import tr.richfacesext.components.jsfcal.Event;

/**
 * 
 * @author mert
 *
 */
public class MonthViewRenderer extends Renderer {

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		MonthView monthView = (MonthView) component;
		ResponseWriter writer = context.getResponseWriter();
		
		encodeIncludeStyles(context, writer, monthView);
		encodeIncludeScripts(context, writer, monthView);
		encodeWidget(writer, monthView);
		encodeMarkup(writer, monthView);
	}

	private void encodeIncludeStyles(FacesContext context, ResponseWriter writer, MonthView monthView) throws IOException {
		ComponentUtils.encodeIncludeStyle(context, writer, monthView, MonthViewConstants.STYLE_FULLCALENDAR);
	}

	private void encodeIncludeScripts(FacesContext context, ResponseWriter writer, MonthView monthView) throws IOException {
		ComponentUtils.encodeIncludeScript(context, writer, monthView, MonthViewConstants.SCRIPT_JQUERY);
		ComponentUtils.encodeIncludeScript(context, writer, monthView, MonthViewConstants.SCRIPT_UI_CORE);
		ComponentUtils.encodeIncludeScript(context, writer, monthView, MonthViewConstants.SCRIPT_UI_DRAGGABLE);
		ComponentUtils.encodeIncludeScript(context, writer, monthView, MonthViewConstants.SCRIPT_FULLCALENDAR);
	}

	private void encodeWidget(ResponseWriter writer, MonthView monthView) throws IOException {
		ValueExpression valueVE = monthView.getValueExpression("value");
        String valueStr = valueVE.getExpressionString();
		
		writer.write("" +
		"<script type=\"text/javascript\">\n" +
		"var GIF_ICAL = '" + MonthViewConstants.GIF_ICAL + "';\n" + 
		"var GIF_OUTLOOK = '" + MonthViewConstants.GIF_OUTLOOK + "';\n" + 
		"var GIF_PREVMONTH = '" + MonthViewConstants.GIF_PREVMONTH + "';\n" + 
		"var GIF_NEXTMONTH = '" + MonthViewConstants.GIF_NEXTMONTH + "';\n" + 
		"var GIF_TODAY = '" + MonthViewConstants.GIF_TODAY + "';\n" + 
		"var FACES_PREFIX = '" + ComponentConstants.FACES_PREFIX + "';\n" + 
		"var PL_EXPORT_ACTIONS = '" + MonthViewConstants.PL_EXPORT_ACTIONS + "';\n" + 
		"\n" +
		"$(document).ready(function() {\n" +
			"\t$('#" + monthView.getId() + "').fullCalendar({\n" +
				"\t\tyear: " + monthView.getYear() + ",\n" +
				"\t\tmonth: " + monthView.getMonth() + ",\n" +
				"\t\tdraggable: " + !monthView.getReadOnly() + ",\n" +
				"\t\tabbrevDayHeadings: " + monthView.getAbbrevDayHeadings() + ",\n" +
				"\t\ttitle: " + monthView.getTitle() + ",\n" +
				"\t\tevents: [\n" +
					getEventsAsStr(monthView.getEvents()) +
				"\t\t],\n" +
				"\t\teventDrop: function(calEvent, dayDelta, jsEvent, ui) {\n" +
				"jQuery.get('" + ComponentConstants.FACES_PREFIX + MonthViewConstants.PL_MONTH_ACTIONS + "?" + 
						MonthViewConstants.KEY_EL + "=" + valueStr.substring(2, valueStr.length()-1) + "&" + 
						MonthViewConstants.KEY_ACTION + "=" + "move" + "&" + MonthViewConstants.KEY_ID + "=" 
						+ "' + calEvent.id + '" + "&" + MonthViewConstants.KEY_DAYDELTA + "=" + "' + dayDelta);" +
				"\t\t\t" +
				"\t\t}," +				
			"\t});\n" +
		"});\n" +
		"</script>");		
	}
	
	private String getEventsAsStr(Collection<Event> events) {
		StringBuffer strEvents = new StringBuffer();
		Object[] eventsArr = events.toArray();
		for (int i = 0; i < eventsArr.length; i++) {
			Event e = (Event) eventsArr[i];

			strEvents.append("\t\t\t{\n");
			strEvents.append("\t\t\t\tid: " + e.getEventId() + ",\n");	
			strEvents.append("\t\t\t\ttitle: '" + e.getTitle() + "',\n");
			strEvents.append("\t\t\t\tstart: new Date(" + e.getStartDate().getTime() + "),\n");	
			strEvents.append("\t\t\t\tend: new Date(" + e.getEndDate().getTime() + ")\n");	
			strEvents.append("\t\t\t}");
			
			if (i != eventsArr.length - 1)
				strEvents.append(",\n");
			else
				strEvents.append("\n");
		}

		return strEvents.toString();
	}

	private void encodeMarkup(ResponseWriter writer, MonthView monthView) throws IOException {
		writer.write("<div style=\"width:" + monthView.getWidth() + "px;height:" + calculateHeightAccordingToWidth(monthView.getWidth(), monthView.getHeight()) + "\" id=\"" + monthView.getId() + "\"></div>");
	}

	private String calculateHeightAccordingToWidth(Integer width, Integer height) {
		float ratio = 1.18f;
		if (height<width/ratio) {
			return String.valueOf((int)width/ratio);
		}
		return String.valueOf(height);
	}
}