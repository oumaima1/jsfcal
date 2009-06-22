package tr.richfacesext.components.jsfcal.month;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import tr.richfacesext.components.jsfcal.Event;

/**
 * 
 * @author mert
 *
 */
public class MonthView extends UIInput{


	private Integer _initYear;
	private Integer _initMonth;
	private Boolean _readOnly;
	private Integer _width;
	private Integer _height;
	private Boolean _abbrevDayHeadings;
	private Boolean _title;

	public MonthView() {
		setRendererType(MonthViewConstants.DEFAULT_RENDERER);
	}

	public String getFamily() {
		return MonthViewConstants.COMPONENT_FAMILY;
	}

	public Integer getInitYear() {
		if(_initYear != null )
			return _initYear;

		ValueBinding vb = getValueBinding("initYear");
		return vb != null ? (java.lang.Integer) vb.getValue(getFacesContext()) : getYear();
	}
	public void setInitYear(Integer initYearValue) {
		_initYear = initYearValue;
	}

	public Integer getInitMonth() {
		if(_initMonth != null )
			return _initMonth;

		ValueBinding vb = getValueBinding("initMonth");
		return vb != null ? (java.lang.Integer) vb.getValue(getFacesContext()) : getMonth();
	}
	public void setInitMonth(Integer initMonthValue) {
		_initMonth = initMonthValue;
	}

	public Boolean getReadOnly() {
		if(_readOnly != null )
			return _readOnly;

		ValueBinding vb = getValueBinding("readOnly");
		return vb != null ? (java.lang.Boolean) vb.getValue(getFacesContext()) : false;
	}
	public void setReadOnly(Boolean readOnlyValue) {
		_readOnly = readOnlyValue;
	}

	public Integer getWidth() {
		if(_width != null )
			return _width;

		ValueBinding vb = getValueBinding("width");
		return vb != null ? (java.lang.Integer) vb.getValue(getFacesContext()) : new Integer("650");
	}
	public void setWidth(Integer widthValue) {
		_width = widthValue;
	}

	public Integer getHeight() {
		if(_height != null )
			return _height;

		ValueBinding vb = getValueBinding("height");
		return vb != null ? (java.lang.Integer) vb.getValue(getFacesContext()) : new Integer("450");
	}
	public void setHeight(Integer heightValue) {
		_height = heightValue;
	}

	public Boolean getAbbrevDayHeadings() {
		if(_abbrevDayHeadings != null )
			return _abbrevDayHeadings;

		ValueBinding vb = getValueBinding("abbrevDayHeadings");
		return vb != null ? (java.lang.Boolean) vb.getValue(getFacesContext()) : true;
	}
	public void setAbbrevDayHeadings(Boolean abbrevDayHeadingsValue) {
		_abbrevDayHeadings = abbrevDayHeadingsValue;
	}

	public Boolean getTitle() {
		if(_title != null )
			return _title;

		ValueBinding vb = getValueBinding("title");
		return vb != null ? (java.lang.Boolean) vb.getValue(getFacesContext()) : true;
	}
	public void setTitle(Boolean titleValue) {
		_title = titleValue;
	}

	public Object saveState(FacesContext context) {
		Object values[] = new Object[8];
		values[0] = super.saveState(context);
		values[1] = _initYear;
		values[2] = _initMonth;
		values[3] = _readOnly;
		values[4] = _width;
		values[5] = _height;
		values[6] = _abbrevDayHeadings;
		values[7] = _title;
		return ((Object) values);
	}
	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(context, values[0]);
		_initYear = (Integer) values[1];
		_initMonth = (Integer) values[2];
		_readOnly = (Boolean) values[3];
		_width = (Integer) values[4];
		_height = (Integer) values[5];
		_abbrevDayHeadings = (Boolean) values[6];
		_title = (Boolean) values[7];
	}

	public Collection<Event> getEvents() {
		if (getValue() == null)
			return null;
		
		if (getValue() instanceof Collection)
			return (Collection<Event>) getValue();
		
		throw new IllegalArgumentException("value property of component should be an instanceof " + Collection.class.getName());
	}

	public int getYear() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}
	
	public int getMonth() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH);
	}
}