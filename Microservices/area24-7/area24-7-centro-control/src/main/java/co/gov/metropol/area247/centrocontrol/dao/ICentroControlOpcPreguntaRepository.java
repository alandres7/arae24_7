package co.gov.metropol.area247.centrocontrol.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.centrocontrol.model.OpcPregunta;
import co.gov.metropol.area247.centrocontrol.model.dto.OpcPreguntaDto;

@Transactional
@Repository
public interface ICentroControlOpcPreguntaRepository extends CrudRepository<OpcPregunta, Long> {
	
	@Query("SELECT new co.gov.metropol.area247.centrocontrol.model.dto.OpcPreguntaDto( "
			+ "op.id, p.id, op.clave, op.valor)FROM OpcPregunta op inner join op.preguntaId p "
			+ "where op.clave = ?1 and p.id = ?2 ")
	List<OpcPreguntaDto> getOpcPregunta(String clave, Long idPregunta);
	
	@Query("SELECT new co.gov.metropol.area247.centrocontrol.model.dto.OpcPreguntaDto( "
			+ "op.id, p.id, op.clave, op.valor)FROM OpcPregunta op inner join op.preguntaId p "
			+ "where p.id = ?1 ")
	List<OpcPreguntaDto> getOpcPregunta(Long idPregunta);
	
	OpcPregunta findById(Long id);
	
	
	
}
