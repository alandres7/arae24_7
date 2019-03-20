package co.gov.metropol.area247.web.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.service.IGtpcService;
import co.gov.metropol.area247.util.PropertiesManager;
import co.gov.metropol.area247.util.constantes.Constantes.Recursos;

@Component
public class GtpcTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(GtpcTask.class);

	@Autowired
	private IGtpcService gtpcService;

	@Scheduled(fixedRateString = "${task.gtpcTask}")
	public void recibirInformacionGtpc() {
		try {
			gtpcService.cargarInformacionDeRutas();
		} catch (Exception e) {
			LOGGER.error(PropertiesManager.obtenerCadena(Recursos.MENSAJES, "gtpcTask.errorEjecutandoTarea"), e);
		}
	}

}
