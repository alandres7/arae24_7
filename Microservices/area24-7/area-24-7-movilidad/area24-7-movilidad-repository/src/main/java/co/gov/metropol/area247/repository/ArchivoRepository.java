package co.gov.metropol.area247.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.Archivo;
import co.gov.metropol.area247.repository.domain.TipoArchivo;
import co.gov.metropol.area247.repository.domain.support.enums.EstadoArchivo;

@Repository
public interface ArchivoRepository extends CrudRepository<Archivo, Long> {
	
	List<Archivo> findByEnabled(boolean enabled);
	
	List<Archivo> findByTipoArchivo(TipoArchivo tipoArchivo);
	
	List<Archivo> findByEstado(EstadoArchivo estado);
	
	List<Archivo> findByFechaCarga(Date fecha);
	
	List<Archivo> findByFechaCargaBetween(Date fechaInicial, Date fechaFinal);

}
