package co.gov.metropol.area247.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.model.TipoParaderoDTO;
import co.gov.metropol.area247.model.mapper.TipoParaderoMapper;
import co.gov.metropol.area247.repository.TipoParaderoRepository;
import co.gov.metropol.area247.repository.domain.TipoParadero;
import co.gov.metropol.area247.service.ITipoParaderoService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class TipoParaderoServiceImpl implements ITipoParaderoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TipoParaderoServiceImpl.class);

	@Autowired
	private TipoParaderoMapper mapper;

	@Autowired
	private TipoParaderoRepository repository;

	@Override
	public TipoParaderoDTO findByNombreAndFuenteDatos(String nombre, Integer fuenteDatos) {
		try {
			if (!Utils.isNull(nombre) && !Utils.isNull(fuenteDatos)) {
				TipoParadero tipoParadero = repository.findByNombreAndFuenteDatos(nombre, fuenteDatos);
				return mapper.toTipoParaderoDTO(tipoParadero);
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar el tipo de paradero por el nombre {} y fuente de datos {}.", nombre,
					fuenteDatos, e);
			throw new Area247Exception(
					String.format("Error al consultar el tipo de paradero por el nombre s% y fuente de datos %s.",
							nombre, fuenteDatos),
					e);
		}
	}

	@Override
	public void saveTipoParadero(TipoParaderoDTO tipoParaderoDTO) {
		try {
			TipoParadero tipoParadero = mapper.toTipoParadero(tipoParaderoDTO);
			repository.save(tipoParadero);
			tipoParaderoDTO.setId(tipoParadero.getId());
		} catch (Exception e) {
			LOGGER.error("Error al tratar de guardar el tipo de paradero {}.", tipoParaderoDTO, e);
			throw new Area247Exception(
					String.format("Error al tratar de guardar el tipo de paradero %s.", tipoParaderoDTO), e);
		}

	}

	@Override
	public void updateTipoParadero(TipoParaderoDTO tipoParaderoDTO) {
		try {
			TipoParadero tipoParadero = mapper.toTipoParadero(tipoParaderoDTO);
			repository.save(tipoParadero);
		} catch (Exception e) {
			LOGGER.error("Error al tratar de actualizar el tipo de paradero {}.", tipoParaderoDTO, e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar el tipo de paradero %s.", tipoParaderoDTO), e);
		}

	}

	@Override
	public void procesarTipoParadero(TipoParaderoDTO tipoParaderoDTO) {

		TipoParaderoDTO tipoParaderoDTOAux = findByNombreAndFuenteDatos(tipoParaderoDTO.getNombre(),
				tipoParaderoDTO.getFuenteDatos());

		if (!Utils.isNull(tipoParaderoDTOAux)) {
			tipoParaderoDTO.setId(tipoParaderoDTOAux.getId());
			updateTipoParadero(tipoParaderoDTO);
		} else {
			saveTipoParadero(tipoParaderoDTO);
		}
	}

	@Override
	public TipoParaderoDTO findById(Long id) {
		try {
			if (!Utils.isNull(id)) {
				TipoParadero tipoParadero = repository.findById(id);
				return mapper.toTipoParaderoDTO(tipoParadero);
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar el tipo de paradero por el id {}.", id, e);
			throw new Area247Exception(String.format("Error al consultar el tipo de paradero por el id %s.", id), e);
		}
	}

}
