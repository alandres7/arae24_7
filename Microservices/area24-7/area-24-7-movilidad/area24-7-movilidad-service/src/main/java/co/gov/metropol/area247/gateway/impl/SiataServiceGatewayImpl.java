package co.gov.metropol.area247.gateway.impl;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import co.gov.metropol.area247.gateway.ISiataServiceGateway;
import co.gov.metropol.area247.services.rest.siata.SiataWSDTO;
import co.gov.metropol.area247.util.PropertiesManager;
import co.gov.metropol.area247.util.ServiceUtils;
import co.gov.metropol.area247.util.constantes.Constantes;
import co.gov.metropol.area247.util.ex.Area247Exception;
import co.gov.metropol.area247.util.web.Coordenada;

@Service
public class SiataServiceGatewayImpl implements ISiataServiceGateway {

	private static final Logger LOGGER = LoggerFactory.getLogger(SiataServiceGatewayImpl.class);

	public SiataWSDTO consultarPronostico(Date fecha, Date fechaOrigen, Coordenada coordenadaOrigen, Date fechaDestino,
			Coordenada coordenadaDestino) {
		try {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			RestTemplate restTemplate = new RestTemplate();
			
			String response = restTemplate.getForObject(PropertiesManager
					.obtenerCadena(Constantes.Recursos.ROUTE_SERVICES, "bootApi.gateway.routeSiataService")
					+ "9d167dd2734426ade156c24f3ccd42a569a5f1d8/" + dateFormat.format(fecha) + "/"
					+ dateFormat.format(fechaOrigen) + "/" + coordenadaOrigen.getLongitud() + "/"
					+ coordenadaOrigen.getLatitud() + "/" + dateFormat.format(fechaDestino) + "/"
					+ coordenadaDestino.getLongitud() + "/" + coordenadaDestino.getLatitud(), String.class);
			
			String value=new String(response.getBytes("ISO-8859-1"),"UTF-8");
			
			return (SiataWSDTO) ServiceUtils.stringResponseToObject(value, SiataWSDTO.class);
		} catch (Exception e) {
			LOGGER.error("Error al consumir el servicio de siata para consultar el pronostico del tiempo", e);
			throw new Area247Exception("Error al consumir el servicio de siata para consultar el pronostico del tiempo",
					e);
		}
	}
}
