package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.TipoArchivo;
import co.gov.metropol.area247.repository.domain.support.enums.ExtensionesDeArchivo;

@Repository
public interface TipoArchivoRepository extends CrudRepository<TipoArchivo , Long> {
	
	/**
	 * Buscar el tipo de carga dado a un nombre
	 * @param nombre - nombre del tipo de carga
	 * @return {@link TipoArchivo}
	 * */
	TipoArchivo findByNombreEquals(String nombre);
	
	/**
	 * Buscar todos los tipos de carga de acuerdo al estado de activacion
	 * @param enabled - estado de activacion
	 * @return List {@link TipoArchivo}
	 * */
	List<TipoArchivo> findByEnabledEquals (boolean enabled);
	
	/**
	 * Buscar todos los tipos de carga dados a una extension
	 * @param extension - {@link ExtensionesDeArchivo}
	 * @return List {@link TipoArchivo}
	 * */
	List<TipoArchivo> findByExtensionEquals(ExtensionesDeArchivo extension);

}
