
package co.gov.supernotariado.bachue.portalgeografico.dao.interfaces;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import co.gov.supernotariado.bachue.portalgeografico.model.ConstantesBachue;

/**
 * Interfaz que contiene los metodos de los parametros del portal gis.
 * @author datatools
 */
@Local
public interface IGisParametrosDao extends Serializable {

	/**
	 * Metodo que obtiene el valor de una parametro en especifico.
	 * @param component El parámetro component define un String que debe contener el nombre del parametro.
	 * @return Un String con el valor del parametro especificado. 
	 */
	String getValue(String component);
	
	/**
	 * Metodo que obtiene todos los valores de los parametros.
	 * @return Una lista ConstantesBachue con todos los valores de los parametros. 
	 */

	List<ConstantesBachue> getAll();
	
	/**
	 * Metodo que obtiene el valor de una parametro en especifico.
	 * @param component El parámetro component define un String que debe contener el nombre del parametro.
	 * @return Un Arreglo de bytes que contiene la imagen o plantilla. 
	 */
	byte[] getBlob(String component);
}

