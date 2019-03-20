package co.gov.metropol.area247.centrocontrol.carga.gateway.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import co.gov.metropol.area247.centrocontrol.carga.gateway.ICirculacionVehiculoGateway;
import co.gov.metropol.area247.centrocontrol.carga.util.ServiceUtils;
import co.gov.metropol.area247.centrocontrol.dto.CirculacionVehiculoWSDTO;

@Service
public class CirculacionVehiculoGatewayImpl implements ICirculacionVehiculoGateway {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceUtils.class);
	
	@Value("${area247.carga.circulacion.url}")
	private String circulacionUrl;
	
	@Override
	public CirculacionVehiculoWSDTO cargarCiculacionVeiculos() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String uri = circulacionUrl;
			String response = restTemplate.getForObject(uri, String.class);
			return (CirculacionVehiculoWSDTO) ServiceUtils.stringResponseToObject(response, CirculacionVehiculoWSDTO.class);
			
		} catch (Exception e) {
			LOGGER.error("Error al consumir el servicio de circulacion", ""); 
			return null;
		}
	}



}
