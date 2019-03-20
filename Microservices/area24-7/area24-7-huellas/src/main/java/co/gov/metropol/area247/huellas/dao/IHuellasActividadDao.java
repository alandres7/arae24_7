package co.gov.metropol.area247.huellas.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.huellas.entity.Actividad;
import co.gov.metropol.area247.huellas.model.ActividadDto;

@Repository
public interface IHuellasActividadDao extends JpaRepository<Actividad, Serializable> {
	
	@Query("select new co.gov.metropol.area247.huellas.model.ActividadDto(a.id, "
			+ "a.nombre, a.descripcion, a.orden) "
			+ "from co.gov.metropol.area247.huellas.entity.Actividad as a join a.capa as c where c.id = ?1")
	public List<ActividadDto> getActividadesXCapa(long idCapa);
	
	public Actividad findById(long id);
	
}
