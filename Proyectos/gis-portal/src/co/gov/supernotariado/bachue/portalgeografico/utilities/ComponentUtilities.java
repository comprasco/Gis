package co.gov.supernotariado.bachue.portalgeografico.utilities;

import java.util.List;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * Clase de utilidad para componentes JSF dinamicos
 * 
 * @author csarmiento
 *
 */
public class ComponentUtilities {

	/**
	 * Obtener el prefijo de componente JSF
	 * 
	 * @param String id identificacion
	 * @return un String con el prefijo
	 */
	public static final String getClientIdPrefix(String id) {
		String prefix = null;
		if (id != null) {
			String separator = getFacesSeparator();
			if (id.contains(separator)) {
				prefix = id.substring(0, id.lastIndexOf(separator));
			}
		}
		return prefix;
	}

	/**
	 * Metodo que retorna el estado del id del cliente calificado
	 * @param String id identificacion 
	 * @return un boolean con el estado del calificador
	 */
	public static final boolean isClientIdQualified(String id) {
		boolean qualified = false;
		if (id != null) {
			String separator = getFacesSeparator();
			qualified = id.contains(separator);
		}
		return qualified;
	}

	/**
	 * Crear el id de un componente con su padre y nombre
	 * 
	 * @param String parent componente padre
	 * @param String child  componente hijo
	 * @return un String con el id
	 */
	public static final String qualifyClientId(String parent, String child) {
		String id = child;
		if ((parent != null) && (child != null)) {
			String separator = getFacesSeparator();
			if (!isClientIdQualified(child)) {
				String prefix = getClientIdPrefix(parent);
				id = prefix + separator + child;
			}
			if (id.startsWith(separator)) {
				id = id.substring(1);
			}
		}
		return id;
	}

	/**
	 * Obtener el separador de un componente JSF
	 * 
	 * @return un String con el separador
	 */
	public static final String getFacesSeparator() {
		return Character.toString(UINamingContainer.getSeparatorChar(FacesContext.getCurrentInstance()));
	}

	/**
	 * Obtener el valor tipo objeto de un atributo
	 * 
	 * @param UIComponent component componente
	 * @param String key llave
	 * @return un Object con el valor del atributo
	 */
	public static final Object getObjectAttribute(UIComponent component, String key) {
		Object value = null;

		Object obj = component.getAttributes().get(key);
		if (obj != null) {
			value = obj;
		}
		return value;
	}

	/**
	 * Obtener el valor tipo texto de un atributo
	 * 
	 * @param UIComponent component componente
	 * @param String key llave
	 * @return un String con el valor del atributo
	 */
	public static final String getStringAttribute(UIComponent component, String key) {
		return getStringAttribute(component, key, null);
	}

	/**
	 * Obtener el valor tipo texto de un atributo
	 * 
	 * @param UIComponent component componente
	 * @param String key llave
	 * @param String def definicion
	 * @return un String con el valor del atributo
	 */
	public static final String getStringAttribute(UIComponent component, String key, String def) {
		String value = def;

		Object obj = component.getAttributes().get(key);
		if (obj != null) {
			value = obj.toString();
		}
		return value;
	}

	/**
	 * Obtener el valor tipo boolean de un atributo
	 * 
	 * @param UIComponent component componente
	 * @param String key llave
	 * @return un boolean con el estado del atributo
	 */
	public static final boolean getBooleanAttribute(UIComponent component, String key) {
		return getBooleanAttribute(component, key, false);
	}

	/**
	 * Obtener el valor tipo boolean de un atributo
	 * 
	 * @param UIComponent component componente
	 * @param String key llave
	 * @param boolean def definicion
	 * @return un boolean con el estado del atributo
	 */
	public static final boolean getBooleanAttribute(UIComponent component, String key, boolean def) {
		boolean value = def;

		Object obj = component.getAttributes().get(key);
		if (obj != null) {
			value = Boolean.valueOf(obj.toString()).booleanValue();
		}
		return value;
	}

	/**
	 * Obtener el valor tipo entero de un atributo
	 * 
	 * @param UIComponent component componente
	 * @param String key llave
	 * @return un int con el valor del atributo
	 */
	public static final int getIntegerAttribute(UIComponent component, String key) {
		return getIntegerAttribute(component, key, 0);
	}

	/**
	 * Obtener el valor tipo entero de un atributo
	 * 
	 * @param UIComponent component componente
	 * @param String key llave
	 * @param int def definicion
	 * @return un int con el valor del atributo
	 */
	public static final int getIntegerAttribute(UIComponent component, String key, int def) {
		int value = def;
		try {
			Object obj = component.getAttributes().get(key);
			if (obj != null) {
				value = Integer.valueOf(obj.toString()).intValue();
			}
		} catch (Exception e) {
			value = def;
		}
		return value;
	}

	/**
	 * Obtener el valor tipo flotante de un atributo
	 * 
	 * @param UIComponent component componente
	 * @param String key llave
	 * @return un double con el valor del atributo
	 */
	public static final double getDoubleAttribute(UIComponent component, String key) {
		return getDoubleAttribute(component, key, 0.0D);
	}

	/**
	 * Obtener el valor tipo flotante de un atributo
	 * 
	 * @param UIComponent component componente
	 * @param String key llave
	 * @param double def definicion
	 * @return un double con el valor del atributo
	 */
	public static final double getDoubleAttribute(UIComponent component, String key, double def) {
		double value = def;
		try {
			Object obj = component.getAttributes().get(key);
			if (obj != null) {
				value = Double.valueOf(obj.toString()).doubleValue();
			}
		} catch (Exception e) {
			value = def;
		}
		return value;
	}

	/**
	 * Obtener la configuración de comportamiento del cliente javascript.
	 * 
	 * @param UIComponent component componente
	 * @param String event evento
	 * @return String con la configuracion del comportamiento
	 */
	@SuppressWarnings("rawtypes")
	public static final String getClientBehaviorAjaxScript(UIComponent component, String event) {
		String script = null;

		ClientBehaviorContext behaviorContext = ClientBehaviorContext.createClientBehaviorContext(
				FacesContext.getCurrentInstance(), component, event, component.getClientId(), null);
		Map<String, List<ClientBehavior>> behaviors = ((UICommand) component).getClientBehaviors();
		if (behaviors.containsKey(event)) {
			script = ((ClientBehavior) ((List) behaviors.get(event)).get(0)).getScript(behaviorContext);
		}
		return script;
	}

	/**
	 * Decodificar comportamientos del cliente
	 * 
	 * @param UIComponent component componente
	 */
	public static final void decodeClientBehaviors(UIComponent component) {
		FacesContext context;
		if ((component instanceof ClientBehaviorHolder)) {
			context = FacesContext.getCurrentInstance();
			ClientBehaviorHolder holder = (ClientBehaviorHolder) component;
			Map<String, List<ClientBehavior>> behaviors = holder.getClientBehaviors();
			if (!behaviors.isEmpty()) {
				ExternalContext external = context.getExternalContext();
				Map<String, String> params = external.getRequestParameterMap();
				String behaviorEvent = (String) params.get("javax.faces.behavior.event");
				if (behaviorEvent != null) {
					List<ClientBehavior> behaviorsForEvent = (List<ClientBehavior>) behaviors.get(behaviorEvent);
					if (behaviors.size() > 0) {
						String behaviorSource = (String) params.get("javax.faces.source");
						String clientId = component.getClientId();
						if ((behaviorSource != null) && (behaviorSource.equals(clientId))) {
							for (ClientBehavior behavior : behaviorsForEvent) {
								behavior.decode(context, component);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Revisar si el componente tiene un ActionListener
	 * 
	 * @param UIComponent component componente
	 * @return un boolean con el estado del escuchador
	 */
	public static final boolean hasActionOrActionListener(UICommand component) {
		return (component.getActionListeners().length > 0) || (component.getActionExpression() != null);
	}

	/**
	 * Revisar si el componente tiene parametros
	 * 
	 * @param FacesContext fc contexto 
	 * @param String clientId id del cliente
	 * @return un boolean con el estado del componente
	 */
	public static final boolean isComponentRequest(FacesContext fc, String clientId) {
		boolean result = false;

		String sourceId = (String) fc.getExternalContext().getRequestParameterMap().get("javax.faces.source");
		if ((sourceId != null) && (sourceId.equals(clientId))) {
			result = true;
		}
		return result;
	}

	/**
	 * Revisar si el componente si existe una expresión
	 * 
	 * @param UIComponent component componente
	 * @param String attribute atributo
	 * @return un boolean con el estado de la expresión
	 */
	public static final boolean isValueExpression(UIComponent component, String attribute) {
		return component.getValueExpression(attribute) != null;
	}

	/**
	 * Setear un valor a la expresión
	 * 
	 * @param FacesContext fc contexto
	 * @param UIComponent component componente
	 * @param String attribute atributo
	 * @param Object value valor
	 * @return un boolean con el estado de la expresión
	 */
	public static final boolean setValueExpressionValue(FacesContext fc, UIComponent component, String attribute,
			Object value) {
		boolean result = false;

		ValueExpression ve = component.getValueExpression(attribute);
		if ((ve != null) && (!ve.isReadOnly(fc.getELContext()))) {
			ve.setValue(fc.getELContext(), value);

			result = true;
		}
		return result;
	}
}
