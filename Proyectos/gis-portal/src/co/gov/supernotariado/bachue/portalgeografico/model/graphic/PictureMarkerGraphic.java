package co.gov.supernotariado.bachue.portalgeografico.model.graphic;

import java.io.Serializable;

import co.gov.supernotariado.bachue.portalgeografico.model.graphic.Graphic;

import co.gov.supernotariado.bachue.portalgeografico.model.map.Coordinate;
import co.gov.supernotariado.bachue.portalgeografico.model.map.Extent;

/**
 * 
 * Esta clase representa Los símbolos de marcador se utilizan para dibujar
 * puntos y multipuntos en la capa de gráficos. PictureMarkerGraphic utiliza una
 * imagen como marcador.
 *
 */
public class PictureMarkerGraphic extends Graphic implements Serializable {
	private static final long serialVersionUID = -3427789515807059468L;
	
	
	/**
	 * Url de la imagen del grafico
	 */
	private String image;
	/**
	 * Ancho de la imagen
	 */
	private int width;
	/**
	 * Largo de la imagen
	 */
	private int height;
	/**
	 * Angulo de la imagen
	 */
	private int angle;
	/**
	 * Coordenadas del grafico
	 */
	private Coordinate coordinate = new Coordinate();
	/**
	 * Si el grafico es arrastable o no
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
	
	
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getAngle() {
		return this.angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public Coordinate getCoordinate() {
		return this.coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public boolean isDraggable() {
		return this.draggable;
	}

	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}

}
