package co.gov.metropol.area247.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.annotations.Log;
import co.gov.metropol.area247.annotations.LogReturnValue;
import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.model.EstructuraDeArchivoDTO;
import co.gov.metropol.area247.repository.TipoArchivoRepository;
import co.gov.metropol.area247.repository.domain.EstructuraDeArchivo;
import co.gov.metropol.area247.repository.domain.TipoArchivo;
import co.gov.metropol.area247.service.IEstructuraDeArchivoService;
import co.gov.metropol.area247.service.impl.abstracts.AbstractEstructuraDeArchivoService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class EstructuraDeArchivoServiceImpl extends AbstractEstructuraDeArchivoService implements IEstructuraDeArchivoService{
	
	@Autowired
	private TipoArchivoRepository tipoArchivoRepository;

	@Override
	@LogReturnValue
	public List<EstructuraDeArchivoDTO> findAll(@Log Long tipoArchivoId) {
		try {
			TipoArchivo tipoArchivo = tipoArchivoRepository.findOne(tipoArchivoId);
			if(!Utils.isNull(tipoArchivo)){
				return mapper.mapToEstructuraDeArchivoDTO((List<EstructuraDeArchivo>) repository.findByTipoArchivoOrderByOrden(tipoArchivo));
			}
			return new ArrayList<>();
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al obtener las estructuras para el tipo de carga %s", tipoArchivoId), e);
			throw new Area247Exception(String.format("Error al obtener las estructuras para el tipo de carga %s", tipoArchivoId), e);
		}
	}

	@Override
	@LogReturnValue
	public List<EstructuraDeArchivoDTO> findByEnabled(@Log boolean enabled, @Log Long tipoArchivoId) {
		try {
			TipoArchivo tipoArchivo = tipoArchivoRepository.findOne(tipoArchivoId);
			if(!Utils.isNull(tipoArchivo)){
				return mapper.mapToEstructuraDeArchivoDTO((List<EstructuraDeArchivo>) repository.findByTipoArchivoAndEnabledOrderByOrden(tipoArchivo, enabled));
			}
			return null;
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al obtener las estructuras para el tipo de carga %s con el estado %s", tipoArchivoId, enabled), e);
			throw new Area247Exception(String.format("Error al obtener las estructuras para el tipo de carga %s con el estado %s", tipoArchivoId, enabled), e);
		}
	}

	@Override
	@LogReturnValue
	public EstructuraDeArchivoDTO findById(Long estructuraId) {
		try {
			return mapper.toEstructuraDeArchivoDTO(repository.findOne(estructuraId));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al obtener la estructura %s", estructuraId), e);
			throw new Area247Exception(String.format("Error al obtener la estructura %s", estructuraId), e);
		}
	}

	@Override
	public void save(@Log EstructuraDeArchivoDTO estructuraDeArchivoDTO) {
		try {
			EstructuraDeArchivo estructuraDeArchivo = mapper.toEstructuraDeArchivo(estructuraDeArchivoDTO);
			List<EstructuraDeArchivoDTO> estructurasDTOs = findAll(estructuraDeArchivoDTO.getTipoArchivo().getId());
			if(!Utils.isNull(estructurasDTOs)){
				estructuraDeArchivo.setOrden(Utils.longValue(estructurasDTOs.size(), 1));
			}else{
				estructuraDeArchivo.setOrden(1L);
			}
			
			repository.save(estructuraDeArchivo);
			estructuraDeArchivoDTO.setOrden(estructuraDeArchivo.getOrden());
			estructuraDeArchivoDTO.setId(estructuraDeArchivo.getId());
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al tratar de guardar la estructura de archivo %s.", estructuraDeArchivoDTO), e);
			throw new Area247Exception(String.format("Error al tratar de guardar la estructura de archivo %s.", estructuraDeArchivoDTO), e);
		}
	}

	@Override
	public void update(@Log EstructuraDeArchivoDTO estructuraDeArchivoDTO) {
		try {
			EstructuraDeArchivo estructuraDeArchivo = mapper.toEstructuraDeArchivo(estructuraDeArchivoDTO);
			repository.save(estructuraDeArchivo);
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al tratar de actualizar la estructura de archivo %s.", estructuraDeArchivoDTO), e);
			throw new Area247Exception(String.format("Error al tratar de actualizar la estructura de archivo %s.", estructuraDeArchivoDTO), e);
		}
	}

	@Override
	public void delete(@Log Long estructuraId) {
		try {
			repository.delete(estructuraId);
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al tratar de borrar la estructura %s.", estructuraId), e);
			throw new Area247Exception(String.format("Error al tratar de borrar la estructura %s.", estructuraId), e);
		}
	}

	@Override
	public void delete(@Log Long[] estructuraIds, @Log Long tipoArchivoId) {
		for (Long estructuraId : estructuraIds) {
			try {
				delete(estructuraId);
			} catch (Exception e) {
				LoggingUtil.logException(String.format("Error al tratar de borrar la estructura %s.", estructuraId), e);
				throw new Area247Exception(String.format("Error al tratar de borrar la estructura %s.", estructuraId), e);
			}
		}
		
		updateOrderToStructure(tipoArchivoId);
		
	}

	@Override
	public void updateOrderToStructure(@Log Long tipoArchivoId) {
		List<EstructuraDeArchivoDTO> estructuraDeArchivoDTOs = findAll(tipoArchivoId);
		
		AtomicInteger counter = new AtomicInteger(0);
		estructuraDeArchivoDTOs.forEach(estructura -> {
			try {
				long order = counter.incrementAndGet();
				estructura.setOrden(order);
				update(estructura);
			} catch (Exception e) {
				LoggingUtil.logException(String.format("Error al tratar actualizar el orden de la estructura %s.", estructura.getId()), e);
				throw new Area247Exception(String.format("Error al tratar actualizar el orden de la estructura %s.", estructura.getId()), e);
			}
			
		});
		
	}
	
	@Override
	@LogReturnValue
	public boolean updateEstructuraAndOrden(@Log EstructuraDeArchivoDTO estructuraDeArchivoDTO){
		try {
			
			TipoArchivo tipoArchivo = tipoArchivoRepository.findOne(estructuraDeArchivoDTO.getTipoArchivo().getId());
			
			if(repository.countByTipoArchivo(tipoArchivo) >= estructuraDeArchivoDTO.getOrden()){
				update(estructuraDeArchivoDTO);
				
				List<EstructuraDeArchivoDTO> estructuraDeArchivoDTOs = mapper.mapToEstructuraDeArchivoDTO((List<EstructuraDeArchivo>)repository.findByTipoArchivoOrderByOrdenAscUltimaModificacionDesc(tipoArchivo)) ;
				
				AtomicInteger counter = new AtomicInteger(0);
				estructuraDeArchivoDTOs.forEach(estructura -> {
					try {
						long order = counter.incrementAndGet();
						estructura.setOrden(order);
						update(estructura);
					} catch (Exception e) {
						LoggingUtil.logException(String.format("Error al tratar actualizar el orden de la estructura %s.", estructura.getId()), e);
						throw new Area247Exception(String.format("Error al tratar actualizar el orden de la estructura %s.", estructura.getId()), e);
					}
					
				});
				
				return true;
			}else{
				return false;
			}
			
		} catch (Exception e) {
			String.format(String.format("Error al tratar de actualizar la estructura de archivo %s.", estructuraDeArchivoDTO), e);
			throw new Area247Exception(String.format("Error al tratar de actualizar la estructura de archivo %s.", estructuraDeArchivoDTO), e);
		}
	}
}
