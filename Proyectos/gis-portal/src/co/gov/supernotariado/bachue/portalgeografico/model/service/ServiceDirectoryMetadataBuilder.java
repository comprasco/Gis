package co.gov.supernotariado.bachue.portalgeografico.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import co.gov.supernotariado.bachue.portalgeografico.utilities.ArcGisJsonUtilities;
import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONException;

/**
 * Esta clase es utilizada para obtener información de un servicio rest ArcGIS
 * a partir de una url
 *
 */
public class ServiceDirectoryMetadataBuilder {
	private static final Logger log = LoggerFactory.getLogger(ServiceDirectoryMetadataBuilder.class);

	/**
	 * Este método construye un entidad de tipo ServiceDirectoryMetadata a partir de
	 * la url del servicio
	 * 
	 * @param String serviceUrl url del servicio
	 * @return un ServiceDirectoryMetadata con los datos metadata
	 * @throws IOException
	 * @throws JSONException
	 */
	public ServiceDirectoryMetadata build(String serviceUrl) throws IOException, JSONException {
		String url = ArcGisJsonUtilities.stripUrl(serviceUrl);

		ServiceDirectoryMetadata metadata = new ServiceDirectoryMetadata();

		String json = ArcGisJsonUtilities.executeJsonQuery(url);

		metadata = populateWithGson(json, url);

		return metadata;
	}

	/**
	 * Este método construye un entidad de tipo ServiceDirectoryMetadata a partir de
	 * la url del servicio y un json
	 * 
	 * @param String json mensaje en formato json
	 * @param String url url 
	 * @return un ServiceDirectoryMetadata con los datos metadata
	 */
	private ServiceDirectoryMetadata populateWithGson(String json, String url) {
		final String remplazar = ".*\\/";
		Gson gson = new Gson();
		ServiceDirectoryMetadata metadata = gson.fromJson(json, ServiceDirectoryMetadata.class);
		metadata.setUrl(url);
		List<ServiceMetadata> services = new ArrayList<ServiceMetadata>();
		for (ServiceMetadata service : metadata.getServices()) {
			String name = service.getName();
			String type = service.getType();

			name = name.replaceFirst(remplazar, "");

			String urlServices = String.format("%s/%s/%s", new Object[] { metadata.getUrl(), name, type });
			try {
				String jsonServices = ArcGisJsonUtilities.executeJsonQuery(urlServices);

				service = gson.fromJson(jsonServices, ServiceMetadata.class);
				service.setUrl(urlServices);

				List<ServiceLayerMetadata> layers = new ArrayList<ServiceLayerMetadata>();
				for (ServiceLayerMetadata layerMetadata : service.getLayers()) {
					String id = layerMetadata.getId();
					String parentLayerId = layerMetadata.getParentLayerId();
					String urlLayer = String.format("%s/%s", new Object[] { urlServices, id });
					String jsonLayer = ArcGisJsonUtilities.executeJsonQuery(urlLayer);
					layerMetadata = gson.fromJson(jsonLayer, ServiceLayerMetadata.class);
					layerMetadata.setUrl(urlLayer);
					layerMetadata.setParentLayerId(parentLayerId);
					layers.add(layerMetadata);

				}
				service.setLayers(layers);
				services.add(service);

			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
		metadata.setServices(services);
		// System.out.println("eureka");
		return metadata;
	}
}
