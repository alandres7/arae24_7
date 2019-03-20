package co.gov.metropol.area247.centrocontrol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.centrocontrol.model.LogCarga;

@Repository
public interface LogCargaRepository extends CrudRepository<LogCarga , Long> {
	
	@Query("select t from LogCarga t where t.usuario=:usuario order by fecha desc")
	List<LogCarga> consultarLogCarga(@Param("usuario") String usuario);
	
}
