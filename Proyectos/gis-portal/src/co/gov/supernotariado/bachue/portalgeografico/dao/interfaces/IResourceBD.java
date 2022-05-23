package co.gov.supernotariado.bachue.portalgeografico.dao.interfaces;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import co.gov.supernotariado.bachue.portalgeografico.model.Departamento;
import co.gov.supernotariado.bachue.portalgeografico.model.Municipios;

/**
 * Interfaz que contiene los metodos de recursos secundarios para el funcionamiento del portal gis.
 * @author datatools
 */
@Local
public interface IResourceBD extends Serializable{
	
	/**
	 * Metodo que obtiene todos los departamentos.
	 * @return Una lista Departamento con todos los departamentos. 
	 */
	List<Departamento> getAllDepto();
	
	/**
	 * Metodo que obtiene los municipios de un departamento en especifico.
	 * @param cod_depto El parámetro cod_depto define un String que debe contener el codigo de un departamento.
	 * @return Una lista de Municipios del departamento especificado. 
	 */
	List<Municipios> municipio(String cod_depto);
	
}
