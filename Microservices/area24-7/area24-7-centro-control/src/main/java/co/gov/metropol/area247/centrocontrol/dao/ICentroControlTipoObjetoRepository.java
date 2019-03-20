package co.gov.metropol.area247.centrocontrol.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.centrocontrol.model.TipoObjeto;

@Repository
public interface ICentroControlTipoObjetoRepository extends CrudRepository<TipoObjeto, Long> {
	List<TipoObjeto> findByNombre (String nombre);
}
