package co.gov.metropol.area247.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.model.FrecuenciaRutaMetroDTO;
import co.gov.metropol.area247.model.mapper.FrecuenciaRutaMetroMapper;
import co.gov.metropol.area247.repository.FrecuenciaRutaMetroRepository;
import co.gov.metropol.area247.repository.domain.FrecuenciaRuta;
import co.gov.metropol.area247.service.IFrecuenciaRutaMetroService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class FrecuenciaRutaMetroServiceImpl implements IFrecuenciaRutaMetroService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FrecuenciaRutaMetroServiceImpl.class);

	@Autowired
	private FrecuenciaRutaMetroMapper mapper;

	@Autowired
	private FrecuenciaRutaMetroRepository repository;

	@Override
	public FrecuenciaRutaMetroDTO findByFrecuenciaRutaId(Long idFrecuencia) {
		try {
			FrecuenciaRuta frecuenciaRuta = repository.findByIdItem(idFrecuencia);
			return mapper.toFrecuenciaRutaMetroDTO(frecuenciaRuta);
		} catch (Exception e) {
			LOGGER.error("Error al consultar la frecuencia de la ruta del metro por el id {}.", idFrecuencia, e);
			throw new Area247Exception(
					String.format("Error al consultar la frecuencia de la ruta del metro por el id s%.", idFrecuencia),
					e);
		}
	}

	@Override
	public void saveFrecuenciaRuta(FrecuenciaRutaMetroDTO frecuenciaRutaMetroDTO) {
		try {
			FrecuenciaRuta frecuenciaRuta = mapper.toFrecuenciaRuta(frecuenciaRutaMetroDTO);
			repository.save(frecuenciaRuta);
			frecuenciaRutaMetroDTO.setId(frecuenciaRuta.getId());
		} catch (Exception e) {
			LOGGER.error("Error al tratar de guardar la frecuencia {}.", frecuenciaRutaMetroDTO, e);
			throw new Area247Exception(
					String.format("Error al tratar de guardar la frecuencia %s.", frecuenciaRutaMetroDTO), e);
		}

	}

	@Override
	public void updateFrecuenciaRuta(FrecuenciaRutaMetroDTO frecuenciaRutaMetroDTO) {
		try {
			FrecuenciaRuta frecuenciaRuta = mapper.toFrecuenciaRuta(frecuenciaRutaMetroDTO);
			repository.save(frecuenciaRuta);
		} catch (Exception e) {
			LOGGER.error("Error al tratar de actualizar la frecuencia {}.", frecuenciaRutaMetroDTO, e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar la frecuencia %s.", frecuenciaRutaMetroDTO), e);
		}

	}

	@Override
	public FrecuenciaRutaMetroDTO findById(Long id) {
		try {
			if (!Utils.isNull(id)) {
				return mapper.toFrecuenciaRutaMetroDTO(repository.findOne(id));
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar la frecuencia por el id {}.", id, e);
			throw new Area247Exception(String.format("Error al consultar la frecuencia por el id %s.", id), e);
		}
	}

	@Override
	public void procesarFrecuencias(List<FrecuenciaRutaMetroDTO> frecuenciasRutaMetroDTO) {
		if (Utils.isNotEmpty(frecuenciasRutaMetroDTO) && !Utils.isNull(frecuenciasRutaMetroDTO.get(0).getRutaMetroDTO())
				&& !Utils.isNull(frecuenciasRutaMetroDTO.get(0).getRutaMetroDTO().getId())) {

			final int tamFrecuenciasRutaMetroDTO = frecuenciasRutaMetroDTO.size();
			List<FrecuenciaRutaMetroDTO> frecuenciasDTOAux = findByIdRuta(
					frecuenciasRutaMetroDTO.get(0).getRutaMetroDTO().getId());

			if (Utils.isNotEmpty(frecuenciasDTOAux)) {
				final int tamFrecuenciasDTOAux = frecuenciasDTOAux.size();
				if (tamFrecuenciasRutaMetroDTO > tamFrecuenciasDTOAux) {
					for (int i = 0; i < tamFrecuenciasRutaMetroDTO; i++) {
						FrecuenciaRutaMetroDTO frecuenciaDTO = frecuenciasRutaMetroDTO.get(i);
						if (i < tamFrecuenciasDTOAux) {
							frecuenciaDTO.setId(frecuenciasDTOAux.get(i).getId());
						}
						procesarFrecuencia(frecuenciaDTO);
					}
				} else {
					for (int i = 0; i < tamFrecuenciasDTOAux; i++) {
						FrecuenciaRutaMetroDTO frecuenciaDTOAux = frecuenciasDTOAux.get(i);
						if (i < tamFrecuenciasRutaMetroDTO) {
							FrecuenciaRutaMetroDTO frecuenciaDTO = frecuenciasRutaMetroDTO.get(i);
							frecuenciaDTO.setId(frecuenciaDTOAux.getId());
							procesarFrecuencia(frecuenciaDTO);
						} else {
							repository.delete(frecuenciaDTOAux.getId());
						}
					}
				}
			} else {
				for (int i = 0; i < tamFrecuenciasRutaMetroDTO; i++) {
					procesarFrecuencia(frecuenciasRutaMetroDTO.get(i));
				}
			}
		}

	}

	@Override
	public void procesarFrecuencia(FrecuenciaRutaMetroDTO frecuenciaRutaMetroDTO) {
		FrecuenciaRutaMetroDTO frecuenciaRutaMetroDTOAux = findById(frecuenciaRutaMetroDTO.getId());
		if (!Utils.isNull(frecuenciaRutaMetroDTOAux)) {
			updateFrecuenciaRuta(frecuenciaRutaMetroDTO);
		} else {
			saveFrecuenciaRuta(frecuenciaRutaMetroDTO);
		}

	}

	@Override
	public List<FrecuenciaRutaMetroDTO> findByIdRuta(Long idRuta) {
		try {
			List<FrecuenciaRuta> frecuenciasRuta = repository.findByIdRuta(idRuta);
			return mapper.mapToFrecuenciaRutaMetroDTO(frecuenciasRuta);
		} catch (Exception e) {
			LOGGER.error("Error al consultar la frecuencia de la ruta del metro por el id de la linea {}.", idRuta, e);
			throw new Area247Exception(String.format(
					"Error al consultar la frecuencia de la ruta del metro por el id de la linea s%.", idRuta), e);
		}
	}

}
