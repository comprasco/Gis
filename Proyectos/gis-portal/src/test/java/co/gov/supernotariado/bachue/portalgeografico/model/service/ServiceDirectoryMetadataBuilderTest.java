package co.gov.supernotariado.bachue.portalgeografico.model.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.supernotariado.bachue.portalgeografico.beans.MapBean;
import co.gov.supernotariado.bachue.portalgeografico.model.service.ServiceDirectoryMetadata;
import co.gov.supernotariado.bachue.portalgeografico.model.service.ServiceDirectoryMetadataBuilder;
import co.gov.supernotariado.bachue.portalgeografico.utilities.JSFUtilities;
import junit.framework.TestCase;

public class ServiceDirectoryMetadataBuilderTest extends TestCase {
	
	private static final Logger log = LoggerFactory.getLogger(ServiceDirectoryMetadataBuilderTest.class);

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testBuild() {
		
		String url =  "http://168.62.168.96:6080/arcgis/rest/services/san_vicente/";
		ServiceDirectoryMetadata serviceDirectory = null;
		try
		{
			serviceDirectory = null;
			ServiceDirectoryMetadataBuilder builder = new ServiceDirectoryMetadataBuilder();
			serviceDirectory = builder.build(url);
		}
		catch (Exception e)
		{
			log.error(e.getMessage());
			JSFUtilities.addErrorMessage(e.getMessage());
		}
		
		
		String version = serviceDirectory.getCurrentVersion();
		int size = serviceDirectory.getServices().size();
		String mapName = serviceDirectory.getServices().get(0).getMapName();
		String layerName = serviceDirectory.getServices().get(0).getLayers().get(1).getName();
		
		assertEquals(version,"10.51");
		assertEquals(size, 1);
		assertEquals(mapName, "San Vicente");
		assertEquals(layerName, "U_CONSTRUCCION");
		
		
	}

}
