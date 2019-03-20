package co.gov.metropol.area247.centrocontrol.seguridad.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.NotificacionPush;


public interface ISeguridadNotificacionPushService {
	/**
	 * Metodo para retornar todas las notificaciones push
	 * @return Lista de notificaciones push
	 * @throws Exception si algo falla
	 */
	List<NotificacionPush> notificacionPushObtenerTodos()throws Exception;
	/**
	 * Metodo para obtener notificacion push por id
	 * @param id de la notificacion push a consultar
	 * @return notificacion push con id especificado
	 * @throws Exception
	 */
	NotificacionPush notificacionPushObtenerPorId(Long id)throws Exception;
	/**
	 * Metodo para guardar / crear una notificacion push
	 * @param nueva notificacion push a crear
	 * @return notificacion push a agregar
	 * @throws Exception
	 */
	boolean notificacionPushGuardar(NotificacionPush notificacionPush);

}
