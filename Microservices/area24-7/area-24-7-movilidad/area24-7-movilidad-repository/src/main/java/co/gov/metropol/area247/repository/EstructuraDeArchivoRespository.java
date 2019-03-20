package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.EstructuraDeArchivo;
import co.gov.metropol.area247.repository.domain.TipoArchivo;

@Repository
public interface EstructuraDeArchivoRespository  extends CrudRepository<EstructuraDeArchivo, Long>{
	
	List<EstructuraDeArchivo> findByTipoArchivoOrderByOrden(TipoArchivo tipoArchivo);
	
	List<EstructuraDeArchivo> findByTipoArchivoAndEnabledOrderByOrden(TipoArchivo tipoArchivo, boolean enabled);
	
	List<EstructuraDeArchivo> findByTipoArchivoOrderByOrdenAscUltimaModificacionDesc(TipoArchivo tipoArchivo);
	
	Integer countByTipoArchivo(TipoArchivo tipoArchivo);
}
