package co.gov.metropol.area247.huellas.dao;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.huellas.entity.PreguntaConsumo;
import co.gov.metropol.area247.huellas.model.PreguntaConsumoDto;

@Transactional
@Repository("preguntaConsumoDao")
public interface IHuellasPreguntaConsumoDao extends JpaRepository<PreguntaConsumo, Serializable>{
	
	//List<Pregunta> findByTipoHuellaId(Long tipoHuellaId);
//	@Query("SELECT new co.gov.metropol.area247.huellas.model.PreguntaConsumoDto("
//			+ "p.id, p.descripcion, p.nombre, um.id, um.abrUnidadBasica"
//			+ ")"
//			+ "FROM PreguntaConsumo p "
//			+       "INNER JOIN p.unidadMedida um "
//			+       "WHERE um.id = ?1")
//	List<PreguntaConsumoDto> getPreguntasConsumo(Long idUnidadMedida);
	
}
