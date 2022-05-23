/**
 * 
 */
package co.gov.supernotariado.bachue.portalgeografico.dao.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;
import co.gov.supernotariado.bachue.portalgeografico.dao.impl.GisParametrosDao;
import co.gov.supernotariado.bachue.portalgeografico.dao.interfaces.IGisParametrosDao;
import co.gov.supernotariado.bachue.portalgeografico.model.ConstantesBachue;

/**
 * Clase que ejecuta los CRUD de los parametros del portal gis.
 * @author datatools
 */
@Stateless
@LocalBean
public class GisParametrosDao implements IGisParametrosDao {
	private static final long serialVersionUID = 3537788836174019386L;
	private static final Logger log = LoggerFactory.getLogger(GisParametrosDao.class);

	@PersistenceContext(unitName = "BachueDS")
	private EntityManager em;

	/**
	 * Metodo que obtiene el valor de una parametro en especifico.
	 * @param component El parámetro component define un String que debe contener el nombre del parametro.
	 * @return Un String con el valor del parametro especificado. 
	 */
	@Override
	public String getValue(String component) {
		TypedQuery<String> query = em.createNamedQuery(Constants.CONSTANTESBACHUE_FINDVALUE, String.class);
		query.setParameter(Constants.IDCONST, component);
		String valor = query.getSingleResult();
		return valor;
	}

	/**
	 * Metodo que obtiene todos los valores de los parametros.
	 * @return Una lista ConstantesBachue con todos los valores de los parametros. 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ConstantesBachue> getAll() {
		log.info(Constants.MENSAJE_GISPARAMETRO_1);
		Query query = em.createNamedQuery(Constants.CONSTANTESBACHUE_FINDALL);
		return query.getResultList();
	}

	/**
	 * Metodo que obtiene el valor de una parametro en especifico.
	 * @param component El parámetro component define un String que debe contener el nombre del parametro.
	 * @return Un Arreglo de bytes que contiene la imagen o plantilla. 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public byte[] getBlob(String component) {
		System.out.println("entra en modo blob");
		byte[] x = null;
		Query query = em.createNamedQuery(Constants.CONSTANTESBACHUE_FINDALLWHERE); 
		query.setParameter(Constants.IDCONST, component);
        List<ConstantesBachue> resultados = query.getResultList();
        for(ConstantesBachue a : resultados) {
            x = a.getIMAGENES();
        }
        System.out.println("sale del modo blob");
		return x;
	}

}
