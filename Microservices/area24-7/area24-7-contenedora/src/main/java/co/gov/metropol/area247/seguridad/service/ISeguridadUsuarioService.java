package co.gov.metropol.area247.seguridad.service;

import java.util.List;

import co.gov.metropol.area247.contenedora.model.dto.UsuarioActualizarContrasenaDto;
import co.gov.metropol.area247.seguridad.model.Usuario;
import co.gov.metropol.area247.seguridad.model.dto.UsuarioDto;

public interface ISeguridadUsuarioService {

	Usuario usuarioObtenerPorId(Long id);

	UsuarioDto usuarioDtoObtenerPorId(Long id);

	Usuario usuarioObtenerPorUsername(String username);

	List<UsuarioDto> usuarioDtoListarTodos();

	boolean usuarioCrear(UsuarioDto usuario);

	boolean actualizarUsuario(UsuarioDto usuario);

	boolean actualizarContrasena(UsuarioDto usuario);

	boolean actualizarPsswrdNoCifrada(UsuarioDto usuario);

	String cargarProcedencia(Usuario usuario, String nomMunicipio);

	public Usuario obtenerUsuarioPorUsername(String nickname);

	boolean confirmarRegistroUsuario(String username, String token) throws Exception;

	Usuario obtenerUsuarioPorEmailOUsername(String email, String username);

	boolean actualizarRolUsuario(UsuarioDto usuario);

	boolean actualizarContrasena(UsuarioActualizarContrasenaDto usuarioActualizarContrasenaDto) throws Exception;
}
