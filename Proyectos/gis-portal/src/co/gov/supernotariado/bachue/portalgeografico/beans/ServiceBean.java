package co.gov.supernotariado.bachue.portalgeografico.beans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;
import co.gov.supernotariado.bachue.portalgeografico.event.MapClickEvent;
import co.gov.supernotariado.bachue.portalgeografico.event.MapExtentEvent;
import co.gov.supernotariado.bachue.portalgeografico.event.MapGraphicViewEvent;
import co.gov.supernotariado.bachue.portalgeografico.model.map.Background;
import co.gov.supernotariado.bachue.portalgeografico.utilities.JSFUtilities;

/**
 * Esta clase tiene como función efectuar operaciones por una capa en particular
 *
 */
@Named
@SessionScoped
public class ServiceBean implements Serializable {
	private static final long serialVersionUID = 9135707557221898353L;

	/**
	 * Variable background
	 * String que contiene el fondo.
	 */
	private String background;
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
	 * Variable url
	 * String que contiene la url.
	 */
	private String url;

	/**
	 * Se inicializan las variables de la clase
	 */
	public ServiceBean() {
		super();
		reset();
	}

	/**
	 * Se resetean las variables de la clase
	 */
	public void reset() {
		this.background = Constants.BACKGROUND_SERVICE_BEAN;
		this.latitude = Constants.LATITUDE_SERVICE_BEAN;
		this.longitude = Constants.LONGITUDE_SERVICE_BEAN;
		this.zoom = Constants.ZOOM_SERVICE_BEAN;
		this.url = Constants.URL_SERVICE_BEAN;
	}

	/**
	 * Al hacer clic en la capa muestra información detallada de la misma
	 * 
	 * @param event evento
	 */
	public void doMapClickListener(AjaxBehaviorEvent event) {
		MapClickEvent e = (MapClickEvent) event;

		String summary = Constants.CLICK_SERVICE_BEAN;
		String detail = String.format(
				"Latitude='%s', Longitude='%s', Zoom='%s', Scale='%s', XMin='%s', YMin='%s', XMax='%s', YMax='%s', Height='%s', Width='%s', X='%s', Y='%s'",
				e.getLatitude(), e.getLongitude(), e.getZoom(), e.getScale(), e.getExtent().getXmin(),
				e.getExtent().getYmin(), e.getExtent().getXmax(), e.getExtent().getYmax(), e.getScreen().getHeight(),
				e.getScreen().getWidth(), e.getScreen().getX(), e.getScreen().getY());

		System.out.println(String.format("%s: %s", summary, detail));
		JSFUtilities.addInfoMessage(summary, detail);
	}

	/**
	 * Al hacer clic en la capa hace acercamiento a la misma
	 * 
	 * @param event evento
	 */
	public void doMapExtentListener(AjaxBehaviorEvent event) {
		MapExtentEvent e = (MapExtentEvent) event;

		String summary = Constants.EXTENT_SERVICE_BEAN;
		String detail = String.format(
				"Latitude='%s', Longitude='%s', Zoom='%s', Scale='%s', XMin='%s', YMin='%s', XMax='%s', YMax='%s', Height='%s', Width='%s'",
				e.getLatitude(), e.getLongitude(), e.getZoom(), e.getScale(), e.getExtent().getXmin(),
				e.getExtent().getYmin(), e.getExtent().getXmax(), e.getExtent().getYmax(), e.getScreen().getHeight(),
				e.getScreen().getWidth());

		System.out.println(String.format("%s: %s", summary, detail));
		JSFUtilities.addInfoMessage(summary, detail);
	}

	/**
	 * Al hacer clic el gráfico de una capa muestra información detallada de la
	 * mismo
	 * 
	 * @param event evento
	 */
	public void doMapGraphicViewListener(AjaxBehaviorEvent event) {
		MapGraphicViewEvent e = (MapGraphicViewEvent) event;

		String summary = Constants.GRAPHIC_SERVICE_BEAN;
		String detail = String.format("Service ID='%s', Layer ID='%s', Graphics ID='%s', Attributes='%s'",
				e.getServiceId(), e.getLayerId(), e.getGraphicId(), e.getAttributesJson());

		System.out.println(String.format("%s: %s", summary, detail));
		JSFUtilities.addInfoMessage(summary, detail);
	}

	/**
	 * Redirije a la información del servicio
	 * 
	 * @return la informacion del servicio
	 */
	public String doViewButtonAction() {
		System.out.println("View button clicked.");

		if (!this.url.toLowerCase().endsWith(Constants.MAP_SERVICE_BEAN)) {
			JSFUtilities.addErrorMessage(Constants.SERVICEBEAN_MENSAJE_1, Constants.SERVICEBEAN_MENSAJE_2);

			return null;
		} else {
			// Must redirect for GISFaces to reload service.
			return Constants.SERVICEBEAN_PAGINA_REDIRECCION;
		}
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public List<SelectItem> getBackgrounds() {
		return Background.getSelectItems();
	}

}
