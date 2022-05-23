package co.gov.supernotariado.bachue.portalgeografico.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;

/**
 * 
 * Esta clase extiende de UIOutput es un componente para un servicio de mapa que
 * tiene un valor, opcionalmente recuperado de un bean de nivel de modelo a
 * través de una expresión de valor, que se muestra al usuario. El usuario no
 * puede modificar directamente el valor representado; es solo para fines de
 * visualización.
 *
 */
@FacesComponent("co.gov.supernotariado.bachue.portalgeografico.component.MapServiceLayer")
public class UIMapServiceLayer extends UIOutput {

	/**
	 * Constructor de la clase donse establece la propieedad rendered en null
	 */
	public UIMapServiceLayer() {
		setRendererType(null);
	}
}