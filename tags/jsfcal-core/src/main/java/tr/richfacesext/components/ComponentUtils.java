package tr.richfacesext.components;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author mert
 * 
 */
public abstract class ComponentUtils {

	@SuppressWarnings("unchecked")
	public static void renderChildren(FacesContext facesContext,
			UIComponent component) throws IOException {
		if (component.getChildCount() > 0) {
			for (Iterator iterator = component.getChildren().iterator(); iterator
					.hasNext();) {
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
		} 
		else {
			renderChildren(facesContext, child);
		}
		child.encodeEnd(facesContext);
	}

	public static void setValueProperty(FacesContext context,
			UIComponent component, String value) {
		if (value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = context.getApplication().createValueBinding(value);
				component.setValueBinding("value", vb);
			} else {
				if (component instanceof ActionSource)
					((UICommand) component).setValue(value);
				else
					((ValueHolder) component).setValue(value);
			}
		}
	}

	public static void setMethodExpressionProperty(FacesContext context,
			UIComponent component, String attributeName, String value) {
		if (value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = context.getApplication().createValueBinding(
						value);
				component.setValueBinding(attributeName, vb);
			} else {
				component.getAttributes().put(attributeName, value);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void setStringProperty(FacesContext context,
			UIComponent component, String attributeName, String value) {
		if (value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = context.getApplication().createValueBinding(
						value);
				component.setValueBinding(attributeName, vb);
			} else {
				component.getAttributes().put(attributeName, value);
			}
		}
	}

	public static void setConverterProperty(FacesContext context,
			UIComponent component, String converter) {
		if (converter != null) {
			if (isValueReference(converter)) {
				ValueBinding vb = context.getApplication().createValueBinding(
						converter);
				component.setValueBinding("converter", vb);
			} else {
				Converter _converter = context.getApplication()
						.createConverter(converter);
				((ValueHolder) component).setConverter(_converter);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void setBooleanProperty(FacesContext context,
			UIComponent component, String attributeName, String value) {
		if (value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = context.getApplication().createValueBinding(
						value);
				component.setValueBinding(attributeName, vb);
			} else {
				component.getAttributes().put(attributeName,
						Boolean.valueOf(value));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void setIntegerProperty(FacesContext context,
			UIComponent component, String attributeName, String value) {
		if (value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = context.getApplication().createValueBinding(
						value);
				component.setValueBinding(attributeName, vb);
			} else {
				component.getAttributes().put(attributeName,
						Integer.valueOf(value));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void setObjectProperty(FacesContext context,
			UIComponent component, String attributeName, String value) {
		if (value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = context.getApplication().createValueBinding(
						value);
				component.setValueBinding(attributeName, vb);
			} else {
				throw new FacesException(
						"object properties could only be as valuebindings");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void setDoubleProperty(FacesContext context,
			UIComponent component, String attributeName, String value) {
		if (value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = context.getApplication().createValueBinding(
						value);
				component.setValueBinding(attributeName, vb);
			} else {
				component.getAttributes().put(attributeName,
						Double.valueOf(value));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void setMethodBindingProperty(FacesContext context,
			UIComponent component, String attributeName, String mbValue) {
		if (mbValue != null) {
			if (isValueReference(mbValue)) {
				Class params[] = { String.class };
				MethodBinding mb = context.getApplication()
						.createMethodBinding(mbValue, params);
				component.getAttributes().put(attributeName, mb);
			} else {
				throw new IllegalArgumentException("Component with id:"
						+ component.getId() + " has an invalid method binding");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void setMethodBindingPropertyWithNoProp(FacesContext context,
			UIComponent component, String attributeName, String mbValue) {
		if (mbValue != null) {
			if (isValueReference(mbValue)) {
				MethodBinding mb = context.getApplication()
						.createMethodBinding(mbValue, null);
				component.getAttributes().put(attributeName, mb);
			} else {
				throw new IllegalArgumentException("Component with id:"
						+ component.getId() + " has an invalid method binding");
			}
		}
	}

	public static void setValidatorProperty(FacesContext context,
			UIComponent component, String validator) {
		if (validator != null) {
			if (isValueReference(validator)) {
				Class params[] = { FacesContext.class, UIComponent.class,
						Object.class };
				MethodBinding mb = context.getApplication()
						.createMethodBinding(validator, params);
				((EditableValueHolder) component).setValidator(mb);
			} else {
				throw new IllegalArgumentException("Component with id:"
						+ component.getId()
						+ " has an invalid validator binding");
			}
		}
	}

	public static void setValueChangeListenerProperty(FacesContext context,
			UIComponent component, String valueChangeListener) {
		if (valueChangeListener != null) {
			if (isValueReference(valueChangeListener)) {
				Class params[] = { ValueChangeEvent.class };
				MethodBinding mb = context.getApplication()
						.createMethodBinding(valueChangeListener, params);
				((EditableValueHolder) component).setValueChangeListener(mb);
			} else {
				throw new IllegalArgumentException("Component with id:"
						+ component.getId() + " has an invalid validator");
			}
		}
	}

	public static void setRequiredProperty(FacesContext context,
			UIComponent component, String required) {
		if (required != null) {
			if (isValueReference(required)) {
				ValueBinding vb = context.getApplication().createValueBinding(
						required);
				component.setValueBinding("required", vb);
			} else {
				((EditableValueHolder) component).setRequired(Boolean.valueOf(
						required).booleanValue());
			}
		}
	}

	public static void setImmediateProperty(FacesContext context,
			UIComponent component, String immediate) {
		if (immediate != null) {
			if (isValueReference(immediate)) {
				ValueBinding vb = context.getApplication().createValueBinding(
						immediate);
				component.setValueBinding("immediate", vb);
			} else {
				((EditableValueHolder) component).setImmediate(Boolean.valueOf(
						immediate).booleanValue());
			}
		}
	}

	public static Object getValueToRender(FacesContext context,
			UIComponent component) {
		if (!(component instanceof ValueHolder))
			throw new IllegalArgumentException("Component : "
					+ component.getId() + "is not a ValueHolder");

		if (component instanceof EditableValueHolder) {
			Object submittedValue = ((EditableValueHolder) component)
					.getSubmittedValue();
			if (submittedValue != null) {
				return submittedValue;
			}
		}

		Object value = ((ValueHolder) component).getValue();
		if (((ValueHolder) component).getConverter() != null)
			return ((ValueHolder) component).getConverter().getAsString(
					context, component, value);
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

	public static UIComponent findComponentById(FacesContext context,
			UIComponent root, String id) {
		UIComponent component = null;

		for (int i = 0; i < root.getChildCount() && component == null; i++) {
			UIComponent child = (UIComponent) root.getChildren().get(i);
			component = findComponentById(context, child, id);
		}

		if (root.getId() != null && component == null
				&& root.getId().equals(id)) {
			component = root;
		}
		return component;
	}

	public static void renderAttributes(FacesContext facesContext,
			UIComponent component, String[] attributes) throws IOException {
		for (int i = 0; i < attributes.length; i++) {
			String attributeName = attributes[i];
			Object attributeValue = component.getAttributes()
					.get(attributeName);
			System.out.println("attributeName: " + attributeName
					+ " //// attributeValue: " + attributeValue);

			if (attributeValue != null)
				renderAttribute(facesContext, component, attributeName,
						attributeValue);
		}
	}

	public static void renderAttribute(FacesContext facesContext,
			UIComponent component, String attributeName, Object attributeValue)
			throws IOException {
		ResponseWriter writer = facesContext.getResponseWriter();

		// Special care for style class, disabled, size and max size.
		if (attributeName.equals(ComponentConstants.DISABLED_ATTR)
				&& attributeValue.toString().equals("false"))
			return;

		if (attributeName.equals(ComponentConstants.SIZE_ATTR)
				&& attributeValue.toString().equals("-2147483648"))
			return;

		if (attributeName.equals(ComponentConstants.MAXLENGTH_ATTR)
				&& attributeValue.toString().equals("-2147483648"))
			return;

		String htmlAttributeName = attributeName
				.equals(ComponentConstants.STYLECLASS_ATTR) ? "class"
				: attributeName;

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

	public static void encodeIncludeScript(FacesContext context,
			ResponseWriter writer, UIComponent component,
			String scriptId) throws IOException {
		writer.startElement("script", component);
		writer.writeAttribute("type", "text/javascript", null);
		writer.writeAttribute("src", scriptId, null);
		writer.endElement("script");
		writer.write("\n");
	}
	
	public static void encodeIncludeScript(FacesContext context,
			ResponseWriter writer, UIComponent component, String appURL,
			String scriptId) throws IOException {
		writer.startElement("script", component);
		writer.writeAttribute("type", "text/javascript", null);
		writer.writeAttribute("src", appURL + "/" + scriptId, null);
		writer.endElement("script");
		writer.write("\n");
	}

	public static void encodeIncludeStyle(FacesContext context,
			ResponseWriter writer, UIComponent component,
			String styleId) throws IOException {
		writer.startElement("link", component);
		writer.writeAttribute("type", "text/css", null);
		writer.writeAttribute("rel", "stylesheet", null);
		writer.writeAttribute("href", styleId, null);
		writer.endElement("link");
		writer.write("\n");
	}
	
	public static void encodeIncludeStyle(FacesContext context,
			ResponseWriter writer, UIComponent component, String appURL,
			String styleId) throws IOException {
		writer.startElement("link", component);
		writer.writeAttribute("type", "text/css", null);
		writer.writeAttribute("rel", "stylesheet", null);
		writer.writeAttribute("href", appURL + "/" + styleId, null);
		writer.endElement("link");
		writer.write("\n");
	}

	public static void setDateProperty(FacesContext context,
			UIComponent component, String attributeName, String value) {
		if (value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = context.getApplication().createValueBinding(
						value);
				component.setValueBinding(attributeName, vb);
			}
		}
	}

	public static boolean viewRootContainsPLKey(String viewId, String viewRootId) {
		return viewRootId.startsWith(viewId) || viewRootId.startsWith("/" + viewId);
	}

	@SuppressWarnings("unchecked")
	public static Map parseQueryString(String qs) throws UnsupportedEncodingException {
		if (qs == null) {
			return Collections.EMPTY_MAP;
		}

		Map params = new HashMap();
		String[] parts = qs.split("&");
		for (int i = 0; i < parts.length; i++) {
			String part = parts[i];
			int idx = part.indexOf('=');
			if (idx == -1) {
				continue;
			}
			String name = urlDecode(part.substring(0, idx));
			String value = urlDecode(part.substring(idx + 1));
			if (!params.containsKey(name)) {
				params.put(name, new String[] { value });
			} 
			else {
				String[] old = (String[]) params.get(name);
				String[] values = new String[old.length + 1];
				System.arraycopy(old, 0, values, 0, old.length);
				values[values.length - 1] = value;
				params.put(name, values);
			}
		}
		return params;
	}

	private static String urlDecode(String s) throws UnsupportedEncodingException {
		Pattern pattern = Pattern.compile("(%[a-fA-F0-9]{2})+|\\+");
		Matcher m = pattern.matcher(s);
		int start = 0;
		StringBuffer sb = new StringBuffer();
		while (m.find(start)) {
			if (start < m.start()) {
				sb.append(s.substring(start, m.start()));
			}
			if ("+".equals(m.group())) {
				sb.append(' ');
			} else {
				String hex = m.group();
				byte[] bytes = new byte[hex.length() / 3];
				for (int i = 0; i < bytes.length; i++) {
					int b = Integer.parseInt(hex.substring(i * 3 + 1, i * 3 + 3), 16);
					bytes[i] = (byte) b;
				}
				sb.append(new String(bytes, "UTF8"));
			}
			start = m.end();
		}
		if (start < s.length()) {
			sb.append(s.substring(start));
		}
		return sb.toString();
	}
	
	public static String getApplicationURL(FacesContext context) {
		HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();

		String scheme = req.getScheme(); // http
		String serverName = req.getServerName(); // hostname.com
		int serverPort = req.getServerPort(); // 80
		String appContext = req.getContextPath();
		
		return scheme + "://" + serverName + ":" + serverPort + appContext;
	}		
}