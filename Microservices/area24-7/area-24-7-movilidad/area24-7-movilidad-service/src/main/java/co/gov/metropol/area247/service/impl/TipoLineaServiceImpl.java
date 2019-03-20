package co.gov.metropol.area247.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.model.TipoLineaDTO;
import co.gov.metropol.area247.model.mapper.TipoLineaMapper;
import co.gov.metropol.area247.repository.TipoLineaRepository;
import co.gov.metropol.area247.repository.domain.TipoLinea;
import co.gov.metropol.area247.service.ITipoLineaService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class TipoLineaServiceImpl implements ITipoLineaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TipoLineaServiceImpl.class);

	@Autowired
	private TipoLineaMapper mapper;

	@Autowired
	private TipoLineaRepository tipoLineaRepository;

	@Override
	public TipoLineaDTO findById(Long id) {
		try {
			if (!Utils.isNull(id)) {
				TipoLinea tipoLinea = tipoLineaRepository.findById(id);
				return mapper.toTipoLineaDTO(tipoLinea);
			} 
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar el tipo de linea por el id {}.", id, e);
			throw new Area247Exception(String.format("Error al consultar el tipo de linea por el id s%.", id),
					e);
		}
	}
	
	@Override
	public TipoLineaDTO findByNombre(String nombre) {
		try {
			if (!Utils.isNull(nombre)) {
				TipoLinea tipoLinea = tipoLineaRepository.findByNombre(nombre);
				return mapper.toTipoLineaDTO(tipoLinea);
			} 
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar el tipo de linea por el nombre {}.", nombre, e);
			throw new Area247Exception(String.format("Error al consultar el tipo de linea por el nombre s%.", nombre),
					e);
		}
	}

	@Override
	public void saveTipoLinea(TipoLineaDTO tipoLineaDTO) {
		try {
			TipoLinea tipoLinea = mapper.toTipoLinea(tipoLineaDTO);
			tipoLineaRepository.save(tipoLinea);
			tipoLineaDTO.setId(tipoLinea.getId());
		} catch (Exception e) {
			LOGGER.error("Error al tratar de guardar el tipo de linea {}.", tipoLineaDTO, e);
			throw new Area247Exception(String.format("Error al tratar de guardar el tipo de linea %s.", tipoLineaDTO),
					e);
		}

	}

	@Override
	public void updateTipoLinea(TipoLineaDTO tipoLineaDTO) {
		try {
			TipoLinea tipoLinea = mapper.toTipoLinea(tipoLineaDTO);
			tipoLineaRepository.save(tipoLinea);
		} catch (Exception e) {
			LOGGER.error("Error al tratar de actualizar el tipo de linea {}.", tipoLineaDTO, e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar el tipo de linea %s.", tipoLineaDTO), e);
		}

	}

	@Override
	public void procesarTipoLinea(TipoLineaDTO tipoLineaDTO) {

		TipoLineaDTO tipoLineaDTOAux = findByNombre(tipoLineaDTO.getNombre());

		if (!Utils.isNull(tipoLineaDTOAux)) {
			tipoLineaDTO.setId(tipoLineaDTOAux.getId());
			updateTipoLinea(tipoLineaDTO);
		} else {
			saveTipoLinea(tipoLineaDTO);
		}
	}

}
