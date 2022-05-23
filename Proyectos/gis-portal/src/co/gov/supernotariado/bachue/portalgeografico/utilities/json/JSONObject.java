package co.gov.supernotariado.bachue.portalgeografico.utilities.json;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONArray;
import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONException;
import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONObject;
import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONString;
import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONTokener;

/**
 * 
 * Clase de objetos json
 *
 */
public class JSONObject {

	/**
	 * Variable map Map que contiene mapeo.
	 */
	private Map<Object, Object> map;

	/**
	 * 
	 * Clase estatica de valor nulo
	 *
	 */
	private static final class Null {
		/**
		 * Metodo de clonacion
		 * 
		 * @return un Object objecto
		 */
		protected final Object clone() {
			return this;
		}

		/**
		 * Metodo de igualacion
		 * 
		 * @param Object object objeto
		 * @return un boolean con el estado de la igualacion
		 */
		public boolean equals(Object object) {
			return (object == null) || (object == this);
		}

		/**
		 * Metodo String
		 * 
		 * @return un String nulo
		 */
		public String toString() {
			return "null";
		}
	}

	/**
	 * Atributo NULL Object que contiene un objeto nulo.
	 */
	public static final Object NULL = new Null();

	/**
	 * Constructor de la clase JSONObject
	 */
	public JSONObject() {
		this.map = new HashMap<Object, Object>();
	}

	/**
	 * Constructor de la clase JSONObject
	 * 
	 * @param JSONObject jo objeto json, String[] names arreglo de nombres
	 */
	public JSONObject(JSONObject jo, String[] names) throws JSONException {
		this();
		for (int i = 0; i < names.length; i++) {
			putOnce(names[i], jo.opt(names[i]));
		}
	}

	/**
	 * Constructor de la clase JSONObject
	 * 
	 * @param JSONTokener x token json
	 */
	public JSONObject(JSONTokener x) throws JSONException {
		this();
		if (x.nextClean() != '{') {
			throw x.syntaxError("A JSONObject text must begin with '{'");
		}
		for (;;) {
			char c = x.nextClean();
			switch (c) {
			case '\000':
				throw x.syntaxError("A JSONObject text must end with '}'");
			case '}':
				return;
			}
			x.back();
			String key = x.nextValue().toString();

			c = x.nextClean();
			if (c == '=') {
				if (x.next() != '>') {
					x.back();
				}
			} else if (c != ':') {
				throw x.syntaxError("Expected a ':' after a key");
			}
			putOnce(key, x.nextValue());
			switch (x.nextClean()) {
			case ',':
			case ';':
				if (x.nextClean() == '}') {
					return;
				}
				x.back();
			}
		}

	}

	/**
	 * Constructor de la clase JSONObject
	 * 
	 * @param Map map mapeo
	 */
	public JSONObject(Map<Object, Object> map) {
		this.map = (map == null ? new HashMap<Object, Object>() : map);
	}

	@SuppressWarnings("rawtypes")
	public JSONObject(Map<Object, Object> map, boolean includeSuperClass) {
		this.map = new HashMap<Object, Object>();
		if (map != null) {
			Iterator<?> i = map.entrySet().iterator();
			while (i.hasNext()) {
				Map.Entry e = (Map.Entry) i.next();
				if (isStandardProperty(e.getValue().getClass())) {
					this.map.put(e.getKey(), e.getValue());
				} else {
					this.map.put(e.getKey(), new JSONObject(e.getValue(), includeSuperClass));
				}
			}
		}
	}

	/**
	 * Constructor de la clase JSONObject
	 * 
	 * @param Object bean objeto
	 */
	public JSONObject(Object bean) {
		this();
		populateInternalMap(bean, false);
	}

	/**
	 * Constructor de la clase JSONObject
	 * 
	 * @param Object bean objeto, boolean includeSuperClass estado de la super clase
	 */
	public JSONObject(Object bean, boolean includeSuperClass) {
		this();
		populateInternalMap(bean, includeSuperClass);
	}

	/**
	 * Metodo que llena el mapa interno
	 * 
	 * @param Object bean objeto, boolean includeSuperClass estado de la super clase
	 */
	@SuppressWarnings("unchecked")
	private void populateInternalMap(Object bean, boolean includeSuperClass) {
		Class<? extends Object> klass = bean.getClass();
		if (klass.getClassLoader() == null) {
			includeSuperClass = false;
		}
		Method[] methods = includeSuperClass ? klass.getMethods() : klass.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			try {
				Method method = methods[i];
				if (Modifier.isPublic(method.getModifiers())) {
					String name = method.getName();
					String key = "";
					if (name.startsWith("get")) {
						key = name.substring(3);
					} else if (name.startsWith("is")) {
						key = name.substring(2);
					}
					if ((key.length() > 0) && (Character.isUpperCase(key.charAt(0)))
							&& (method.getParameterTypes().length == 0)) {
						if (key.length() == 1) {
							key = key.toLowerCase();
						} else if (!Character.isUpperCase(key.charAt(1))) {
							key = key.substring(0, 1).toLowerCase() + key.substring(1);
						}
						Object result = method.invoke(bean, (Object[]) null);
						if (result == null) {
							this.map.put(key, NULL);
						} else if (result.getClass().isArray()) {
							this.map.put(key, new JSONArray(result, includeSuperClass));
						} else if ((result instanceof Collection)) {
							this.map.put(key, new JSONArray((Collection<?>) result, includeSuperClass));
						} else if ((result instanceof Map)) {
							this.map.put(key, new JSONObject((Map<Object, Object>) result, includeSuperClass));
						} else if (isStandardProperty(result.getClass())) {
							this.map.put(key, result);
						} else if ((result.getClass().getPackage().getName().startsWith("java"))
								|| (result.getClass().getClassLoader() == null)) {
							this.map.put(key, result.toString());
						} else {
							this.map.put(key, new JSONObject(result, includeSuperClass));
						}
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Metodo que valida la propiedad
	 * 
	 * @param Class clazz clase
	 * @return un boolean con el estado
	 */
	static boolean isStandardProperty(Class<? extends Object> clazz) {
		return (clazz.isPrimitive()) || (clazz.isAssignableFrom(Byte.class)) || (clazz.isAssignableFrom(Short.class))
				|| (clazz.isAssignableFrom(Integer.class)) || (clazz.isAssignableFrom(Long.class))
				|| (clazz.isAssignableFrom(Float.class)) || (clazz.isAssignableFrom(Double.class))
				|| (clazz.isAssignableFrom(Character.class)) || (clazz.isAssignableFrom(String.class))
				|| (clazz.isAssignableFrom(Boolean.class));
	}

	/**
	 * Constructor de la clase JSONObject
	 * 
	 * @param Object object objeto, String[] names nombres
	 */
	public JSONObject(Object object, String[] names) {
		this();
		Class<? extends Object> c = object.getClass();
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			try {
				putOpt(name, c.getField(name).get(object));
			} catch (Exception localException) {
			}
		}
	}

	/**
	 * Constructor de la clase JSONObject
	 * 
	 * @param String source recurso
	 */
	public JSONObject(String source) throws JSONException {
		this(new JSONTokener(source));
	}

	/**
	 * Metodo que acumula
	 * 
	 * @param String key llave, Object value valor
	 * @return un JSONObject objeto json
	 */
	public JSONObject accumulate(String key, Object value) throws JSONException {
		testValidity(value);
		Object o = opt(key);
		if (o == null) {
			put(key, (value instanceof JSONArray) ? new JSONArray().put(value) : value);
		} else if ((o instanceof JSONArray)) {
			((JSONArray) o).put(value);
		} else {
			put(key, new JSONArray().put(o).put(value));
		}
		return this;
	}

	/**
	 * Metodo que agrega
	 * 
	 * @param String key llave, Object value valor
	 * @return un JSONObject objeto json
	 */
	public JSONObject append(String key, Object value) throws JSONException {
		testValidity(value);
		Object o = opt(key);
		if (o == null) {
			put(key, new JSONArray().put(value));
		} else if ((o instanceof JSONArray)) {
			put(key, ((JSONArray) o).put(value));
		} else {
			throw new JSONException("JSONObject[" + key + "] is not a JSONArray.");
		}
		return this;
	}

	/**
	 * Metodo que convierte de double a String
	 * 
	 * @param double d valor
	 * @return un String con el valor
	 */
	public static String doubleToString(double d) {
		if ((Double.isInfinite(d)) || (Double.isNaN(d))) {
			return "null";
		}
		String s = Double.toString(d);
		if ((s.indexOf('.') > 0) && (s.indexOf('e') < 0) && (s.indexOf('E') < 0)) {
			while (s.endsWith("0")) {
				s = s.substring(0, s.length() - 1);
			}
			if (s.endsWith(".")) {
				s = s.substring(0, s.length() - 1);
			}
		}
		return s;
	}

	/**
	 * Metodo que obtiene un objeto
	 * 
	 * @param String key llave
	 * @return un Object objeto
	 */
	public Object get(String key) throws JSONException {
		Object o = opt(key);
		if (o == null) {
			throw new JSONException("JSONObject[" + quote(key) + "] not found.");
		}
		return o;
	}

	/**
	 * Metodo que obtieneel estado
	 * 
	 * @param String key llave
	 * @return un boolean con el estado
	 */
	public boolean getBoolean(String key) throws JSONException {
		Object o = get(key);
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
		throw new JSONException("JSONObject[" + quote(key) + "] is not a Boolean.");
	}

	/**
	 * Metodo que obtiene un valor
	 * 
	 * @param String key llave
	 * @return un double con el valor
	 */
	public double getDouble(String key) throws JSONException {
		Object o = get(key);
		try {
			return (o instanceof Number) ? ((Number) o).doubleValue() : Double.valueOf((String) o).doubleValue();
		} catch (Exception e) {
			throw new JSONException("JSONObject[" + quote(key) + "] is not a number.");
		}
	}

	/**
	 * Metodo que obtiene un valor
	 * 
	 * @param String key llave
	 * @return un int con el valor
	 */
	public int getInt(String key) throws JSONException {
		Object o = get(key);
		return (o instanceof Number) ? ((Number) o).intValue() : (int) getDouble(key);
	}

	/**
	 * Metodo que obtiene un arreglo json
	 * 
	 * @param String key llave
	 * @return un JSONArray arreglo json
	 */
	public JSONArray getJSONArray(String key) throws JSONException {
		Object o = get(key);
		if ((o instanceof JSONArray)) {
			return (JSONArray) o;
		}
		throw new JSONException("JSONObject[" + quote(key) + "] is not a JSONArray.");
	}

	/**
	 * Metodo que obtiene un objeto json
	 * 
	 * @param String key llave
	 * @return un JSONObject objeto json
	 */
	public JSONObject getJSONObject(String key) throws JSONException {
		Object o = get(key);
		if ((o instanceof JSONObject)) {
			return (JSONObject) o;
		}
		throw new JSONException("JSONObject[" + quote(key) + "] is not a JSONObject.");
	}

	/**
	 * Metodo que obtiene un valor
	 * 
	 * @param String key llave
	 * @return un long con el valor
	 */
	public long getLong(String key) throws JSONException {
		Object o = get(key);
		return (long) ((o instanceof Number) ? ((Number) o).longValue() : getDouble(key));
	}

	/**
	 * Metodo que obtiene los nombres
	 * 
	 * @param JSONObject jo objeto json
	 * @return un arreglo de String con los nombres
	 */
	public static String[] getNames(JSONObject jo) {
		int length = jo.length();
		if (length == 0) {
			return null;
		}
		Iterator<Object> i = jo.keys();
		String[] names = new String[length];
		int j = 0;
		while (i.hasNext()) {
			names[j] = ((String) i.next());
			j++;
		}
		return names;
	}

	/**
	 * Metodo que obtiene los nombres
	 * 
	 * @param Object object objeto
	 * @return un arreglo de String con los nombres
	 */
	public static String[] getNames(Object object) {
		if (object == null) {
			return null;
		}
		Class<? extends Object> klass = object.getClass();
		Field[] fields = klass.getFields();
		int length = fields.length;
		if (length == 0) {
			return null;
		}
		String[] names = new String[length];
		for (int i = 0; i < length; i++) {
			names[i] = fields[i].getName();
		}
		return names;
	}

	/**
	 * Metodo que obtiene String
	 * 
	 * @param String key llave
	 * @return un String
	 */
	public String getString(String key) throws JSONException {
		return get(key).toString();
	}

	/**
	 * Metodo que obtiene un boolean
	 * 
	 * @param String key llave
	 * @return un boolean
	 */
	public boolean has(String key) {
		return this.map.containsKey(key);
	}

	/**
	 * Metodo que obtiene si es nulo
	 * 
	 * @param String key llave
	 * @return un boolean con el estado
	 */
	public boolean isNull(String key) {
		return NULL.equals(opt(key));
	}

	/**
	 * Metodo que gestiona las llaves
	 * 
	 * @return un Iterator iterancia
	 */
	public Iterator<Object> keys() {
		return this.map.keySet().iterator();
	}

	/**
	 * Metodo que devuelve la longitud
	 * 
	 * @return un int con la longitud
	 */
	public int length() {
		return this.map.size();
	}

	/**
	 * Metodo que devuelve los nombres
	 * 
	 * @return un JSONArray arreglo de json con los nombres
	 */
	public JSONArray names() {
		JSONArray ja = new JSONArray();
		Iterator<?> keys = keys();
		while (keys.hasNext()) {
			ja.put(keys.next());
		}
		return ja.length() == 0 ? null : ja;
	}

	/**
	 * Metodo que convierte un numero a String
	 * 
	 * @param Number n valor
	 * @return un String con el valor
	 */
	public static String numberToString(Number n) throws JSONException {
		if (n == null) {
			throw new JSONException("Null pointer");
		}
		testValidity(n);

		String s = n.toString();
		if ((s.indexOf('.') > 0) && (s.indexOf('e') < 0) && (s.indexOf('E') < 0)) {
			while (s.endsWith("0")) {
				s = s.substring(0, s.length() - 1);
			}
			if (s.endsWith(".")) {
				s = s.substring(0, s.length() - 1);
			}
		}
		return s;
	}

	/**
	 * Metodo opt objeto
	 * 
	 * @param String key llave
	 * @return un Object objeto
	 */
	public Object opt(String key) {
		return key == null ? null : this.map.get(key);
	}

	/**
	 * Metodo opt estado
	 * 
	 * @param String key llave
	 * @return un boolean con el estado
	 */
	public boolean optBoolean(String key) {
		return optBoolean(key, false);
	}

	/**
	 * Metodo opt estado
	 * 
	 * @param String key llave, boolean defaultValue estado del valor
	 * @return un boolean con el estado
	 */
	public boolean optBoolean(String key, boolean defaultValue) {
		try {
			return getBoolean(key);
		} catch (Exception e) {
		}
		return defaultValue;
	}

	/**
	 * Metodo colocar un objeto en formato json
	 * 
	 * @param String key llave, Collection value valor de la coleccion
	 * @return un JSONObject objeto en formato json
	 */
	public JSONObject put(String key, Collection<Object> value) throws JSONException {
		put(key, new JSONArray(value));
		return this;
	}

	/**
	 * Metodo opt valor
	 * 
	 * @param String key llave
	 * @return un double con el valor
	 */
	public double optDouble(String key) {
		return optDouble(key, Double.NaN);
	}

	/**
	 * Metodo opt valor
	 * 
	 * @param String key llave, double defaultValue valor por defecto
	 * @return un double con el valor
	 */
	public double optDouble(String key, double defaultValue) {
		try {
			Object o = opt(key);
			return (o instanceof Number) ? ((Number) o).doubleValue() : new Double((String) o).doubleValue();
		} catch (Exception e) {
		}
		return defaultValue;
	}

	/**
	 * Metodo opt valor
	 * 
	 * @param String key llave
	 * @return un int con el valor
	 */
	public int optInt(String key) {
		return optInt(key, 0);
	}

	/**
	 * Metodo opt valor
	 * 
	 * @param String key llave, int defaultValue valor por defecto
	 * @return un int con el valor
	 */
	public int optInt(String key, int defaultValue) {
		try {
			return getInt(key);
		} catch (Exception e) {
		}
		return defaultValue;
	}

	/**
	 * Metodo opt arreglo de json
	 * 
	 * @param String key llave
	 * @return un JSONArray arreglo de json
	 */
	public JSONArray optJSONArray(String key) {
		Object o = opt(key);
		return (o instanceof JSONArray) ? (JSONArray) o : null;
	}

	/**
	 * Metodo opt objeto de json
	 * 
	 * @param String key llave
	 * @return un JSONObject objeto de json
	 */
	public JSONObject optJSONObject(String key) {
		Object o = opt(key);
		return (o instanceof JSONObject) ? (JSONObject) o : null;
	}

	/**
	 * Metodo opt valor
	 * 
	 * @param String key llave
	 * @return un long con el valor
	 */
	public long optLong(String key) {
		return optLong(key, 0L);
	}

	/**
	 * Metodo opt valor
	 * 
	 * @param String key llave, long defaultValue valor por defecto
	 * @return un long con el valor
	 */
	public long optLong(String key, long defaultValue) {
		try {
			return getLong(key);
		} catch (Exception e) {
		}
		return defaultValue;
	}

	/**
	 * Metodo opt String
	 * 
	 * @param String key llave
	 * @return un String
	 */
	public String optString(String key) {
		return optString(key, "");
	}

	/**
	 * Metodo opt String
	 * 
	 * @param String key llave, String defaultValue valor
	 * @return un String
	 */
	public String optString(String key, String defaultValue) {
		Object o = opt(key);
		return o != null ? o.toString() : defaultValue;
	}

	/**
	 * Metodo colocar un objeto en formato json
	 * 
	 * @param String key llave, boolean value estado del valor
	 * @return un JSONObject objeto en formato json
	 */
	public JSONObject put(String key, boolean value) throws JSONException {
		put(key, value ? Boolean.TRUE : Boolean.FALSE);
		return this;
	}

	/**
	 * Metodo colocar un objeto en formato json
	 * 
	 * @param String key llave, double value valor
	 * @return un JSONObject objeto en formato json
	 */
	public JSONObject put(String key, double value) throws JSONException {
		put(key, new Double(value));
		return this;
	}

	/**
	 * Metodo colocar un objeto en formato json
	 * 
	 * @param String key llave, int value valor
	 * @return un JSONObject objeto en formato json
	 */
	public JSONObject put(String key, int value) throws JSONException {
		put(key, new Integer(value));
		return this;
	}

	/**
	 * Metodo colocar un objeto en formato json
	 * 
	 * @param String key llave, long value valor
	 * @return un JSONObject objeto en formato json
	 */
	public JSONObject put(String key, long value) throws JSONException {
		put(key, new Long(value));
		return this;
	}

	/**
	 * Metodo colocar un objeto en formato json
	 * 
	 * @param String key llave, Map value valor
	 * @return un JSONObject objeto en formato json
	 */
	public JSONObject put(String key, Map<Object, Object> value) throws JSONException {
		put(key, new JSONObject(value));
		return this;
	}

	/**
	 * Metodo colocar un objeto en formato json
	 * 
	 * @param String key llave, Object value valor
	 * @return un JSONObject objeto en formato json
	 */
	public JSONObject put(String key, Object value) throws JSONException {
		if (key == null) {
			throw new JSONException("Null key.");
		}
		if (value != null) {
			testValidity(value);
			this.map.put(key, value);
		} else {
			remove(key);
		}
		return this;
	}

	/**
	 * Metodo colocar una vez un objeto en formato json
	 * 
	 * @param String key llave, Object value valor
	 * @return un JSONObject objeto en formato json
	 */
	public JSONObject putOnce(String key, Object value) throws JSONException {
		if ((key != null) && (value != null)) {
			if (opt(key) != null) {
				throw new JSONException("Duplicate key \"" + key + "\"");
			}
			put(key, value);
		}
		return this;
	}

	/**
	 * Metodo colocar opt un objeto en formato json
	 * 
	 * @param String key llave, Object value valor
	 * @return un JSONObject objeto en formato json
	 */
	public JSONObject putOpt(String key, Object value) throws JSONException {
		if ((key != null) && (value != null)) {
			put(key, value);
		}
		return this;
	}

	/**
	 * Metodo para citar
	 * 
	 * @param String string valor
	 * @return un String valor
	 */
	public static String quote(String string) {
		if ((string == null) || (string.length() == 0)) {
			return "\"\"";
		}
		char c = '\000';

		int len = string.length();
		StringBuffer sb = new StringBuffer(len + 4);

		sb.append('"');
		for (int i = 0; i < len; i++) {
			char b = c;
			c = string.charAt(i);
			switch (c) {
			case '"':
			case '\\':
				sb.append('\\');
				sb.append(c);
				break;
			case '/':
				if (b == '<') {
					sb.append('\\');
				}
				sb.append(c);
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\r':
				sb.append("\\r");
				break;
			default:
				if ((c < ' ') || ((c >= '?') && (c < ' ')) || ((c >= '?') && (c < '?'))) {
					String t = "000" + Integer.toHexString(c);
					sb.append("\\u" + t.substring(t.length() - 4));
				} else {
					sb.append(c);
				}
				break;
			}
		}
		sb.append('"');
		return sb.toString();
	}

	/**
	 * Metodo remover
	 * 
	 * @param String key llave
	 * @return un Object objeto
	 */
	public Object remove(String key) {
		return this.map.remove(key);
	}

	/**
	 * Metodo que ordena las llaves
	 * 
	 * @return un Iterator iterador
	 */
	public Iterator<Object> sortedKeys() {
		return new TreeSet<Object>(this.map.keySet()).iterator();
	}

	/**
	 * Metodo que convierte un String en objeto
	 * 
	 * @param String s valor
	 * @return un Object objeto
	 */
	public static Object stringToValue(String s) {
		if (s.equals("")) {
			return s;
		}
		if (s.equalsIgnoreCase("true")) {
			return Boolean.TRUE;
		}
		if (s.equalsIgnoreCase("false")) {
			return Boolean.FALSE;
		}
		if (s.equalsIgnoreCase("null")) {
			return NULL;
		}
		char b = s.charAt(0);
		if (((b >= '0') && (b <= '9')) || (b == '.') || (b == '-') || (b == '+')) {
			if (b == '0') {
				if ((s.length() > 2) && ((s.charAt(1) == 'x') || (s.charAt(1) == 'X'))) {
					try {
						return new Integer(Integer.parseInt(s.substring(2), 16));
					} catch (Exception localException) {
					}
				} else {
					try {
						return new Integer(Integer.parseInt(s, 8));
					} catch (Exception localException1) {
					}
				}
			}
			try {
				if ((s.indexOf('.') > -1) || (s.indexOf('e') > -1) || (s.indexOf('E') > -1)) {
					return Double.valueOf(s);
				}
				Long myLong = new Long(s);
				if (myLong.longValue() == myLong.intValue()) {
					return new Integer(myLong.intValue());
				}
				return myLong;
			} catch (Exception localException2) {
			}
		}
		return s;
	}

	/**
	 * Metodo validador
	 * 
	 * @param Object o objeto
	 */
	static void testValidity(Object o) throws JSONException {
		if (o != null) {
			if ((o instanceof Double)) {
				if ((((Double) o).isInfinite()) || (((Double) o).isNaN())) {
					throw new JSONException("JSON does not allow non-finite numbers.");
				}
			} else if (((o instanceof Float)) && ((((Float) o).isInfinite()) || (((Float) o).isNaN()))) {
				throw new JSONException("JSON does not allow non-finite numbers.");
			}
		}
	}

	/**
	 * Metodo que devuelve un arreglo en formato json
	 * 
	 * @param JSONArray names arreglo json de nombres
	 * @return un JSONArray arreglo en formato json
	 */
	public JSONArray toJSONArray(JSONArray names) throws JSONException {
		if ((names == null) || (names.length() == 0)) {
			return null;
		}
		JSONArray ja = new JSONArray();
		for (int i = 0; i < names.length(); i++) {
			ja.put(opt(names.getString(i)));
		}
		return ja;
	}

	/**
	 * Metodo que devuelve un String
	 * 
	 * @return un String
	 */
	public String toString() {
		try {
			Iterator<?> keys = keys();
			StringBuffer sb = new StringBuffer("{");
			while (keys.hasNext()) {
				if (sb.length() > 1) {
					sb.append(',');
				}
				Object o = keys.next();
				sb.append(quote(o.toString()));
				sb.append(':');
				sb.append(valueToString(this.map.get(o)));
			}
			sb.append('}');
			return sb.toString();
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * Metodo que devuelve un String
	 * 
	 * @param int indentFactor factor de identificacion
	 * @return un String
	 */
	public String toString(int indentFactor) throws JSONException {
		return toString(indentFactor, 0);
	}

	/**
	 * Metodo que devuelve un String
	 * 
	 * @param int indentFactor factor de identificacion, int indent identificador
	 * @return un String
	 */
	String toString(int indentFactor, int indent) throws JSONException {
		int n = length();
		if (n == 0) {
			return "{}";
		}
		Iterator<?> keys = sortedKeys();
		StringBuffer sb = new StringBuffer("{");
		int newindent = indent + indentFactor;
		if (n == 1) {
			Object o = keys.next();
			sb.append(quote(o.toString()));
			sb.append(": ");
			sb.append(valueToString(this.map.get(o), indentFactor, indent));
		} else {
			while (keys.hasNext()) {
				Object o = keys.next();
				if (sb.length() > 1) {
					sb.append(",\n");
				} else {
					sb.append('\n');
				}
				for (int j = 0; j < newindent; j++) {
					sb.append(' ');
				}
				sb.append(quote(o.toString()));
				sb.append(": ");
				sb.append(valueToString(this.map.get(o), indentFactor, newindent));
			}
			if (sb.length() > 1) {
				sb.append('\n');
				for (int j = 0; j < indent; j++) {
					sb.append(' ');
				}
			}
		}
		sb.append('}');
		return sb.toString();
	}

	/**
	 * Metodo que convierte un objeto en String
	 * 
	 * @param Object value valor
	 * @return un String con el valor
	 */
	@SuppressWarnings("unchecked")
	static String valueToString(Object value) throws JSONException {
		if ((value == null) || (value.equals(null))) {
			return "null";
		}
		if ((value instanceof JSONString)) {
			String o = "";
			try {
				o = ((JSONString) value).toJSONString();
			} catch (Exception e) {
				throw new JSONException(e);
			}
			if ((o instanceof String)) {
				return (String) o;
			}
			throw new JSONException("Bad value from toJSONString: " + o);
		}
		if ((value instanceof Number)) {
			return numberToString((Number) value);
		}
		if (((value instanceof Boolean)) || ((value instanceof JSONObject)) || ((value instanceof JSONArray))) {
			return value.toString();
		}
		if ((value instanceof Map)) {
			return new JSONObject((Map<Object, Object>) value).toString();
		}
		if ((value instanceof Collection)) {
			return new JSONArray((Collection<Object>) value).toString();
		}
		if (value.getClass().isArray()) {
			return new JSONArray(value).toString();
		}
		return quote(value.toString());
	}

	/**
	 * Metodo que convierte un objeto en String
	 * 
	 * @param Object value valor, int indentFactor factor de identificacion, int
	 *               indent identificador
	 * @return un String con el valor
	 */
	@SuppressWarnings("unchecked")
	static String valueToString(Object value, int indentFactor, int indent) throws JSONException {
		if ((value == null) || (value.equals(null))) {
			return "null";
		}
		try {
			if ((value instanceof JSONString)) {
				Object o = ((JSONString) value).toJSONString();
				if ((o instanceof String)) {
					return (String) o;
				}
			}
		} catch (Exception localException) {
		}
		if ((value instanceof Number)) {
			return numberToString((Number) value);
		}
		if ((value instanceof Boolean)) {
			return value.toString();
		}
		if ((value instanceof JSONObject)) {
			return ((JSONObject) value).toString(indentFactor, indent);
		}
		if ((value instanceof JSONArray)) {
			return ((JSONArray) value).toString(indentFactor, indent);
		}
		if ((value instanceof Map)) {
			return new JSONObject((Map<Object, Object>) value).toString(indentFactor, indent);
		}
		if ((value instanceof Collection)) {
			return new JSONArray((Collection<Object>) value).toString(indentFactor, indent);
		}
		if (value.getClass().isArray()) {
			return new JSONArray(value).toString(indentFactor, indent);
		}
		return quote(value.toString());
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
			Iterator<?> keys = keys();
			writer.write(123);
			while (keys.hasNext()) {
				if (b) {
					writer.write(44);
				}
				Object k = keys.next();
				writer.write(quote(k.toString()));
				writer.write(58);
				Object v = this.map.get(k);
				if ((v instanceof JSONObject)) {
					((JSONObject) v).write(writer);
				} else if ((v instanceof JSONArray)) {
					((JSONArray) v).write(writer);
				} else {
					writer.write(valueToString(v));
				}
				b = true;
			}
			writer.write(125);
			return writer;
		} catch (IOException e) {
			throw new JSONException(e);
		}
	}
}
