package co.gov.supernotariado.bachue.portalgeografico.model.map;

import java.io.Serializable;

/**
 * Clase que se encarga de almacenar latitude y longitude válidas para Colombia
 * 
 * @author csarmiento
 *
 */
public class Coordinate implements Serializable {
	private static final long serialVersionUID = 8606961765098525165L;
	
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
	 * Contructor por defecto de la clase Coordinate
	 * 
	 */
	public Coordinate() {
	}

	/**
	 * Contructor recibe las coordenadas decimales
	 * 
	 * @param double latitude Valor de la latitud
	 * @param double longitude Valor de la longitud
	 */
	public Coordinate(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	
	/**
	 * Valida que las cooordendas estén dentro del límite de Colombia
	 * 
	 * @return un booolean con el estado si cumple o no con los limites
	 */
	public boolean isValid() {
		return (this.latitude <= 90.0D) && (this.latitude >= -90.0D) && (this.longitude <= 180.0D)
				&& (this.longitude >= -180.0D);
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


}
