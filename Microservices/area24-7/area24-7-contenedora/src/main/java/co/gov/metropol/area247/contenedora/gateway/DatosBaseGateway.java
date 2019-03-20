package co.gov.metropol.area247.contenedora.gateway;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import co.gov.metropol.area247.contenedora.model.Categoria;
import co.gov.metropol.area247.contenedora.model.Subcategoria;
import co.gov.metropol.area247.contenedora.model.dto.DrawingInfoDto;
import co.gov.metropol.area247.contenedora.model.enums.TiposRecurso;
import co.gov.metropol.area247.contenedora.service.IContenedoraCapaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraCategoriaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraSubcategoriaService;
import co.gov.metropol.area247.core.gateway.geo.dto.Feature;

@Component
public class DatosBaseGateway {

	@Autowired
	IContenedoraCapaService capaService;

	@Autowired
	IContenedoraCategoriaService categoriaService;

	@Autowired
	IContenedoraSubcategoriaService subcategoriaService;
	
	

	public List<Feature> obtenerInformacionCapa(Long tipoRecurso, Long idrecurso, Categoria categoria,
			Subcategoria subCartegoria) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.TEXT_PLAIN_VALUE);

		String urlRecurso = null;

		for (TiposRecurso tiposRecursoIterador : TiposRecurso.values()) {
			if (tiposRecursoIterador.getValor() == tipoRecurso) {
				if (tiposRecursoIterador.equals(TiposRecurso.CATEGORIA)) {
					urlRecurso = categoriaService.categoriaObtenerPorId(idrecurso).getUrlRecurso();
				} else {
					if (tiposRecursoIterador.equals(TiposRecurso.SUBCATEGORIA)) {
						urlRecurso = subcategoriaService.subcategoriaObtenerPorId(idrecurso).getUrlRecurso();
					}
				}
			}
		}

		String urlComplento = "/query";
		StringBuilder stringOutoFields = new StringBuilder();
		String aliasNombre, aliasNombreItem, aliasDescripcionItem, aliasId;

		if (categoria != null) {
			aliasNombre = categoria.getAliasNombre();
			aliasNombreItem = ""; 
			//categoria.getAliasNombreItem();
			aliasDescripcionItem = "";
			//categoria.getAliasDescripcionItem();
			aliasId = categoria.getAliasTipo();
		} else {
			aliasNombre = subCartegoria.getAliasNombre();
			aliasNombreItem = subCartegoria.getAliasNombreItem();
			aliasDescripcionItem = subCartegoria.getAliasDescripcionItem();
			aliasId = subCartegoria.getAliasId();
		}

		if (!aliasNombre.isEmpty() || aliasNombre != null) {
			if(!aliasNombre.contentEquals(stringOutoFields)) {
				stringOutoFields.append(aliasNombre);
			}
			
		}

		if (!aliasNombreItem.isEmpty() || aliasNombreItem != null) {
			if (stringOutoFields.length() > 1) {
				if(!aliasNombreItem.contentEquals(stringOutoFields)) {
					stringOutoFields.append(", " + aliasNombreItem);
				}
				
			} else {
				stringOutoFields.append(aliasNombreItem);
			}
		}

		if (!aliasDescripcionItem.isEmpty() || aliasDescripcionItem != null) {
			if (stringOutoFields.length() > 1) {
				if(!aliasDescripcionItem.contentEquals(stringOutoFields)) {
					stringOutoFields.append(", " + aliasDescripcionItem);
				}
				
			} else {
				stringOutoFields.append(aliasDescripcionItem);
			}
		}

		try {
			if (!aliasId.isEmpty() || aliasId != null) {
				if (stringOutoFields.length() > 1) {
					if(!aliasId.contentEquals(stringOutoFields)) {
						stringOutoFields.append(", " + aliasId);
					}
					
				} else {
					stringOutoFields.append(aliasId);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlRecurso + urlComplento)
				.queryParam("where", "1=1").queryParam("outSR", 4218)
				//.queryParam("returnGeometry", true)
				//.queryParam("returnTrueCurves", true)
				.queryParam("outFields", stringOutoFields.toString()).queryParam("f", "pjson");
		

		HttpEntity<?> entity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> response = null;

		try {

			response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String.class);

			JSONObject featuresJson = new JSONObject(response.getBody());
			Gson gson = new Gson();
			return Arrays.asList(gson.fromJson(featuresJson.get("features").toString(), Feature[].class));

		} catch (Exception e) {
			System.out.print("Error: " + e);
		}
		return null;
	}
	
	

	public DrawingInfoDto obtenerInformacionCategoria(String url) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.TEXT_PLAIN_VALUE);

		String complemento = "?f=pjson";
		UriComponentsBuilder builder;
		HttpEntity<?> entity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> response = null;

		// primero ejecuta la url para obtener el color de los marcadores
		builder = UriComponentsBuilder.fromHttpUrl(url + complemento);
		try {

			response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String.class);

			JSONObject drawingInfoJson = new JSONObject(response.getBody());
			Gson gson = new Gson();
			System.out.print("objeto mapeado ---> " + drawingInfoJson.get("drawingInfo").toString());
			return gson.fromJson(drawingInfoJson.get("drawingInfo").toString(), DrawingInfoDto.class);

		} catch (Exception e) {
			System.out.print("Error: " + e);
		}

		return null;
	}
	
	public JSONObject obtenerInformacionSubLayers(String url) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.TEXT_PLAIN_VALUE);

		String complemento = "?f=pjson";
		UriComponentsBuilder builder;
		HttpEntity<?> entity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> response = null;

		// primero ejecuta la url para obtener el color de los marcadores
		builder = UriComponentsBuilder.fromHttpUrl(url + complemento);
		JSONObject jsonObjectServicio = null;
		try {

			
			response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String.class);

			jsonObjectServicio = new JSONObject(response.getBody());
			System.out.print("Elemento subcategoria obtenido ---> " + jsonObjectServicio.get("subLayers").toString());

		} catch (Exception e) {
			System.out.print("Error: " + e);
		}

		return jsonObjectServicio;
	}
	
	

}
