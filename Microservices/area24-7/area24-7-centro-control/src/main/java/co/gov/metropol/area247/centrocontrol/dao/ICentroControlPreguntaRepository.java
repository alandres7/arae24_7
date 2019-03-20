package co.gov.metropol.area247.centrocontrol.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.centrocontrol.model.Formulario;
import co.gov.metropol.area247.centrocontrol.model.Pregunta;
import co.gov.metropol.area247.centrocontrol.model.TipoPregunta;

@Transactional
@Repository
public interface ICentroControlPreguntaRepository extends CrudRepository<Pregunta, Long> {

	List<Pregunta> findByTipoPregunta(TipoPregunta tipoPregunta); 
	List<Pregunta> findByFormulario(Formulario formulario);
	Pregunta findById(Long id);
	
}
