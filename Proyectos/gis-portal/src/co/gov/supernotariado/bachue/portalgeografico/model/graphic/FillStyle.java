package co.gov.supernotariado.bachue.portalgeografico.model.graphic;

/**
 * 
 * Enumercaciones que contiene las constantes para los tipos de estilos de
 * relleno de un gráfico
 *
 */
public enum FillStyle {
	BACKWARD_DIAGONAL("esri.symbol.SimpleFillSymbol.STYLE_BACKWARD_DIAGONAL"),
	CROSS("esri.symbol.SimpleFillSymbol.STYLE_CROSS"),
	DIAGONAL_CROSS("esri.symbol.SimpleFillSymbol.STYLE_DIAGONAL_CROSS"),
	FORWARD_DIAGONAL("esri.symbol.SimpleFillSymbol.STYLE_FORWARD_DIAGONAL"),
	HORIZONTAL("esri.symbol.SimpleFillSymbol.STYLE_HORIZONTAL"), NONE("esri.symbol.SimpleFillSymbol.STYLE_NULL"),
	SOLID("esri.symbol.SimpleFillSymbol.STYLE_SOLID"), VERTICAL("esri.symbol.SimpleFillSymbol.STYLE_VERTICAL");

	/**
	 * valor de la enumeración
	 */
	private String value;

	/**
	 * Constructor por omision
	 * 
	 * @param String value valor de la enumeracion
	 */
	private FillStyle(String value) {
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
