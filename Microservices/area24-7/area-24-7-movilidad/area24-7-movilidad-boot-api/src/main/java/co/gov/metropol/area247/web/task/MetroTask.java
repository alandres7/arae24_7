package co.gov.metropol.area247.web.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.service.IMetroService;
import co.gov.metropol.area247.util.PropertiesManager;
import co.gov.metropol.area247.util.constantes.Constantes.Recursos;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Component
public class MetroTask {

	@Autowired
	private IMetroService metroService;

	@Scheduled(cron = "${task.metroTask}")
	public void recibirInformacionMetro() {
		try {
			metroService.cargarDatosMetro();
		} catch (Exception e) {
			final String msg = PropertiesManager.obtenerCadena(Recursos.MENSAJES, "metroTask.errorEjecutandoTarea");
			LoggingUtil.logException(msg, e);
			throw new Area247Exception(msg, e);
		}
	}
}
