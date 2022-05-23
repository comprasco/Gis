package co.gov.supernotariado.bachue.portalgeografico.dao.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.gov.supernotariado.bachue.portalgeografico.model.BusquedaBus;
import co.gov.supernotariado.bachue.portalgeografico.model.DataTable;
import co.gov.supernotariado.bachue.portalgeografico.model.ListaValores;

/**
 * Interfaz que contiene los metodos de administracion del portal gis.
 * @author datatools
 */
@Local
public interface IAdministracionDao {

	/**
	 * Metodo que obtiene los valores segun el grupo de lista
	 * @param Search El parámetro Search define un int que debe contener el codigo de una lista de administracion
	 * @return Una lista de DataTable que contiene los valores de un grupo de lista definido. 
	 */	
	public List<DataTable> tipoBusqueda(int Search);

	/**
	 * Metodo que obtiene los valores activos segun el grupo de lista
	 * @param Search El parámetro Search define un int que debe contener el codigo de una lista de administracion
	 * @return Una lista de DataTable que contiene los valores activos de un grupo de lista definido. 
	 */	
	public List<DataTable> tipoBusquedaA(int Search);

	/**
	 * Metodo que obtiene la lista de valores activos segun el id del grupo de lista y valor definidos.
	 * @param Search, valor El parámetro Search define un int que debe contener el codigo de una lista de administracion y El parámetro valor define un String que debe contener nombre del valor. 
	 * @return Una lista de ListaValores que contiene los  valores activos segun los valores recibidos. 
	 */	
	public List<ListaValores> tBusquedaA(int Search, String valor);

	/**
	 * Metodo que obtiene la lista de valores activos segun el id del grupo de lista.
	 * @param Search El parámetro Search define un int que debe contener el codigo de una lista de administracion. 
	 * @return Una lista de ListaValores que contiene los  valores activos segun el codigo de lista recibido. 
	 */	
	public List<ListaValores> tBusquedaMasivaA(int Search);

	/**
	 * Metodo que obtiene el codigo de bus definido en las listas segun el grupo de lista y el nombre del valor.
	 * @param lista, Search El parámetro lista define un int que debe contener el codigo de una lista de administracion y El parámetro Search define un String que debe contener nombre del valor. 
	 * @return Un String que contiene el codigo de bus segun los parametros recibidos. 
	 */	
	public String tipoBusquedaBus(int lista, String Search);

	/**
	 * Metodo que obtiene la cantidad maxima de una consulta.
	 * @param registro El parámetro registro define un String que debe contener el nombre de la consulta. 
	 * @return Un int que contiene el valor maximo segun el nombre de la consulta recibida. 
	 */	
	public int tBusquedaARegistro(String registro);

	/**
	 * Metodo que obtiene una lista de obtiene los valores de busqueda definidos en la aplicacion. 
	 * @return Una lista de BusquedaBus String que contiene los valores de busqueda definidos en la aplicacion. 
	 */	
	public List<BusquedaBus> BusquedaB();

	/**
	 * Metodo que obtiene la lista de valores segun el id del grupo de lista y valor definidos.
	 * @param Search, codId El parámetro Search define un int que debe contener el codigo de una lista de administracion y El parámetro codId define un String que debe contener nombre del valor. 
	 * @return Una lista de DataTable que contiene los  valores de un grupo de lista definido segun los valores recibidos. 
	 */	
	public List<DataTable> tBusquedaIdeBus(int Search, String codId);

	/**
	 * Metodo que inserta un nuevos valores de lista.
	 * @param lista, valor, estado, codBus, busqBus,usuario, fecha, ip 
	 * El parámetro lista define un int que debe contener el codigo de una lista de administracion, El parámetro valor define un String que debe contener nombre del nuevo valor de lista
	 * El parámetro estado define un String que define el estado del nuevo valor, El parámetro codBus define un String que contiene el codigo de busqueda de datos por ws si aplica 
	 * El parámetro busqBus define un String que define el modo de busqueda mediante ws si aplica, El parámetro usuario define un String que contiene el nombre de usuario que inicia sesion
	 * El parámetro fecha define un Date que define la fecha actual de insercion y El parámetro ip define un String que contiene la ip de donde se inicio sesion.
	 */	
	public void insertarAmon(int lista, String valor, String estado, String codBus, String busqBus, String usuario,
			Date fecha, String ip);

	/**
	 * Metodo que modifica los valores de un grupo de lista.
	 * @param lista, valor, estado, valorP, codBus, busqBus,usuario, fecha 
	 * El parámetro lista define un int que debe contener el codigo de una lista de administracion, El parámetro valor define un String que debe contener el nombre del valor a modificar
	 * El parámetro estado define un String que define el estado a modificar, El parámetro codBus define un String que contiene nuevo codigo de busqueda de datos por ws si aplica 
	 * El parámetro busqBus define un String que define el nuevo modo de busqueda mediante ws si aplica, El parámetro usuario define un String que contiene el nombre de usuario que inicia sesion
	 * El parámetro fechaMod define un Date que define la fecha actual de la modificacion y El parámetro valorP define un String que contiene el nuevo nombre del valor de la lista.
	 */
	public void modificarAdmon(int lista, String valor, String estado, String valorP, String codBus, String busqBus,
			String usuario, Date fechaMod);

	/**
	 * Metodo que elimina los valores de un grupo de lista.
	 * @param lista, valor El parámetro Search define un int que debe contener el codigo de una lista de administracion y El parámetro codId define un String que debe contener nombre del valor. 
	 */	
	public void eliminarAdmon(int lista, String valor);

	/**
	 * Metodo que modifica los valores de todos los registros .
	 * @param registro, valor, usuario, fecha 
	 * El parámetro valor define un int que debe contener la cantidad maxima de la consulta, El parametro registro define un String que contiene el nombre de la consulta a modificar
	 * El parámetro usuario define un String que contiene el usuario activo en la sesion, El parámetro fecha define un Date que contiene la fecha actual de la modificacion. 
	 */
	public void modificarRegistros(String registro, int valor, String usuario, Date fecha);

}
