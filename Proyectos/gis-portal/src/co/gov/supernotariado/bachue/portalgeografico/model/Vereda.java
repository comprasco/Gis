package co.gov.supernotariado.bachue.portalgeografico.model;

import javax.naming.NamingException;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;

/**
 * Clase de implementacion de entidad de la tabla VEREDA de Gis
 * @author datatools
 */
@Entity
@NamedQueries(value = {
	    @NamedQuery(name = Constants.VEREDA_GETALL, query = Constants.SELECT_VEREDA)
	})
@Table(name = Constants.TABLA_VEREDA)
public class Vereda {
    
	/**
	 * Variable objectid
	 * Integer que contiene el id del objeto.
	 */
	@Id
    private Integer objectid;
    
	/**
	 * Variable codigo
	 * String que contiene el codigo.
	 */
    private String codigo;
	/**
	 * Variable nombre
	 * String que contiene el nombre.
	 */
    private String nombre;
    
    
    /**
     * Constructor de la clase Vereda
     */
	public Vereda() throws NamingException{
	}
    
    
	public Integer getObjectid() {
		return objectid;
	}

	public void setObjectid(Integer objectid) {
		this.objectid = objectid;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
    
}
