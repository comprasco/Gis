package co.gov.supernotariado.bachue.portalgeografico.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;

/**
 * Clase de implementacion de entidad de la tabla SDB_PGN_CONSTANTES de Bachue
 * @author datatools
 */
@Entity
@Table(name=Constants.TABLA_CONSTANTES_BACHUE)
@NamedQuery(name=Constants.CONSTANTESBACHUE_FINDALL, query=Constants.SELECT_CONSTANTES_BACHUE)
@NamedQuery(name=Constants.CONSTANTESBACHUE_FINDALLWHERE, query=Constants.SELECT_CONSTANTES_BACHUE_WHERE)
@NamedQuery(name=Constants.CONSTANTESBACHUE_FINDVALUE, query=Constants.SELECT_CONSTANTES_BACHUE_CARACTER)
public class ConstantesBachue implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Variable ID_CONSTANTE
	 * String que contiene el id de la constante.
	 */  
	@Id
	private String ID_CONSTANTE;
	/**
	 * Variable CARACTER
	 * String que contiene el nombre de la constante.
	 */ 
	private String CARACTER;
	/**
	 * Variable ENTERO
	 * int que contiene el entero de la constante. Permite representar constantes con valores en el dominio de los numeros naturales..
	 */ 
	private int ENTERO;
	/**
	 * Variable REAL
	 * double que contiene el valor decimal de la constante. Permite representar constantes con valores en el dominio de los numeros reales..
	 */
	private double REAL;
	/**
	 * Variable FECHA
	 * Date que contiene el valor fecha de la constante.
	 */
	private Date FECHA;
	/**
	 * Variable DESCRIPCION
	 * String que contiene el texto descriptivo del uso de la constante.
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
	 * Variable IP_MODIFICACION
	 * String que contiene la ip del usuario que modifica el registro.
	 */
	private String IP_MODIFICACION;
	/**
	 * Variable IMAGENES
	 * Arreglo de bytes que contiene la imagen o plantilla.
	 */
	private byte[] IMAGENES;
	/**
	 * Variable TIPO_ARCHIVO
	 * String que contiene el identificador de la extension (formato) del archivo imagen. Se espera que si el campo imagenes tiene informacion, el valor de este campo no sea vacio.
	 */
	private String TIPO_ARCHIVO;
	/**
	 * Variable TIPO
	 * char que contiene el tipo de constante Manual=M o Automatica=A.
	 */
	private char TIPO;
	/**
	 * Variable ACTIVO
	 * char que contiene el estado del registro en base de datos, posibles valores  S o N .
	 */
	private char ACTIVO;
	/**
	 * Variable TEXTO
	 * String que contiene la generacion de certificados.
	 */
	private String TEXTO;
	
	
	 /**
     * Constructor de la clase ConstantesBachue
     */
	public ConstantesBachue() {
		super();
	}   
	
	
	public String getID_CONSTANTE() {
		return this.ID_CONSTANTE;
	}

	public void setID_CONSTANTE(String ID_CONSTANTE) {
		this.ID_CONSTANTE = ID_CONSTANTE;
	}   
	public String getCARACTER() {
		return this.CARACTER;
	}

	public void setCARACTER(String CARACTER) {
		this.CARACTER = CARACTER;
	}   
	public int getENTERO() {
		return this.ENTERO;
	}

	public void setENTERO(int ENTERO) {
		this.ENTERO = ENTERO;
	}   
	public double getREAL() {
		return this.REAL;
	}

	public void setREAL(double REAL) {
		this.REAL = REAL;
	}   
	public Date getFECHA() {
		return this.FECHA;
	}

	public void setFECHA(Date FECHA) {
		this.FECHA = FECHA;
	}   
	public String getDESCRIPCION() {
		return this.DESCRIPCION;
	}

	public void setDESCRIPCION(String DESCRIPCION) {
		this.DESCRIPCION = DESCRIPCION;
	}   
	public String getID_USUARIO_CREACION() {
		return this.ID_USUARIO_CREACION;
	}

	public void setID_USUARIO_CREACION(String ID_USUARIO_CREACION) {
		this.ID_USUARIO_CREACION = ID_USUARIO_CREACION;
	}   
	public Date getFECHA_CREACION() {
		return this.FECHA_CREACION;
	}

	public void setFECHA_CREACION(Date FECHA_CREACION) {
		this.FECHA_CREACION = FECHA_CREACION;
	}   
	public String getIP_CREACION() {
		return this.IP_CREACION;
	}

	public void setIP_CREACION(String IP_CREACION) {
		this.IP_CREACION = IP_CREACION;
	}   
	public String getID_USUARIO_MODIFICACION() {
		return this.ID_USUARIO_MODIFICACION;
	}

	public void setID_USUARIO_MODIFICACION(String ID_USUARIO_MODIFICACION) {
		this.ID_USUARIO_MODIFICACION = ID_USUARIO_MODIFICACION;
	}   
	public Date getFECHA_MODIFICACION() {
		return this.FECHA_MODIFICACION;
	}

	public void setFECHA_MODIFICACION(Date FECHA_MODIFICACION) {
		this.FECHA_MODIFICACION = FECHA_MODIFICACION;
	}   
	public String getIP_MODIFICACION() {
		return this.IP_MODIFICACION;
	}

	public void setIP_MODIFICACION(String IP_MODIFICACION) {
		this.IP_MODIFICACION = IP_MODIFICACION;
	}   
	public String getTIPO_ARCHIVO() {
		return this.TIPO_ARCHIVO;
	}

	public void setTIPO_ARCHIVO(String TIPO_ARCHIVO) {
		this.TIPO_ARCHIVO = TIPO_ARCHIVO;
	}      
	public String getTEXTO() {
		return this.TEXTO;
	}

	public void setTEXTO(String TEXTO) {
		this.TEXTO = TEXTO;
	}


	public byte[] getIMAGENES() {
		return IMAGENES;
	}


	public void setIMAGENES(byte[] iMAGENES) {
		IMAGENES = iMAGENES;
	}


	public char getTIPO() {
		return TIPO;
	}


	public void setTIPO(char tIPO) {
		TIPO = tIPO;
	}


	public char getACTIVO() {
		return ACTIVO;
	}


	public void setACTIVO(char aCTIVO) {
		ACTIVO = aCTIVO;
	}
   
}
