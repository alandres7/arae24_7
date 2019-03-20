package co.gov.metropol.area247.contenedora.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.model.IconoEstado;

@Repository
public interface IContenedoraIconoEstadoRepository extends CrudRepository<IconoEstado, Long> {

	List<IconoEstado> findByIdCapa(Long idCapa);
	List<IconoEstado> findByIdCategoria(Long idCategoria);
	
}
