package co.gov.supernotariado.bachue.portalgeografico.model;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;

/**
 * Clase de implementacion de entidad de la tabla SDB_BNG_BUSQUEDA_BUS de Gis
 * @author datatools
 */
@Entity
@Table(name = Constants.TABLA_BUSQUEDA_BUS)
@NamedQuery(name = Constants.BUSQUEDABUS_FINDALL, query = Constants.SELECT_BUSQUEDA_BUS)
@NamedQuery(name = Constants.BUSQUEDABUS_FINDVALUE, query = Constants.SELECT_BUSQUEDA_BUS_COD)
public class BusquedaBus implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Variable COD_TIPO_BUSQUEDA
	 * int que contiene el codigo del tipo de busqueda en administracion.
	 */
	@Id
	private int COD_TIPO_BUSQUEDA;
	/**
	 * Variable DESCRIPCION
	 * String que contiene el nombre del tipo de busqueda.
	 */
	private String DESCRIPCION;
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
     * Constructor de la clase BusquedaBus
     */
	public BusquedaBus() {
		super();
	}

	
	public int getCOD_TIPO_BUSQUEDA() {
		return this.COD_TIPO_BUSQUEDA;
	}

	public void setCOD_TIPO_BUSQUEDA(int COD_TIPO_BUSQUEDA) {
		this.COD_TIPO_BUSQUEDA = COD_TIPO_BUSQUEDA;
	}

	public String getDESCRIPCION() {
		return this.DESCRIPCION;
	}

	public void setDESCRIPCION(String DESCRIPCION) {
		this.DESCRIPCION = DESCRIPCION;
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
