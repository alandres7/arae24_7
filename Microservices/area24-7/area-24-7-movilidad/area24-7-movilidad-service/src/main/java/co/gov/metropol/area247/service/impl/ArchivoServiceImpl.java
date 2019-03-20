package co.gov.metropol.area247.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.annotations.Log;
import co.gov.metropol.area247.annotations.LogReturnValue;
import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.model.ArchivoDTO;
import co.gov.metropol.area247.repository.TipoArchivoRepository;
import co.gov.metropol.area247.repository.domain.Archivo;
import co.gov.metropol.area247.repository.domain.TipoArchivo;
import co.gov.metropol.area247.service.IArchivoService;
import co.gov.metropol.area247.service.impl.abstracts.AbstractArchivoService;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class ArchivoServiceImpl extends AbstractArchivoService implements IArchivoService{
		
	@Autowired
	private TipoArchivoRepository tipoArchivoRepository;

	@Override
	@LogReturnValue
	public List<ArchivoDTO> findAll() {
		try {
			return mapper.mapToArchivoDTO((List<Archivo>)repository.findAll());
		} catch (Exception e) {
			LoggingUtil.logException("Error al obtener los archivos cargados.", e);
			throw new Area247Exception("Error al obtener los archivos cargados.", e);
		}
	}

	@Override
	@LogReturnValue
	public List<ArchivoDTO> findByEnabled(@Log boolean enabled) {
		try {
			return mapper.mapToArchivoDTO((List<Archivo>)repository.findByEnabled(enabled));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al obtener los archivos cargados activos %s.",enabled), e);
			throw new Area247Exception(String.format("Error al obtener los archivos cargados activos %s.",enabled), e);
		}
	}

	@Override
	@LogReturnValue
	public List<ArchivoDTO> findByTipoArchivo(@Log Long tipoArchivoId) {
		try {
			TipoArchivo tipoArchivo = tipoArchivoRepository.findOne(tipoArchivoId);
			return mapper.mapToArchivoDTO((List<Archivo>)repository.findByTipoArchivo(tipoArchivo));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al obtener los archivos cargados para el tipo de archivo %s.",tipoArchivoId), e);
			throw new Area247Exception(String.format("Error al obtener los archivos cargados para el tipo de archivo %s.",tipoArchivoId), e);
		}
	}

	@Override
	@LogReturnValue
	public List<ArchivoDTO> findByFechaCarga(@Log Date fechaCarga) {
		try {
			return mapper.mapToArchivoDTO((List<Archivo>)repository.findByFechaCarga(fechaCarga));
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al obtener los archivos cargados cargados en la fecha %s.", fechaCarga), e);
			throw new Area247Exception(String.format("Error al obtener los archivos cargados cargados en la fecha %s.", fechaCarga), e);
		}
	}
	
	@Override
	@LogReturnValue
	public void save(@Log ArchivoDTO archivoDTO) {
		try {
			Archivo archivo = mapper.toArchivo(archivoDTO);
			repository.save(archivo);
			archivoDTO.setId(archivo.getId());
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al guardar el archivo cargado %s.", archivoDTO), e);
			throw new Area247Exception(String.format("Error al guardar el archivo cargado %s.", archivoDTO), e);
		}
	}

	@Override
	public void update(@Log ArchivoDTO archivoDTO) {
		try {
			Archivo archivo = mapper.toArchivo(archivoDTO);
			repository.save(archivo);
		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al actualizar el archivo cargado %s.", archivoDTO), e);
			throw new Area247Exception(String.format("Error al actualizar el archivo cargado %s.", archivoDTO), e);
		}
	}

}
