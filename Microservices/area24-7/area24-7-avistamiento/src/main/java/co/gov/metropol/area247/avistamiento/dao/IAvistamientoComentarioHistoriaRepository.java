package co.gov.metropol.area247.avistamiento.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.avistamiento.model.ComentarioHistoria;
import co.gov.metropol.area247.avistamiento.model.dto.ComentarioHistoriaDto;

@Transactional
@Repository
public interface IAvistamientoComentarioHistoriaRepository extends CrudRepository<ComentarioHistoria, Long> {
		
	@Query("SELECT new co.gov.metropol.area247.avistamiento.model.dto.ComentarioHistoriaDto(" + 
		    "ch.id, ch.descripcion, ch.fechaPublicacion, ch.estado, ch.idUsuario) " +
			"FROM ComentarioHistoria as ch WHERE ch.id = ?1")
	ComentarioHistoriaDto obtenerComentarioHistoriaPorId(Long idComentarioHistoria);
	
	@Query(nativeQuery = true, name = "getComments")
	List<ComentarioHistoriaDto> obtenerComentarioHistoriaPorIdHistoria(@Param("idHistoria") Long idHistoria, @Param("username")String username);
	
	@Query("SELECT new co.gov.metropol.area247.avistamiento.model.dto.ComentarioHistoriaDto(" + 
		    "ch.id, ch.descripcion, ch.fechaPublicacion, ch.estado, ch.idUsuario) " +
			"FROM ComentarioHistoria as ch WHERE ch.historia.id = ?1")
	List<ComentarioHistoriaDto> obtenerComentarioHistoriaPorIdHistoria(Long idHistoria);

	
}
