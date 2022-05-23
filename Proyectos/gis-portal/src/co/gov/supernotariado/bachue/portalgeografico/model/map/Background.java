package co.gov.supernotariado.bachue.portalgeografico.model.map;

import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

import co.gov.supernotariado.bachue.portalgeografico.model.map.Background;

/**
 * Clase que almacena todas los mapas base de ESRI que son utilizados en el
 * visor
 *
 */
public enum Background {
	STREETS("streets", "Streets", 24, "The Streets basemap presents a multiscale street map for the world.",
			"https://www.arcgis.com/sharing/rest/content/items/d8855ee4d3d74413babfb0f41203b168/info/thumbnail/world_street_map.jpg"),
	STREETS_VECTOR("streets-vector", "Streets (Vector)", 20,
			"The Streets basemap presents a multiscale street map for the world.",
			"https://www.arcgis.com/sharing/rest/content/items/d8855ee4d3d74413babfb0f41203b168/info/thumbnail/world_street_map.jpg"),
	STREETS_NAVIGATION_VECTOR("streets-navigation-vector", "Streets Navigation (Vector)", 20,
			"The Streets basemap presents a multiscale street map for the world featuring a custom navigation map style.",
			"https://www.arcgis.com/sharing/rest/content/items/d8855ee4d3d74413babfb0f41203b168/info/thumbnail/world_street_map.jpg"),
	STREETS_NIGHT_VECTOR("streets-night-vector", "Streets Night (Vector)", 20,
			"The Streets basemap presents a multiscale street map for the world featuring a custom 'night time' street map style.",
			"https://www.arcgis.com/sharing/rest/content/items/d8855ee4d3d74413babfb0f41203b168/info/thumbnail/world_street_map.jpg"),
	STREETS_RELIEF_VECTOR("streets-relief-vector", "Streets Relief (Vector)", 20,
			"The Streets basemap presents a multiscale street map for the world designed for use with a relief map.",
			"https://www.arcgis.com/sharing/rest/content/items/d8855ee4d3d74413babfb0f41203b168/info/thumbnail/world_street_map.jpg"),
	SATELLITE("satellite", "Satellite", 24,
			"The World Imagery map is a detailed imagery map layer that is designed to be used as a basemap for various maps and applications.",
			"https://www.arcgis.com/sharing/rest/content/items/86de95d4e0244cba80f0fa2c9403a7b2/info/thumbnail/tempimagery.jpg"),
	HYBRID("hybrid", "Hybrid", 24,
			"The World Imagery map is a detailed imagery map layer and labels that is designed to be used as a basemap for various maps and applications.",
			"https://www.arcgis.com/sharing/rest/content/items/413fd05bbd7342f5991d5ec96f4f8b18/info/thumbnail/tempimagery_with_labels_ne_usa.png"),
	TOPOGRAPHIC("topo", "Topographic", 24,
			"The Topographic map includes boundaries, cities, water features, physiographic features, parks, landmarks, transportation, and buildings.",
			"https://www.arcgis.com/sharing/rest/content/items/6e03e8c26aad4b9c92a87c1063ddb0e3/info/thumbnail/topo_map_2.jpg"),
	TOPOGRAPHIC_VECTOR("topo-vector", "Topographic (Vector)", 20,
			"The Topographic map includes boundaries, cities, water features, physiographic features, parks, landmarks, transportation, and buildings.",
			"https://www.arcgis.com/sharing/rest/content/items/6e03e8c26aad4b9c92a87c1063ddb0e3/info/thumbnail/topo_map_2.jpg"),
	OCEANS("oceans", "Oceans", 17,
			"The Ocean Basemap is designed to be used as a basemap by marine GIS professionals and as a reference map by anyone interested in ocean data.",
			"https://www.arcgis.com/sharing/rest/content/items/48b8cec7ebf04b5fbdcaf70d09daff21/info/thumbnail/tempoceans.jpg"),
	LIGHTGRAY("gray", "Light Gray", 24,
			"The Light Gray Canvas basemap is designed to be used as a neutral background map for overlaying and emphasizing other map layers.",
			"https://www.arcgis.com/sharing/rest/content/items/8b3b470883a744aeb60e5fff0a319ce7/info/thumbnail/templight_gray_canvas_with_labels__ne_usa.png"),
	LIGHTGRAY_VECTOR("gray-vector", "Light Gray (Vector)", 20,
			"The Light Gray Canvas basemap is designed to be used as a neutral background map for overlaying and emphasizing other map layers.",
			"https://www.arcgis.com/sharing/rest/content/items/8b3b470883a744aeb60e5fff0a319ce7/info/thumbnail/templight_gray_canvas_with_labels__ne_usa.png"),
	DARKGRAY("dark-gray", "Dark Gray", 24,
			"The Dark Gray Canvas basemap is designed to be used as a soothing background map for overlaying and focus attention on other map layers.",
			"https://www.arcgis.com/sharing/rest/content/items/25869b8718c0419db87dad07de5b02d8/info/thumbnail/DGCanvasBase.png"),
	DARKGRAY_VECTOR("dark-gray-vector", "Dark Gray (Vector)", 20,
			"The Dark Gray Canvas basemap is designed to be used as a soothing background map for overlaying and focus attention on other map layers.",
			"https://www.arcgis.com/sharing/rest/content/items/25869b8718c0419db87dad07de5b02d8/info/thumbnail/DGCanvasBase.png"),
	TERRAIN("terrain", "Terrain", 14,
			"The Terrain with Labels basemap is designed to be used to overlay and emphasize other thematic map layers.",
			"https://www.arcgis.com/sharing/rest/content/items/aab054ab883c4a4094c72e949566ad40/info/thumbnail/tempTerrain_with_labels_ne_usa.png"),
	NATGEO("national-geographic", "National Geographic", 17,
			"The National Geographic basemap is designed to be used as a general reference map for informational and educational purposes.",
			"https://www.arcgis.com/sharing/rest/content/items/509e2d6b034246d692a461724ae2d62c/info/thumbnail/natgeo.jpg"),
	OSM("osm", "OpenStreetMap", 20,
			"The OpenStreetMap is a community map layer that is designed to be used as a basemap for various maps and applications.",
			"https://www.arcgis.com/sharing/rest/content/items/5d2bfa736f8448b3a1708e1f6be23eed/info/thumbnail/temposm.jpg");

	
	
	/**
	 * Variable value
	 * String que contiene el valor.
	 */
	private String value;
	/**
	 * Variable label
	 * String que contiene la etiqueta.
	 */
	private String label;
	/**
	 * Variable lods
	 * int que contiene el valor de los atributos lods.
	 */
	private int lods;
	/**
	 * Variable description
	 * String que contiene la descripcion.
	 */
	private String description;
	/**
	 * Variable thumbnail
	 * String que contiene la miniatura.
	 */
	private String thumbnail;

	
	/**
	 * Este constructor por defecto que recibe el valor del mapa, la etiqueta, las
	 * escalas, descripción y la imágen 
	 * 
	 * @param value
	 * @param label
	 * @param lods
	 * @param description
	 * @param thumbnail
	 */
	private Background(String value, String label, int lods, String description, String thumbnail) {
		this.value = value;
		this.label = label;
		this.lods = lods;
		this.description = description;
		this.thumbnail = thumbnail;
	}

	public String getValue() {
		return this.value;
	}

	public String getLabel() {
		return this.label;
	}

	public int getLods() {
		return this.lods;
	}

	public String getDescription() {
		return this.description;
	}

	public String getThumbnail() {
		return this.thumbnail;
	}

	/**
	 * Obtiene el listado de todas los mapas base de ESRI
	 * 
	 * @return una List<SelectItem> de los mapas base
	 */
	public static final List<SelectItem> getSelectItems() {
		List<SelectItem> items = new ArrayList<>();
		for (Background b : values()) {
			String description = String.format("%s Contiene %d niveles de detalle.",
					new Object[] { b.getDescription(), Integer.valueOf(b.getLods()) });
			items.add(new SelectItem(b.getValue(), b.getLabel(), description));
		}
		return items;
	}
}