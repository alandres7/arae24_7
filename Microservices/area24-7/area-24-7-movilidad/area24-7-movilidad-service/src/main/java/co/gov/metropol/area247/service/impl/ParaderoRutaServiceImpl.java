package co.gov.metropol.area247.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

import co.gov.metropol.area247.model.ParaderoRutaMetroDTO;
import co.gov.metropol.area247.model.RutaMetroDTO;
import co.gov.metropol.area247.model.mapper.ParaderoRutaMetroMapper;
import co.gov.metropol.area247.repository.ParaderoRutaRepository;
import co.gov.metropol.area247.repository.domain.ParaderoRuta;
import co.gov.metropol.area247.service.IParaderoRutaService;
import co.gov.metropol.area247.service.IRutaMetroService;
import co.gov.metropol.area247.service.ITipoOrientacionService;
import co.gov.metropol.area247.service.ITipoParaderoService;
import co.gov.metropol.area247.util.PropertiesManager;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.constantes.Constantes.Recursos;
import co.gov.metropol.area247.util.ex.Area247Exception;
import co.gov.metropol.area247.util.web.Coordenada;

@Service
public class ParaderoRutaServiceImpl implements IParaderoRutaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ParaderoRutaServiceImpl.class);

	@Autowired
	private ParaderoRutaMetroMapper paraderoRutaMetroMapper;

	@Autowired
	private ParaderoRutaRepository paraderoRutaRepository;

	@Autowired
	private ITipoParaderoService tipoParaderoService;

	@Autowired
	private ITipoOrientacionService tipoOrientacionService;
	
	@Autowired
	private IRutaMetroService rutaMetroService;

	@Override
	public ParaderoRutaMetroDTO findByCodigoAndFuenteDatos(String codigo, Integer fuenteDatos) {
		try {
			ParaderoRuta paraderoRuta = paraderoRutaRepository.findByCodigoAndFuenteDatos(codigo, fuenteDatos);
			return paraderoRutaMetroMapper.toParaderoRutaMetroDTO(paraderoRuta);
		} catch (Exception e) {
			LOGGER.error(
					"Error al consultar el paradero de la ruta del metro por el codigo {} y la fuente de datos {}.",
					codigo, fuenteDatos, e);
			throw new Area247Exception(String.format(
					"Error al consultar el paradero de la ruta del metro por el codigo s% y la fuente de datos %s.",
					codigo, fuenteDatos), e);
		}
	}

	@Override
	public void saveParaderoRuta(ParaderoRutaMetroDTO paraderoRutaMetroDTO) {
		try {
			ParaderoRuta paraderoRuta = paraderoRutaMetroMapper.toParaderoRuta(paraderoRutaMetroDTO);

			if (!Utils.isNull(paraderoRutaMetroDTO.getTipoParaderoDTO())) {
				tipoParaderoService.procesarTipoParadero(paraderoRutaMetroDTO.getTipoParaderoDTO());
				paraderoRuta.setIdTipoParadero(paraderoRutaMetroDTO.getTipoParaderoDTO().getId());
			}

			if (!Utils.isNull(paraderoRutaMetroDTO.getTipoOrientacionDTO())) {
				tipoOrientacionService.procesarTipoOrientacion(paraderoRutaMetroDTO.getTipoOrientacionDTO());
				paraderoRuta.setIdTipoOrientacion(paraderoRutaMetroDTO.getTipoOrientacionDTO().getId());
			}

			paraderoRutaRepository.save(paraderoRuta);
			paraderoRutaMetroDTO.setId(paraderoRuta.getId());
		} catch (Exception e) {
			LOGGER.error("Error al tratar de guardar el paradero {}.", paraderoRutaMetroDTO, e);
			throw new Area247Exception(
					String.format("Error al tratar de guardar el paradero %s.", paraderoRutaMetroDTO), e);
		}

	}

	@Override
	public void updateParaderoRuta(ParaderoRutaMetroDTO paraderoRutaMetroDTO) {
		try {
			ParaderoRuta paraderoRuta = paraderoRutaMetroMapper.toParaderoRuta(paraderoRutaMetroDTO);

			if (!Utils.isNull(paraderoRutaMetroDTO.getTipoParaderoDTO())) {
				tipoParaderoService.procesarTipoParadero(paraderoRutaMetroDTO.getTipoParaderoDTO());
				paraderoRuta.setIdTipoParadero(paraderoRutaMetroDTO.getTipoParaderoDTO().getId());
			}

			if (!Utils.isNull(paraderoRutaMetroDTO.getTipoOrientacionDTO())) {
				tipoOrientacionService.procesarTipoOrientacion(paraderoRutaMetroDTO.getTipoOrientacionDTO());
				paraderoRuta.setIdTipoOrientacion(paraderoRutaMetroDTO.getTipoOrientacionDTO().getId());
			}

			paraderoRutaRepository.save(paraderoRuta);
		} catch (Exception e) {
			LOGGER.error("Error al tratar de actualizar el paradero {}.", paraderoRutaMetroDTO, e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar el paradero %s.", paraderoRutaMetroDTO), e);
		}

	}

	@Override
	public ParaderoRutaMetroDTO findById(Long id) {
		try {
			return paraderoRutaMetroMapper.toParaderoRutaMetroDTO(paraderoRutaRepository.findOne(id));
		} catch (Exception e) {
			LOGGER.error("Error al consultar el paradero del metro por el id {}.", id, e);
			throw new Area247Exception(String.format("Error al consultar el paradero del metro por el id %s.", id), e);
		}
	}

	@Override
	public void procesarParadero(ParaderoRutaMetroDTO paraderoDTO) {
		ParaderoRutaMetroDTO paraderoDTOAux = findByCodigoAndFuenteDatos(paraderoDTO.getCodigo(),
				paraderoDTO.getFuenteDatos());
		if (!Utils.isNull(paraderoDTOAux)) {
			paraderoDTO.setId(paraderoDTOAux.getId());
			updateParaderoRuta(paraderoDTO);
		} else {
			saveParaderoRuta(paraderoDTO);
		}

	}

	@Override
	public List<ParaderoRutaMetroDTO> obtenerParaderosCercanos(Coordenada coordenada, List<Long> modosRecorrido) {
		Assert.notNull(coordenada, "El argumento coordenada no debe ser nulo");

		try {

			List<ParaderoRuta> paraderosCercanos = null;
			List<ParaderoRutaMetroDTO> paraderosCercanosDTO = Lists.newArrayList();

			if (Utils.isNotEmpty(modosRecorrido)) {
				paraderosCercanos = paraderoRutaRepository.findByLocalizacion(modosRecorrido, coordenada.getLatitud(), coordenada.getLongitud(),
						coordenada.getRadio());
			} else {
				paraderosCercanos = paraderoRutaRepository.findByLocalizacion(coordenada.getLatitud(), coordenada.getLongitud(),
						coordenada.getRadio());
			}

			if (Utils.isNotEmpty(paraderosCercanos)) {
				paraderosCercanosDTO = paraderoRutaMetroMapper.mapToParaderoRutaMetroDTO(paraderosCercanos);
				
				for (ParaderoRutaMetroDTO paraderoDTO : paraderosCercanosDTO) {
					conProfundizacion(paraderoDTO);
					RutaMetroDTO rutaMetroDTO = paraderoDTO.getRutaDTO();
					rutaMetroService.conHorarios(rutaMetroDTO);
					rutaMetroService.conFrecuencias(rutaMetroDTO);
				}
			}

			return paraderosCercanosDTO;

		} catch (Exception e) {

			throw new Area247Exception("Ocurrió un error al momento de obtener los paraderos cercanos");

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.gov.metropol.area247.service.IParaderoRutaService#findByIdRuta(java.
	 * lang.Long)
	 */
	@Override
	public List<ParaderoRutaMetroDTO> findByIdRuta(Long idRuta, boolean m2o) {
		try {
			List<ParaderoRutaMetroDTO> paraderosDTO = paraderoRutaMetroMapper.mapToParaderoRutaMetroDTO(paraderoRutaRepository.findByIdRuta(idRuta));
			
			if (m2o) {
				for (ParaderoRutaMetroDTO paraderoDTO : paraderosDTO) {
					conProfundizacion(paraderoDTO);
				}
			}
			
			return paraderosDTO;
		} catch (Exception e) {
			LOGGER.error("Error al consultar el paradero del metro por el id de la ruta {}.", idRuta, e);
			throw new Area247Exception(
					String.format("Error al consultar el paradero del metro por el id de la ruta %s.", idRuta), e);
		}
	}

	private void conProfundizacion(ParaderoRutaMetroDTO paraderoDTO) {
		
		if (!Utils.isNull(paraderoDTO.getRutaDTO()) && !Utils.isNull(paraderoDTO.getRutaDTO().getId())) {
			paraderoDTO.setRutaDTO(rutaMetroService.findById(paraderoDTO.getRutaDTO().getId(), false, false));
		}
		
		if (!Utils.isNull(paraderoDTO.getTipoParaderoDTO()) && !Utils.isNull(paraderoDTO.getTipoParaderoDTO().getId())) {
			paraderoDTO.setTipoParaderoDTO(tipoParaderoService.findById(paraderoDTO.getTipoParaderoDTO().getId()));
		}
		
		if (!Utils.isNull(paraderoDTO.getTipoOrientacionDTO()) && !Utils.isNull(paraderoDTO.getTipoOrientacionDTO().getId())) {
			paraderoDTO.setTipoOrientacionDTO(tipoOrientacionService.findById(paraderoDTO.getTipoOrientacionDTO().getId()));
		}
		
	}

	@Override
	public List<ParaderoRutaMetroDTO> findAllActivas() {
		try {
			return paraderoRutaMetroMapper.mapToParaderoRutaMetroDTO(paraderoRutaRepository.findAllActivas());
		} catch (Exception e) {
			throw new Area247Exception(PropertiesManager.obtenerCadena(Recursos.MENSAJES,
					"metro.service.excepcion.errorObtenerTodosParaderos"));
		}
	}
	
	@Override
	public List<ParaderoRutaMetroDTO> findByFuenteDatos(Integer... fuenteDatos) {
		try {
			return paraderoRutaMetroMapper.mapToParaderoRutaMetroDTO(
					paraderoRutaRepository.findByFuenteDatos(Lists.newArrayList(fuenteDatos)));
		} catch (Exception e) {
			throw new Area247Exception(PropertiesManager.obtenerCadena(Recursos.MENSAJES,
					"metro.service.excepcion.errorObtenerTodosParaderos"));
		}
	}

}
