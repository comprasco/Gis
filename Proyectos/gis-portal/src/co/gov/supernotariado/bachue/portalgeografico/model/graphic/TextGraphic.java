package co.gov.supernotariado.bachue.portalgeografico.model.graphic;

import java.io.Serializable;

import co.gov.supernotariado.bachue.portalgeografico.model.graphic.Graphic;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.TextHorizontalAlignment;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.TextVerticalAlignment;

import co.gov.supernotariado.bachue.portalgeografico.model.map.Coordinate;
import co.gov.supernotariado.bachue.portalgeografico.model.map.Extent;

/**
 * Los símbolos de texto se usan para agregar texto en la capa de gráficos. Los
 * símbolos de texto también se pueden usar para definir la propiedad de símbolo
 * de Gráfico si el tipo de geometría es Punto o Multipunto.
 * 
 * @author csarmiento
 *
 */
public class TextGraphic extends Graphic implements Serializable {
	private static final long serialVersionUID = 4049941146776450907L;
	/**
	 * Coordenada del texto
	 */
	private Coordinate coordinate = new Coordinate();
	/**
	 * Contenido del texto del grafico
	 */
	private String text;
	/**
	 * Familia de la fuente
	 */
	private String fontFamily;
	/**
	 * Tamaño de la fuente
	 */
	private String fontSize;
	/**
	 * Tamaño de la fuente
	 */
	private String fontColor;
	/**
	 * Negrita del texto
	 */
	private boolean fontBold;
	/**
	 * El tamaño en puntos del halo del símbolo de texto.
	 */
	private int haloSize = 2;
	/**
	 * El color del halo del símbolo de texto.
	 */
	private String haloColor;
	/**
	 * Transparencia del texto
	 */
	private double opacity = 1.0D;
	/**
	 * Angulo del grafico
	 */
	private int angle;
	/**
	 * Desplazamiento horizontal
	 */
	private int horizontalOffset;
	/**
	 * Desplazamiento vertical
	 */
	private int verticalOffset;
	/**
	 * Alineción horizontal
	 */
	private TextHorizontalAlignment horizontalAlignment = TextHorizontalAlignment.CENTER;
	/**
	 * Alineacion vertical
	 */
	private TextVerticalAlignment verticalAlignment = TextVerticalAlignment.BASELINE;

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

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFontFamily() {
		return this.fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	public String getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public String getFontColor() {
		return this.fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public boolean isFontBold() {
		return this.fontBold;
	}

	public void setFontBold(boolean fontBold) {
		this.fontBold = fontBold;
	}

	public int getHaloSize() {
		return this.haloSize;
	}

	public void setHaloSize(int haloSize) {
		this.haloSize = haloSize;
	}

	public String getHaloColor() {
		return this.haloColor;
	}

	public void setHaloColor(String haloColor) {
		this.haloColor = haloColor;
	}

	public double getOpacity() {
		return this.opacity;
	}

	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}

	public int getAngle() {
		return this.angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public int getHorizontalOffset() {
		return this.horizontalOffset;
	}

	public void setHorizontalOffset(int horizontalOffset) {
		this.horizontalOffset = horizontalOffset;
	}

	public int getVerticalOffset() {
		return this.verticalOffset;
	}

	public void setVerticalOffset(int verticalOffset) {
		this.verticalOffset = verticalOffset;
	}

	public TextHorizontalAlignment getHorizontalAlignment() {
		return this.horizontalAlignment;
	}

	public void setHorizontalAlignment(TextHorizontalAlignment horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
	}

	public TextVerticalAlignment getVerticalAlignment() {
		return this.verticalAlignment;
	}

	public void setVerticalAlignment(TextVerticalAlignment verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

}
