package co.gov.supernotariado.bachue.portalgeografico.model.service;

import java.io.IOException;
import java.util.ArrayList;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;
import co.gov.supernotariado.bachue.portalgeografico.utilities.ArcGisJsonUtilities;
import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONArray;
import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONException;
import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONObject;

/**
 * Esta clase es utilizada para obtener información de un servicio rest ArcGIS
 * a partir de una url
 *
 */
public class ServiceMetadataBuilder {
	
	/**
	 * Este método construye un entidad de tipo ServiceMetadata a partir de
	 * la url del servicio
	 * 
	 * @param String serviceUrl url del servicio
	 * @param String name nombre
	 * @param String type tipo
	 * @return un ServiceMetadata con la entidad 
	 * @throws IOException
	 * @throws JSONException
	 */
	public ServiceMetadata build(String serviceUrl, String name, String type) throws IOException, JSONException {
		String url = ArcGisJsonUtilities.stripUrl(serviceUrl);

		ServiceMetadata metadata = new ServiceMetadata();
		metadata.setUrl(url);
		metadata.setName(name);
		metadata.setType(type);

		String json = ArcGisJsonUtilities.executeJsonQuery(url);

		populate(metadata, json);

		return metadata;
	}

	/**
	 * Este método construye un entidad de tipo ServiceMetadata a partir de
	 * la url del servicio y un json
	 * 
	 * @param ServiceMetadata metadata
	 * @param String json mensaje en formato json
	 * @throws IOException
	 * @throws JSONException
	 */
	private void populate(ServiceMetadata metadata, String json) throws IOException, JSONException {
		JSONObject obj = new JSONObject(json);
		if (obj != null) {
			if (obj.has(Constants.CURRENTV_METADATA)) {
				metadata.setVersion(obj.getString(Constants.CURRENTV_METADATA));
			}
			if (obj.has(Constants.SERVICED_METADATA)) {
				metadata.setDescription(obj.getString(Constants.SERVICED_METADATA));
			}
			if (obj.has(Constants.LAYERS_METADATA)) {
				ServiceLayerMetadataBuilder builder = new ServiceLayerMetadataBuilder();

				JSONArray layers = obj.getJSONArray(Constants.LAYERS_METADATA);
				if (layers.length() > 0) {
					metadata.setLayers(new ArrayList<>());
					for (int i = 0; i < layers.length(); i++) {
						JSONObject layer = layers.getJSONObject(i);
						String id = layer.getString(Constants.ID_METADATA);

						String url = String.format("%s/%s", new Object[] { metadata.getUrl(), id });

						metadata.getLayers().add(builder.build(url, id));
					}
				}
			}
		}
	}
}
