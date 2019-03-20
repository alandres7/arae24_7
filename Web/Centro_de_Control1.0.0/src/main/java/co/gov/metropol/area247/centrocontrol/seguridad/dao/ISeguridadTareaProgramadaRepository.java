package co.gov.metropol.area247.centrocontrol.seguridad.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.centrocontrol.model.TareaProgramada;

@Repository
public interface ISeguridadTareaProgramadaRepository extends CrudRepository<TareaProgramada, Long> {
	
	List<TareaProgramada> findByActivo(Boolean activo); 

}
