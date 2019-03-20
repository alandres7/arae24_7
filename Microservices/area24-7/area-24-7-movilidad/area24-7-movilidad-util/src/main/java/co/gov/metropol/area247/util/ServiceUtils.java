package co.gov.metropol.area247.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.gov.metropol.area247.util.ex.Area247Exception;

public class ServiceUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceUtils.class);
	
	public static Object stringResponseToObject(String response, Class<?> objectClass){
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			return mapper.readValue(response, objectClass);
		} catch (Exception e) {
			LOGGER.error("Error al transformar la respuesta del servico al objeto", e);
			throw new Area247Exception("Error al transformar la respuesta del servico al objeto", e);
		}
		
	}

}
