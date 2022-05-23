package co.gov.supernotariado.bachue.portalgeografico.model.legend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase almacena información de un servicio de leyenda
 *
 */
public class LegendLayerMetadata implements Serializable {
	private static final long serialVersionUID = 5515197571285862093L;
	
	/**
	 * Variable layerId
	 * int que contiene el id de la capa.
	 */
	private int layerId;
	/**
	 * Variable layerName
	 * String que contiene el nombre de la capa.
	 */
	private String layerName;
	/**
	 * Variable layerType
	 * String que contiene el tipo de capa.
	 */
	private String layerType;
	/**
	 * Variable minScale
	 * int que contiene la escala minima.
	 */
	private int minScale;
	/**
	 * Variable maxScale
	 * int que contiene la escala maxima.
	 */
	private int maxScale;
	/**
	 * Variable layerName
	 * Lista de LegendLayerSymbolMetadata que contiene la simbologia de las capas.
	 */
	private List<LegendLayerSymbolMetadata> symbology = new ArrayList<>();

	
	public int getLayerId() {
		return this.layerId;
	}

	public void setLayerId(int layerId) {
		this.layerId = layerId;
	}

	public String getLayerName() {
		return this.layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	public String getLayerType() {
		return this.layerType;
	}

	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}

	public int getMinScale() {
		return this.minScale;
	}

	public void setMinScale(int minScale) {
		this.minScale = minScale;
	}

	public int getMaxScale() {
		return this.maxScale;
	}

	public void setMaxScale(int maxScale) {
		this.maxScale = maxScale;
	}

	public List<LegendLayerSymbolMetadata> getSymbology() {
		return this.symbology;
	}

	public void setSymbology(List<LegendLayerSymbolMetadata> symbology) {
		this.symbology = symbology;
	}
}
