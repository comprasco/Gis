package co.gov.supernotariado.bachue.portalgeografico.beans;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;
import co.gov.supernotariado.bachue.portalgeografico.dao.interfaces.IGestionUsuarioDao;

/**
 * Esta clase define la sesion y el idioma del portal gis
 * 
 * @author datatools
 */
@ManagedBean
@SessionScoped
public class GisProperties {
	private static final Logger log = LoggerFactory.getLogger(GisProperties.class);
	/**
	 * Variable idioma
	 * String que contiene el idioma actual de la aplicacion
	 */
	private String idioma = Constants.APLICACION_ES;

	
	@EJB
	private IGestionUsuarioDao gesUser;

	
	/**
	 * Metodo constructor.
	 */	
	@PostConstruct
	public void init() {
		// this.idioma = Constants.APLICACION_ES;
	}

	/**
	 * Metodo que direcciona la aplicacion a la pagina de error.
	 */
	public void paginaError() {
		String url = Constants.PAGINA_ERROR;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			fc.getExternalContext().redirect(url);
		} catch (IOException e) {
			log.warn(Constants.MENSAJE_SESION_1 + e);
		}

	}

	/**
	 * Metodo que cierra la sesion en la aplicacion.
	 * @throws IOException 
	 */
	public void cerrarSesion() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		
		ExternalContext externalContext = context.getExternalContext();
		Object session = externalContext.getSession(false);
		HttpSession httpSession = (HttpSession) session;
		httpSession.invalidate();
		context.getExternalContext().invalidateSession();
		
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		request.getSession().invalidate();		
		weblogic.servlet.security.ServletAuthentication.logout(request);
		weblogic.servlet.security.ServletAuthentication.invalidateAll(request);
		weblogic.servlet.security.ServletAuthentication.done(request);
		


		String url = Constants.PAGINA_ERROR;
		try {
			context.getExternalContext().redirect(url);
		} catch (IOException e) {
			log.warn(Constants.MENSAJE_SESION_2 + e);
		}
	}

	/**
	 * Metodo que cambia el idioma de la aplicacion.
	 */
	public void idioma() throws IOException {

		if (this.idioma.equals(Constants.APLICACION_ES)) {
			this.idioma = Constants.APLICACION_EN;
		} else {
			this.idioma = Constants.APLICACION_ES;
		}
		System.out.println(idioma);
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

}
