package co.gov.metropol.area247.gateway.impl;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

import co.gov.metropol.area247.gateway.IGtpcServiceGateway;
import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.repository.domain.LogEvento;
import co.gov.metropol.area247.repository.domain.support.enums.EstadoRespuestaWS;
import co.gov.metropol.area247.repository.domain.support.enums.SistemaTransporte;
import co.gov.metropol.area247.service.ILogEventoService;
import co.gov.metropol.area247.service.ITipoParametroService;
import co.gov.metropol.area247.services.rest.gtpc.RutaGtpcWSDTO;
import co.gov.metropol.area247.services.rest.gtpc.ViajesGtpcWSDTO;
import co.gov.metropol.area247.util.PropertiesManager;
import co.gov.metropol.area247.util.constantes.Constantes;
import co.gov.metropol.area247.util.constantes.Constantes.TipoParametro;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class GtpcServiceGatewayImpl implements IGtpcServiceGateway {

	private static final Logger LOGGER = LoggerFactory.getLogger(GtpcServiceGatewayImpl.class);

	@Autowired
	private ILogEventoService logEventoService;

	@Autowired
	private ITipoParametroService tipoParametroService;

	@Override
	public List<RutaGtpcWSDTO> cargarInformacionRutas() {

		String url = PropertiesManager.obtenerCadena(Constantes.Recursos.ROUTE_SERVICES,
				"bootApi.gateway.routeGtpcService");
		String resultadoEvento = EstadoRespuestaWS.EXITOSO.getMsg();
		String error = null;

		try {
			
			String secretariasHabilitadas = tipoParametroService
					.obtenerValorCadena(TipoParametro.SECRETARIAS_HABILITADAS);
			RestTemplate restTemplate = new RestTemplate();
			String response = restTemplate.getForObject(url.concat(secretariasHabilitadas), String.class);

			JSONObject featuresJson;

			featuresJson = new JSONObject(response);
			Gson gson = new Gson();
			return Arrays.asList(gson.fromJson(featuresJson.get("rutas").toString(), RutaGtpcWSDTO[].class));

		} catch (Exception e) {
			error = e.getMessage();
			resultadoEvento = EstadoRespuestaWS.ERROR_TECNICO.getMsg();
			final String msg = "Error al consumir el servicio de gtpc validando su estado";
			LoggingUtil.logException(msg, e);
			throw new Area247Exception(msg, e);

		} finally {

			String urlAux = url;

			if (url.length() >= 50)
				urlAux = url.substring(0, 50);

			LogEvento logEvento = new LogEvento(SistemaTransporte.GTPC.name(), Constantes.MOVILIDAD, urlAux,
					resultadoEvento, error);
			logEventoService.saveLogEvento(logEvento);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.gov.metropol.area247.gateway.IGtpcServiceGateway#
	 * cargarInformacionViajesRuta()
	 */
	@Override
	public List<ViajesGtpcWSDTO> cargarInformacionViajesRuta(String fecha) {

		try {

			String url = PropertiesManager.obtenerCadena(Constantes.Recursos.ROUTE_SERVICES,
					"bootApi.gateway.routeGtpcTripsService");

			String secretariasHabilitadas = tipoParametroService
					.obtenerValorCadena(TipoParametro.SECRETARIAS_HABILITADAS);
			
			Map<String, String> variables = new HashMap<>(1);
			variables.put("fecha", fecha);

			RestTemplate accesoHttp = new RestTemplate();
			accesoHttp.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
			String response = accesoHttp.getForObject(url.concat(secretariasHabilitadas), String.class, variables);

			JSONObject featuresJson;
			featuresJson = new JSONObject(response);
			Gson gson = new Gson();

			return Arrays.asList(gson.fromJson(featuresJson.get("trips").toString(), ViajesGtpcWSDTO[].class));

		} catch (Exception e) {
			LOGGER.error("Error al consumir la informacion del servicio web de viajes de GTPC : ", e.getMessage());
			return Lists.newArrayList();
		}

	}
}
