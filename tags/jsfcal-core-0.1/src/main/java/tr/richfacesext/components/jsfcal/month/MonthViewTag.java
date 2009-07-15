package tr.richfacesext.components.jsfcal.month;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;

import tr.richfacesext.components.ComponentUtils;

/**
 * 
 * @author mert
 *
 */
public class MonthViewTag extends UIComponentTag {

	private String _value = null;
	private String _converter = null;
	private String _immediate = null;
	private String _required = null;
	private String _validator = null;
	private String _valueChangeListener = null;
	private String _initYear = null;
	private String _initMonth = null;
	private String _readOnly = null;
	private String _width = null;
	private String _height = null;
	private String _abbrevDayHeadings = null;
	private String _title = null;
	private String _language = null;

	public void release(){
		super.release();
		_value = null;
		_converter = null;
		_immediate = null;
		_required = null;
		_validator = null;
		_valueChangeListener = null;
		_initYear = null;
		_initMonth = null;
		_readOnly = null;
		_width = null;
		_height = null;
		_abbrevDayHeadings = null;
		_title = null;
		_language = null;
	}

	protected void setProperties(UIComponent uicomponent){
		super.setProperties(uicomponent);

		ComponentUtils.setValueProperty(getFacesContext(), uicomponent, _value);
		ComponentUtils.setConverterProperty(getFacesContext(), uicomponent, _converter);
		ComponentUtils.setImmediateProperty(getFacesContext(), uicomponent, _immediate);
		ComponentUtils.setRequiredProperty(getFacesContext(), uicomponent, _required);
		ComponentUtils.setValidatorProperty(getFacesContext(), uicomponent, _validator);
		ComponentUtils.setValueChangeListenerProperty(getFacesContext(), uicomponent, _valueChangeListener);
		ComponentUtils.setIntegerProperty(getFacesContext(), uicomponent, "initYear", _initYear );
		ComponentUtils.setIntegerProperty(getFacesContext(), uicomponent, "initMonth", _initMonth );
		ComponentUtils.setBooleanProperty(getFacesContext(), uicomponent, "readOnly", _readOnly );
		ComponentUtils.setIntegerProperty(getFacesContext(), uicomponent, "width", _width );
		ComponentUtils.setIntegerProperty(getFacesContext(), uicomponent, "height", _height );
		ComponentUtils.setBooleanProperty(getFacesContext(), uicomponent, "abbrevDayHeadings", _abbrevDayHeadings );
		ComponentUtils.setBooleanProperty(getFacesContext(), uicomponent, "title", _title );
		ComponentUtils.setStringProperty(getFacesContext(), uicomponent, "language", _language );
	}

	public String getComponentType() {
		return MonthViewConstants.COMPONENT_TYPE;
	}

	public String getRendererType() {
		return MonthViewConstants.DEFAULT_RENDERER;
	}

	public void setValue(String value){
		_value = value;
	}

	public void setConverter(String value){
		_converter = value;
	}

	public void setImmediate(String value){
		_immediate = value;
	}

	public void setRequired(String value){
		_required = value;
	}

	public void setValidator(String value){
		_validator = value;
	}

	public void setValueChangeListener(String value){
		_valueChangeListener = value;
	}

	public void setInitYear(String value){
		_initYear = value;
	}

	public void setInitMonth(String value){
		_initMonth = value;
	}

	public void setReadOnly(String value){
		_readOnly = value;
	}

	public void setWidth(String value){
		_width = value;
	}

	public void setHeight(String value){
		_height = value;
	}

	public void setAbbrevDayHeadings(String value){
		_abbrevDayHeadings = value;
	}

	public void setTitle(String value){
		_title = value;
	}
	
	public void setLanguage(String value){
		_language = value;
	}
}