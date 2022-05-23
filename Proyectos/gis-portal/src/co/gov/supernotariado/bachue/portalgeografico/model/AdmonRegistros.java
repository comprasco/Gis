package co.gov.supernotariado.bachue.portalgeografico.model;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;

/**
 * Clase de implementacion de entidad de la tabla SDB_BNG_ADMON_REGISTROS de Gis
 * @author datatools
 */
@Entity
@Table(name = Constants.TABLA_ADMON_REGISTROS)
@NamedQuery(name = Constants.ADMONREGISTROS_FINDVALUE, query = Constants.SELECT_ADMON_REGISTROS_CANTIDAD)
@NamedQuery(name = Constants.ADMONREGISTROS_CHANGEVALUE, query = Constants.UPDATE_ADMON_REGISTROS_CANTIDAD)
public class AdmonRegistros implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Variable REGISTRO
	 * String que contiene el nombre del registro de la pestaña de administracion.
	 */
	@Id
	private String REGISTRO;
	/**
	 * Variable CANTIDAD
	 * int que contiene el valor del registro de cada consulta.
	 */
	private int CANTIDAD;
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
     * Constructor de la clase AdmonRegistros
     */
	public AdmonRegistros() {
		super();
	}

	
	public String getREGISTRO() {
		return this.REGISTRO;
	}

	public void setREGISTRO(String REGISTRO) {
		this.REGISTRO = REGISTRO;
	}

	public int getCANTIDAD() {
		return this.CANTIDAD;
	}

	public void setCANTIDAD(int CANTIDAD) {
		this.CANTIDAD = CANTIDAD;
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
