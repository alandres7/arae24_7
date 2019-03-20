package co.gov.metropol.area247.contenedora.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.model.Mensaje;
import co.gov.metropol.area247.contenedora.model.dto.MensajeDto;
@Repository
public interface IContenedoraMensajeRepository extends CrudRepository<Mensaje, Long> {
	
	Mensaje findByNombreIdentificador(String nombreIdentificador);
	
	@Query("SELECT new co.gov.metropol.area247.contenedora.model.dto.MensajeDto(men.id, men.descripcion, "
			+ "men.titulo, men.nombreIdentificador, men.uso, men.idAplicacion) "
			+ "FROM Mensaje AS men where men.id = ?1 ")
	MensajeDto mensajeDtoPorId(Long id);
	
	
	@Query("SELECT new co.gov.metropol.area247.contenedora.model.dto.MensajeDto(men.id, men.descripcion, "
			+ "men.titulo, men.nombreIdentificador, men.uso, men.idAplicacion) "
			+ "FROM Mensaje AS men ")
	List<MensajeDto> mensajeDtoObtenerTodos();

}
