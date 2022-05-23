package co.gov.supernotariado.bachue.portalgeografico.dao.interfaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;

import co.gov.supernotariado.bachue.portalgeografico.model.AnotacionesDataTable;
import co.gov.supernotariado.bachue.portalgeografico.model.AttributeDataTable;
import co.gov.supernotariado.bachue.portalgeografico.model.DatosJuridicoDataTable;
import co.gov.supernotariado.bachue.portalgeografico.model.DatosPredioDataTable;

/**
 * Interfaz que contiene los metodos de Consultas del portal gis.
 * @author datatools
 */
@Local
public interface IConsultationDao {

	/**
	 * Metodo que obtiene los registros por numero de la propiedad
	 * @param Search, value El parámetro Search define un String que contiene el tipo de busqueda y El parámetro value define un String con el numero de la propiedad
	 * @return Una lista de AttributeDataTable que contiene los valores de los predios asociados al numero. 
	 */	
	public List<AttributeDataTable> propertyNumber(String Search, String value);

	/**
	 * Metodo que obtiene los registros de predios por su rango de area
	 * @param Search, value El parámetro Search define un String que contiene el tipo de busqueda y El parámetro value define un String con el numero de la propiedad
	 * @return Una lista de AttributeDataTable que contiene los valores de los predios asociados al numero. 
	 */	
	public List<AttributeDataTable> propertyAreaRange(String Search, String value);

	/**
	 * Metodo que obtiene los registros por numero de propietario
	 * @param Search, value El parámetro Search define un String que contiene el tipo de busqueda y El parámetro value define un String con el numero del propietario
	 * @return Una lista de AttributeDataTable que contiene los valores de los predios asociados al numero. 
	 */	
	public List<AttributeDataTable> ownerId(String identification, String value);

	/**
	 * Metodo que obtiene los registros por la direccion de la propiedad
	 * @param Search, value El parámetro Search define un String que contiene el tipo de busqueda y El parámetro value define un String con la direccion de la propiedad
	 * @return Una lista de AttributeDataTable que contiene los valores de los predios asociados a la direccion. 
	 */	
	public List<AttributeDataTable> ownerBasicDataDir(String Search, String value) throws IOException;

	/**
	 * Metodo que obtiene los registros por el nombre del propietario
	 * @param Search, value,value2 El parámetro Search define un String que contiene el tipo de busqueda, El parámetro value define un String con los nombres del propietario
	 * y El parámetro value2 define un String que contiene los apellidos del propietario
	 * @return Una lista de AttributeDataTable que contiene los valores de los predios asociados al nombre del propietario. 
	 */	
	public List<AttributeDataTable> ownerBasicDataNom(String Search, String value, String value2) throws IOException;

	/**
	 * Metodo que obtiene los registros por la razon social
	 * @param Search, value El parámetro Search define un String que contiene el tipo de busqueda y El parámetro value define un String con la razon social
	 * @return Una lista de AttributeDataTable que contiene los valores de los predios asociados a la razon social. 
	 */	
	public List<AttributeDataTable> ownerBasicDataNomNit(String Search, String value) throws IOException;

	/**
	 * Metodo que obtiene los registros por los numeros de las propiedades
	 * @param Search, value El parámetro Search define una lista de String que contiene el tipo de busqueda y El parámetro value define una lista de String con los numeros de las propiedades
	 * @return Una lista de AttributeDataTable que contiene los valores de los predios asociados a los numeros. 
	 */	
	public List<AttributeDataTable> propertyNumber(ArrayList<String> Search, ArrayList<String> value);

	/**
	 * Metodo que obtiene los registros por la lista de numeros de propietarios
	 * @param identification, value El parámetro identification define una lista de String que contiene el tipo de busqueda y El parámetro value define una lista de String con los numero de los propietarios
	 * @return Una lista de AttributeDataTable que contiene los valores de los predios asociados a los numeros. 
	 */	
	public List<AttributeDataTable> ownerId(ArrayList<String> identification, ArrayList<String> value);

	/**
	 * Metodo que obtiene los registros por lista de los nombres de los propietarios
	 * @param Search, value,value2 El parámetro Search define una lista de String que contiene el tipo de busqueda, El parámetro value define una lista String con los nombres de los propietarios
	 * y El parámetro value2 define una lista de String que contiene los apellidos de los propietarios
	 * @return Una lista de AttributeDataTable que contiene los valores de los predios asociados a los nombres de los propietarios. 
	 */	
	public List<AttributeDataTable> ownerBasicDataNom(ArrayList<String> Search, ArrayList<String> value,
			ArrayList<String> value2) throws IOException;

	/**
	 * Metodo que obtiene los registros por la identificacion del predio
	 * @param identification, value El parámetro identification define un String que contiene el tipo de busqueda y El parámetro value define un String con el numero de la propiedad
	 * @return Una lista de String que contiene los valores del predio asociado al numero. 
	 */	
	public List<String> datosPredio(String identification, String value);

	/**
	 * Metodo que obtiene una lista de los datos del predio consultados por poup
	 * @return Una lista de DatosPredioDataTable que contiene los valores del predio consultados por poup. 
	 */	
	public List<DatosPredioDataTable> datosPredioTable();

	/**
	 * Metodo que obtiene una lista de los datos juridicos del predio consultados por poup
	 * @return Una lista de DatosJuridicoDataTable que contiene los valores juridicos del predio consultados por poup. 
	 */	
	public List<DatosJuridicoDataTable> datosJuridicoTable();

	/**
	 * Metodo que obtiene una lista de las anotaciones del predio consultados por poup
	 * @return Una lista de AnotacionesDataTable que contiene las anotaciones del predio consultados por poup. 
	 */
	public List<AnotacionesDataTable> datosAnotacionesTable();

	/**
	 * Metodo que obtiene el departamento del predio consultado por poup
	 * @return Un String que contiene el departamento del predio consultado por poup. 
	 */	
	public String territorioD();

	/**
	 * Metodo que obtiene el municipio del predio consultado por poup
	 * @return Un String que contiene el municipio del predio consultado por poup. 
	 */
	public String territorioM();
}
