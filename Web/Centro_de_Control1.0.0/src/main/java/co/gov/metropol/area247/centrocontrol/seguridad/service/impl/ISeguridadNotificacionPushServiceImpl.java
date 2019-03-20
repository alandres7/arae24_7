package co.gov.metropol.area247.centrocontrol.seguridad.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.model.NotificacionPush;
import co.gov.metropol.area247.centrocontrol.seguridad.dao.ISeguridadNotificacionPushRepository;
import co.gov.metropol.area247.centrocontrol.seguridad.service.ISeguridadNotificacionPushService;

@Service
public class ISeguridadNotificacionPushServiceImpl implements ISeguridadNotificacionPushService {

	@Autowired
	ISeguridadNotificacionPushRepository notificacionPushDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(ISeguridadNotificacionPushServiceImpl.class);

	@Override
	public List<NotificacionPush> notificacionPushObtenerTodos() throws Exception {
		List<NotificacionPush> listNotifi = null;
		try {
			listNotifi = notificacionPushDao.ListarTodosOrdenadosPorId();
			if (listNotifi.isEmpty()) {
				LOGGER.info("No se encuentran notificaciones push");
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listNotifi;
	}

	@Override
	public NotificacionPush notificacionPushObtenerPorId(Long id) throws Exception {
		NotificacionPush notificacionPush = notificacionPushDao.findOne(id);
		if (notificacionPush == null) {
			LOGGER.info("No se encuentra una Notificacion Push con id: " + id);
		}
		return notificacionPush;
	}

	@Override
	public boolean notificacionPushGuardar(NotificacionPush nuevaNotificacionPush) {
		try {
			notificacionPushDao.save(nuevaNotificacionPush);
			LOGGER.info("Notificacion Push guardada con exito");
			return true;
		} catch (Exception e) {
			LOGGER.error("No se pudo guardar la Notificacion Push. Error: " + e);
			return false;
		}

	}

}
