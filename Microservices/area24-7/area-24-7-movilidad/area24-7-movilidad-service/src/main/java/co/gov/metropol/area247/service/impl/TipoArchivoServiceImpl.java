package co.gov.metropol.area247.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import co.gov.metropol.area247.annotations.Log;
import co.gov.metropol.area247.annotations.LogReturnValue;
import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.model.TipoArchivoDTO;
import co.gov.metropol.area247.repository.domain.TipoArchivo;
import co.gov.metropol.area247.repository.domain.support.enums.ExtensionesDeArchivo;
import co.gov.metropol.area247.service.ITipoArchivoService;
import co.gov.metropol.area247.service.impl.abstracts.AbstractTipoArchivoService;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class TipoArchivoServiceImpl extends AbstractTipoArchivoService implements ITipoArchivoService{
	
	@Override
	@LogReturnValue
	public List<TipoArchivoDTO> findAll() {
		try {
			return mapper.mapToTipoArchivoDTO((List<TipoArchivo>) repository.findAll());
		} catch (Exception e) {
			LoggingUtil.logException("Error al obtener todos los tipos de carga de archivo", e);
			throw new Area247Exception("Error al obtener todos los tipos de carga de archivo", e);
		}
	}

	@Override
	@LogReturnValue
	public List<TipoArchivoDTO> findByEnabled(@Log boolean enabled) {
		try {
			return mapper.mapToTipoArchivoDTO((List<TipoArchivo>) repository.findByEnabledEquals(enabled));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al obtener todos los tipos de carga de archivo con estado de activacion %s.", enabled), e);
			throw new Area247Exception(String.format("Error al obtener todos los tipos de carga de archivo con estado de activacion %s.", enabled), e);
		}
	}

	@Override
	@LogReturnValue
	public List<TipoArchivoDTO> findByExtension(@Log ExtensionesDeArchivo extension) {
		try {
			return mapper.mapToTipoArchivoDTO((List<TipoArchivo>) repository.findByExtensionEquals(extension));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al obtener todos los tipos de carga de archivo con extension de archivo %s.", extension.toString()), e);
			throw new Area247Exception(String.format("Error al obtener todos los tipos de carga de archivo con extension de archivo %s.", extension.toString()), e);
		}
	}

	@Override
	@LogReturnValue
	public TipoArchivoDTO findById(@Log Long loadFileTypeId) {
		try {
			return mapper.toTipoArchivoDTO(repository.findOne(loadFileTypeId));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al obtener el tipo de carga de archivo con identificador %s.", loadFileTypeId), e);
			throw new Area247Exception(String.format("Error al obtener el tipo de carga de archivo con identificador %s.", loadFileTypeId), e);
		}
	}

	@Override
	@LogReturnValue
	public TipoArchivoDTO findByName(@Log String name) {
		try {
			return mapper.toTipoArchivoDTO(repository.findByNombreEquals(name));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al obtener el tipo de carga de archivo con el nombre %s.", name), e);
			throw new Area247Exception(String.format("Error al obtener el tipo de carga de archivo con el nombre %s.", name), e);
		}
	}

	@Override
	public void save(@Log TipoArchivoDTO loadFileTypeDTO) {
		try {
			TipoArchivo loadFileType = mapper.toTipoArchivo(loadFileTypeDTO);
			repository.save(loadFileType);
			loadFileTypeDTO.setId(loadFileType.getId());
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al tratar de guardar el tipo de carga de archivo %s.", loadFileTypeDTO), e);
			throw new Area247Exception(String.format("Error al tratar de guardar el tipo de carga de archivo %s.", loadFileTypeDTO), e);
		}
		
	}

	@Override
	public void update(@Log TipoArchivoDTO loadFileTypeDTO) {
		try {
			TipoArchivo loadFileType = mapper.toTipoArchivo(loadFileTypeDTO);
			repository.save(loadFileType);
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al tratar de actualizar el tipo de carga de archivo %s.", loadFileTypeDTO), e);
			throw new Area247Exception(String.format("Error al tratar de actualizar el tipo de carga de archivo %s.", loadFileTypeDTO), e);
		}
	}

	@Override
	public void delete(@Log Long loadFileTypeId) {
		try {
			repository.delete(loadFileTypeId);
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al tratar de borrar el tipo de carga de archivo con id %s.", loadFileTypeId), e);
			throw new Area247Exception(String.format("Error al tratar de borrar el tipo de carga de archivo con id %s.", loadFileTypeId), e);
		}
		
	}

	@Override
	public void delete(@Log Long[] loadFileTypeIds) {
		for (Long loadFileTypeId : loadFileTypeIds) {
			try {
				delete(loadFileTypeId);
			} catch (Exception e) {
				LoggingUtil.logException(String.format("Error al tratar de borrar el tipo de carga de archivo %s.", loadFileTypeId), e);
				throw new Area247Exception(String.format("Error al tratar de borrar el tipo de carga de archivo %s.", loadFileTypeId), e);
			}
		}
	}

}
