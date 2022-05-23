package co.gov.supernotariado.bachue.portalgeografico.model.graphic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.gov.supernotariado.bachue.portalgeografico.model.graphic.FillStyle;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.Graphic;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.LineStyle;

import co.gov.supernotariado.bachue.portalgeografico.model.map.Coordinate;
import co.gov.supernotariado.bachue.portalgeografico.model.map.Extent;

/**
 * 
 * Esta clase representa un gráfico de un conjunto de anillos donde cada anillo
 * es un conjunto de puntos. El primer y el último punto de un anillo deben ser
 * iguales.
 *
 */
public class PolygonGraphic extends Graphic implements Serializable {
	private static final long serialVersionUID = -5546406028501751473L;
	/**
	 * Url de la imagen del grafico
	 */
	private String imageUrl;
	/**
	 * Ancho de la imagen
	 */
	private int imageWidth;
	/**
	 * Largo de la imagen
	 */
	private int imageHeight;
	/**
	 * Estilo de llenado
	 */
	private FillStyle fillStyle = FillStyle.SOLID;
	/**
	 * Color de llenado
	 */
	private String fillColor;
	/**
	 * Transparencia de llenado
	 */
	private double fillOpacity = 1.0D;
	/**
	 * Estilo de la linea del borde
	 */
	private LineStyle outlineStyle = LineStyle.SOLID;
	/**
	 * Color de la linea del borde
	 */
	private String outlineColor;
	/**
	 * Transparencia de la linea del borde
	 */
	private double outlineOpacity = 1.0D;
	/**
	 * Ancho de la linea del borde
	 */
	private double outlineWidth = 1.0D;
	/**
	 * Coordenadas del grafico
	 */
	private List<Coordinate> coordinates = new ArrayList<>();

	
	/**
	 * Obtiene el extent del grafico a partir de una coordenada
	 * 
	 * @return Extent devuelve el extent
	 */
	public Extent getExtent() {
		Extent e = null;
		if ((this.coordinates != null) && (!this.coordinates.isEmpty())) {
			e = Extent.union(this.coordinates);
		}
		return e;
	}
	
	
	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getImageWidth() {
		return this.imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return this.imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public FillStyle getFillStyle() {
		return this.fillStyle;
	}

	public void setFillStyle(FillStyle fillStyle) {
		this.fillStyle = fillStyle;
	}

	public String getFillColor() {
		return this.fillColor;
	}

	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}

	public double getFillOpacity() {
		return this.fillOpacity;
	}

	public void setFillOpacity(double fillOpacity) {
		this.fillOpacity = fillOpacity;
	}

	public LineStyle getOutlineStyle() {
		return this.outlineStyle;
	}

	public void setOutlineStyle(LineStyle outlineStyle) {
		this.outlineStyle = outlineStyle;
	}

	public String getOutlineColor() {
		return this.outlineColor;
	}

	public void setOutlineColor(String outlineColor) {
		this.outlineColor = outlineColor;
	}

	public double getOutlineOpacity() {
		return this.outlineOpacity;
	}

	public void setOutlineOpacity(double outlineOpacity) {
		this.outlineOpacity = outlineOpacity;
	}

	public double getOutlineWidth() {
		return this.outlineWidth;
	}

	public void setOutlineWidth(double outlineWidth) {
		this.outlineWidth = outlineWidth;
	}

	public List<Coordinate> getCoordinates() {
		return this.coordinates;
	}

	public void setCoordinates(List<Coordinate> coordinates) {
		this.coordinates = coordinates;
	}

}
