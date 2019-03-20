package co.gov.metropol.area247.service.impl;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import co.gov.metropol.area247.gateway.IGtpcServiceGateway;
import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.model.EmpresaRutaDTO;
import co.gov.metropol.area247.model.EmpresaTransporteDTO;
import co.gov.metropol.area247.model.FrecuenciaRutaDTO;
import co.gov.metropol.area247.model.HorarioRutaDTO;
import co.gov.metropol.area247.model.InfoViajesRutaDTO;
import co.gov.metropol.area247.model.OperadorDTO;
import co.gov.metropol.area247.model.OperadorEmpresaDTO;
import co.gov.metropol.area247.model.ParaderoRutaDTO;
import co.gov.metropol.area247.model.RutaGtpcDTO;
import co.gov.metropol.area247.model.RutaMetroDTO;
import co.gov.metropol.area247.model.RutaTipoSistemaRutaDTO;
import co.gov.metropol.area247.model.TipoModoTransporteDTO;
import co.gov.metropol.area247.model.TipoOrientacionDTO;
import co.gov.metropol.area247.model.TipoParaderoDTO;
import co.gov.metropol.area247.model.TipoRutaDTO;
import co.gov.metropol.area247.model.TipoSistemaRutaDTO;
import co.gov.metropol.area247.repository.domain.EmpresaRuta;
import co.gov.metropol.area247.repository.domain.EmpresaTransporte;
import co.gov.metropol.area247.repository.domain.FrecuenciaRuta;
import co.gov.metropol.area247.repository.domain.HorarioRuta;
import co.gov.metropol.area247.repository.domain.Operador;
import co.gov.metropol.area247.repository.domain.OperadorEmpresa;
import co.gov.metropol.area247.repository.domain.ParaderoRuta;
import co.gov.metropol.area247.repository.domain.Ruta;
import co.gov.metropol.area247.repository.domain.RutaTipoSistemaRuta;
import co.gov.metropol.area247.repository.domain.TipoModoTransporte;
import co.gov.metropol.area247.repository.domain.TipoOrientacion;
import co.gov.metropol.area247.repository.domain.TipoParadero;
import co.gov.metropol.area247.repository.domain.TipoRuta;
import co.gov.metropol.area247.repository.domain.TipoSistemaRuta;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;
import co.gov.metropol.area247.service.IGtpcService;
import co.gov.metropol.area247.service.impl.abstracts.AbstractGtpcService;
import co.gov.metropol.area247.services.rest.gtpc.FrecuenciaGtpcWSDTO;
import co.gov.metropol.area247.services.rest.gtpc.HorarioGtpcWSDTO;
import co.gov.metropol.area247.services.rest.gtpc.ParaderoGtpcWSDTO;
import co.gov.metropol.area247.services.rest.gtpc.RutaGtpcWSDTO;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.constantes.Constantes;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class GtpcServiceImpl extends AbstractGtpcService implements IGtpcService {

	private Long idTipoRuta;
	private Long idTipoModoTransporte;
	private Long idTipoSistemaRuta;
	private Long idRuta;
	private Long idTipoParadero;
	private Long idOrientacion;
	private Long idOperador;
	private Long idEmpresaTransporte;
	private Long idRutaTipoSistemaRuta;

	@Autowired
	private IGtpcServiceGateway gtpcServiceGateway;
	

	@Override
	public void cargarInformacionDeRutas() {
		List<RutaGtpcWSDTO> rutasGtpcWSDTO = gtpcServiceGateway.cargarInformacionRutas();
		for (RutaGtpcWSDTO rutaGtpcWSDTO : rutasGtpcWSDTO) {
			
			if (!isRutaValida(rutaGtpcWSDTO)) 
				continue;
			
			TipoRutaDTO tipoRutaDTO = rutaGtpcWSDTO.getTipoRutaDTO();
			TipoModoTransporteDTO tipoModoTransporteDTO = rutaGtpcWSDTO.getTipoModoTransporteDTO();

			// TipoSistemaRutaDTO tipoSistemaRutaDTO =
			// rutaGtpcWSDTO.getTipoSitemaRutaDTO();
			List<TipoSistemaRutaDTO> listTipoSistemaRutaDTO = rutaGtpcWSDTO.getTiposSitemaRutaDTO();

			List<RutaTipoSistemaRutaDTO> listRutaTipoSistemaRutaDTO = rutaGtpcWSDTO.getRutaTiposSitemaRutaDTO();

			RutaGtpcDTO rutaGtpcDTO = rutaGtpcWSDTO.getRutaGtpcDTO();
			List<ParaderoGtpcWSDTO> paraderosGtpcWSDTO = rutaGtpcWSDTO.getParaderos();
			List<FrecuenciaGtpcWSDTO> frecuenciaRutaGtpcWSDTO = rutaGtpcWSDTO.getFrecuencia();
			List<HorarioGtpcWSDTO> horarioGtpcWSDTO = rutaGtpcWSDTO.getHorario();

			// OperadorDTO operadorDTO = rutaGtpcWSDTO.getOperadorDTO();
			List<OperadorDTO> operadoresDTO = rutaGtpcWSDTO.getOperadoresDTO();

			// EmpresaTransporteDTO empresaTransporteDTO =
			// rutaGtpcWSDTO.getEmpresaTransporteDTO();
			List<EmpresaTransporteDTO> empresasTransporteDTO = rutaGtpcWSDTO.getEmpresasTransporteDTO();

			// OperadorEmpresaDTO operadorEmpresaDTO =
			// rutaGtpcWSDTO.getOperadorEmpresaDTO();
			List<OperadorEmpresaDTO> operadoresEmpresaDTO = rutaGtpcWSDTO.getOperadoresEmpresaDTO();
			
			

			// procesar tablas anexas a ruta
			procesarTipoRuta(tipoRutaDTO);
//			procesarTipoModoTransporte(tipoModoTransporteDTO); //--los modos de transporte estan parametrizados
			procesarTipoSistemaRuta(listTipoSistemaRutaDTO);
			// procesarTipoSistemaRuta(tipoSistemaRutaDTO);//--borrar

			// seteo los id que son llaves foraneas en ruta
			rutaGtpcDTO.setIdTipoRuta(this.idTipoRuta);
			rutaGtpcDTO.setIdModoRuta(ModoRecorrido.AUTOBUS);
			// rutaGtpcDTO.setIdTipoSistemaRuta(this.idTipoSistemaRuta);
			// procesar la ruta
			procesarRutaGtpc(rutaGtpcDTO);
			
			procesarRutaTipoSistemaRuta(listRutaTipoSistemaRutaDTO, listTipoSistemaRutaDTO);
			// procesar los pareaderos de cada ruta
			if (!Utils.isEmpty(paraderosGtpcWSDTO)) {
				paraderosGtpcWSDTO.forEach(paraderoGtpcWSTO -> {
					TipoParaderoDTO tipoParadetoDTO = paraderoGtpcWSTO.getTipoParaderoDTO();
					TipoOrientacionDTO tipoOrientacionDTO = paraderoGtpcWSTO.getTipoOrientacionDTO();

					procesarTipoParaderoGtpc(tipoParadetoDTO);
					procesarTipoOrientacionGtpc(tipoOrientacionDTO);

					ParaderoRutaDTO paraderoRutaDTO = paraderoGtpcWSTO.getParaderoRuta();
					paraderoRutaDTO.setIdTipoParadero(this.idTipoParadero);
					paraderoRutaDTO.setIdTipoOrientacion(this.idOrientacion);
					paraderoRutaDTO.setIdRuta(this.idRuta);

					procesarParaderoRutaGtpc(paraderoRutaDTO);
				});
			}
			// se procesan la frecuencia de los paraderos
			if (!Utils.isEmpty(frecuenciaRutaGtpcWSDTO)) {
				frecuenciaRutaGtpcWSDTO.forEach(frecuenciaRutaGtpc -> {
					FrecuenciaRutaDTO frecuenciaRutaDTO = frecuenciaRutaGtpc.getFrecuenciaRuta();
					frecuenciaRutaDTO.setIdRuta(this.idRuta);
					procesarFrecuenciaRutaGtpc(frecuenciaRutaDTO);
				});
			}
			// se proces los horarios de las rutas
			if (!Utils.isEmpty(horarioGtpcWSDTO)) {
				horarioGtpcWSDTO.forEach(horarioRutaGtpc -> {
					HorarioRutaDTO horarioRutaDTO = horarioRutaGtpc.getHorarioRuta();
					horarioRutaDTO.setIdRuta(this.idRuta);
					procesarHorarioRutaGtpc(horarioRutaDTO);
				});
			}
			// se procesan operador
			// procesarOperador(operadorDTO); // borra
			procesarOperadores(operadoresDTO);

			// se procesa la empresa de transporte
			// ProcesarEmpresaTransporte(empresaTransporteDTO);
			procesarEmpresasTransporte(empresasTransporteDTO);

			// se procesa operador empresa
			/*
			 * operadorEmpresaDTO.setIdEmpresaTransporte(this.
			 * idEmpresaTransporte);
			 * operadorEmpresaDTO.setIdOperador(this.idOperador);
			 */
			// ProcesarOperadorEmpresa(operadorEmpresaDTO);
			
			procesarOperadoresEmpresa(operadoresEmpresaDTO, empresasTransporteDTO, operadoresDTO);
			
			procesarInfoViajesRutaDTO(frecuenciaRutaGtpcWSDTO, horarioGtpcWSDTO, rutaGtpcDTO);
			
			procesarEmpresasRuta(empresasTransporteDTO);
		}
	}
	
	private void procesarInfoViajesRutaDTO(List<FrecuenciaGtpcWSDTO> frecuenciasRutaGtpcWSDTO,
			List<HorarioGtpcWSDTO> horariosGtpcWSDTO, RutaGtpcDTO rutaGtpcDTO) {
		
		RutaMetroDTO rutaMetroDTO = new RutaMetroDTO().withId(this.idRuta);
		List<InfoViajesRutaDTO> infosViajesRutaDTO = Lists.newArrayList();
		// Obtenemos el horario que se supone que es solo uno
		if (Utils.isNotEmpty(horariosGtpcWSDTO) && Utils.isNotEmpty(frecuenciasRutaGtpcWSDTO)) {
			HorarioGtpcWSDTO horarioGtpcWSDTO = horariosGtpcWSDTO.get(0);
			for (FrecuenciaGtpcWSDTO frecuenciaRutaGtpcWSDTO : frecuenciasRutaGtpcWSDTO) {
				
				Long numSalidas = frecuenciaRutaGtpcWSDTO.getFrecuenciaMinimaPicoAm().longValue()
						+ frecuenciaRutaGtpcWSDTO.getFrecuenciaMinimaPicoPm().longValue()
						+ frecuenciaRutaGtpcWSDTO.getFrecuenciaMinimaValleDiurno().longValue()
						+ frecuenciaRutaGtpcWSDTO.getFrecuenciaMinimaValleNocturno().longValue();
				
				String horaInicio = obtenerHora(horarioGtpcWSDTO.getHoraInicioOperacion());
				String horaFin = obtenerHora(horarioGtpcWSDTO.getHoraFinOperacion());
				
				InfoViajesRutaDTO infoViajeRutaDTO = new InfoViajesRutaDTO();
				infoViajeRutaDTO.setNumSalidas(numSalidas);
				infoViajeRutaDTO.setEnabled(true);
				infoViajeRutaDTO.setHoraInicio(horaInicio);
				infoViajeRutaDTO.setHoraFin(horaFin);
				infoViajeRutaDTO.setTiempoVuelta(3600l);
				infoViajeRutaDTO.setIdaVuelta(false);
				infoViajeRutaDTO.setRutaDTO(rutaMetroDTO);
				infoViajeRutaDTO.setFrecuencia(calcularFrecuenciaEntreDespachos(numSalidas, horaInicio, horaFin));
				infosViajesRutaDTO.add(infoViajeRutaDTO);
			}
		}
		
		viajesRutaService.procesarInfoViajesRuta(infosViajesRutaDTO, rutaMetroDTO);
	}

	private void procesarTipoRuta(TipoRutaDTO tipoRutadto) {
		if (!Utils.isNull(tipoRutadto)) {
			TipoRutaDTO tipoRutaDTO = findTipoRutaByIdItem_idFuenteDatos(tipoRutadto.getIdItem(),
					tipoRutadto.getFuenteDatos());
			if (!Utils.isNull(tipoRutaDTO)) {
				updateTipoRuta(tipoRutaDTO);
			} else {
				saveTipoRUta(tipoRutadto);
			}
		}
	}

	private void procesarTipoModoTransporte(TipoModoTransporteDTO tipoModoTransportedto) {
		if (!Utils.isNull(tipoModoTransportedto)) {
			TipoModoTransporteDTO tipoModoTransporteDTO = findTipoModoTransByIdItem_idFuenteDatos(
					tipoModoTransportedto.getIdItem(), tipoModoTransportedto.getFuenteDatos());
			if (!Utils.isNull(tipoModoTransporteDTO)) {
				updateTipoModoTransporte(tipoModoTransporteDTO);
			} else {
				saveTipoModoTransporte(tipoModoTransportedto);
			}
		}
	}
	
	//borrar
	private void procesarTipoSistemaRuta(TipoSistemaRutaDTO tipoSistemaRutadto) {
		TipoSistemaRutaDTO tipoSistemaRutaDTO = findTipoSistRutaByIdItem_idFuenteDatos(tipoSistemaRutadto.getIdItem(),
				tipoSistemaRutadto.getFuenteDatos());
		if (!Utils.isNull(tipoSistemaRutaDTO)) {
			updateTipoSistemaRuta(tipoSistemaRutaDTO);
		} else {
			saveTipoSistemaRuta(tipoSistemaRutadto);
		}
	}
	
	private void procesarRutaTipoSistemaRuta(List<RutaTipoSistemaRutaDTO> listRutaTipoSistemaRutaDTO, List<TipoSistemaRutaDTO> listTipoSistemaRutaDTO) {
		for (RutaTipoSistemaRutaDTO rutaTipoSistemaRutaDTO : listRutaTipoSistemaRutaDTO) {
			rutaTipoSistemaRutaDTO.setIdRuta(this.idRuta);
			
			Optional<TipoSistemaRutaDTO> tipoSistemaRutaOptional = listTipoSistemaRutaDTO.stream().filter(p -> p.getIdItem().equals(rutaTipoSistemaRutaDTO.getIdTipoSistemaRuta())).findFirst();
			
			if (tipoSistemaRutaOptional.isPresent()) {
				TipoSistemaRutaDTO tipoSistemaRutaDTOAux = tipoSistemaRutaOptional.get();
				rutaTipoSistemaRutaDTO.setIdTipoSistemaRuta(tipoSistemaRutaDTOAux.getId());
				
				RutaTipoSistemaRutaDTO rutaTipoSistemaRutaDTOAux  = findRutaTipoSistRutaByIdRuta_idTipoSis(rutaTipoSistemaRutaDTO.getIdRuta(), rutaTipoSistemaRutaDTO.getIdTipoSistemaRuta());
				
				if (Utils.isNull(rutaTipoSistemaRutaDTOAux)) {
					saveRutaTipoSistemaRuta(rutaTipoSistemaRutaDTO);
				}
			}
		}
	}
	
	private void procesarTipoSistemaRuta(List<TipoSistemaRutaDTO> listTipoSistemaRutadto) {
		for (TipoSistemaRutaDTO tipoSistemaRutadto : listTipoSistemaRutadto) {
			TipoSistemaRutaDTO tipoSistemaRutaDTO = findTipoSistRutaByIdItem_idFuenteDatos(tipoSistemaRutadto.getIdItem(),
					tipoSistemaRutadto.getFuenteDatos());
			if (!Utils.isNull(tipoSistemaRutaDTO)) {
				tipoSistemaRutadto.setId(tipoSistemaRutaDTO.getId());
				updateTipoSistemaRuta(tipoSistemaRutadto);
			} else {
				saveTipoSistemaRuta(tipoSistemaRutadto);
			}
		}		
	}
	
	private void procesarRutaGtpc(RutaGtpcDTO rutaGtpcDTO) {
		RutaGtpcDTO rutaGtpcDTOAux = findRutaGtpcByIdItem_idFuenteDatos(rutaGtpcDTO.getIdItem(),
				rutaGtpcDTO.getFuenteDatos());
		if (!Utils.isNull(rutaGtpcDTOAux)) {
			rutaGtpcDTO.setId(rutaGtpcDTOAux.getId());
			updateRutaGtpc(rutaGtpcDTO);
		} else {
			saveRutaGtpc(rutaGtpcDTO);
		}
	}

	private void procesarTipoParaderoGtpc(TipoParaderoDTO tipoParaderodto) {
		TipoParaderoDTO tipoParaderoDTO = findTipoParaderoByIdItem_idFuenteDatos(tipoParaderodto.getIdItem(),
				tipoParaderodto.getFuenteDatos());
		if (!Utils.isNull(tipoParaderoDTO)) {
			updateTipoParaderGtpc(tipoParaderoDTO);
		} else {
			saveTipoParadertoGtpc(tipoParaderodto);
		}
	}

	private void procesarTipoOrientacionGtpc(TipoOrientacionDTO tipoTipoOrientaciondto) {
		TipoOrientacionDTO tipoOrientacionDTO = findTipoOrientacionByIdItem_idFuenteDatos(
				tipoTipoOrientaciondto.getIdItem(), tipoTipoOrientaciondto.getFuenteDatos());
		if (!Utils.isNull(tipoOrientacionDTO)) {
			updateTipoOrientacionGtpc(tipoOrientacionDTO);
		} else {
			saveTipoOrientacionGtpc(tipoTipoOrientaciondto);
		}
	}

	private void procesarParaderoRutaGtpc(ParaderoRutaDTO paraderoRutaDTO) {
		ParaderoRutaDTO paraderoRutaDTOAux = findParaderoRutaByCodigoAndFuenteDatos(paraderoRutaDTO.getCodigo(),
				paraderoRutaDTO.getFuenteDatos());
		if (!Utils.isNull(paraderoRutaDTOAux)) {
			paraderoRutaDTO.setId(paraderoRutaDTOAux.getId());
			updateParaderoRutaGtpc(paraderoRutaDTO);
		} else {
			saveParaderoRutaGtpc(paraderoRutaDTO);
		}
	}

	private void procesarFrecuenciaRutaGtpc(FrecuenciaRutaDTO frecuenciaRutadto) {
		FrecuenciaRutaDTO frecuenciaRutaDTO = findFrecuenciaRutaByIdItem_idFuenteDatos(frecuenciaRutadto.getIdItem(),
				frecuenciaRutadto.getFuenteDatos());
		if (!Utils.isNull(frecuenciaRutaDTO)) {
			updateParaderoRutaGtpc(frecuenciaRutaDTO);
		} else {
			saveParaderoRutaGtpc(frecuenciaRutadto);
		}
	}

	private void procesarHorarioRutaGtpc(HorarioRutaDTO horarioRutadto) {
		HorarioRutaDTO horarioRutaDTO = findHorarioRutaByIdItem_idFuenteDatos(horarioRutadto.getIdItem(),
				horarioRutadto.getFuenteDatos());
		if (!Utils.isNull(horarioRutaDTO)) {
			updateHorarioRutaGtpc(horarioRutaDTO);
		} else {
			saveHorarioRutaGtpc(horarioRutadto);
		}
	}

	private void procesarOperador(OperadorDTO operadordto) {
		OperadorDTO operadorDTO = findOperadorByIdItem_idFuenteDatos(operadordto.getIdItem());
		if (!Utils.isNull(operadorDTO)) {
			updateOperador(operadorDTO);
		} else {
			saveOperador(operadordto);
		}
	}

	private void procesarOperadores(List<OperadorDTO> operadoresDTO) {
		for (OperadorDTO operadorDTO : operadoresDTO) {
			OperadorDTO operadorDTOAux = findOperadorByIdItem_idFuenteDatos(operadorDTO.getIdItem());
			if (!Utils.isNull(operadorDTOAux)) {
				operadorDTO.setId(operadorDTOAux.getId());
				updateOperador(operadorDTO);
			} else {
				saveOperador(operadorDTO);
			}
		}
	}

	private void ProcesarEmpresaTransporte(EmpresaTransporteDTO empresaTransportedto) {
		EmpresaTransporteDTO empresaTransporteDTO = findEmpresaTransporteByIdItem_idFuenteDatos(
				empresaTransportedto.getIdItem());
		if (!Utils.isNull(empresaTransporteDTO)) {
			updateEmpresaTransporte(empresaTransporteDTO);
		} else {
			saveEmpresaTransporte(empresaTransportedto);
		}
	}
	
	private void procesarEmpresasTransporte(List<EmpresaTransporteDTO> empresasTransporteDTO) {
		for (EmpresaTransporteDTO empresaTransporteDTO : empresasTransporteDTO) {
			EmpresaTransporteDTO empresaTransporteDTOAux = findEmpresaTransporteByIdItem_idFuenteDatos(
					empresaTransporteDTO.getIdItem());
			if (!Utils.isNull(empresaTransporteDTOAux)) {
				empresaTransporteDTO.setId(empresaTransporteDTOAux.getId());
				updateEmpresaTransporte(empresaTransporteDTO);
			} else {
				saveEmpresaTransporte(empresaTransporteDTO);
			}
		}
	}

	private void procesarOperadoresEmpresa(List<OperadorEmpresaDTO> operadoresEmpresadto,
			List<EmpresaTransporteDTO> empresasTransporteDTO, List<OperadorDTO> operadoresDTO) {
	
		List<OperadorEmpresaDTO> idsSincronizados = new ArrayList<>(
				sincronizarIdsOperadorEmpresa(operadoresEmpresadto, empresasTransporteDTO, operadoresDTO));
		
		procesarOperadoresEmpresa(idsSincronizados);
		
	}

	private void procesarOperadoresEmpresa(List<OperadorEmpresaDTO> operadoresEmpresadto) {
		for (OperadorEmpresaDTO operadorEmpresadto : operadoresEmpresadto) {
			OperadorEmpresaDTO operadorEmpresaDTO = findOperadorEmpresaByIdItem_idFuenteDatos(
					operadorEmpresadto.getIdItem());
			if (!Utils.isNull(operadorEmpresaDTO)) {
				updateOperadorEmpresa(operadorEmpresaDTO);
			} else {
				saveOperadorEmpresa(operadorEmpresadto);
			}
		}
	}

	/**
	 * Este metodo persiste las empresas que estan relacionadas con la ruta que
	 * se persiste en el momento
	 * 
	 * @param empresasTransporteDTO
	 *            - empresas relacionadas con la ruta, estas ya deben estar
	 *            persistidas, osea ya deben tener un id.
	 */
	private void procesarEmpresasRuta(List<EmpresaTransporteDTO> empresasTransporteDTO) {
			empresaTransporteService.procesarEmpresasTransporte(empresasTransporteDTO, this.idRuta);
	}

	@Override
	public TipoRutaDTO findTipoRutaById(Long id) {
		try {
			return mapper.toTipoRutaDTO(tipoRutaRepository.findOne(id));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al consultar el tipo ruta por el id %s ", id), e);
			throw new Area247Exception(String.format("Error al consultar el tipo ruta por el id %s ", id), e);
		}
	}

	@Override
	public TipoRutaDTO findTipoRutaByIdItem_idFuenteDatos(Long idItem, int fuenteDatos) {
		try {
			return mapper.toTipoRutaDTO(tipoRutaRepository.findByIdItem_idFuenteDatos(idItem, fuenteDatos));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al consultar el tipo ruta por el idItem %s , fuenteDatos %s ",
					idItem, fuenteDatos), e);
			throw new Area247Exception(String.format(
					"Error al consultar el tipo ruta por el idItem %s , fuenteDatos %s ", idItem, fuenteDatos), e);
		}
	}

	@Override
	public void updateTipoRuta(TipoRutaDTO tipoRutaDTO) {
		try {
			TipoRuta tipoRuta = mapper.toTipoRuta(tipoRutaDTO);
			tipoRutaRepository.save(tipoRuta);
			this.idTipoRuta = tipoRuta.getId();
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de actualizar el tipo ruta con idItem %s y fuenteDtos %s",
							tipoRutaDTO.getIdItem(), tipoRutaDTO.getFuenteDatos()),
					e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar el tipo ruta con idItem %s y fuenteDtos %s",
							tipoRutaDTO.getIdItem(), tipoRutaDTO.getFuenteDatos()),
					e);
		}
	}

	@Override
	public void saveTipoRUta(TipoRutaDTO tipoRutaDTO) {
		try {
			TipoRuta tipoRuta = mapper.toTipoRuta(tipoRutaDTO);
			tipoRutaRepository.save(tipoRuta);
			this.idTipoRuta = tipoRuta.getId();
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de insertar el tipo ruta con idItem %s y fuenteDtos %s",
							tipoRutaDTO.getIdItem(), tipoRutaDTO.getFuenteDatos()),
					e);
			throw new Area247Exception(
					String.format("Error al tratar de insertar el tipo ruta con idItem %s y fuenteDtos %s",
							tipoRutaDTO.getIdItem(), tipoRutaDTO.getFuenteDatos()),
					e);
		}
	}

	@Override
	public TipoModoTransporteDTO findTipoModoTransById(Long id) {
		try {
			return mapper.toTipoModoTransporteDTO(tipoModoTransporteRepository.findOne(id));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al consultar el tipo modo transporte por el id %s ", id), e);
			throw new Area247Exception(String.format("Error al consultar el tipo modo transporte por el id %s ", id),
					e);
		}
	}

	@Override
	public TipoModoTransporteDTO findTipoModoTransByIdItem_idFuenteDatos(Long idItem, int fuentDatos) {
		try {
			return mapper.toTipoModoTransporteDTO(
					tipoModoTransporteRepository.findByIdItem_idFuenteDatos(idItem, fuentDatos));
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al consultar el tipo modo transporte por el idItem %s , fuenteDatos %s ",
							idItem, fuentDatos),
					e);
			throw new Area247Exception(
					String.format("Error al consultar el tipo modo transporte por el idItem %s , fuenteDatos %s ",
							idItem, fuentDatos),
					e);
		}
	}

	@Override
	public void updateTipoModoTransporte(TipoModoTransporteDTO tipoModoTransporteDTO) {
		try {
			TipoModoTransporte tipoModoTransporte = mapper.toTipoModoTransporte(tipoModoTransporteDTO);
			tipoModoTransporteRepository.save(tipoModoTransporte);
			this.idTipoModoTransporte = tipoModoTransporte.getId();
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de actualizar el tipo modo transporte con idItem %s y fuenteDtos %s",
							tipoModoTransporteDTO.getIdItem(), tipoModoTransporteDTO.getFuenteDatos()),
					e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar el tipo modo transporte con idItem %s y fuenteDtos %s",
							tipoModoTransporteDTO.getIdItem(), tipoModoTransporteDTO.getFuenteDatos()),
					e);
		}
	}

	@Override
	public void saveTipoModoTransporte(TipoModoTransporteDTO tipoModoTransporteDTO) {
		try {
			TipoModoTransporte tipoModoTransporte = mapper.toTipoModoTransporte(tipoModoTransporteDTO);
			tipoModoTransporteRepository.save(tipoModoTransporte);
			this.idTipoModoTransporte = tipoModoTransporte.getId();
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de insertar el tipo modo transporte con idItem %s y fuenteDtos %s",
							tipoModoTransporteDTO.getIdItem(), tipoModoTransporteDTO.getFuenteDatos()),
					e);
			throw new Area247Exception(
					String.format("Error al tratar de insertar el tipo modo transporte con idItem %s y fuenteDtos %s",
							tipoModoTransporteDTO.getIdItem(), tipoModoTransporteDTO.getFuenteDatos()),
					e);
		}
	}

	@Override
	public TipoSistemaRutaDTO findTipoSistRutaById(Long id) {
		try {
			return mapper.toTipoSistemaRutaDTO(tipoSistemaRutaRepostory.findOne(id));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al consultar el tipo sistema ruta por el id %s", id), e);
			throw new Area247Exception(String.format("Error al consultar el tipo sistema ruta por el id %s", id), e);
		}
	}

	@Override
	public TipoSistemaRutaDTO findTipoSistRutaByIdItem_idFuenteDatos(Long idItem, int fuenteDatos) {
		try {
			return mapper
					.toTipoSistemaRutaDTO(tipoSistemaRutaRepostory.findByIdItem_idFuenteDatos(idItem, fuenteDatos));
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al consultar el tipo sistema ruta por el idItem %s , fuenteDatos %s ", idItem,
							fuenteDatos),
					e);
			throw new Area247Exception(
					String.format("Error al consultar el tipo sistema ruta por el idItem %s , fuenteDatos %s ", idItem,
							fuenteDatos),
					e);
		}
	}
	
	@Override
	public RutaTipoSistemaRutaDTO findRutaTipoSistRutaByIdRuta_idTipoSis(Long idRuta, Long idTipoSistemaRuta) {
		try {
			return mapper.toRutaTipoSistemaRutaDTO(rutaTipoSistemaRutaRepository.findByidRuta_idTipoSis(idRuta, idTipoSistemaRuta));
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al consultar el tipo sistema ruta por el idRuta %s , idTiposistemaRUta %s ", idRuta,
							idTipoSistemaRuta),
					e);
			throw new Area247Exception(
					String.format("Error al consultar el tipo sistema ruta por el idRuta %s , idTiposistemaRUta %s ", idRuta,
							idTipoSistemaRuta),
					e);
		}
	}

	@Override
	public void updateTipoSistemaRuta(TipoSistemaRutaDTO tipoSistemaRutaDTO) {
		try {
			TipoSistemaRuta tipoSistemaRuta = mapper.toTipoSistemaRuta(tipoSistemaRutaDTO);
			tipoSistemaRutaRepostory.save(tipoSistemaRuta);
			this.idTipoSistemaRuta = tipoSistemaRuta.getId();
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de actualizar el tipo sistema ruta con idItem %s y fuenteDtos %s",
							tipoSistemaRutaDTO.getIdItem(), tipoSistemaRutaDTO.getFuenteDatos()),
					e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar el tipo sistema ruta con idItem %s y fuenteDtos %s",
							tipoSistemaRutaDTO.getIdItem(), tipoSistemaRutaDTO.getFuenteDatos()),
					e);
		}
	}

	@Override
	public void saveTipoSistemaRuta(TipoSistemaRutaDTO tipoSistemaRutaDTO) {
		try {
			TipoSistemaRuta tipoSistemaRuta = mapper.toTipoSistemaRuta(tipoSistemaRutaDTO);
			tipoSistemaRutaRepostory.save(tipoSistemaRuta);
			tipoSistemaRutaDTO.setId(tipoSistemaRuta.getId());
			this.idTipoSistemaRuta = tipoSistemaRuta.getId();
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de insertar el tipo sistema ruta con idItem %s y fuenteDtos %s",
							tipoSistemaRutaDTO.getIdItem(), tipoSistemaRutaDTO.getFuenteDatos()),
					e);
			throw new Area247Exception(
					String.format("Error al tratar de insertar el tipo sistema ruta con idItem %s y fuenteDtos %s",
							tipoSistemaRutaDTO.getIdItem(), tipoSistemaRutaDTO.getFuenteDatos()),
					e);
		}
	}
	
	@Override
	public void updateRutaTipoSistemaRuta(RutaTipoSistemaRutaDTO rutaTipoSistemaRutaDTO) {
		try {
			RutaTipoSistemaRuta rutaTipoSistemaRuta  = mapper.toRutaTipoSistemaRuta(rutaTipoSistemaRutaDTO);
			rutaTipoSistemaRutaRepository.save(rutaTipoSistemaRuta);
			this.idRutaTipoSistemaRuta = rutaTipoSistemaRuta.getId();
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de actualizar ruta tipo sistema ruta con idIdRuta %s",
							rutaTipoSistemaRutaDTO.getIdRuta()), e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar ruta tipo sistema ruta con idIdRuta %",
							rutaTipoSistemaRutaDTO.getIdRuta()),e);
		}
	}
	
	@Override
	public void saveRutaTipoSistemaRuta(RutaTipoSistemaRutaDTO rutaTipoSistemaRutaDTO) {
		try {
			RutaTipoSistemaRuta rutaTipoSistemaRuta  = mapper.toRutaTipoSistemaRuta(rutaTipoSistemaRutaDTO);
			rutaTipoSistemaRutaRepository.save(rutaTipoSistemaRuta);
			this.idRutaTipoSistemaRuta = rutaTipoSistemaRuta.getId();
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de actualizar ruta tipo sistema ruta con idIdRuta %s",
							rutaTipoSistemaRutaDTO.getIdRuta()), e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar ruta tipo sistema ruta con idIdRuta %",
							rutaTipoSistemaRutaDTO.getIdRuta()),e);
		}
	}

	@Override
	public RutaGtpcDTO findRutaGtpcByIdItem_idFuenteDatos(Long idItem, int fuenteDatos) {
		try {
			return mapper.toRutaGtpcDTO(rutaRepository.findByIdItem_idFuenteDatos(idItem, fuenteDatos));
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al consultar la ruta por el idItem %s , fuenteDatos %s ", idItem, fuenteDatos),
					e);
			throw new Area247Exception(
					String.format("Error al consultar la ruta por el idItem %s , fuenteDatos %s ", idItem, fuenteDatos),
					e);
		}
	}

	@Override
	public void updateRutaGtpc(RutaGtpcDTO rutaGtpcDTO) {
		try {
			Ruta rutaGtpc = mapper.toRutaGtpc(rutaGtpcDTO);
			rutaGtpc.setCoordenadas(rutaGtpcDTO.getCoordenadas());
			rutaRepository.save(rutaGtpc);
			rutaGtpcDTO.setId(rutaGtpc.getId());
			this.idRuta = rutaGtpc.getId();
		} catch (Exception e) {
			LoggingUtil
					.logException(String.format("Error al tratar de actualizar la  ruta con idItem %s y fuenteDtos %s",
							rutaGtpcDTO.getIdItem(), rutaGtpcDTO.getFuenteDatos()), e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar la ruta con idItem %s y fuenteDtos %s",
							rutaGtpcDTO.getIdItem(), rutaGtpcDTO.getFuenteDatos()),
					e);
		}
	}

	@Override
	public void saveRutaGtpc(RutaGtpcDTO rutaGtpcDTO) {
		try {
			Ruta rutaGtpc = mapper.toRutaGtpc(rutaGtpcDTO);
			rutaRepository.save(rutaGtpc);
			rutaGtpcDTO.setId(rutaGtpc.getId());
			this.idRuta = rutaGtpc.getId();
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al tratar de insertar la  ruta con idItem %s y fuenteDtos %s",
					rutaGtpcDTO.getIdItem(), rutaGtpcDTO.getFuenteDatos()), e);
			throw new Area247Exception(
					String.format("Error al tratar de insertar la ruta con idItem %s y fuenteDtos %s",
							rutaGtpcDTO.getIdItem(), rutaGtpcDTO.getFuenteDatos()),
					e);
		}
	}

	@Override
	public TipoParaderoDTO findTipoParaderoByIdItem_idFuenteDatos(Long idItem, int fuenteDatos) {
		try {
			return mapper.toTipoParaderoGtpcDTO(tipoParaderoRepository.findByIdItem_idFuenteDatos(idItem, fuenteDatos));
		} catch (Exception e) {
			LoggingUtil.logException(String.format(
					"Error al consultar el tipo paradero por el idItem %s , fuenteDatos %s ", idItem, fuenteDatos), e);
			throw new Area247Exception(String.format(
					"Error al consultar el tipo paradero por el idItem %s , fuenteDatos %s ", idItem, fuenteDatos), e);
		}
	}

	@Override
	public void updateTipoParaderGtpc(TipoParaderoDTO tipoParaderoDTO) {
		try {
			TipoParadero tipoParaderoGtpc = mapper.toTipoParaderoGtpc(tipoParaderoDTO);
			tipoParaderoRepository.save(tipoParaderoGtpc);
			this.idTipoParadero = tipoParaderoGtpc.getId();
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de actualizar el tipo paradero con idItem %s y fuenteDtos %s",
							tipoParaderoDTO.getIdItem(), tipoParaderoDTO.getFuenteDatos()),
					e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar el tipo paradero con idItem %s y fuenteDtos %s",
							tipoParaderoDTO.getIdItem(), tipoParaderoDTO.getFuenteDatos()),
					e);
		}
	}

	@Override
	public void saveTipoParadertoGtpc(TipoParaderoDTO tipoParaderoDTO) {
		try {
			TipoParadero tipoParaderoGtpc = mapper.toTipoParaderoGtpc(tipoParaderoDTO);
			tipoParaderoRepository.save(tipoParaderoGtpc);
			this.idTipoParadero = tipoParaderoGtpc.getId();
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de insertar el tipo paradero con idItem %s y fuenteDtos %s",
							tipoParaderoDTO.getIdItem(), tipoParaderoDTO.getFuenteDatos()),
					e);
			throw new Area247Exception(
					String.format("Error al tratar de insertar el tipo paradero con idItem %s y fuenteDtos %s",
							tipoParaderoDTO.getIdItem(), tipoParaderoDTO.getFuenteDatos()),
					e);
		}
	}

	@Override
	public TipoOrientacionDTO findTipoOrientacionByIdItem_idFuenteDatos(Long idItem, int fuenteDatos) {
		try {
			return mapper.toTipoOrientacionGtpcDTO(
					tipoOrientacionRepository.findByIdItem_idFuenteDatos(idItem, fuenteDatos));
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al consultar el tipo orientación por el idItem %s , fuenteDatos %s ", idItem,
							fuenteDatos),
					e);
			throw new Area247Exception(
					String.format("Error al consultar el tipo orientación por el idItem %s , fuenteDatos %s ", idItem,
							fuenteDatos),
					e);
		}
	}

	@Override
	public void updateTipoOrientacionGtpc(TipoOrientacionDTO tipoOrientacionDTO) {
		try {
			TipoOrientacion tipoOrientacionGtpc = mapper.toTipoOrientacionGtpc(tipoOrientacionDTO);
			tipoOrientacionRepository.save(tipoOrientacionGtpc);
			this.idOrientacion = tipoOrientacionGtpc.getId();
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de actualizar el tipo orientación con idItem %s y fuenteDtos %s",
							tipoOrientacionDTO.getIdItem(), tipoOrientacionDTO.getFuenteDatos()),
					e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar el tipo orientación con idItem %s y fuenteDtos %s",
							tipoOrientacionDTO.getIdItem(), tipoOrientacionDTO.getFuenteDatos()),
					e);
		}

	}

	@Override
	public void saveTipoOrientacionGtpc(TipoOrientacionDTO tipoOrientacionDTO) {
		try {
			TipoOrientacion tipoOrientacionGtpc = mapper.toTipoOrientacionGtpc(tipoOrientacionDTO);
			tipoOrientacionRepository.save(tipoOrientacionGtpc);
			this.idOrientacion = tipoOrientacionGtpc.getId();
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de insertar el tipo orientación con idItem %s y fuenteDtos %s",
							tipoOrientacionDTO.getIdItem(), tipoOrientacionDTO.getFuenteDatos()),
					e);
			throw new Area247Exception(
					String.format("Error al tratar de insertar el tipo orientación con idItem %s y fuenteDtos %s",
							tipoOrientacionDTO.getIdItem(), tipoOrientacionDTO.getFuenteDatos()),
					e);
		}

	}

	@Override
	public ParaderoRutaDTO findParaderoRutaByIdItem_idFuenteDatos(Long idItem, int fuenteDatos) {
		try {
			return mapper.toParaderoRutaGtpcDTO(paraderoRutaRepository.findByIdItem_idFuenteDatos(idItem, fuenteDatos));
		} catch (Exception e) {
			LoggingUtil.logException(String.format(
					"Error al consultar el paradero ruta por el idItem %s , fuenteDatos %s ", idItem, fuenteDatos), e);
			throw new Area247Exception(String.format(
					"Error al consultar el paradero ruta por el idItem %s , fuenteDatos %s ", idItem, fuenteDatos), e);
		}
	}
	
	public ParaderoRutaDTO findParaderoRutaByCodigoAndFuenteDatos(String codigo, int fuenteDatos) {
		try {
			return mapper.toParaderoRutaGtpcDTO(paraderoRutaRepository.findByCodigoAndFuenteDatos(codigo, fuenteDatos));
		} catch (Exception e) {
			LoggingUtil.logException(String.format(
					"Error al consultar el paradero ruta por el codigo %s , fuenteDatos %s ", codigo, fuenteDatos), e);
			throw new Area247Exception(String.format(
					"Error al consultar el paradero ruta por el codigo %s , fuenteDatos %s ", codigo, fuenteDatos), e);
		}
	}

	@Override
	public void updateParaderoRutaGtpc(ParaderoRutaDTO paraderoRutaDTO) {
		try {
			ParaderoRuta paraderoRuta = mapper.toParaderoRutaGtpc(paraderoRutaDTO);
			paraderoRutaRepository.save(paraderoRuta);
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de actualizar el paradero ruta con idItem %s y fuenteDtos %s",
							paraderoRutaDTO.getIdItem(), paraderoRutaDTO.getFuenteDatos()),
					e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar el paradero ruta con idItem %s y fuenteDtos %s",
							paraderoRutaDTO.getIdItem(), paraderoRutaDTO.getFuenteDatos()),
					e);
		}
	}

	@Override
	public void saveParaderoRutaGtpc(ParaderoRutaDTO paraderoRutaDTO) {
		try {
			ParaderoRuta paraderoRuta = mapper.toParaderoRutaGtpc(paraderoRutaDTO);
			paraderoRutaRepository.save(paraderoRuta);
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de insertar el paradero ruta con idItem %s y fuenteDtos %s",
							paraderoRutaDTO.getIdItem(), paraderoRutaDTO.getFuenteDatos()),
					e);
			throw new Area247Exception(
					String.format("Error al tratar de insertar el paradero ruta con idItem %s y fuenteDtos %s",
							paraderoRutaDTO.getIdItem(), paraderoRutaDTO.getFuenteDatos()),
					e);
		}

	}

	@Override
	public List<ParaderoRutaDTO> obtenerParaderosCercanos(Double latitud, Double longitud, Float radio) {
		try {
			return mapper.mapToParaderoRutaDTO(
					(List<ParaderoRuta>) paraderoRutaRepository.findByLocalizacion(latitud, longitud, radio));
		} catch (Exception e) {
			LoggingUtil.logException(String.format(
					"Error al tratar de obtener los paraderos para la latidud: %s, longitud: %s y radio: %s.", latitud,
					longitud, radio), e);
			throw new Area247Exception(String.format(
					"Error al tratar de obtener los paraderos para la latidud: %s, longitud: %s y radio: %s.", latitud,
					longitud, radio), e);
		}
	}

	@Override
	public TipoParaderoDTO findTipoParaderoById(Long id) {
		return mapper.toTipoParaderoDTO(tipoParaderoRepository.findOne(id));
	}

	@Override
	public TipoOrientacionDTO findTipoOrientacionParaderoById(Long id) {
		return mapper.toTipoOrientacionGtpcDTO(tipoOrientacionRepository.findOne(id));
	}

	@Override
	public List<FrecuenciaRutaDTO> findFrecuenciaRutaByIdRuta(Long id) {
		try {
			return mapper.mapToFrecuenciaRutaDTO(
					(List<FrecuenciaRuta>) frecuenciaRutaRepository.findFrecuenciaRutaByIdRuta(id));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al consultar la frecuencia ruta por el id %s", id), e);
			throw new Area247Exception(String.format("Error al consultar la frecuencia ruta por el id %s", id), e);
		}
	}

	@Override
	public FrecuenciaRutaDTO findFrecuenciaRutaByIdItem_idFuenteDatos(Long idItem, int fuenteDatos) {
		try {
			return mapper
					.toFrecuenciaRutaGtpcDTO(frecuenciaRutaRepository.findByIdItem_idFuenteDatos(idItem, fuenteDatos));
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al consultar la frecuencia ruta por el idItem %s , fuenteDatos %s ", idItem,
							fuenteDatos),
					e);
			throw new Area247Exception(
					String.format("Error al consultar la frecuencia ruta por el idItem %s , fuenteDatos %s ", idItem,
							fuenteDatos),
					e);
		}
	}

	@Override
	public void updateParaderoRutaGtpc(FrecuenciaRutaDTO frecuenciaRutaDTO) {
		try {
			FrecuenciaRuta frecuenciaRuta = mapper.toFrecuenciaRutaGtpc(frecuenciaRutaDTO);
			frecuenciaRutaRepository.save(frecuenciaRuta);
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de actualizar la frecuencia ruta con idItem %s y fuenteDtos %s",
							frecuenciaRutaDTO.getIdItem(), frecuenciaRutaDTO.getFuenteDatos()),
					e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar la frecuencia ruta con idItem %s y fuenteDtos %s",
							frecuenciaRutaDTO.getIdItem(), frecuenciaRutaDTO.getFuenteDatos()),
					e);
		}

	}

	@Override
	public void saveParaderoRutaGtpc(FrecuenciaRutaDTO frecuenciaRutaDTO) {
		try {
			FrecuenciaRuta frecuenciaRuta = mapper.toFrecuenciaRutaGtpc(frecuenciaRutaDTO);
			frecuenciaRutaRepository.save(frecuenciaRuta);
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de insertar la frecuencia ruta con idItem %s y fuenteDtos %s",
							frecuenciaRutaDTO.getIdItem(), frecuenciaRutaDTO.getFuenteDatos()),
					e);
			throw new Area247Exception(
					String.format("Error al tratar de insertar la frecuencia ruta con idItem %s y fuenteDtos %s",
							frecuenciaRutaDTO.getIdItem(), frecuenciaRutaDTO.getFuenteDatos()),
					e);
		}

	}

	@Override
	public List<HorarioRutaDTO> findHorarioRutaByIdRuta(Long id) {
		try {
			return mapper.mapHorariosRutaDTO((List<HorarioRuta>) horarioRutaRepository.findByIdRuta(id));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al consultar los  horarios ruta por el idRuta %s", id), e);
			throw new Area247Exception(String.format("Error al consultar los horarios ruta por el idRuta %s", id), e);
		}
	}

	@Override
	public HorarioRutaDTO findHorarioRutaByIdItem_idFuenteDatos(Long idItem, int fuenteDatos) {
		try {
			return mapper.toHorarioRutaGtpcDTO(horarioRutaRepository.findByIdItem_idFuenteDatos(idItem, fuenteDatos));
		} catch (Exception e) {
			LoggingUtil.logException(String.format(
					"Error al consultar el horario ruta por el idItem %s , fuenteDatos %s ", idItem, fuenteDatos), e);
			throw new Area247Exception(String.format(
					"Error al consultar el horario ruta por el idItem %s , fuenteDatos %s ", idItem, fuenteDatos), e);
		}
	}

	@Override
	public void updateHorarioRutaGtpc(HorarioRutaDTO horarioRutaDTO) {
		try {
			HorarioRuta horarioRuta = mapper.toHorarioRutaGtpc(horarioRutaDTO);
			horarioRutaRepository.save(horarioRuta);
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de actualizar el horario ruta con idItem %s y fuenteDtos %s",
							horarioRutaDTO.getIdItem(), horarioRutaDTO.getFuenteDatos()),
					e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar el horario ruta con idItem %s y fuenteDtos %s",
							horarioRutaDTO.getIdItem(), horarioRutaDTO.getFuenteDatos()),
					e);
		}
	}

	@Override
	public void saveHorarioRutaGtpc(HorarioRutaDTO horarioRutaDTO) {
		try {
			HorarioRuta horarioRuta = mapper.toHorarioRutaGtpc(horarioRutaDTO);
			horarioRutaRepository.save(horarioRuta);
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de insertar el horario ruta con idItem %s y fuenteDtos %s",
							horarioRutaDTO.getIdItem(), horarioRutaDTO.getFuenteDatos()),
					e);
			throw new Area247Exception(
					String.format("Error al tratar de insertar el horario ruta con idItem %s y fuenteDtos %s",
							horarioRutaDTO.getIdItem(), horarioRutaDTO.getFuenteDatos()),
					e);
		}
	}

	@Override
	public OperadorDTO findOperadorByIdItem_idFuenteDatos(Long idItem) {
		try {
			return mapper.toOperadorDTO(operadorRepository.findByIdItem(idItem));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al consultar el operador por el idItem %s", idItem), e);
			throw new Area247Exception(String.format("Error al consultar el operador por el idItem %s", idItem), e);
		}
	}

	@Override
	public void updateOperador(OperadorDTO operadorDTO) {
		try {
			Operador operador = mapper.toOperador(operadorDTO);
			operadorRepository.save(operador);
			this.idOperador = operador.getId();
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de actualizar el operador con idItem %s", operadorDTO.getIdItem()),
					e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar el operador con idItem %s", operadorDTO.getIdItem()),
					e);
		}
	}

	@Override
	public void saveOperador(OperadorDTO operadorDTO) {
		try {
			Operador operador = mapper.toOperador(operadorDTO);
			operadorRepository.save(operador);
			operadorDTO.setId(operador.getId());
			this.idOperador = operador.getId();
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de insertar el operador con idItem %s", operadorDTO.getIdItem()), e);
			throw new Area247Exception(
					String.format("Error al tratar de insertar el operador con idItem %s", operadorDTO.getIdItem()), e);
		}
	}

	@Override
	public EmpresaTransporteDTO findEmpresaTransporteByIdItem_idFuenteDatos(Long idItem) {
		try {
			return mapper.toEmpresaTransporteDTO(empresaTransporteRepository.findByIdItem(idItem));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al consultar la empresa transporte por el idItem %s", idItem),
					e);
			throw new Area247Exception(
					String.format("Error al consultar la empresa transporte por el idItem %s", idItem), e);
		}
	}

	@Override
	public void updateEmpresaTransporte(EmpresaTransporteDTO empresaTransporteDTO) {
		try {
			EmpresaTransporte empresaTransporte = mapper.toEmpresaTransporte(empresaTransporteDTO);
			empresaTransporteRepository.save(empresaTransporte);
			this.idEmpresaTransporte = empresaTransporte.getId();
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al tratar de actualizar la empresa transporte con idItem %s",
					empresaTransporteDTO.getIdItem()), e);
			throw new Area247Exception(
					String.format("Error al tratar de actualizar la empresa transporte con idItem %s",
							empresaTransporteDTO.getIdItem()),
					e);
		}
	}

	@Override
	public void saveEmpresaTransporte(EmpresaTransporteDTO empresaTransporteDTO) {
		try {
			EmpresaTransporte empresaTransporte = mapper.toEmpresaTransporte(empresaTransporteDTO);
			empresaTransporteRepository.save(empresaTransporte);
			empresaTransporteDTO.setId(empresaTransporte.getId());
			this.idEmpresaTransporte = empresaTransporte.getId();
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al tratar de insertar la empresa transporte con idItem %s",
					empresaTransporteDTO.getIdItem()), e);
			throw new Area247Exception(String.format("Error al tratar de insertar la empresa transporte con idItem %s",
					empresaTransporteDTO.getIdItem()), e);
		}
	}

	@Override
	public List<EmpresaTransporteDTO> findEmpresaTransporteByIdRuta(Long idRuta) {
		try {
			List<EmpresaRutaDTO> empresaRutaDTO = new ArrayList<>();
			empresaRutaDTO = mapper
					.mapEmpresaRutaDTO((List<EmpresaRuta>) empresaRutaRepository.findEmpresaRutaByIdRuta(idRuta));
			List<Long> idEmpresas = new ArrayList<>();
			if (!empresaRutaDTO.isEmpty()) {
				for (int i = 0; i < empresaRutaDTO.size(); i++) {
					idEmpresas.add(empresaRutaDTO.get(i).getIdEmpresaTransporte());
				}
			} else {
				idEmpresas.add((long) -1);
			}

			return mapper.mapEmpresaTransporteDTO(
					(List<EmpresaTransporte>) empresaTransporteRepository.findByIds(idEmpresas));
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al consultar las empresas transporte por el idRuta %s", idRuta), e);
			throw new Area247Exception(
					String.format("Error al consultar las empresas transporte por el idRuta  %s", idRuta), e);
		}
	}

	private Set<OperadorEmpresaDTO> sincronizarIdsOperadorEmpresa(List<OperadorEmpresaDTO> operadoresEmpresadto,
			List<EmpresaTransporteDTO> empresasTransporteDTO, List<OperadorDTO> operadoresDTO) {

		Set<OperadorEmpresaDTO> idsSincronizados = new HashSet<>();

		if (!Utils.isNull(operadoresEmpresadto)) {
			for (OperadorEmpresaDTO operadorEmpresaDTO : operadoresEmpresadto) {

				Optional operadorOptional = operadoresDTO.stream()
						.filter(p -> p.getIdItem().equals(operadorEmpresaDTO.getIdOperador())).findFirst();

				Optional empresaTransporteOptional = empresasTransporteDTO.stream()
						.filter(p -> p.getIdItem().equals(operadorEmpresaDTO.getIdEmpresaTransporte())).findFirst();

				if (operadorOptional.isPresent() && empresaTransporteOptional.isPresent()) {
					OperadorDTO operadorDTO = (OperadorDTO) operadorOptional.get();
					EmpresaTransporteDTO empresaTransporteDTO = (EmpresaTransporteDTO) empresaTransporteOptional.get();
					idsSincronizados.add(new OperadorEmpresaDTO(operadorDTO.getId(), empresaTransporteDTO.getId(),
							operadorEmpresaDTO.getActivo()));
				}
			}
		}

		return idsSincronizados;
	}

	@Override
	public OperadorEmpresaDTO findOperadorEmpresaByIdItem_idFuenteDatos(Long idItem) {
		try {
			return mapper.toOperadorEmpresaDTO(operadorEmpresaRepository.findByIdItem(idItem));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al consultar el operador empresa por el idItem %s", idItem),
					e);
			throw new Area247Exception(String.format("Error al consultar el operador empresa por el idItem %s", idItem),
					e);
		}
	}

	@Override
	public void updateOperadorEmpresa(OperadorEmpresaDTO operadorEmpresaDTO) {
		try {
			OperadorEmpresa operadorEmpresa = mapper.toOperadorEmpresa(operadorEmpresaDTO);
			operadorEmpresaRepository.save(operadorEmpresa);
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al tratar de actualizar el operador empresa con idItem %s",
					operadorEmpresaDTO.getIdItem()), e);
			throw new Area247Exception(String.format("Error al tratar de actualizar el operador empresa con idItem %s",
					operadorEmpresaDTO.getIdItem()), e);
		}
	}

	@Override
	public void saveOperadorEmpresa(OperadorEmpresaDTO operadorEmpresaDTO) {
		try {
			OperadorEmpresa operadorEmpresa = mapper.toOperadorEmpresa(operadorEmpresaDTO);
			operadorEmpresaRepository.save(operadorEmpresa);
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al tratar de insertar el operador empresa con idItem %s",
					operadorEmpresaDTO.getIdItem()), e);
			throw new Area247Exception(String.format("Error al tratar de insertar el operador empresa con idItem %s",
					operadorEmpresaDTO.getIdItem()), e);
		}
	}

	@Override
	public List<RutaGtpcDTO> obtenerRutasCercanasGtpc(Double latitud, Double longitud, Float radio) {
		try {
			return mapper.mapRutaGtpcDTO((List<Ruta>) rutaRepository.rutaCercana(latitud, longitud, radio));
		} catch (Exception e) {
			LoggingUtil.logException(String.format(
					"Error al tratar de obtener las rutas gtpc para la latidud: %s, longitud: %s y radio: %s.", latitud,
					longitud, radio), e);
			throw new Area247Exception(String.format(
					"Error al tratar de obtener las rutas gtpc para la latidud: %s, longitud: %s y radio: %s.", latitud,
					longitud, radio), e);
		}
	}

	@Override
	public List<ParaderoRutaDTO> obtenerParaderos(Long idRuta) {
		try {
			return mapper.mapToParaderoRutaDTO((List<ParaderoRuta>) paraderoRutaRepository.findByIdRuta(idRuta));
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de obtener los paraderos para el parametro.", idRuta), e);
			throw new Area247Exception(
					String.format("Error al tratar de obtener los paraderos para el parametro.", idRuta), e);
		}
	}

	@Override
	public List<RutaGtpcDTO> findByCodigoOrDescripcion(String parametro) {
		try {
			return mapper.mapRutaGtpcDTO((List<Ruta>) rutaRepository.findByCodigoOrDescripcion(parametro));
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de obtener las rutas gtpc para el parametro: %s.", parametro), e);
			throw new Area247Exception(
					String.format("Error al tratar de obtener las rutas gtpc para el parametro: %s.", parametro), e);
		}
	}
	
	private static final boolean isRutaValida(RutaGtpcWSDTO rutaGtpcWSDTO) {		
		return (!Utils.isNull(rutaGtpcWSDTO) && rutaGtpcWSDTO.getCoordenadas() != null);
	}

	 // TODO eliminar metodo cuando el WS de GTPC devuelva los horarios en formato HH:mm:ss
    @Deprecated
    private static String obtenerHora(String fecha) {
    	if (fecha != null && !fecha.isEmpty()) {
    		String[] partesFecha = fecha.split(" ");
    		if (partesFecha != null && partesFecha.length > 1) {
    			return partesFecha[3];
    		} else {
    			return fecha;
    		}
    	}
    	return "";
    }
    
    /**
	 * Convierte una cadena que contiene una hora a segundos 
	 * @param hora - cadena que contiene una hora en formato hh:mm:ss
	 * @return numero de segundos que representa la hora de la cadena
	 * @see MetroGtfsServiceImpl#formatearSegundos(int)
	 * TODO eliminar cuando GTPC realice los calculos para GTFS
	 */
    @Deprecated
	private static int horaStringAInt(String hora) {
		Time horaTime = Time.valueOf(hora);
		return ((horaTime.getHours() * 3600) + (horaTime.getMinutes() * 60) + horaTime.getSeconds());
	}
	
	// TODO se elimina cuando GTPC realice los calculos para GTFS
	@Deprecated
	private static long calcularFrecuenciaEntreDespachos(Long numDespachos, String horaInicio, String horaFin) {
		
		int horaInicioSeg = horaStringAInt(horaInicio);
		int horaFinSeg = horaStringAInt(horaFin);
		
		int segundos = horaFinSeg - horaInicioSeg;
		
		return segundos / numDespachos;
		
	}
}
