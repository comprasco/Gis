package co.gov.supernotariado.bachue.portalgeografico.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.application.ResourceDependencies;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import co.gov.supernotariado.bachue.portalgeografico.event.Event;
import co.gov.supernotariado.bachue.portalgeografico.event.MapGeoLocationEvent;
import co.gov.supernotariado.bachue.portalgeografico.utilities.ComponentUtilities;

/**
 * 
 * Clase que utiliza la interfaz ClientBehaviorHolder para ser implementada para
 * geocodificación y que desee admitir los comportamientos del cliente según lo
 * definido por ClientBehavior.
 *
 */
@FacesComponent("co.gov.supernotariado.bachue.portalgeografico.component.GeoLocation")
@ResourceDependencies({
		@javax.faces.application.ResourceDependency(library = "javax.faces", name = "jsf.js", target = "head"),
		@javax.faces.application.ResourceDependency(library = "javascript", name = "gisportal.js", target = "head"),
		@javax.faces.application.ResourceDependency(library = "css", name = "gisportal.css", target = "head") })
public class UIGeoLocation extends UIComponentBase implements ClientBehaviorHolder {

	/**
	 * Constructor de la clase donse establece la propieedad rendered en null
	 */
	public UIGeoLocation() {
		setRendererType(null);
	}

	/**
	 * Devuelve el identificador de la familia de componentes a la que pertenece
	 * este componente. Este identificador, junto con el valor de la propiedad
	 * rendererType, se puede usar para seleccionar el Renderer apropiado para esta
	 * instancia de componente.
	 * 
	 * @return retorna el identificador de la familia de componentes
	 */
	public String getFamily() {
		return null;
	}

	/**
	 * Devuelve una Colección no nula e inmodificable que contiene los nombres de
	 * los eventos lógicos admitidos por el componente que implementa esta interfaz.
	 * 
	 * @return retorna una Colección no nula e inmodificable
	 */
	public Collection<String> getEventNames() {
		return Arrays.asList(new String[] { Event.GEOLOCATION.toString() });
	}

	/**
	 * Devuelve el nombre de evento predeterminado para esta implementación de
	 * ClientBehaviorHolder.
	 * 
	 * @return el nombre de evento predeterminado
	 */
	public String getDefaultEventName() {
		return Event.GEOLOCATION.toString();
	}

	/**
	 * Devuelve una bandera que indica si este componente es responsable de
	 * representar sus componentes secundarios.
	 * 
	 * @return Devuelve una bandera de tipo boolean
	 */
	public boolean getRendersChildren() {
		return false;
	}

	/**
	 * Decodifique cualquier estado nuevo de este UIComponent a partir de la
	 * solicitud contenida en el FacesContext especificado y almacene este estado
	 * según sea necesario
	 * 
	 * @param FacesContext context contexto de la aplicacion
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void decode(FacesContext context) {
		String clientId = getClientId();
		if (ComponentUtilities.isComponentRequest(context, clientId)) {
		}
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();

		String name = (String) params.get("javax.faces.behavior.event");
		String timestamp;
		String latitude;
		String longitude;
		String zoom;
		String altitude;
		String accuracy;
		String altitudeAccuracy;
		String heading;
		String speed;
		if (Event.GEOLOCATION.toString().equals(name)) {
			timestamp = (String) params.get("gisportal.timestamp");
			latitude = (String) params.get("gisportal.latitude");
			longitude = (String) params.get("gisportal.longitude");
			zoom = (String) params.get("gisportal.zoom");
			altitude = (String) params.get("gisportal.altitude");
			accuracy = (String) params.get("gisportal.accuracy");
			altitudeAccuracy = (String) params.get("gisportal.altitudeAccuracy");
			heading = (String) params.get("gisportal.heading");
			speed = (String) params.get("gisportal.speed");

			List<ClientBehavior> behaviors = (List) getClientBehaviors().get(Event.GEOLOCATION.toString());
			if ((behaviors != null) && (!behaviors.isEmpty())) {
				for (ClientBehavior behavior : behaviors) {
					MapGeoLocationEvent event = new MapGeoLocationEvent(this, behavior);
					event.setTimestamp(Long.parseLong(timestamp));
					event.setLatitude(Double.parseDouble(latitude));
					event.setLongitude(Double.parseDouble(longitude));
					event.setZoom(Integer.parseInt(zoom));
					event.setAltitude(Double.parseDouble(altitude));
					event.setAccuracy(Double.parseDouble(accuracy));
					event.setAltitudeAccuracy(Double.parseDouble(altitudeAccuracy));
					event.setHeading(Double.parseDouble(heading));
					event.setSpeed(Double.parseDouble(speed));

					queueEvent(event);
				}
			}
		}
	}

	/**
	 * Representa el UIComponent especificado al principio en la secuencia de salida
	 * o escritor asociado con la respuesta que estamos creando.+
	 * 
	 * @param FacesContext context contexto de la aplicacion
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		String clientId = getClientId();

		boolean watch = ComponentUtilities.getBooleanAttribute(this, "watch", true);
		boolean accuracy = ComponentUtilities.getBooleanAttribute(this, "accuracy", true);
		int timeout = ComponentUtilities.getIntegerAttribute(this, "timeout", 60000);
		int maximumAge = ComponentUtilities.getIntegerAttribute(this, "maximumAge", 0);

		ResponseWriter writer = context.getResponseWriter();

		writer.startElement("span", this);
		writer.writeAttribute("id", clientId, null);
		writer.endElement("span");

		writer.startElement("script", this);
		writer.writeAttribute("type", "text/javascript", null);
		if (!context.isPostback()) {
			writer.write("if (!gisportal) var gisportal = {};");

			writer.write("gisportal.geolocationWatchId = null;");

			encodeJsfGisGeoLocationFunction(context, this, writer);
		}
		writer.write(String.format("gisportal.startWatchCurrentPosition(%s, %s, %s, %s);",
				new Object[] { Boolean.valueOf(watch), Boolean.valueOf(accuracy), Integer.valueOf(timeout),
						Integer.valueOf(maximumAge) }));
	}

	/**
	 * Si nuestra propiedad representada es verdadera, renderice el final del estado
	 * actual de este componente UIC.
	 * 
	 * @param FacesContext context contexto de la aplicacion
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();

		writer.endElement("script");
	}

	/**
	 * Renderiza los componentes para geocodificación
	 * 
	 * @param FacesContext   context contexto de la aplicacion
	 * @param UIComponent    component componente que hace referencia
	 * @param ResponseWriter writer Utilizado para escibir codigo javascript
	 * @throws IOException
	 */
	private void encodeJsfGisGeoLocationFunction(FacesContext context, UIComponent component, ResponseWriter writer)
			throws IOException {
		writer.write("gisportal.generateJsfGisGeoLocationEvent = function(position) {");

		List<ClientBehavior> behaviors = (List<ClientBehavior>) getClientBehaviors().get(Event.GEOLOCATION.toString());
		if ((behaviors != null) && (!behaviors.isEmpty())) {
			writer.write("var event = null;");
			for (ClientBehavior behavior : behaviors) {
				List<ClientBehaviorContext.Parameter> parameters = new ArrayList<>();
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.timestamp", "position.timestamp"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.latitude", "position.coords.latitude"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.longitude", "position.coords.longitude"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.zoom", "0"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.altitude",
						"gisportal.getNumericValue(position.coords.altitude, 0)"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.accuracy",
						"gisportal.getNumericValue(position.coords.accuracy, 0)"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.altitudeAccuracy",
						"gisportal.getNumericValue(position.coords.altitudeAccuracy, 0)"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.heading",
						"gisportal.getNumericValue(position.coords.heading, 0)"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.speed",
						"gisportal.getNumericValue(position.coords.speed, 0)"));

				ClientBehaviorContext cbc = ClientBehaviorContext.createClientBehaviorContext(context, component,
						Event.GEOLOCATION.toString(), getClientId(), parameters);
				String script = behavior.getScript(cbc);

				script = script.replace("'position.timestamp'", "position.timestamp");
				script = script.replace("'position.coords.latitude'", "position.coords.latitude");
				script = script.replace("'position.coords.longitude'", "position.coords.longitude");
				script = script.replace("'level'", "0");
				script = script.replace("'gisportal.getNumericValue(position.coords.altitude, 0)'",
						"gisportal.getNumericValue(position.coords.altitude, 0)");
				script = script.replace("'gisportal.getNumericValue(position.coords.accuracy, 0)'",
						"gisportal.getNumericValue(position.coords.accuracy, 0)");
				script = script.replace("'gisportal.getNumericValue(position.coords.altitudeAccuracy, 0)'",
						"gisportal.getNumericValue(position.coords.altitudeAccuracy, 0)");
				script = script.replace("'gisportal.getNumericValue(position.coords.heading, 0)'",
						"gisportal.getNumericValue(position.coords.heading, 0)");
				script = script.replace("'gisportal.getNumericValue(position.coords.speed, 0)'",
						"gisportal.getNumericValue(position.coords.speed, 0)");

				writer.write(script + ";");
			}
		}
		writer.write("};");
	}
}
