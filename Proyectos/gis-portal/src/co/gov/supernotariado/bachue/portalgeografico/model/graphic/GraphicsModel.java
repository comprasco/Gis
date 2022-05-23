package co.gov.supernotariado.bachue.portalgeografico.model.graphic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.gov.supernotariado.bachue.portalgeografico.model.map.Extent;

/**
 * 
 * Una capa que contiene una o más características gráficas. Cada mapa contiene
 * un GraphicsLayer por defecto, accesible mediante la propiedad Map.graphics.
 * Puede crear sus propias capas gráficas y agregarlas al mapa. Las capas de
 * gráficos se pueden reordenar dentro del grupo de capas de gráficos
 *
 */
public class GraphicsModel implements Serializable {
	private static final long serialVersionUID = -7030307087308908664L;
	/**
	 * Nombre de la coleccion de graficos
	 */
	private String name;
	/**
	 * Refrescar la coleccion de graficos
	 */
	private boolean refresh;
	/**
	 * Lista de graficos que hacen parte de la coleccion
	 */
	private List<Graphic> graphics;

	/**
	 * Constructor por omision
	 */
	public GraphicsModel() {
		reset();
	}

	/**
	 * Refresca los valores por defecto de los atributos
	 */
	public void reset() {
		this.name = "Graphics Layer";
		this.refresh = true;
		this.graphics = new ArrayList<>();
	}

	/**
	 * Obtiene el extent de la suma de todos los graficos
	 * 
	 * @return Extent extent
	 */
	public Extent getExtent() {
		Extent extent = null;
		if (this.graphics != null) {
			for (Graphic g : this.graphics) {
				if (g.getExtent() != null) {
					extent = Extent.union(extent, g.getExtent());
				}
			}
		}
		return extent;
	}

	/**
	 * Buscando grafico por id de la coleccion
	 * 
	 * @param id
	 * @return
	 */
	public Graphic findGraphic(String id) {
		Graphic graphic = null;
		if ((id != null) && (this.graphics != null) && (!this.graphics.isEmpty())) {
			for (Graphic g : this.graphics) {
				if (id.equals(g.getId())) {
					graphic = g;
					break;
				}
			}
		}
		return graphic;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRefresh() {
		return this.refresh;
	}

	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}

	public List<Graphic> getGraphics() {
		return this.graphics;
	}

	public void setGraphics(List<Graphic> graphics) {
		this.graphics = graphics;
	}
}
