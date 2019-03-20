package co.gov.metropol.area247.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.model.TipoRutaDTO;
import co.gov.metropol.area247.model.mapper.TipoRutaMapper;
import co.gov.metropol.area247.repository.TipoRutaRepository;
import co.gov.metropol.area247.repository.domain.TipoRuta;
import co.gov.metropol.area247.service.ITipoRutaService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class TipoRutaServiceImpl implements ITipoRutaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TipoRutaServiceImpl.class);

	@Autowired
	private TipoRutaMapper mapper;

	@Autowired
	private TipoRutaRepository repository;

	@Override
	public TipoRutaDTO findByNombreAndFuenteDatos(String nombre, Integer fuenteDatos) {
		try {
			if (!Utils.isNull(nombre) && !Utils.isNull(fuenteDatos)) {
				TipoRuta tipoRuta = repository.findByNombreAndFuenteDatos(nombre, fuenteDatos);
				return mapper.toTipoRutaDTO(tipoRuta);
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar el tipo de ruta por el nombre {} y la fuente de datos.", nombre,
					fuenteDatos, e);
			throw new Area247Exception(
					String.format("Error al consultar el tipo de ruta por el nombre s% y la fuente de datos.", nombre,
							fuenteDatos),
					e);
		}
	}

	@Override
	public void saveTipoRuta(TipoRutaDTO tipoRutaDTO) {
		try {
			TipoRuta tipoRuta = mapper.toTipoRuta(tipoRutaDTO);
			repository.save(tipoRuta);
			tipoRutaDTO.setId(tipoRuta.getId());
		} catch (Exception e) {
			LOGGER.error("Error al tratar de guardar el tipo de ruta {}.", tipoRutaDTO, e);
			throw new Area247Exception(String.format("Error al tratar de guardar el tipo de ruta %s.", tipoRutaDTO), e);
		}

	}

	@Override
	public void updateTipoRuta(TipoRutaDTO tipoRutaDTO) {
		try {
			TipoRuta tipoRuta = mapper.toTipoRuta(tipoRutaDTO);
			repository.save(tipoRuta);
		} catch (Exception e) {
			LOGGER.error("Error al tratar de actualizar el tipo de ruta {}.", tipoRutaDTO, e);
			throw new Area247Exception(String.format("Error al tratar de actualizar el tipo de ruta %s.", tipoRutaDTO),
					e);
		}

	}

	@Override
	public void procesarTipoRuta(TipoRutaDTO tipoRutaDTO) {

		TipoRutaDTO tipoRutaDTOAux = findByNombreAndFuenteDatos(tipoRutaDTO.getNombre(), tipoRutaDTO.getFuenteDatos());

		if (!Utils.isNull(tipoRutaDTOAux)) {
			tipoRutaDTO.setId(tipoRutaDTOAux.getId());
			updateTipoRuta(tipoRutaDTO);
		} else {
			saveTipoRuta(tipoRutaDTO);
		}
	}

	@Override
	public TipoRutaDTO findById(Long id) {
		try {
			if (!Utils.isNull(id)) {
				TipoRuta tipoRuta = repository.findOne(id);
				return mapper.toTipoRutaDTO(tipoRuta);
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar el tipo de ruta por el id {}", id, e);
			throw new Area247Exception(String.format("Error al consultar el tipo de ruta por el id {}", id), e);
		}
	}

}
