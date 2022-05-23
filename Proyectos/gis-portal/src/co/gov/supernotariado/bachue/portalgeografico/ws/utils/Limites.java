package co.gov.supernotariado.bachue.portalgeografico.ws.utils;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;


/**
 * Clase que contiene todos las propiedades Limites.
 *
 * @author  Carlos Calder�n
 * Fecha de Creacion: 27/05/2020
 */
public class Limites
{
	/** Propiedad id xmax. */
	private double id_xmax;

	/** Propiedad id xmin. */
	private double id_xmin;

	/** Propiedad id ymax. */
	private double id_ymax;

	/** Propiedad id ymin. */
	private double id_ymin;

	/**
	 * Instancia un nuevo objeto limites.
	 */
	public Limites()
	{
		id_xmax     = (Double.MAX_VALUE - 1d) * -1d;
		id_xmin     = Double.MAX_VALUE;
		id_ymax     = id_xmax;
		id_ymin     = id_xmin;
	}

	/**
	 * Instancia un nuevo objeto limites.
	 *
	 * @param ajsona_puntos de ajsona puntos
	 * @throws JSONException cuando se produce algun error en el proceso
	 */
	public Limites(JSONArray ajsona_puntos)
	    throws JSONException
	{
		this();

		if((ajsona_puntos != null) && (ajsona_puntos.length() > 0))
		{
			JSONArray ljsona_puntos;

			ljsona_puntos = ajsona_puntos.getJSONArray(0);

			if(ljsona_puntos != null)
			{
				double ld_xmax;
				double ld_xmin;
				double ld_ymax;
				double ld_ymin;

				ld_xmax     = getXmax();
				ld_xmin     = getXmin();
				ld_ymax     = getYmax();
				ld_ymin     = getYmin();

				for(int li_i = 0, li_tam = ljsona_puntos.length(); li_i < li_tam; li_i++)
				{
					JSONArray ljsona_punto;

					ljsona_punto = ljsona_puntos.getJSONArray(li_i);

					if(ljsona_punto != null)
					{
						double ld_x;
						double ld_y;

						ld_x     = ljsona_punto.getDouble(0);
						ld_y     = ljsona_punto.getDouble(1);

						if(ld_x > ld_xmax)
							ld_xmax = ld_x;

						if(ld_x < ld_xmin)
							ld_xmin = ld_x;

						if(ld_y > ld_ymax)
							ld_ymax = ld_y;

						if(ld_y < ld_ymin)
							ld_ymin = ld_y;
					}
				}

				setXmax(ld_xmax);
				setXmin(ld_xmin);
				setYmax(ld_ymax);
				setYmin(ld_ymin);
			}
		}
	}

	/**
	 * Modifica el valor de Xmax.
	 *
	 * @param ad_d de ad d
	 */
	public void setXmax(double ad_d)
	{
		id_xmax = ad_d;
	}

	/**
	 * Retorna Objeto o variable de valor xmax.
	 *
	 * @return el valor de xmax
	 */
	public double getXmax()
	{
		return id_xmax;
	}

	/**
	 * Modifica el valor de Xmin.
	 *
	 * @param ad_d de ad d
	 */
	public void setXmin(double ad_d)
	{
		id_xmin = ad_d;
	}

	/**
	 * Retorna Objeto o variable de valor xmin.
	 *
	 * @return el valor de xmin
	 */
	public double getXmin()
	{
		return id_xmin;
	}

	/**
	 * Modifica el valor de Ymax.
	 *
	 * @param ad_d de ad d
	 */
	public void setYmax(double ad_d)
	{
		id_ymax = ad_d;
	}

	/**
	 * Retorna Objeto o variable de valor ymax.
	 *
	 * @return el valor de ymax
	 */
	public double getYmax()
	{
		return id_ymax;
	}

	/**
	 * Modifica el valor de Ymin.
	 *
	 * @param ad_d de ad d
	 */
	public void setYmin(double ad_d)
	{
		id_ymin = ad_d;
	}

	/**
	 * Retorna Objeto o variable de valor ymin.
	 *
	 * @return el valor de ymin
	 */
	public double getYmin()
	{
		return id_ymin;
	}

	/**
	 * to String.
	 *
	 * @return el valor de String
	 */
	@Override
	public String toString()
	{
		return "xmin: " + id_xmin + " | ymin: " + id_ymin + " | xmax: " + id_xmax + " | ymax: " + id_ymax;
	}
}
