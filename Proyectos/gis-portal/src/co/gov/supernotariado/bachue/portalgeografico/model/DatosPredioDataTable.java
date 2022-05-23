package co.gov.supernotariado.bachue.portalgeografico.model;

/**
 * Esta clase define el modelo de la tabla datos del predio
 * @author datatools
 */
public class DatosPredioDataTable {

	/**
	 * Variable folioMatricula
	 * String que contiene la matricula del predio.
	 */
	private String folioMatricula;
	/**
	 * Variable identificacion
	 * String que contiene la identificacion del propietario.
	 */
	private String identificacion;
	/**
	 * Variable nombreTitular
	 * String que contiene el nombre del propietario.
	 */
	private String nombreTitular;
	/**
	 * Variable direccion
	 * String que contiene la direccion.
	 */
	private String direccion;
	/**
	 * Variable participacion
	 * String que contiene el porcentaje de participacion.
	 */
	private String participacion;

    /**
     * Constructor de la clase DatosPredioDataTable
     * @param String folioMatricula, String identificacion, String nombreTitular, String direccion, String participacion
     */
	public DatosPredioDataTable(String folioMatricula, String identificacion, String nombreTitular, String direccion,
			String participacion) {
		super();
		this.folioMatricula = folioMatricula;
		this.identificacion = identificacion;
		this.nombreTitular = nombreTitular;
		this.direccion = direccion;
		this.participacion = participacion;
	}

	public String getFolioMatricula() {
		return folioMatricula;
	}

	public void setFolioMatricula(String folioMatricula) {
		this.folioMatricula = folioMatricula;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombreTitular() {
		return nombreTitular;
	}

	public void setNombreTitular(String nombreTitular) {
		this.nombreTitular = nombreTitular;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getParticipacion() {
		return participacion;
	}

	public void setParticipacion(String participacion) {
		this.participacion = participacion;
	}

}
