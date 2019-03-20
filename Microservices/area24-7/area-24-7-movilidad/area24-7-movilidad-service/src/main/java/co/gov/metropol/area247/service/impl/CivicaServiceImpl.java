package co.gov.metropol.area247.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.gateway.ICivicaServiceGateway;
import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.model.CivicaDTO;
import co.gov.metropol.area247.repository.domain.PuntoTarjetaCivica;
import co.gov.metropol.area247.service.ICivicaService;
import co.gov.metropol.area247.service.impl.abstracts.AbstractCivicaService;
import co.gov.metropol.area247.services.rest.metro.FeatureCivicaExpedicionWSDTO;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class CivicaServiceImpl extends AbstractCivicaService implements ICivicaService {

	@Autowired
	private ICivicaServiceGateway civicaServiceGateway;
	
	@Override
	public void cargarPutosCivicaExpedicion() {
		try {
			List<FeatureCivicaExpedicionWSDTO> features = civicaServiceGateway.consultarCivicaExpedicion(); 
			features.forEach(feature -> {
				procesarCivicaExpedicion(feature.getCivicaDTO());
			});
			
		} catch (Exception e) {
			LoggingUtil.logException("Error al procesar civica expedición.", e);
			throw new Area247Exception("Error al procesar civica expedición.", e);
		}
		
	}
	
	private void procesarCivicaExpedicion(CivicaDTO civicaDTO) {
			CivicaDTO itemCivicaDTO = findCivicaByIdItemAndTipoPunto(civicaDTO.getIdItem(), civicaDTO.getTipoPunto());

			if (!Utils.isNull(itemCivicaDTO)) {
				updateCivica(itemCivicaDTO);
			} else {
				saveCivica(civicaDTO);
			}
	}

	@Override
	public CivicaDTO findCivicaByIdItemAndTipoPunto(Long idItem, String tipoPunto) {
		try {
			return mapper.tocivicaDTO(civicaRepository.findByIdItemAndTipoPunto(idItem, tipoPunto));
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al consultar la tarjeta civica por el idItem s% de encicla.", idItem), e);
			throw new Area247Exception(
					String.format("Error al consultar la tarjeta civica por el idItem s% de encicla.", idItem), e);
		}
	}

	@Override
	public void saveCivica(CivicaDTO civicaDTO) {
		try {
			PuntoTarjetaCivica puntoTarjetaCivica = mapper.toPuntoTarjetaCivica(civicaDTO);
			civicaRepository.save(puntoTarjetaCivica);
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de guardar el punto de tarjeta civica %s.", civicaDTO), e);
			throw new Area247Exception(
					String.format("Error al tratar de guardar el punto de tarjeta civica %s.", civicaDTO), e);
		}
	}

	@Override
	public void updateCivica(CivicaDTO civicaDTO) {
		try {
			PuntoTarjetaCivica puntoTarjetaCivica = mapper.toPuntoTarjetaCivica(civicaDTO);
			civicaRepository.save(puntoTarjetaCivica);
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de actualizar el punto de tarjeta civica %s.", civicaDTO), e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar el punto de tarjeta civica %s.", civicaDTO), e);
		}
	}

}
