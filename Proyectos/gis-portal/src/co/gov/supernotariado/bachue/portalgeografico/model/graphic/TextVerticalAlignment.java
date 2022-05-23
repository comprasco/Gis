package co.gov.supernotariado.bachue.portalgeografico.model.graphic;

/**
 * 
 * Enumercaciones que contiene las constantes para los tipos de centrado
 * vertical del texto
 *
 */
public enum TextVerticalAlignment {
	BASELINE, TOP, MIDDLE, BOTTOM;

	/**
	 * Constructor por default de la enumeracion
	 */
	private TextVerticalAlignment() {
	}

	/**
	 * 
	 * @param String value valor de la enumeracion
	 */
	public String toString() {
		return name().toLowerCase();
	}
}
