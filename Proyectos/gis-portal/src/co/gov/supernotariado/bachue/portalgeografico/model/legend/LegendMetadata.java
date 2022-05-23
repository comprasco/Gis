package co.gov.supernotariado.bachue.portalgeografico.model.legend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.gov.supernotariado.bachue.portalgeografico.model.legend.LegendLayerSymbolMetadata;

/**
 * Esta clase construye información de un servicio de leyenda
 *
 */
public class LegendMetadata implements Serializable {
	private static final long serialVersionUID = -2908703969567397577L;
	
	
	/**
	 * Variable serviceUrl
	 * String que contiene la url del servicio.
	 */
	private String serviceUrl;
	/**
	 * Variable layers
	 * Lista de LegendLayerMetadata que contiene la informacion de las capas.
	 */
	private List<LegendLayerMetadata> layers = new ArrayList<>();

	
	/**
	 * Buscar una capa de la leyenda por ID
	 * 
	 * @param int layerId El id de la capa
	 * @return un LegendLayerMetadata de la capa
	 */
	public LegendLayerMetadata findLayer(int layerId) {
		LegendLayerMetadata layer = null;
		if (this.layers != null) {
			for (LegendLayerMetadata l : this.layers) {
				if (l.getLayerId() == layerId) {
					layer = l;
					break;
				}
			}
		}
		return layer;
	}

	/**
	 * Buscar una simbolo de la leyenda por ID y nombre del simbolo
	 * 
	 * @param int layerId El id de la capa
	 * @param String symbolLabel La etiqueta de los simbolos
	 * @return un LegendLayerMetadata del simbolo
	 */
	public LegendLayerSymbolMetadata findSymbol(int layerId, String symbolLabel) {
		LegendLayerSymbolMetadata symbol = null;

		LegendLayerMetadata layer = findLayer(layerId);
		if (layer != null) {
			if (layer.getSymbology() != null) {
				for (LegendLayerSymbolMetadata s : layer.getSymbology()) {
					if (symbolLabel.equals(s.getLabel())) {
						symbol = s;
						break;
					}
				}
			}
		}
		return symbol;
	}

	public String getServiceUrl() {
		return this.serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public List<LegendLayerMetadata> getLayers() {
		return this.layers;
	}

	public void setLayers(List<LegendLayerMetadata> layers) {
		this.layers = layers;
	}
}
