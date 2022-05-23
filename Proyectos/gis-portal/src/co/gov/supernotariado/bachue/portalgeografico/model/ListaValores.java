package co.gov.supernotariado.bachue.portalgeografico.model;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;

/**
 * Clase de implementacion de entidad de la tabla SDB_ACC_LISTA_VALORES de Gis
 * @author datatools
 */
@Entity
@Table(name = Constants.TABLA_LISTA_VALORES)

@NamedQuery(name = Constants.LISTAVALORES_FINDTWO, query = Constants.SELECT_LISTA_VALORES_DATATABLE)
@NamedQuery(name = Constants.LISTAVALORES_FINDTWOA, query = Constants.SELECT_LISTA_VALORES_DATATABLE_ACTIVO)
@NamedQuery(name = Constants.LISTAVALORES_FINDTWOAMAX, query = Constants.SELECT_LISTA_VALORES_DATATABLE_DESCRIPCION)

@NamedQuery(name = Constants.LISTAVALORES_FINDALL, query = Constants.SELECT_LISTA_VALORES)
@NamedQuery(name = Constants.LISTAVALORES_FINDVALUE, query = Constants.SELECT_LISTA_VALORES_CODBUS)
@NamedQuery(name = Constants.LISTAVALORES_FINDALLVALUE, query = Constants.SELECT_LISTA_VALORES_TLISTA)
@NamedQuery(name = Constants.LISTAVALORES_FINDVALUEA, query = Constants.SELECT_LISTA_VALORES_TLISTA_ACTIVO)
@NamedQuery(name = Constants.LISTAVALORES_FINDALLVALUEA, query = Constants.SELECT_LISTA_VALORES_TLISTA_ACTIVO_VALOR)
@NamedQuery(name = Constants.LISTAVALORES_CHANGEVALUE, query = Constants.UPDATE_LISTA_VALORES)
@NamedQuery(name = Constants.LISTAVALORES_DELETEVALUE, query = Constants.DELETE_LISTA_VALORES)
public class ListaValores implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Variable TIPO_LISTA
	 * int que contiene el numero del tipo de lista.
	 */
	private int TIPO_LISTA;
	/**
	 * Variable VALOR
	 * String que contiene el nombre del valor de la lista.
	 */ 
	@Id
	private String VALOR;
	/**
	 * Variable ESTADO
	 * String que contiene el estado del valor de la lista.
	 */ 
	private String ESTADO;
	/**
	 * Variable COD_BUS
	 * String que contiene el codigo del bus.
	 */
	private String COD_BUS;
	/**
	 * Variable COD_BUSQUEDA_BUS
	 * int que contiene el numero del tipo de busqueda en el bus.
	 */
	private int COD_BUSQUEDA_BUS;
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
     * Constructor de la clase ListaValores
     */
	public ListaValores() {
		super();
	}

	
	
	public String getVALOR() {
		return this.VALOR;
	}

	public void setVALOR(String VALOR) {
		this.VALOR = VALOR;
	}

	public String getESTADO() {
		return this.ESTADO;
	}

	public void setESTADO(String ESTADO) {
		this.ESTADO = ESTADO;
	}

	public String getCOD_BUS() {
		return COD_BUS;
	}

	public void setCOD_BUS(String cOD_BUS) {
		COD_BUS = cOD_BUS;
	}

	public int getCOD_BUSQUEDA_BUS() {
		return COD_BUSQUEDA_BUS;
	}

	public void setCOD_BUSQUEDA_BUS(int cOD_BUSQUEDA_BUS) {
		COD_BUSQUEDA_BUS = cOD_BUSQUEDA_BUS;
	}

	public int getTIPO_LISTA() {
		return TIPO_LISTA;
	}

	public void setTIPO_LISTA(int tIPO_LISTA) {
		TIPO_LISTA = tIPO_LISTA;
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
