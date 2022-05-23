package co.gov.supernotariado.bachue.portalgeografico.model;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;

/**
 * Clase de implementacion de entidad de la tabla SDB_BGN_PAIS de Gis
 * @author datatools
 */
@Entity
@Table(name=Constants.TABLA_PAIS)
public class Pais implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Variable ID_PAIS
	 * String que contiene el codigo del pais.
	 */   
	@Id
	private String ID_PAIS;
	/**
	 * Variable NOMBRE
	 * String que contiene el nombre del pais.
	 */
	private String NOMBRE;
	/**
	 * Variable ID_USUARIO_CREACION
	 * String que contiene del usuario de creacion del registro.
	 */
	private String ID_USUARIO_CREACION;
	/**
	 * Variable FECHA_CREACION
	 * Date que contiene la fecha de creacion del registro.
	 */
	private Date FECHA_CREACION;
	/**
	 * Variable IP_CREACION
	 * String que contiene la ip del usuario.
	 */
	private String IP_CREACION;
	/**
	 * Variable ID_USUARIO_MODIFICACION
	 * String que contiene el usuario que modifica el registo.
	 */
	private String ID_USUARIO_MODIFICACION;
	/**
	 * Variable FECHA_MODIFICACION
	 * Date que contiene la fecha de modificacion del registro.
	 */
	private Date FECHA_MODIFICACION;
	
	
	 /**
     * Constructor de la clase Pais
     */
	public Pais() {
		super();
	}
	
	
	public String getID_PAIS() {
		return this.ID_PAIS;
	}

	public void setID_PAIS(String ID_PAIS) {
		this.ID_PAIS = ID_PAIS;
	}   
	public String getNOMBRE() {
		return this.NOMBRE;
	}

	public void setNOMBRE(String NOMBRE) {
		this.NOMBRE = NOMBRE;
	}   
	
	
	public String getID_USUARIO_CREACION() {
		return ID_USUARIO_CREACION;
	}
	public void setID_USUARIO_CREACION(String iD_USUARIO_CREACION) {
		ID_USUARIO_CREACION = iD_USUARIO_CREACION;
	}
	public Date getFECHA_CREACION() {
		return FECHA_CREACION;
	}
	public void setFECHA_CREACION(Date fECHA_CREACION) {
		FECHA_CREACION = fECHA_CREACION;
	}
	public String getIP_CREACION() {
		return IP_CREACION;
	}
	public void setIP_CREACION(String iP_CREACION) {
		IP_CREACION = iP_CREACION;
	}
	public String getID_USUARIO_MODIFICACION() {
		return ID_USUARIO_MODIFICACION;
	}
	public void setID_USUARIO_MODIFICACION(String iD_USUARIO_MODIFICACION) {
		ID_USUARIO_MODIFICACION = iD_USUARIO_MODIFICACION;
	}
	public Date getFECHA_MODIFICACION() {
		return FECHA_MODIFICACION;
	}
	public void setFECHA_MODIFICACION(Date fECHA_MODIFICACION) {
		FECHA_MODIFICACION = fECHA_MODIFICACION;
	}
}