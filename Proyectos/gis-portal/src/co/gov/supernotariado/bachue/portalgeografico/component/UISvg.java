package co.gov.supernotariado.bachue.portalgeografico.component;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import co.gov.supernotariado.bachue.portalgeografico.utilities.ComponentUtilities;
import co.gov.supernotariado.bachue.portalgeografico.utilities.StringUtilities;

/**
 * 
 * Esta clase extiende de UIOutput es un componente para la leyenda de mapa que
 * tiene un valor, opcionalmente recuperado de un bean de nivel de modelo a
 * través de una expresión de valor, que se muestra al usuario. El usuario no
 * puede modificar directamente el valor representado; es solo para fines de
 * visualización.
 *
 */
@FacesComponent("co.gov.supernotariado.bachue.portalgeografico.component.Svg")
public class UISvg extends UIOutput {
	/**
	 * Constructor de la clase donse establece la propieedad rendered en null
	 */
	public UISvg() {
		setRendererType(null);
	}

	/**
	 * Representa el UIComponent especificado al principio en la secuencia de salida
	 * o escritor asociado con la respuesta que estamos creando.+
	 * 
	 * @param FacesContext context contexto de la aplicacion
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();

		String clientId = getClientId();

		String style = ComponentUtilities.getStringAttribute(this, "style");
		String styleClass = ComponentUtilities.getStringAttribute(this, "styleClass");
		String height = ComponentUtilities.getStringAttribute(this, "height");
		String width = ComponentUtilities.getStringAttribute(this, "width");
		String title = ComponentUtilities.getStringAttribute(this, "title");
		String path = ComponentUtilities.getStringAttribute(this, "path");
		String fillColor = ComponentUtilities.getStringAttribute(this, "fillColor", "#FFFFFF");
		double fillOpacity = ComponentUtilities.getDoubleAttribute(this, "fillOpacity", 1.0D);
		String strokeColor = ComponentUtilities.getStringAttribute(this, "strokeColor", "#000000");
		double strokeOpacity = ComponentUtilities.getDoubleAttribute(this, "strokeOpacity", 1.0D);
		double strokeWidth = ComponentUtilities.getDoubleAttribute(this, "strokeWidth", 1.0D);
		String message = ComponentUtilities.getStringAttribute(this, "message", "SVG not supported.");

		writer.startElement("svg", this);
		writer.writeAttribute("id", clientId, null);
		writer.writeAttribute("style", style, null);
		writer.writeAttribute("styleClass", styleClass, null);
		writer.writeAttribute("height", height, null);
		writer.writeAttribute("width", width, null);
		if (title != null) {
			writer.startElement("title", this);
			writer.write(StringUtilities.defaultString(title));
			writer.endElement("title");
		}
		if (path != null) {
			writer.startElement("path", this);
			writer.writeAttribute("d", path, null);
			writer.writeAttribute("fill", fillColor, null);
			writer.writeAttribute("fill-opacity", Double.valueOf(fillOpacity), null);
			writer.writeAttribute("stroke", strokeColor, null);
			writer.writeAttribute("stroke-opacity", Double.valueOf(strokeOpacity), null);
			writer.writeAttribute("stroke-width", Double.valueOf(strokeWidth), null);
			writer.endElement("path");
		}
		writer.write(StringUtilities.defaultString(message));

		writer.endElement("svg");
	}
}
