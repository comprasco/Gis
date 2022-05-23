package co.gov.supernotariado.bachue.portalgeografico.event;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;
import javax.faces.event.FacesListener;

import co.gov.supernotariado.bachue.portalgeografico.model.map.Extent;
import co.gov.supernotariado.bachue.portalgeografico.model.map.Screen;

/**
 * Evento que representa un click en cualquier parte del mapa
 *
 */
public class MapClickEvent extends AjaxBehaviorEvent {
	private static final long serialVersionUID = 5179890280858855519L;
	
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
	 * int que contiene el valor de acercamiento.
	 */
	private int zoom;
	/**
	 * Variable scale
	 * double que contiene la escala.
	 */
	private double scale;
	/**
	 * Variable extent
	 * Objeto Extent que contiene el extent.
	 */
	private Extent extent;
	/**
	 * Variable screen
	 * Objeto Screen que contiene screen.
	 */
	private Screen screen;

	/**
	 * Constructor un nuevo objeto de evento a partir del componente fuente
	 * especificado y el comportamiento de Ajax.
	 * 
	 * @param UIComponent component componente
	 * @param Behavior behavior comportamiento
	 */
	public MapClickEvent(UIComponent component, Behavior behavior) {
		super(component, behavior);
	}

	/**
	 * Metodo que devuelve verdadero si este FacesListener es una instancia de la clase de
	 * escucha adecuada que admite este evento.
	 * @param FacesListener listener escuchador
	 * @return un boolean
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

	public double getScale() {
		return this.scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public Extent getExtent() {
		return this.extent;
	}

	public void setExtent(Extent extent) {
		this.extent = extent;
	}

	public Screen getScreen() {
		return this.screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}
}
