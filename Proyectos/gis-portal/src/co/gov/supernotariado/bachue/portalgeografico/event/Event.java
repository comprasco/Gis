package co.gov.supernotariado.bachue.portalgeografico.event;

/**
 * 
 * Enumercaciones que contiene las constantes para los tipos de eventos de un
 * visor geográfico
 *
 */
public enum Event {
	CLICK, EXTENT, VIEW, ACTION, DRAG, GEOLOCATION;

    /**
     * Constructor de la clase Event
     */
	private Event() {
	}

	/**
	 * Metodo que retorna la representacion de un objeto en String.
	 * @return un String en minuscula
	 */
	public String toString() {
		return name().toLowerCase();
	}

}