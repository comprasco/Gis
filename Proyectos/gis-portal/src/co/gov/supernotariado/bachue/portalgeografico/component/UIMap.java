package co.gov.supernotariado.bachue.portalgeografico.component;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.faces.application.ResourceDependencies;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import co.gov.supernotariado.bachue.portalgeografico.event.Event;
import co.gov.supernotariado.bachue.portalgeografico.event.MapClickEvent;
import co.gov.supernotariado.bachue.portalgeografico.event.MapExtentEvent;
import co.gov.supernotariado.bachue.portalgeografico.event.MapGeoLocationEvent;
import co.gov.supernotariado.bachue.portalgeografico.event.MapGraphicDragEvent;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.CircleGraphic;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.Graphic;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.GraphicsModel;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.PictureMarkerGraphic;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.PolygonGraphic;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.PolylineGraphic;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.SvgMarkerGraphic;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.TextGraphic;
import co.gov.supernotariado.bachue.portalgeografico.model.map.Extent;
import co.gov.supernotariado.bachue.portalgeografico.model.map.Screen;
import co.gov.supernotariado.bachue.portalgeografico.utilities.ComponentUtilities;
import co.gov.supernotariado.bachue.portalgeografico.utilities.GISUtilities;
import co.gov.supernotariado.bachue.portalgeografico.utilities.StringUtilities;

/**
 * En esta clase de declaran todas la librerias de ArcGIS y las variables globales del mapa
 *
 */
@FacesComponent("co.gov.supernotariado.bachue.portalgeografico.component.Map")
@ResourceDependencies({
		@javax.faces.application.ResourceDependency(library = "javax.faces", name = "jsf.js", target = "head"),
		@javax.faces.application.ResourceDependency(library = "javascript", name = "gisportal.js", target = "head"),
		@javax.faces.application.ResourceDependency(library = "css", name = "gisportal.css", target = "head") })
public class UIMap extends UIComponentBase implements ClientBehaviorHolder {
	/**
	 * Constructor de la clase donse establece la propieedad rendered en null
	 */
	public UIMap() {
		setRendererType(null);
	}

	/**
	 * Devuelve el identificador de la familia de componentes a la que pertenece
	 * este componente. Este identificador, junto con el valor de la propiedad
	 * rendererType, se puede usar para seleccionar el Renderer apropiado para esta
	 * instancia de componente.
	 * 
	 * @return retorna el identificador de la familia de componentes
	 */
	public String getFamily() {
		return null;
	}

	/**
	 * Devuelve una Colección no nula e inmodificable que contiene los nombres de
	 * los eventos lógicos admitidos por el componente que implementa esta interfaz.
	 * 
	 * @return Devuelve una Colección de strings no nula e inmodificable
	 */
	public Collection<String> getEventNames() {
		return Arrays.asList(new String[] { Event.CLICK.toString(), Event.EXTENT.toString(), Event.VIEW.toString(),
				Event.ACTION.toString(), Event.DRAG.toString(), Event.GEOLOCATION.toString() });
	}

	/**
	 * Devuelve el nombre de evento predeterminado para esta implementación de
	 * ClientBehaviorHolder.
	 * 
	 * @return el nombre de evento predeterminado
	 */
	public String getDefaultEventName() {
		return Event.CLICK.toString();
	}

	/**
	 * Devuelve una bandera que indica si este componente es responsable de
	 * representar sus componentes secundarios.
	 * 
	 * @return Devuelve una bandera de tipo boolean
	 */
	public boolean getRendersChildren() {
		return true;
	}

	/**
	 * Decodifique cualquier estado nuevo de este UIComponent a partir de la
	 * solicitud contenida en el FacesContext especificado y almacene este estado
	 * según sea necesario
	 * 
	 * @param FacesContext context contexto de la aplicacion
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public void decode(FacesContext context) {
		String clientId = getClientId();
		if (ComponentUtilities.isComponentRequest(context, clientId)) {
		}
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();

		String name = (String) params.get("javax.faces.behavior.event");
		String latitude;
		String longitude;
		String zoom;
		String scale;
		String wkid;
		String xmin;
		String ymin;
		String xmax;
		String ymax;
		String height;
		String width;
		String x;
		String y;
		MapGraphicDragEvent event;
		String timestamp;
		String altitude;
		String accuracy;
		String altitudeAccuracy;
		String heading;
		String speed;
		if (Event.CLICK.toString().equals(name)) {
			List<ClientBehavior> behaviors = (List<ClientBehavior>) getClientBehaviors().get(Event.CLICK.toString());
			if ((behaviors != null) && (!behaviors.isEmpty())) {
				latitude = (String) params.get("gisportal.latitude");
				longitude = (String) params.get("gisportal.longitude");
				zoom = (String) params.get("gisportal.zoom");
				scale = (String) params.get("gisportal.scale");
				wkid = (String) params.get("gisportal.extent.wkid");
				xmin = (String) params.get("gisportal.extent.xmin");
				ymin = (String) params.get("gisportal.extent.ymin");
				xmax = (String) params.get("gisportal.extent.xmax");
				ymax = (String) params.get("gisportal.extent.ymax");
				height = (String) params.get("gisportal.screen.height");
				width = (String) params.get("gisportal.screen.width");
				x = (String) params.get("gisportal.screen.x");
				y = (String) params.get("gisportal.screen.y");
				for (ClientBehavior clientBehavior : behaviors) {
					Extent extent = new Extent();
					extent.setWkid(Integer.parseInt(wkid));
					extent.setXmin(Double.parseDouble(xmin));
					extent.setYmin(Double.parseDouble(ymin));
					extent.setXmax(Double.parseDouble(xmax));
					extent.setYmax(Double.parseDouble(ymax));

					Screen screen = new Screen();
					screen.setHeight(Integer.parseInt(height));
					screen.setWidth(Integer.parseInt(width));
					screen.setX(Double.valueOf(Double.parseDouble(x)).intValue());
					screen.setY(Double.valueOf(Double.parseDouble(y)).intValue());

					MapClickEvent mapClickEvent = new MapClickEvent(this, clientBehavior);
					mapClickEvent.setLatitude(Double.parseDouble(latitude));
					mapClickEvent.setLongitude(Double.parseDouble(longitude));
					mapClickEvent.setZoom(Integer.parseInt(zoom));
					mapClickEvent.setScale(Double.parseDouble(scale));
					mapClickEvent.setExtent(extent);
					mapClickEvent.setScreen(screen);

					queueEvent(mapClickEvent);
				}
			}
		} else {
			if (Event.EXTENT.toString().equals(name)) {
				latitude = (String) params.get("gisportal.latitude");
				longitude = (String) params.get("gisportal.longitude");
				zoom = (String) params.get("gisportal.zoom");
				scale = (String) params.get("gisportal.scale");
				wkid = (String) params.get("gisportal.extent.wkid");
				xmin = (String) params.get("gisportal.extent.xmin");
				ymin = (String) params.get("gisportal.extent.ymin");
				xmax = (String) params.get("gisportal.extent.xmax");
				ymax = (String) params.get("gisportal.extent.ymax");
				height = (String) params.get("gisportal.screen.height");
				width = (String) params.get("gisportal.screen.width");

				ComponentUtilities.setValueExpressionValue(context, this, "latitude",
						Double.valueOf(Double.parseDouble(latitude)));
				ComponentUtilities.setValueExpressionValue(context, this, "longitude",
						Double.valueOf(Double.parseDouble(longitude)));
				ComponentUtilities.setValueExpressionValue(context, this, "zoom",
						Double.valueOf(Double.parseDouble(zoom)));

				List<ClientBehavior> behaviors = (List<ClientBehavior>) getClientBehaviors()
						.get(Event.EXTENT.toString());
				if ((behaviors != null) && (!behaviors.isEmpty())) {
					for (ClientBehavior behavior : behaviors) {
						Extent extent = new Extent();
						extent.setWkid(Integer.parseInt(wkid));
						extent.setXmin(Double.parseDouble(xmin));
						extent.setYmin(Double.parseDouble(ymin));
						extent.setXmax(Double.parseDouble(xmax));
						extent.setYmax(Double.parseDouble(ymax));

						Screen screen = new Screen();
						screen.setHeight(Integer.parseInt(height));
						screen.setWidth(Integer.parseInt(width));

						MapExtentEvent mapExtentEvent = new MapExtentEvent(this, behavior);
						mapExtentEvent.setLatitude(Double.parseDouble(latitude));
						mapExtentEvent.setLongitude(Double.parseDouble(longitude));
						mapExtentEvent.setZoom(Integer.parseInt(zoom));
						mapExtentEvent.setScale(Double.parseDouble(scale));
						mapExtentEvent.setExtent(extent);
						mapExtentEvent.setScreen(screen);

						queueEvent(mapExtentEvent);
					}
				}
			} else {
				String uuid;
				String attribsJson;
				Map<String, Object> sourceMap;
				Map<String, Object> attribsMap;
				ClientBehavior behavior;
				List<ClientBehavior> behaviors;
				if (Event.VIEW.toString().equals(name)) {
					behaviors = (List) getClientBehaviors().get(Event.VIEW.toString());
					if ((behaviors != null) && (!behaviors.isEmpty())) {
						uuid = (String) params.get("gisportal.uuid");
						String sourceJson = StringUtilities.decode((String) params.get("gisportal.source"));
						attribsJson = StringUtilities.decode((String) params.get("gisportal.attributes"));

						sourceMap = StringUtilities.toMap(sourceJson);
						attribsMap = StringUtilities.toMap(attribsJson);
					}
				} else {
					String action;
					if (Event.ACTION.toString().equals(name)) {
						behaviors = (List) getClientBehaviors().get(Event.ACTION.toString());
						if ((behaviors != null) && (!behaviors.isEmpty())) {
							action = (String) params.get("gisportal.act");
							uuid = (String) params.get("gisportal.uuid");
							String sourceJson = StringUtilities.decode((String) params.get("gisportal.source"));
							attribsJson = StringUtilities.decode((String) params.get("gisportal.attributes"));

							sourceMap = StringUtilities.toMap(sourceJson);
							attribsMap = StringUtilities.toMap(attribsJson);
						}
					} else {
						String attribs;
						if (Event.DRAG.toString().equals(name)) {
							behaviors = (List) getClientBehaviors().get(Event.DRAG.toString());
							if ((behaviors != null) && (!behaviors.isEmpty())) {
								uuid = (String) params.get("gisportal.uuid");
								String type = (String) params.get("gisportal.type");
								latitude = (String) params.get("gisportal.latitude");
								longitude = (String) params.get("gisportal.longitude");
								String sourceJson = StringUtilities.decode((String) params.get("gisportal.source"));
								attribs = StringUtilities.decode((String) params.get("gisportal.attributes"));

								sourceMap = StringUtilities.toMap(sourceJson);
								for (ClientBehavior clientBehavior : behaviors) {
									event = new MapGraphicDragEvent(this, clientBehavior);
									event.setServiceId(StringUtilities.toString(sourceMap.get("serviceId")));
									event.setGraphicId(uuid);
									event.setType(type);
									event.setLatitude(Double.parseDouble(latitude));
									event.setLongitude(Double.parseDouble(longitude));
									event.setAttributesJson(attribs);
									event.setAttributesMap(StringUtilities.toMap(attribs));

									queueEvent(event);
								}
							}
						} else if (Event.GEOLOCATION.toString().equals(name)) {
							timestamp = (String) params.get("gisportal.timestamp");
							latitude = (String) params.get("gisportal.latitude");
							longitude = (String) params.get("gisportal.longitude");
							zoom = (String) params.get("gisportal.zoom");
							altitude = (String) params.get("gisportal.altitude");
							accuracy = (String) params.get("gisportal.accuracy");
							altitudeAccuracy = (String) params.get("gisportal.altitudeAccuracy");
							heading = (String) params.get("gisportal.heading");
							speed = (String) params.get("gisportal.speed");

							ComponentUtilities.setValueExpressionValue(context, this, "latitude",
									Double.valueOf(Double.parseDouble(latitude)));
							ComponentUtilities.setValueExpressionValue(context, this, "longitude",
									Double.valueOf(Double.parseDouble(longitude)));
							ComponentUtilities.setValueExpressionValue(context, this, "zoom",
									Double.valueOf(Double.parseDouble(zoom)));

							behaviors = (List) getClientBehaviors().get(Event.GEOLOCATION.toString());
							if ((behaviors != null) && (!behaviors.isEmpty())) {
								for (ClientBehavior clientBehavior : behaviors) {
									MapGeoLocationEvent mapGeoLocationEvent = new MapGeoLocationEvent(this,
											clientBehavior);
									mapGeoLocationEvent.setTimestamp(Long.parseLong(timestamp));
									mapGeoLocationEvent.setLatitude(Double.parseDouble(latitude));
									mapGeoLocationEvent.setLongitude(Double.parseDouble(longitude));
									mapGeoLocationEvent.setZoom(Integer.parseInt(zoom));
									mapGeoLocationEvent.setAltitude(Double.parseDouble(altitude));
									mapGeoLocationEvent.setAccuracy(Double.parseDouble(accuracy));
									mapGeoLocationEvent.setAltitudeAccuracy(Double.parseDouble(altitudeAccuracy));
									mapGeoLocationEvent.setHeading(Double.parseDouble(heading));
									mapGeoLocationEvent.setSpeed(Double.parseDouble(speed));

									queueEvent(mapGeoLocationEvent);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Representa el UIComponent especificado al principio en la secuencia de salida
	 * o escritor asociado con la respuesta que estamos creando.+
	 * 
	 * @param FacesContext context contexto de la aplicacion
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void encodeBegin(FacesContext context) throws IOException {
		String clientId = getClientId();

		String jsapi = ComponentUtilities.getStringAttribute(this, "jsapi", "https://js.arcgis.com/3.27");
		boolean proxyEnabled = ComponentUtilities.getBooleanAttribute(this, "proxyEnabled", false);
		boolean proxyAllRequests = ComponentUtilities.getBooleanAttribute(this, "proxyAllRequests", false);
		String proxyUrl = ComponentUtilities.getStringAttribute(this, "proxyUrl");
		String mapPanel = ComponentUtilities.getStringAttribute(this, "mapPanel");
		String overviewPanel = ComponentUtilities.getStringAttribute(this, "overviewPanel");
		String geocoderPanel = ComponentUtilities.getStringAttribute(this, "geocoderPanel");
		String legendPanel = ComponentUtilities.getStringAttribute(this, "legendPanel");

		String loadPanel = ComponentUtilities.getStringAttribute(this, "loadPanel");
		String markerPanel = ComponentUtilities.getStringAttribute(this, "markerPanel");
		String coordinatesPanel = ComponentUtilities.getStringAttribute(this, "coordinatesPanel");
		String loadingPanel = ComponentUtilities.getStringAttribute(this, "loadingPanel");
		String navigationPanel = ComponentUtilities.getStringAttribute(this, "navigationPanel");
		String measurementPanel = ComponentUtilities.getStringAttribute(this, "measurementPanel");
		String background = ComponentUtilities.getStringAttribute(this, "background");
		String latitude = ComponentUtilities.getStringAttribute(this, "latitude", Double.toString(4.528175D));
		String longitude = ComponentUtilities.getStringAttribute(this, "longitude", Double.toString(-74.5795D));
		String zoom = ComponentUtilities.getStringAttribute(this, "zoom", Integer.toString(3));
		String zoomFactor = ComponentUtilities.getStringAttribute(this, "zoomFactor", null);
		String lods = ComponentUtilities.getStringAttribute(this, "lods");
		String wkid = ComponentUtilities.getStringAttribute(this, "wkid", null);
		String popupActions = ComponentUtilities.getStringAttribute(this, "popupActions", "Select");
		String popupWidth = ComponentUtilities.getStringAttribute(this, "popupWidth", "400");
		String popupHeight = ComponentUtilities.getStringAttribute(this, "popupHeight", "200");
		boolean showSlider = ComponentUtilities.getBooleanAttribute(this, "showSlider", true);
		boolean showArrows = ComponentUtilities.getBooleanAttribute(this, "showArrows", false);
		boolean showPopup = ComponentUtilities.getBooleanAttribute(this, "showPopup", true);
		boolean showPopupExport = ComponentUtilities.getBooleanAttribute(this, "showPopupExport", true);
		boolean showScalebar = ComponentUtilities.getBooleanAttribute(this, "showScalebar", true);
		boolean showLogo = ComponentUtilities.getBooleanAttribute(this, "showLogo", false);
		boolean showAttribution = ComponentUtilities.getBooleanAttribute(this, "showAttribution", false);
		boolean enableNavigation = ComponentUtilities.getBooleanAttribute(this, "enableNavigation", true);

		mapPanel = ComponentUtilities.qualifyClientId(clientId, mapPanel);
		overviewPanel = ComponentUtilities.qualifyClientId(clientId, overviewPanel);
		geocoderPanel = ComponentUtilities.qualifyClientId(clientId, geocoderPanel);
		legendPanel = ComponentUtilities.qualifyClientId(clientId, legendPanel);

		loadPanel = ComponentUtilities.qualifyClientId(clientId, loadPanel);
		markerPanel = ComponentUtilities.qualifyClientId(clientId, markerPanel);
		coordinatesPanel = ComponentUtilities.qualifyClientId(clientId, coordinatesPanel);
		loadingPanel = ComponentUtilities.qualifyClientId(clientId, loadingPanel);
		navigationPanel = ComponentUtilities.qualifyClientId(clientId, navigationPanel);
		measurementPanel = ComponentUtilities.qualifyClientId(clientId, measurementPanel);

		ResponseWriter writer = context.getResponseWriter();

		writer.startElement("span", this);
		writer.writeAttribute("id", clientId, null);
		writer.endElement("span");
		if (!context.isPostback()) {
			writer.write(
					"<link rel='stylesheet' type='text/css' href='https://netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.css'></link>");

			writer.write(String.format(
					"<link rel='stylesheet' type='text/css' href='%s/dijit/themes/tundra/tundra.css'></link>",
					new Object[] { jsapi }));
			writer.write(String.format("<link rel='stylesheet' type='text/css' href='%s/esri/css/esri.css'></link>",
					new Object[] { jsapi }));
			writer.write(String.format("<script type='text/javascript' src='%s'></script>", new Object[] { jsapi }));
		}
		writer.startElement("script", this);
		writer.writeAttribute("type", "text/javascript", null);
		if (!context.isPostback()) {
			writer.write("dojo.require('esri.map');");
			writer.write("dojo.require('esri.Color');");
			writer.write("dojo.require('esri.graphic');");
			writer.write("dojo.require('esri.geometry.Circle');");
			writer.write("dojo.require('esri.dijit.Legend');");
			writer.write("dojo.require('esri.dijit.Bookmarks');");
			writer.write("dojo.require('esri.dijit.Measurement');");
			writer.write("dojo.require('esri.dijit.Scalebar');");
			writer.write("dojo.require('esri.dijit.Search');");
			writer.write("dojo.require('esri.dijit.OverviewMap');");
			writer.write("dojo.require('esri.dijit.BasemapGallery');");
			writer.write("dojo.require('esri.layers.KMLLayer');");
			writer.write("dojo.require('esri.layers.WFSLayer');");
			writer.write("dojo.require('esri.layers.graphics');");
			writer.write("dojo.require('esri.layers.GraphicsLayer');");
			writer.write("dojo.require('esri.layers.FeatureLayer');");
			writer.write("dojo.require('esri.layers.WMSLayer');");
			writer.write("dojo.require('esri.layers.WCSLayer');");
			writer.write("dojo.require('esri.toolbars.navigation');");
			writer.write("dojo.require('esri.toolbars.draw');");
			writer.write("dojo.require('esri.symbols.SimpleMarkerSymbol');");
			writer.write("dojo.require('esri.symbols.SimpleLineSymbol');");
			writer.write("dojo.require('esri.symbols.SimpleFillSymbol');");
			writer.write("dojo.require('esri.renderers.UniqueValueRenderer');");
			writer.write("dojo.require('esri.renderers.SimpleRenderer');");
			writer.write("dojo.require('esri.symbols.TextSymbol');");
			writer.write("dojo.require('esri.symbols.Font');");
			writer.write("dojo.require('esri.tasks.query');");
			writer.write("dojo.require('esri.tasks.FeatureSet');");
			writer.write("dojo.require('esri.tasks.PrintTask');");
			writer.write("dojo.require('esri.dijit.Print');");
			writer.write("dojo.require('dojo.promise.all');");
			writer.write("dojo.require('esri.geometry.scaleUtils');");
			writer.write("dojo.require('dojo._base.array');");
			writer.write("dojo.require('dojo._base.lang');");
			writer.write("dojo.require('dojo.dom');");
			writer.write("dojo.require('dojo.on');");
			writer.write("dojo.require('dojo.dom-construct');");
			writer.write("dojo.require('dijit.form.CheckBox');");
			writer.write("dojo.require('dojox.xml.parser');");
			writer.write("dojo.require('esri.config');");
			writer.write("dojo.require('dijit.Menu');");
			writer.write("dojo.require('dijit.MenuItem');");
			writer.write("dojo.require('dijit.Dialog');");
			writer.write("dojo.require('dijit.form.HorizontalSlider');");
			writer.write("dojo.require('dijit.registry');");

			writer.write("if (!gisportal) var gisportal = {};");

			writer.write("gisportal.map = null;");
			writer.write("gisportal.template = null;");
			writer.write("gisportal.overview = null;");
			writer.write("gisportal.geocoder = null;");
			writer.write("gisportal.scalebar = null;");
			writer.write("gisportal.legend = null;");

			writer.write("gisportal.load = null;");

			writer.write("gisportal.marker = null;");
			writer.write("gisportal.navbar = null;");
			writer.write("gisportal.measurement = null;");
			writer.write("gisportal.toolbar = null;");
			writer.write("gisportal.graphicsLayer = null;");
			writer.write("gisportal.csvGraphics = null;");
			writer.write("gisportal.pointGraphics = null;");
			writer.write("gisportal.pointSymbol = null;");
			writer.write("gisportal.polylineSymbol = null;");
			writer.write("gisportal.polygonSymbol = null;");
			writer.write("gisportal.pointRenderer = null;");
			writer.write("gisportal.polylineGraphics = null;");
			writer.write("gisportal.polylineRenderer = null;");
			writer.write("gisportal.polygonGraphics = null;");
			writer.write("gisportal.polygonRenderer = null;");
			writer.write("gisportal.infoWindow = null;");

			writer.write(String.format("gisportal.jsapi = '%s';", new Object[] { jsapi }));
			writer.write(String.format("gisportal.clientId = '%s';", new Object[] { clientId }));
			writer.write(String.format("gisportal.defaultLatitude = %s;", new Object[] { Double.valueOf(4.828175D) }));
			writer.write(String.format("gisportal.defaultLongitude = %s;", new Object[] { Double.valueOf(-74.5795D) }));
			writer.write(String.format("gisportal.defaultZoom = %s;", new Object[] { Integer.valueOf(3) }));
			if (loadingPanel != null) {
				writer.write(String.format("gisportal.loadingDiv = '%s';", new Object[] { loadingPanel }));
			}
			writer.write("gisportal.initMap = function() {");
			if (proxyEnabled) {
				String page = proxyUrl;
				if (page == null) {
					ExternalContext ec = context.getExternalContext();
					page = String.format("%s://%s:%s%s%s",
							new Object[] { ec.getRequestScheme(), ec.getRequestServerName(),
									Integer.valueOf(ec.getRequestServerPort()), ec.getRequestContextPath(),
									"/proxy/proxy.jsp" });
				}
				writer.write(String.format("esriConfig.defaults.io.proxyUrl = '%s';", new Object[] { page }));
				writer.write(String.format("esriConfig.defaults.io.alwaysUseProxy = %s;",
						new Object[] { Boolean.valueOf(proxyAllRequests) }));
			}
			writer.write(String.format(
					"gisportal.map = new esri.Map('%s', {center:[%s,%s], zoom:%s, slider:%b, nav:%b, logo:%b, showAttribution:%b",
					new Object[] { mapPanel, longitude, latitude, zoom, Boolean.valueOf(showSlider),
							Boolean.valueOf(showArrows), Boolean.valueOf(showLogo),
							Boolean.valueOf(showAttribution) }));
			if (background != null) {
				writer.write(String.format(", basemap:'%s'", new Object[] { background }));
			}
			if (lods != null) {
				writer.write(String.format(", lods:gisportal.generateLodLevels(%s)", new Object[] { lods }));
			}
			writer.write("});");
			if (wkid != null) {
				writer.write(String.format("gisportal.map.spatialReference = new esri.SpatialReference(%s);",
						new Object[] { wkid }));
			}
			if (showPopup) {
				writer.write(String.format("gisportal.map.infoWindow.resize(%s, %s);",
						new Object[] { popupWidth, popupHeight }));
				writer.write("gisportal.map.infoWindow.visibleWhenEmpty = false;");
				writer.write("gisportal.map.infoWindow.pagingInfo = false;");
				writer.write("dojo.addClass(gisportal.map.infoWindow.domNode, 'light');");
				if (zoomFactor != null) {
					writer.write(
							String.format("gisportal.map.infoWindow.zoomFactor = %s;", new Object[] { zoomFactor }));
				}
				if (showPopupExport) {
					writer.write("gisportal.addPopupFooterLink('Export', gisportal.exportPopupGraphicToHtml);");
				}
				List<ClientBehavior> behaviors = (List) getClientBehaviors().get(Event.ACTION.toString());
				if ((behaviors != null) && (!behaviors.isEmpty())) {
					String[] actions = popupActions.split(",");
					for (String action : actions) {
						writer.write(String.format(
								"gisportal.addPopupFooterLink('%s', gisportal.generateJsfMapGraphicActionEvent);",
								new Object[] { action }));
					}
				}
				writer.write(
						"gisportal.template = new esri.InfoTemplate(gisportal.buildGenericInfoTemplateFeatureTitle, gisportal.buildGenericInfoTemplateContent);");
			} else {
				writer.write("gisportal.map.showInfoWindowOnClick = false;");
			}
			writer.write("dojo.connect(gisportal.map, 'onLoad', gisportal.initWidgets);");
			if (showPopup) {
				writer.write(
						"dojo.connect(gisportal.map, 'onClick', dojo._base.lang.hitch(this, gisportal.processMapClick));");
			}
			if (loadingPanel != null) {
				writer.write("dojo.connect(gisportal.map, 'onUpdateStart', gisportal.showProgressBar);");
				writer.write("dojo.connect(gisportal.map, 'onUpdateEnd',  gisportal.hideProgressBar);");
			}
			if (coordinatesPanel != null) {
				writer.write(String.format(
						"dojo.connect(gisportal.map, 'onMouseMove', function(evt) { gisportal.showCoordinates(evt, '%s'); });",
						new Object[] { coordinatesPanel }));
				writer.write(String.format(
						"dojo.connect(gisportal.map, 'onMouseOut',  function(evt) { gisportal.hideCoordinates(evt, '%s'); });",
						new Object[] { coordinatesPanel }));
			}
			if (legendPanel != null) {
				writer.write(String.format(
						"dojo.connect(gisportal.map, 'onLayerAdd', function(layer) { gisportal.refreshLegend(layer,'%s'); });",
						new Object[] { legendPanel }));
			}

			if (loadPanel != null) {
				writer.write(String.format("dojo.connect(gisportal.load, 'onChange', insertarmapa);",
						new Object[] { loadPanel }));
			}

			writer.write("gisportal.initServices();");
			writer.write("};");

			writer.write("gisportal.initWidgets = function(map) {");
			writer.write("dojo.connect(gisportal.map, 'onExtentChange', gisportal.generateJsfMapExtentChangeEvent);");
			if (!enableNavigation) {
				writer.write("gisportal.map.disableMapNavigation();");
			}
			if (overviewPanel != null) {
				writer.write(String.format("gisportal.createOverview('%s');", new Object[] { overviewPanel }));
				writer.write(String.format(
						"dojo.connect(gisportal.map, 'onBasemapChange', function() { gisportal.createOverview('%s'); });",
						new Object[] { overviewPanel }));
			}
			if (markerPanel != null) {
				writer.write(String.format(
						"gisportal.marker = new esri.dijit.Bookmarks({ map:gisportal.map,bookmarks: [], editable:true }, '%s');",
						new Object[] { markerPanel }));
				writer.write("gisportal.marker.startup();");
			}

			if (geocoderPanel != null) {
				writer.write(String.format(
						"gisportal.geocoder = new esri.dijit.Search({ map:gisportal.map, enableButtonMode:true, enableInfoWindow:false, enableHighlight:false }, '%s');",
						new Object[] { geocoderPanel }));
				writer.write("gisportal.geocoder.startup();");
			}
			if (navigationPanel != null) {
				writer.write("gisportal.navbar = new esri.toolbars.Navigation(gisportal.map);");
				writer.write(
						String.format("gisportal.populateNavigationToolbar('%s');", new Object[] { navigationPanel }));
			}
			if (measurementPanel != null) {
				writer.write(String.format(
						"gisportal.measurement = new esri.dijit.Measurement({ map:gisportal.map }, '%s');",
						new Object[] { measurementPanel }));
				writer.write("gisportal.measurement.startup();");
			}
			if (showScalebar) {
				writer.write(
						"gisportal.scalebar = new esri.dijit.Scalebar({ map:gisportal.map, scalebarUnit:'dual' });");
			}

			writer.write("initToolbar();");
			writer.write("dojo.connect(gisportal.map, 'onUpdateEnd', identify);");
			writer.write("dojo.connect(gisportal.map, 'onLayerAddResult', layerAddResultEvent);");
			writer.write("gisportal.infoWindow = gisportal.map.infoWindow;");
			// writer.write("gisportal.toolbar = new esri.toolbars.Draw(gisportal.map);");
			writer.write(
					"gisportal.basemapGallery = new esri.dijit.BasemapGallery({showArcGISBasemaps: true,map: map}, 'centerForm:basemapGallery');");
			writer.write("gisportal.basemapGallery.selected = false;");
			writer.write("gisportal.basemapGallery.domNode.style.display='none';");
			writer.write("gisportal.basemapGallery.startup();");
			writer.write("};");

			encodeJsfMapClickFunction(context, this, writer);
			encodeJsfMapExtentChangeFunction(context, this, writer);
			encodeJsfMapGraphicViewFunction(context, this, writer);
			encodeJsfMapGraphicActionFunction(context, this, writer);
			encodeJsfMapGraphicDragFunction(context, this, writer);
			encodeJsfMapGeoLocationFunction(context, this, writer);

			writer.write("dojo.addOnLoad(gisportal.initMap);");
		} else {
			writer.write("gisportal.hideProgressBar();");
			if ((ComponentUtilities.isValueExpression(this, "latitude"))
					&& (ComponentUtilities.isValueExpression(this, "longitude"))
					&& (ComponentUtilities.isValueExpression(this, "zoom"))) {
				writer.write(String.format("gisportal.setMapCenter(%s, %s, %s);",
						new Object[] { latitude, longitude, zoom }));
			}
			if (ComponentUtilities.isValueExpression(this, "background")) {
				writer.write(String.format("gisportal.setBackgroundLayer('%s');", new Object[] { background }));
			}
		}
	}

	/**
	 * Si nuestra propiedad representada es verdadera, renderice el final del estado
	 * actual de este componente UIC.
	 * 
	 * @param FacesContext context contexto de la aplicacion
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		if (context.isPostback()) {
			writer.write("gisportal.refreshMapLayers();");

			writer.write("gisportal.reorderGraphicsLayers();");
		}
		writer.write("gisportal.refreshLegend();");

		writer.endElement("script");
	}

	/**
	 * Si nuestra propiedad representada es verdadera, renderice los componentes UIC
	 * secundarios de este componente UIComponent.
	 * 
	 * @param FacesContext context contexto de la aplicacion
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		if (!context.isPostback()) {
			for (UIComponent child : getChildren()) {
				if (child.isRendered()) {
					encodeServiceDefinitionVar(context, child, writer);
				}
			}
			writer.write("gisportal.initServices = function() {");
			for (UIComponent child : getChildren()) {
				if (child.isRendered()) {
					encodeServiceDefinition(context, child, writer);
				}
			}
			writer.write("};");
		} else {
			for (UIComponent child : getChildren()) {
				if (child.isRendered()) {
					UIMapService service = (UIMapService) child;
					encodeServiceLayerDefinition(context, service, writer);
				}
			}
		}
	}

	/**
	 * Codificamos las varaiables GIS para el mapa
	 * 
	 * @param FacesContext   context contexto de la aplicacion
	 * @param UIComponent    component componente que hace referencia
	 * @param ResponseWriter writer Utilizado para escibir codigo javascript
	 * @throws IOException
	 */
	private void encodeServiceDefinitionVar(FacesContext context, UIComponent component, ResponseWriter writer)
			throws IOException {
		UIMapService service = (UIMapService) component;

		String id = String.format("gisportal.%s", new Object[] { service.getId() });

		writer.write(String.format("%s = null;", new Object[] { id }));
	}

	/**
	 * Codificamos los servicios GIS para el mapa
	 * 
	 * @param FacesContext   context contexto de la aplicacion
	 * @param UIComponent    component componente que hace referencia
	 * @param ResponseWriter writer Utilizado para escibir codigo javascript
	 * @throws IOException
	 */
	private void encodeServiceDefinition(FacesContext context, UIComponent component, ResponseWriter writer)
			throws IOException {
		UIMapService service = (UIMapService) component;

		String id = service.getId();
		String type = ComponentUtilities.getStringAttribute(service, "type");
		String url = ComponentUtilities.getStringAttribute(service, "url");
		boolean popup = ComponentUtilities.getBooleanAttribute(service, "showPopup", true);

		String var = String.format("gisportal.%s", new Object[] { id });
		if ("tiled".equals(type)) {
			writer.write(String.format("%s = new esri.layers.ArcGISTiledMapServiceLayer('%s', { id:'%s' });",
					new Object[] { var, url, id }));
		} else if ("image".equals(type)) {
			writer.write(String.format("%s = new esri.layers.ArcGISImageServiceLayer('%s', { id:'%s' });",
					new Object[] { var, url, id }));
		} else if ("dynamic".equals(type)) {
			writer.write(String.format("%s = new esri.layers.ArcGISDynamicMapServiceLayer('%s', { id:'%s' });",
					new Object[] { var, url, id }));
			if (popup) {
				writer.write(String.format("dojo.connect(%s, 'onLoad', gisportal.loadInfoTemplates);",
						new Object[] { var }));
			}
		} else if ("kml".equals(type)) {
			writer.write(
					String.format("%s = new esri.layers.KMLLayer('%s', { id:'%s' });", new Object[] { var, url, id }));

			writer.write(String.format("dojo.connect(%s, 'onUpdateEnd', gisportal.reorderGraphicsLayers);",
					new Object[] { var }));
		} else if ("feature".equals(type)) {
			writer.write(String.format("%s = new esri.layers.FeatureLayer('%s', { id:'%s', outFields:['*'] });",
					new Object[] { var, url, id }));
			if (popup) {
				writer.write(String.format("%s.setInfoTemplate(gisportal.template);", new Object[] { var }));
			}
		} else if ("graphics".equals(type)) {
			writer.write(String.format("%s = new esri.layers.GraphicsLayer({ id:'%s' });", new Object[] { var, id }));
			if (popup) {
				writer.write(String.format("%s.setInfoTemplate(gisportal.template);", new Object[] { var }));
			}
			writer.write(
					String.format("dojo.connect(%s, 'onMouseOver', function() { gisportal.setCursor('pointer'); });",
							new Object[] { var }));
			writer.write(
					String.format("dojo.connect(%s, 'onMouseOut', function() { gisportal.setCursor('default'); });",
							new Object[] { var }));

			writer.write(
					String.format("dojo.connect(%s, 'onMouseDown', gisportal.holdGraphic);", new Object[] { var }));
			writer.write(
					String.format("dojo.connect(%s, 'onMouseDrag', gisportal.dragGraphic);", new Object[] { var }));
			writer.write(
					String.format("dojo.connect(%s, 'onMouseOut', gisportal.releaseGraphic);", new Object[] { var }));
			writer.write(
					String.format("dojo.connect(%s, 'onMouseUp', gisportal.releaseGraphic);", new Object[] { var }));
		} else {
			throw new IOException(String.format("Invalid map service layer type '%s'.", new Object[] { type }));
		}
		encodeServiceLayerDefinition(context, service, writer);

		writer.write(String.format("gisportal.map.addLayer(%s);", new Object[] { var }));
	}

	/**
	 * Codificamos las capas de los servicios GIS para el mapa
	 * 
	 * @param FacesContext   context contexto de la aplicacion
	 * @param UIComponent    component componente que hace referencia
	 * @param ResponseWriter writer Utilizado para escibir codigo javascript
	 * @throws IOException
	 */
	private void encodeServiceLayerDefinition(FacesContext context, UIMapService service, ResponseWriter writer)
			throws IOException {
		String var = String.format("gisportal.%s", new Object[] { service.getId() });

		String visible = ComponentUtilities.getStringAttribute(service, "visible");
		if (visible != null) {
			writer.write(String.format("%s.setVisibility(%s);", new Object[] { var, visible }));
		}
		String opacity = ComponentUtilities.getStringAttribute(service, "opacity");
		if (opacity != null) {
			writer.write(String.format("try { %s.setOpacity(%s); } catch (e) {}", new Object[] { var, opacity }));
		}
		String refresh = ComponentUtilities.getStringAttribute(service, "refreshInterval");
		if (refresh != null) {
			writer.write(String.format("%s.setRefreshInterval(%s);", new Object[] { var, refresh }));
		}
		String minScale = ComponentUtilities.getStringAttribute(service, "minScale");
		if (minScale != null) {
			writer.write(String.format("%s.setMinScale(%s);", new Object[] { var, minScale }));
		}
		String maxScale = ComponentUtilities.getStringAttribute(service, "maxScale");
		if (maxScale != null) {
			writer.write(String.format("%s.setMaxScale(%s);", new Object[] { var, maxScale }));
		}
		Map<String, UIMapServiceLayer> layers;
		if (service.getChildCount() > 0) {
			layers = getVisibleServiceLayers(service);
			if (layers.isEmpty()) {
				writer.write(String.format("%s.setVisibleLayers([-1]);", new Object[] { var }));
			} else {
				Set<String> keys = layers.keySet();

				writer.write("var des=[];");
				for (String key : keys) {
					UIMapServiceLayer layer = (UIMapServiceLayer) layers.get(key);
					String where = ComponentUtilities.getStringAttribute(layer, "where", "");

					writer.write(String.format("des[%s]=\"%s\";", new Object[] { key, where }));
				}
				writer.write(String.format("%s.setLayerDefinitions(des, true);", new Object[] { var }));
				writer.write(String.format("%s.setVisibleLayers([%s]);",
						new Object[] { var, StringUtilities.join(keys.iterator(), ",") }));

				writer.write(String.format("%s.popupAttributes=[];", new Object[] { var }));
				for (String key : keys) {
					UIMapServiceLayer layer = (UIMapServiceLayer) layers.get(key);
					String attribs = ComponentUtilities.getStringAttribute(layer, "popupAttributes", "*");
					if (!attribs.equals("*")) {
						writer.write(String.format("%s.popupAttributes[%s]=[%s];", new Object[] { var, key, attribs }));
					}
				}
				for (String key : keys) {
					UIMapServiceLayer layer = (UIMapServiceLayer) layers.get(key);
					String extent = ComponentUtilities.getStringAttribute(layer, "extent");
					if ((extent != null) && (!"".equals(extent))) {
						String url = ComponentUtilities.getStringAttribute(service, "url");

						writer.write(String.format("gisportal.zoomToExtent('%s/%s', \"%s\");",
								new Object[] { url, key, extent }));
					}
				}
			}
		}
		String type = ComponentUtilities.getStringAttribute(service, "type");
		if ("graphics".equals(type)) {
			encodeGraphicsLayer(context, service, writer, var);
		}
	}

	/**
	 * Codificamos la visibilidad de las capas del visor
	 * 
	 * @param UIMapService service servicio de arcgis
	 * @return retorna un Map<String, UIMapServiceLayer>
	 * @throws IOException
	 */
	private Map<String, UIMapServiceLayer> getVisibleServiceLayers(UIMapService service) throws IOException {
		Map<String, UIMapServiceLayer> map = new TreeMap<String, UIMapServiceLayer>();
		for (UIComponent child : service.getChildren()) {
			UIMapServiceLayer layer = (UIMapServiceLayer) child;

			String number = ComponentUtilities.getStringAttribute(layer, "number", "0");
			boolean visible = ComponentUtilities.getBooleanAttribute(layer, "visible", true);
			if (visible) {
				map.put(number, layer);
			}
		}
		return map;
	}

	/**
	 * Codificamos el comportamiento de las capas de gráficos y SVG
	 * 
	 * @param FacesContext   context contexto de la aplicacion
	 * @param UIMapService   service servicio de arcgis
	 * @param ResponseWriter writer Utilizado para escibir codigo javascript
	 * @param String         id del grafico
	 * @throws IOException
	 */
	private void encodeGraphicsLayer(FacesContext context, UIMapService service, ResponseWriter writer, String id)
			throws IOException {
		GraphicsModel model = (GraphicsModel) service.getAttributes().get("model");
		if ((model != null) && ((model.isRefresh()) || (!context.isPostback()))) {
			writer.write(String.format("%s.clear();", new Object[] { id }));

			writer.write(String.format("%s.name = '%s';", new Object[] { id, model.getName() }));
			for (Graphic graphic : model.getGraphics()) {
				if (graphic.isVisible()) {
					if ((graphic instanceof PictureMarkerGraphic)) {
						encodeGraphicsLayerPictureMarker(context, service, writer, id, (PictureMarkerGraphic) graphic);
					}
					if ((graphic instanceof SvgMarkerGraphic)) {
						encodeGraphicsLayerSvgMarker(context, service, writer, id, (SvgMarkerGraphic) graphic);
					} else if ((graphic instanceof PolylineGraphic)) {
						encodeGraphicsLayerPolyline(context, service, writer, id, (PolylineGraphic) graphic);
					} else if ((graphic instanceof CircleGraphic)) {
						encodeGraphicsLayerCircle(context, service, writer, id, (CircleGraphic) graphic);
					} else if ((graphic instanceof PolygonGraphic)) {
						encodeGraphicsLayerPolygon(context, service, writer, id, (PolygonGraphic) graphic);
					} else if ((graphic instanceof TextGraphic)) {
						encodeGraphicsLayerText(context, service, writer, id, (TextGraphic) graphic);
					}
				}
			}
			model.setRefresh(false);
		}
	}

	/**
	 * Codificamos el comportamiento de las capas de iconos
	 * 
	 * @param FacesContext         context contexto de la aplicacion
	 * @param UIMapService         service servicio de arcgis
	 * @param ResponseWriter       writer Utilizado para escibir codigo javascript
	 * @param String               id del grafico
	 * @param PictureMarkerGraphic graphic grafico del mapa
	 * @throws IOException
	 */
	private void encodeGraphicsLayerPictureMarker(FacesContext context, UIMapService service, ResponseWriter writer,
			String id, PictureMarkerGraphic graphic) throws IOException {
		String function = String.format(
				"gisportal.addPictureMarkerGraphic(%s, '%s', '%s', %s, %s, %s, '%s', %d, %d, %d, %b);",
				new Object[] { id,

						StringUtilities.defaultString(graphic.getId()),
						StringUtilities.defaultString(graphic.getType()),
						Double.valueOf(graphic.getCoordinate().getLatitude()),
						Double.valueOf(graphic.getCoordinate().getLongitude()),
						StringUtilities.toJson(graphic.getAttributes()),
						StringUtilities.defaultString(graphic.getImage()), Integer.valueOf(graphic.getWidth()),
						Integer.valueOf(graphic.getHeight()), Integer.valueOf(graphic.getAngle()),
						Boolean.valueOf(graphic.isDraggable()) });

		writer.write(function);
	}

	/**
	 * Codificamos el comportamiento de las capas de svg
	 * 
	 * @param FacesContext         context contexto de la aplicacion
	 * @param UIMapService         service servicio de arcgis
	 * @param ResponseWriter       writer Utilizado para escibir codigo javascript
	 * @param String               id del grafico
	 * @param PictureMarkerGraphic graphic grafico del mapa
	 * @throws IOException
	 */
	private void encodeGraphicsLayerSvgMarker(FacesContext context, UIMapService service, ResponseWriter writer,
			String id, SvgMarkerGraphic graphic) throws IOException {
		String function = String.format(
				"gisportal.addSvgMarkerGraphic(%s, '%s', '%s', %s, %s, %s, '%s', %d, %d, '%s', %s, %s, '%s', %s, %s, %b);",
				new Object[] { id,

						StringUtilities.defaultString(graphic.getId()),
						StringUtilities.defaultString(graphic.getType()),
						Double.valueOf(graphic.getCoordinate().getLatitude()),
						Double.valueOf(graphic.getCoordinate().getLongitude()),
						StringUtilities.toJson(graphic.getAttributes()),
						StringUtilities.defaultString(graphic.getPath()), Integer.valueOf(graphic.getSize()),
						Integer.valueOf(graphic.getAngle()), StringUtilities.defaultString(graphic.getFillColor()),
						Double.valueOf(graphic.getFillOpacity()), graphic.getOutlineStyle(),
						StringUtilities.defaultString(graphic.getOutlineColor()),
						Double.valueOf(graphic.getOutlineOpacity()), Double.valueOf(graphic.getOutlineWidth()),
						Boolean.valueOf(graphic.isDraggable()) });

		writer.write(function);
	}

	/**
	 * Codificamos el comportamiento de las capas de polilineas
	 * 
	 * @param FacesContext         context contexto de la aplicacion
	 * @param UIMapService         service servicio de arcgis
	 * @param ResponseWriter       writer Utilizado para escibir codigo javascript
	 * @param String               id del grafico
	 * @param PictureMarkerGraphic graphic grafico del mapa
	 * @throws IOException
	 */
	private void encodeGraphicsLayerPolyline(FacesContext context, UIMapService service, ResponseWriter writer,
			String id, PolylineGraphic graphic) throws IOException {
		String function = String.format("gisportal.addPolylineGraphic(%s, '%s', '%s', %s, %s, %s, '%s', %s, %s);",
				new Object[] { id,

						StringUtilities.defaultString(graphic.getId()),
						StringUtilities.defaultString(graphic.getType()),
						GISUtilities.formatCoordinates(graphic.getCoordinates()),
						StringUtilities.toJson(graphic.getAttributes()), graphic.getStyle(),
						StringUtilities.defaultString(graphic.getColor()), Double.valueOf(graphic.getOpacity()),
						Integer.valueOf(graphic.getWidth()) });

		writer.write(function);
	}

	/**
	 * Codificamos el comportamiento de las capas de poligonos
	 * 
	 * @param FacesContext         context contexto de la aplicacion
	 * @param UIMapService         service servicio de arcgis
	 * @param ResponseWriter       writer Utilizado para escibir codigo javascript
	 * @param String               id del grafico
	 * @param PictureMarkerGraphic graphic grafico del mapa
	 * @throws IOException
	 */
	private void encodeGraphicsLayerPolygon(FacesContext context, UIMapService service, ResponseWriter writer,
			String id, PolygonGraphic graphic) throws IOException {
		String function = String.format(
				"gisportal.addPolygonGraphic(%s, '%s', '%s', %s, %s, '%s', %s, %s, %s, '%s', %s, %s, '%s', %s, %s);",
				new Object[] { id,

						StringUtilities.defaultString(graphic.getId()),
						StringUtilities.defaultString(graphic.getType()),
						GISUtilities.formatCoordinates(graphic.getCoordinates()),
						StringUtilities.toJson(graphic.getAttributes()),
						StringUtilities.defaultString(graphic.getImageUrl()), Integer.valueOf(graphic.getImageHeight()),
						Integer.valueOf(graphic.getImageWidth()), graphic.getFillStyle(),
						StringUtilities.defaultString(graphic.getFillColor()), Double.valueOf(graphic.getFillOpacity()),
						graphic.getOutlineStyle(), StringUtilities.defaultString(graphic.getOutlineColor()),
						Double.valueOf(graphic.getOutlineOpacity()), Double.valueOf(graphic.getOutlineWidth()) });

		writer.write(function);
	}

	/**
	 * Codificamos el comportamiento de las capas de circulos
	 * 
	 * @param FacesContext         context contexto de la aplicacion
	 * @param UIMapService         service servicio de arcgis
	 * @param ResponseWriter       writer Utilizado para escibir codigo javascript
	 * @param String               id del grafico
	 * @param PictureMarkerGraphic graphic grafico del mapa
	 * @throws IOException
	 */
	private void encodeGraphicsLayerCircle(FacesContext context, UIMapService service, ResponseWriter writer, String id,
			CircleGraphic graphic) throws IOException {
		String function = String.format(
				"gisportal.addCircleGraphic(%s, '%s', '%s', %s, %s, %s, '%s', %s, %s, %s, '%s', %s, %s, '%s', %s, %s, %s, %s, %s);",
				new Object[] { id,

						StringUtilities.defaultString(graphic.getId()),
						StringUtilities.defaultString(graphic.getType()),
						Double.valueOf(graphic.getCoordinate().getLatitude()),
						Double.valueOf(graphic.getCoordinate().getLongitude()),
						StringUtilities.toJson(graphic.getAttributes()),
						StringUtilities.defaultString(graphic.getImageUrl()), Integer.valueOf(graphic.getImageHeight()),
						Integer.valueOf(graphic.getImageWidth()), graphic.getFillStyle(),
						StringUtilities.defaultString(graphic.getFillColor()), Double.valueOf(graphic.getFillOpacity()),
						graphic.getOutlineStyle(), StringUtilities.defaultString(graphic.getOutlineColor()),
						Double.valueOf(graphic.getOutlineOpacity()), Double.valueOf(graphic.getOutlineWidth()),
						Integer.valueOf(graphic.getRadius()), graphic.getRadiusUnit(),
						Integer.valueOf(graphic.getCurvePoints()) });

		writer.write(function);
	}

	/**
	 * Codificamos el comportamiento de las capas de textos
	 * 
	 * @param FacesContext         context contexto de la aplicacion
	 * @param UIMapService         service servicio de arcgis
	 * @param ResponseWriter       writer Utilizado para escibir codigo javascript
	 * @param String               id del grafico
	 * @param PictureMarkerGraphic graphic grafico del mapa
	 * @throws IOException
	 */
	private void encodeGraphicsLayerText(FacesContext context, UIMapService service, ResponseWriter writer, String id,
			TextGraphic graphic) throws IOException {
		String function = String.format(
				"gisportal.addTextGraphic(%s, '%s', '%s', %s, %s, %s, '%s', '%s', '%s', %b, '%s', %s, %s, '%s', %s, %s, %s, '%s', '%s');",
				new Object[] { id,

						StringUtilities.defaultString(graphic.getId()),
						StringUtilities.defaultString(graphic.getType()),
						Double.valueOf(graphic.getCoordinate().getLatitude()),
						Double.valueOf(graphic.getCoordinate().getLongitude()),
						StringUtilities.toJson(graphic.getAttributes()),
						StringUtilities.defaultString(graphic.getText()),
						StringUtilities.defaultString(graphic.getFontSize()),
						StringUtilities.defaultString(graphic.getFontFamily()), Boolean.valueOf(graphic.isFontBold()),
						StringUtilities.defaultString(graphic.getFontColor()), Double.valueOf(graphic.getOpacity()),
						Integer.valueOf(graphic.getAngle()), StringUtilities.defaultString(graphic.getHaloColor()),
						Integer.valueOf(graphic.getHaloSize()), Integer.valueOf(graphic.getHorizontalOffset()),
						Integer.valueOf(graphic.getVerticalOffset()),
						StringUtilities.defaultString(graphic.getHorizontalAlignment().toString()),
						StringUtilities.defaultString(graphic.getVerticalAlignment().toString()) });

		writer.write(function);
	}

	/**
	 * Codificamos el comportamiento el evento de click en el mapa
	 * 
	 * @param FacesContext         context contexto de la aplicacion
	 * @param UIMapService         service servicio de arcgis
	 * @param ResponseWriter       writer Utilizado para escibir codigo javascript
	 * @param String               id del grafico
	 * @param PictureMarkerGraphic graphic grafico del mapa
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void encodeJsfMapClickFunction(FacesContext context, UIComponent component, ResponseWriter writer)
			throws IOException {
		writer.write("gisportal.generateJsfMapClickEvent = function(mapPoint, screenPoint) {");

		List<ClientBehavior> behaviors = (List) getClientBehaviors().get(Event.CLICK.toString());
		if ((behaviors != null) && (!behaviors.isEmpty())) {
			writer.write("var event = null;");
			writer.write("var geoextent = gisportal.map.geographicExtent;");
			writer.write("var scale = gisportal.map.getScale();");
			writer.write("var level = gisportal.map.getLevel();");
			writer.write("var height = gisportal.map.height;");
			writer.write("var width = gisportal.map.width;");
			for (ClientBehavior behavior : behaviors) {
				List<ClientBehaviorContext.Parameter> parameters = new ArrayList();
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.latitude", "mapPoint.getLatitude()"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.longitude", "mapPoint.getLongitude()"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.zoom", "level"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.scale", "scale"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.extent.wkid",
						"geoextent.spatialReference.wkid"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.extent.xmin", "geoextent.xmin"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.extent.ymin", "geoextent.ymin"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.extent.xmax", "geoextent.xmax"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.extent.ymax", "geoextent.ymax"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.screen.height", "height"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.screen.width", "width"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.screen.x", "screenPoint.x"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.screen.y", "screenPoint.y"));

				ClientBehaviorContext cbc = ClientBehaviorContext.createClientBehaviorContext(context, component,
						Event.CLICK.toString(), getClientId(), parameters);
				String script = behavior.getScript(cbc);

				script = replaceScriptParameterValueQuotes(script, parameters);

				writer.write(script + ";");
			}
		}
		writer.write("};");
	}

	/**
	 * Codificamos el comportamiento el evento de extent en el mapa
	 * 
	 * @param FacesContext         context contexto de la aplicacion
	 * @param UIMapService         service servicio de arcgis
	 * @param ResponseWriter       writer Utilizado para escibir codigo javascript
	 * @param String               id del grafico
	 * @param PictureMarkerGraphic graphic grafico del mapa
	 * @throws IOException
	 */
	private void encodeJsfMapExtentChangeFunction(FacesContext context, UIComponent component, ResponseWriter writer)
			throws IOException {
		writer.write("gisportal.generateJsfMapExtentChangeEvent = function(extent, delta, levelChange, lod) {");

		List<ClientBehavior> behaviors = (List<ClientBehavior>) getClientBehaviors().get(Event.EXTENT.toString());
		if ((behaviors != null) && (!behaviors.isEmpty())) {
			writer.write("var event = null;");
			writer.write("var geoextent = gisportal.map.geographicExtent;");
			writer.write("var point = extent.getCenter();");
			writer.write("var scale = gisportal.map.getScale();");
			writer.write("var level = gisportal.map.getLevel();");
			writer.write("var height = gisportal.map.height;");
			writer.write("var width = gisportal.map.width;");
			for (ClientBehavior behavior : behaviors) {
				List<ClientBehaviorContext.Parameter> parameters = new ArrayList<>();
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.latitude", "point.getLatitude()"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.longitude", "point.getLongitude()"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.zoom", "level"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.scale", "scale"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.extent.wkid",
						"geoextent.spatialReference.wkid"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.extent.xmin", "geoextent.xmin"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.extent.ymin", "geoextent.ymin"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.extent.xmax", "geoextent.xmax"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.extent.ymax", "geoextent.ymax"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.screen.height", "height"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.screen.width", "width"));

				ClientBehaviorContext cbc = ClientBehaviorContext.createClientBehaviorContext(context, component,
						Event.EXTENT.toString(), getClientId(), parameters);
				String script = behavior.getScript(cbc);

				script = replaceScriptParameterValueQuotes(script, parameters);

				writer.write(script + ";");
			}
		}
		writer.write("};");
	}

	/**
	 * Codificamos el comportamiento el evento de agregar gráficos en el mapa
	 * 
	 * @param FacesContext         context contexto de la aplicacion
	 * @param UIMapService         service servicio de arcgis
	 * @param ResponseWriter       writer Utilizado para escibir codigo javascript
	 * @param String               id del grafico
	 * @param PictureMarkerGraphic graphic grafico del mapa
	 * @throws IOException
	 */
	private void encodeJsfMapGraphicViewFunction(FacesContext context, UIComponent component, ResponseWriter writer)
			throws IOException {
		writer.write("gisportal.generateJsfMapGraphicViewEvent = function(evt) {");

		List<ClientBehavior> behaviors = (List<ClientBehavior>) getClientBehaviors().get(Event.VIEW.toString());
		if ((behaviors != null) && (!behaviors.isEmpty())) {
			writer.write("var event = null;");

			writer.write("var graphic = gisportal.map.infoWindow.getSelectedFeature();");

			writer.write("var uuid = '';");
			writer.write("if (graphic.uuid) { uuid = graphic.uuid; }");

			writer.write("var source = escape(dojo.toJson(graphic.source));");

			writer.write("var attribs = escape(dojo.toJson(graphic.attributes));");
			for (ClientBehavior behavior : behaviors) {
				List<ClientBehaviorContext.Parameter> parameters = new ArrayList<>();
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.uuid", "uuid"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.source", "source"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.attributes", "attribs"));

				ClientBehaviorContext cbc = ClientBehaviorContext.createClientBehaviorContext(context, component,
						Event.VIEW.toString(), getClientId(), parameters);
				String script = behavior.getScript(cbc);

				script = replaceScriptParameterValueQuotes(script, parameters);

				writer.write(script + ";");
			}
		}
		writer.write("};");
	}

	/**
	 * Codificamos el comportamiento el evento de selección de gráfico en el mapa
	 * 
	 * @param FacesContext         context contexto de la aplicacion
	 * @param UIMapService         service servicio de arcgis
	 * @param ResponseWriter       writer Utilizado para escibir codigo javascript
	 * @param String               id del grafico
	 * @param PictureMarkerGraphic graphic grafico del mapa
	 * @throws IOException
	 */
	private void encodeJsfMapGraphicActionFunction(FacesContext context, UIComponent component, ResponseWriter writer)
			throws IOException {
		writer.write("gisportal.generateJsfMapGraphicActionEvent = function(evt) {");

		List<ClientBehavior> behaviors = (List<ClientBehavior>) getClientBehaviors().get(Event.ACTION.toString());
		if ((behaviors != null) && (!behaviors.isEmpty())) {
			writer.write("var event = null;");

			writer.write("var act = evt.target.innerHTML;");

			writer.write("var graphic = gisportal.map.infoWindow.getSelectedFeature();");

			writer.write("var uuid = '';");
			writer.write("if (graphic.uuid) { uuid = graphic.uuid; }");

			writer.write("var source = escape(dojo.toJson(graphic.source));");

			writer.write("var attribs = escape(dojo.toJson(graphic.attributes));");
			for (ClientBehavior behavior : behaviors) {
				List<ClientBehaviorContext.Parameter> parameters = new ArrayList<>();
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.act", "act"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.uuid", "uuid"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.source", "source"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.attributes", "attribs"));

				ClientBehaviorContext cbc = ClientBehaviorContext.createClientBehaviorContext(context, component,
						Event.ACTION.toString(), getClientId(), parameters);
				String script = behavior.getScript(cbc);

				script = replaceScriptParameterValueQuotes(script, parameters);

				writer.write(script + ";");
			}
		}
		writer.write("};");
	}

	/**
	 * Codificamos el comportamiento el evento de editar graficos en el mapa
	 * 
	 * @param FacesContext         context contexto de la aplicacion
	 * @param UIMapService         service servicio de arcgis
	 * @param ResponseWriter       writer Utilizado para escibir codigo javascript
	 * @param String               id del grafico
	 * @param PictureMarkerGraphic graphic grafico del mapa
	 * @throws IOException
	 */
	private void encodeJsfMapGraphicDragFunction(FacesContext context, UIComponent component, ResponseWriter writer)
			throws IOException {
		writer.write("gisportal.generateJsfMapGraphicDragEvent = function(graphic) {");

		List<ClientBehavior> behaviors = (List<ClientBehavior>) getClientBehaviors().get(Event.DRAG.toString());
		if ((behaviors != null) && (!behaviors.isEmpty())) {
			writer.write("var event = null;");

			writer.write("var point = esri.geometry.webMercatorToGeographic(graphic.geometry);");

			writer.write("var source = escape(dojo.toJson(graphic.source));");

			writer.write("var attribs = escape(dojo.toJson(graphic.attributes));");
			for (ClientBehavior behavior : behaviors) {
				List<ClientBehaviorContext.Parameter> parameters = new ArrayList<>();
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.uuid", "graphic.uuid"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.type", "graphic.type"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.latitude", "point.getLatitude()"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.longitude", "point.getLongitude()"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.source", "source"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.attributes", "attribs"));

				ClientBehaviorContext cbc = ClientBehaviorContext.createClientBehaviorContext(context, component,
						Event.DRAG.toString(), getClientId(), parameters);
				String script = behavior.getScript(cbc);

				script = replaceScriptParameterValueQuotes(script, parameters);

				writer.write(script + ";");
			}
		}
		writer.write("};");
	}

	/**
	 * Codificamos el comportamiento el evento de geocodificar direccion
	 * 
	 * @param FacesContext         context contexto de la aplicacion
	 * @param UIMapService         service servicio de arcgis
	 * @param ResponseWriter       writer Utilizado para escibir codigo javascript
	 * @param String               id del grafico
	 * @param PictureMarkerGraphic graphic grafico del mapa
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void encodeJsfMapGeoLocationFunction(FacesContext context, UIComponent component, ResponseWriter writer)
			throws IOException {
		writer.write("gisportal.generateJsfMapGeoLocationEvent = function(position, level) {");

		List<ClientBehavior> behaviors = (List) getClientBehaviors().get(Event.GEOLOCATION.toString());
		if ((behaviors != null) && (!behaviors.isEmpty())) {
			writer.write("var event = null;");
			for (ClientBehavior behavior : behaviors) {
				List<ClientBehaviorContext.Parameter> parameters = new ArrayList();
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.timestamp", "position.timestamp"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.latitude", "position.coords.latitude"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.longitude", "position.coords.longitude"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.zoom", "level"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.altitude",
						"gisportal.getNumericValue(position.coords.altitude, 0)"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.accuracy",
						"gisportal.getNumericValue(position.coords.accuracy, 0)"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.altitudeAccuracy",
						"gisportal.getNumericValue(position.coords.altitudeAccuracy, 0)"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.heading",
						"gisportal.getNumericValue(position.coords.heading, 0)"));
				parameters.add(new ClientBehaviorContext.Parameter("gisportal.speed",
						"gisportal.getNumericValue(position.coords.speed, 0)"));

				ClientBehaviorContext cbc = ClientBehaviorContext.createClientBehaviorContext(context, component,
						Event.GEOLOCATION.toString(), getClientId(), parameters);
				String script = behavior.getScript(cbc);

				script = replaceScriptParameterValueQuotes(script, parameters);

				writer.write(script + ";");
			}
		}
		writer.write("};");
	}

	/**
	 * Funcion que reemplaza las comillas simples en una url
	 * 
	 * @param script
	 * @param parameters
	 * @return un String con el valor remplazado
	 */
	private String replaceScriptParameterValueQuotes(String script, List<ClientBehaviorContext.Parameter> parameters) {
		for (ClientBehaviorContext.Parameter parameter : parameters) {
			String value = parameter.getValue().toString();
			if (value != null) {
			}
			script = script.replace("'" + value + "'", value);
		}
		return script;
	}
}
