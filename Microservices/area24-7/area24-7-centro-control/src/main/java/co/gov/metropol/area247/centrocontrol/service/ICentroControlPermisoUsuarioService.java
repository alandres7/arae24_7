package co.gov.metropol.area247.centrocontrol.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.PermisoUsuario;


public interface ICentroControlPermisoUsuarioService {
	/**
	 * Servicio para obtener la Lista de PermisosUsuarios de un usuario dado
	 * @param idPermisoUsuario id del usuario
	 * @return Lista de PermisoUsuario del usuario dado, null en caso de error
	 */
	List<PermisoUsuario> permisoUsuarioObtenerPorIdUsuario(Long idPermisoUsuario);
	/**
	 * Servicio para guardar un PermisoUsuario
	 * @param permisoUsuario PermisoUsuario a guardar
	 * @return PermisoUsuario guardado, null si hubo un error al guardar 
	 */
	PermisoUsuario permisoUsuarioGuardar(PermisoUsuario permisoUsuario);
	/**
	 * Servicio para guardar un PermisoUsuario. Se garantiza es transaccional
	 * @param PermisoUsuario a guardar
	 * @return PermisoUsuario guardado, null en caso de error.
	 */
	List<PermisoUsuario> permisoUsuarioGuardarPorLote(List<PermisoUsuario> permisos);
//	/**
//	 * Servicio para retornar un objeto similar a PermisoUsuario, adicionando nombre del objeto
//	 * @param idPermisoUsuario
//	 * @return
//	 */
//	List<PermisoUsuarioDto> permisoUsuarioObtenerPermisosDto(Long idPermisoUsuario);
	/**
	 * Servicio para eliminar un elemento PermisoUsuario
	 * @param idPermisoUsuario id valido del elemento a borrar
	 * @return true si fue exitoso, false si no se pudo.
	 */
	boolean permisoUsuarioEliminar(Long idPermisoUsuario);
	/**
	 * 
	 * @param idUsuario
	 * @param idObjeto
	 * @return
	 */
	PermisoUsuario permisoUsuarioObtenerPermisoPorUsuarioPorObjeto(Long idUsuario, Long idObjeto);
}
