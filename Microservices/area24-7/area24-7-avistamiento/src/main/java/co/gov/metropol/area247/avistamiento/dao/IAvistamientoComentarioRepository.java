package co.gov.metropol.area247.avistamiento.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.avistamiento.model.Comentario;
import co.gov.metropol.area247.avistamiento.model.dto.ComentarioDto;

@Transactional
@Repository
public interface IAvistamientoComentarioRepository extends CrudRepository<Comentario, Long> {
	
	@Query("SELECT c FROM co.gov.metropol.area247.avistamiento.model.Comentario c inner join c.avistamiento a WHERE a.id = ?1 ")
	List<Comentario> findByIdAvistamiento(Long idAvistamiento);

	@Query("SELECT new co.gov.metropol.area247.avistamiento.model.dto.ComentarioDto(" + 
		    "c.id, c.descripcion, c.fechaPublicacion, c.estado, c.idUsuario) " +
			"FROM Comentario as c WHERE c.id = ?1")
	ComentarioDto obtenerComentarioPorId(Long idComentarioHistoria);
	
	@Query(nativeQuery = true, name = "getCommentsByState")
	List<ComentarioDto> obtenerComentarioPorIdAvistamiento(@Param("idAvistamiento") Long idAvistamiento, @Param("username")String username);
	
	@Query("SELECT new co.gov.metropol.area247.avistamiento.model.dto.ComentarioDto(" + 
		    "c.id, c.descripcion, c.fechaPublicacion, c.estado, c.idUsuario) " +
			"FROM Comentario as c WHERE c.avistamiento.id = ?1")
	List<ComentarioDto> obtenerComentarioPorIdAvistamiento(Long idAvistamiento);
	
}
