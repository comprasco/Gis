package co.gov.supernotariado.bachue.portalgeografico.model.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.gov.supernotariado.bachue.portalgeografico.model.service.ServiceMetadata;

/**
 * Esta clase almacena la información de un directorio de un servicio rest ArcGIS
 *
 */
public class ServiceDirectoryMetadata implements Serializable {
	private static final long serialVersionUID = -6430850674927017440L;
	
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
	 * Variable currentVersion
	 * String que contiene la version actual.
	 */
	private String currentVersion;
	/**
	 * Variable folders
	 * Lista de String que contiene las carpetas.
	 */
	private List<String> folders = new ArrayList<>();
	/**
	 * Variable services
	 * Lista de ServiceMetadata que contiene los servicios.
	 */
	private List<ServiceMetadata> services;

	
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

	public List<String> getFolders() {
		return this.folders;
	}

	public void setFolders(List<String> folders) {
		this.folders = folders;
	}

	public List<ServiceMetadata> getServices() {
		return this.services;
	}

	public void setServices(List<ServiceMetadata> services) {
		this.services = services;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}
}