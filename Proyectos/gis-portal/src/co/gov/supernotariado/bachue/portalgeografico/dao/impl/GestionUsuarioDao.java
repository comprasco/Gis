package co.gov.supernotariado.bachue.portalgeografico.dao.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.bachue.snr.servicios.gestion.usuario.RolTypeORU;
import com.bachue.snr.servicios.gestion.usuario.SUTCBGestionUsuarios;
import com.bachue.snr.servicios.gestion.usuario.SUTCBGestionUsuariosSOAP12BindingQSService;
import com.bachue.snr.servicios.gestion.usuario.TipoEntradaObtenerRolesUsuario;
import com.bachue.snr.servicios.gestion.usuario.TipoSalidaObtenerRolesUsuario;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;
import co.gov.supernotariado.bachue.portalgeografico.dao.interfaces.IGestionUsuarioDao;
import co.gov.supernotariado.bachue.portalgeografico.dao.interfaces.IGisParametrosDao;




/**
 * Clase obtiene por medio del ws de gestion de usuarios los permisos de un usuario.
 * @author datatools
 */
@Stateless
@LocalBean
public class GestionUsuarioDao implements IGestionUsuarioDao {

	@EJB
	private IGisParametrosDao gisParametrosDao;
	
	/**
	 * Metodo que obtiene los permisos de un usuario.
	 * @param usuario El parámetro usuario define un String que debe contener el usuario que inicio sesion en el portal gis.
	 * @return Un boolean que define si el usuario tiene permisos de ingreso. 
	 */	
	@Override
	public boolean accesoUsuario(String usuario) {
		boolean estado = false;

		String modulo = gisParametrosDao.getValue(Constants.COD_MODULO_ARCGIS);
		TipoEntradaObtenerRolesUsuario e = new TipoEntradaObtenerRolesUsuario();
		e.setComponente(modulo);
		e.setLoginUsuario(usuario);

		TipoSalidaObtenerRolesUsuario s = new TipoSalidaObtenerRolesUsuario();
		s = obtenerRolesUsuario(e);
		int cod = s.getCodigoMensaje().intValue();
		List<RolTypeORU> roles = s.getRoles().getRol();
		String rol = roles.get(0).getCodigoRol();
		if ((cod == 200) && (!rol.equals("")) && (!rol.equals(null))) {
			estado = true;
		}
		return estado;
	}

	/**
	 * Metodo que consume el servicio web de gestion de usuarios.
	 * @param entrada El parámetro entrada define un objeto TipoEntradaObtenerRolesUsuario que debe contener el usuario y modulo.
	 * @return Un objeto TipoSalidaObtenerRolesUsuario que contiene la respuesta del servicio. 
	 */	
	private TipoSalidaObtenerRolesUsuario obtenerRolesUsuario(TipoEntradaObtenerRolesUsuario entrada) {
		URL url;
		try {
			url = new URL(gisParametrosDao.getValue(Constants.URL_WS_GESTION_USUARIOS));
			SUTCBGestionUsuariosSOAP12BindingQSService service = new SUTCBGestionUsuariosSOAP12BindingQSService(url);
			SUTCBGestionUsuarios port = service.getSUTCBGestionUsuariosSOAP12BindingQSPort();
			TipoSalidaObtenerRolesUsuario s = port.obtenerRolesUsuario(entrada);
			return s;
		} catch (MalformedURLException e) {
			return null;
		}
		
	}

}
