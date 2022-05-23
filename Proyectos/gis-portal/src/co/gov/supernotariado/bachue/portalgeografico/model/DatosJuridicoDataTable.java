package co.gov.supernotariado.bachue.portalgeografico.model;

/**
 * Esta clase define el modelo de la tabla de datos juridicos
 * @author datatools
 */
public class DatosJuridicoDataTable {
	
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
	 * Variable datosDocumento
	 * String que contiene los datos del documento.
	 */
	private String datosDocumento;
	/**
	 * Variable actoJuridico
	 * String que contiene el acto juridico del predio.
	 */
	private String actoJuridico;
	/**
	 * Variable dRR
	 * String que contiene el dRR del predio.
	 */
	private String dRR;
	/**
	 * Variable fechaAnotacion
	 * String que contiene la fecha de la anotacion del predio.
	 */
	private String fechaAnotacion;
	/**
	 * Variable valor
	 * String que contiene el valor.
	 */
	private String valor;	
	
    /**
     * Constructor de la clase DatosJuridicoDataTable
     * @param String folioMatricula, String consecutivo, String datosDocumento, String actoJuridico, String dRR, 
     * String fechaAnotacion, String valor
     */
	public DatosJuridicoDataTable(String folioMatricula, String consecutivo, String datosDocumento, String actoJuridico,
			String dRR, String fechaAnotacion, String valor) {
		super();
		this.folioMatricula = folioMatricula;
		this.consecutivo = consecutivo;
		this.datosDocumento = datosDocumento;
		this.actoJuridico = actoJuridico;
		this.dRR = dRR;
		this.fechaAnotacion = fechaAnotacion;
		this.valor = valor;
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
	public String getDatosDocumento() {
		return datosDocumento;
	}
	public void setDatosDocumento(String datosDocumento) {
		this.datosDocumento = datosDocumento;
	}
	public String getActoJuridico() {
		return actoJuridico;
	}
	public void setActoJuridico(String actoJuridico) {
		this.actoJuridico = actoJuridico;
	}
	public String getdRR() {
		return dRR;
	}
	public void setdRR(String dRR) {
		this.dRR = dRR;
	}
	public String getFechaAnotacion() {
		return fechaAnotacion;
	}
	public void setFechaAnotacion(String fechaAnotacion) {
		this.fechaAnotacion = fechaAnotacion;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	

}
