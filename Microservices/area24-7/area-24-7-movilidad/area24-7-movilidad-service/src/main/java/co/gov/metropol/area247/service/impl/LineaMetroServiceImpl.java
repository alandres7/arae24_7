package co.gov.metropol.area247.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.model.TareviaEstacionMetroDTO;
import co.gov.metropol.area247.model.mapper.EstacionLineaMetroMapper;
import co.gov.metropol.area247.model.mapper.FrecuenciaLineaMetroMapper;
import co.gov.metropol.area247.model.mapper.HorarioLineaMetroMapper;
import co.gov.metropol.area247.model.mapper.LineaMetroMapper;
import co.gov.metropol.area247.repository.EstacionLineaMetroRepository;
import co.gov.metropol.area247.repository.FrecuenciaLineaMetroRepository;
import co.gov.metropol.area247.repository.HorarioLineaMetroRepository;
import co.gov.metropol.area247.repository.LineaMetroRepository;
import co.gov.metropol.area247.repository.domain.EstacionMetro;
import co.gov.metropol.area247.repository.domain.LineaMetro;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;
import co.gov.metropol.area247.service.IEstacionLineaMetroService;
import co.gov.metropol.area247.service.IFrecuenciaLineaMetroService;
import co.gov.metropol.area247.service.IHorarioLineaMetroService;
import co.gov.metropol.area247.service.ILineaMetroService;
import co.gov.metropol.area247.service.ITipoLineaService;
import co.gov.metropol.area247.util.PropertiesManager;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.constantes.Constantes.Recursos;
import co.gov.metropol.area247.util.ex.Area247Exception;
import co.gov.metropol.area247.util.web.Coordenada;

@Service
public class LineaMetroServiceImpl implements ILineaMetroService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LineaMetroServiceImpl.class);

	@Autowired
	private LineaMetroMapper mapper;

	@Autowired
	private HorarioLineaMetroMapper horioLineaMapper;

	@Autowired
	private FrecuenciaLineaMetroMapper frecuenciaLineaMapper;

	@Autowired
	private EstacionLineaMetroMapper estacionLineaMapper;

	@Autowired
	private IEstacionLineaMetroService estacionLineaMetroService;

	@Autowired
	private LineaMetroRepository lineaMetroRepository;

	@Autowired
	private FrecuenciaLineaMetroRepository frecuenciaLineaMetroRepository;

	@Autowired
	private HorarioLineaMetroRepository horiarioLineaMetroRepository;

	@Autowired
	private EstacionLineaMetroRepository estacionLineaMetroRepository;

	@Autowired
	private ITipoLineaService tipoLineaService;

	@Autowired
	private IFrecuenciaLineaMetroService frecuenciaLineaMetroService;

	@Autowired
	private IHorarioLineaMetroService horarioLineaMetroService;

	@Override
	public LineaMetroDTO findByLineaMetroId(Long idLinea) {
		try {
			LineaMetro lineaMetro = lineaMetroRepository.findByIdLinea(idLinea);
			return mapper.toLineaMetroDTO(lineaMetro);
		} catch (Exception e) {
			LOGGER.error("Error al consultar la linea del metro por el id {}.", idLinea, e);
			throw new Area247Exception(String.format("Error al consultar la linea del metro por el id %s.", idLinea),
					e);
		}
	}

	@Override
	public LineaMetro saveLineaMetro(LineaMetroDTO lineaMetroDTO) {
		try {

			LineaMetro lineaMetro = mapper.toLineaMetro(lineaMetroDTO);
			lineaMetroRepository.save(lineaMetro);
			lineaMetroDTO.setId(lineaMetro.getId());
			return lineaMetro;

		} catch (Exception e) {
			LOGGER.error("Error al tratar de guardar la estacion {}.", lineaMetroDTO, e);
			throw new Area247Exception(String.format("Error al tratar de guardar la estacion %s.", lineaMetroDTO), e);
		}

	}

	@Override
	public LineaMetro updateLineaMetro(LineaMetroDTO lineaMetroDTO) {
		try {

			LineaMetro lineaMetro = mapper.toLineaMetro(lineaMetroDTO);
			return lineaMetroRepository.save(lineaMetro);

		} catch (Exception e) {
			LOGGER.error("Error al tratar de actualizar la linea {}.", lineaMetroDTO, e);
			throw new Area247Exception(String.format("Error al tratar de actualizar la linea %s.", lineaMetroDTO), e);
		}

	}

	@Override
	public LineaMetroDTO findById(Long id, boolean m2o, boolean o2m) {
		try {

			LineaMetroDTO lineaMetroDTO = mapper.toLineaMetroDTO(lineaMetroRepository.findOne(id));

			if (m2o)
				conProfundizacion(lineaMetroDTO);

			if (o2m)
				conListasSecundarias(lineaMetroDTO);

			return lineaMetroDTO;

		} catch (Exception e) {
			LOGGER.error("Error al consultar la linea por el id {}.", id, e);
			throw new Area247Exception(String.format("Error al consultar la linea por el id %s.", id), e);
		}
	}

	@Override
	public void procesarLineas(List<LineaMetroDTO> lineasMetroDTO) {
		lineasMetroDTO.iterator().forEachRemaining(lineaDTO -> {
			LineaMetroDTO lineaMetroDTO = findByLineaMetroId(lineaDTO.getIdLinea());
			if (!Utils.isNull(lineaMetroDTO)) {
				lineaDTO.setId(lineaMetroDTO.getId());
				updateLineaMetro(lineaDTO);
			} else {
				saveLineaMetro(lineaDTO);
			}
		});
	}

	@Override
	public LineaMetro procesarLinea(LineaMetroDTO lineaMetroDTO) {
		LineaMetroDTO lineaMetroDTOAux = findByCodigo(lineaMetroDTO.getCodigo());
		if (!Utils.isNull(lineaMetroDTOAux)) {
			lineaMetroDTO.setId(lineaMetroDTOAux.getId());
			return updateLineaMetro(lineaMetroDTO);
		} else {
			return saveLineaMetro(lineaMetroDTO);
		}
	}

	@Override
	public List<LineaMetroDTO> findByCodigoOrDescripcion(String parametro) {

		List<LineaMetroDTO> lineasMetroDTO = mapper
				.mapToLineaMetroDTO(lineaMetroRepository.findByCodigoOrDescripcion(parametro));

		lineasMetroDTO.iterator().forEachRemaining(lineaMetroDTO -> {

			lineaMetroDTO.setFrecuenciaLineaMetro(frecuenciaLineaMapper
					.mapToFrecuenciaLineaMetroDTO(frecuenciaLineaMetroRepository.findByIdLinea(lineaMetroDTO.getId())));

			lineaMetroDTO.setHorarioLineaMetro(horioLineaMapper
					.mapToHorarioLineaMetroDTO(horiarioLineaMetroRepository.findByIdLinea(lineaMetroDTO.getId())));

			lineaMetroDTO.setEstacionLineaMetro(estacionLineaMapper
					.mapToEstacionLineaMetroDTO(estacionLineaMetroRepository.findByIdLinea(lineaMetroDTO.getId())));

		});

		return lineasMetroDTO;
	}

	@Override
	public LineaMetroDTO findByCodigo(String codigo) {
		try {
			LineaMetro lineaMetro = lineaMetroRepository.findByCodigo(codigo);
			return mapper.toLineaMetroDTO(lineaMetro);
		} catch (Exception e) {
			LOGGER.error("Error al consultar la linea del metro por el codigo {}.", codigo, e);
			throw new Area247Exception(String.format("Error al consultar la linea del metro por el codigo %s.", codigo),
					e);
		}
	}

	@Override
	public List<TareviaEstacionMetroDTO> obtenerEstacionesMetroCercanas(Double latitud, Double longitud, Float radio) {
		List<EstacionMetro> estacionesMetro = estacionLineaMetroRepository.findByLocalizacion(latitud, longitud, radio);
		List<TareviaEstacionMetroDTO> estacionesMetroDTO = new ArrayList<>();
		for (int i = 0; i < estacionesMetro.size(); i++) {
			TareviaEstacionMetroDTO tareviaEstacionMetro = new TareviaEstacionMetroDTO(estacionesMetro.get(i));
			estacionesMetroDTO.add(tareviaEstacionMetro);
		}
		return estacionesMetroDTO;
	}

	@Override
	public List<LineaMetroDTO> obtenerlineasCercanas(Coordenada coordenada, List<ModoRecorrido> modosRecorrido) {

		Assert.notNull(coordenada, "El argumento coordenada no debe ser nulo");

		try {

			List<LineaMetro> lineasCercanas = null;
			List<LineaMetroDTO> lineasCercanosDTO = Lists.newArrayList();

			if (Utils.isNull(modosRecorrido)) {
				lineasCercanas = lineaMetroRepository.lineaCercana(coordenada.getLatitud(), coordenada.getLongitud(),
						coordenada.getRadio());
			} else {
				List<Long> modosRecorridoLong = obtenerIdentificadoresModoRecorrido(modosRecorrido);

				lineasCercanas = lineaMetroRepository.lineaCercana(coordenada.getLatitud(), coordenada.getLongitud(),
						coordenada.getRadio(), modosRecorridoLong);
			}

			if (Utils.isNotEmpty(lineasCercanas)) {

				lineasCercanosDTO = mapper.mapToLineaMetroDTO(lineasCercanas);

				for (LineaMetroDTO lineaMetroDTO : lineasCercanosDTO) {
					lineaMetroDTO.setFrecuenciaLineaMetro(
							frecuenciaLineaMetroService.findByIdLinea(lineaMetroDTO.getId(), false));
					lineaMetroDTO.setHorarioLineaMetro(horarioLineaMetroService.findByIdLinea(lineaMetroDTO.getId()));
				}
			}

			return lineasCercanosDTO;

		} catch (Exception e) {

			throw new Area247Exception("Ocurrió un error al momento de obtener las lineas cercanas");

		}

	}

	private List<Long> obtenerIdentificadoresModoRecorrido(List<ModoRecorrido> modosRecorrido) {
		List<Long> identificadores = Lists.newArrayList();

		if (Utils.isNotEmpty(modosRecorrido)) {
			for (ModoRecorrido modo : modosRecorrido) {
				Long identificador = Long.valueOf(modo.indice());
				identificadores.add(identificador);
			}
		}

		return identificadores;
	}

	public void conProfundizacion(LineaMetroDTO lineaMetroDTO) {

		if (!Utils.isNull(lineaMetroDTO.getTipoLinea()) && !Utils.isNull(lineaMetroDTO.getTipoLinea().getId())) {
			lineaMetroDTO.setTipoLinea(tipoLineaService.findById(lineaMetroDTO.getTipoLinea().getId()));
		}

	}

	public void conListasSecundarias(LineaMetroDTO lineaMetroDTO) {
		conFrecuencias(lineaMetroDTO);
		conHorarios(lineaMetroDTO);
		conEstaciones(lineaMetroDTO);
	}

	@Override
	public void conFrecuencias(LineaMetroDTO lineaMetroDTO) {
		lineaMetroDTO.setFrecuenciaLineaMetro(frecuenciaLineaMetroService.findByIdLinea(lineaMetroDTO.getId(), false));
	}

	@Override
	public void conHorarios(LineaMetroDTO lineaMetroDTO) {
		lineaMetroDTO.setHorarioLineaMetro(horioLineaMapper
				.mapToHorarioLineaMetroDTO(horiarioLineaMetroRepository.findByIdLinea(lineaMetroDTO.getId())));
	}

	@Override
	public void conEstaciones(LineaMetroDTO lineaMetroDTO) {
		lineaMetroDTO.setEstacionLineaMetro(estacionLineaMetroService.findByIdLinea(lineaMetroDTO.getId(), false));

	}

	@Override
	public List<LineaMetroDTO> findAllActivas() {
		try {
			return mapper.mapToLineaMetroDTO(lineaMetroRepository.getAllActivas());
		} catch (Exception e) {
			throw new Area247Exception(PropertiesManager.obtenerCadena(Recursos.MENSAJES,
					"metro.service.excepcion.errorObtenerTodasLineas"));
			
		}
	}
	
	

}
