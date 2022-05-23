package co.gov.supernotariado.bachue.portalgeografico.model.graphic;

/**
 * 
 * Enumercaciones que contiene las constantes para los tipos de centrado
 * horizontal del texto
 *
 */
public enum TextHorizontalAlignment {
	LEFT, RIGHT, CENTER, JUSTIFY;

	/**
	 * Constructor por default de la enumeracion
	 */
	private TextHorizontalAlignment() {
	}

	/**
	 * Retorna la enumeracion en string
	 * 
	 * @return
	 */
	public String toString() {
		return name().toLowerCase();
	}
}