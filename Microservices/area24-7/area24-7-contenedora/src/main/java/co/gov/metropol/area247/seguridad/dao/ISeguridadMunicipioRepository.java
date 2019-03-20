package co.gov.metropol.area247.seguridad.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.seguridad.model.Municipio;
import co.gov.metropol.area247.seguridad.model.dto.MunicipioDto;

@Repository
public interface ISeguridadMunicipioRepository extends CrudRepository<Municipio, Long> {

	Municipio findByNombre(String nombre);
	
	@Query("SELECT new co.gov.metropol.area247.seguridad.model.dto.MunicipioDto("
			+ "m.id, m.nombre)"
			+ " from Municipio m "
			+ "inner join m.departamento d "
			+ "where m.nombre = ?1 and d.nombre = ?2")
	MunicipioDto municipioByNombre(String nomMunicipio, String nomDepartamento);
	
	List<Municipio> findByValleAburra(boolean valleAburra);
	
}
