package co.gov.supernotariado.bachue.portalgeografico.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;
import co.gov.supernotariado.bachue.portalgeografico.utilities.ArcGisJsonUtilities;
import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONArray;
import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONException;
import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONObject;

/**
 * Esta clase es utilizada para obtener información de un layer de servicio rest
 * ArcGIS apartir de una url
 *
 */
public class ServiceLayerMetadataBuilder {

	/**
	 * Este método construye un entidad de tipo ServiceLayerMetadata a partir de la
	 * url de la capa
	 * 
	 * @param String serviceUrl url del servicio
	 * @param String id identificacion
	 * @return un ServiceLayerMetadata con la entidad
	 * @throws IOException
	 * @throws JSONException
	 */
	public ServiceLayerMetadata build(String serviceUrl, String id) throws IOException, JSONException {
		String url = ArcGisJsonUtilities.stripUrl(serviceUrl);

		ServiceLayerMetadata metadata = new ServiceLayerMetadata();
		metadata.setUrl(url);
		metadata.setId(id);

		String json = ArcGisJsonUtilities.executeJsonQuery(url);

		populate(metadata, json);

		return metadata;
	}

	/**
	 * Este método construye un entidad de tipo ServiceLayerMetadata a partir de la
	 * url de la capa
	 * 
	 * @param ServiceLayerMetadata metadata entidad
	 * @param String json mensaje en formato json
	 * @throws IOException
	 * @throws JSONException
	 */
	private void populate(ServiceLayerMetadata metadata, String json) throws IOException, JSONException {
		JSONObject obj = new JSONObject(json);
		if (obj != null) {
			if (obj.has(Constants.NAME_LAYER_METADATA)) {
				metadata.setName(obj.getString(Constants.NAME_LAYER_METADATA));
			}
			if (obj.has(Constants.TYPE_LAYER_METADATA)) {
				metadata.setType(obj.getString(Constants.TYPE_LAYER_METADATA));
			}
			if (obj.has(Constants.GEOMETRYTYPE_LAYER_METADATA)) {
				metadata.setGeometryType(obj.getString(Constants.GEOMETRYTYPE_LAYER_METADATA));
			}
			if ((obj.has(Constants.FIELDS_LAYER_METADATA)) && (!obj.getString(Constants.FIELDS_LAYER_METADATA).equals(Constants.NULO))) {
				JSONArray fields = obj.getJSONArray(Constants.FIELDS_LAYER_METADATA);
				if (fields.length() > 0) {
					metadata.setFields(new ArrayList<>());
					for (int i = 0; i < fields.length(); i++) {
						JSONObject f = fields.getJSONObject(i);

						ServiceLayerFieldMetadata field = new ServiceLayerFieldMetadata();
						if (f.has(Constants.NAME_LAYER_METADATA)) {
							field.setName(f.getString(Constants.NAME_LAYER_METADATA));
						}
						if (f.has(Constants.TYPE_LAYER_METADATA)) {
							field.setType(f.getString(Constants.TYPE_LAYER_METADATA));
						}
						if (f.has(Constants.ALIAS_LAYER_METADATA)) {
							field.setAlias(f.getString(Constants.ALIAS_LAYER_METADATA));
						}
						if (f.has(Constants.LENGTH_LAYER_METADATA)) {
							field.setLength(f.getInt(Constants.LENGTH_LAYER_METADATA));
						}
						if ((f.has(Constants.DOMAIN_LAYER_METADATA)) && (!f.getString(Constants.DOMAIN_LAYER_METADATA).equals(Constants.NULO))) {
							JSONObject d = f.getJSONObject(Constants.DOMAIN_LAYER_METADATA);
							if (d.has(Constants.CODEDVALUES_LAYER_METADATA)) {
								field.setValues(new LinkedHashMap<>());

								JSONArray values = d.getJSONArray(Constants.CODEDVALUES_LAYER_METADATA);
								for (int j = 0; j < values.length(); j++) {
									JSONObject value = values.getJSONObject(j);

									field.getValues().put(value.getString(Constants.CODE_LAYER_METADATA), value.getString(Constants.NAME_LAYER_METADATA));
								}
							}
						}
						metadata.getFields().add(field);
					}
				}
			}
		}
	}
}