package co.gov.supernotariado.bachue.portalgeografico.model.service;

import java.io.Serializable;
import java.util.Map;

/**
 * Clase que almacena la información de un atributo de una capa de un servicio ArcGIS
 *
 */
public class ServiceLayerFieldMetadata implements Serializable {
	private static final long serialVersionUID = 3184478797212590240L;
	
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
	 * Variable alias
	 * String que contiene el alias.
	 */
	private String alias;
	/**
	 * Variable length
	 * int que contiene la longitud.
	 */
	private int length;
	/**
	 * Variable values
	 * Mapa de String que contiene los valores.
	 */
	private Map<String, String> values;

	
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Map<String, String> getValues() {
		return this.values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}
}