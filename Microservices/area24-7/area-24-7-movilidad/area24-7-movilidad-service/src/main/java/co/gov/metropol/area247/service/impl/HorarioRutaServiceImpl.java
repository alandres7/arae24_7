package co.gov.metropol.area247.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.model.HorarioRutaMetroDTO;
import co.gov.metropol.area247.model.mapper.HorarioRutaMetroMapper;
import co.gov.metropol.area247.repository.HorarioRutaRepository;
import co.gov.metropol.area247.repository.domain.HorarioRuta;
import co.gov.metropol.area247.service.IHorarioRutaService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class HorarioRutaServiceImpl implements IHorarioRutaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HorarioRutaServiceImpl.class);

	@Autowired
	private HorarioRutaMetroMapper mapper;

	@Autowired
	private HorarioRutaRepository repository;

	@Override
	public HorarioRutaMetroDTO findByIdItemAndFuenteDatos(Long idItem, Integer fuenteDatos) {
		try {
			HorarioRuta horarioRuta = repository.findByIdItem_idFuenteDatos(idItem, fuenteDatos);
			return mapper.toHorarioRutaMetroDTO(horarioRuta);
		} catch (Exception e) {
			LOGGER.error("Error al consultar el horario de la ruta del metro por el idItem {} y la fuente de datos {}.",
					idItem, fuenteDatos, e);
			throw new Area247Exception(String.format(
					"Error al consultar el horario de la ruta del metro por el idItem s% y la fuente de datos %s.",
					idItem, fuenteDatos), e);
		}
	}

	@Override
	public void saveHorarioRuta(HorarioRutaMetroDTO horarioRutaMetroDTO) {
		try {
			HorarioRuta horarioRuta = mapper.toHorarioRuta(horarioRutaMetroDTO);
			repository.save(horarioRuta);
			horarioRutaMetroDTO.setId(horarioRuta.getId());
		} catch (Exception e) {
			LOGGER.error("Error al tratar de guardar el horario {}.", horarioRutaMetroDTO, e);
			throw new Area247Exception(String.format("Error al tratar de guardar el horario %s.", horarioRutaMetroDTO),
					e);
		}

	}

	@Override
	public void updateHorarioRuta(HorarioRutaMetroDTO horarioRutaMetroDTO) {
		try {
			HorarioRuta horarioRuta = mapper.toHorarioRuta(horarioRutaMetroDTO);
			repository.save(horarioRuta);
		} catch (Exception e) {
			LOGGER.error("Error al tratar de actualizar el horario {}.", horarioRutaMetroDTO, e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar el horario %s.", horarioRutaMetroDTO), e);
		}

	}

	@Override
	public HorarioRutaMetroDTO findById(Long id) {
		try {
			if (!Utils.isNull(id)) {
				return mapper.toHorarioRutaMetroDTO(repository.findOne(id));
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar el horario del metro por el id {}.", id, e);
			throw new Area247Exception(String.format("Error al consultar el horario del metro por el id %s.", id), e);
		}
	}

	@Override
	public void procesarHorario(HorarioRutaMetroDTO horarioDTO) {
		HorarioRutaMetroDTO horarioDTOAux = findById(horarioDTO.getId());
		if (!Utils.isNull(horarioDTOAux)) {
			horarioDTO.setId(horarioDTOAux.getId());
			updateHorarioRuta(horarioDTO);
		} else {
			saveHorarioRuta(horarioDTO);
		}

	}

	@Override
	public void procesarHorarios(List<HorarioRutaMetroDTO> horariosDTO) {
		if (Utils.isNotEmpty(horariosDTO) 
				&& !Utils.isNull(horariosDTO.get(0).getRutaDTO())
				&& !Utils.isNull(horariosDTO.get(0).getRutaDTO().getId())) {
			
			final int tamHorariosRutaMetroDTO = horariosDTO.size();
			List<HorarioRutaMetroDTO> horariosDTOAux = findByIdRuta(horariosDTO.get(0).getRutaDTO().getId());
			
			if (Utils.isNotEmpty(horariosDTOAux)) {
				final int tamHorariosDTOAux = horariosDTOAux.size();
				if (tamHorariosRutaMetroDTO > tamHorariosDTOAux) {
					for (int i = 0; i < tamHorariosRutaMetroDTO; i++) {
						HorarioRutaMetroDTO horarioDTO = horariosDTO.get(i);
						if (i < tamHorariosDTOAux) {
							horarioDTO.setId(horariosDTOAux.get(i).getId());
						}
						procesarHorario(horarioDTO);
					}
				} else {
					for (int i = 0; i < tamHorariosDTOAux; i++) {
						HorarioRutaMetroDTO horarioDTOAux = horariosDTOAux.get(i);
						if (i < tamHorariosRutaMetroDTO) {
							HorarioRutaMetroDTO horarioDTO = horariosDTO.get(i);
							horarioDTO.setId(horarioDTOAux.getId());
							procesarHorario(horarioDTO);
						} else {
							repository.delete(horarioDTOAux.getId());
						}
					}
				}
			} else {
				for (int i = 0; i < tamHorariosRutaMetroDTO; i++) {
					procesarHorario(horariosDTO.get(i));
				}
			}

		}
	}

	@Override
	public List<HorarioRutaMetroDTO> findByIdRuta(Long idRuta) {
		try {
			List<HorarioRuta> horarioRutas = repository.findByIdRuta(idRuta);
			return mapper.mapToHorarioRutaMetroDTO(horarioRutas);
		} catch (Exception e) {
			LOGGER.error("Error al consultar el horario de la ruta del metro por el id {}.", idRuta, e);
			throw new Area247Exception(
					String.format("Error al consultar el horario de la ruta del metro por el id s%.", idRuta), e);
		}
	}

}
