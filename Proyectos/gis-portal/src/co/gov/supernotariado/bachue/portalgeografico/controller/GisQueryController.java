package co.gov.supernotariado.bachue.portalgeografico.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;
import co.gov.supernotariado.bachue.portalgeografico.controller.GisQueryController;

/**
 * Esta clase define Constructores adicionales para el portal gis
 * @author datatools
 */
@ManagedBean
@ViewScoped
public class GisQueryController implements Serializable, Comparable<GisQueryController>{
	private static final Logger log = LoggerFactory.getLogger(GisQueryController.class);
	private static final long serialVersionUID = -1127288229201442895L;
	
	
	/**
	 * Metodo constructor que inicializa la clase.
	 */
	public GisQueryController(){
		log.info(Constants.MENSAJE_CONTROLLER_1);
//		Context ctx = new InitialContext();
//		ds = (DataSource)ctx.lookup("java:comp/env/jdbc/tierrasDS");
	}

	/**
	 * Metodo que compara los componentes.
	 * 
	 * @param o objeto GisQueryController
	 */
	@Override
	public int compareTo(GisQueryController o) {
		return 0;
	}	

}
