package co.gov.supernotariado.bachue.portalgeografico.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;
import co.gov.supernotariado.bachue.portalgeografico.model.Vereda;

/**
 * Clase que ejecuta los CRUD de los valores de la tabla Vereda.
 * @author datatools
 */
@Stateless
@LocalBean
public class VeredaDao implements Serializable{

	/**
	 * id serial estatico
	 */
	private static final long serialVersionUID = 1L;
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Metodo que obtiene todos los valores de la tabla vereda.
	 * @return Una lista Vereda con todos los valores de la tabla vereda. 
	 */
	public List<Vereda> getAll(){
		
		final TypedQuery<Vereda> query= em.createQuery(Constants.VEREDA_SELECT_1 + Vereda.class.getSimpleName() + Constants.VEREDA_ALIAS, Vereda.class);
		return query.getResultList();
	}
}
