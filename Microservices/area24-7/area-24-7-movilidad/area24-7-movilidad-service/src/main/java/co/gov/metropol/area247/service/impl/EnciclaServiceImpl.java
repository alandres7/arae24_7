package co.gov.metropol.area247.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.annotations.Log;
import co.gov.metropol.area247.annotations.LogReturnValue;
import co.gov.metropol.area247.gateway.IEnciclaServiceGateway;
import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.model.CiclorutaDTO;
import co.gov.metropol.area247.model.DareviaZonaDTO;
import co.gov.metropol.area247.model.DisponibilidadCiclaDTO;
import co.gov.metropol.area247.model.EstacionEnciclaDTO;
import co.gov.metropol.area247.model.ModoEstacionDTO;
import co.gov.metropol.area247.model.TareviaEstacionEnciclaDTO;
import co.gov.metropol.area247.model.ZonaCiudadEnciclaDTO;
import co.gov.metropol.area247.repository.domain.Cicloruta;
import co.gov.metropol.area247.repository.domain.DareviaZona;
import co.gov.metropol.area247.repository.domain.TareviaEstacionEncicla;
import co.gov.metropol.area247.service.IEnciclaService;
import co.gov.metropol.area247.service.IModoEstacionService;
import co.gov.metropol.area247.service.impl.abstracts.AbstractEnciclaService;
import co.gov.metropol.area247.services.rest.encicla.EnciclaWSDTO;
import co.gov.metropol.area247.services.rest.encicla.FeatureWSDTO;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class EnciclaServiceImpl extends AbstractEnciclaService implements IEnciclaService {

	@Autowired
	private IEnciclaServiceGateway enciclaServiceGateway;
	
	@Autowired
	private IModoEstacionService modoEstacionService;
	
	private ModoEstacionDTO modoEstacionDTO;

	@Override
	public void cargarEstacionesEncicla() {
		try {
			EnciclaWSDTO enciclaWSTO = enciclaServiceGateway.consultarEstatusEncicla();
			List<ZonaCiudadEnciclaDTO> zonasEncicla = enciclaWSTO.getZonasCiudadEncicla();
			modoEstacionDTO = modoEstacionService.findByNombre("ENCICLA");
			Long ultimaConsulta = enciclaWSTO.getDate();
			if (!Utils.isEmpty(zonasEncicla)) {
				zonasEncicla.forEach(zonaCiudadEncicla -> {
					procesarZonasEncicla(zonaCiudadEncicla, ultimaConsulta);
					procesarEstacionesEncicla(zonaCiudadEncicla.getEstacionesEncicla(), zonaCiudadEncicla,
							ultimaConsulta);
				});
			}
		} catch (Exception e) {
			LoggingUtil.logException("Error al consumir el servicio de encicla.", e);
			throw new Area247Exception("Error al consumir el servicio de encicla.", e);
		}

	}
	
	@Override
	public void cargarCicloRutas() {
		try {
			List<FeatureWSDTO> features = enciclaServiceGateway.consultarCicloRutas();
			features.forEach(feature -> {
				procesarCicloruta(feature.getCicloRutaDTO());
			});
			
		} catch (Exception e) {
			LoggingUtil.logException("Error al procesar cicloRutas.", e);
			throw new Area247Exception("Error al procesar cicloRutas.", e);
		}
		
	}
	
	private void procesarCicloruta(CiclorutaDTO cicloRutaDTO){
		CiclorutaDTO itemCicloRuta = findCicloRutaById(cicloRutaDTO.getIdItem());
		if (!Utils.isNull(itemCicloRuta)) {
			updateCicloRura(itemCicloRuta);
		} else {
			saveCicloRuta(cicloRutaDTO);
		}
	}
	
	@Override
	public CiclorutaDTO findCicloRutaById(Long idItem) {
		try {
			return mapper.toCicloRutaDTO(cicloRutaRepository.findByIdItem(idItem));
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al consultar la cicloRuta por el idItem s% de encicla.", idItem), e);
			throw new Area247Exception(
					String.format("Error al consultar la cicloRuta por el idItem s% de encicla.", idItem), e);
		}
	}
	
	@Override
	public void saveCicloRuta(CiclorutaDTO cicloRutaDTO) {
		try {
			Cicloruta cicloRuta = mapper.toCicloRuta(cicloRutaDTO);
			cicloRutaRepository.save(cicloRuta);
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de guardar la cicloRuta %s.", cicloRutaDTO), e);
			throw new Area247Exception(
					String.format("Error al tratar de guardar la cicloRuta %s.", cicloRutaDTO), e);
		}
	}

	@Override
	public void updateCicloRura(CiclorutaDTO ciclorutaDTO) {
		try {
			Cicloruta cicloRuta = mapper.toCicloRuta(ciclorutaDTO);
			cicloRutaRepository.save(cicloRuta);
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de actualizar la cicloRuta %s.", ciclorutaDTO), e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar la cicloRuta %s.", ciclorutaDTO), e);
		}		
	}

	@Override
	@LogReturnValue
	public EstacionEnciclaDTO findByEstacionId(@Log Long idEstacion) {
		try {
			return mapper.toEstacionEnciclaDTO(repository.findByIdEstacion(idEstacion));
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al consultar la estacion por el id s% de encicla.", idEstacion), e);
			throw new Area247Exception(
					String.format("Error al consultar la estacion por el id s% de encicla.", idEstacion), e);
		}
	}

	@Override
	@LogReturnValue
	public DisponibilidadCiclaDTO findAvailabilityByEstacionId(@Log Long idEstacion) {
		try {
			TareviaEstacionEnciclaDTO estacion = mapper
					.toTareviaEstacionEnciclaDTO(EstacionRepository.findByIdEstacionEncicla(idEstacion));
			DisponibilidadCiclaDTO disponibilidad = new DisponibilidadCiclaDTO();
			disponibilidad.setIdEstacionEncicla(estacion.getIdEstacionEncicla());
			disponibilidad.setCantidadBicicletas(estacion.getCantidadBicicletas());
			disponibilidad.setPuestosEstacionEncila(estacion.getLugares());
			return disponibilidad;
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al consultar la estacion por el id s% de encicla.", idEstacion), e);
			throw new Area247Exception(
					String.format("Error al consultar la estacion por el id s% de encicla.", idEstacion), e);
		}
	}

	@Override
	public void saveEstacionEncicla(@Log TareviaEstacionEnciclaDTO tareviaEstacionEnciclaDTO) {
		try {
			TareviaEstacionEncicla tareviaEstacionEncicla = mapper.toTareviaEstacionEstacion(tareviaEstacionEnciclaDTO);
			EstacionRepository.save(tareviaEstacionEncicla);
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de guardar la estacion %s.", tareviaEstacionEnciclaDTO), e);
			throw new Area247Exception(
					String.format("Error al tratar de guardar la estacion %s.", tareviaEstacionEnciclaDTO), e);
		}
	}

	@Override
	public void updateEstacionEncicla(@Log TareviaEstacionEnciclaDTO tareviaEstacionEnciclaDTO, Long ultimaConsulta) {
		try {
			TareviaEstacionEncicla tareviaEstacionEncicla = mapper.toTareviaEstacionEstacion(tareviaEstacionEnciclaDTO);
			Date date = new Date(ultimaConsulta * 1000L);
			tareviaEstacionEncicla.setUltimaModificacion(date);
			EstacionRepository.save(tareviaEstacionEncicla);
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de actualizar la estacion %s.", tareviaEstacionEnciclaDTO), e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar la estacion %s.", tareviaEstacionEnciclaDTO), e);
		}
	}

	private void procesarEstacionesEncicla(@Log List<EstacionEnciclaDTO> estacionesEncicla,
			ZonaCiudadEnciclaDTO zonaEncicla, Long ultimaConsulta) {
		estacionesEncicla.forEach(estacion -> {
			TareviaEstacionEnciclaDTO tareviaEstacionEnciclaDTO = findByEstacionEnciclaId(estacion.getIdEstacion());
			if (!Utils.isNull(tareviaEstacionEnciclaDTO)) {
				tareviaEstacionEnciclaDTO.setCantidadBicicletas(estacion.getCiclas());
				tareviaEstacionEnciclaDTO.setCapacidadEstacionEncila(estacion.getCapacidad());
				tareviaEstacionEnciclaDTO.setLugares(estacion.getLugares());
				updateEstacionEncicla(tareviaEstacionEnciclaDTO, ultimaConsulta);
			} else {
				TareviaEstacionEnciclaDTO tareviaEstacionEnciclaDTONew = new TareviaEstacionEnciclaDTO();
				tareviaEstacionEnciclaDTONew.setIdEstacionEncicla(estacion.getIdEstacion());
				tareviaEstacionEnciclaDTONew.setIdZona(zonaEncicla.getIdZonaCiudad());
				tareviaEstacionEnciclaDTONew.setNombreEstacionEncicla(estacion.getNombre());
				tareviaEstacionEnciclaDTONew.setDireccionEstacionEncicla(estacion.getDireccion() != null && !estacion.getDireccion().isEmpty() ? estacion.getDescripcion() : "NA");
				tareviaEstacionEnciclaDTONew.setDescripcionEstacionEncicla(estacion.getDescripcion() != null && !estacion.getDescripcion().isEmpty() ? estacion.getDescripcion() : "NA");
				tareviaEstacionEnciclaDTONew.setLatitudEstacionEncila(estacion.getLatitud());
				tareviaEstacionEnciclaDTONew.setLongitudEstacionEncila(estacion.getLongitud());
				tareviaEstacionEnciclaDTONew.setActivaEstacionEncicla("S");
				tareviaEstacionEnciclaDTONew.setTipoEstacionEncicla((estacion.getTipo().equals("manual")) ? "M" : "A");
				tareviaEstacionEnciclaDTONew.setCapacidadEstacionEncila(estacion.getCapacidad());
				tareviaEstacionEnciclaDTONew.setCantidadBicicletas(estacion.getCiclas());
				tareviaEstacionEnciclaDTONew.setModoEstacionDTO(modoEstacionDTO);
				saveEstacionEncicla(tareviaEstacionEnciclaDTONew);
			}
		});
	}

	@Override
	@LogReturnValue
	public List<TareviaEstacionEnciclaDTO> obtenerEstacionesPorProximidad(@Log Double latitud, @Log Double longitud,
			@Log Float radio) {
		try {
			return mapper.mapTareviaEstacionEnciclaDTO(
					(List<TareviaEstacionEncicla>) EstacionRepository.findByLocalizacion(latitud, longitud, radio));
		} catch (Exception e) {
			LoggingUtil.logException(String.format(
					"Error al tratar de obtener las estaciones para la latidud: %s, longitud: %s y radio: %s.", latitud,
					longitud, radio), e);
			throw new Area247Exception(String.format(
					"Error al tratar de obtener las estaciones para la latidud: %s, longitud: %s y radio: %s.", latitud,
					longitud, radio), e);
		}
	}

	@Override
	@LogReturnValue
	public EstacionEnciclaDTO findById(@Log Long id) {
		try {
			return mapper.toEstacionEnciclaDTO(repository.findOne(id));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al consultar la estacion por el id %s.", id), e);
			throw new Area247Exception(String.format("Error al consultar la estacion por el id %s.", id), e);
		}
	}

	private void procesarZonasEncicla(@Log ZonaCiudadEnciclaDTO zonaEncicla, Long ultimaConsulta) {
		DareviaZonaDTO dareviaZonaDTO = findByZonaId(zonaEncicla.getIdZonaCiudad());
		if (!Utils.isNull(dareviaZonaDTO)) {
			updateZonaEncicla(dareviaZonaDTO, ultimaConsulta);
		} else {
			DareviaZonaDTO dareviaZonaDTONew = new DareviaZonaDTO();
			dareviaZonaDTONew.setIdZona(zonaEncicla.getIdZonaCiudad());
			dareviaZonaDTONew.setNombreZona(zonaEncicla.getNombre());
			dareviaZonaDTONew.setDescripcionZona(zonaEncicla.getDescripcion());
			saveZonaEncicla(dareviaZonaDTONew);
		}
	}

	@Override
	public DareviaZonaDTO findByZonaId(Long idZona) {
		try {
			return mapper.toDareviaZonaDTO(zonaRepository.findByIdZona(idZona));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al consultar la zona por el id s% de encicla.", idZona), e);
			throw new Area247Exception(String.format("Error al consultar la zona por el id s% de encicla.", idZona), e);
		}

	}

	@Override
	public void saveZonaEncicla(DareviaZonaDTO dareviaZonaDTO) {
		try {
			DareviaZona dareviaZona = mapper.toDareviaZona(dareviaZonaDTO);
			zonaRepository.save(dareviaZona);
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al tratar de guardar la zona %s.", dareviaZonaDTO), e);
			throw new Area247Exception(String.format("Error al tratar de guardar la zona %s.", dareviaZonaDTO), e);
		}
	}

	@Override
	public void updateZonaEncicla(DareviaZonaDTO dareviaZonaDTO, Long ultimaConsulta) {
		try {
			DareviaZona dareviaZona = mapper.toDareviaZona(dareviaZonaDTO);
			Date date = new Date(ultimaConsulta * 1000L);
			dareviaZona.setUltimaModificacion(date);
			zonaRepository.save(dareviaZona);
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al tratar de actualizar la zona %s.", dareviaZonaDTO), e);
			throw new Area247Exception(String.format("Error al tratar de actualizar la zona %s.", dareviaZonaDTO), e);
		}
	}

	@Override
	public TareviaEstacionEnciclaDTO findByEstacionEnciclaId(Long idEstacionEncicla) {
		try {
			return mapper.toTareviaEstacionEnciclaDTO(EstacionRepository.findByIdEstacionEncicla(idEstacionEncicla));
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al consultar la estación por el id s% de encicla.", idEstacionEncicla), e);
			throw new Area247Exception(
					String.format("Error al consultar la estación por el id s% de encicla.", idEstacionEncicla), e);
		}
	}

	@Override
	public List<TareviaEstacionEnciclaDTO> obtenerEstacionesCercanas(Double latitud, Double longitud, Double radio) {
		try {
			List<TareviaEstacionEncicla> estaciones = EstacionRepository.findByLocalizacion(latitud, longitud, radio); 
			return mapper.mapTareviaEstacionEnciclaDTO(estaciones);
		} catch (Exception e) {
			LoggingUtil.logException(String.format(
					"Error al tratar de obtener las estaciones para la latidud: %s, longitud: %s y radio: %s.", latitud,
					longitud, radio), e);
			
			throw new Area247Exception(String.format(
					"Error al tratar de obtener las estaciones para la latidud: %s, longitud: %s y radio: %s.", latitud,
					longitud, radio), e);
		}
	}

	@Override
	public DisponibilidadCiclaDTO disponibilidadEstacionEncicla(Long id) {
		try {
			EnciclaWSDTO enciclaWSTO = enciclaServiceGateway.consultarEstatusEncicla();
			List<ZonaCiudadEnciclaDTO> zonasEncicla = enciclaWSTO.getZonasCiudadEncicla();

			DisponibilidadCiclaDTO estacionDTO = new DisponibilidadCiclaDTO();
			if (!Utils.isEmpty(zonasEncicla)) {
				zonasEncicla.forEach(zonaCiudadEncicla -> {
					List<EstacionEnciclaDTO> estacionesEncicla = zonaCiudadEncicla.getEstacionesEncicla();
					estacionesEncicla.forEach(estacion -> {
						if (id == estacion.getIdEstacion()) {
							estacionDTO.setPuestosEstacionEncila(estacion.getLugares());
							estacionDTO.setCantidadBicicletas(estacion.getCiclas());
							estacionDTO.setIdEstacionEncicla(estacion.getIdEstacion());
							estacionDTO.setId(estacion.getId());
						}
					});
				});
			}

			if (estacionDTO.getIdEstacionEncicla() != null) {
				return estacionDTO;
			}
			return null;

		} catch (Exception e) {
			LoggingUtil.logException("Error al consultar la disponibilidad de ciclas en la estación.", e);
			throw new Area247Exception("Error al consultar la disponibilidad de ciclas en la estación.", e);
		}
	}

	@Override
	public List<CiclorutaDTO> obtenerCiclorutasCercanas(Double latitud, Double longitud, Double radio) {
		try {
			return mapper
					.mapCicloRutaDTO((List<Cicloruta>) cicloRutaRepository.cicloRutaCercana(latitud, longitud, radio));
		} catch (Exception e) {
			LoggingUtil.logException(String.format(
					"Error al tratar de obtener las ciclorutas para la latidud: %s, longitud: %s y radio: %s.", latitud,
					longitud, radio), e);
			throw new Area247Exception(String.format(
					"Error al tratar de obtener las ciclorutas para la latidud: %s, longitud: %s y radio: %s.", latitud,
					longitud, radio), e);
		}
	}

}
