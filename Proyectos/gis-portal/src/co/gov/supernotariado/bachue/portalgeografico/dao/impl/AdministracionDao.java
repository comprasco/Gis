package co.gov.supernotariado.bachue.portalgeografico.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;
import co.gov.supernotariado.bachue.portalgeografico.dao.interfaces.IAdministracionDao;
import co.gov.supernotariado.bachue.portalgeografico.model.AdmonRegistros;
import co.gov.supernotariado.bachue.portalgeografico.model.BusquedaBus;
import co.gov.supernotariado.bachue.portalgeografico.model.DataTable;
import co.gov.supernotariado.bachue.portalgeografico.model.ListaValores;

/**
 * Clase que ejecuta los CRUD de administracion del portal gis.
 * @author datatools
 */
@Stateless
@LocalBean
public class AdministracionDao implements IAdministracionDao {

	@PersistenceContext(unitName = "tierrasDS")
	private EntityManager em;

	private static final Logger log = LoggerFactory.getLogger(ResourceBDDao.class);

	/**
	 * Metodo que obtiene los valores segun el grupo de lista
	 * @param Search El parámetro Search define un int que debe contener el codigo de una lista de administracion
	 * @return Una lista de DataTable que contiene los valores de un grupo de lista definido. 
	 */	
	@Override
	public List<DataTable> tipoBusqueda(int Search) {
		log.info(Constants.MENSAJE_ADMON_1);
		int i = 1;
		List<DataTable> dataTable;
		dataTable = new ArrayList<DataTable>();
		TypedQuery<DataTable> query = em.createNamedQuery(Constants.LISTAVALORES_FINDTWO, DataTable.class);
		query.setParameter(Constants.TLISTA, Search);
		List<DataTable> listOfSimpleEntities = query.getResultList();
		for (DataTable entity : listOfSimpleEntities) {
			dataTable.add(new DataTable(i, entity.getValue(), entity.getState(), entity.getList(), entity.getCodBus(),
					entity.getBusquedaB()));
			i++;
		}
		return dataTable;
	}

	/**
	 * Metodo que obtiene los valores activos segun el grupo de lista
	 * @param Search El parámetro Search define un int que debe contener el codigo de una lista de administracion
	 * @return Una lista de DataTable que contiene los valores activos de un grupo de lista definido. 
	 */	
	@Override
	public List<DataTable> tipoBusquedaA(int Search) {
		int i = 1;
		List<DataTable> dataTable;
		dataTable = new ArrayList<DataTable>();
		TypedQuery<DataTable> query = em.createNamedQuery(Constants.LISTAVALORES_FINDTWOA, DataTable.class);
		query.setParameter(Constants.TLISTA, Search);
		List<DataTable> listOfSimpleEntities = query.getResultList();
		for (DataTable entity : listOfSimpleEntities) {
			dataTable.add(new DataTable(i, entity.getValue(), entity.getState(), entity.getList(), entity.getCodBus(),
					entity.getBusquedaB()));
			i++;
		}
		return dataTable;
	}

	/**
	 * Metodo que obtiene el codigo de bus definido en las listas segun el grupo de lista y el nombre del valor.
	 * @param lista, Search El parámetro lista define un int que debe contener el codigo de una lista de administracion y El parámetro Search define un String que debe contener nombre del valor. 
	 * @return Un String que contiene el codigo de bus segun los parametros recibidos. 
	 */	
	@Override
	public String tipoBusquedaBus(int lista, String Search) {
		TypedQuery<String> query = em.createNamedQuery(Constants.LISTAVALORES_FINDVALUE, String.class);
		query.setParameter(Constants.TLISTA, lista);
		query.setParameter(Constants.TVALOR, Search);
		String valor = query.getSingleResult();
		return valor;
	}

	/**
	 * Metodo que obtiene una lista de obtiene los valores de busqueda definidos en la aplicacion. 
	 * @return Una lista de BusquedaBus String que contiene los valores de busqueda definidos en la aplicacion. 
	 */	
	@Override
	public List<BusquedaBus> BusquedaB() {
		List<BusquedaBus> dataTable;
		dataTable = new ArrayList<BusquedaBus>();
		TypedQuery<BusquedaBus> query = em.createNamedQuery(Constants.BUSQUEDABUS_FINDALL, BusquedaBus.class);
		dataTable = query.getResultList();
		return dataTable;
	}

	/**
	 * Metodo que obtiene la lista de valores activos segun el id del grupo de lista y valor definidos.
	 * @param Search, valor El parámetro Search define un int que debe contener el codigo de una lista de administracion y El parámetro valor define un String que debe contener nombre del valor. 
	 * @return Una lista de ListaValores que contiene los  valores activos segun los valores recibidos. 
	 */	
	@Override
	public List<ListaValores> tBusquedaA(int Search, String valor) {
		List<ListaValores> dataTable;
		dataTable = new ArrayList<ListaValores>();
		TypedQuery<ListaValores> query = em.createNamedQuery(Constants.LISTAVALORES_FINDALLVALUEA, ListaValores.class);
		query.setParameter(Constants.TVALOR, valor);
		query.setParameter(Constants.TLISTA, Search);
		dataTable = query.getResultList();
		return dataTable;
	}

	/**
	 * Metodo que obtiene la cantidad maxima de una consulta.
	 * @param registro El parámetro registro define un String que debe contener el nombre de la consulta. 
	 * @return Un int que contiene el valor maximo segun el nombre de la consulta recibida. 
	 */	
	@Override
	public int tBusquedaARegistro(String registro) {
		TypedQuery<Integer> query = em.createNamedQuery(Constants.ADMONREGISTROS_FINDVALUE, Integer.class);
		query.setParameter(Constants.TREGISTRO, registro);
		int result = query.getSingleResult();
		return result;
	}

	/**
	 * Metodo que obtiene la lista de valores activos segun el id del grupo de lista.
	 * @param Search El parámetro Search define un int que debe contener el codigo de una lista de administracion. 
	 * @return Una lista de ListaValores que contiene los  valores activos segun el codigo de lista recibido. 
	 */	
	@Override
	public List<ListaValores> tBusquedaMasivaA(int Search) {
		List<ListaValores> dataTable;
		dataTable = new ArrayList<ListaValores>();
		TypedQuery<ListaValores> query = em.createNamedQuery(Constants.LISTAVALORES_FINDVALUEA, ListaValores.class);
		query.setParameter(Constants.TLISTA, Search);
		dataTable = query.getResultList();
		return dataTable;
	}

	/**
	 * Metodo que obtiene la lista de valores segun el id del grupo de lista y valor definidos.
	 * @param Search, codId El parámetro Search define un int que debe contener el codigo de una lista de administracion y El parámetro codId define un String que debe contener nombre del valor. 
	 * @return Una lista de DataTable que contiene los  valores de un grupo de lista definido segun los valores recibidos. 
	 */	
	@Override
	public List<DataTable> tBusquedaIdeBus(int Search, String codId) {
		int i = 1;
		List<DataTable> dataTable;
		dataTable = new ArrayList<DataTable>();
		TypedQuery<DataTable> query = em.createNamedQuery(Constants.LISTAVALORES_FINDTWOAMAX, DataTable.class);
		query.setParameter(Constants.TLISTA, Search);
		query.setParameter(Constants.TDESCRIP, codId);
		List<DataTable> listOfSimpleEntities = query.getResultList();
		for (DataTable entity : listOfSimpleEntities) {
			dataTable.add(new DataTable(i, entity.getValue(), entity.getState(), entity.getList(), entity.getCodBus(),
					entity.getBusquedaB()));
			i++;
		}
		return dataTable;
	}

	/**
	 * Metodo que inserta un nuevos valores de lista.
	 * @param lista, valor, estado, codBus, busqBus,usuario, fecha, ip 
	 * El parámetro lista define un int que debe contener el codigo de una lista de administracion, El parámetro valor define un String que debe contener nombre del nuevo valor de lista
	 * El parámetro estado define un String que define el estado del nuevo valor, El parámetro codBus define un String que contiene el codigo de busqueda de datos por ws si aplica 
	 * El parámetro busqBus define un String que define el modo de busqueda mediante ws si aplica, El parámetro usuario define un String que contiene el nombre de usuario que inicia sesion
	 * El parámetro fecha define un Date que define la fecha actual de insercion y El parámetro ip define un String que contiene la ip de donde se inicio sesion.
	 */	
	@Override
	public void insertarAmon(int lista, String valor, String estado, String codBus, String busqBus,String usuario, Date fecha, String ip) {
		Query query = em.createNamedQuery(Constants.BUSQUEDABUS_FINDVALUE);
		query.setParameter(Constants.TDESCRIPCION, busqBus);
		int value = (int) query.getSingleResult();
		ListaValores lv = new ListaValores();
		lv.setTIPO_LISTA(lista);
		lv.setVALOR(valor);
		lv.setESTADO(estado);
		lv.setCOD_BUS(codBus);
		lv.setCOD_BUSQUEDA_BUS(value);
		lv.setID_USUARIO_CREACION(usuario);
		lv.setFECHA_CREACION(fecha);
		lv.setIP_CREACION(ip);
		lv.setID_USUARIO_MODIFICACION(usuario);
		lv.setFECHA_MODIFICACION(fecha);
		em.persist(lv);
	}

	/**
	 * Metodo que modifica los valores de un grupo de lista.
	 * @param lista, valor, estado, valorP, codBus, busqBus,usuario, fecha 
	 * El parámetro lista define un int que debe contener el codigo de una lista de administracion, El parámetro valor define un String que debe contener el nombre del valor a modificar
	 * El parámetro estado define un String que define el estado a modificar, El parámetro codBus define un String que contiene nuevo codigo de busqueda de datos por ws si aplica 
	 * El parámetro busqBus define un String que define el nuevo modo de busqueda mediante ws si aplica, El parámetro usuario define un String que contiene el nombre de usuario que inicia sesion
	 * El parámetro fechaMod define un Date que define la fecha actual de la modificacion y El parámetro valorP define un String que contiene el nuevo nombre del valor de la lista.
	 */
	@Override
	public void modificarAdmon(int lista, String valor, String estado, String valorP, String codBus, String busqBus,
			String usuario, Date fechaMod) {
		Query query = em.createNamedQuery(Constants.BUSQUEDABUS_FINDVALUE);
		query.setParameter(Constants.TDESCRIPCION, busqBus);
		int value = (int) query.getSingleResult();
		em.createNamedQuery(Constants.LISTAVALORES_CHANGEVALUE, ListaValores.class).setParameter(Constants.TVALOR, valor)
				.setParameter(Constants.TESTADO, estado).setParameter(Constants.TLISTA, lista).setParameter(Constants.TVALORP, valorP)
				.setParameter(Constants.USUMOD, usuario).setParameter(Constants.FECHAMOD, fechaMod).setParameter(Constants.CODBUS, codBus)
				.setParameter(Constants.TBUSQBUS, value).executeUpdate();
	}
	
	/**
	 * Metodo que elimina los valores de un grupo de lista.
	 * @param lista, valor El parámetro Search define un int que debe contener el codigo de una lista de administracion y El parámetro codId define un String que debe contener nombre del valor. 
	 */	
	@Override
	public void eliminarAdmon(int lista, String valor) {
		em.createNamedQuery(Constants.LISTAVALORES_DELETEVALUE, ListaValores.class).setParameter(Constants.TLISTA, lista)
				.setParameter(Constants.TVALORP, valor).executeUpdate();
	}

	/**
	 * Metodo que modifica los valores de todos los registros .
	 * @param registro, valor, usuario, fecha 
	 * El parámetro valor define un int que debe contener la cantidad maxima de la consulta, El parametro registro define un String que contiene el nombre de la consulta a modificar
	 * El parámetro usuario define un String que contiene el usuario activo en la sesion, El parámetro fecha define un Date que contiene la fecha actual de la modificacion. 
	 */
	@Override
	public void modificarRegistros(String registro, int valor, String usuario, Date fecha) {
		em.createNamedQuery(Constants.ADMONREGISTROS_CHANGEVALUE, AdmonRegistros.class).setParameter(Constants.CANTIDAD, valor)
				.setParameter(Constants.USUMOD, usuario).setParameter(Constants.FECHAMOD, fecha).setParameter(Constants.REGISTRO, registro)
				.executeUpdate();
	}

}
