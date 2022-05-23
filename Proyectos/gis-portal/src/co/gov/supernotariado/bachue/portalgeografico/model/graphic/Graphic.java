package co.gov.supernotariado.bachue.portalgeografico.model.graphic;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import co.gov.supernotariado.bachue.portalgeografico.model.map.Extent;

/**
 * 
 * Un gráfico puede contener geometría, un símbolo, atributos o una plantilla de
 * información. Se muestra un gráfico en GraphicsLayer. GraphicsLayer le permite
 * escuchar eventos en Graphics.
 *
 */
public abstract class Graphic implements Serializable {
	private static final long serialVersionUID = -7045367701984621307L;
	/**
	 * Id del grafico
	 */
	private String id;
	/**
	 * Tipo de grafico
	 */
	private String type;
	/**
	 * Visibilidad del grafico
	 */
	private boolean visible = true;
	/**
	 * Atributos del grafico
	 */
	private Map<String, Object> attributes = new LinkedHashMap<>();
	/**
	 * Información adicional del grafico
	 */
	private Object data;

	/**
	 * Metodo que retorna el extent del grafico
	 * 
	 * @return
	 */
	public abstract Extent getExtent();

	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isVisible() {
		return this.visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
