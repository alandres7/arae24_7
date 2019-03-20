package co.gov.metropol.area247.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import co.gov.metropol.area247.model.EmpresaRutaDTO;
import co.gov.metropol.area247.model.EmpresaTransporteDTO;
import co.gov.metropol.area247.model.RutaMetroDTO;
import co.gov.metropol.area247.model.mapper.EmpresaRutaMapper;
import co.gov.metropol.area247.model.mapper.EmpresaTransporteMapper;
import co.gov.metropol.area247.repository.EmpresaRutaRepository;
import co.gov.metropol.area247.repository.EmpresaTransporteRepository;
import co.gov.metropol.area247.repository.domain.EmpresaRuta;
import co.gov.metropol.area247.repository.domain.EmpresaTransporte;
import co.gov.metropol.area247.service.IEmpresaTransporteService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class EmpresaTransporteServiceImpl implements IEmpresaTransporteService {
	
	@Autowired
	private EmpresaTransporteMapper mapper;
	
	@Autowired
	private EmpresaTransporteRepository repository;
	
	@Autowired
	private EmpresaRutaRepository empresaRutaRepository;
	
	@Autowired
	private EmpresaRutaMapper empresaRutaMapper;
	
	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IEmpresaTransporteService#findByIdRuta(java.lang.Long)
	 */
	@Override
	public List<EmpresaTransporteDTO> findByIdRuta(Long idRuta) {
		try {
			if (!Utils.isNull(idRuta)) {
				return mapper.mapToEmpresaTransporteDTO(repository.findByIdRuta(idRuta));
			}
			return Lists.newArrayList();
		} catch (Exception e) {
			throw new Area247Exception(String.format(
					"Error al obtener las empresas de transporte segun el identificador de la ruta %s.", idRuta), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IEmpresaTransporteService#findByNombre(java.lang.String)
	 */
	@Override
	public EmpresaTransporteDTO findByNombre(String nombre) {
		try {
			return mapper.toEmpresaTransporteDTO(repository.findByNombre(nombre));
		} catch (Exception e) {
			throw new Area247Exception(String.format(
					"Error al obtener las empresas de transporte segun el nombre de la ruta %s.", nombre), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IEmpresaTransporteService#findAllActivas()
	 */
	@Override
	public List<EmpresaTransporteDTO> findAllActivas() {
		try {
			return mapper.mapToEmpresaTransporteDTO(repository.findAllActivas());
		} catch (Exception e) {
			throw new Area247Exception("Error al obtener las empresas de transporte activas.");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IEmpresaTransporteService#procesarEmpresasTransporte(java.util.List)
	 */
	@Override
	public void procesarEmpresasTransporte(List<EmpresaTransporteDTO> empresasTransporteDTO, Long idRuta) {
		
		if (Utils.isNotEmpty(empresasTransporteDTO)) {
			// Guardar las empresas y crear las relaciones
			List<EmpresaRutaDTO> empresasRutaDTO = Lists.newArrayList();
			for (EmpresaTransporteDTO empresaTransporteDTO : empresasTransporteDTO) {
				EmpresaTransporteDTO empresaTransporteDTOAux = findByNombre(empresaTransporteDTO.getNombre());
				
				if (!Utils.isNull(empresaTransporteDTOAux)) {
					empresaTransporteDTO.setId(empresaTransporteDTOAux.getId());
				}
				
				save(empresaTransporteDTO);
				
				EmpresaRutaDTO empresaRutaDTO = new EmpresaRutaDTO();
				empresaRutaDTO.setIdEmpresaTransporte(empresaTransporteDTO.getId());
				empresaRutaDTO.setIdRuta(idRuta);
				empresaRutaDTO.setActivo("S");
				empresasRutaDTO.add(empresaRutaDTO);
			}
			// Guardar las relaciones //
			// Obtener las relaciones existentes
			List<EmpresaRutaDTO> empresasRutaDTOAux = empresaRutaMapper.mapToEmpresaRutaDTO(empresaRutaRepository.findEmpresaRutaByIdRuta(idRuta));
			// Eliminar identicas de ambas listas
			diferenciaSimetrica(empresasRutaDTO, empresasRutaDTOAux);
			// Eliminar sobrantes
			deleteAll(empresasRutaDTOAux);
			// Persistir nueva informacion
			saveAll(empresasRutaDTO);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IEmpresaTransporteService#save(co.gov.metropol.area247.model.EmpresaTransporteDTO)
	 */
	@Override
	public void save(EmpresaTransporteDTO empresaTransporteDTO) {
		try {
			EmpresaTransporte empresaTransporte = mapper.toEmpresaTransporte(empresaTransporteDTO);
			repository.save(empresaTransporte);
			empresaTransporteDTO.setId(empresaTransporte.getId());
		} catch (Exception e) {
			throw new Area247Exception(
					String.format("Error al tratar de persistir la informacion de la empresa de transporte %s.",
							empresaTransporteDTO),
					e);
		}
	}
	
	/**
	 * Realiza la operacion de la diferencia simetrica en ambas listas, es decir
	 * en cada lista elimina los objetos que esten repetidos en ambas listas.
	 * e.g: 
	 * <ul>
	 * <li>Actuales   = [EmpresaRutaDTO(null, 1 (IdEmpresa), 1 (IdRuta)),
	 * EmpresaRutaDTO(null, 2, 1)]</li>
	 * <li>Existentes = [EmpresaRutaDTO(1 (IdEmpresaRuta),
	 * 1 (IdEmpresa), 1 (IdRuta))]</li>
	 * </ul>
	 * <P>
	 * Entonces en Actuales se eliminaria el primer elemento y en Existentes se
	 * elimina el unico elemento porque el id de la empresa y ruta coincide con
	 * el primer objeto del la lista de Actuales
	 * <P>
	 * Creado 14/08/2018 1:57 p.m
	 * 
	 * @param actuales
	 *            - a comparar
	 * @param existentes
	 *            - a comparar
	 */
	private void diferenciaSimetrica(List<EmpresaRutaDTO> actuales, List<EmpresaRutaDTO> existentes) {
		// Dejamos los que se van a persistir
		@SuppressWarnings("unchecked")
		List<EmpresaRutaDTO> actualesCopia = (List<EmpresaRutaDTO>) ((ArrayList<EmpresaRutaDTO>) actuales).clone();
		actuales.removeIf(eliminarRepetidos(existentes));
		// Dejamos los que se van a eliminar
		existentes.removeIf(eliminarRepetidos(actualesCopia));
	}
	
	private Predicate<EmpresaRutaDTO> eliminarRepetidos(List<EmpresaRutaDTO> otraCosa) {
		return p -> contieneEmpresaRuta(otraCosa, p.getIdEmpresaTransporte(), p.getIdRuta());		
	}
	
	/**
	 * Valida si el id de la empresa y ruta se encuentran en un objeto de la
	 * lista de objetos {@link EmpresaRutaDTO}
	 * <P>Creado 14/08/2018 1:43 p.m
	 * 
	 * @param empresasRuta
	 *            - lista de objetos {@link EmpresaRutaDTO} sobre la cual se
	 *            validara
	 * @param idEmpresa
	 *            - filtro
	 * @param idRuta
	 *            - filtro
	 * @return <code>true</code> si el id de la empresa y ruta se encuentran en
	 *         un objeto tipo {@link EmpresaRutaDTO}
	 */
	private boolean contieneEmpresaRuta(List<EmpresaRutaDTO> empresasRuta, Long idEmpresa, Long idRuta) {

		for (EmpresaRutaDTO empresaRutaDTO : empresasRuta) {
			if (empresaRutaDTO.getIdEmpresaTransporte().equals(idEmpresa)
					&& empresaRutaDTO.getIdRuta().equals(idRuta)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IEmpresaTransporteService#save(co.gov.metropol.area247.model.EmpresaRutaDTO)
	 */
	@Override
	public void save(EmpresaRutaDTO empresaRutaDTO) {
		try {
			EmpresaRuta empresaRuta = empresaRutaMapper.toEmpresaRuta(empresaRutaDTO);
			empresaRutaRepository.save(empresaRuta);
			empresaRutaDTO.setId(empresaRuta.getId());
		} catch (Exception e) {
			throw new Area247Exception(
					String.format("Error al tratar de persistir la informacion de la empresa ruta %s.",
							empresaRutaDTO),
					e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IEmpresaTransporteService#saveAll(java.util.List)
	 */
	@Override
	public void saveAll(List<EmpresaRutaDTO> empresasRutaDTO) {
		if (Utils.isNotEmpty(empresasRutaDTO)) {
			for (EmpresaRutaDTO empresaRutaDTO : empresasRutaDTO) {
				save(empresaRutaDTO);
			}
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IEmpresaTransporteService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		try {
			empresaRutaRepository.delete(id);
		} catch (Exception e) {
			throw new Area247Exception("Error al eliminar la informacion de empresa ruta", e.getCause());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.gov.metropol.area247.service.IEmpresaTransporteService#deleteAll(java.util.List)
	 */
	@Override
	public void deleteAll(List<EmpresaRutaDTO> empresasRutaDTO) {
		if (Utils.isNotEmpty(empresasRutaDTO)) {
			for (EmpresaRutaDTO empresaRutaDTO : empresasRutaDTO) {
				delete(empresaRutaDTO.getId());
			}
		}
	}

}
