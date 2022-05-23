package co.gov.supernotariado.bachue.portalgeografico.model.graphic;

import java.io.Serializable;

import co.gov.supernotariado.bachue.portalgeografico.model.graphic.FillStyle;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.Graphic;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.LineStyle;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.MeasurementUnit;

import co.gov.supernotariado.bachue.portalgeografico.model.map.Coordinate;
import co.gov.supernotariado.bachue.portalgeografico.model.map.Extent;

/**
 * 
 * Esta clase representa un gráfico de un un círculo creado por un punto central
 * especificado. Este punto se puede proporcionar como un objeto de
 * esri/geometry/Point o una matriz de valores de latitud / longitud.
 *
 */
public class CircleGraphic extends Graphic implements Serializable {
	private static final long serialVersionUID = -3556816446904137841L;
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
	private Coordinate coordinate = new Coordinate();
	/**
	 * Radio del grafico
	 */
	private int radius;
	/**
	 * Tipo de medida
	 */
	private MeasurementUnit radiusUnit = MeasurementUnit.METERS;
	/**
	 * Curvatura en puntos
	 */
	private int curvePoints = 60;
	
	
	/**
	 * Obtiene el extent del grafico a partir de una coordenada
	 * 
	 * @return Extent devuelve el extent
	 */
	public Extent getExtent() {
		Extent e = null;
		if (this.coordinate != null) {
			e = new Extent(this.coordinate);
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

	public Coordinate getCoordinate() {
		return this.coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public int getRadius() {
		return this.radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public MeasurementUnit getRadiusUnit() {
		return this.radiusUnit;
	}

	public void setRadiusUnit(MeasurementUnit radiusUnit) {
		this.radiusUnit = radiusUnit;
	}

	public int getCurvePoints() {
		return this.curvePoints;
	}

	public void setCurvePoints(int curvePoints) {
		this.curvePoints = curvePoints;
	}

}
