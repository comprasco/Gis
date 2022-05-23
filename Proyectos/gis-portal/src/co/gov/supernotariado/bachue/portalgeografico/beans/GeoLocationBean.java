package co.gov.supernotariado.bachue.portalgeografico.beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;

import co.gov.supernotariado.bachue.portalgeografico.event.MapGeoLocationEvent;

/**
 * Este bean tiene como función manejar la geocodificacion de una dirección
 *
 */
@Named
@SessionScoped
public class GeoLocationBean implements Serializable {
	private static final long serialVersionUID = -8866985012091642915L;

	/**
	 * Variable watch boolean que contiene el estado del atributo watch para la geocodificacion.
	 */
	private boolean watch;
	/**
	 * Variable accuracy boolean que contiene el estado del atributo accuracy para la geocodificacion.
	 */
	private boolean accuracy;
	/**
	 * Variable timeout int que contiene el valor del tiempo de espera para la geocodificacion.
	 */
	private int timeout;
	/**
	 * Variable maximumAge int que contiene el valor del atributo maximumAge para la geocodificacion.
	 */
	private int maximumAge;
	/**
	 * Atributo event Objeto event que contiene la informacion de los eventos para la geocodificacion.
	 */
	private MapGeoLocationEvent event;

	/**
	 * Se inicializan las variables de la clase
	 */
	public GeoLocationBean() {
		super();
		reset();
	}

	/**
	 * Se resetean las variables de la clase
	 */
	public void reset() {
		this.watch = true;
		this.accuracy = true;
		this.timeout = 60000;
		this.maximumAge = 0;
		this.event = null;
	}

	/**
	 * Metodo escuchador de geocodificacion
	 * @param event evento escuchador
	 */
	public void doGeoLocationListener(AjaxBehaviorEvent event) {
		this.event = (MapGeoLocationEvent) event;
	}

	public boolean isWatch() {
		return watch;
	}

	public void setWatch(boolean watch) {
		this.watch = watch;
	}

	public boolean isAccuracy() {
		return accuracy;
	}

	public void setAccuracy(boolean accuracy) {
		this.accuracy = accuracy;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getMaximumAge() {
		return maximumAge;
	}

	public void setMaximumAge(int maximumAge) {
		this.maximumAge = maximumAge;
	}

	public MapGeoLocationEvent getEvent() {
		return event;
	}

	public void setEvent(MapGeoLocationEvent event) {
		this.event = event;
	}
}
