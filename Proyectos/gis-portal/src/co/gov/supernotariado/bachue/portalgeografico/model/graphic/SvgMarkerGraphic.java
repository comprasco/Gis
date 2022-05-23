package co.gov.supernotariado.bachue.portalgeografico.model.graphic;

import java.io.Serializable;

import co.gov.supernotariado.bachue.portalgeografico.model.graphic.Graphic;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.LineStyle;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.SvgMarkerPath;

import co.gov.supernotariado.bachue.portalgeografico.model.map.Coordinate;
import co.gov.supernotariado.bachue.portalgeografico.model.map.Extent;

/**
 * Los símbolos de marcador se utilizan para dibujar puntos y multipuntos en la
 * capa de gráficos.
 * 
 * @author csarmiento
 *
 */
public class SvgMarkerGraphic extends Graphic implements Serializable {
	private static final long serialVersionUID = 6341950928671862199L;
	/**
	 * Coordenadas del grafico
	 */
	private Coordinate coordinate = new Coordinate();
	/**
	 * Ruta del svg
	 */
	private String path = SvgMarkerPath.MARKER.getPath();
	/**
	 * Tamaño del svg
	 */
	private int size = 24;
	/**
	 * Angulo del grafico
	 */
	private int angle = 0;
	/**
	 * Color de relleno
	 */
	private String fillColor = "#FFFFFF";
	/**
	 * Transparencia del relleno
	 */
	private double fillOpacity = 1.0D;
	/**
	 * Tipo de linea del contorno
	 */
	private LineStyle outlineStyle = LineStyle.SOLID;
	/**
	 * Color de linea del contorno
	 */
	private String outlineColor = "#000000";
	/**
	 * Transparencia de linea del contorno
	 */
	private double outlineOpacity = 1.0D;
	/**
	 * Ancho de linea del contorno
	 */
	private double outlineWidth = 1.0D;
	/**
	 * Si es arrastable o no
	 */
	private boolean draggable = false;
	
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

	public Coordinate getCoordinate() {
		return this.coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getAngle() {
		return this.angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
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

	public boolean isDraggable() {
		return this.draggable;
	}

	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}


}
