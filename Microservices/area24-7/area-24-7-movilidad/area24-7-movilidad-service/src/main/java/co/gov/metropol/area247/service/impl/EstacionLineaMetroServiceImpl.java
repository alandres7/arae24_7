package co.gov.metropol.area247.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

import co.gov.metropol.area247.model.EstacionLineaMetroDTO;
import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.model.mapper.EstacionLineaMetroMapper;
import co.gov.metropol.area247.repository.EstacionLineaMetroRepository;
import co.gov.metropol.area247.repository.domain.EstacionMetro;
import co.gov.metropol.area247.service.IEstacionLineaMetroService;
import co.gov.metropol.area247.service.ILineaMetroService;
import co.gov.metropol.area247.util.PropertiesManager;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.constantes.Constantes.Recursos;
import co.gov.metropol.area247.util.ex.Area247Exception;
import co.gov.metropol.area247.util.web.Coordenada;

@Service
public class EstacionLineaMetroServiceImpl implements IEstacionLineaMetroService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EstacionLineaMetroServiceImpl.class);

	@Autowired
	private EstacionLineaMetroMapper estacionLineaMetroMapper;

	@Autowired
	private EstacionLineaMetroRepository estacionLineaMetroRepository;

	@Autowired
	private ILineaMetroService lineaMetroService;

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IEstacionLineaMetroService#findByEstacionLineaId(java.lang.Long)
	 */
	@Override
	public EstacionLineaMetroDTO findByEstacionLineaId(Long idEstacion) {
		try {
			if (!Utils.isNull(idEstacion)) {
				EstacionMetro estacionMetro = estacionLineaMetroRepository.findByIdEstacion(idEstacion);
				return estacionLineaMetroMapper.toEstacionLineaMetroDTO(estacionMetro);
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar la estacion de la linea del metro por el id {}.", idEstacion, e);
			throw new Area247Exception(
					String.format("Error al consultar la estacion de la linea del metro por el id s%.", idEstacion), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IEstacionLineaMetroService#saveEstacionMetro(co.gov.metropol.area247.model.EstacionLineaMetroDTO)
	 */
	@Override
	public void saveEstacionMetro(EstacionLineaMetroDTO estacionLineaMetroDTO) {
		try {
			EstacionMetro estacionMetro = estacionLineaMetroMapper.toEstacionMetro(estacionLineaMetroDTO);

//			if (!Utils.isNull(estacionLineaMetroDTO.getModoEstacionDTO())) {
//				modoEstacionService.procesarModoEstacion(estacionLineaMetroDTO.getModoEstacionDTO());
//				estacionMetro.setIdModoEstacion(estacionLineaMetroDTO.getModoEstacionDTO().getId());
//			}

			estacionLineaMetroRepository.save(estacionMetro);
			estacionLineaMetroDTO.setId(estacionMetro.getId());
		} catch (Exception e) {
			LOGGER.error("Error al tratar de guardar la estacion {}.", estacionLineaMetroDTO, e);
			throw new Area247Exception(
					String.format("Error al tratar de guardar la estacion %s.", estacionLineaMetroDTO), e);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IEstacionLineaMetroService#updateEstacionMetro(co.gov.metropol.area247.model.EstacionLineaMetroDTO)
	 */
	@Override
	public void updateEstacionMetro(EstacionLineaMetroDTO estacionLineaMetroDTO) {
		try {
			EstacionMetro estacionMetro = estacionLineaMetroMapper.toEstacionMetro(estacionLineaMetroDTO);

//			if (!Utils.isNull(estacionLineaMetroDTO.getModoEstacionDTO())) {
//				modoEstacionService.procesarModoEstacion(estacionLineaMetroDTO.getModoEstacionDTO());
//				estacionMetro.setIdModoEstacion(estacionLineaMetroDTO.getModoEstacionDTO().getId());
//			}

			estacionLineaMetroRepository.save(estacionMetro);
		} catch (Exception e) {
			LOGGER.error("Error al tratar de actualizar la estacion {}.", estacionLineaMetroDTO, e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar la estacion %s.", estacionLineaMetroDTO), e);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IEstacionLineaMetroService#findById(java.lang.Long)
	 */
	@Override
	public EstacionLineaMetroDTO findById(Long id) {
		try {
			return estacionLineaMetroMapper.toEstacionLineaMetroDTO(estacionLineaMetroRepository.findOne(id));
		} catch (Exception e) {
			LOGGER.error("Error al consultar la estacion del metro por el id {}.", id, e);
			throw new Area247Exception(String.format("Error al consultar la estacion del metro por el id %s.", id), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IEstacionLineaMetroService#procesarEstaciones(java.util.List)
	 */
	@Override
	public void procesarEstaciones(List<EstacionLineaMetroDTO> estacionesLineaMetroDTO) {
		estacionesLineaMetroDTO.iterator().forEachRemaining(estacionDTO -> {
			EstacionLineaMetroDTO estacionMetroDTO = findByEstacionLineaId(estacionDTO.getIdEstacion());
			estacionDTO.setLineaDTO(lineaMetroService.findByLineaMetroId(estacionDTO.getLineaDTO().getIdLinea()));
			if (!Utils.isNull(estacionMetroDTO)) {
				estacionDTO.setId(estacionMetroDTO.getId());
				updateEstacionMetro(estacionDTO);
			} else {
				saveEstacionMetro(estacionDTO);
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IEstacionLineaMetroService#procesarEstacion(co.gov.metropol.area247.model.EstacionLineaMetroDTO)
	 */
	@Override
	public void procesarEstacion(EstacionLineaMetroDTO estacionLineaMetroDTO) {
		EstacionLineaMetroDTO estacionMetroDTOAux = findByCodigoAndIdLinea(estacionLineaMetroDTO.getCodigo(),
				estacionLineaMetroDTO.getLineaDTO().getId());
		if (!Utils.isNull(estacionMetroDTOAux)) {
			estacionLineaMetroDTO.setId(estacionMetroDTOAux.getId());
			updateEstacionMetro(estacionLineaMetroDTO);
		} else {
			saveEstacionMetro(estacionLineaMetroDTO);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IEstacionLineaMetroService#findByCodigoAndIdLinea(java.lang.String, java.lang.Long)
	 */
	@Override
	public EstacionLineaMetroDTO findByCodigoAndIdLinea(String codigo, Long idLinea) {
		try {
			if (!Utils.isNull(codigo)) {
				EstacionMetro estacionMetro = estacionLineaMetroRepository.findByCodigoAndIdLinea(codigo, idLinea);
				return estacionLineaMetroMapper.toEstacionLineaMetroDTO(estacionMetro);
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar la estacion de la linea del metro por el codigo {}.", codigo, e);
			throw new Area247Exception(
					String.format("Error al consultar la estacion de la linea del metro por el codigo s%.", codigo), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IEstacionLineaMetroService#obtenerEstacionesCercanas(co.gov.metropol.area247.util.web.Coordenada, java.util.List)
	 */
	@Override
	public List<EstacionLineaMetroDTO> obtenerEstacionesCercanas(Coordenada coordenada, List<Long> modosRecorrido) {
		Assert.notNull(coordenada, "El argumento coordenada no debe ser nulo");
		
		try {
			
			List<EstacionMetro> estacionesCercanas = null;
			List<EstacionLineaMetroDTO> estacionesCercanasDTO = Lists.newArrayList();
			
			if (Utils.isNotEmpty(modosRecorrido)) {
				estacionesCercanas = estacionLineaMetroRepository.findByLocalizacion(modosRecorrido,
						coordenada.getLatitud(), coordenada.getLongitud(), coordenada.getRadio());
			} else {
				estacionesCercanas = estacionLineaMetroRepository
						.findByLocalizacion(coordenada.getLatitud(), coordenada.getLongitud(), coordenada.getRadio());
			}
			
			if (Utils.isNotEmpty(estacionesCercanas)) {
				estacionesCercanasDTO = estacionLineaMetroMapper.mapToEstacionLineaMetroDTO(estacionesCercanas);
				
				for (EstacionLineaMetroDTO estacionDTO : estacionesCercanasDTO) {
					conProfundizacion(estacionDTO);
					LineaMetroDTO lineaMetroDTO = estacionDTO.getLineaDTO();
					lineaMetroService.conHorarios(lineaMetroDTO);
					lineaMetroService.conFrecuencias(lineaMetroDTO);
				}
			}
			
			return estacionesCercanasDTO;
			
		} catch (Exception e) {
			
			throw new Area247Exception("Error al consultar las estaciones de las lineas definidas al punto dado. ", e);
			
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IEstacionLineaMetroService#findByIdLinea(java.lang.Long, boolean)
	 */
	@Override
	public List<EstacionLineaMetroDTO> findByIdLinea(Long idLinea, boolean profundizacion) {
		try {
			
			List<EstacionLineaMetroDTO> estacionesDTO = estacionLineaMetroMapper.mapToEstacionLineaMetroDTO(estacionLineaMetroRepository.findByIdLinea(idLinea));
			
			if (profundizacion) {
				for (EstacionLineaMetroDTO estacionLineaMetroDTO : estacionesDTO) {
					conProfundizacion(estacionLineaMetroDTO);
				}
			}
			
			return estacionesDTO;
			
		} catch (Exception e) {
			LOGGER.error("Error al consultar las estaciones del metro por el id de la linea {}.", idLinea, e);
			throw new Area247Exception(
					String.format("Error al consultar las estaciones del metro por el id de la linea %s.", idLinea), e);
		}
	}
	
	private void conProfundizacion(EstacionLineaMetroDTO estacionDTO) {
		
		if (!Utils.isNull(estacionDTO.getLineaDTO()) && !Utils.isNull(estacionDTO.getLineaDTO().getId())) {
			estacionDTO.setLineaDTO(lineaMetroService.findById(estacionDTO.getLineaDTO().getId(), false, false));
		}
		
//		if (!Utils.isNull(estacionDTO.getModoEstacionDTO()) && !Utils.isNull(estacionDTO.getModoEstacionDTO().getId())) {
//			estacionDTO.setModoEstacionDTO(modoEstacionService.findById(estacionDTO.getModoEstacionDTO().getId()));
//		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IEstacionLineaMetroService#getAllActivas()
	 */
	@Override
	public List<EstacionLineaMetroDTO> getAllActivas() {
		try {
			return estacionLineaMetroMapper.mapToEstacionLineaMetroDTO(estacionLineaMetroRepository.getAllActivas());
		} catch (Exception e) {
			throw new Area247Exception(PropertiesManager.obtenerCadena(Recursos.MENSAJES,
					"metro.service.excepcion.errorObtenerTodasEstaciones"));
		}
	}

}
