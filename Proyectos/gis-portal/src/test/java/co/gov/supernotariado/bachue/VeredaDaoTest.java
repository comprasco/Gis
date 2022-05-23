package co.gov.supernotariado.bachue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import co.gov.supernotariado.bachue.portalgeografico.model.Vereda;

public class VeredaDaoTest extends JPATest{

	@Test
	public void testGetAll_success() {
		
		List<Vereda> veredas = em.createNamedQuery("Vereda.getAll",Vereda.class).getResultList();
		assertEquals(1, veredas.size());
		
	}
	
	public void testGetObjectById_success() {
		
		Vereda vereda = em.find(Vereda.class,1);
		assertNotNull(vereda);
	}
}
