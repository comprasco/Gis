package co.gov.supernotariado.bachue.portalgeografico.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.supernotariado.bachue.portalgeografico.ws.utils.DateUtils;
import co.gov.supernotariado.bachue.portalgeografico.ws.utils.Limites;
import co.gov.supernotariado.bachue.portalgeografico.ws.utils.StringUtils;

/**
 * Clase que recibe numero predial y consulta en los servicios de ArcGIS el
 * predio y el mapa base
 *
 */
public class ClienteJSON {
	private static final Logger log = LoggerFactory.getLogger(ClienteJSON.class);

	/**
	 * 
	 * @param as_numeroPredial
	 * @param as_urlBase
	 * @param as_urlTask
	 * @return
	 * @throws Exception
	 */
	public static String getMap(String as_numeroPredial, String as_urlBase, String as_urlTask) throws Exception {
		String findResult = find(as_numeroPredial, as_urlBase);
		String exportResult = exportWebMapTask(findResult, as_urlBase, as_urlTask);
		String imageUrl = getURLImagen(exportResult);
		if (!imageUrl.isEmpty()) {
			return getUrlToBase64(imageUrl);
		}
		return null;
	}
	
	/**
	 * Determinar si un objeto <code>java.util.Collection</code> esta instanciado y
	 * no es vacio
	 *
	 * @param ac_c <code>java.util.Collection</code> a validar
	 * @return <code>true</code> si <i>ac_c</i> no es null y tiene elementos.
	 *         <code>false</code> de lo contrario
	 */
	public static boolean isValidCollection(Collection<String> ac_c) {
		return (ac_c != null) && !ac_c.isEmpty();
	}

	/**
	 * 
	 * @param amso_argumentos
	 * @return
	 */
	private static String getArgumentos(Map<String, Object> amso_argumentos) {
		String ls_argumentos;

		ls_argumentos = null;

		if (amso_argumentos != null) {
			Set<String> lss_argumentos;

			lss_argumentos = amso_argumentos.keySet();

			if (isValidCollection(lss_argumentos)) {
				Iterator<String> lsi_argumentos;

				lsi_argumentos = lss_argumentos.iterator();

				if (lsi_argumentos != null) {
					StringBuilder lsb_argumentos;

					lsb_argumentos = new StringBuilder();

					while (lsi_argumentos.hasNext()) {
						String ls_argumento;

						ls_argumento = lsi_argumentos.next();

						if (StringUtils.isValidString(ls_argumento)) {
							Object lo_valor;

							lo_valor = amso_argumentos.get(ls_argumento);

							lsb_argumentos.append(ls_argumento);
							lsb_argumentos.append("=");

							if (lo_valor != null)
								lsb_argumentos.append(lo_valor);

							lsb_argumentos.append("&");
						}
					}

					if (lsb_argumentos.length() > 0)
						ls_argumentos = lsb_argumentos.toString();
				}
			}
		}

		return ls_argumentos;
	}

	/**
	 * 
	 * @param as_json
	 * @return
	 * @throws Exception
	 */
	private static JSONObject getJSON(String as_json) throws Exception {
		JSONObject ljsono_json;

		ljsono_json = null;

		if (StringUtils.isValidString(as_json))
			ljsono_json = new JSONObject(as_json);

		return ljsono_json;
	}

	/**
	 * 
	 * @param ais_respuesta
	 * @return
	 * @throws IOException
	 */
	private static String getString(InputStream ais_respuesta) throws IOException {
		BufferedReader lbr_respuesta;
		StringBuilder lsb_respuesta;

		lbr_respuesta = new BufferedReader(new InputStreamReader(ais_respuesta));
		lsb_respuesta = new StringBuilder();

		{
			String ls_linea;

			while ((ls_linea = lbr_respuesta.readLine()) != null)
				lsb_respuesta.append(ls_linea);
		}

		lbr_respuesta.close();

		return lsb_respuesta.toString();
	}

	/**
	 * 
	 * @param as_urlBase
	 * @param amso_argumentos
	 * @return
	 */
	private static String getURL(String as_urlBase, Map<String, Object> amso_argumentos) {
		String ls_url;

		ls_url = null;

		{
			StringBuilder lsb_url;

			lsb_url = new StringBuilder();

			if (StringUtils.isValidString(as_urlBase)) {
				String ls_argumentos;

				lsb_url.append(as_urlBase + "/find");

				ls_argumentos = getArgumentos(amso_argumentos);

				if (StringUtils.isValidString(ls_argumentos)) {
					lsb_url.append("?");
					lsb_url.append(ls_argumentos);
				}
			}

			if (lsb_url.length() > 0)
				ls_url = lsb_url.toString();
		}

		return ls_url;
	}

	/**
	 * 
	 * @param as_respuestaExportWebMapTask
	 * @return
	 * @throws Exception
	 */
	private static String getURLImagen(String as_respuestaExportWebMapTask) throws Exception {
		JSONObject ljsono_respuestaExportWebMapTask;
		String ls_url;

		ljsono_respuestaExportWebMapTask = StringUtils.isValidString(as_respuestaExportWebMapTask)
				? getJSON(as_respuestaExportWebMapTask)
				: null;

		ls_url = null;

		if (ljsono_respuestaExportWebMapTask != null) {
			JSONArray ljsona_results;

			ljsona_results = ljsono_respuestaExportWebMapTask.getJSONArray("results");

			if ((ljsona_results != null) && (ljsona_results.length() > 0)) {
				JSONObject ljsono_tmp;

				ljsono_tmp = ljsona_results.getJSONObject(0);

				if (ljsono_tmp != null) {
					JSONObject ljsono_value;

					ljsono_value = ljsono_tmp.getJSONObject("value");

					if (ljsono_value != null)
						ls_url = ljsono_value.getString("url");
				}
			}
		}

		return ls_url;
	}

	/**
	 * Convertir url imagen a base64
	 * 
	 * @param imageUrl
	 * @return
	 */
	private static String getUrlToBase64(String imageUrl) {
		byte[] imageBytes;
		try {
			imageBytes = IOUtils.toByteArray(new URL(imageUrl));
			String base64 = Base64.getEncoder().encodeToString(imageBytes);
			return base64;
		} catch (IOException ex) {
			log.error("getUrlToBase64", ex);
			return null;
		}

	}

	/**
	 * 
	 * @param as_respuestaFind
	 * @param as_urlTask
	 * @return
	 * @throws Exception
	 */
	private static String exportWebMapTask(String as_respuestaFind, String as_urlBase, String as_urlTask)
			throws Exception {
		String ls_respuesta;

		ls_respuesta = null;

		try {
			JSONObject ljsono_respuestaFind;

			ljsono_respuestaFind = StringUtils.isValidString(as_respuestaFind) ? getJSON(as_respuestaFind) : null;

			if (ljsono_respuestaFind != null) {
				JSONArray ljsona_rings;
				JSONObject ljsono_spatialReference;
				String ls_geometryType;
				String ls_json;
				String ls_value;

				System.err.println(ljsono_respuestaFind.toString(4));

				ljsona_rings = null;
				ljsono_spatialReference = null;
				ls_geometryType = null;
				ls_json = null;
				ls_value = null;

				{
					JSONArray ljsona_results;

					ljsona_results = ljsono_respuestaFind.getJSONArray("results");

					if ((ljsona_results != null) && (ljsona_results.length() > 0)) {
						JSONObject ljsono_tmp;

						ljsono_tmp = ljsona_results.getJSONObject(0);

						if (ljsono_tmp != null) {
							ls_geometryType = ljsono_tmp.getString("geometryType");
							ls_value = ljsono_tmp.getString("value");

							{
								JSONObject ljsono_geometry;

								ljsono_geometry = ljsono_tmp.getJSONObject("geometry");

								if (ljsono_geometry != null) {
									ljsona_rings = ljsono_geometry.getJSONArray("rings");
									ljsono_spatialReference = ljsono_geometry.getJSONObject("spatialReference");
								}
							}
						}
					}
				}

				if (ljsona_rings != null) {
					JSONObject ljsono_raiz;

					ljsono_raiz = new JSONObject();

					{
						JSONObject ljsono_mapOptions;
						Limites ll_limites;

						ll_limites = new Limites(ljsona_rings);

						ll_limites.setXmin(ll_limites.getXmin() - 5d);

						ljsono_mapOptions = new JSONObject();

						ljsono_mapOptions.put("showAttribution", Boolean.FALSE);

						{
							JSONObject ljsono_extent;

							ljsono_extent = new JSONObject();

							ljsono_extent.put("xmin", new Double(ll_limites.getXmin()));
							ljsono_extent.put("ymin", new Double(ll_limites.getYmin()));
							ljsono_extent.put("xmax", new Double(ll_limites.getXmax()));
							ljsono_extent.put("ymax", new Double(ll_limites.getYmax()));

							ljsono_mapOptions.put("extent", ljsono_extent);

							ljsono_extent.put("spatialReference", ljsono_spatialReference);

							ljsono_mapOptions.put("spatialReference", ljsono_spatialReference);
						}

						{
							Double ld_escala;
							double ld_x;
							double ld_y;

							ld_x = Math.abs(ll_limites.getXmax() - ll_limites.getXmin());
							ld_y = Math.abs(ll_limites.getYmax() - ll_limites.getYmin());
							ld_escala = new Double(((ld_x > ld_y) ? ld_x : ld_y) * 6.5d);

							ljsono_mapOptions.put("scale", ld_escala);
						}

						ljsono_raiz.put("mapOptions", ljsono_mapOptions);
					}

					{
						JSONArray ljsona_operationalLayers;

						ljsona_operationalLayers = new JSONArray();

						{
							JSONObject ljsono_operationalLayer1;

							ljsono_operationalLayer1 = new JSONObject();

							ljsono_operationalLayer1.put("id", "layer1");
							ljsono_operationalLayer1.put("title", "layer1");
							ljsono_operationalLayer1.put("opacity", new Integer(1));
							ljsono_operationalLayer1.put("minScale", new Integer(0));
							ljsono_operationalLayer1.put("maxScale", new Integer(0));
							ljsono_operationalLayer1.put("url",
									"https://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer");

							ljsona_operationalLayers.put(ljsono_operationalLayer1);
						}

						{
							JSONObject ljsono_operationalLayer2;

							ljsono_operationalLayer2 = new JSONObject();

							ljsono_operationalLayer2.put("id", "sanVicente");
							ljsono_operationalLayer2.put("title", "sanVicente");
							ljsono_operationalLayer2.put("opacity", new Double(0.75d));
							ljsono_operationalLayer2.put("minScale", new Integer(0));
							ljsono_operationalLayer2.put("maxScale", new Integer(0));
							ljsono_operationalLayer2.put("url", as_urlBase);

							{
								JSONArray ljsona_layers;

								ljsona_layers = new JSONArray();

								for (int li_i = 1; li_i <= 8; li_i++) {
									if (li_i == 6) {
										JSONObject ljsono_layer;

										ljsono_layer = new JSONObject();

										ljsono_layer.put("id", new Integer(li_i));

										{
											JSONObject ljsono_layerDefinition;

											ljsono_layerDefinition = new JSONObject();

											{
												JSONObject ljsono_source;

												ljsono_source = new JSONObject();

												ljsono_source.put("type", "mapLayer");
												ljsono_source.put("mapLayerId", new Integer(li_i));

												ljsono_layerDefinition.put("source", ljsono_source);
											}

											ljsono_layer.put("layerDefinition", ljsono_layerDefinition);
										}

										ljsona_layers.put(ljsono_layer);
									}
								}

								ljsono_operationalLayer2.put("layers", ljsona_layers);
							}

							ljsona_operationalLayers.put(ljsono_operationalLayer2);
						}

						{
							JSONObject ljsono_operationalLayer3;

							ljsono_operationalLayer3 = new JSONObject();

							ljsono_operationalLayer3.put("id", "find_widget_findGraphics");
							ljsono_operationalLayer3.put("opacity", new Integer(1));
							ljsono_operationalLayer3.put("minScale", new Integer(0));
							ljsono_operationalLayer3.put("maxScale", new Integer(0));

							{
								JSONObject ljsono_featureCollection;

								ljsono_featureCollection = new JSONObject();

								{
									JSONArray ljsona_layers;

									ljsona_layers = new JSONArray();

									{
										JSONObject ljsono_layer;

										ljsono_layer = new JSONObject();

										{
											JSONObject ljsono_layerDefinition;

											ljsono_layerDefinition = new JSONObject();

											if (StringUtils.isValidString(ls_value))
												ljsono_layerDefinition.put("name", ls_value);

											if (StringUtils.isValidString(ls_geometryType))
												ljsono_layerDefinition.put("geometryType", ls_geometryType);

											ljsono_layer.put("layerDefinition", ljsono_layerDefinition);
										}

										{
											JSONObject ljsono_featureSet;

											ljsono_featureSet = new JSONObject();

											if (StringUtils.isValidString(ls_geometryType))
												ljsono_featureSet.put("geometryType", ls_geometryType);

											{
												JSONArray ljsona_features;

												ljsona_features = new JSONArray();

												{
													JSONObject ljsono_tmp1;

													ljsono_tmp1 = new JSONObject();

													{
														JSONObject ljsono_geometry;

														ljsono_geometry = new JSONObject();

														if (ljsona_rings != null)
															ljsono_geometry.put("rings", ljsona_rings);

														ljsono_geometry.put("spatialReference",
																ljsono_spatialReference);

														ljsono_tmp1.put("geometry", ljsono_geometry);
													}

													{
														JSONObject ljsono_symbol;

														ljsono_symbol = new JSONObject();

														{
															JSONArray ljsona_color;

															ljsona_color = new JSONArray();

															ljsona_color.put(new Integer(0));
															ljsona_color.put(new Integer(50));
															ljsona_color.put(new Integer(255));
															ljsona_color.put(new Integer(32));

															ljsono_symbol.put("color", ljsona_color);
														}

														{
															JSONObject ljsono_outline;

															ljsono_outline = new JSONObject();

															{
																JSONArray ljsona_color;

																ljsona_color = new JSONArray();

																ljsona_color.put(new Integer(0));
																ljsona_color.put(new Integer(255));
																ljsona_color.put(new Integer(255));
																ljsona_color.put(new Integer(255));

																ljsono_outline.put("color", ljsona_color);
															}

															ljsono_outline.put("width", new Integer(1));
															ljsono_outline.put("type", "esriSLS");
															ljsono_outline.put("style", "esriSLSSolid");

															ljsono_symbol.put("outline", ljsono_outline);
														}

														ljsono_symbol.put("type", "esriSFS");
														ljsono_symbol.put("style", "esriSFSSolid");

														ljsono_tmp1.put("symbol", ljsono_symbol);
													}

													ljsona_features.put(ljsono_tmp1);
												}

												ljsono_featureSet.put("features", ljsona_features);
											}

											ljsono_layer.put("featureSet", ljsono_featureSet);
										}

										ljsona_layers.put(ljsono_layer);
									}

									ljsono_featureCollection.put("layers", ljsona_layers);
								}

								ljsono_operationalLayer3.put("featureCollection", ljsono_featureCollection);
							}

							ljsona_operationalLayers.put(ljsono_operationalLayer3);
						}

						ljsono_raiz.put("operationalLayers", ljsona_operationalLayers);
					}

					{
						JSONObject ljsono_exportOptions;

						ljsono_exportOptions = new JSONObject();
						/*
						 * { JSONArray ljsona_color;
						 * 
						 * ljsona_color = new JSONArray();
						 * 
						 * ljsona_color.put(new Integer(330)); ljsona_color.put(new Integer(255));
						 * 
						 * ljsono_exportOptions.put("outputSize", ljsona_color); }
						 */
						ljsono_exportOptions.put("dpi", new Integer(256));

						ljsono_raiz.put("exportOptions", ljsono_exportOptions);
					}

					{
						JSONObject ljsono_layoutOptions;

						ljsono_layoutOptions = new JSONObject();

						ljsono_layoutOptions.put("titleText", "Información Predio Registremos");
						ljsono_layoutOptions.put("authorText", "Registremos");

						{
							StringBuilder lsb_sb;

							lsb_sb = new StringBuilder();

							lsb_sb.append("Copyright ");

							{
								Calendar lc_today;

								lc_today = DateUtils.getCalendar();

								lsb_sb.append(lc_today.get(Calendar.YEAR));
							}

							ljsono_layoutOptions.put("copyrightText", lsb_sb.toString());
						}

						{
							JSONObject ljsono_scaleBarOptions;

							ljsono_scaleBarOptions = new JSONObject();

							ljsono_scaleBarOptions.put("metricUnit", "esriKilometers");
							ljsono_scaleBarOptions.put("metricLabel", "km");
							ljsono_scaleBarOptions.put("nonMetricUnit", "esriMiles");
							ljsono_scaleBarOptions.put("nonMetricLabel", "mi");

							ljsono_layoutOptions.put("scaleBarOptions", ljsono_scaleBarOptions);
						}
						ljsono_raiz.put("layoutOptions", ljsono_layoutOptions);
					}

					ls_json = ljsono_raiz.toString(0);
				}

				if (StringUtils.isValidString(ls_json)) {
					Map<String, Object> lmso_argumentos;

					lmso_argumentos = new HashMap<String, Object>();

					lmso_argumentos.put(new String("f"), new String("json"));
					lmso_argumentos.put(new String("Format"), new String("PNG32"));
					lmso_argumentos.put(new String("Layout_Template"), new String("Letter%20ANSI%20A%20Landscape"));
					String webMapJSON = java.net.URLEncoder.encode(ls_json, "UTF-8");
					webMapJSON = webMapJSON.replace("%5C%2F", "%2F");
					lmso_argumentos.put(new String("Web_Map_as_JSON"), webMapJSON);

					ls_respuesta = invocarServicio(getURL(as_urlTask, lmso_argumentos));
				}
			}
		} catch (UnsupportedEncodingException ex) {
			log.error("exportWebMapTask", ex);
		}

		return ls_respuesta;
	}

	/**
	 * 
	 * @param as_numeroPredial
	 * @param as_urlBase
	 * @return
	 * @throws Exception
	 */
	private static String find(String as_numeroPredial, String as_urlBase) throws Exception {
		Map<String, Object> lmso_argumentos;

		lmso_argumentos = new HashMap<String, Object>();

		lmso_argumentos.put(new String("contains"), Boolean.TRUE);
		lmso_argumentos.put(new String("f"), new String("json"));
		lmso_argumentos.put(new String("layers"), new String("2,6"));
		lmso_argumentos.put(new String("returnGeometry"), Boolean.TRUE);
		lmso_argumentos.put(new String("searchFields"), new String("codigo"));
		lmso_argumentos.put(new String("searchText"), as_numeroPredial);
		lmso_argumentos.put(new String("sr"), new String("102100"));

		return invocarServicio(getURL(as_urlBase, lmso_argumentos));
	}

	/**
	 * 
	 * @param as_url
	 * @return
	 * @throws Exception
	 */
	private static String invocarServicio(String as_url) throws Exception {
		String ls_respuesta;

		ls_respuesta = null;

		if (StringUtils.isValidString(as_url)) {
			InputStream lis_respuesta;

			lis_respuesta = new URL(as_url).openStream();

			if (lis_respuesta != null)
				ls_respuesta = getString(lis_respuesta);

			lis_respuesta.close();
		}

		return ls_respuesta;
	}
}
