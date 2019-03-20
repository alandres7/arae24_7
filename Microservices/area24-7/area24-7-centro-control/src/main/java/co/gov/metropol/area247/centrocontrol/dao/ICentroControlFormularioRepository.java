package co.gov.metropol.area247.centrocontrol.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.centrocontrol.model.Formulario;
import co.gov.metropol.area247.centrocontrol.model.Objeto;

@Transactional
@Repository
public interface ICentroControlFormularioRepository extends CrudRepository<Formulario, Long> {

	Formulario findByNombre(String nombre);
	Formulario findByIdAplicacion(Long idAplicacion);
}
