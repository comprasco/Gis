package co.gov.supernotariado.bachue.portalgeografico.model.service;

import java.io.Serializable;
import java.util.List;

import co.gov.supernotariado.bachue.portalgeografico.model.service.ServiceLayerMetadata;

/**
 * Esta clase almacena la información de un servicio rest ArcGIS
 *
 */
public class ServiceMetadata implements Serializable {
	private static final long serialVersionUID = -2965407670093405738L;
	
	/**
	 * Variable url
	 * String que contiene la url.
	 */
	private String url;
	/**
	 * Variable version
	 * String que contiene la version.
	 */
	private String version;
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
	 * Variable description
	 * String que contiene la descripcion.
	 */
	private String description;
	/**
	 * Variable layers
	 * Lista de ServiceLayerMetadata que contiene las capas
	 */
	private List<ServiceLayerMetadata> layers;
	/**
	 * Variable currentVersion
	 * String que contiene la version actual.
	 */
	private String currentVersion;
	/**
	 * Variable mapName
	 * String que contiene el nombre del mapa.
	 */
	private String mapName;
	/**
	 * Variable supportsDynamicLayers
	 * String que contiene el soporte de las capas dinamicas.
	 */
	private String supportsDynamicLayers;

	
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ServiceLayerMetadata> getLayers() {
		return this.layers;
	}

	public void setLayers(List<ServiceLayerMetadata> layers) {
		this.layers = layers;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getSupportsDynamicLayers() {
		return supportsDynamicLayers;
	}

	public void setSupportsDynamicLayers(String supportsDynamicLayers) {
		this.supportsDynamicLayers = supportsDynamicLayers;
	}
}