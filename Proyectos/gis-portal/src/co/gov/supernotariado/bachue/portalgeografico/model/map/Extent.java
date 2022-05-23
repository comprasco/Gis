package co.gov.supernotariado.bachue.portalgeografico.model.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.gov.supernotariado.bachue.portalgeografico.model.map.Coordinate;
import co.gov.supernotariado.bachue.portalgeografico.model.map.Extent;

/**
 * 
 * Una extensión es un rectángulo especificado al proporcionar la coordenada de
 * la esquina inferior izquierda y la coordenada de la esquina superior derecha
 * en unidades de mapa.
 *
 */
public class Extent implements Serializable {
	private static final long serialVersionUID = -8670823861666878998L;
	
	/**
	 * Variable WKID_WGS84
	 * int que contiene el valor del sistema de referencia.
	 */
	public static final int WKID_WGS84 = 4326;
	/**
	 * Variable wkid
	 * int que contiene el id de la referencia espacial.
	 */
	private int wkid;
	/**
	 * Variable xmin
	 * double que contiene el valor minimo de x.
	 */
	private double xmin;
	/**
	 * Variable ymin
	 * double que contiene el valor minimo de y.
	 */
	private double ymin;
	/**
	 * Variable xmax
	 * double que contiene el valor maximo de x.
	 */
	private double xmax;
	/**
	 * Variable ymax
	 * double que contiene el valor maximo de y.
	 */
	private double ymax;

	/**
	 * Contructor por defecto de la clase Extent
	 * 
	 */
	public Extent() {
	}

	
	/**
	 * Recibe un punto almacenado en la clase de tipo coordenadas
	 * 
	 * @param Coordinate c coordenada
	 */
	public Extent(Coordinate c) {
		this(c, c);
	}

	/**
	 * Recibe dos punto almacenado en la clase de tipo coordenadas
	 * 
	 * @param Coordinate c1 coordenada 1
	 * @param Coordinate c2 coordenada 2
	 */
	public Extent(Coordinate c1, Coordinate c2) {
		this(c1.getLongitude(), c1.getLatitude(), c2.getLongitude(), c2.getLatitude());
	}

	/**
	 * Recibe cuatro puntos que representan un rectangulo
	 * 
	 * @param double xmin
	 * @param double ymin
	 * @param double xmax
	 * @param double ymax
	 */
	public Extent(double xmin, double ymin, double xmax, double ymax) {
		this(4326, xmin, ymin, xmax, ymax);
	}

	/**
	 * Recibe cuatro puntos que representan un rectangulo y la referencia espacial
	 * 
	 * @param double wkid
	 * @param double xmin
	 * @param double ymin
	 * @param double xmax
	 * @param double ymax
	 */
	public Extent(int wkid, double xmin, double ymin, double xmax, double ymax) {
		this.wkid = wkid;
		this.xmin = xmin;
		this.ymin = ymin;
		this.xmax = xmax;
		this.ymax = ymax;
	}

	
	/**
	 * Metodo que obtiene las coordenadas
	 * @return una lista de coordenadas
	 */
	public List<Coordinate> getCoordinates() {
		List<Coordinate> coordinates = null;
		if ((this.xmin != 0.0D) && (this.ymin != 0.0D) && (this.xmax != 0.0D) && (this.ymax != 0.0D)) {
			coordinates = new ArrayList<>();

			coordinates.add(new Coordinate(this.ymin, this.xmin));
			coordinates.add(new Coordinate(this.ymax, this.xmax));
		}
		return coordinates;
	}

	/**
	 * Obtiene el centroide del extent
	 * 
	 * @return un Coordinate centroide del extent
	 */
	public Coordinate getCenter() {
		double x = (this.xmin + this.xmax) / 2.0D;
		double y = (this.ymin + this.ymax) / 2.0D;

		return new Coordinate(y, x);
	}

	/**
	 * Obtiene el ancho del rectángulo de la extensión
	 * 
	 * @return un double con el ancho
	 */
	public double getWidth() {
		return Math.abs(this.xmax - this.xmin);
	}

	/**
	 * Obtiene el alto del rectángulo
	 * 
	 * @return un double con el ancho
	 */
	public double getHeight() {
		return Math.abs(this.ymax - this.ymin);
	}

	/**
	 * Realiza la unión entre una lista extensiones
	 * 
	 * @param List<Coordinate> coordinates coordenadas
	 * @return un Extent de la union entre la lista
	 */
	public static final Extent union(List<Coordinate> coordinates) {
		Extent e = null;
		if ((coordinates != null) && (!coordinates.isEmpty())) {
			e = new Extent((Coordinate) coordinates.get(0));
			for (Coordinate c : coordinates) {
				if (c.getLatitude() < e.getYmin()) {
					e.setYmin(c.getLatitude());
				}
				if (c.getLatitude() > e.getYmax()) {
					e.setYmax(c.getLatitude());
				}
				if (c.getLongitude() < e.getXmin()) {
					e.setXmin(c.getLongitude());
				}
				if (c.getLongitude() > e.getXmax()) {
					e.setXmax(c.getLongitude());
				}
			}
		}
		return e;
	}

	/**
	 * Realiza la unión entre dos extensiones
	 * 
	 * @param Extent e1 extencion 1
	 * @param Extent e2 extencion 2
	 * @return un Extent de la union de dos extenciones
	 */
	public static final Extent union(Extent e1, Extent e2) {
		Extent e = null;
		List<Coordinate> coordinates = new ArrayList<>();
		if ((e1 != null) && (e1.getCoordinates() != null)) {
			coordinates.addAll(e1.getCoordinates());
		}
		if ((e2 != null) && (e2.getCoordinates() != null)) {
			coordinates.addAll(e2.getCoordinates());
		}
		if (!coordinates.isEmpty()) {
			e = union(coordinates);
		}
		return e;
	}
	
	
	public int getWkid() {
		return this.wkid;
	}

	public void setWkid(int wkid) {
		this.wkid = wkid;
	}

	public double getXmin() {
		return this.xmin;
	}

	public void setXmin(double xmin) {
		this.xmin = xmin;
	}

	public double getYmin() {
		return this.ymin;
	}

	public void setYmin(double ymin) {
		this.ymin = ymin;
	}

	public double getXmax() {
		return this.xmax;
	}

	public void setXmax(double xmax) {
		this.xmax = xmax;
	}

	public double getYmax() {
		return this.ymax;
	}

	public void setYmax(double ymax) {
		this.ymax = ymax;
	}

}
