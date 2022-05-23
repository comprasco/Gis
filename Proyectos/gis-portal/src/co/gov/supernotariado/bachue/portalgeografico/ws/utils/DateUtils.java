package co.gov.supernotariado.bachue.portalgeografico.ws.utils;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Clase de utilidades para el manejo de fechas.
 *
 * @author Edgar Prieto
 */
public class DateUtils
{
	/** Constante DEFAULT_LENIENT. */
	public static final boolean DEFAULT_LENIENT = false;

	/** Constante DEFAULT_CLEAN_FIELD. */
	public static final int DEFAULT_CLEAN_FIELD = Calendar.HOUR_OF_DAY;

	/** Constante DEFAULT_LOCALE. */
	public static final Locale DEFAULT_LOCALE = Locale.getDefault();

	/** Constante DEFAULT_TIME_ZONE. */
	public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();

	/**
	 * Retorna el valor de calendar.
	 *
	 * @return el valor de calendar
	 */
	public static Calendar getCalendar()
	{
		return getCalendar((Calendar)null);
	}

	/**
	 * Obtener una una instancia del calendario occidental inicializado en la fecha indicada por
	 * <i>ad_d</i>.
	 *
	 * @author Edgar Prieto
	 * @param ad_d Fecha de ajuste del calendario
	 * @return Instancia del calendario occidental inicializado en la fecha indicada por
	 * <i>ad_d</i>. Si es esta fecha es null el calendario queda inicializado en la fecha actual del
	 * sistema
	 */
	public static Calendar getCalendar(java.util.Date ad_d)
	{
		return getCalendar(ad_d, DEFAULT_LENIENT);
	}

	/**
	 * Retorna el valor de calendar.
	 *
	 * @param ac_c correspondiente al valor del tipo de objeto Calendar
	 * @return el valor de calendar
	 */
	public static Calendar getCalendar(Calendar ac_c)
	{
		return getCalendar(ac_c, DEFAULT_LENIENT);
	}

	/**
	 * Retorna el valor de clean date.
	 *
	 * @param ac_c correspondiente al valor del tipo de objeto Calendar
	 * @return el valor de clean date
	 */
	public static java.util.Date getCleanDate(Calendar ac_c)
	{
		return getCleanDate(ac_c, DEFAULT_CLEAN_FIELD);
	}

	/**
	 * Obtener una fecha sin informaci�n de horas, minutos, segundos y milisegundos.
	 *
	 * @author Edgar Prieto
	 * @param ad_d Fecha de la que se eliminar informaci�n de horas, minutos, segundos y
	 * milisegundos.
	 * @return <i>ad_d</i> sin informaci�n de horas, minutos, segundos y milisegundos.
	 */
	public static java.util.Date getCleanDate(java.util.Date ad_d)
	{
		return getCleanDate(getCalendar(ad_d));
	}

	/**
	 * Obtener una fecha sin informaci�n de horas, minutos, segundos y milisegundos.
	 *
	 * @author Edgar Prieto
	 * @param ad_d Fecha de la que se eliminar informaci�n de horas, minutos, segundos y
	 * milisegundos.
	 * @return <i>ad_d</i> sin informaci�n de horas, minutos, segundos y milisegundos.
	 */
	public static java.sql.Date getCleanSQLDate(java.util.Date ad_d)
	{
		return getSQLDate(getCleanDate(ad_d));
	}

	/**
	 * Obtener un objeto <code>java.util.Date</code> a partir de un objeto
	 * <code>java.lang.String</code> dado un formato de representaci�n.
	 *
	 * @author Edgar Prieto
	 * @param as_date    <code>java.lang.String</code> representado una fecha en un formato v�lido.
	 * @param as_format    Formato de representaci�n de fecha.
	 * @return    Un objeto <code>java.util.Date</code> si <i>as_date</i> representa una fecha en el
	 *            formato <i>as_format</i>. <code>null</code> de lo contrario.
	 */
	public static java.util.Date getDate(String as_date, String as_format)
	{
		return getDate(as_date, as_format, DEFAULT_LENIENT);
	}

	/**
	 * Retorna el valor de date.
	 *
	 * @param at_t correspondiente al valor del tipo de objeto Timestamp
	 * @return el valor de date
	 */
	public static java.util.Date getDate(Timestamp at_t)
	{
		return (at_t != null) ? new java.util.Date(at_t.getTime()) : null;
	}

	/**
	 * Obtener el d�a mes de la fecha de acuerdo a <code>java.util.GregorianCalendar</code>.
	 *
	 * @author Edgar Prieto
	 * @param ad_d Fecha de la que se quiere obtener el d�a del mes.
	 * @return D�a del mes de la fecha de acuerdo a <code>java.util.GregorianCalendar</code>.
	 */
	public static int getDayOfMonth(java.util.Date ad_d)
	{
		return getField(ad_d, Calendar.DAY_OF_MONTH);
	}

	/**
	 * Retorna el valor de mes.
	 *
	 * @param ai_mes correspondiente al valor del tipo de objeto int
	 * @return el valor de mes
	 */
	public static String getMes(int ai_mes)
	{
		String ls_mes;
		ls_mes = null;

		switch(ai_mes)
		{
			case 12:
				ls_mes = "DICIEMBRE";

				break;

			case 11:
				ls_mes = "NOVIEMBRE";

				break;

			case 10:
				ls_mes = "OCTUBRE";

				break;

			case 9:
				ls_mes = "SEPTIEMBRE";

				break;

			case 8:
				ls_mes = "AGOSTO";

				break;

			case 7:
				ls_mes = "JULIO";

				break;

			case 6:
				ls_mes = "JUNIO";

				break;

			case 5:
				ls_mes = "MAYO";

				break;

			case 4:
				ls_mes = "ABRIL";

				break;

			case 3:
				ls_mes = "MARZO";

				break;

			case 2:
				ls_mes = "FEBRERO";

				break;

			case 1:
				ls_mes = "ENERO";

				break;

			default:
				break;
		}

		return ls_mes;
	}

	/**
	 * Obtener el mes de la fecha de acuerdo a <code>java.util.GregorianCalendar</code>.
	 *
	 * @author Edgar Prieto
	 * @param ad_d Fecha de la que se quiere obtener el mes.
	 * @return Mes de la fecha de acuerdo a <code>java.util.GregorianCalendar</code>.
	 */
	public static int getMonth(java.util.Date ad_d)
	{
		return getField(ad_d, Calendar.MONTH) + 1;
	}

	/**
	 * Obtener un objeto <code>java.sql.Date</code> a partir de un objeto
	 * <code>java.util.Date</code>.
	 *
	 * @author Edgar Prieto
	 * @param ad_d    <code>java.util.Date</code> representado una fecha.
	 * @return            Un objeto <code>java.sql.Date</code> representando la fecha dada por
	 *                    <i>ad_d</i>.
	 */
	public static java.sql.Date getSQLDate(java.util.Date ad_d)
	{
		return (ad_d != null) ? new java.sql.Date(ad_d.getTime()) : null;
	}

	/**
	 * Obtener un objeto <code>java.sql.Timestamp</code> representando la fecha del sistema.
	 *
	 * @author Edgar Prieto
	 * @return    Un objeto <code>java.sql.Timestamp</code> representando la fecha del sistema.
	 */
	public static Timestamp getTimestamp()
	{
		return getTimestampNotNull((Calendar)null);
	}

	/**
	 * Obtener un objeto <code>java.sql.Timestamp</code> a partir de un objeto
	 * <code>java.util.Date</code>.
	 *
	 * @author Edgar Prieto
	 * @param ad_d    <code>java.util.Date</code> representado una fecha.
	 * @return            Un objeto <code>java.sql.Timestamp</code> representando la fecha dada por
	 *                    <i>ad_d</i>.
	 */
	public static Timestamp getTimestamp(java.util.Date ad_d)
	{
		return (ad_d != null) ? getTimestamp(ad_d.getTime()) : null;
	}

	/**
	 * Obtener el a�o de la fecha de acuerdo a <code>java.util.GregorianCalendar</code>.
	 *
	 * @author Edgar Prieto
	 * @param ad_d Fecha de la que se quiere obtener el a�o.
	 * @return A�o de la fecha de acuerdo a <code>java.util.GregorianCalendar</code>
	 */
	public static int getYear(java.util.Date ad_d)
	{
		return getField(ad_d, Calendar.YEAR);
	}

	/**
	 * Retorna el valor del objeto de java.util.Date
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto Date
	 * @param ai_field correspondiente al valor del tipo de objeto int
	 * @return devuelve el valor de java.util.Date
	 */
	public static java.util.Date clearDateFrom(java.util.Date ad_d, int ai_field)
	{
		return clearDateFrom(getCalendar(ad_d), ai_field);
	}

	/**
	 * Retorna el valor del objeto de String.
	 *
	 * @param dtc correspondiente al valor del tipo de objeto Date
	 * @return devuelve el valor de String
	 */
	public static String convertirLetrasLarga(java.util.Date dtc)
	{
		String ls_return;

		ls_return = "";

		if(dtc != null)
		{
			@SuppressWarnings("deprecation")
			String sTemp = dtc.getDate() + " DE " + getMes(dtc.getMonth() + 1) + " DE " + (dtc.getYear() + 1900);

			ls_return = sTemp.toLowerCase();
		}

		return ls_return;
	}

	/**
	 * Obtener la diferencia en d�as entre dos fechas
	 *
	 * @author Edgar Prieto
	 * @param ad_fechaInicial Fecha inicial del rango
	 * @param ad_fechaFinal Fecha final del rango
	 * @return diferencia en d�as entre dos fechas
	 */
	public static long obtenerDiferenciaEnDias(java.util.Date ad_fechaInicial, java.util.Date ad_fechaFinal)
	{
		long ll_diferencia;

		ll_diferencia = 0;

		if((ad_fechaInicial != null) && (ad_fechaFinal != null) && !ad_fechaInicial.after(ad_fechaFinal))
		{
			Calendar lc_fecha;
			Calendar lc_fechaFinal;

			lc_fecha          = clearCalendarFrom(getCalendar(ad_fechaInicial), Calendar.HOUR);
			lc_fechaFinal     = clearCalendarFrom(getCalendar(ad_fechaFinal), Calendar.HOUR);

			while(lc_fecha.before(lc_fechaFinal))
			{
				lc_fecha.add(Calendar.DATE, 1);

				ll_diferencia++;
			}
		}

		return ll_diferencia;
	}

	/**
	 * Retorna el valor del objeto de Calendar.
	 *
	 * @param ac_c correspondiente al valor del tipo de objeto Calendar
	 * @param ai_field correspondiente al valor del tipo de objeto int
	 * @return devuelve el valor de Calendar
	 */
	private static Calendar setActualMinimum(Calendar ac_c, int ai_field)
	{
		if(ac_c != null)
			ac_c.set(ai_field, ac_c.getActualMinimum(ai_field));

		return ac_c;
	}

	/**
	 * Retorna el valor de calendar.
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto Date
	 * @param ab_lenient correspondiente al valor del tipo de objeto boolean
	 * @return el valor de calendar
	 */
	private static Calendar getCalendar(java.util.Date ad_d, boolean ab_lenient)
	{
		Calendar lc_c;

		/* Obtener una instancia del calendario occidental */
		lc_c = getCalendar(ab_lenient);

		if(ad_d != null)
			lc_c.setTime(ad_d);

		return lc_c;
	}

	/**
	 * Retorna el valor de calendar.
	 *
	 * @param ab_lenient correspondiente al valor del tipo de objeto boolean
	 * @return el valor de calendar
	 */
	private static Calendar getCalendar(boolean ab_lenient)
	{
		return getCalendar((Calendar)null, ab_lenient);
	}

	/**
	 * Retorna el valor de calendar.
	 *
	 * @param ac_c correspondiente al valor del tipo de objeto Calendar
	 * @param ab_lenient correspondiente al valor del tipo de objeto boolean
	 * @return el valor de calendar
	 */
	private static Calendar getCalendar(Calendar ac_c, boolean ab_lenient)
	{
		Calendar lc_c;

		/* Obtener una instancia del calendario occidental */
		lc_c = (ac_c != null) ? (Calendar)ac_c.clone()
			                  : new java.util.GregorianCalendar(DEFAULT_TIME_ZONE, DEFAULT_LOCALE);

		lc_c.setLenient(ab_lenient);

		return lc_c;
	}

	/**
	 * Obtener una fecha sin informaci�n desde el campo especificado por <i>ai_field</i>.
	 *
	 * @author Edgar Prieto
	 * @param ac_c correspondiente al valor del tipo de objeto Calendar
	 * @param ai_field Campo de <code>java.util.Calendar</code> desde donde se quiere eliminar la
	 * informaci�n
	 * @return <i>ad_d</i> sin informaci�n de horas, minutos, segundos y milisegundos.
	 */
	private static Calendar getCleanCalendar(Calendar ac_c, int ai_field)
	{
		return clearCalendarFrom(getCalendar(ac_c), ai_field);
	}

	/**
	 * Obtener una fecha sin informaci�n desde el campo especificado por <i>ai_field</i>.
	 *
	 * @author Edgar Prieto
	 * @param ac_c correspondiente al valor del tipo de objeto Calendar
	 * @param ai_field Campo de <code>java.util.Calendar</code> desde donde se quiere eliminar la
	 * informaci�n
	 * @return <i>ad_d</i> sin informaci�n de horas, minutos, segundos y milisegundos.
	 */
	private static java.util.Date getCleanDate(Calendar ac_c, int ai_field)
	{
		return getCleanCalendar(ac_c, ai_field).getTime();
	}

	/**
	 * Retorna el valor de date.
	 *
	 * @param as_date correspondiente al valor del tipo de objeto String
	 * @param as_format correspondiente al valor del tipo de objeto String
	 * @param ab_lenient correspondiente al valor del tipo de objeto boolean
	 * @return el valor de date
	 */
	private static java.util.Date getDate(String as_date, String as_format, boolean ab_lenient)
	{
		java.util.Date ld_d;

		if(StringUtils.isValidString(as_date) && StringUtils.isValidString(as_format))
		{
			SimpleDateFormat lsdf_sdf;

			/* Obtener un objeto para el formato de fechas. La operaci�n de formato debe ser estricta */
			lsdf_sdf = new SimpleDateFormat(as_format);

			lsdf_sdf.setLenient(ab_lenient);

			try
			{
				/* Obtener una fecha representada por as_date en formato as_format */
				ld_d = lsdf_sdf.parse(StringUtils.getString(as_date));
			}
			catch(Exception le_e)
			{
				/* No se pudo realizar la operaci�n de formato */
				ld_d = null;
			}
		}
		else
			ld_d = null;

		return ld_d;
	}

	/**
	 * Retorna el valor de field.
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto Date
	 * @param ai_field correspondiente al valor del tipo de objeto int
	 * @return el valor de field
	 */
	private static int getField(java.util.Date ad_d, int ai_field)
	{
		return getCalendar(ad_d).get(ai_field);
	}

	/**
	 * Retorna el valor de timestamp.
	 *
	 * @param ac_c correspondiente al valor del tipo de objeto Calendar
	 * @return el valor de timestamp
	 */
	public static Timestamp getTimestamp(Calendar ac_c)
	{
		return (ac_c != null) ? getTimestamp(ac_c.getTimeInMillis()) : null;
	}

	/**
	 * Obtener un objeto <code>java.sql.Timestamp</code> a partir de un objeto primitivo
	 * <code>long</code>.
	 *
	 * @author Edgar Prieto
	 * @param al_long correspondiente al valor del tipo de objeto long
	 * @return            Un objeto <code>java.sql.Timestamp</code> representando la fecha dada por
	 *                    <i>al_l</i>.
	 */
	private static Timestamp getTimestamp(long al_long)
	{
		return new Timestamp(al_long);
	}

	/**
	 * Retorna el valor de timestamp not null.
	 *
	 * @param ac_c correspondiente al valor del tipo de objeto Calendar
	 * @return el valor de timestamp not null
	 */
	private static Timestamp getTimestampNotNull(Calendar ac_c)
	{
		return getTimestamp(getCalendar(ac_c));
	}

	/**
	 * Retorna el valor del objeto de Calendar.
	 *
	 * @param ac_c correspondiente al valor del tipo de objeto Calendar
	 * @param ai_field correspondiente al valor del tipo de objeto int
	 * @return devuelve el valor de Calendar
	 */
	private static Calendar clearCalendarFrom(Calendar ac_c, int ai_field)
	{
		Calendar lc_c;

		lc_c = getCalendar(ac_c);

		if(ac_c != null)
		{
			switch(ai_field)
			{
				case Calendar.DAY_OF_MONTH:
					lc_c = clearFromDayOfMonth(lc_c);

					break;

				case Calendar.HOUR:
				case Calendar.HOUR_OF_DAY:
					lc_c = clearFromHour(lc_c);

					break;

				case Calendar.MILLISECOND:
					lc_c = clearFromMillisecond(lc_c);

					break;

				case Calendar.MINUTE:
					lc_c = clearFromMinute(lc_c);

					break;

				case Calendar.MONTH:
					lc_c = clearFromMonth(lc_c);

					break;

				case Calendar.SECOND:
					lc_c = clearFromSecond(lc_c);

					break;

				default:
					break;
			}
		}

		return lc_c;
	}

	/**
	 * Retorna el valor del objeto de java.util.Date
	 *
	 * @param ac_c correspondiente al valor del tipo de objeto Calendar
	 * @param ai_field correspondiente al valor del tipo de objeto int
	 * @return devuelve el valor de java.util.Date
	 */
	private static java.util.Date clearDateFrom(Calendar ac_c, int ai_field)
	{
		return clearCalendarFrom(ac_c, ai_field).getTime();
	}

	/**
	 * Retorna el valor del objeto de Calendar.
	 *
	 * @param ac_c correspondiente al valor del tipo de objeto Calendar
	 * @return devuelve el valor de Calendar
	 */
	private static Calendar clearFromDayOfMonth(Calendar ac_c)
	{
		clearFromHour(ac_c);
		setActualMinimum(ac_c, Calendar.DAY_OF_MONTH);

		return ac_c;
	}

	/**
	 * Retorna el valor del objeto de Calendar.
	 *
	 * @param ac_c correspondiente al valor del tipo de objeto Calendar
	 * @return devuelve el valor de Calendar
	 */
	private static Calendar clearFromHour(Calendar ac_c)
	{
		clearFromMinute(ac_c);
		setActualMinimum(ac_c, Calendar.HOUR);
		setActualMinimum(ac_c, Calendar.HOUR_OF_DAY);

		return ac_c;
	}

	/**
	 * Retorna el valor del objeto de Calendar.
	 *
	 * @param ac_c correspondiente al valor del tipo de objeto Calendar
	 * @return devuelve el valor de Calendar
	 */
	private static Calendar clearFromMillisecond(Calendar ac_c)
	{
		setActualMinimum(ac_c, Calendar.MILLISECOND);

		return ac_c;
	}

	/**
	 * Retorna el valor del objeto de Calendar.
	 *
	 * @param ac_c correspondiente al valor del tipo de objeto Calendar
	 * @return devuelve el valor de Calendar
	 */
	private static Calendar clearFromMinute(Calendar ac_c)
	{
		clearFromSecond(ac_c);
		setActualMinimum(ac_c, Calendar.MINUTE);

		return ac_c;
	}

	/**
	 * Retorna el valor del objeto de Calendar.
	 *
	 * @param ac_c correspondiente al valor del tipo de objeto Calendar
	 * @return devuelve el valor de Calendar
	 */
	private static Calendar clearFromMonth(Calendar ac_c)
	{
		clearFromDayOfMonth(ac_c);
		setActualMinimum(ac_c, Calendar.MONTH);

		return ac_c;
	}

	/**
	 * Retorna el valor del objeto de Calendar.
	 *
	 * @param ac_c correspondiente al valor del tipo de objeto Calendar
	 * @return devuelve el valor de Calendar
	 */
	private static Calendar clearFromSecond(Calendar ac_c)
	{
		clearFromMillisecond(ac_c);
		setActualMinimum(ac_c, Calendar.SECOND);

		return ac_c;
	}
}
