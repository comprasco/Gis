package co.gov.supernotariado.bachue.portalgeografico.model.graphic;

/**
 * Enumercaciones que contiene las constantes para los tipos de líneas
 */
public enum LineStyle {
	DASH("esri.symbol.SimpleLineSymbol.STYLE_DASH"), DASHDOT("esri.symbol.SimpleLineSymbol.STYLE_DASHDOT"),
	DASHDOTDOT("esri.symbol.SimpleLineSymbol.STYLE_DASHDOTDOT"), DOT("esri.symbol.SimpleLineSymbol.STYLE_DOT"),
	LONGDASH("esri.symbol.SimpleLineSymbol.STYLE_LONGDASH"),
	LONGDASHDOT("esri.symbol.SimpleLineSymbol.STYLE_LONGDASHDOT"), NONE("esri.symbol.SimpleLineSymbol.STYLE_NULL"),
	SHORTDASH("esri.symbol.SimpleLineSymbol.STYLE_SHORTDASH"),
	SHORTDASHDOT("esri.symbol.SimpleLineSymbol.STYLE_SHORTDASHDOT"),
	SHORTDASHDOTDOT("esri.symbol.SimpleLineSymbol.STYLE_SHORTDASHDOTDOT"),
	SHORTDOT("esri.symbol.SimpleLineSymbol.STYLE_SHORTDOT"), SOLID("esri.symbol.SimpleLineSymbol.STYLE_SOLID");

	/**
	 * valor de la enumeración
	 */
	private String value;

	/**
	 * Constructor por omision
	 * 
	 * @param String value valor de la enumeracion
	 */
	private LineStyle(String value) {
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
