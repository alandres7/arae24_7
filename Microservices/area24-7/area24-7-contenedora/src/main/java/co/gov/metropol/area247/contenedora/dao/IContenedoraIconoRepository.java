package co.gov.metropol.area247.contenedora.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.model.Icono;

@Transactional
@Repository
public interface IContenedoraIconoRepository extends CrudRepository<Icono, Long> {
	
	Icono findByNombre(String nombre);
	
}
   