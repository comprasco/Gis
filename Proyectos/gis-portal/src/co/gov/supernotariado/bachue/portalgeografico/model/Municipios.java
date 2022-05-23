package co.gov.supernotariado.bachue.portalgeografico.model;

import java.io.Serializable;
import javax.persistence.*;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;

/**
 * Clase de implementacion de entidad de la tabla MUNICIPIOS de Gis
 * @author datatools
 */
@Entity
@Table(name=Constants.TABLA_MUNICIPIOS)
@NamedQuery(name=Constants.MUNICIPIOS_FINDALLVALUE, query=Constants.SELECT_MUNICIPIOS)
public class Municipios implements Serializable {
	private static final long serialVersionUID = 1L;
	

	/**
	 * Variable OBJECTID
	 * int que contiene el codigo del objeto.
	 */   
	@Id
	private int OBJECTID;
	/**
	 * Variable COD_DANE
	 * String que contiene el codigo del dane.
	 */
	private String COD_DANE;
	/**
	 * Variable DANE
	 * String que contiene el nombre del municipio por el dane.
	 */
	private String DANE;
	/**
	 * Variable NOM_DEPTO
	 * String que contiene el nombre del departamento.
	 */
	private String NOM_DEPTO;
	/**
	 * Variable NUM_DANE
	 * int que contiene el numero del dane.
	 */
	private int NUM_DANE;
	/**
	 * Variable NOM_MUNI
	 * String que contiene el nombre del municipio.
	 */
	private String NOM_MUNI;
	/**
	 * Variable RULEID
	 * int que contiene el id del rule.
	 */
	private int RULEID;
	
	
	 /**
     * Constructor de la clase Municipios
     */
	public Municipios() {
		super();
	}   
	
	
	public int getOBJECTID() {
		return this.OBJECTID;
	}

	public void setOBJECTID(int OBJECTID) {
		this.OBJECTID = OBJECTID;
	}   
  
   
	public int getNUM_DANE() {
		return this.NUM_DANE;
	}

	public void setNUM_DANE(int NUM_DANE) {
		this.NUM_DANE = NUM_DANE;
	}     
	public int getRULEID() {
		return this.RULEID;
	}

	public void setRULEID(int RULEID) {
		this.RULEID = RULEID;
	}
	public String getCOD_DANE() {
		return COD_DANE;
	}
	public void setCOD_DANE(String cOD_DANE) {
		COD_DANE = cOD_DANE;
	}
	public String getDANE() {
		return DANE;
	}
	public void setDANE(String dANE) {
		DANE = dANE;
	}
	public String getNOM_DEPTO() {
		return NOM_DEPTO;
	}
	public void setNOM_DEPTO(String nOM_DEPTO) {
		NOM_DEPTO = nOM_DEPTO;
	}
	public String getNOM_MUNI() {
		return NOM_MUNI;
	}
	public void setNOM_MUNI(String nOM_MUNI) {
		NOM_MUNI = nOM_MUNI;
	}
   
}
