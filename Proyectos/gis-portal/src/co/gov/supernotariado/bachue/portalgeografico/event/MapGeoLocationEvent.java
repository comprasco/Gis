package co.gov.supernotariado.bachue.portalgeografico.event;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;
import javax.faces.event.FacesListener;

/**
 * 
 * Evento que se dispara cuando se realiza una geocodificación de una dirección
 */
public class MapGeoLocationEvent extends AjaxBehaviorEvent {
	private static final long serialVersionUID = -7539452873861081760L;
	
	/**
	 * Variable timestamp
	 * Long que contiene un tiempo definido.
	 */
	private long timestamp;
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
	 * Variable zoom
	 * int que contiene el valor del acercamiento.
	 */
	private int zoom;
	/**
	 * Variable altitude
	 * double que contiene la altitud.
	 */
	private double altitude;
	/**
	 * Variable accuracy
	 * double que contiene la exactitud.
	 */
	private double accuracy;
	/**
	 * Variable altitudeAccuracy
	 * double que contiene la exactitudcon respecto a la altitud.
	 */
	private double altitudeAccuracy;
	/**
	 * Variable heading
	 * double que contiene el valor del atributo heading.
	 */
	private double heading;
	/**
	 * Variable speed
	 * double que contiene el valor de la velocidad.
	 */
	private double speed;

	
	/**
	 * Constructor un nuevo objeto de evento a partir del componente fuente
	 * especificado y el comportamiento de Ajax.
	 * 
	 * @param UIComponent component componente
	 * @param Behavior behavior comportamiento
	 */
	public MapGeoLocationEvent(UIComponent component, Behavior behavior) {
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

	public long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
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

	public int getZoom() {
		return this.zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public double getAltitude() {
		return this.altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public double getAccuracy() {
		return this.accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public double getAltitudeAccuracy() {
		return this.altitudeAccuracy;
	}

	public void setAltitudeAccuracy(double altitudeAccuracy) {
		this.altitudeAccuracy = altitudeAccuracy;
	}

	public double getHeading() {
		return this.heading;
	}

	public void setHeading(double heading) {
		this.heading = heading;
	}

	public double getSpeed() {
		return this.speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
