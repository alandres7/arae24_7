package co.gov.metropol.area247.centrocontrol.seguridad.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.centrocontrol.model.NotificacionPush;

@Transactional
@Repository
public interface ISeguridadNotificacionPushRepository extends CrudRepository<NotificacionPush, Long> {
	
	
	@Query("SELECT n FROM NotificacionPush AS n ORDER BY n.id DESC")
	List<NotificacionPush> ListarTodosOrdenadosPorId();

}
