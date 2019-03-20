package co.gov.metropol.area247.core.gateway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.GenericType;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.socrata.api.Soda2Consumer;
import com.socrata.builders.SoqlQueryBuilder;
import com.socrata.exceptions.SodaError;
import com.socrata.model.soql.ConditionalExpression;
import com.socrata.model.soql.SoqlQuery;

import co.gov.metropol.area247.core.gateway.conversor.dto.Geometry;
import co.gov.metropol.area247.core.gateway.conversor.dto.RequestGeometriesContainer;
import co.gov.metropol.area247.core.gateway.geo.dto.Feature;
import co.gov.metropol.area247.core.gateway.posconsumo.dto.PosconsumoPoint;
import co.gov.metropol.area247.core.gateway.siata.dto.IStationsData;
import co.gov.metropol.area247.core.gateway.siata.dto.StationAgua;
import co.gov.metropol.area247.core.gateway.siata.dto.StationsDataAgua;
import co.gov.metropol.area247.core.ordenamiento.dto.CoordenadaDto;

@Component
public class MarkersProvider {
	
	private static final String PARAM_WHERE = "where";
	private static final String VAL_WHERE = "1=1";
	private static final String PARAM_SR = "outSR";
	private static final int VALUE_SR = 4218;
	private static final String PARAM_OUT = "f";
	private static final String VALUE_OUT = "pjson";
	private static final String PARAM_FIELDS = "outFields";
	private static final String VALUE_FIELDS_EQUIPAMIENTOS = "OBJECTID,NOMBRE,CLASIFICAC,TIPO,MUNICIPIO";
	private static final String PARAM_IN_SR = "inSR";
	private static final String PARAM_OUT_SR = "outSR";
	private static final String PARAM_GEOMETRIES = "geometries";
	private static final String KEY_GEOMETRY_TYPE = "geometryType";
	private static final String KEY_GEOMETRIES = "geometries";
	private static final String LBL_NOMBRE_EQUIPAMIENTO = "NOMBRE";
	private static final String URL_COMPLEMENTO = "/query";
    private static final String URL_EQUIPAMIENTOS = "http://geografico.metropol.gov.co:6080/arcgis/rest/services/App24-7/EQUIPAMIENTOS/MapServer/1/query";
	
	private Gson conversorJson;
	
	private UriComponentsBuilder builder;
	
	public Feature getMarkerEquipamientos(String nombre) {
		List<Feature> features;
		try {
			features = getFeatures(URL_EQUIPAMIENTOS,VALUE_FIELDS_EQUIPAMIENTOS);
		} catch (Exception e) {
			features = new ArrayList<>();
		}
		return features.stream()
		.filter(feature->
		nombre.equals(feature.getAttributes().get(LBL_NOMBRE_EQUIPAMIENTO).getAsString()))
		.findAny()
		.orElse(new Feature());
	}
	
	
	public List<CoordenadaDto> guardarCoordenadas(Feature feature){
		List<CoordenadaDto> coordenadasModel = new ArrayList<>();
		List<double[]> ring = feature.getGeometry().getRings().get(0);
		ring.forEach(parCoordenada->{
				CoordenadaDto coordenadaModel = new CoordenadaDto();
				coordenadaModel.setLatitud(parCoordenada[0]);
				coordenadaModel.setLongitud(parCoordenada[1]);
				coordenadasModel.add(coordenadaModel);
			});
		return coordenadasModel;
	}
	
	public List<Feature> getFeatures(String url, String fields)throws Exception{
		getResponseFeatures(url, fields);
		JSONObject featuresJson = getResponse();
		conversorJson = new Gson();
		return Arrays.asList(conversorJson.fromJson(featuresJson.get("features").toString(), Feature[].class));
	}
	
	public List<Geometry> getGeometries(String url, RequestGeometriesContainer request) throws Exception{
		getResponseGeometries(url, request);
		 conversorJson = new Gson();
		 JSONObject responseGeometries = getResponse();
		return Arrays.asList(conversorJson.fromJson(responseGeometries.get("geometries").toString(), Geometry[].class));
	}
	
	public List<String> getCategoriasCapaProviders(String url) throws Exception{
		List<String> nombresCategoria = new ArrayList<>();
		getResponseFeatures(url, null);
		JSONObject infoCapa = getResponse();
		JSONObject drawingInfo =  infoCapa.getJSONObject("drawingInfo");
		JSONObject renderer = drawingInfo.getJSONObject("renderer");
		JSONArray uniqueValueInfos = renderer.getJSONArray("uniqueValueInfos");
		if(uniqueValueInfos.length() > 0) {
			for (int i = 0; i < uniqueValueInfos.length(); i++) {
				String nombreCategoria = uniqueValueInfos.getJSONObject(i).getString("value");
				if(!"".equals(nombreCategoria.trim())) {
					nombresCategoria.add(nombreCategoria);
				}
			}
		}
		return nombresCategoria;
	}
        
        
	
	private void getResponseFeatures(String url, String fields) throws Exception {
		StringBuilder urlFinal = new StringBuilder(url);
		if (!url.contains(URL_COMPLEMENTO) && fields != null) {
			urlFinal.append(URL_COMPLEMENTO);
		}
		builder = UriComponentsBuilder.fromHttpUrl(urlFinal.toString());
		if (fields != null) {
			builder.queryParam(PARAM_WHERE, VAL_WHERE).queryParam(PARAM_SR, VALUE_SR).queryParam(PARAM_FIELDS, fields);
		}
		builder.queryParam(PARAM_OUT, VALUE_OUT);
	}
	
	private void getResponseGeometries(String url, RequestGeometriesContainer request) throws Exception{
		builder = UriComponentsBuilder.fromHttpUrl(url);
		if(request != null) {
			JSONObject geometriesContainer = new JSONObject();
			geometriesContainer.put(KEY_GEOMETRY_TYPE, request.getGeometriesContainer().getGeometryType());
			geometriesContainer.put(KEY_GEOMETRIES, request.getGeometriesContainer().getGeometries());
			builder.queryParam(PARAM_IN_SR, request.getInSR()).queryParam(PARAM_OUT_SR, request.getOutSR())
					.queryParam(PARAM_GEOMETRIES, geometriesContainer);
		}
		builder.queryParam(PARAM_OUT, VALUE_OUT);
	}
	
	
	private JSONObject getResponse() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.TEXT_PLAIN_VALUE);
		HttpEntity<?> requestHeaders = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, requestHeaders,
				String.class);
		JSONObject jsonResponse = new JSONObject(response.getBody());
		return jsonResponse;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<?> getStationsDataDirect(String url, Object  stationsDataModel) throws Exception{		
		HttpEntity<IStationsData> stationsDataResponse = (HttpEntity<IStationsData>)  exchangeData(url, stationsDataModel);
		IStationsData<?> stationsData = stationsDataResponse.getBody();
		return stationsData.getDatos();

	}
	
	
	
	private ResponseEntity<? extends Object> exchangeData(String url, Object model){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> request = new HttpEntity<>(headers);
		return restTemplate.exchange(url, HttpMethod.GET, request, model.getClass());
	}
	
	@SuppressWarnings({"unchecked" })
	public List<StationAgua> getStationsAgua(String url, StationsDataAgua  stationsDataModel) throws Exception{
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> request = new HttpEntity<>(headers);
		
		HttpEntity<StationsDataAgua> stationsDataResponse = 
				(HttpEntity<StationsDataAgua>) restTemplate.exchange(url, HttpMethod.GET, request, stationsDataModel.getClass());
		StationsDataAgua stationsDataAgua = stationsDataResponse.getBody();
		return stationsDataAgua.getDatos();

	}
        
        public List<PosconsumoPoint> getPayload(String url) throws SodaError, InterruptedException{
        	SoqlQuery query = new SoqlQueryBuilder()
                    .setWhereClause(new ConditionalExpression("Ciudad IN ('Barbosa','Bello','Caldas','Copacabana','Envigado','Girardota','Itagüi','La Estrella','Medellín','Sabaneta') AND departamento='Antioquia'"))
                    .build();
        	Soda2Consumer consumer = Soda2Consumer.newConsumer(url);
        	return consumer.query("niud-weuj", query, new GenericType<List<PosconsumoPoint>>() {});
        }
        
        
	
}
