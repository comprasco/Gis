package co.gov.supernotariado.bachue.portalgeografico.model.service;

import java.io.Serializable;
import java.util.List;

import co.gov.supernotariado.bachue.portalgeografico.model.service.ServiceLayerFieldMetadata;

/**
 * Clase que almacena la información de una capa de un servicio ArcGIS
 *
 */
public class ServiceLayerMetadata implements Serializable {
	private static final long serialVersionUID = -6077242743780953638L;
	
	/**
	 * Variable url
	 * String que contiene la url.
	 */
	private String url;
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
	 * Variable type
	 * String que contiene el tipo de geometria.
	 */
	private String geometryType;
	/**
	 * Variable fields
	 * Lista de ServiceLayerFieldMetadata que contiene los datos de fields.
	 */
	private List<ServiceLayerFieldMetadata> fields;
	/**
	 * Variable parentLayerId
	 * String que contiene el id de la capa padre.
	 */
	private String parentLayerId;

	
	
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getGeometryType() {
		return this.geometryType;
	}

	public void setGeometryType(String geometryType) {
		this.geometryType = geometryType;
	}

	public List<ServiceLayerFieldMetadata> getFields() {
		return this.fields;
	}

	public void setFields(List<ServiceLayerFieldMetadata> fields) {
		this.fields = fields;
	}

	public String getParentLayerId() {
		return parentLayerId;
	}

	public void setParentLayerId(String parentLayerId) {
		this.parentLayerId = parentLayerId;
	}
}
