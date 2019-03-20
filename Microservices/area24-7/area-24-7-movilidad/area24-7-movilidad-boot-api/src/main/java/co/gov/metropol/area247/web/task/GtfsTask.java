package co.gov.metropol.area247.web.task;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.repository.domain.TipoParametro;
import co.gov.metropol.area247.service.IGtfsEscrituraService;
import co.gov.metropol.area247.service.IShellScriptService;
import co.gov.metropol.area247.util.PropertiesManager;
import co.gov.metropol.area247.util.constantes.Constantes.Recursos;

@Component
public class GtfsTask {

	private static final  Logger LOGGER = LoggerFactory.getLogger(GtfsTask.class);
	
	@Autowired
	@Qualifier("gtfsGeneralEscrituraService")
	private IGtfsEscrituraService gtfsGeneralEscrituraService;

	@Autowired
	@Qualifier("shActualizaOTPService")
	private IShellScriptService shActualizaOTPService;

	/**
	 * Tarea automatica que crea el archivo GTFS con la informacion del Metro y
	 * GTPC. La ubicacion donde se creara este archivo se configura en
	 * {@link TipoParametro} de id = 4. Lo recomendable es que esta ruta -NO sea
	 * la de OpenTripPlanner, cuando se desee actualizar OPT con el nuevo GTFS
	 * lo recomendable copie este archivo al OPT y se reinicie el servidor.
	 */
	@Scheduled(cron = "${task.gtfsTask}")
	public void crearArchivoGtfs() {
		try {
			gtfsGeneralEscrituraService.escribirGTFS();
		} catch (Exception e) {
			LoggingUtil.logException(
					PropertiesManager.obtenerCadena(Recursos.MENSAJES, "gtfsTask.errorEjecutandoTarea"), e);
		}
	}

	/**
	 * Tarea automatica que ejecuta un archivo .sh que detiene el servicio OTP,
	 * mueve archivo GTFS a la carpeta donde se encuentra la carpeta del OTP y
	 * correr nuevamente el servicio OTP.
	 * <P>
	 * Creado 19/11/2018 14:00 p.m
	 */
	@Scheduled(cron = "${task.actualizaOTPTask}")
	public void actualizarOTP() {
		try {
			File file = shActualizaOTPService.crearShellScript();
			Runtime rt = Runtime.getRuntime();
			String commands = "sh " + file.getAbsolutePath();
			rt.exec(commands);
		} catch (Exception e) {
			LOGGER.error(
					PropertiesManager.obtenerCadena(Recursos.MENSAJES, "gtfsTask.errorEjecutandoTareaScript"), e);
		}
	}
}
