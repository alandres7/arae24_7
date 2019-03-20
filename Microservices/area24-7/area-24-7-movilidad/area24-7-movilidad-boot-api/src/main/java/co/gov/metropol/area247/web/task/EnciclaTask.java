package co.gov.metropol.area247.web.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.service.IEnciclaService;
import co.gov.metropol.area247.service.IGbfsEscrituraService;
import co.gov.metropol.area247.util.PropertiesManager;
import co.gov.metropol.area247.util.constantes.Constantes.Recursos;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Component
public class EnciclaTask {

	private static final  Logger LOGGER = LoggerFactory.getLogger(EnciclaTask.class);

	@Autowired
	private IEnciclaService enciclaService;
	
	@Autowired
	@Qualifier("gbfsEnciclaEscrituraService")
	private IGbfsEscrituraService gbfsEnciclaEscrituraService;

	@Scheduled(fixedRateString = "${task.timeEnciclaTask}")
	public void consultarServicioEncicla() {
		try {
			enciclaService.cargarEstacionesEncicla();
			enciclaService.cargarCicloRutas();
			gbfsEnciclaEscrituraService.escribirGBFS();
		} catch (Exception e) {
			LOGGER.error(PropertiesManager.obtenerCadena(Recursos.MENSAJES, "enciclaTask.errorEjecutandoTarea"), e.getMessage());
		}
	}


}
