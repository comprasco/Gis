package co.gov.supernotariado.bachue.portalgeografico.event;

import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;
import javax.faces.event.FacesListener;

/**
 * 
 * Evento que se dispara cuando se edita la posicón de un grafico al mapa
 *
 */
public class MapGraphicDragEvent extends AjaxBehaviorEvent {
	private static final long serialVersionUID = -5506652288211889909L;
	
	/**
	 * Variable serviceId
	 * String que contiene el id del servicio.
	 */
	private String serviceId;
	/**
	 * Variable graphicId
	 * String que contiene el id del grafico.
	 */
	private String graphicId;
	/**
	 * Variable type
	 * String que contiene el valor del atributo type.
	 */
	private String type;
	/**
	 * Variable latitude
	 * double que contiene la latitud.
	 */
	private double latitude;
	/**
	 * Variable longitude
	 * double que contiene la longitud.
	 */
	private double longitude;
	/**
	 * Variable attributesJson
	 * String que contiene los atributos del json.
	 */
	private String attributesJson;
	/**
	 * Variable attributesMap
	 * Mapa de String y object que contiene los atributos del mapa.
	 */
	private Map<String, Object> attributesMap;

	
	/**
	 * Constructor un nuevo objeto de evento a partir del componente fuente
	 * especificado y el comportamiento de Ajax.
	 * 
	 * @param UIComponent component componente
	 * @param Behavior behavior comportamiento
	 */
	public MapGraphicDragEvent(UIComponent component, Behavior behavior) {
		super(component, behavior);
	}

	/**
	 * Devuelve verdadero si este FacesListener es una instancia de la clase de
	 * escucha adecuada que admite este evento.
	 * @param FacesListener listener escuchador
	 * @return un boolean con el estado
	 */
	public boolean isAppropriateListener(FacesListener listener) {
		return listener instanceof AjaxBehaviorListener;
	}

	/**
	 * Transmita esta instancia de evento al FacesListener especificado, por
	 * cualquier mecanismo que sea apropiado.
	 * @param FacesListener listener escuchador
	 */
	public void processListener(FacesListener listener) {
		((AjaxBehaviorListener) listener).processAjaxBehavior(this);
	}

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getGraphicId() {
		return this.graphicId;
	}

	public void setGraphicId(String graphicId) {
		this.graphicId = graphicId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getAttributesJson() {
		return this.attributesJson;
	}

	public void setAttributesJson(String attributesJson) {
		this.attributesJson = attributesJson;
	}

	public Map<String, Object> getAttributesMap() {
		return this.attributesMap;
	}

	public void setAttributesMap(Map<String, Object> attributesMap) {
		this.attributesMap = attributesMap;
	}
}
