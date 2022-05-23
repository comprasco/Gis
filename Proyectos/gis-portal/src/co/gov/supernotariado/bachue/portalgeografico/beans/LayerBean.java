package co.gov.supernotariado.bachue.portalgeografico.beans;

import java.io.Serializable;

/**
 * 
 * Bean que se encarga de almacenar la información de las capas que se muestar
 * en la funcionalidad de administrar capas
 *
 */
public class LayerBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Variable id
	 * String que contiene el id.
	 */
	private String id;
	/**
	 * Variable name
	 * String que contiene el nombre.
	 */
	private String name;
	/**
	 * Variable type
	 * String que contiene el tipo.
	 */
	private String type;
	/**
	 * Variable geometry
	 * String que contiene la geometria.
	 */
	private String geometry;

    /**
     * Constructor de la clase LayerBean
     * @param String id, String name, String type, String geometry
     */
	public LayerBean(String id, String name, String type, String geometry) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.geometry = geometry;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGeometry() {
		return geometry;
	}

	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}

}
