package co.gov.supernotariado.bachue.portalgeografico.model;

import java.io.Serializable;

/**
 * Entidad que almacena lo puntos de la funcionalidad de carga CSV
 *
 */
public class Puntos implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Variable consecutivo
	 * Long que contiene el consecutivo.
	 */
	private Long consecutivo;
	/**
	 * Variable longitud
	 * Double que contiene la longitud.
	 */
	private Double longitud;
	/**
	 * Variable latitud
	 * Double que contiene la latitud.
	 */
	private Double latitud;

	
    /**
     * Constructor de la clase Puntos
     * @param Long consecutivo, Double longitud, Double latitud
     */
	public Puntos(Long consecutivo, Double longitud, Double latitud) {
		super();
		this.consecutivo = consecutivo;
		this.longitud = longitud;
		this.latitud = latitud;
	}

	
	
	public Long getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(Long consecutivo) {
		this.consecutivo = consecutivo;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

}
