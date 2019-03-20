package co.gov.metropol.area247.web.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.service.IDeiService;
import co.gov.metropol.area247.util.PropertiesManager;
import co.gov.metropol.area247.util.constantes.Constantes.Recursos;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Component
public class DeiTask {

	private final static Logger LOGGER = LoggerFactory.getLogger(DeiTask.class);

	@Autowired
	private IDeiService deiService;

	@Scheduled(fixedRateString = "${task.deiTask}")
	public void consultarServicioDei() {
		try {
			//deiService.cargarCirculacionDeVehiculos();
		} catch (Exception e) {
			LOGGER.error(PropertiesManager.obtenerCadena(Recursos.MENSAJES, "deiTask.errorEjecutandoTarea"), e);
			throw new Area247Exception(
					PropertiesManager.obtenerCadena(Recursos.MENSAJES, "deiTask.errorEjecutandoTarea"), e);
		}
	}

}
