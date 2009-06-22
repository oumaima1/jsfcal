package tr.richfacesext.components;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.ActionSource;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.ValueChangeEvent;
import javax.faces.webapp.UIComponentTag;

/**
 * 
 * @author mert
 *
 */
public abstract class ComponentUtils {

	@SuppressWarnings("unchecked")
	public static void renderChildren(FacesContext facesContext, UIComponent component) throws IOException {
		if (component.getChildCount() > 0) {
			for (Iterator iterator = component.getChildren().iterator(); iterator.hasNext();) {
				UIComponent child = (UIComponent) iterator.next();
				renderChild(facesContext, child);
			}
		}
	}

	 public static void renderChild(FacesContext facesContext, UIComponent child) throws IOException {
		if (!child.isRendered()) {
			return;
		}

		child.encodeBegin(facesContext);
		
		if (child.getRendersChildren()) {
			child.encodeChildren(facesContext);
		} else {
			renderChildren(facesContext, child);
		}
		child.encodeEnd(facesContext);
	}
	
	public static void setValueProperty(FacesContext context, UIComponent component, String value) {
		if(value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = context.getApplication().createValueBinding(value);
				component.setValueBinding("value", vb);
			} else {
				if (component instanceof ActionSource)
					((UICommand)component).setValue(value);
				else
					((ValueHolder)component).setValue(value);
			}
		}
	}
	
	public static void setMethodExpressionProperty(FacesContext context, UIComponent component, String attributeName, String value) {
		if(value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = context.getApplication().createValueBinding(value);
				component.setValueBinding(attributeName, vb);
			} 
			else {
				component.getAttributes().put(attributeName, value);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void setStringProperty(FacesContext context, UIComponent component, String attributeName, String value) {
		if(value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = context.getApplication().createValueBinding(value);
				component.setValueBinding(attributeName, vb);
			} else {
				component.getAttributes().put(attributeName, value);
			}
		}
	}
	
	public static void setConverterProperty(FacesContext context, UIComponent component, String converter) {
		if(converter != null) {
			if (isValueReference(converter)) {
				ValueBinding vb = context.getApplication().createValueBinding(converter);
				component.setValueBinding("converter", vb);
			} else {
				Converter _converter = context.getApplication().createConverter(converter);
				((ValueHolder)component).setConverter(_converter);
			}
		}
	}	
	
	@SuppressWarnings("unchecked")
	public static void setBooleanProperty(FacesContext context, UIComponent component, String attributeName, String value) {
		if(value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = context.getApplication().createValueBinding(value);
				component.setValueBinding(attributeName, vb);
			} else {
				component.getAttributes().put(attributeName, Boolean.valueOf(value));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void setIntegerProperty(FacesContext context, UIComponent component, String attributeName, String value) {
		if(value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = context.getApplication().createValueBinding(value);
				component.setValueBinding(attributeName, vb);
			} else {
				component.getAttributes().put(attributeName, Integer.valueOf(value));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void setObjectProperty(FacesContext context, UIComponent component, String attributeName, String value) {
		if(value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = context.getApplication().createValueBinding(value);
				component.setValueBinding(attributeName, vb);
			} else {
				throw new FacesException("object properties could only be as valuebindings");
			}
		}
	}	
	
	@SuppressWarnings("unchecked")
	public static void setDoubleProperty(FacesContext context, UIComponent component, String attributeName, String value) {
		if(value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = context.getApplication().createValueBinding(value);
				component.setValueBinding(attributeName, vb);
			} else {
				component.getAttributes().put(attributeName, Double.valueOf(value));
			}
		}
	}	
	
	@SuppressWarnings("unchecked")
	public static void setMethodBindingProperty(FacesContext context, UIComponent component, String attributeName, String mbValue) {
		if(mbValue != null) {
			if (isValueReference(mbValue)) {
				Class params [] = { String.class };
				MethodBinding mb = context.getApplication().createMethodBinding(mbValue, params);
				component.getAttributes().put(attributeName, mb);
			} else {
				throw new IllegalArgumentException("Component with id:" + component.getId() + " has an invalid method binding");
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void setMethodBindingPropertyWithNoProp(FacesContext context, UIComponent component, String attributeName, String mbValue) {
		if(mbValue != null) {
			if (isValueReference(mbValue)) {
				MethodBinding mb = context.getApplication().createMethodBinding(mbValue, null);
				component.getAttributes().put(attributeName, mb);
			} else {
				throw new IllegalArgumentException("Component with id:" + component.getId() + " has an invalid method binding");
			}
		}
	}
	
	public static void setValidatorProperty(FacesContext context, UIComponent component, String validator) {
		if(validator != null) {
			if (isValueReference(validator)) {
				Class params [] = { FacesContext.class, UIComponent.class, Object.class };
				MethodBinding mb = context.getApplication().createMethodBinding(validator, params);
				((EditableValueHolder)component).setValidator(mb);
			} else {
				throw new IllegalArgumentException("Component with id:" + component.getId() + " has an invalid validator binding");
			}
		}
	}
	
	public static void setValueChangeListenerProperty(FacesContext context, UIComponent component, String valueChangeListener) {
		if(valueChangeListener != null) {
			if (isValueReference(valueChangeListener)) {
				Class params [] = { ValueChangeEvent.class };
				MethodBinding mb = context.getApplication().createMethodBinding(valueChangeListener, params);
				((EditableValueHolder)component).setValueChangeListener(mb);
			} else {
				throw new IllegalArgumentException("Component with id:" + component.getId() + " has an invalid validator");
			}
		}
	}
	
	public static void setRequiredProperty(FacesContext context, UIComponent component, String required) {
		if(required != null) {
			if (isValueReference(required)) {
				ValueBinding vb = context.getApplication().createValueBinding(required);
				component.setValueBinding("required", vb);
			} else {
				((EditableValueHolder)component).setRequired(Boolean.valueOf(required).booleanValue());
			}
		}
	}
	
	public static void setImmediateProperty(FacesContext context, UIComponent component, String immediate) {
		if(immediate != null) {
			if (isValueReference(immediate)) {
				ValueBinding vb = context.getApplication().createValueBinding(immediate);
				component.setValueBinding("immediate", vb);
			} else {
				((EditableValueHolder)component).setImmediate(Boolean.valueOf(immediate).booleanValue());
			}
		}
	}	

	public static Object getValueToRender(FacesContext context, UIComponent component) {
		if (!(component instanceof ValueHolder))
			throw new IllegalArgumentException("Component : " + component.getId() + "is not a ValueHolder");

		if (component instanceof EditableValueHolder) {
			Object submittedValue = ((EditableValueHolder) component).getSubmittedValue();
			if (submittedValue != null) {
				return submittedValue;
			}
		}

		Object value = ((ValueHolder) component).getValue();
		if(((ValueHolder)component).getConverter() != null)
			return ((ValueHolder)component).getConverter().getAsString(context, component, value);
		else
			return value;
	}
		
	
	public static int getIntegerValue(FacesContext context, ValueBinding vb) {
		return ((Integer) vb.getValue(context)).intValue();
	}
	
	public static String getMethodExpression(MethodBinding mb) {
		String methodExpression = mb.getExpressionString();
		
		return methodExpression.substring(2, methodExpression.length() - 1);
	}
	
	public static boolean getBooleanValue(FacesContext context, ValueBinding vb) {
		return ((Boolean) vb.getValue(context)).booleanValue();
	}

	private static boolean isValueReference(String v) {
		return UIComponentTag.isValueReference(v);
	}
	
	public static UIComponent findComponentById(FacesContext context, UIComponent root, String id) {
		UIComponent component = null;
		
		for (int i = 0; i < root.getChildCount() && component == null; i++) {
			UIComponent child = (UIComponent) root.getChildren().get(i);
			component = findComponentById(context, child, id);
		}

		if (root.getId() != null && component == null && root.getId().equals(id)) {
			component = root;
		}
		return component;
	}
	
	public static void renderAttributes(FacesContext facesContext, UIComponent component, String[] attributes) throws IOException{
		for (int i = 0; i < attributes.length; i++) {
			String attributeName = attributes[i];
			Object attributeValue = component.getAttributes().get(attributeName);
			System.out.println("attributeName: " + attributeName + " //// attributeValue: " + attributeValue );

			if(attributeValue != null)
				renderAttribute(facesContext, component, attributeName, attributeValue);
		}
	}

	public static void renderAttribute(FacesContext facesContext, UIComponent component, String attributeName, Object attributeValue) throws IOException{
		ResponseWriter writer = facesContext.getResponseWriter();

		//Special care for style class, disabled, size and max size.
		if(attributeName.equals(ComponentConstants.DISABLED_ATTR) && attributeValue.toString().equals("false"))
			return;

		if(attributeName.equals(ComponentConstants.SIZE_ATTR) && attributeValue.toString().equals("-2147483648"))
			return;

		if(attributeName.equals(ComponentConstants.MAXLENGTH_ATTR) && attributeValue.toString().equals("-2147483648"))
			return;

		String htmlAttributeName = attributeName.equals(ComponentConstants.STYLECLASS_ATTR) ? "class" : attributeName;

		writer.writeAttribute(htmlAttributeName, attributeValue, attributeName);
	}	
	
	public static Object[] getObjectArr(Object value) {
		Object[] objArr = new Object[0];
		
		if (value instanceof Collection) 
			objArr = ((Collection) value).toArray();
		else if (value instanceof Map)
			objArr = ((Map) value).entrySet().toArray();
		else if (value instanceof Object[])
			objArr = (Object[]) value;
		
		return objArr;
	}

	public static String getListAsCommaSeperatedValues(List<String> props) {
		StringBuilder builder = new StringBuilder();
		for (String s : props) {
			builder.append(s);
			if (props.size() > 1 && !props.get(props.size() - 1).equals(s))
				builder.append(", ");
		}
		return builder.toString();
	}	
	
	public static void encodeIncludeScript(FacesContext context, ResponseWriter writer, UIComponent component, String contextPath, String scriptId) throws IOException {
		writer.startElement("script", component);
		writer.writeAttribute("type", "text/javascript", null);
		writer.writeAttribute("src", contextPath + scriptId, null);
		writer.endElement("script");
		writer.write("\n");
	}
	
	public static void encodeIncludeStyle(FacesContext context, ResponseWriter writer, UIComponent component, String contextPath, String styleId) throws IOException {
		writer.startElement("link", component);
		writer.writeAttribute("type", "text/css", null);
		writer.writeAttribute("rel", "stylesheet", null);
		writer.writeAttribute("href", contextPath + styleId, null);
		writer.endElement("link");
		writer.write("\n");
	}

	public static void setDateProperty(FacesContext context,
			UIComponent component, String attributeName, String value) {
		if(value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = context.getApplication().createValueBinding(value);
				component.setValueBinding(attributeName, vb);
			}
		}
	}
}