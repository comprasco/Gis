package co.gov.supernotariado.bachue.portalgeografico.dao.interfaces;

import javax.ejb.Local;

/**
 * Interfaz que contiene los metodos de gestion de usuarios del portal gis.
 * @author datatools
 */
@Local
public interface IGestionUsuarioDao {
	
	/**
	 * Metodo que obtiene los permisos de un usuario.
	 * @param usuario El parámetro usuario define un String que debe contener el usuario que inicio sesion en el portal gis.
	 * @return Un boolean que define si el usuario tiene permisos de ingreso. 
	 */	
	public boolean accesoUsuario(String usuario);

}
