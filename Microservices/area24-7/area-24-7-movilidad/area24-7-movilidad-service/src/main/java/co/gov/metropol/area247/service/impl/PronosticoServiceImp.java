package co.gov.metropol.area247.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.gateway.ISiataServiceGateway;
import co.gov.metropol.area247.model.PronosticoDTO;
import co.gov.metropol.area247.service.IPronosticoService;
import co.gov.metropol.area247.service.impl.abstracts.AbstractPronosticoService;
import co.gov.metropol.area247.util.ex.Area247Exception;
import co.gov.metropol.area247.util.web.Coordenada;

@Service
public class PronosticoServiceImp extends AbstractPronosticoService implements IPronosticoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PronosticoServiceImp.class);

	@Autowired
	private ISiataServiceGateway siataServiceGateway;

	@Override
	public PronosticoDTO obtenerPronostico(Date fecha, Date fechaOrigen, Coordenada coordenadaOrigen, Date fechaDestino,
			Coordenada coordenadaDestino) {
		try {
			PronosticoDTO pronostico = mapper.toPronosticoDTO(siataServiceGateway.consultarPronostico(fecha,
					fechaOrigen, coordenadaOrigen, fechaDestino, coordenadaDestino));

			return pronostico;
		} catch (Exception e) {
			LOGGER.error("Error al consumir el servicio del SIATA.", e);
			throw new Area247Exception("Error al consumir el servicio del SIATA.", e);
		}
	}

}
