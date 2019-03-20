package co.gov.metropol.area247.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.model.HorarioLineaMetroDTO;
import co.gov.metropol.area247.model.mapper.HorarioLineaMetroMapper;
import co.gov.metropol.area247.repository.HorarioLineaMetroRepository;
import co.gov.metropol.area247.repository.domain.HorarioLinea;
import co.gov.metropol.area247.service.IHorarioLineaMetroService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class HorarioLineaMetroServiceImpl implements IHorarioLineaMetroService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HorarioLineaMetroServiceImpl.class);

	@Autowired
	private HorarioLineaMetroMapper horarioLineaMetroMapper;

	@Autowired
	private HorarioLineaMetroRepository horarioLineaMetroRepository;

	// @Autowired
	// private ILineaMetroService lineaMetroService;

	@Override
	public List<HorarioLineaMetroDTO> findByIdLinea(Long idLinea) {
		try {
			List<HorarioLinea> horarioLineas = horarioLineaMetroRepository.findByIdLinea(idLinea);
			return horarioLineaMetroMapper.mapToHorarioLineaMetroDTO(horarioLineas);
		} catch (Exception e) {
			LOGGER.error("Error al consultar el horario de la linea del metro por el id {}.", idLinea, e);
			throw new Area247Exception(
					String.format("Error al consultar el horario de la linea del metro por el id s%.", idLinea), e);
		}
	}

	@Override
	public void saveHorarioLinea(HorarioLineaMetroDTO horarioLineaMetroDTO) {
		try {
			HorarioLinea horarioLinea = horarioLineaMetroMapper.toHorarioLinea(horarioLineaMetroDTO);
			horarioLineaMetroRepository.save(horarioLinea);
			horarioLineaMetroDTO.setId(horarioLinea.getId());
		} catch (Exception e) {
			LOGGER.error("Error al tratar de guardar el horario {}.", horarioLineaMetroDTO, e);
			throw new Area247Exception(String.format("Error al tratar de guardar el horario %s.", horarioLineaMetroDTO),
					e);
		}

	}

	@Override
	public void updateHorarioLinea(HorarioLineaMetroDTO horarioLineaMetroDTO) {
		try {
			HorarioLinea horarioLinea = horarioLineaMetroMapper.toHorarioLinea(horarioLineaMetroDTO);
			horarioLineaMetroRepository.save(horarioLinea);
		} catch (Exception e) {
			LOGGER.error("Error al tratar de actualizar el horario {}.", horarioLineaMetroDTO, e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar el horario %s.", horarioLineaMetroDTO), e);
		}

	}

	@Override
	public HorarioLineaMetroDTO findById(Long id) {
		try {
			return horarioLineaMetroMapper.toHorarioLineaMetroDTO(horarioLineaMetroRepository.findOne(id));
		} catch (Exception e) {
			LOGGER.error("Error al consultar el horario por el id {}.", id, e);
			throw new Area247Exception(String.format("Error al consultar el horario por el id %s.", id), e);
		}
	}

	@Override
	public void procesarHorarios(List<HorarioLineaMetroDTO> horariosLineaMetroDTO) {
		if (Utils.isNotEmpty(horariosLineaMetroDTO) 
				&& !Utils.isNull(horariosLineaMetroDTO.get(0).getLineaDTO())
				&& !Utils.isNull(horariosLineaMetroDTO.get(0).getLineaDTO().getId())) {
			
			final int tamHorariosLineaMetroDTO = horariosLineaMetroDTO.size();
			List<HorarioLineaMetroDTO> horariosDTOAux = findByIdLinea(horariosLineaMetroDTO.get(0).getLineaDTO().getId());
			
			if (Utils.isNotEmpty(horariosDTOAux)) {
				final int tamHorariosDTOAux = horariosDTOAux.size();
				if (tamHorariosLineaMetroDTO > tamHorariosDTOAux) {
					for (int i = 0; i < tamHorariosLineaMetroDTO; i++) {
						HorarioLineaMetroDTO horarioDTO = horariosLineaMetroDTO.get(i);
						if (i < tamHorariosDTOAux) {
							horarioDTO.setId(horariosDTOAux.get(i).getId());
						}
						procesarHorario(horarioDTO);
					}
				} else {
					for (int i = 0; i < tamHorariosDTOAux; i++) {
						HorarioLineaMetroDTO horarioDTOAux = horariosDTOAux.get(i);
						if (i < tamHorariosLineaMetroDTO) {
							HorarioLineaMetroDTO horarioDTO = horariosLineaMetroDTO.get(i);
							horarioDTO.setId(horarioDTOAux.getId());
							procesarHorario(horarioDTO);
						} else {
							horarioLineaMetroRepository.delete(horarioDTOAux.getId());
						}
					}
				}
			} else {
				for (int i = 0; i < tamHorariosLineaMetroDTO; i++) {
					procesarHorario(horariosLineaMetroDTO.get(i));
				}
			}

		}

	}

	@Override
	public void procesarHorario(HorarioLineaMetroDTO horarioLineaMetroDTO) {
		HorarioLineaMetroDTO horarioLineaDTOAux = findByIdHorario(horarioLineaMetroDTO.getId());
		if (!Utils.isNull(horarioLineaDTOAux)) {
			horarioLineaMetroDTO.setId(horarioLineaDTOAux.getId());
			updateHorarioLinea(horarioLineaMetroDTO);
		} else {
			saveHorarioLinea(horarioLineaMetroDTO);
		}
	}

	@Override
	public HorarioLineaMetroDTO findByIdHorario(Long idHorario) {
		try {
			if (!Utils.isNull(idHorario)) {
				HorarioLinea horarioLinea = horarioLineaMetroRepository.findOne(idHorario);
				return horarioLineaMetroMapper.toHorarioLineaMetroDTO(horarioLinea);
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar el horario de la linea del metro por el id {}.", idHorario, e);
			throw new Area247Exception(
					String.format("Error al consultar el horario de la linea del metro por el id s%.", idHorario), e);
		}
	}

}
