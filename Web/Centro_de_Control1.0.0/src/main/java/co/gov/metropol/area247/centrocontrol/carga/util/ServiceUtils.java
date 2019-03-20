package co.gov.metropol.area247.centrocontrol.carga.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;


public class ServiceUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceUtils.class);
	
	public static Object stringResponseToObject(String response, Class<?> objectClass){
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(response, objectClass);
		} catch (Exception e) {
			LOGGER.error("Error al transformar la respuesta del servico al objeto", e);
			return null;
		}
		
	}

}
