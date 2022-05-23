package co.gov.supernotariado.bachue.portalgeografico.utilities;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * Clase de utilidades para todo el entorno de jsf
 *
 */
public class JSFUtilities {

	/**
	 * Metodo que obtiene los datos de la vista
	 * 
	 * @return un String con la url
	 */
	public static final String getViewUrl() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();

		return String.format("%s://%s:%s%s", new Object[] { ec.getRequestScheme(), ec.getRequestServerName(),
				Integer.valueOf(ec.getRequestServerPort()), request.getRequestURI() });
	}

	/**
	 * Metodo que obtiene ejecuta las acciones
	 * 
	 * @param String action accion
	 * @return un String con la salida de la expresion
	 */
	public static final String executeAction(String action) {
		String outcome = null;

		String mbe = action;
		if (!isValidBindingExpression(action)) {
			mbe = wrapAsBindingExpression(action);
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		ELContext elc = fc.getELContext();
		Application app = fc.getApplication();
		MethodExpression me = app.getExpressionFactory().createMethodExpression(elc, mbe, String.class, new Class[0]);
		Object obj = me.invoke(elc, null);
		if (obj != null) {
			outcome = obj.toString();
		}
		return outcome;
	}

	/**
	 * Metodo de navegacion
	 * 
	 * @param String outcome valor salida
	 */
	public static final void navigate(String outcome) {
		FacesContext fc = FacesContext.getCurrentInstance();

		NavigationHandler handler = fc.getApplication().getNavigationHandler();
		handler.handleNavigation(fc, null, outcome);
	}

	/**
	 * Metodo de renderizado
	 * 
	 * @param String id identificacion
	 */
	public static final void render(String id) {
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(id);
	}

	/**
	 * Metodo que obtiene el bean manejado
	 * 
	 * @param String name nombre
	 * @return un Object con el bean
	 */
	public static final Object getManagedBean(String name) {
		Object bean = null;

		String vbe = name;
		if (!isValidBindingExpression(name)) {
			vbe = wrapAsBindingExpression(name);
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		ELContext elc = fc.getELContext();
		bean = fc.getApplication().getExpressionFactory().createValueExpression(elc, vbe, Object.class).getValue(elc);

		return bean;
	}

	/**
	 * Metodo que asigna el bean manejado
	 * 
	 * @param String name nombre, Object value valor
	 */
	public static final void setManagedBean(String name, Object value) {
		String vbe = name;
		if (!isValidBindingExpression(name)) {
			vbe = wrapAsBindingExpression(name);
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		ELContext elc = fc.getELContext();
		fc.getApplication().getExpressionFactory().createValueExpression(elc, vbe, Object.class).setValue(elc, value);
	}

	/**
	 * Metodo que valida la expresion
	 * 
	 * @param String name nombre
	 * @return un boolean con el estado de la validacion
	 */
	public static final boolean isValidBindingExpression(String value) {
		return value.trim().matches("^\\#\\{.+\\}$");
	}

	/**
	 * Metodo que obtiene un String de la union de la expresion
	 * 
	 * @param String value valor
	 * @return un String con el nombre
	 */
	public static final String getBindingExpressionString(String value) {
		String name = value;
		if (isValidBindingExpression(value)) {
			Pattern pattern = Pattern.compile("^\\#\\{(.+)\\}$");
			Matcher matcher = pattern.matcher(value);
			if ((matcher.find()) && (matcher.groupCount() == 1)) {
				name = matcher.group(1).trim();
			}
		}
		return name;
	}

	/**
	 * Metodo que envuelve el enlace en una expresion
	 * 
	 * @param String value valor
	 * @return un String con la expresion
	 */
	public static final String wrapAsBindingExpression(String value) {
		return String.format("#{%s}", new Object[] { value });
	}

	/**
	 * Metodo que obtiene el valor del recurso
	 * 
	 * @param String bundle valor, String key llave
	 * @return un String con el recurso
	 */
	public static final String getResourceValue(String bundle, String key) {
		return getResourceValue(bundle, key, null);
	}

	/**
	 * Metodo que obtiene el valor del recurso
	 * 
	 * @param String bundle valor, String key llave, Object[] params parametros
	 * @return un String con el recurso
	 */
	public static final String getResourceValue(String bundle, String key, Object[] params) {
		String text = null;
		try {
			Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

			ResourceBundle rb = ResourceBundle.getBundle(bundle, locale);
			text = rb.getString(key);
			if (params != null) {
				MessageFormat mf = new MessageFormat(text, locale);
				text = mf.format(params, new StringBuffer(), null).toString();
			}
		} catch (Exception e) {
			text = "???" + key + "???";
		}
		return text;
	}

	/**
	 * Metodo que agrega la informacion al mensaje
	 * 
	 * @param String text texto
	 */
	public static final void addInfoMessage(String text) {
		addInfoMessage(text, text);
	}

	/**
	 * Metodo que agrega la informacion al mensaje
	 * 
	 * @param String summary resumen, String detail detalle
	 */
	public static final void addInfoMessage(String summary, String detail) {
		addMessage(FacesMessage.SEVERITY_INFO, null, summary, detail);
	}

	/**
	 * Metodo que agrega la advertencia al mensaje
	 * 
	 * @param String text texto
	 */
	public static final void addWarningMessage(String text) {
		addWarningMessage(text, text);
	}

	/**
	 * Metodo que agrega la advertencia al mensaje
	 * 
	 * @param String summary resumen, String detail detalle
	 */
	public static final void addWarningMessage(String summary, String detail) {
		addMessage(FacesMessage.SEVERITY_WARN, null, summary, detail);
	}

	/**
	 * Metodo que agrega el error al mensaje
	 * 
	 * @param String text texto
	 */
	public static final void addErrorMessage(String text) {
		addErrorMessage(text, text);
	}

	/**
	 * Metodo que agrega el error al mensaje
	 * 
	 * @param String summary resumen, String detail detalle
	 */
	public static final void addErrorMessage(String summary, String detail) {
		addMessage(FacesMessage.SEVERITY_ERROR, null, summary, detail);
	}

	/**
	 * Metodo que agrega el error fatal al mensaje
	 * 
	 * @param String text texto
	 */
	public static final void addFatalMessage(String text) {
		addFatalMessage(text, text);
	}

	/**
	 * Metodo que agrega el error fatal al mensaje
	 * 
	 * @param String summary resumen, String detail detalle
	 */
	public static final void addFatalMessage(String summary, String detail) {
		addMessage(FacesMessage.SEVERITY_FATAL, null, summary, detail);
	}

	/**
	 * Metodo que agrega el mensaje
	 * 
	 * @param FacesMessage.Severity severity estado de severidad, String id
	 *                              identificacion, String summary resumen, String
	 *                              detail detalle
	 */
	public static final void addMessage(FacesMessage.Severity severity, String id, String summary, String detail) {
		FacesMessage message = new FacesMessage(severity, summary, detail);
		FacesContext.getCurrentInstance().addMessage(id, message);
	}

	/**
	 * Metodo que direcciona a la primera pagina de la tabla
	 * 
	 * @param UIData datatable tabla de datos
	 */
	public static final void gotoFirstDataTablePage(UIData datatable) {
		if (datatable != null) {
			datatable.setFirst(0);
		}
	}

	/**
	 * Metodo que direcciona a la ultima pagina de la tabla
	 * 
	 * @param UIData datatable tabla de datos
	 */
	public static final void gotoLastDataTablePage(UIData datatable) {
		if (datatable != null) {
			int rows = datatable.getRows();
			if (rows > 0) {
				int count = datatable.getRowCount();

				int first = count - (count % rows != 0 ? count % rows : rows);

				datatable.setFirst(first);
			}
		}
	}

	/**
	 * Metodo que busca el dato padre del componente
	 * 
	 * @param UIComponent component componente
	 * @return un UIData del dato padre del componente
	 */
	public static final UIData findParentUIData(UIComponent component) {
		if (component == null) {
			return null;
		}
		if ((component instanceof UIData)) {
			return (UIData) component;
		}
		return findParentUIData(component.getParent());
	}

	/**
	 * Metodo que asigna la vista al attributo
	 * 
	 * @param String viewId id de la vista, String key llave, Object value valor
	 */
	public static final void setViewAttribute(String viewId, String key, Object value) {
		FacesContext context = FacesContext.getCurrentInstance();

		@SuppressWarnings("unchecked")
		List<String> views = (List<String>) context.getExternalContext().getSessionMap().get("com.sun.faces.VIEW_LIST");
		if ((views != null) && (!views.contains(viewId))) {
			views.add(viewId);
		}
		UIViewRoot view = (UIViewRoot) context.getExternalContext().getSessionMap().get(viewId);
		if (view == null) {
			ViewHandler viewHandler = context.getApplication().getViewHandler();
			view = viewHandler.createView(context, viewId);
		}
		view.getAttributes().put(key, value);

		context.getExternalContext().getSessionMap().put(viewId, view);
	}

	/**
	 * Metodo que obtiene la vista del atributo
	 * 
	 * @param String viewId id de la vista, String key llave
	 * @return un Object con el valor
	 */
	public static final Object getViewAttribute(String viewId, String key) {
		Object value = null;

		UIViewRoot view = (UIViewRoot) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get(viewId);
		if (view != null) {
			value = view.getAttributes().get(key);
		}
		return value;
	}

	/**
	 * Metodo que elimina la vista del atributo
	 * 
	 * @param String viewId id de la vista, String key llave
	 * @return un boolean con el estado del atributo
	 */
	public static final boolean removeViewAttribute(String viewId, String key) {
		boolean removed = false;

		UIViewRoot view = (UIViewRoot) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get(viewId);
		if (view != null) {
			if (view.getAttributes().containsKey(key)) {
				removed = view.getAttributes().remove(key) != null;
			}
		}
		return removed;
	}

	/**
	 * Metodo que asigna la vista al attributo
	 * 
	 * @param String key llave, Object value valor
	 */
	public static final void setViewAttribute(String key, Object value) {
		FacesContext.getCurrentInstance().getViewRoot().getAttributes().put(key, value);
	}

	/**
	 * Metodo que obtiene la vista del atributo
	 * 
	 * @param String key llave
	 * @return un Object con el valor
	 */
	public static final Object getViewAttribute(String key) {
		return FacesContext.getCurrentInstance().getViewRoot().getAttributes().get(key);
	}

	/**
	 * Metodo que elimina la vista del atributo
	 * 
	 * @param String key llave
	 * @return un boolean con el estado del atributo
	 */
	public static final boolean removeViewAttribute(String key) {
		return FacesContext.getCurrentInstance().getViewRoot().getAttributes().remove(key) != null;
	}

	/**
	 * Metodo que busca el componente
	 * 
	 * @param UIComponent base base, String id identificacion
	 * @return un UIComponent con el componente
	 */
	public static final UIComponent findComponent(UIComponent base, String id) {
		if (id.equals(base.getId())) {
			return base;
		}
		UIComponent kid = null;
		UIComponent result = null;
		Iterator<UIComponent> kids = base.getFacetsAndChildren();
		while ((kids.hasNext()) && (result == null)) {
			kid = (UIComponent) kids.next();
			if (id.equals(kid.getId())) {
				result = kid;
			} else {
				result = findComponent(kid, id);
				if (result != null) {
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Metodo que busca el componente
	 * 
	 * @param String id identificacion
	 * @return un UIComponent con el componente
	 */
	public static final UIComponent findComponent(String id) {
		UIComponent component = null;

		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			UIComponent root = context.getViewRoot();
			component = findComponent(root, id);
		}
		return component;
	}
}
