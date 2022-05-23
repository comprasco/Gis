package co.gov.supernotariado.bachue.portalgeografico.model;

/**
 * Esta clase define el modelo de el formato json para consumir el servicio de datos registrales por direccion
 * @author datatools
 */
public class AddressSearch {
	
	/**
	 * Variable Departamento
	 * String que contiene el nombre departamento del predio.
	 */
	private String Departamento;
	/**
	 * Variable Municipio
	 * String que contiene el nombre municipio del predio.
	 */
	private String Municipio;
	/**
	 * Variable Vereda
	 * String que contiene el nombre de la vereda del predio.
	 */
	private String Vereda;
	/**
	 * Variable idTipoEje
	 * String que contiene el idTipoEje del predio.
	 */
	private String idTipoEje;
	/**
	 * Variable datoEjePrincipal
	 * String que contiene el datoEjePrincipal del predio.
	 */
	private String datoEjePrincipal;
	/**
	 * Variable prefijoEje
	 * String que contiene el prefijoEje del predio.
	 */
	private String prefijoEje;
	/**
	 * Variable coordenadaEje
	 * String que contiene la coordenadaEje del predio.
	 */
	private String coordenadaEje;
	
	
	/**
	 * Variable idTipoEje1
	 * String que contiene el idTipoEje1 del predio.
	 */
	private String idTipoEje1;
	/**
	 * Variable datoEjeSecundario
	 * String que contiene el datoEjeSecundario del predio.
	 */
	private String datoEjeSecundario;
	/**
	 * Variable prefijoEje1
	 * String que contiene el prefijoEje1 del predio.
	 */
	private String prefijoEje1;
	/**
	 * Variable numero
	 * String que contiene el numero del predio.
	 */
	private String numero;
	/**
	 * Variable coordenadaEje1
	 * String que contiene la coordenadaEje1 del predio.
	 */
	private String coordenadaEje1;
	/**
	 * Variable complementoDireccion
	 * String que contiene el complemento de la direccion del predio.
	 */
	private String complementoDireccion;
	/**
	 * Variable direccionCompleta
	 * String que contiene la direccion completa del predio.
	 */
	private String direccionCompleta;
	
	
    /**
     * Constructor de la clase AddressSearch
     * @param String departamento, String municipio, String vereda, String idTipoEje,String datoEjePrincipal, 
     * String prefijoEje, String coordenadaEje, String idTipoEje1,String datoEjeSecundario, String prefijoEje1, 
     * String numero, String coordenadaEje1, String complementoDireccion, String direccionCompleta
     */
	public AddressSearch(String departamento, String municipio, String vereda, String idTipoEje,
			String datoEjePrincipal, String prefijoEje, String coordenadaEje, String idTipoEje1,
			String datoEjeSecundario, String prefijoEje1, String numero, String coordenadaEje1,
			String complementoDireccion, String direccionCompleta) {
		super();
		Departamento = departamento;
		Municipio = municipio;
		Vereda = vereda;
		this.idTipoEje = idTipoEje;
		this.datoEjePrincipal = datoEjePrincipal;
		this.prefijoEje = prefijoEje;
		this.coordenadaEje = coordenadaEje;
		this.idTipoEje1 = idTipoEje1;
		this.datoEjeSecundario = datoEjeSecundario;
		this.prefijoEje1 = prefijoEje1;
		this.numero = numero;
		this.coordenadaEje1 = coordenadaEje1;
		this.complementoDireccion = complementoDireccion;
		this.direccionCompleta = direccionCompleta;
	}
	
	
	public String getIdTipoEje() {
		return idTipoEje;
	}
	public void setIdTipoEje(String idTipoEje) {
		this.idTipoEje = idTipoEje;
	}
	public String getDatoEjePrincipal() {
		return datoEjePrincipal;
	}
	public void setDatoEjePrincipal(String datoEjePrincipal) {
		this.datoEjePrincipal = datoEjePrincipal;
	}
	public String getPrefijoEje() {
		return prefijoEje;
	}
	public void setPrefijoEje(String prefijoEje) {
		this.prefijoEje = prefijoEje;
	}
	public String getCoordenadaEje() {
		return coordenadaEje;
	}
	public void setCoordenadaEje(String coordenadaEje) {
		this.coordenadaEje = coordenadaEje;
	}
	public String getIdTipoEje1() {
		return idTipoEje1;
	}
	public void setIdTipoEje1(String idTipoEje1) {
		this.idTipoEje1 = idTipoEje1;
	}
	public String getDatoEjeSecundario() {
		return datoEjeSecundario;
	}
	public void setDatoEjeSecundario(String datoEjeSecundario) {
		this.datoEjeSecundario = datoEjeSecundario;
	}
	public String getPrefijoEje1() {
		return prefijoEje1;
	}
	public void setPrefijoEje1(String prefijoEje1) {
		this.prefijoEje1 = prefijoEje1;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getCoordenadaEje1() {
		return coordenadaEje1;
	}
	public void setCoordenadaEje1(String coordenadaEje1) {
		this.coordenadaEje1 = coordenadaEje1;
	}
	public String getComplementoDireccion() {
		return complementoDireccion;
	}
	public void setComplementoDireccion(String complementoDireccion) {
		this.complementoDireccion = complementoDireccion;
	}
	public String getDireccionCompleta() {
		return direccionCompleta;
	}
	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}


	public String getDepartamento() {
		return Departamento;
	}


	public void setDepartamento(String departamento) {
		Departamento = departamento;
	}


	public String getMunicipio() {
		return Municipio;
	}


	public void setMunicipio(String municipio) {
		Municipio = municipio;
	}


	public String getVereda() {
		return Vereda;
	}


	public void setVereda(String vereda) {
		Vereda = vereda;
	}
	
	
}
