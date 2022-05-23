package co.gov.supernotariado.bachue.portalgeografico.component;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import co.gov.supernotariado.bachue.portalgeografico.model.legend.LegendLayerSymbolMetadata;
import co.gov.supernotariado.bachue.portalgeografico.model.legend.LegendMetadata;
import co.gov.supernotariado.bachue.portalgeografico.model.legend.LegendMetadataBuilder;
import co.gov.supernotariado.bachue.portalgeografico.utilities.ComponentUtilities;
import co.gov.supernotariado.bachue.portalgeografico.utilities.JSFUtilities;
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
@FacesComponent("co.gov.supernotariado.bachue.portalgeografico.component.MapSymbol")
public class UIMapSymbol extends UIOutput {
	/**
	 * Constructor de la clase donse establece la propieedad rendered en null
	 */
	public UIMapSymbol() {
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

		String url = ComponentUtilities.getStringAttribute(this, "url");
		int layer = ComponentUtilities.getIntegerAttribute(this, "layer", 0);
		String label = ComponentUtilities.getStringAttribute(this, "label");

		LegendLayerSymbolMetadata symbol = getSymbol(url, layer, label);
		if (symbol != null) {
			int height = ComponentUtilities.getIntegerAttribute(this, "height", symbol.getHeight());
			int width = ComponentUtilities.getIntegerAttribute(this, "width", symbol.getWidth());
			String title = ComponentUtilities.getStringAttribute(this, "title", symbol.getLabel());
			String alt = ComponentUtilities.getStringAttribute(this, "alt", symbol.getLabel());

			writer.startElement("img", this);
			writer.writeAttribute("id", clientId, null);
			writer.writeAttribute("src", symbol.isImageDataUrlAvailable() ? symbol.getImageDataUrl() : symbol.getUrl(),
					null);
			writer.writeAttribute("height", Integer.valueOf(height), null);
			writer.writeAttribute("width", Integer.valueOf(width), null);
			writer.writeAttribute("title", StringUtilities.defaultString(title), null);
			writer.writeAttribute("alt", StringUtilities.defaultString(alt), null);
			writer.endElement("img");
		} else {
			writer.startElement("i", this);
			writer.writeAttribute("id", clientId, null);
			writer.writeAttribute("class", "fa fa-picture-o", null);
			writer.endElement("i");
		}
	}

	/**
	 * 
	 * @param String url ruta de la leyenda
	 * @param String layer capa
	 * @param String label etiqueta
	 * @return un LegendLayerSymbolMetadata con la metadata
	 */
	private LegendLayerSymbolMetadata getSymbol(String url, int layer, String label) {
		LegendLayerSymbolMetadata symbol = null;
		try {
			LegendMetadataBuilder builder = new LegendMetadataBuilder();
			LegendMetadata legend = builder.build(url);
			symbol = legend.findSymbol(layer, label);
		} catch (Throwable t) {
			String summary = String.format("An error occurred retrieving legend metadata for map service '%s'.",
					new Object[] { url });
			JSFUtilities.addErrorMessage(summary, t.getMessage());
		}
		return symbol;
	}
}
