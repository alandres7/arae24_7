package co.gov.metropol.area247.entorno.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.entorno.model.Medicion;
import co.gov.metropol.area247.entorno.model.dto.MedicionDto;

@Repository
public interface IEntornoMedicionRepository extends CrudRepository<Medicion, Long> {

	List<Medicion> findByIdCapa(Long idCapa);	
	
	@Query("select new co.gov.metropol.area247.entorno.model.dto.MedicionDto(m.id, m.idCapa, m.nombre, "
			+ "m.descripcion, m.color, m.significado, m.recomendacion, m.escalaInicial, m.escalaFinal, "
			+ "m.icono) From Medicion m where m.idCapa = ?1 ")
	List<MedicionDto> medicionDtoObtenerPorIdCapa(Long idCapa);
	
	@Query("select new co.gov.metropol.area247.entorno.model.dto.MedicionDto(m.id, m.idCapa, m.nombre, "
			+ "m.descripcion, m.color, m.significado, m.recomendacion, m.escalaInicial, m.escalaFinal, "
			+ "m.icono) From Medicion m where m.id = ?1 ")
	MedicionDto medicionDtoObtenerPorId(Long idMedicion);
	
	@Query("select m.nombre From Medicion m ")
	List<String> obtenerNombresMedicion();
	
}
