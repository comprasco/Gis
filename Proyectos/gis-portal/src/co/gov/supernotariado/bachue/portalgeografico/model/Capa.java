package co.gov.supernotariado.bachue.portalgeografico.model;

import java.io.Serializable;

/**
 * Entidad que almacena lo puntos de la funcionalidad de carga servicios OGC
 *
 */
public class Capa implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Variable id
	 * String que contiene el id.
	 */
	private String id;
	/**
	 * Variable layerName
	 * String que contiene el nombre de la capa.
	 */
	private String layerName;

	 /**
     * Constructor de la clase Capa
     */
	public Capa() {
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

}
