package co.gov.supernotariado.bachue.portalgeografico.utilities.json;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * 
 * Clase de componentes json array
 *
 */
public class JSONArray {
	/**
	 * Variable myArrayList ArrayList que contiene una lista de arreglos.
	 */
	private ArrayList<Object> myArrayList;

	/**
	 * Constructor de la clase JSONArray
	 */
	public JSONArray() {
		this.myArrayList = new ArrayList<Object>();
	}

	/**
	 * Constructor de la clase JSONArray
	 * 
	 * @param JSONTokener x token de json
	 */
	public JSONArray(JSONTokener x) throws JSONException {
		this();
		if (x.nextClean() != '[') {
			throw x.syntaxError("A JSONArray text must start with '['");
		}
		if (x.nextClean() != ']') {
			x.back();
			for (;;) {
				if (x.nextClean() == ',') {
					x.back();
					this.myArrayList.add(JSONObject.NULL);
				} else {
					x.back();
					this.myArrayList.add(x.nextValue());
				}
				switch (x.nextClean()) {
				case ',':
					if (x.nextClean() == ']') {
						return;
					}
					x.back();
					break;
				case ']':
					return;
				default:
					throw x.syntaxError("Expected a ',' or ']'");
				}
			}
		}
	}

	/**
	 * Constructor de la clase JSONArray
	 * 
	 * @param String source recurso
	 */
	public JSONArray(String source) throws JSONException {
		this(new JSONTokener(source));
	}

	/**
	 * Constructor de la clase JSONArray
	 * 
	 * @param Collection collection coleccion
	 */
	public JSONArray(Collection<Object> collection) {
		this.myArrayList = (collection == null ? new ArrayList<Object>() : new ArrayList<Object>(collection));
	}

	/**
	 * Constructor de la clase JSONArray
	 * 
	 * @param Object array arreglo
	 */
	public JSONArray(Object array) throws JSONException {
		this();
		if (array.getClass().isArray()) {
			int length = Array.getLength(array);
			for (int i = 0; i < length; i++) {
				put(Array.get(array, i));
			}
		} else {
			throw new JSONException("JSONArray initial value should be a string or collection or array.");
		}
	}

	/**
	 * Constructor de la clase JSONArray
	 * 
	 * @param Object array arreglo, boolean includeSuperClass incluye super clase
	 */
	public JSONArray(Object array, boolean includeSuperClass) throws JSONException {
		this();
		if (array.getClass().isArray()) {
			int length = Array.getLength(array);
			for (int i = 0; i < length; i++) {
				Object o = Array.get(array, i);
				if (JSONObject.isStandardProperty(o.getClass())) {
					this.myArrayList.add(o);
				} else {
					this.myArrayList.add(new JSONObject(o, includeSuperClass));
				}
			}
		} else {
			throw new JSONException("JSONArray initial value should be a string or collection or array.");
		}
	}

	/**
	 * Metodo que obtiene el objeto
	 * 
	 * @param int index indice
	 * @return un Object objeto
	 */
	public Object get(int index) throws JSONException {
		Object o = opt(index);
		if (o == null) {
			throw new JSONException("JSONArray[" + index + "] not found.");
		}
		return o;
	}

	/**
	 * Metodo que obtiene el estado
	 * 
	 * @param int index indice
	 * @return un boolean del estado
	 */
	public boolean getBoolean(int index) throws JSONException {
		Object o = get(index);
		if (!o.equals(Boolean.FALSE)) {
			if ((o instanceof String)) {
				if (!((String) o).equalsIgnoreCase("false")) {
				}
			}
		} else {
			return false;
		}
		if (!o.equals(Boolean.TRUE)) {
			if ((o instanceof String)) {
				if (!((String) o).equalsIgnoreCase("true")) {
				}
			}
		} else {
			return true;
		}
		throw new JSONException("JSONArray[" + index + "] is not a Boolean.");
	}

	/**
	 * Metodo que obtiene el valor
	 * 
	 * @param int index indice
	 * @return un double del valor
	 */
	public double getDouble(int index) throws JSONException {
		Object o = get(index);
		try {
			return (o instanceof Number) ? ((Number) o).doubleValue() : Double.valueOf((String) o).doubleValue();
		} catch (Exception e) {
			throw new JSONException("JSONArray[" + index + "] is not a number.");
		}
	}

	/**
	 * Metodo que obtiene el valor
	 * 
	 * @param int index indice
	 * @return un int del valor
	 */
	public int getInt(int index) throws JSONException {
		Object o = get(index);
		return (o instanceof Number) ? ((Number) o).intValue() : (int) getDouble(index);
	}

	/**
	 * Metodo que obtiene un arreglo en formato json
	 * 
	 * @param int index indice
	 * @return unJSONArray arreglo en formato json
	 */
	public JSONArray getJSONArray(int index) throws JSONException {
		Object o = get(index);
		if ((o instanceof JSONArray)) {
			return (JSONArray) o;
		}
		throw new JSONException("JSONArray[" + index + "] is not a JSONArray.");
	}

	/**
	 * Metodo que obtiene un objeto en formato json
	 * 
	 * @param int index indice
	 * @return JSONObject objeto en formato json
	 */
	public JSONObject getJSONObject(int index) throws JSONException {
		Object o = get(index);
		if ((o instanceof JSONObject)) {
			return (JSONObject) o;
		}
		throw new JSONException("JSONArray[" + index + "] is not a JSONObject.");
	}

	/**
	 * Metodo que obtiene el valor
	 * 
	 * @param int index indice
	 * @return un long del valor
	 */
	public long getLong(int index) throws JSONException {
		Object o = get(index);
		return (long) ((o instanceof Number) ? ((Number) o).longValue() : getDouble(index));
	}

	/**
	 * Metodo que obtiene un String
	 * 
	 * @param int index indice
	 * @return un String
	 */
	public String getString(int index) throws JSONException {
		return get(index).toString();
	}

	/**
	 * Metodo que compara si es nulo
	 * 
	 * @param int index indice
	 * @return un boolean con el estado
	 */
	public boolean isNull(int index) {
		return JSONObject.NULL.equals(opt(index));
	}

	/**
	 * Metodo union
	 * 
	 * @param String separator separador
	 * @return un String con la union
	 */
	public String join(String separator) throws JSONException {
		int len = length();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			if (i > 0) {
				sb.append(separator);
			}
			sb.append(JSONObject.valueToString(this.myArrayList.get(i)));
		}
		return sb.toString();
	}

	/**
	 * Metodo de longitud
	 * 
	 * @return un int con la longitud
	 */
	public int length() {
		return this.myArrayList.size();
	}

	/**
	 * Metodo optener
	 * 
	 * @param int index indice
	 * @return un Object objeto
	 */
	public Object opt(int index) {
		return (index < 0) || (index >= length()) ? null : this.myArrayList.get(index);
	}

	/**
	 * Metodo opt estado
	 * 
	 * @param int index indice
	 * @return un boolean del estado
	 */
	public boolean optBoolean(int index) {
		return optBoolean(index, false);
	}

	/**
	 * Metodo opt estado
	 * 
	 * @param int index indice, boolean defaultValue estado del valor
	 * @return un boolean del estado
	 */
	public boolean optBoolean(int index, boolean defaultValue) {
		try {
			return getBoolean(index);
		} catch (Exception e) {
		}
		return defaultValue;
	}

	/**
	 * Metodo opt valor
	 * 
	 * @param int index indice
	 * @return un double con el valor
	 */
	public double optDouble(int index) {
		return optDouble(index, Double.NaN);
	}

	/**
	 * Metodo opt valor
	 * 
	 * @param int index indice, double defaultValue valor por defecto
	 * @return un double con el valor
	 */
	public double optDouble(int index, double defaultValue) {
		try {
			return getDouble(index);
		} catch (Exception e) {
		}
		return defaultValue;
	}

	/**
	 * Metodo opt valor
	 * 
	 * @param int index indice
	 * @return un int con el valor
	 */
	public int optInt(int index) {
		return optInt(index, 0);
	}

	/**
	 * Metodo opt valor
	 * 
	 * @param int index indice, int defaultValue valor por defecto
	 * @return un int con el valor
	 */
	public int optInt(int index, int defaultValue) {
		try {
			return getInt(index);
		} catch (Exception e) {
		}
		return defaultValue;
	}

	/**
	 * Metodo opt arreglo en formato json
	 * 
	 * @param int index indice
	 * @return un arreglo en formato json
	 */
	public JSONArray optJSONArray(int index) {
		Object o = opt(index);
		return (o instanceof JSONArray) ? (JSONArray) o : null;
	}

	/**
	 * Metodo opt arreglo en formato json
	 * 
	 * @param int index indice
	 * @return un arreglo en formato json
	 */
	public JSONObject optJSONObject(int index) {
		Object o = opt(index);
		return (o instanceof JSONObject) ? (JSONObject) o : null;
	}

	/**
	 * Metodo opt valor
	 * 
	 * @param int index indice
	 * @return un long con el valor
	 */
	public long optLong(int index) {
		return optLong(index, 0L);
	}

	/**
	 * Metodo opt valor
	 * 
	 * @param int index indice, long defaultValue valor por defecto
	 * @return un long con el valor
	 */
	public long optLong(int index, long defaultValue) {
		try {
			return getLong(index);
		} catch (Exception e) {
		}
		return defaultValue;
	}

	/**
	 * Metodo opt String
	 * 
	 * @param int index indice
	 * @return un String
	 */
	public String optString(int index) {
		return optString(index, "");
	}

	/**
	 * Metodo opt String
	 * 
	 * @param int index indice, String defaultValue valor por defecto
	 * @return un String
	 */
	public String optString(int index, String defaultValue) {
		Object o = opt(index);
		return o != null ? o.toString() : defaultValue;
	}

	/**
	 * Metodo colocar un arreglo en formato json
	 * 
	 * @param boolean value estado del valor
	 * @return un JSONArray arreglo en formato json
	 */
	public JSONArray put(boolean value) {
		put(value ? Boolean.TRUE : Boolean.FALSE);
		return this;
	}

	/**
	 * Metodo colocar un arreglo en formato json
	 * 
	 * @param Collection value valor de la coleccion
	 * @return un JSONArray arreglo en formato json
	 */
	public JSONArray put(Collection<Object> value) {
		put(new JSONArray(value));
		return this;
	}

	/**
	 * Metodo colocar un arreglo en formato json
	 * 
	 * @param double value valor
	 * @return un JSONArray arreglo en formato json
	 */
	public JSONArray put(double value) throws JSONException {
		Double d = new Double(value);
		JSONObject.testValidity(d);
		put(d);
		return this;
	}

	/**
	 * Metodo colocar un arreglo en formato json
	 * 
	 * @param int value valor
	 * @return un JSONArray arreglo en formato json
	 */
	public JSONArray put(int value) {
		put(new Integer(value));
		return this;
	}

	/**
	 * Metodo colocar un arreglo en formato json
	 * 
	 * @param long value valor
	 * @return un JSONArray arreglo en formato json
	 */
	public JSONArray put(long value) {
		put(new Long(value));
		return this;
	}

	/**
	 * Metodo colocar un arreglo en formato json
	 * 
	 * @param Map value valor
	 * @return un JSONArray arreglo en formato json
	 */
	public JSONArray put(Map<Object, Object> value) {
		put(new JSONObject(value));
		return this;
	}

	/**
	 * Metodo colocar un arreglo en formato json
	 * 
	 * @param object value valor
	 * @return un JSONArray arreglo en formato json
	 */
	public JSONArray put(Object value) {
		this.myArrayList.add(value);
		return this;
	}

	/**
	 * Metodo colocar un arreglo en formato json
	 * 
	 * @param int index indice, boolean value estado del valor
	 * @return un JSONArray arreglo en formato json
	 */
	public JSONArray put(int index, boolean value) throws JSONException {
		put(index, value ? Boolean.TRUE : Boolean.FALSE);
		return this;
	}

	/**
	 * Metodo colocar un arreglo en formato json
	 * 
	 * @param int index indice, Collection value valor de la coleccion
	 * @return un JSONArray arreglo en formato json
	 */
	public JSONArray put(int index, Collection<Object> value) throws JSONException {
		put(index, new JSONArray(value));
		return this;
	}

	/**
	 * Metodo colocar un arreglo en formato json
	 * 
	 * @param int index indice, double value valor
	 * @return un JSONArray arreglo en formato json
	 */
	public JSONArray put(int index, double value) throws JSONException {
		put(index, new Double(value));
		return this;
	}

	/**
	 * Metodo colocar un arreglo en formato json
	 * 
	 * @param int index indice, int value valor
	 * @return un JSONArray arreglo en formato json
	 */
	public JSONArray put(int index, int value) throws JSONException {
		put(index, new Integer(value));
		return this;
	}

	/**
	 * Metodo colocar un arreglo en formato json
	 * 
	 * @param int index indice, long value valor
	 * @return un JSONArray arreglo en formato json
	 */
	public JSONArray put(int index, long value) throws JSONException {
		put(index, new Long(value));
		return this;
	}

	/**
	 * Metodo colocar un arreglo en formato json
	 * 
	 * @param int index indice, Map value valor
	 * @return un JSONArray arreglo en formato json
	 */
	public JSONArray put(int index, Map<Object, Object> value) throws JSONException {
		put(index, new JSONObject(value));
		return this;
	}

	/**
	 * Metodo colocar un arreglo en formato json
	 * 
	 * @param int index indice, Object value valor
	 * @return un JSONArray arreglo en formato json
	 */
	public JSONArray put(int index, Object value) throws JSONException {
		JSONObject.testValidity(value);
		if (index < 0) {
			throw new JSONException("JSONArray[" + index + "] not found.");
		}
		if (index < length()) {
			this.myArrayList.set(index, value);
		} else {
			while (index != length()) {
				put(JSONObject.NULL);
			}
			put(value);
		}
		return this;
	}

	/**
	 * Metodo remover
	 * 
	 * @param int index indice
	 */
	public Object remove(int index) {
		Object o = opt(index);
		this.myArrayList.remove(index);
		return o;
	}

	/**
	 * Metodo que genera un objeto json
	 * 
	 * @param JSONArray names nombres
	 * @return un JSONObject objeto json
	 */
	public JSONObject toJSONObject(JSONArray names) throws JSONException {
		if ((names == null) || (names.length() == 0) || (length() == 0)) {
			return null;
		}
		JSONObject jo = new JSONObject();
		for (int i = 0; i < names.length(); i++) {
			jo.put(names.getString(i), opt(i));
		}
		return jo;
	}

	/**
	 * Metodo que retorna un String
	 * 
	 * @return un String
	 */
	public String toString() {
		try {
			return '[' + join(",") + ']';
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * Metodo que retorna un String
	 * 
	 * @param int indentFactor factor de identificacion
	 * @return un String
	 */
	public String toString(int indentFactor) throws JSONException {
		return toString(indentFactor, 0);
	}

	/**
	 * Metodo que retorna un String
	 * 
	 * @param int indentFactor factor de identificacion, int indent identificador
	 * @return un String
	 */
	String toString(int indentFactor, int indent) throws JSONException {
		int len = length();
		if (len == 0) {
			return "[]";
		}
		StringBuffer sb = new StringBuffer("[");
		if (len == 1) {
			sb.append(JSONObject.valueToString(this.myArrayList.get(0), indentFactor, indent));
		} else {
			int newindent = indent + indentFactor;
			sb.append('\n');
			for (int i = 0; i < len; i++) {
				if (i > 0) {
					sb.append(",\n");
				}
				for (int j = 0; j < newindent; j++) {
					sb.append(' ');
				}
				sb.append(JSONObject.valueToString(this.myArrayList.get(i), indentFactor, newindent));
			}
			sb.append('\n');
			for (int i = 0; i < indent; i++) {
				sb.append(' ');
			}
		}
		sb.append(']');
		return sb.toString();
	}

	/**
	 * Metodo de escritura
	 * 
	 * @param Writer writer valor
	 * @return un Writer una escritura
	 */
	public Writer write(Writer writer) throws JSONException {
		try {
			boolean b = false;
			int len = length();

			writer.write(91);
			for (int i = 0; i < len; i++) {
				if (b) {
					writer.write(44);
				}
				Object v = this.myArrayList.get(i);
				if ((v instanceof JSONObject)) {
					((JSONObject) v).write(writer);
				} else if ((v instanceof JSONArray)) {
					((JSONArray) v).write(writer);
				} else {
					writer.write(JSONObject.valueToString(v));
				}
				b = true;
			}
			writer.write(93);
			return writer;
		} catch (IOException e) {
			throw new JSONException(e);
		}
	}
}
