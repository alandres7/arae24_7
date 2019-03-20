package co.gov.metropol.area247.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.model.PuntoTarjetaCivicaDTO;
import co.gov.metropol.area247.repository.domain.PuntoTarjetaCivica;
import co.gov.metropol.area247.service.IPuntoTarjetaCivicaService;
import co.gov.metropol.area247.service.impl.abstracts.AbstractPuntoTarjetaCivicaService;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class PuntoTarjetaCivicaServiceImp extends AbstractPuntoTarjetaCivicaService
		implements IPuntoTarjetaCivicaService {

	@Override
	public List<PuntoTarjetaCivicaDTO> obtenerEstacionesCercanas(Double latitud, Double longitud, Double radio) {
		try {
			List<PuntoTarjetaCivica> puntosTarjetasCivicas = tarjetaCivicaRepository.findByLocalizacion(latitud, longitud, radio);
			return mapper.mapPuntoTarjetaCivicaDTO(puntosTarjetasCivicas);
		} catch (Exception e) {
			LoggingUtil.logException(String.format(
					"Error al tratar de obtener los puntos de tarjeta civica para la latidud: %s, longitud: %s y radio: %s.",
					latitud, longitud, radio), e);
			throw new Area247Exception(String.format(
					"Error al tratar de obtener los puntos de tarjeta civica para la latidud: %s, longitud: %s y radio: %s.",
					latitud, longitud, radio), e);
		}
	}

}
