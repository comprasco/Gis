package co.gov.supernotariado.bachue.portalgeografico.model;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

/**
 * Clase de implementacion de entidad de la tabla MUNICIPIO anterior de Gis 
 * @author datatools
 */
@Entity
@Table(name="MUNICIPIO")
@NamedQuery(name="Municipio.findAll", query="SELECT m FROM Municipio m")
@NamedQuery(name="Municipio.findAllValue", query="SELECT m FROM Municipio m where m.ID_DEPARTAMENTO = :cod_depto")
@NamedQuery(name="Municipio.findValue", query="SELECT m FROM Municipio m where m.ID_DEPARTAMENTO = :depto and m.ID_PAIS = :pais and m.ID_MUNICIPIO = :municipio")
public class Municipio implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Variable ID_PAIS
	 * String que contiene el codigo del pais.
	 */
	private String ID_PAIS;
	/**
	 * Variable ID_DEPARTAMENTO
	 * String que contiene el codigo del departamento.
	 */ 
	private String ID_DEPARTAMENTO; 
	/**
	 * Variable ID_MUNICIPIO
	 * String que contiene el codigo del municipio.
	 */ 
	@Id
	private String ID_MUNICIPIO;
	/**
	 * Variable NOMBRE
	 * String que contiene el nombre del municipio.
	 */
	private String NOMBRE;
	
	
	 /**
     * Constructor de la clase Municipio
     */
	public Municipio() {
		super();
	} 
	
	
	public String getID_PAIS() {
		return this.ID_PAIS;
	}

	public void setID_PAIS(String ID_PAIS) {
		this.ID_PAIS = ID_PAIS;
	}   
	public String getID_DEPARTAMENTO() {
		return this.ID_DEPARTAMENTO;
	}

	public void setID_DEPARTAMENTO(String ID_DEPARTAMENTO) {
		this.ID_DEPARTAMENTO = ID_DEPARTAMENTO;
	}   
	public String getID_MUNICIPIO() {
		return this.ID_MUNICIPIO;
	}

	public void setID_MUNICIPIO(String ID_MUNICIPIO) {
		this.ID_MUNICIPIO = ID_MUNICIPIO;
	}   
	public String getNOMBRE() {
		return this.NOMBRE;
	}

	public void setNOMBRE(String NOMBRE) {
		this.NOMBRE = NOMBRE;
	}   
   
}
