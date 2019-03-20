package co.gov.metropol.area247.seguridad.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.seguridad.model.Usuario;
import co.gov.metropol.area247.seguridad.model.dto.UsuarioDto;

@Repository
public interface ISeguridadUsuarioRepository extends CrudRepository<Usuario, Long> {

	@Query("SELECT new co.gov.metropol.area247.seguridad.model.dto.UsuarioDto("
			+ "u.id, u.email, u.nombre, u.apellido, u.username, u.activo, "
			+ "rl.id, rl.nombre, g.descripcion, f.descripcion, n.nombre, m.nombre, u.fechaBloqueo, u.razonBloqueo) "
			+ "FROM co.gov.metropol.area247.seguridad.model.Usuario u "
			+ "left join u.genero g "
			+ "left join u.fuenteRegistro f "
			+ "left join u.nivelEducativo n "
			+ "left join u.municipio m "
			+ "left join u.rol rl "
			+ "WHERE u.id = ?1")
	UsuarioDto usuarioDtoObtenerPorId(Long id);
	
	
	@Query("SELECT new co.gov.metropol.area247.seguridad.model.dto.UsuarioDto(u.id, u.email, u.nombre, "
			+ "u.apellido, u.username, u.activo, rl.id, rl.nombre, g.descripcion, f.descripcion, n.nombre, "
			+ "m.nombre, u.fechaBloqueo, u.razonBloqueo) "
			+ "FROM co.gov.metropol.area247.seguridad.model.Usuario u "
			+ "left join u.genero g "
			+ "left join u.fuenteRegistro f "
			+ "left join u.nivelEducativo n "
			+ "left join u.municipio m "
			+ "left join u.rol rl "
			+ "WHERE u.fuenteRegistro = 4 ")
	List<UsuarioDto> usuarioDtoListarTodos();
	
	Usuario findByUsername(String username);
	
	@Query("SELECT u FROM Usuario u WHERE u.email = ?1 OR  u.username = ?2 ")
	List<Usuario> findByEmailOrUsername(String email, String username);
	
	Usuario findByUsernameAndTokenDispositivo(String username, String tokenDispositivo);
	
}
