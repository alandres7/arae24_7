package co.gov.metropol.area247.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.model.ModoEstacionDTO;
import co.gov.metropol.area247.model.mapper.ModoEstacionMapper;
import co.gov.metropol.area247.repository.ModoEstacionRepository;
import co.gov.metropol.area247.repository.domain.ModoEstacion;
import co.gov.metropol.area247.service.IModoEstacionService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class ModoEstacionServiceImpl implements IModoEstacionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModoEstacionServiceImpl.class);

	@Autowired
	private ModoEstacionRepository repository;

	@Autowired
	private ModoEstacionMapper mapper;
	
	@Override
	public ModoEstacionDTO findById(Long id) {
		try {
			if (!Utils.isNull(id)) {
				ModoEstacion modoEstacion = repository.findById(id);
				return mapper.toModoEstacionDTO(modoEstacion);
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar el modo de la estacion de la linea del metro por el id {}.", id,
					e);
			throw new Area247Exception(String.format(
					"Error al consultar el modo de la estacion de la linea del metro por el id s%.", id), e);
		}
	}

	@Override
	public ModoEstacionDTO findByNombre(String nombre) {
		try {
			if (!Utils.isNull(nombre)) {
				ModoEstacion modoEstacion = repository.findByNombre(nombre);
				return mapper.toModoEstacionDTO(modoEstacion);
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar el modo de la estacion de la linea del metro por el nombre {}.", nombre,
					e);
			throw new Area247Exception(String.format(
					"Error al consultar el modo de la estacion de la linea del metro por el nombre s%.", nombre), e);
		}
	}

	@Override
	public void saveModoEstacion(ModoEstacionDTO modoEstacionDTO) {
		try {
			ModoEstacion modoEstacion = mapper.toModoEstacion(modoEstacionDTO);
			repository.save(modoEstacion);
			modoEstacionDTO.setId(modoEstacion.getId());
		} catch (Exception e) {
			LOGGER.error("Error al tratar de guardar el modo de la estacion {}.", modoEstacionDTO, e);
			throw new Area247Exception(
					String.format("Error al tratar de guardar el modo de la estacion %s.", modoEstacionDTO), e);
		}

	}

	@Override
	public void updateModoEstacion(ModoEstacionDTO modoEstacionDTO) {
		try {
			ModoEstacion modoEstacion = mapper.toModoEstacion(modoEstacionDTO);
			repository.save(modoEstacion);
		} catch (Exception e) {
			LOGGER.error("Error al tratar de guardar el modo de la estacion {}.", modoEstacionDTO, e);
			throw new Area247Exception(
					String.format("Error al tratar de guardar el modo de la estacion %s.", modoEstacionDTO), e);
		}

	}

	@Override
	public void procesarModoEstacion(ModoEstacionDTO modoEstacionDTO) {
		ModoEstacionDTO modoEstacionDTOAux = findByNombre(modoEstacionDTO.getNombre());
		if (!Utils.isNull(modoEstacionDTOAux)) {
			modoEstacionDTO.setId(modoEstacionDTOAux.getId());
			updateModoEstacion(modoEstacionDTO);
		} else {
			saveModoEstacion(modoEstacionDTO);
		}

	}

}
