package co.gov.metropol.area247.centrocontrol.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.centrocontrol.model.Objeto;
import co.gov.metropol.area247.centrocontrol.model.TipoObjeto;

@Repository
public interface ICentroControlObjetoRepository extends CrudRepository<Objeto, Long> {

	Objeto findByNombre(String nombre);
	List<Objeto> findByTipoObjeto(TipoObjeto tipoObjeto);
	List<Objeto> findByIdAplicacion(Long idAplicacion);
	
}
