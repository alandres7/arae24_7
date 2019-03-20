package co.gov.metropol.area247.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import co.gov.metropol.area247.model.RutaMetroDTO;
import co.gov.metropol.area247.model.mapper.RutaMetroMapper;
import co.gov.metropol.area247.repository.RutaRepository;
import co.gov.metropol.area247.repository.domain.Ruta;
import co.gov.metropol.area247.service.IFrecuenciaRutaMetroService;
import co.gov.metropol.area247.service.IHorarioRutaService;
import co.gov.metropol.area247.service.IParaderoRutaService;
import co.gov.metropol.area247.service.IRutaMetroService;
import co.gov.metropol.area247.service.ITipoModoTransporteService;
import co.gov.metropol.area247.service.ITipoRutaService;
import co.gov.metropol.area247.service.ITipoSistemaRutaService;
import co.gov.metropol.area247.util.PropertiesManager;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.constantes.Constantes.Recursos;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class RutaMetroServiceImpl implements IRutaMetroService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RutaMetroServiceImpl.class);

	@Autowired
	private RutaMetroMapper mapper;

	@Autowired
	private RutaRepository rutaRepository;

	@Autowired
	private ITipoRutaService tipoRutaService;

	@Autowired
	private ITipoModoTransporteService modoTransporteService;

	@Autowired
	private ITipoSistemaRutaService tipoSistemaRutaService;
	
	@Autowired
	private IHorarioRutaService horarioRutaService;
	
	@Autowired
	private IFrecuenciaRutaMetroService frecuenciaRutaMetroService;
	
	@Autowired
	private IParaderoRutaService paraderoRutaService;

	@Override
 	public RutaMetroDTO findByRutaMetroId(Long idRuta) {
		try {
			Ruta rutaMetro = rutaRepository.findByIdItem(idRuta);
			return mapper.toRutaMetroDTO(rutaMetro);
		} catch (Exception e) {
			LOGGER.error("Error al consultar la ruta del metro por el id {}.", idRuta, e);
			throw new Area247Exception(String.format("Error al consultar la ruta del metro por el id s%.", idRuta), e);
		}
	}

	@Override
	public void saveRuta(RutaMetroDTO rutaMetroDTO) {
		try {
			Ruta rutaMetro = mapper.toRuta(rutaMetroDTO);

			if (!Utils.isNull(rutaMetroDTO.getTipoRutaDTO())) {
				tipoRutaService.procesarTipoRuta(rutaMetroDTO.getTipoRutaDTO());
				rutaMetro.setIdTipoRuta(rutaMetroDTO.getTipoRutaDTO().getId());
			}

//			if (!Utils.isNull(rutaMetroDTO.getModoRutaDTO())) {
//				modoTransporteService.procesarTipoModoTransporte(rutaMetroDTO.getModoRutaDTO());
//				rutaMetro.setIdModoRuta(rutaMetroDTO.getModoRutaDTO().getId());
//			}

		/*	if (!Utils.isNull(rutaMetroDTO.getTipoSistemaRutaDTO())) {
				tipoSistemaRutaService.procesarTipoSistemaRuta(rutaMetroDTO.getTipoSistemaRutaDTO());
				rutaMetro.setIdTipoSistemaRuta(rutaMetroDTO.getTipoSistemaRutaDTO().getId());
			}*/

			rutaRepository.save(rutaMetro);
			rutaMetroDTO.setId(rutaMetro.getId());
		} catch (Exception e) {
			LOGGER.error("Error al tratar de guardar la ruta {}.", rutaMetroDTO, e);
			throw new Area247Exception(String.format("Error al tratar de guardar la ruta %s.", rutaMetroDTO), e);
		}

	}

	@Override
	public void updateRuta(RutaMetroDTO rutaMetroDTO) {
		try {
			Ruta rutaMetro = mapper.toRuta(rutaMetroDTO);

			if (!Utils.isNull(rutaMetroDTO.getTipoRutaDTO())) {
				tipoRutaService.procesarTipoRuta(rutaMetroDTO.getTipoRutaDTO());
				rutaMetro.setIdTipoRuta(rutaMetroDTO.getTipoRutaDTO().getId());
			}

//			if (!Utils.isNull(rutaMetroDTO.getModoRutaDTO())) {
//				modoTransporteService.procesarTipoModoTransporte(rutaMetroDTO.getModoRutaDTO());
//				rutaMetro.setIdModoRuta(rutaMetroDTO.getModoRutaDTO().getId());
//			}

			/*if (!Utils.isNull(rutaMetroDTO.getTipoSistemaRutaDTO())) {
				tipoSistemaRutaService.procesarTipoSistemaRuta(rutaMetroDTO.getTipoSistemaRutaDTO());
				rutaMetro.setIdTipoSistemaRuta(rutaMetroDTO.getTipoSistemaRutaDTO().getId());
			}*/

			rutaRepository.save(rutaMetro);
		} catch (Exception e) {
			LOGGER.error("Error al tratar de actualizar la ruta {}.", rutaMetroDTO, e);
			throw new Area247Exception(String.format("Error al tratar de actualizar la ruta %s.", rutaMetroDTO), e);
		}

	}

	@Override
	public RutaMetroDTO findById(Long id, boolean m2o, boolean o2m) {
		try {
			RutaMetroDTO rutaMetroDTO = mapper.toRutaMetroDTO(rutaRepository.findOne(id));
			
			if (m2o)
				conProfundizacion(rutaMetroDTO);
		
			return rutaMetroDTO;
		} catch (Exception e) {
			LOGGER.error("Error al consultar la ruta por el id {}.", id, e);
			throw new Area247Exception(String.format("Error al consultar la ruta por el id %s.", id), e);
		}
	}

	@Override
	public void procesarRutas(List<RutaMetroDTO> rutasMetroDTO) {
		rutasMetroDTO.iterator().forEachRemaining(rutaDTO -> {
			RutaMetroDTO rutaMetroDTO = findByIdItemAndFuenteDatos(rutaDTO.getIdItem(), rutaDTO.getFuenteDatos());
			if (!Utils.isNull(rutaMetroDTO)) {
				rutaDTO.setId(rutaMetroDTO.getId());
				updateRuta(rutaDTO);
			} else {
				saveRuta(rutaDTO);
			}
		});
	}

	@Override
	public void procesarRuta(RutaMetroDTO rutaMetroDTO) {
		RutaMetroDTO rutaMetroDTOAux = findByCodigoAndFuenteDatos(rutaMetroDTO.getCodigo(),
				rutaMetroDTO.getFuenteDatos());
		if (!Utils.isNull(rutaMetroDTOAux)) {
			rutaMetroDTO.setId(rutaMetroDTOAux.getId());
			updateRuta(rutaMetroDTO);
		} else {
			saveRuta(rutaMetroDTO);
		}

	}

	@Override
	public RutaMetroDTO findByIdItemAndFuenteDatos(Long idItem, Integer fuenteDatos) {
		try {
			if (!Utils.isNull(idItem) && !Utils.isNull(fuenteDatos)) {
				Ruta rutaMetro = rutaRepository.findByIdItem_idFuenteDatos(idItem, fuenteDatos);
				return mapper.toRutaMetroDTO(rutaMetro);
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar la ruta del metro por el idItem {} y la fuente de datos {}.", idItem,
					fuenteDatos, e);
			throw new Area247Exception(
					String.format("Error al consultar la ruta del metro por el idItem %s y la fuente de datos %s.",
							idItem, fuenteDatos),
					e);
		}
	}

	@Override
	public RutaMetroDTO findByCodigoAndFuenteDatos(String codigo, Integer fuenteDatos) {
		try {
			if (!Utils.isNull(codigo) && !Utils.isNull(fuenteDatos)) {
				Ruta rutaMetro = rutaRepository.findByCodigoAndFuenteDatos(codigo, fuenteDatos);
				return mapper.toRutaMetroDTO(rutaMetro);
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar la ruta del metro por el codigo {} y la fuente de datos {}.", codigo,
					fuenteDatos, e);
			throw new Area247Exception(
					String.format("Error al consultar la ruta del metro por el codigo %s y la fuente de datos %s.",
							codigo, fuenteDatos),
					e);
		}
	}

	@Override
	public RutaMetroDTO findByCodigo(String codigo) {
		try {
			if (!Utils.isNull(codigo)) {
				Ruta rutaMetro = rutaRepository.findByCodigo(codigo);
				return mapper.toRutaMetroDTO(rutaMetro);
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("Error al consultar la ruta del metro por el codigo {}.", codigo, e);
			throw new Area247Exception(
					String.format("Error al consultar la ruta del metro por el codigo %s.",codigo), e);
		}
	}

	public void conProfundizacion(RutaMetroDTO rutaMetroDTO) {
		
		if (!Utils.isNull(rutaMetroDTO.getTipoRutaDTO()) && !Utils.isNull(rutaMetroDTO.getTipoRutaDTO().getId())) {
			rutaMetroDTO.setTipoRutaDTO(tipoRutaService.findById(rutaMetroDTO.getTipoRutaDTO().getId()));
		}
		
		/*if (!Utils.isNull(rutaMetroDTO.getTipoSistemaRutaDTO()) && !Utils.isNull(rutaMetroDTO.getTipoSistemaRutaDTO().getId())) {
			rutaMetroDTO.setTipoSistemaRutaDTO(tipoSistemaRutaService.findById(rutaMetroDTO.getTipoSistemaRutaDTO().getId()));
		}*/
		
	}
	
	@Override
	public void conListasSecundarias(RutaMetroDTO rutaMetroDTO) {
		conHorarios(rutaMetroDTO);
		conFrecuencias(rutaMetroDTO);
		conParaderos(rutaMetroDTO);
	}
	
	@Override
	public void conHorarios(RutaMetroDTO rutaMetroDTO) {
		rutaMetroDTO.setHorariosDTO(horarioRutaService.findByIdRuta(rutaMetroDTO.getId()));
		
	}

	@Override
	public void conFrecuencias(RutaMetroDTO rutaMetroDTO) {
		rutaMetroDTO.setFrecuenciasDTO(frecuenciaRutaMetroService.findByIdRuta(rutaMetroDTO.getId()));
	}

	
	@Override
	public void conParaderos(RutaMetroDTO rutaMetroDTO) {
		rutaMetroDTO.setParaderosDTO(paraderoRutaService.findByIdRuta(rutaMetroDTO.getId(), false));
	}

	@Override
	public List<RutaMetroDTO> findAllActivas() {
		try {
			return mapper.mapToRutaMetroDTO(rutaRepository.getAllActivas());
		} catch (Exception e) {
			throw new Area247Exception(PropertiesManager.obtenerCadena(Recursos.MENSAJES,
					"metro.service.excepcion.errorObtenerTodasRutas"));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IRutaMetroService#findByFuenteDatos(java.lang.Integer[])
	 */
	@Override
	public List<RutaMetroDTO> findByFuenteDatos(Integer... fuenteDatos) {
		try {
			return mapper.mapToRutaMetroDTO(rutaRepository.findByFuenteDatos(Lists.newArrayList(fuenteDatos)));
		} catch (Exception e) {
			throw new Area247Exception(PropertiesManager.obtenerCadena(Recursos.MENSAJES,
					"metro.service.excepcion.errorObtenerPorFuenteDatos"));
		}
	}

	@Override
	public List<RutaMetroDTO> findByCodigoAndFuenteDatos(List<String> codigos, Integer fuenteDatos) {
		try {
			return mapper.mapToRutaMetroDTO(rutaRepository.findListByCodigoAndFuenteDatos(codigos, fuenteDatos));
		} catch (Exception e) {
			throw new Area247Exception(PropertiesManager.obtenerCadena(Recursos.MENSAJES,
					"metro.service.excepcion.errorObtenerPorFuenteDatos"));
		}
	}

}
