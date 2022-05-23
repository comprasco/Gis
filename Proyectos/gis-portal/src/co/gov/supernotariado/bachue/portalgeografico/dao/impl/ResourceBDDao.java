package co.gov.supernotariado.bachue.portalgeografico.dao.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;
import co.gov.supernotariado.bachue.portalgeografico.dao.impl.ResourceBDDao;
import co.gov.supernotariado.bachue.portalgeografico.dao.interfaces.IResourceBD;
import co.gov.supernotariado.bachue.portalgeografico.model.Departamento;
import co.gov.supernotariado.bachue.portalgeografico.model.Municipios;

/**
 * Clase que ejecuta los CRUD de recursos secundarios para el funcionamiento del portal gis.
 * @author datatools
 */
@Stateless
@LocalBean
public class ResourceBDDao implements IResourceBD {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(ResourceBDDao.class);
	
	
	@PersistenceContext(unitName="tierrasDS")
	private EntityManager em;
	

	
	/**
	 * Metodo que obtiene todos los departamentos.
	 * @return Una lista Departamento con todos los departamentos. 
	 */
	@Override
	public List<Departamento> getAllDepto() {
		log.info(Constants.MENSAJE_RESOURCEBD_1);
		TypedQuery<Departamento> query = em.createNamedQuery(Constants.DEPARTAMENTO_FINDALL, Departamento.class);
		return query.getResultList();
	}

	/**
	 * Metodo que obtiene los municipios de un departamento en especifico.
	 * @param cod_depto El parámetro cod_depto define un String que debe contener el codigo de un departamento.
	 * @return Una lista de Municipios del departamento especificado. 
	 */
	@Override
	public List<Municipios> municipio(String cod_depto) {
		TypedQuery<Municipios> query = em.createNamedQuery(Constants.MUNICIPIOS_FINDALLVALUE, Municipios.class);
		query.setParameter(Constants.COD_DEPTO, cod_depto);
		return query.getResultList();
	}

}
