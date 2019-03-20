package co.gov.metropol.area247.gateway.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import co.gov.metropol.area247.gateway.IMetroServiceGateway;
import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.repository.domain.LogEvento;
import co.gov.metropol.area247.repository.domain.support.enums.EstadoRespuestaWS;
import co.gov.metropol.area247.repository.domain.support.enums.SistemaTransporte;
import co.gov.metropol.area247.service.ILogEventoService;
import co.gov.metropol.area247.services.rest.metro.MetroWSDTO;
import co.gov.metropol.area247.util.PropertiesManager;
import co.gov.metropol.area247.util.ServiceUtils;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.constantes.Constantes;
import co.gov.metropol.area247.util.constantes.Constantes.Recursos;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class MetroServiceGatewayImpl implements IMetroServiceGateway {

	@Autowired
	private ILogEventoService logEventoService;

	@Override
	public MetroWSDTO consultarDatosMetro() {

		String urlWSMetro = PropertiesManager.obtenerCadena(Constantes.Recursos.ROUTE_SERVICES,
				"bootApi.gateway.routeMetroService");
		String resultadoEvento = EstadoRespuestaWS.EXITOSO.getMsg();
		String error = null;

		try {
			RestTemplate restTemplate = new RestTemplate();
			String response = restTemplate.getForObject(urlWSMetro, String.class);
			MetroWSDTO metroWSDTO = (MetroWSDTO) ServiceUtils.stringResponseToObject(response, MetroWSDTO.class);

			if (Utils.isNull(metroWSDTO)) {

				final String msg = PropertiesManager.obtenerCadena(Recursos.MENSAJES, "metroTask.error.servicio");
				LoggingUtil.logInfo(msg);
				throw new Area247Exception(msg);

			} else {
				final int respuesta = metroWSDTO.getCodigo();

				if (respuesta == Constantes.Metro.ERROR_TECNICO_DESCIFRADO) {
					resultadoEvento = EstadoRespuestaWS.ERROR_TECNICO.getMsg();
					error = PropertiesManager.obtenerCadena(Recursos.MENSAJES, "metroTask.error.tecnico");
					throw new Area247Exception(error);
				} else if (respuesta == Constantes.Metro.ERROR_AUTENTICACION) {
					resultadoEvento = EstadoRespuestaWS.AUTENTICACION_FALLIDA.getMsg();
					error = PropertiesManager.obtenerCadena(Recursos.MENSAJES, "metroTask.error.autenticacion");
					throw new Area247Exception(error);
				} else if (respuesta == Constantes.Metro.ERROR_DATOS_INCONSISTENTES) {
					resultadoEvento = EstadoRespuestaWS.ERROR_DATOS.getMsg();
					error = PropertiesManager.obtenerCadena(Recursos.MENSAJES, "metroTask.error.datos.inconsistentes");
					throw new Area247Exception(error);
				} else {
					return metroWSDTO;
				}
			}

		} catch (Exception e) {
			resultadoEvento = EstadoRespuestaWS.ERROR_TECNICO.getMsg();
			error = e.getMessage();
			final String msg = PropertiesManager.obtenerCadena(Recursos.MENSAJES, "metroTask.error.servicio");
			LoggingUtil.logException(msg, e);
			throw new Area247Exception(msg);
		} finally {
			LogEvento logEvento = new LogEvento(SistemaTransporte.METRO.name(), Constantes.MOVILIDAD, urlWSMetro,
					resultadoEvento, error);
			logEventoService.saveLogEvento(logEvento);
		}
	}

}
