package co.gov.supernotariado.bachue.portalgeografico.ws.utils;

import javax.sql.DataSource;


/**
 * Clase que contiene todos las propiedades de ConverterUtils.
 *
 * @author Julian Vaca
 */
public class ConverterUtils
{
	/**
	 * Retorna el valor de data source.
	 *
	 * @param ao_o correspondiente al valor del tipo de objeto Object
	 * @return el valor de data source
	 */
	public static DataSource getDataSource(Object ao_o)
	{
		return ((ao_o != null) && ao_o instanceof DataSource) ? (DataSource)ao_o : null;
	}

	/**
	 * Retorna Objeto o variable de valor mail session.
	 *
	 * @param ao_o de ao o
	 * @return el valor de mail session
	 */
	public static javax.mail.Session getMailSession(Object ao_o)
	{
		return ((ao_o != null) && ao_o instanceof javax.mail.Session) ? (javax.mail.Session)ao_o : null;
	}

	/**
	 * Retorna Objeto o variable de valor string.
	 *
	 * @param ao_o de ao o
	 * @return el valor de string
	 */
	public static String getString(Object ao_o)
	{
		return ((ao_o != null) && ao_o instanceof String) ? (String)ao_o : null;
	}
}
