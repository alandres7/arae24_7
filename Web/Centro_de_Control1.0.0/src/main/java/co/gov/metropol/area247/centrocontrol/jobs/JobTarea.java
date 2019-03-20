package co.gov.metropol.area247.centrocontrol.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import co.gov.metropol.area247.centrocontrol.model.RespuestaLogin;
import co.gov.metropol.area247.centrocontrol.model.UsuarioJSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;


@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class JobTarea implements org.quartz.Job {
	
	@Autowired
	private Environment env;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(JobTarea.class); 
	
	public JobTarea() {}
    
    public void execute(JobExecutionContext context) throws JobExecutionException {   	
    	JobDataMap dataMap = context.getJobDetail().getJobDataMap();
    	
    	String idRecurso = dataMap.getString("idRecurso");
    	String tipoRecurso = dataMap.getString("tipoRecurso");
    	String idAplicacion = dataMap.getString("idAplicacion");
    	String serverEndpoint = dataMap.getString("serverEndpoint");
    	String log = "";
    	
    	
    	if(tipoRecurso.equals("CAPA")) {
    		log = "Se empieza a ejecutar la tarea para actualizar los marcadores de la Capa (" + idRecurso + ") a las " + LocalDateTime.now();    		
    	}else {
    		log = "Se empieza a ejecutar la tarea para actualizar los marcadores de la Categoria (" + idRecurso + ") a las " + LocalDateTime.now();	
    	}
    	System.out.println(log);	
		LOGGER.info(log);
		
    	
    	String url;	
    	if(idAplicacion.equals("4")) {
			// Actualizadores de Ordenamiento
		    if(tipoRecurso.equals("CAPA")){
		        url = serverEndpoint + "/api/contenedora/load-markers-capa?idCapa=" + idRecurso;
		    }else {
			    url = serverEndpoint + "/api/contenedora/load-markers-categoria?idCategoria=" + idRecurso;
		    }
		}else {
			if(idAplicacion.equals("5")){
			    // Actualizadores de Mi Entorno
			    url = serverEndpoint + "/api/entorno/estacion/update?idCapa=" + idRecurso;
			}else {
				if(idAplicacion.equals("6")){
				    // Actualizadores de Huellas
				    //url = serverEndpoint + "/api/entorno/estacion/update?idCapa=" + idRecurso;
					url = "";
				}else {
					url = "";
				}
			}
		}
    	   	
    	RestTemplate restTemplate = new RestTemplate();    	      	
    	String tokenAcceso = null;
    	   	
    	UsuarioJSON usuario = new UsuarioJSON();
		usuario.setUsername(env.getProperty("area247.username"));
		usuario.setContrasena(env.getProperty("area247.password"));
		usuario.setNombreFuenteRegistro(env.getProperty("area247.fuente.registro"));
		
		String usuarioResourceUrl = serverEndpoint + "/api/login";
		try {
			HttpEntity<RespuestaLogin> object = restTemplate.postForEntity(usuarioResourceUrl,usuario,RespuestaLogin.class);
			tokenAcceso = object.getBody().getToken();
		} catch (Exception ex) {
			tokenAcceso = null;
		}
    	
    	
		if(tokenAcceso!=null) {								
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		    headers.set("Authorization",tokenAcceso);
            
		    HttpEntity<LinkedMultiValueMap<String,Object>> requestEntity 
		        = new HttpEntity<LinkedMultiValueMap<String,Object>>(headers);
		    HttpEntity<Boolean> respuesta = null;
		    
		    try {
		        respuesta = restTemplate.exchange(url,HttpMethod.PUT,requestEntity,Boolean.class);
		    } catch (Exception ex) {
		        LOGGER.error("Error durante la ejecución de la tarea: "+ex.getMessage());
		    }
		    if(respuesta.getBody()) {			
		    	if(tipoRecurso.equals("CAPA")) {
		    		log = "Se actualizaron los marcadores para la Capa (" + idRecurso + ") a las " + LocalDateTime.now();    		
		    	}else {
		    		log = "Se actualizaron los marcadores para la Categoria (" + idRecurso + ") a las " + LocalDateTime.now();	
		    	}
		    }else {
		    	if(tipoRecurso.equals("CAPA")) {
		    		log = "No se pudieron actualizar los marcadores para la Capa (" + idRecurso + ") a las " + LocalDateTime.now();    		
		    	}else {
		    		log = "No se pudieron actualizar los marcadores para la Categoria (" + idRecurso + ") a las " + LocalDateTime.now();	
		    	}
		    }
		}else {
			if(tipoRecurso.equals("CAPA")) {
	    		log = "No se pudieron actualizar los marcadores para la Capa (" + idRecurso + ") a las " + LocalDateTime.now();    		
	    	}else {
	    		log = "No se pudieron actualizar los marcadores para la Categoria (" + idRecurso + ") a las " + LocalDateTime.now();	
	    	}
	    }
		
		System.out.println(log);	
		LOGGER.info(log);
		
    }
}