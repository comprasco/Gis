package co.gov.supernotariado.bachue.portalgeografico.model.graphic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.gov.supernotariado.bachue.portalgeografico.model.graphic.Graphic;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.LineStyle;

import co.gov.supernotariado.bachue.portalgeografico.model.map.Coordinate;
import co.gov.supernotariado.bachue.portalgeografico.model.map.Extent;

/**
 * 
 * Esta clase representa un gráfico de una matriz de rutas donde cada ruta es
 * una matriz de puntos.
 *
 */
public class PolylineGraphic extends Graphic implements Serializable {
	private static final long serialVersionUID = 4788712880026002522L;
	/**
	 * Estilo de la linea
	 */
	private LineStyle style = LineStyle.SOLID;
	/**
	 * Color del polilinea
	 */
	private String color;
	/**
	 * Transparencia de polilinea
	 */
	private double opacity = 1.0D;
	/**
	 * Anncho de polilinea
	 */
	private int width = 1;
	/**
	 * Coordenadas de puntos que conforman la polilinea
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
	
	
	public LineStyle getStyle() {
		return this.style;
	}

	public void setStyle(LineStyle style) {
		this.style = style;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public double getOpacity() {
		return this.opacity;
	}

	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public List<Coordinate> getCoordinates() {
		return this.coordinates;
	}

	public void setCoordinates(List<Coordinate> coordinates) {
		this.coordinates = coordinates;
	}

}
