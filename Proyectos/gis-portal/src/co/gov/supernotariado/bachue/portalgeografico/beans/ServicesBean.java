package co.gov.supernotariado.bachue.portalgeografico.beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.supernotariado.bachue.portalgeografico.beans.ServicesBean;
import co.gov.supernotariado.bachue.portalgeografico.component.Constants;
import co.gov.supernotariado.bachue.portalgeografico.model.service.ServiceDirectoryMetadata;
import co.gov.supernotariado.bachue.portalgeografico.model.service.ServiceDirectoryMetadataBuilder;
import co.gov.supernotariado.bachue.portalgeografico.utilities.JSFUtilities;

/**
 * 
 * Se almacenan y gestionan los servicios que se visualizan en la funcionalidad
 * de administrar capas
 *
 */
@Named
@SessionScoped
public class ServicesBean implements Serializable {
	private static final long serialVersionUID = 9135707557221898353L;
	private static final Logger log = LoggerFactory.getLogger(ServicesBean.class);
	
	/**
	 * Variable id
	 * String que contiene la url.
	 */
	private String url;
	/**
	 * Variable serviceDirectory
	 * String que contiene el directorio de servicio.
	 */
	private ServiceDirectoryMetadata serviceDirectory;

	/**
	 * Se inicializan las variables de la clase
	 */
	public ServicesBean() {
		super();
		reset();
	}

	/**
	 * Se resetean las variables de la clase
	 */
	public void reset() {
		this.url = Constants.URL_SERVICESBEAN;
		this.serviceDirectory = null;
	}

	/**
	 * Se ejecuta metodo para construir por la información del servicio a través de
	 * url.
	 * 
	 * @param event evento
	 */
	public void doQueryButtonActionListener(ActionEvent event) {
		System.out.println("Begin query action listener.");

		try {
			this.serviceDirectory = null;

			ServiceDirectoryMetadataBuilder builder = new ServiceDirectoryMetadataBuilder();
			this.serviceDirectory = builder.build(this.url);
		} catch (Exception e) {
			log.error(e.getMessage());
			JSFUtilities.addErrorMessage(e.getMessage());
		}

		System.out.println("End query action listener.");
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ServiceDirectoryMetadata getServiceDirectory() {
		return serviceDirectory;
	}

	public void setServiceDirectory(ServiceDirectoryMetadata serviceDirectory) {
		this.serviceDirectory = serviceDirectory;
	}
}
