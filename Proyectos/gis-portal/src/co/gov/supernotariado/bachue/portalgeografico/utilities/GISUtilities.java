package co.gov.supernotariado.bachue.portalgeografico.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import co.gov.supernotariado.bachue.portalgeografico.model.map.Coordinate;

/**
 * 
 * Clase de utilidades GIS
 *
 */
public class GISUtilities {
	public static final String SPATIAL_REFERENCE_WEB_MERCATOR = "102100";

	/**
	 * Lee un archivo a través de una URL y lo transforma en string
	 * 
	 * @param String spec url del archivo
	 * @return un String con informacion del archivo
	 * @throws IOException
	 */
	public static final String getUrlConnectionOutput(String spec) throws IOException {
		StringBuffer sb = new StringBuffer();
		URL url = new URL(spec);
		URLConnection connection = url.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
			sb.append(System.getProperty("line.separator"));
		}
		reader.close();
		return sb.toString();
	}

	/**
	 * Transforma en string unas coordenadas recibidas como lista
	 * 
	 * @param Lista de coodenadas coordinates
	 * @return un String con coordenadas
	 */
	public static final String formatCoordinates(List<Coordinate> coordinates) {
		StringBuffer sb = new StringBuffer();
		if ((coordinates != null) && (!coordinates.isEmpty())) {
			for (Coordinate c : coordinates) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append("[").append(c.getLongitude()).append(",").append(c.getLatitude()).append("]");
			}
		}
		sb.insert(0, "[").append("]");
		return sb.toString();
	}
}
