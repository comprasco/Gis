package co.gov.supernotariado.bachue.portalgeografico.model;

/**
 * Esta clase define el modelo de el formato json para consumir el servicio de datos registrales por nombres
 * @author datatools
 */
public class NameSearch {
	
	/**
	 * Variable primerNombre
	 * String que contiene el primer nombre.
	 */
	private String primerNombre;
	/**
	 * Variable segundoNombre
	 * String que contiene el segundo nombre.
	 */
	private String segundoNombre;
	/**
	 * Variable primerApellido
	 * String que contiene el primer apellido.
	 */
	private String primerApellido;
	/**
	 * Variable segundoApellido
	 * String que contiene el segundo apellido.
	 */
	private String segundoApellido;
	/**
	 * Variable razonSocial
	 * String que contiene la razon social.
	 */
	private String razonSocial;
	
    /**
     * Constructor de la clase NameSearch
     * @param String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String razonSocial
     */
	public NameSearch(String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
			String razonSocial) {
		super();
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.razonSocial = razonSocial;
	}
	
	
	public String getPrimerNombre() {
		return primerNombre;
	}
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}
	public String getSegundoNombre() {
		return segundoNombre;
	}
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

}
