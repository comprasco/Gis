package co.gov.supernotariado.bachue.portalgeografico.utilities;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONException;
import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONObject;

/**
 * 
 * Clase de utilidades GIS para los String
 *
 */
public class StringUtilities {
	/**
	 * metodo toMap para String
	 * 
	 * @param String json mensaje en formato json
	 * @return un mapa de String y object con los valores del mapa
	 */
	public static final Map<String, Object> toMap(String json) {
		Map<String, Object> map = null;
		try {
			JSONObject jo = new JSONObject(json);
			Iterator<?> iter = jo.keys();
			if (iter.hasNext()) {
				map = new HashMap<String, Object>();
				while (iter.hasNext()) {
					String key = (String) iter.next();
					Object value = jo.get(key);

					map.put(key, value);
				}
			}
		} catch (JSONException localJSONException) {
		}
		return map;
	}

	/**
	 * metodo toJson para String
	 * 
	 * @param map mapa de String y object con los valores del mapa
	 * @return un String del objeto recibido
	 */
	public static final String toJson(Map<String, Object> map) {
		return new JSONObject(map).toString();
	}

	/**
	 * metodo join para String
	 * 
	 * @param Iterator iter iteracion, String delimiter delimitador
	 * @return un String con la conversion de los valores recibidos
	 */
	public static final String join(Iterator<String> iter, String delimiter) {
		StringBuffer sb = new StringBuffer();
		if (iter != null) {
			while (iter.hasNext()) {
				if (sb.length() != 0) {
					sb.append(delimiter);
				}
				sb.append((String) iter.next());
			}
		}
		return sb.toString();
	}

	/**
	 * metodo defaultString
	 * 
	 * @param String s
	 * @return un String del valor recibido
	 */
	public static final String defaultString(String s) {
		return s != null ? s : "";
	}

	/**
	 * metodo defaultString
	 * 
	 * @param String s, String def valores por defecto
	 * @return
	 */
	public static final String defaultString(String s, String def) {
		return s != null ? s : def;
	}

	/**
	 * metodo toString
	 * 
	 * @param Object o objeto
	 * @return un String del objeto recibido
	 */
	public static final String toString(Object o) {
		return o != null ? o.toString() : "";
	}

	/**
	 * metodo encode codificacion utf8
	 * 
	 * @param String s valor
	 * @return un String con la codificacion
	 */
	public static final String encode(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20");
		} catch (Exception e) {
		}
		return s;
	}

	/**
	 * metodo decode decodificacion utf8
	 * 
	 * @param String s valor
	 * @return un Strin con la decodificacion
	 */
	public static final String decode(String s) {
		try {
			return URLDecoder.decode(s.replaceAll("\\%20", "+"), "UTF-8");
		} catch (Exception e) {
		}
		return s;
	}
}
