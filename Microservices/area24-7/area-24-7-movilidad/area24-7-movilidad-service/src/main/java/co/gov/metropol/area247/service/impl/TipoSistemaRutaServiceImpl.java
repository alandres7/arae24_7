package co.gov.metropol.area247.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import co.gov.metropol.area247.model.TipoSistemaRutaDTO;
import co.gov.metropol.area247.model.mapper.TipoSistemaRutaMapper;
import co.gov.metropol.area247.repository.TipoSistemaRutaRepository;
import co.gov.metropol.area247.repository.domain.TipoSistemaRuta;
import co.gov.metropol.area247.service.ITipoSistemaRutaService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class TipoSistemaRutaServiceImpl implements ITipoSistemaRutaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TipoSistemaRutaServiceImpl.class);

	@Autowired
	private TipoSistemaRutaMapper mapper;

	@Autowired
	private TipoSistemaRutaRepository repository;

	@Override
	public TipoSistemaRutaDTO findByNombreAndFuenteDatos(String nombre, Integer fuenteDatos) {
		try {
			if (!Utils.isNull(nombre) && !Utils.isNull(fuenteDatos)) {
				TipoSistemaRuta tipoSistemaRuta = repository.findByNombreAndFuenteDatos(nombre, fuenteDatos);
				return mapper.toTipoSistemaRutaDTO(tipoSistemaRuta);
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar el tipo de sistema de ruta por el nombre {} y fuente de datos {}.", nombre,
					fuenteDatos, e);
			throw new Area247Exception(String.format(
					"Error al consultar el tipo de sistema de ruta por el nombre s% y fuente de datos %s.", nombre,
					fuenteDatos), e);
		}
	}

	@Override
	public void saveTipoSistemaRuta(TipoSistemaRutaDTO tipoSistemaRutaDTO) {
		try {
			TipoSistemaRuta tipoSistemaRuta = mapper.toTipoSistemaRuta(tipoSistemaRutaDTO);
			repository.save(tipoSistemaRuta);
			tipoSistemaRutaDTO.setId(tipoSistemaRuta.getId());
		} catch (Exception e) {
			LOGGER.error("Error al tratar de guardar el tipo de sistema de ruta {}.", tipoSistemaRutaDTO, e);
			throw new Area247Exception(
					String.format("Error al tratar de guardar el tipo de sistema de ruta %s.", tipoSistemaRutaDTO), e);
		}

	}

	@Override
	public void updateTipoSistemaRuta(TipoSistemaRutaDTO tipoSistemaRutaDTO) {
		try {
			TipoSistemaRuta tipoSistemaRuta = mapper.toTipoSistemaRuta(tipoSistemaRutaDTO);
			repository.save(tipoSistemaRuta);
		} catch (Exception e) {
			LOGGER.error("Error al tratar de actualizar el tipo de sistema de ruta {}.", tipoSistemaRutaDTO, e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar el tipo de sistema de ruta %s.", tipoSistemaRutaDTO),
					e);
		}

	}

	@Override
	public void procesarTipoSistemaRuta(TipoSistemaRutaDTO tipoSistemaRutaDTO) {

		TipoSistemaRutaDTO tipoSistemaRutaDTOAux = findByNombreAndFuenteDatos(tipoSistemaRutaDTO.getNombre(),
				tipoSistemaRutaDTO.getFuenteDatos());

		if (!Utils.isNull(tipoSistemaRutaDTOAux)) {
			tipoSistemaRutaDTO.setId(tipoSistemaRutaDTOAux.getId());
			updateTipoSistemaRuta(tipoSistemaRutaDTO);
		} else {
			saveTipoSistemaRuta(tipoSistemaRutaDTO);
		}
	}

	@Override
	public TipoSistemaRutaDTO findById(Long id) {
		try {
			if (!Utils.isNull(id)) {
				TipoSistemaRuta tipoSistemaRuta = repository.findOne(id);
				return mapper.toTipoSistemaRutaDTO(tipoSistemaRuta);
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar el tipo de sistema de ruta por el id {}.", id, e);
			throw new Area247Exception(String.format("Error al consultar el tipo de sistema de ruta por el id %s.", id),
					e);
		}
	}

	@Override
	public List<TipoSistemaRutaDTO> findAllActivas() {
		try {
			return mapper.mapToTipoSistemaRutaDTO(Lists.newArrayList(repository.findAll()));
		} catch (Exception e) {
			throw new Area247Exception("Error al obtener todos los tipos de sistemas");
		}
	}

}
