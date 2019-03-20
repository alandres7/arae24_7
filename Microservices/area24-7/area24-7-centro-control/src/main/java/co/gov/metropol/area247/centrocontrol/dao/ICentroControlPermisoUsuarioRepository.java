package co.gov.metropol.area247.centrocontrol.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.centrocontrol.model.PermisoUsuario;;

@Repository
public interface ICentroControlPermisoUsuarioRepository extends CrudRepository<PermisoUsuario, Long> {
	/**
	 * Obtener Lista PermisoUsuario por IdUsuario
	 * @param idUsuario
	 * @return
	 */
	List<PermisoUsuario> findByIdUsuario(Long idUsuario);
//	/**
//	 * Obtener left join entre Objetos y PermisosUsuario
//	 * @param idUsuario
//	 * @return
//	 */
//	@Query("SELECT new co.gov.metropol.area247.centrocontrol.model.dto.PermisoUsuarioDto("+
//	"a.id, a.idUsuario, b.id, a.puedeAdicionar, a.puedeEditar, a.puedeBorrar, "
//	+ "a.puedeImprimir, a.puedeConsultar, b.nombre) FROM PermisoUsuario as a left join a.objeto as b WHERE a.idUsuario = ?1")
//	List<PermisoUsuarioDto> obtenerPermisoUsuarioDtoPorIdUsuario(Long idUsuario);
//	
	
	List<PermisoUsuario> findByIdObjeto(Long idObjeto);
}
