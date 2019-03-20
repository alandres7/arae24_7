package co.gov.metropol.area247.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.model.TipoModoTransporteDTO;
import co.gov.metropol.area247.model.mapper.TipoModoTransporteMapper;
import co.gov.metropol.area247.repository.TipoModoTransporteRepository;
import co.gov.metropol.area247.repository.domain.TipoModoTransporte;
import co.gov.metropol.area247.service.ITipoModoTransporteService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class TipoModoTransporteServiceImpl implements ITipoModoTransporteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TipoModoTransporteServiceImpl.class);

	@Autowired
	private TipoModoTransporteRepository repository;

	@Autowired
	private TipoModoTransporteMapper mapper;

	@Override
	public TipoModoTransporteDTO findById(Long id) {
		try {

			if (!Utils.isNull(id)) {
				TipoModoTransporte tipoModoTransporte = repository.findById(id);
				return mapper.toTipoModoTransporteDTO(tipoModoTransporte);
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar el tipo de modo de transporte por el id.", id, e);
			throw new Area247Exception(String.format("Error al consultar el tipo de modo de transporte por el id", id),
					e);
		}
	}
	
	@Override
	public TipoModoTransporteDTO findByNombreAndFuenteDatos(String nombre, Integer fuenteDatos) {
		try {
			if (!Utils.isNull(nombre) && !Utils.isNull(fuenteDatos)) {
				TipoModoTransporte tipoModoTransporte = repository.findByNombreAndFuenteDatos(nombre, fuenteDatos);
				return mapper.toTipoModoTransporteDTO(tipoModoTransporte);
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar el tipo de modo de transporte por el nombre {} y fuente de datos {}.",
					nombre, fuenteDatos, e);
			throw new Area247Exception(String.format(
					"Error al consultar el tipo de modo de transporte por el nombre %s y fuente de datos %s.", nombre,
					fuenteDatos), e);
		}
	}

	@Override
	public void saveTipoModoTransporte(TipoModoTransporteDTO tipoModoTransporteDTO) {
		try {
			TipoModoTransporte tipoModoTransporte = mapper.toTipoModoTransporte(tipoModoTransporteDTO);
			repository.save(tipoModoTransporte);
			tipoModoTransporteDTO.setId(tipoModoTransporte.getId());
		} catch (Exception e) {
			LOGGER.error("Error al tratar de guardar el tipo de modo de transporte {}.", tipoModoTransporteDTO, e);
			throw new Area247Exception(String.format("Error al tratar de guardar el tipo de modo de transporte %s.",
					tipoModoTransporteDTO), e);
		}

	}

	@Override
	public void updateTipoModoTransporte(TipoModoTransporteDTO tipoModoTransporteDTO) {
		try {
			TipoModoTransporte tipoModoTransporte = mapper.toTipoModoTransporte(tipoModoTransporteDTO);
			repository.save(tipoModoTransporte);
		} catch (Exception e) {
			LOGGER.error("Error al tratar de guardar el tipo de modo de transporte {}.", tipoModoTransporteDTO, e);
			throw new Area247Exception(String.format("Error al tratar de guardar el tipo de modo de transporte %s.",
					tipoModoTransporteDTO), e);
		}

	}

	@Override
	public void procesarTipoModoTransporte(TipoModoTransporteDTO tipoModoTransporteDTO) {
		TipoModoTransporteDTO tipoModoTransporteDTOAux = findByNombreAndFuenteDatos(tipoModoTransporteDTO.getNombre(),
				tipoModoTransporteDTO.getFuenteDatos());

		if (!Utils.isNull(tipoModoTransporteDTOAux)) {
			tipoModoTransporteDTO.setId(tipoModoTransporteDTOAux.getId());
			updateTipoModoTransporte(tipoModoTransporteDTO);
		} else {
			saveTipoModoTransporte(tipoModoTransporteDTO);
		}

	}

}
