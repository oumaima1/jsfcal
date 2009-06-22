package tr.richfacesext.components.jsfcal.month;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import tr.richfacesext.components.ComponentUtils;

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
		ComponentUtils.encodeIncludeStyle(context, writer, monthView, "", MonthViewConstants.STYLE_FULLCALENDAR);
	}

	private void encodeIncludeScripts(FacesContext context, ResponseWriter writer, MonthView monthView) throws IOException {
		ComponentUtils.encodeIncludeScript(context, writer, monthView, "", MonthViewConstants.SCRIPT_JQUERY);
		ComponentUtils.encodeIncludeScript(context, writer, monthView, "", MonthViewConstants.SCRIPT_UI_CORE);
		ComponentUtils.encodeIncludeScript(context, writer, monthView, "", MonthViewConstants.SCRIPT_UI_DRAGGABLE);
		ComponentUtils.encodeIncludeScript(context, writer, monthView, "", MonthViewConstants.SCRIPT_FULLCALENDAR);
	}

	private void encodeWidget(ResponseWriter writer, MonthView monthView) throws IOException {
		writer.write("" +
		"<script type=\"text/javascript\">\n" +
		"$(document).ready(function() {\n" +
			"\t$('#" + monthView.getId() + "').fullCalendar({\n" +
				"\t\tdraggable: true\n" +
			"\t});\n" +
		"});\n" +
		"</script>");		
	}
	
	private void encodeMarkup(ResponseWriter writer, MonthView monthView) throws IOException {
		writer.write("<div style=\"width:" + monthView.getWidth() + "px;height:" + monthView.getHeight() + "\" id=\"" + monthView.getId() + "\"></div>");
	}
}