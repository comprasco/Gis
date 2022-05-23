package co.gov.supernotariado.bachue.portalgeografico.model.graphic;

/**
 * 
 * Enumercaciones que contiene las constantes para los tipos de medidas
 *
 */
public enum MeasurementUnit {
	ACRES("esri.Units.ACRES"), ARES("esri.Units.ARES"), CENTIMETERS("esri.Units.CENTIMETERS"),
	DECIMAL_DEGREES("esri.Units.DECIMAL_DEGREES"), DECIMETERS("esri.Units.DECIMETERS"),
	DEGREE_MINUTE_SECONDS("esri.Units.DEGREE_MINUTE_SECONDS"), FEET("esri.Units.DEGREE_MINUTE_SECONDS"),
	HECTARES("esri.Units.HECTARES"), INCHES("esri.Units.INCHES"), KILOMETERS("esri.Units.KILOMETERS"),
	METERS("esri.Units.METERS"), MILES("esri.Units.MILES"), MILLIMETERS("esri.Units.MILLIMETERS"),
	NAUTICAL_MILES("esri.Units.NAUTICAL_MILES"), POINTS("esri.Units.POINTS"),
	SQUARE_CENTIMETERS("esri.Units.SQUARE_CENTIMETERS"), SQUARE_DECIMETERS("esri.Units.SQUARE_DECIMETERS"),
	SQUARE_FEET("esri.Units.SQUARE_FEET"), SQUARE_INCHES("esri.Units.SQUARE_INCHES"),
	SQUARE_KILOMETERS("esri.Units.SQUARE_KILOMETERS"), SQUARE_METERS("esri.Units.SQUARE_METERS"),
	SQUARE_MILES("esri.Units.SQUARE_MILES"), SQUARE_MILLIMETERS("esri.Units.SQUARE_MILLIMETERS"),
	SQUARE_YARDS("esri.Units.SQUARE_YARDS"), UNKNOWN("esri.Units.UNKNOWN"), YARDS("esri.Units.YARDS");

	/**
	 * valor de la enumeración
	 */
	private String value;

	/**
	 * Constructor por omision
	 * 
	 * @param String value valor de la enumeracion
	 */
	private MeasurementUnit(String value) {
		this.value = value;
	}

	/**
	 * Retorna la enumeracion en string
	 * 
	 * @return
	 */
	public String toString() {
		return this.value;
	}
}
