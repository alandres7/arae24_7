package co.gov.metropol.area247.vigias.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.vigias.model.ComentarioVigia;
import co.gov.metropol.area247.vigias.model.dto.ComentarioVigiaDto;


@Transactional
@Repository
public interface IVigiasComentarioRepository extends CrudRepository<ComentarioVigia, Long> {
	
	@Query("SELECT new co.gov.metropol.area247.vigias.model.dto.ComentarioVigiaDto(c.id, c.descripcion, "
			+ "c.fechaCreacion, c.estado, c.idUsuario, c.idReporteVigia, c.recorridoArbol) "
			+ "FROM ComentarioVigia AS c WHERE c.idReporteVigia = ?1")
	List<ComentarioVigiaDto> comentarioVigiaDtoObtenerPorReporteVigia(Long idReporteVigia);
	
	
	@Query("SELECT new co.gov.metropol.area247.vigias.model.dto.ComentarioVigiaDto(c.id, c.descripcion, "
			+ "c.fechaCreacion, c.estado, c.idUsuario, c.idReporteVigia, c.recorridoArbol) "
			+ "FROM ComentarioVigia AS c WHERE c.id = ?1")
	ComentarioVigiaDto ComentarioVigiaDtoConsultarPorId(Long idComentarioVigia);
	
		
	@Query("SELECT new co.gov.metropol.area247.vigias.model.dto.ComentarioVigiaDto(c.id, c.descripcion, "
			+ "c.fechaCreacion, c.estado, c.idUsuario, c.idReporteVigia, c.recorridoArbol) "
			+ "FROM ComentarioVigia AS c WHERE c.idUsuario = ?1")
	List<ComentarioVigiaDto> comentarioVigiaDtoPorIdUsuario(Long idUsuario);

	
	@Query("SELECT new co.gov.metropol.area247.vigias.model.dto.ComentarioVigiaDto(c.id, c.descripcion, "
			+ "c.fechaCreacion, c.estado, c.idUsuario, c.idReporteVigia, c.recorridoArbol) "
			+ "FROM ComentarioVigia AS c WHERE c.estado = '?1' ")
	List<ComentarioVigiaDto> comentarioVigiaDtoPorEstado(String estado);
		
}
