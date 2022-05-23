package co.gov.supernotariado.bachue.portalgeografico.model;

/**
 * Esta clase define el modelo de la tabla de anotaciones
 * @author datatools
 */
public class AnotacionesDataTable {
	
	/**
	 * Variable folioMatricula
	 * String que contiene el folio de matricula del predio.
	 */
	private String folioMatricula;
	/**
	 * Variable consecutivo
	 * String que contiene el consecutivo de la lista.
	 */
	private String consecutivo;
	/**
	 * Variable identificacion
	 * String que contiene la identificacion del interviniente.
	 */
	private String identificacion;
	/**
	 * Variable nombInterviniente
	 * String que contiene el nombre del interviniente.
	 */
	private String nombInterviniente;
	/**
	 * Variable rol
	 * String que contiene el rol del interviniente.
	 */
	private String rol;
	
    /**
     * Constructor de la clase AnotacionesDataTable
     * @param String folioMatricula, String consecutivo, String identificacion,String nombInterviniente, String rol
     */
	public AnotacionesDataTable(String folioMatricula, String consecutivo, String identificacion,
			String nombInterviniente, String rol) {
		super();
		this.folioMatricula = folioMatricula;
		this.consecutivo = consecutivo;
		this.identificacion = identificacion;
		this.nombInterviniente = nombInterviniente;
		this.rol = rol;
	}
	public String getFolioMatricula() {
		return folioMatricula;
	}
	public void setFolioMatricula(String folioMatricula) {
		this.folioMatricula = folioMatricula;
	}
	public String getConsecutivo() {
		return consecutivo;
	}
	public void setConsecutivo(String consecutivo) {
		this.consecutivo = consecutivo;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public String getNombInterviniente() {
		return nombInterviniente;
	}
	public void setNombInterviniente(String nombInterviniente) {
		this.nombInterviniente = nombInterviniente;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	

}
