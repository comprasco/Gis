package co.gov.supernotariado.bachue.portalgeografico.model.legend;

import java.io.Serializable;

/**
 * Esta clase almacena información de un atributo del servicio de leyenda
 *
 */
public class LegendLayerSymbolMetadata implements Serializable {
	private static final long serialVersionUID = -5701008566408792610L;
	
	/**
	 * Variable label
	 * String que contiene la etiqueta.
	 */
	private String label;
	/**
	 * Variable url
	 * String que contiene la url.
	 */
	private String url;
	/**
	 * Variable imageData
	 * String que contiene el valor de imageData.
	 */
	private String imageData;
	/**
	 * Variable contentType
	 * String que contiene el valor del atributo contentType.
	 */
	private String contentType;
	/**
	 * Variable height
	 * int que contiene la altura.
	 */
	private int height;
	/**
	 * Variable width
	 * int que contiene la anchura.
	 */
	private int width;
	
	/**
	 * Metodo que retorna si los datos de imagen estan disponible o no
	 * @return un boolean con el estado
	 */
	public boolean isImageDataUrlAvailable() {
		return (this.imageData != null) && (!"".equals(this.imageData));
	}

	/**
	 * Metodo que retorna los datos de la imagen
	 * @return un String que contiene los datos de la imagen
	 */
	public String getImageDataUrl() {
		return String.format("data:%s;base64,%s", new Object[] { this.contentType, this.imageData });
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageData() {
		return this.imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
