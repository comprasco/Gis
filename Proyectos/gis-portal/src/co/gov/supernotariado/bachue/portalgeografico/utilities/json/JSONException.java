package co.gov.supernotariado.bachue.portalgeografico.utilities.json;

/**
 * 
 * Clase de excepciones json
 *
 */
public class JSONException extends Exception {

	/**
	 * Variable estática serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Variable cause Throwable que contiene la causa de la excepcion.
	 */
	private Throwable cause;

	/**
	 * Metodo constructor
	 * 
	 * @param String message mensaje
	 */
	public JSONException(String message) {
		super(message);
	}

	/**
	 * Metodo constructor
	 * 
	 * @param Throwable t excepcion
	 */
	public JSONException(Throwable t) {
		super(t.getMessage());
		this.cause = t;
	}

	/**
	 * Metodo de excepciones que obtiene la causa
	 * 
	 * @return un Throwable con la causa
	 */
	public Throwable getCause() {
		return this.cause;
	}
}