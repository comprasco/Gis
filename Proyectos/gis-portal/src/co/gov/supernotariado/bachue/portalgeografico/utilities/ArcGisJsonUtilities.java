package co.gov.supernotariado.bachue.portalgeografico.utilities;

import java.io.IOException;

import co.gov.supernotariado.bachue.portalgeografico.utilities.GISUtilities;

/**
 * Clase de utilidades para lecturas de servicios JSON
 *
 */
public class ArcGisJsonUtilities {
	/**
	 * Ejecuta una petición JSON a una url predeterminada de un servicio
	 * 
	 * @param String url contiene la url
	 * @return un String con la peticion ejecutada
	 * @throws IOException
	 */
	public static final String executeJsonQuery(String url) throws IOException {
		return GISUtilities.getUrlConnectionOutput(stripUrl(url) + "?f=pjson");
	}

	/**
	 * Ejecuta una petición JSON a una url predeterminada de un servicio para obtener la leyenda
	 * 
	 * @param String url contiene la url
	 * @return un String con la leyenda
	 * @throws IOException
	 */
	public static final String executeJsonLegendQuery(String url) throws IOException {
		return GISUtilities.getUrlConnectionOutput(stripUrl(url) + "/legend?f=pjson");
	}

	/**
	 * Reemplazar caracteres especiales de la url
	 * 
	 * @param String url contiene la url
	 * @return un String con la url reemplazada
	 */
	public static final String stripUrl(String url) {
		return url.replaceAll("/$", "").replaceAll("\\?.*$", "");
	}
}
