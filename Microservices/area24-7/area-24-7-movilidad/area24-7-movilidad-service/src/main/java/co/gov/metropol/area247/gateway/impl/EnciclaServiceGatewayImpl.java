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
import co.gov.metropol.area247.gateway.IEnciclaServiceGateway;
import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.repository.domain.LogEvento;
import co.gov.metropol.area247.repository.domain.support.enums.EstadoRespuestaWS;
import co.gov.metropol.area247.repository.domain.support.enums.SistemaTransporte;
import co.gov.metropol.area247.service.ILogEventoService;
import co.gov.metropol.area247.services.rest.encicla.EnciclaWSDTO;
import co.gov.metropol.area247.services.rest.encicla.FeatureWSDTO;
import co.gov.metropol.area247.util.PropertiesManager;
import co.gov.metropol.area247.util.ServiceUtils;
import co.gov.metropol.area247.util.constantes.Constantes;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class EnciclaServiceGatewayImpl implements IEnciclaServiceGateway {
	
	@Autowired
	private ILogEventoService logEventoService;
	
	@Override
	@LogReturnValue
	public EnciclaWSDTO consultarEstatusEncicla(){
		
		final String uri = PropertiesManager.obtenerCadena(Constantes.Recursos.ROUTE_SERVICES,
				"bootApi.gateway.routeEnciclaStatusService");
		String resultadoEvento = EstadoRespuestaWS.EXITOSO.getMsg();
		String error = null;
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			String response = restTemplate.getForObject(uri, String.class);
			return (EnciclaWSDTO) ServiceUtils.stringResponseToObject(response, EnciclaWSDTO.class);
			
		} catch (Exception e) {
			error = e.getMessage();
			resultadoEvento = EstadoRespuestaWS.ERROR_TECNICO.getMsg();
			final String msg = "Error al consumir el servicio de encicla para validar el status";
			LoggingUtil.logException(msg, e);
			throw new Area247Exception(msg, e);
			
		} finally {
			LogEvento logEvento = new LogEvento(SistemaTransporte.GTPC.name(), Constantes.MOVILIDAD, uri, resultadoEvento,
					error);
			logEventoService.saveLogEvento(logEvento);
		}
	}

	@Override
	@LogReturnValue
	public List<FeatureWSDTO> consultarCicloRutas(){
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.TEXT_PLAIN_VALUE);

		String urlRecurso = PropertiesManager.obtenerCadena(Constantes.Recursos.ROUTE_SERVICES,
				"bootApi.gateway.routeEnciclaConsultarCicloRutas");
  
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlRecurso)
				.queryParam("where", "0=0")
				.queryParam("geometryType", "esriGeometryEnvelope")
				.queryParam("spatialRel", "esriSpatialRelIntersects")
				.queryParam("outFields", "OBJECTID,NAME,SHAPE_LENG,MUNICIPIO")
				.queryParam("returnGeometry", true)
				.queryParam("returnTrueCurves", false)
				.queryParam("outSR", 4326)
				.queryParam("returnIdsOnly", false)
				.queryParam("returnCountOnly", false)
				.queryParam("returnZ", false)
		        .queryParam("returnM", false)
		        .queryParam("returnDistinctValues", false)
		        .queryParam("f", "pjson");

		HttpEntity<?> entity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> response = null;
		
		try
		{
			
			response = restTemplate.exchange(
			        builder.build().encode().toUri(), 
			        HttpMethod.GET, 
			        entity, 
			        String.class);
			
			JSONObject featuresJson = new  JSONObject(response.getBody().toString());
			Gson gson = new Gson();
			return Arrays.asList(gson.fromJson(featuresJson.get("features").toString(), FeatureWSDTO[].class));

		}catch(Exception e){
			String msg = "Error al consumir el servicio para consultar las ciclorutas";
			LoggingUtil.logException(String.format("%s : [%s]", msg, e.getMessage()));
			throw new Area247Exception(msg);
		}
	}


}
