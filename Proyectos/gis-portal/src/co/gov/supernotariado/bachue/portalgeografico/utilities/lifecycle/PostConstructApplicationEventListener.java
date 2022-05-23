
package co.gov.supernotariado.bachue.portalgeografico.utilities.lifecycle;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;
import co.gov.supernotariado.bachue.portalgeografico.utilities.lifecycle.PostConstructApplicationEventListener;

/**
 * 
 * Al implementar esta clase, un objeto indica que es un evento para uno o más
 * tipos de eventos del sistema. El tipo exacto de evento que hará que se
 * invoque el método processEvent de la clase implementadora se indica mediante
 * el argumento facesEventClass que se pasa cuando el escucha se instala usando
 * Application.subscribeToEvent
 *
 */
public class PostConstructApplicationEventListener implements SystemEventListener {
	private static final Logger logger = Logger
			.getLogger(PostConstructApplicationEventListener.class.getCanonicalName());

	/**
	 * Este método debe devolver verdadero si y solo si esta instancia de escucha
	 * está interesada en recibir eventos de la instancia a la que hace referencia
	 * el parámetro fuente.
	 * 
	 * @param Object source recurso
	 * @return boolean retorna con el estado
	 */
	public boolean isListenerForSource(Object source) {
		return source instanceof Application;
	}

	/**
	 * Cuando se le llama, el evento puede asumir que cualquier garantía dada en el
	 * javadoc para la subclase específica de SystemEvent es verdadera.
	 * 
	 * @param SystemEvent event Evento que se hara seguimiento
	 */
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		logger.info(
				Constants.MENSAJE_POSTCONSTRUCT_1 + getProperties().getProperty(Constants.VERSION_POSTCONSTRUCT, ""));
	}

	/**
	 * Metodo que busca en el archivo pom las propiedades qur contiene
	 * 
	 * @return un objeto de tipo Properties
	 */
	private Properties getProperties() {
		Properties props = new Properties();
		try {
			InputStream stream = getClass().getResourceAsStream(Constants.POM_PROPERTIES_POSTCONSTRUCT);

			props.load(stream);

			stream.close();
		} catch (Exception e) {
			logger.info(Constants.MENSAJE_POSTCONSTRUCT_2);
		}
		return props;
	}
}
