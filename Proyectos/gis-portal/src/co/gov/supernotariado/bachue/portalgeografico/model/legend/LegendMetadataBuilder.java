package co.gov.supernotariado.bachue.portalgeografico.model.legend;

import java.io.IOException;

import co.gov.supernotariado.bachue.portalgeografico.model.legend.LegendLayerMetadata;
import co.gov.supernotariado.bachue.portalgeografico.model.legend.LegendLayerSymbolMetadata;
import co.gov.supernotariado.bachue.portalgeografico.model.legend.LegendMetadata;

import co.gov.supernotariado.bachue.portalgeografico.utilities.ArcGisJsonUtilities;
import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONArray;
import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONException;
import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONObject;

/**
 * Esta clase construye información de un atributo del servicio de leyenda
 *
 */
public class LegendMetadataBuilder {
	
	
	/**
	 * A través de una url se lee el json de leyenda servicio de ArcGIS
	 * 
	 * @param String mapServiceUrl ulr del mapa del servicio
	 * @return un LegendMetadatade la legenda
	 * @throws IOException
	 * @throws JSONException
	 */
	public LegendMetadata build(String mapServiceUrl) throws IOException, JSONException {
		String url = ArcGisJsonUtilities.stripUrl(mapServiceUrl);

		LegendMetadata legend = new LegendMetadata();
		legend.setServiceUrl(url);

		String json = ArcGisJsonUtilities.executeJsonLegendQuery(url);

		populate(url, legend, json);

		return legend;
	}

	/**
	 * Lee todos los layers de un servicio para construir la leyenda
	 * 
	 * @param String mapServiceUrl url del mapa del servicio
	 * @param LegendMetadata legend legenda
	 * @param String json mensaje en formato json
	 * @throws JSONException
	 */
	private void populate(String mapServiceUrl, LegendMetadata legend, String json) throws JSONException {
		JSONObject obj = new JSONObject(json);

		JSONArray layers = obj.getJSONArray("layers");
		if (layers != null) {
			for (int i = 0; i < layers.length(); i++) {
				JSONObject layer = layers.getJSONObject(i);

				LegendLayerMetadata legendLayer = new LegendLayerMetadata();
				if (layer.has("layerId")) {
					legendLayer.setLayerId(Integer.valueOf(layer.getString("layerId")).intValue());
				}
				if (layer.has("layerName")) {
					legendLayer.setLayerName(layer.getString("layerName"));
				}
				if (layer.has("layerType")) {
					legendLayer.setLayerType(layer.getString("layerType"));
				}
				if (layer.has("minScale")) {
					legendLayer.setMinScale(Integer.valueOf(layer.getString("minScale")).intValue());
				}
				if (layer.has("maxScale")) {
					legendLayer.setMaxScale(Integer.valueOf(layer.getString("maxScale")).intValue());
				}
				legend.getLayers().add(legendLayer);

				JSONArray symbology = layer.getJSONArray("legend");
				if (symbology != null) {
					for (int j = 0; j < symbology.length(); j++) {
						JSONObject symbol = symbology.getJSONObject(j);

						LegendLayerSymbolMetadata lls = new LegendLayerSymbolMetadata();
						if (symbol.has("label")) {
							lls.setLabel(symbol.getString("label"));
						}
						if (symbol.has("url")) {
							lls.setUrl(String.format("%s/%s/images/%s", new Object[] { mapServiceUrl,
									Integer.valueOf(legendLayer.getLayerId()), symbol.getString("url") }));
						}
						if (symbol.has("imageData")) {
							lls.setImageData(symbol.getString("imageData"));
						}
						if (symbol.has("contentType")) {
							lls.setContentType(symbol.getString("contentType"));
						}
						if (symbol.has("height")) {
							lls.setHeight(Integer.valueOf(symbol.getString("height")).intValue());
						}
						if (symbol.has("width")) {
							lls.setWidth(Integer.valueOf(symbol.getString("width")).intValue());
						}
						legendLayer.getSymbology().add(lls);
					}
				}
			}
		}
	}
}
