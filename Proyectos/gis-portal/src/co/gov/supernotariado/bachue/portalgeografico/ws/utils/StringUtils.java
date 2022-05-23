package co.gov.supernotariado.bachue.portalgeografico.ws.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;


/**
 * Clase de utilidades para el manejo de cadenas.
 *
 * @author Edgar Prieto
 */
public class StringUtils
{
	/**
	 * Obtener un objeto <code>java.lang.String</code> a partir de un objeto
	 * <code>java.util.Date</code> dado un formato de representaci�n.
	 *
	 * @author Edgar Prieto
	 * @param ad_d <code>java.util.Date</code> a convertir.
	 * @param as_format        Formato de representaci�n de fecha.
	 * @return Un objeto <code>java.lang.String</code> representando a <i>ad_d </i> en el formato
	 *         <i>as_format </i>. <code>null</code> en caso de que la conversi�n no se pueda
	 *         realizar.
	 */
	public static String getString(Date ad_d, String as_format)
	{
		return getString(ad_d, as_format, Locale.getDefault());
	}

	/**
	 * Obtener el valor la representaci�n <code>String</code> de un <code>boolean</code>.
	 *
	 * @author Jorge Pati�o
	 * @param ab_b    <code>boolean</code> el cual ser� interpretado como un <code>String</code>.
	 * @return        <b>S</b> si ab_b es <code>true</code> y <b>N</b> si ab_b es <code>false</code>
	 */
	public static String getString(boolean ab_b)
	{
		return ab_b ? EstadoCommon.S : EstadoCommon.N;
	}

	/**
	 * Retorna el valor de string.
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto Date
	 * @param as_format correspondiente al valor del tipo de objeto String
	 * @param al_locale correspondiente al valor del tipo de objeto Locale
	 * @return el valor de string
	 */
	public static String getString(Date ad_d, String as_format, Locale al_locale)
	{
		String ls_date;

		if((ad_d != null) && isValidString(as_format))
		{
			SimpleDateFormat lsdf_sdf;

			/*
			 * Obtener un objeto para el formato de fachas. La operaci�n de formato debe ser
			 * estricta
			 */
			lsdf_sdf = new SimpleDateFormat(as_format, (al_locale != null) ? al_locale : Locale.getDefault());

			lsdf_sdf.setLenient(false);

			try
			{
				StringBuilder lsb_date;

				/* Obtener la cadena que representa a la fecha dada */
				lsb_date     = new StringBuilder(
					    lsdf_sdf.format(ad_d, new StringBuffer(), new FieldPosition(DateFormat.YEAR_FIELD))
					);

				ls_date = lsb_date.toString();
			}
			catch(Exception le_e)
			{
				ls_date = null;
			}
		}
		else
			ls_date = null;

		return ls_date;
	}

	/**
	 * Convertir un <code>double</code> en un objeto <code>java.lang.String</code>.
	 *
	 * @author Edgar Prieto
	 * @param ad_d <code>double</double> a convertir.
	 * @return    <code>java.lang.String</code> con formato xxxxxxxxx.yy representando a <i>ad_d</i>.
	 */
	public static String getString(double ad_d)
	{
		DecimalFormat ldf_f;

		ldf_f = new DecimalFormat();

		/* El indicador de separadores siempre debe estar presente */
		ldf_f.setDecimalSeparatorAlwaysShown(false);

		/* No utilizar agrupamiento */
		ldf_f.setGroupingUsed(false);

		return ldf_f.format(ad_d);
	}

	/**
	 * Eliminar los caracteres en blanco iniciales y finales de un objeto
	 * <code>java.lang.String</code>.
	 *
	 * @author Edgar Prieto
	 * @param as_s <code>java.lang.String</code> a eliminar los caracteres en blanco.
	 * @return <code>java.lang.String</code> sin caracteres en blanco antes y despu�s del
	 *         contenido de la cadena. <code>null</code> si <i>as_s </i> es <code>null</code>.
	 */
	public static String getString(String as_s)
	{
		return (as_s == null) ? null : as_s.trim();
	}

	/**
	 * Retorna el valor de string.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto Object
	 * @return el valor de string
	 */
	public static String getString(Object as_s)
	{
		return (as_s == null) ? null : as_s.toString();
	}

	/**
	 * Obtiene una cadena de caracteres y la almacena en un arreglo.
	 *
	 * @param as_s Cadena de caracteres a almacenar en un arreglo
	 * @return arreglo de salida contenedor de la cadena de caracteres
	 */
	public static String[] getStringArray(String as_s)
	{
		String[] lsa_sa;

		if(StringUtils.isValidString(as_s))
		{
			lsa_sa        = new String[1];
			lsa_sa[0]     = as_s;
		}
		else
			lsa_sa = null;

		return lsa_sa;
	}

	/**
	 * Retorna el valor de string array.
	 *
	 * @param as_string correspondiente al valor del tipo de objeto String
	 * @param as_separador correspondiente al valor del tipo de objeto String
	 * @return el valor de string array
	 */
	public static String[] getStringArray(String as_string, String as_separador)
	{
		String[] lsa_return;

		lsa_return = null;

		if(
		    StringUtils.isValidString(as_string) && StringUtils.isValidString(as_separador)
			    && as_string.contains(as_separador)
		)
			lsa_return = as_string.split(as_separador);

		return lsa_return;
	}

	/**
	 * Eliminar los caracteres en blanco iniciales y finales de un objeto
	 * <code>java.lang.String</code> y convertir los caracteres restantes a su representaci�n en
	 * min�sculas.
	 *
	 * @author Edgar Prieto
	 * @param as_s <code>java.lang.String</code> a eliminar los caracteres en blanco.
	 * @return <code>java.lang.String</code> sin caracteres en blanco antes y despu�s del
	 *         contenido de la cadena y con los caracteres restantes convertidos a su representaci�n
	 *         en min�sculas. <code>null</code> si <i>as_s </i> es <code>null</code>.
	 */
	public static String getStringLowerCase(String as_s)
	{
		return (as_s == null) ? null : as_s.trim().toLowerCase();
	}

	/**
	 * Eliminar los caracteres en blanco iniciales y finales de un objeto
	 * <code>java.lang.String</code>. Reemplazar todas las secuencias de caracteres en blanco
	 * intermedias y reeplazarlas por un caracter espacio. Convertir todos los caracteres a su
	 * representaci�n en may�sculas. Reemplazar las ocurrencias de vocales acentuadas por su
	 * respectiva vocal sin acento.
	 *
	 * @author Edgar Prieto
	 * @param as_s        <code>java.lang.String</code> a normalizar.
	 * @return <code>java.lang.String</code> normalizada. <code>null</code> si <i>as_s </i> es
	 *         <code>null</code>.
	 */
	public static String getStringNormalized(String as_s)
	{
		String ls_s;

		if((ls_s = getStringTrim(as_s)) != null)
		{
			ls_s     = ls_s.toUpperCase();
			ls_s     = ls_s.replace('�', 'A');
			ls_s     = ls_s.replace('�', 'A');
			ls_s     = ls_s.replace('�', 'A');
			ls_s     = ls_s.replace('�', 'A');
			ls_s     = ls_s.replace('�', 'A');
			ls_s     = ls_s.replace('�', 'A');
			ls_s     = ls_s.replace('�', 'E');
			ls_s     = ls_s.replace('�', 'E');
			ls_s     = ls_s.replace('�', 'E');
			ls_s     = ls_s.replace('�', 'E');
			ls_s     = ls_s.replace('�', 'I');
			ls_s     = ls_s.replace('�', 'I');
			ls_s     = ls_s.replace('�', 'I');
			ls_s     = ls_s.replace('�', 'I');
			ls_s     = ls_s.replace('�', 'O');
			ls_s     = ls_s.replace('�', 'O');
			ls_s     = ls_s.replace('�', 'O');
			ls_s     = ls_s.replace('�', 'O');
			ls_s     = ls_s.replace('�', 'O');
			ls_s     = ls_s.replace('�', 'U');
			ls_s     = ls_s.replace('�', 'U');
			ls_s     = ls_s.replace('�', 'U');
			ls_s     = ls_s.replace('�', 'U');
		}

		return ls_s;
	}

	/**
	 * Retorna el valor de string not empty.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto String
	 * @return el valor de string not empty
	 */
	public static String getStringNotEmpty(String as_s)
	{
		String ls_s;

		ls_s = getStringNotNull(as_s);

		if(ls_s.length() < 1)
			ls_s = " ";

		return ls_s;
	}

	/**
	 * Eliminar los caracteres en blanco iniciales y finales de un objeto
	 * <code>java.lang.String</code>.
	 *
	 * @author Edgar Prieto
	 * @param as_s <code>java.lang.String</code> a eliminar los caracteres en blanco.
	 * @return <code>java.lang.String</code> sin caracteres en blanco antes y despu�s del
	 *         contenido de la cadena. Objeto <code>java.lang.String</code> sin contenido si
	 *         <i>as_s </i> es <code>null</code>.
	 */
	public static String getStringNotNull(String as_s)
	{
		return (as_s == null) ? new String() : as_s.trim();
	}

	/**
	 * Eliminar los caracteres en blanco iniciales y finales de un objeto
	 * <code>java.lang.String</code> y convertir los caracteres restantes a su representaci�n en
	 * may�sculas.
	 *
	 * @author Edgar Prieto
	 * @param as_s <code>java.lang.String</code> a eliminar los caracteres en blanco.
	 * @return <code>java.lang.String</code> sin caracteres en blanco antes y despu�s del
	 *         contenido de la cadena y con los caracteres restantes convertidos a su representaci�n
	 *         en may�sculas. <code>null</code> si <i>as_s </i> es <code>null</code>
	 */
	public static String getStringUpperCase(String as_s)
	{
		return (as_s == null) ? null : as_s.trim().toUpperCase();
	}

	/**
	 * Retorna el valor de true string.
	 *
	 * @return el valor de true string
	 */
	public static String getTrueString()
	{
		return Boolean.TRUE.toString();
	}

	/**
	 * Valida la propiedad valid characters.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto String
	 * @param as_caracters correspondiente al valor del tipo de objeto String
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en valid characters
	 */
	public static boolean isValidCharacters(String as_s, String as_caracters)
	{
		boolean lb_b;
		lb_b = true;

		if(isValidString(as_s) && isValidString(as_caracters))
		{
			for(int li_i = 0, li_lenght = as_caracters.length(); li_i < li_lenght; li_i++)
			{
				if(as_s.contains(String.valueOf(as_caracters.charAt(li_i))))
				{
					lb_b = false;

					break;
				}
			}
		}

		return lb_b;
	}

	/**
	 * Determina si un objeto <code>java.lang.String</code> es <code>null</code> o si no
	 * contiene caracteres diferentes a caracteres en blanco.
	 *
	 * @author Edgar Prieto
	 * @param as_s <code>java.lang.String</code> a validar.
	 * @return <code>true</code> si <i>as_s </i> tiene caracteres diferentes a caracteres en
	 *         blanco o <code>false</code> si <i>as_s </i> es <code>null</code> o no tiene
	 *         caracteres diferentes a caracteres en blanco.
	 */
	public static boolean isValidString(String as_s)
	{
		return (as_s != null) && (as_s.trim().length() > 0);
	}

	/**
	 * Retorna el valor del objeto de String.
	 *
	 * @param as_cadena correspondiente al valor del tipo de objeto String
	 * @return devuelve el valor de String
	 */
	public static String reemplazarCaracteresUTF8(String as_cadena)
	{
		if(StringUtils.isValidString(as_cadena))
		{
			as_cadena     = as_cadena.replace("�", "\\u193\\'3f");
			as_cadena     = as_cadena.replace("�", "\\u201\\'3f");
			as_cadena     = as_cadena.replace("�", "\\u205\\'3f");
			as_cadena     = as_cadena.replace("�", "\\u211\\'3f");
			as_cadena     = as_cadena.replace("�", "\\u218\\'3f");
			as_cadena     = as_cadena.replace("�", "\\u209\\'3f");
			as_cadena     = as_cadena.replace("�", "\\u220\\'3f");
			as_cadena     = as_cadena.replace("�", "\\u225\\'3f");
			as_cadena     = as_cadena.replace("�", "\\u233\\'3f");
			as_cadena     = as_cadena.replace("�", "\\u237\\'3f");
			as_cadena     = as_cadena.replace("�", "\\u243\\'3f");
			as_cadena     = as_cadena.replace("�", "\\u250\\'3f");
			as_cadena     = as_cadena.replace("�", "\\u241\\'3f");
			as_cadena     = as_cadena.replace("�", "\\u252\\'3f");
		}

		return as_cadena;
	}

	/**
	 * Eliminar los caracteres en blanco iniciales y finales de un objeto
	 * <code>java.lang.String</code>. Reemplazar todas las secuencias de caracteres en blanco
	 * intermedias y reeplazarlas por un caracter espacio.
	 *
	 * @author Edgar Prieto
	 * @param as_s <code>java.lang.String</code> a eliminar los caracteres en blanco.
	 * @return <code>java.lang.String</code> sin caracteres en blanco antes y despu�s del
	 *         contenido de la cadena y las secuencias intermedias de caracteres en blanco
	 *         reemplazadas por el caracter espacio. <code>null</code> si <i>as_s </i> es
	 *         <code>null</code>.
	 */
	private static String getStringTrim(String as_s)
	{
		String ls_s;

		ls_s = null;

		if(as_s != null)
		{
			boolean       lb_hasSpace;
			char          lc_c;
			StringBuilder lsb_sb;

			lb_hasSpace     = false;
			lsb_sb          = new StringBuilder(getString(as_s));

			for(int li_i = 0; li_i < lsb_sb.length();)
			{
				lc_c = lsb_sb.charAt(li_i);

				if(Character.isWhitespace(lc_c) || Character.isSpaceChar(lc_c))
				{
					if(lb_hasSpace)
						lsb_sb.deleteCharAt(li_i);
					else
					{
						lsb_sb.replace(li_i, li_i + 1, " ");
						li_i++;
					}

					lb_hasSpace = true;
				}
				else
				{
					li_i++;
					lb_hasSpace = false;
				}
			}

			ls_s = lsb_sb.toString();
		}

		return ls_s;
	}

	/**
	  * M�todo para saber si una cadena de String est� �nicamente compuesta por n�meros
	  *
	  * @param as_s de as s
	  * @return retorna <code>true</code> si se cumple la condicion, de lo contario retorna <code>false</code> en numeric
	  * @author Carlos Calderon
	  */
	public static boolean isNumeric(String as_s)
	{
		boolean lb_return;

		try
		{
			Integer.parseInt(as_s);
			lb_return = true;
		}
		catch(NumberFormatException lnfe_e)
		{
			lb_return = false;
		}

		return lb_return;
	}
}
