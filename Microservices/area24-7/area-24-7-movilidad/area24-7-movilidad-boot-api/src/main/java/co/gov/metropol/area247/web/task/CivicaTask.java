package co.gov.metropol.area247.web.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.service.ICivicaService;
import co.gov.metropol.area247.util.PropertiesManager;
import co.gov.metropol.area247.util.constantes.Constantes.Recursos;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Component
@PropertySource("classpath:timeTask.properties")
public class CivicaTask {
	private static final  Logger LOGGER = LoggerFactory.getLogger(CivicaTask.class);

	@Autowired
	private ICivicaService civicaService;
	

//	@Scheduled(fixedRateString = "${task.timeCivicaTask}")
//	public void consultarServicioCivica() {
//		try {
//			civicaService.cargarPutosCivicaExpedicion();
//		} catch (Exception e) {
//			LOGGER.error(PropertiesManager.obtenerCadena(Recursos.MENSAJES, "civicaTask.errorEjecutandoTarea"), e);
//			throw new Area247Exception(
//					PropertiesManager.obtenerCadena(Recursos.MENSAJES, "civicaTask.errorEjecutandoTarea"), e);
//		}
//	}
}
