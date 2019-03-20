package co.gov.metropol.area247.gateway.impl;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import co.gov.metropol.area247.annotations.LogReturnValue;
import co.gov.metropol.area247.gateway.ICivicaServiceGateway;
import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.repository.domain.LogEvento;
import co.gov.metropol.area247.repository.domain.support.enums.EstadoRespuestaWS;
import co.gov.metropol.area247.repository.domain.support.enums.SistemaTransporte;
import co.gov.metropol.area247.service.ILogEventoService;
import co.gov.metropol.area247.services.rest.metro.FeatureCivicaExpedicionWSDTO;
import co.gov.metropol.area247.util.PropertiesManager;
import co.gov.metropol.area247.util.constantes.Constantes;
import co.gov.metropol.area247.util.constantes.Constantes.Recursos;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class CivicaServiceGatewayImp implements ICivicaServiceGateway {

	@Autowired
	private ILogEventoService logEventoService;
	
	@Override
	@LogReturnValue
	public List<FeatureCivicaExpedicionWSDTO> consultarCivicaExpedicion() {
		
		String resultadoEvento = EstadoRespuestaWS.EXITOSO.getMsg();
		String error = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.TEXT_PLAIN_VALUE);

		String urlRecurso = PropertiesManager.obtenerCadena(Constantes.Recursos.ROUTE_SERVICES,
				"bootApi.gateway.routePuntosRecargaExpedicionCivica");

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlRecurso).queryParam("where", "0=0")
				.queryParam("geometryType", "esriGeometryMultipoint")
				.queryParam("spatialRel", "esriSpatialRelIntersects")
				.queryParam("outFields", "FID,Nombre_del,Dirección,Municipio,Tipo_de_pu,HORARIO")
				.queryParam("returnGeometry", true).queryParam("returnTrueCurves", false).queryParam("outSR", 4326)
				.queryParam("returnIdsOnly", false).queryParam("returnCountOnly", false).queryParam("returnZ", false)
				.queryParam("returnM", false).queryParam("returnDistinctValues", false).queryParam("f", "pjson");

		HttpEntity<?> entity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> response = null;

		try {
			response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String.class);

			JSONObject featuresJson = new JSONObject(response.getBody().toString());
			Gson gson = new Gson();
			return Arrays.asList(
					gson.fromJson(featuresJson.get("features").toString(), FeatureCivicaExpedicionWSDTO[].class));

		} catch (Exception e) {
			resultadoEvento = EstadoRespuestaWS.ERROR_TECNICO.getMsg();
			error = e.getMessage();
			final String msg = PropertiesManager.obtenerCadena(Recursos.MENSAJES, "metroTask.error.servicio");
			LoggingUtil.logException(msg, e);
			throw new Area247Exception(msg);
		} finally {
			String url = null != urlRecurso && !urlRecurso.isEmpty() && urlRecurso.length() > 50
					? urlRecurso.substring(0, 50) : urlRecurso;
			LogEvento logEvento = new LogEvento(SistemaTransporte.METRO.name(), Constantes.MOVILIDAD, url,
					resultadoEvento, error);
			logEventoService.saveLogEvento(logEvento);
		}
	}

}
