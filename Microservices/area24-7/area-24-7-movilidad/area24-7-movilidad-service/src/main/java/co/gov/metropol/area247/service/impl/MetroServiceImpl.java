package co.gov.metropol.area247.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.model.EmpresaTransporteDTO;
import co.gov.metropol.area247.model.EstacionLineaMetroDTO;
import co.gov.metropol.area247.model.FrecuenciaLineaMetroDTO;
import co.gov.metropol.area247.model.FrecuenciaRutaMetroDTO;
import co.gov.metropol.area247.model.HorarioLineaMetroDTO;
import co.gov.metropol.area247.model.HorarioRutaMetroDTO;
import co.gov.metropol.area247.model.InfoViajesLineaDTO;
import co.gov.metropol.area247.model.InfoViajesRutaDTO;
import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.model.MetroDTO;
import co.gov.metropol.area247.model.ParaderoRutaMetroDTO;
import co.gov.metropol.area247.model.RutaMetroDTO;
import co.gov.metropol.area247.service.IEmpresaTransporteService;
import co.gov.metropol.area247.service.IEstacionLineaMetroService;
import co.gov.metropol.area247.service.IFrecuenciaLineaMetroService;
import co.gov.metropol.area247.service.IFrecuenciaRutaMetroService;
import co.gov.metropol.area247.service.IHorarioLineaMetroService;
import co.gov.metropol.area247.service.IHorarioRutaService;
import co.gov.metropol.area247.service.ILineaMetroService;
import co.gov.metropol.area247.service.IMetroGtfsService;
import co.gov.metropol.area247.service.IMetroService;
import co.gov.metropol.area247.service.IParaderoRutaService;
import co.gov.metropol.area247.service.IRutaMetroService;
import co.gov.metropol.area247.service.ITarifaIntegradaMetroService;
import co.gov.metropol.area247.service.ITipoParametroService;
import co.gov.metropol.area247.service.IViajesLineaService;
import co.gov.metropol.area247.service.IViajesRutaService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.constantes.Constantes;

@Service
public class MetroServiceImpl implements IMetroService {

	@Autowired
	private ILineaMetroService lineaMetroService;
	
	@Autowired
	private ITarifaIntegradaMetroService tarifaIntegradaMetroService;

	@Autowired
	private IFrecuenciaLineaMetroService frecuenciaLineaMetroService;

	@Autowired
	private IEstacionLineaMetroService estacionLineaMetroService;

	@Autowired
	private IHorarioLineaMetroService horarioLineaMetroService;

	@Autowired
	private IRutaMetroService rutaMetroService;

	@Autowired
	private IFrecuenciaRutaMetroService frecuenciaRutaMetroService;

	@Autowired
	private IParaderoRutaService paraderoRutaMetroService;

	@Autowired
	private IHorarioRutaService horarioRutaMetroService;
	
	@Autowired
	private IMetroGtfsService metroGtfsService;
	
	@Autowired
	private ITipoParametroService tipoParametroService;
	
	@Autowired
	private IViajesRutaService viajesRutaService;
	
	@Autowired
	private IViajesLineaService viajesLineaService;
	
	@Autowired
	private IEmpresaTransporteService empresaTransporteService;

	@Override
	public void cargarDatosMetro() {

		String ubicacion = tipoParametroService.findById(Constantes.TipoParametro.URL_FILE_GTFS_METRO).getValor();
		MetroDTO metroDTO = metroGtfsService.obtenerInfoMetroGtfs(ubicacion);

		if (!Utils.isNull(metroDTO)) {
			// Guardamos las lineas
			procesarLineas(metroDTO.getLineasDTO());
			// Guardamos las rutas
			procesarRutas(metroDTO.getRutasDTO());
		}
	}

	@Override
	public void procesarLineas(List<LineaMetroDTO> lineasDTO) {

		if (Utils.isNotEmpty(lineasDTO)) {
			for (LineaMetroDTO lineaDTO : lineasDTO) {
				// Guardamos la lineaMetro
				lineaDTO.setValorTarifa(tarifaIntegradaMetroService
						.obtenerTarifaPorCombinaciones(Arrays.asList(Long.valueOf(lineaDTO.getModoLinea().indice()))));
				lineaMetroService.procesarLinea(lineaDTO);

				// Guardamos los horarios de la linea
				List<HorarioLineaMetroDTO> horariosDTO = lineaDTO.getHorarioLineaMetro();
				procesarHorariosDeLinea(horariosDTO, lineaDTO);

				// Guardamos las frecuencias de la linea
				List<FrecuenciaLineaMetroDTO> frecuenciasDTO = lineaDTO.getFrecuenciaLineaMetro();
				procesarFrecuenciasDeLinea(frecuenciasDTO, lineaDTO);

				// Guardamos las estaciones de la linea
				List<EstacionLineaMetroDTO> estacionesDTO = lineaDTO.getEstacionLineaMetro();
				procesarEstacionesDeLinea(estacionesDTO, lineaDTO);

				List<InfoViajesLineaDTO> infosViajesLineaDTO = lineaDTO.getInfoViajesLineaMetro();
				procesarInfosViajesDeLinea(infosViajesLineaDTO, lineaDTO);
			}
		}
	}

	private void procesarRutas(List<RutaMetroDTO> rutasDTO) {

		if (Utils.isNotEmpty(rutasDTO)) {
			for (RutaMetroDTO rutaDTO : rutasDTO) {
				rutaDTO.setValorTarifa(tarifaIntegradaMetroService
						.obtenerTarifaPorCombinaciones(Arrays.asList(Long.valueOf(rutaDTO.getModoRutaDTO().indice()))));
				rutaMetroService.procesarRuta(rutaDTO);

				// Guardamos las frecuencias de las rutas
				List<FrecuenciaRutaMetroDTO> frecuenciasDTO = rutaDTO.getFrecuenciasDTO();
				procesarFrecuenciasDeRuta(frecuenciasDTO, rutaDTO);

				// Guardamos los paraderos
				List<ParaderoRutaMetroDTO> paraderosDTO = rutaDTO.getParaderosDTO();
				procesarParaderosDeRuta(paraderosDTO, rutaDTO);

				// Guardamos los horarios
				List<HorarioRutaMetroDTO> horariosDTO = rutaDTO.getHorariosDTO();
				procesarHorariosDeRuta(horariosDTO, rutaDTO);

				// Guardamos la informacion de los viajes
				List<InfoViajesRutaDTO> infoViajesDTO = rutaDTO.getInfoViajesDTO();
				procesarInfoViajesDeRuta(infoViajesDTO, rutaDTO);

				// Guardamos la empresa 
				List<EmpresaTransporteDTO> empresasTransporteDTO = rutaDTO.getEmpresasTransporteDTO();
				procesarEmpresasTransporteDeRuta(empresasTransporteDTO, rutaDTO);
			}
		}
	}
	
	private void procesarFrecuenciasDeLinea(List<FrecuenciaLineaMetroDTO> frecuenciasDTO, LineaMetroDTO lineaDTO) {
		if (Utils.isNotEmpty(frecuenciasDTO)) {
			for (FrecuenciaLineaMetroDTO frecuenciaDTO : frecuenciasDTO) {
				frecuenciaDTO.setLineaDTO(lineaDTO);
			}
			frecuenciaLineaMetroService.procesarFrecuencias(frecuenciasDTO);
		}
	}

	private void procesarHorariosDeLinea(List<HorarioLineaMetroDTO> horariosDTO, LineaMetroDTO lineaDTO) {
		if (Utils.isNotEmpty(horariosDTO)) {
			for (HorarioLineaMetroDTO horarioDTO : horariosDTO) {
				horarioDTO.setLineaDTO(lineaDTO);
			}
			horarioLineaMetroService.procesarHorarios(horariosDTO);
		}
	}

	private void procesarEstacionesDeLinea(List<EstacionLineaMetroDTO> estacionesDTO, LineaMetroDTO lineaDTO) {
		if (Utils.isNotEmpty(estacionesDTO)) {
			for (EstacionLineaMetroDTO estacionDTO : estacionesDTO) {
				estacionDTO.setLineaDTO(lineaDTO);
				estacionDTO.setModoEstacionDTO(lineaDTO.getModoLinea());
				estacionLineaMetroService.procesarEstacion(estacionDTO);
			}
		}
	}

	private void procesarInfosViajesDeLinea(List<InfoViajesLineaDTO> infosViajesLineaDTO, LineaMetroDTO lineaDTO) {
		if (Utils.isNotEmpty(infosViajesLineaDTO)) {
			viajesLineaService.procesarInfoViajesLinea(infosViajesLineaDTO, lineaDTO);
		}
	}
	
	private void procesarEmpresasTransporteDeRuta(List<EmpresaTransporteDTO> empresasTransporteDTO,
			RutaMetroDTO rutaDTO) {
		if (Utils.isNotEmpty(empresasTransporteDTO)) {
			empresaTransporteService.procesarEmpresasTransporte(empresasTransporteDTO, rutaDTO.getId());
		}
	}

	private void procesarInfoViajesDeRuta(List<InfoViajesRutaDTO> infoViajesDTO, RutaMetroDTO rutaDTO) {
		if (Utils.isNotEmpty(infoViajesDTO)) {
			viajesRutaService.procesarInfoViajesRuta(infoViajesDTO, rutaDTO);
		}
	}

	private void procesarFrecuenciasDeRuta(List<FrecuenciaRutaMetroDTO> frecuenciasDTO, RutaMetroDTO rutaDTO) {
		if (Utils.isNotEmpty(frecuenciasDTO)) {
			for (FrecuenciaRutaMetroDTO frecuenciaDTO : frecuenciasDTO) {
				frecuenciaDTO.setRutaMetroDTO(rutaDTO);
			}
			frecuenciaRutaMetroService.procesarFrecuencias(frecuenciasDTO);
		}
	}

	private void procesarParaderosDeRuta(List<ParaderoRutaMetroDTO> paraderosDTO, RutaMetroDTO rutaDTO) {
		if (Utils.isNotEmpty(paraderosDTO)) {
			for (ParaderoRutaMetroDTO paraderoDTO : paraderosDTO) {
				paraderoDTO.setRutaDTO(rutaDTO);
				paraderoRutaMetroService.procesarParadero(paraderoDTO);
			}
		}
	}

	private void procesarHorariosDeRuta(List<HorarioRutaMetroDTO> horariosDTO, RutaMetroDTO rutaDTO) {
		if (Utils.isNotEmpty(horariosDTO)) {
			for (HorarioRutaMetroDTO horarioDTO : horariosDTO) {
				horarioDTO.setRutaDTO(rutaDTO);
			}
			horarioRutaMetroService.procesarHorarios(horariosDTO);
		}
	}

}
