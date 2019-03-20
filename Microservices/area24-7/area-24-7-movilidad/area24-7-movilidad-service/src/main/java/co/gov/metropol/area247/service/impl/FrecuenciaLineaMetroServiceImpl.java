package co.gov.metropol.area247.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.model.FrecuenciaLineaMetroDTO;
import co.gov.metropol.area247.model.mapper.FrecuenciaLineaMetroMapper;
import co.gov.metropol.area247.repository.FrecuenciaLineaMetroRepository;
import co.gov.metropol.area247.repository.domain.FrecuenciaLinea;
import co.gov.metropol.area247.service.IFrecuenciaLineaMetroService;
import co.gov.metropol.area247.service.ILineaMetroService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class FrecuenciaLineaMetroServiceImpl implements IFrecuenciaLineaMetroService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FrecuenciaLineaMetroServiceImpl.class);

	@Autowired
	private FrecuenciaLineaMetroMapper mapper;

	@Autowired
	private FrecuenciaLineaMetroRepository repository;

	@Autowired
	private ILineaMetroService lineaMetroService;

	@Override
	public FrecuenciaLineaMetroDTO findByFrecuenciaLineaId(Long idFrecuencia) {
		try {
			FrecuenciaLinea frecuenciaLinea = repository.findByIdFrecuencia(idFrecuencia);
			return mapper.toFrecuenciaLineaMetroDTO(frecuenciaLinea);
		} catch (Exception e) {
			LOGGER.error("Error al consultar la frecuencia de la linea del metro por el id {}.", idFrecuencia, e);
			throw new Area247Exception(
					String.format("Error al consultar la frecuencia de la linea del metro por el id s%.", idFrecuencia),
					e);
		}
	}

	@Override
	public FrecuenciaLinea saveFrecuenciaLinea(FrecuenciaLineaMetroDTO frecuenciaLineaMetroDTO) {
		try {
			FrecuenciaLinea frecuenciaLinea = mapper.toFrecuenciaLinea(frecuenciaLineaMetroDTO);
			repository.save(frecuenciaLinea);
			frecuenciaLineaMetroDTO.setId(frecuenciaLinea.getId());
			return frecuenciaLinea;
		} catch (Exception e) {
			LOGGER.error("Error al tratar de guardar la frecuencia {}.", frecuenciaLineaMetroDTO, e);
			throw new Area247Exception(
					String.format("Error al tratar de guardar la frecuencia %s.", frecuenciaLineaMetroDTO), e);
		}

	}

	@Override
	public FrecuenciaLinea updateFrecuenciaLinea(FrecuenciaLineaMetroDTO frecuenciaLineaMetroDTO) {
		try {
			FrecuenciaLinea frecuenciaLinea = mapper.toFrecuenciaLinea(frecuenciaLineaMetroDTO);
			return repository.save(frecuenciaLinea);
		} catch (Exception e) {
			LOGGER.error("Error al tratar de actualizar la frecuencia {}.", frecuenciaLineaMetroDTO, e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar la frecuencia %s.", frecuenciaLineaMetroDTO), e);
		}

	}

	@Override
	public FrecuenciaLineaMetroDTO findById(Long id) {
		try {
			if (!Utils.isNull(id)) {
				return mapper.toFrecuenciaLineaMetroDTO(repository.findOne(id));
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar la frecuencia por el id {}.", id, e);
			throw new Area247Exception(String.format("Error al consultar la frecuencia por el id %s.", id), e);
		}
	}

	@Override
	public void procesarFrecuencias(List<FrecuenciaLineaMetroDTO> frecuenciasLineaMetroDTO) {
		if (Utils.isNotEmpty(frecuenciasLineaMetroDTO) && !Utils.isNull(frecuenciasLineaMetroDTO.get(0).getLineaDTO())
				&& !Utils.isNull(frecuenciasLineaMetroDTO.get(0).getLineaDTO().getId())) {

			final int tamFrecuenciasLineaMetroDTO = frecuenciasLineaMetroDTO.size();
			List<FrecuenciaLineaMetroDTO> frecuenciasDTOAux = findByIdLinea(
					frecuenciasLineaMetroDTO.get(0).getLineaDTO().getId(), false);

			if (Utils.isNotEmpty(frecuenciasDTOAux)) {
				final int tamFrecuenciasDTOAux = frecuenciasDTOAux.size();
				if (tamFrecuenciasLineaMetroDTO > tamFrecuenciasDTOAux) {
					for (int i = 0; i < tamFrecuenciasLineaMetroDTO; i++) {
						FrecuenciaLineaMetroDTO frecuenciaDTO = frecuenciasLineaMetroDTO.get(i);
						if (i < tamFrecuenciasDTOAux) {
							frecuenciaDTO.setId(frecuenciasDTOAux.get(i).getId());
						}
						procesarFrecuencia(frecuenciaDTO);
					}
				} else {
					for (int i = 0; i < tamFrecuenciasDTOAux; i++) {
						FrecuenciaLineaMetroDTO frecuenciaDTOAux = frecuenciasDTOAux.get(i);
						if (i < tamFrecuenciasLineaMetroDTO) {
							FrecuenciaLineaMetroDTO frecuenciaDTO = frecuenciasLineaMetroDTO.get(i);
							frecuenciaDTO.setId(frecuenciaDTOAux.getId());
							procesarFrecuencia(frecuenciaDTO);
						} else {
							repository.delete(frecuenciaDTOAux.getId());
						}
					}
				}
			} else {
				for (int i = 0; i < tamFrecuenciasLineaMetroDTO; i++) {
					procesarFrecuencia(frecuenciasLineaMetroDTO.get(i));
				}
			}
		}
	}

	@Override
	public FrecuenciaLinea procesarFrecuencia(FrecuenciaLineaMetroDTO frecuenciaLineaMetroDTO) {
		FrecuenciaLineaMetroDTO frecuenciaLineaDTOAux = findById(frecuenciaLineaMetroDTO.getId());
		if (!Utils.isNull(frecuenciaLineaDTOAux)) {
			return updateFrecuenciaLinea(frecuenciaLineaMetroDTO);
		} else {
			return saveFrecuenciaLinea(frecuenciaLineaMetroDTO);
		}
	}

	@Override
	public List<FrecuenciaLineaMetroDTO> findByIdLinea(Long idLinea, boolean m2o) {
		try {

			List<FrecuenciaLineaMetroDTO> frecuenciasDTO = mapper
					.mapToFrecuenciaLineaMetroDTO(repository.findByIdLinea(idLinea));

			if (m2o) {
				for (FrecuenciaLineaMetroDTO estacionDTO : frecuenciasDTO) {
					conProfundizacion(estacionDTO);
				}
			}
			
			return frecuenciasDTO;

		} catch (Exception e) {
			LOGGER.error("Error al consultar la frecuencia de la linea del metro por el id de la linea {}.", idLinea,
					e);
			throw new Area247Exception(String.format(
					"Error al consultar la frecuencia de la linea del metro por el id de la linea s%.", idLinea), e);
		}
	}

	private void conProfundizacion(FrecuenciaLineaMetroDTO frecuenciaDTO) {

		if (!Utils.isNull(frecuenciaDTO.getLineaDTO()) && !Utils.isNull(frecuenciaDTO.getLineaDTO().getId())) {
			frecuenciaDTO.setLineaDTO(lineaMetroService.findById(frecuenciaDTO.getLineaDTO().getId(), false, false));
		}
	}
}
