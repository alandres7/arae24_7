package co.gov.metropol.area247.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.model.TipoOrientacionDTO;
import co.gov.metropol.area247.model.mapper.TipoOrientacionMapper;
import co.gov.metropol.area247.repository.TipoOrientacionRepository;
import co.gov.metropol.area247.repository.domain.TipoOrientacion;
import co.gov.metropol.area247.service.ITipoOrientacionService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class TipoOrientacionServiceImpl implements ITipoOrientacionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TipoOrientacionServiceImpl.class);

	@Autowired
	private TipoOrientacionMapper mapper;

	@Autowired
	private TipoOrientacionRepository repository;

	@Override
	public TipoOrientacionDTO findByNombreAndFuenteDatos(String nombre, Integer fuenteDatos) {
		try {
			if (!Utils.isNull(nombre) && !Utils.isNull(fuenteDatos)) {
				TipoOrientacion tipoOrientacion = repository.findByNombreAndFuenteDatos(nombre, fuenteDatos);
				return mapper.toTipoOrientacionDTO(tipoOrientacion);
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar el tipo de orientacion por el nombre {} y fuente de datos {}.", nombre,
					fuenteDatos, e);
			throw new Area247Exception(
					String.format("Error al consultar el tipo de orientacion por el nombre s% y fuente de datos %s.",
							nombre, fuenteDatos),
					e);
		}
	}

	@Override
	public void saveTipoOrientacion(TipoOrientacionDTO tipoOrientacionDTO) {
		try {
			TipoOrientacion tipoOrientacion = mapper.toTipoOrientacion(tipoOrientacionDTO);
			repository.save(tipoOrientacion);
			tipoOrientacionDTO.setId(tipoOrientacion.getId());
		} catch (Exception e) {
			LOGGER.error("Error al tratar de guardar el tipo de orientacion {}.", tipoOrientacionDTO, e);
			throw new Area247Exception(
					String.format("Error al tratar de guardar el tipo de orientacion %s.", tipoOrientacionDTO), e);
		}

	}

	@Override
	public void updateTipoOrientacion(TipoOrientacionDTO tipoOrientacionDTO) {
		try {
			TipoOrientacion tipoOrientacion = mapper.toTipoOrientacion(tipoOrientacionDTO);
			repository.save(tipoOrientacion);
		} catch (Exception e) {
			LOGGER.error("Error al tratar de actualizar el tipo de orientacion {}.", tipoOrientacionDTO, e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar el tipo de orientacion %s.", tipoOrientacionDTO), e);
		}

	}

	@Override
	public void procesarTipoOrientacion(TipoOrientacionDTO tipoOrientacionDTO) {

		TipoOrientacionDTO tipoOrientacionDTOAux = findByNombreAndFuenteDatos(tipoOrientacionDTO.getNombre(),
				tipoOrientacionDTO.getFuenteDatos());

		if (!Utils.isNull(tipoOrientacionDTOAux)) {
			tipoOrientacionDTO.setId(tipoOrientacionDTOAux.getId());
			updateTipoOrientacion(tipoOrientacionDTO);
		} else {
			saveTipoOrientacion(tipoOrientacionDTO);
		}
	}

	@Override
	public TipoOrientacionDTO findById(Long id) {
		try {
			if (!Utils.isNull(id)) {
				TipoOrientacion tipoOrientacion = repository.findById(id);
				return mapper.toTipoOrientacionDTO(tipoOrientacion);
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar el tipo de orientacion por el id {}.", id, e);
			throw new Area247Exception(String.format("Error al consultar el tipo de orientacion por el id s%.", id), e);
		}
	}

}
