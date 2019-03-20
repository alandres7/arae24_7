package co.gov.metropol.area247.seguridad.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.model.dto.UsuarioActualizarContrasenaDto;
import co.gov.metropol.area247.contenedora.service.IContenedoraAplicacionService;
import co.gov.metropol.area247.contenedora.util.Utils;
import co.gov.metropol.area247.seguridad.dao.ISeguridadRolRepository;
import co.gov.metropol.area247.seguridad.dao.ISeguridadUsuarioRepository;
import co.gov.metropol.area247.seguridad.model.FuenteRegistro;
import co.gov.metropol.area247.seguridad.model.Municipio;
import co.gov.metropol.area247.seguridad.model.Usuario;
import co.gov.metropol.area247.seguridad.model.dto.UsuarioDto;
import co.gov.metropol.area247.seguridad.service.ISeguridadFuenteRegistroService;
import co.gov.metropol.area247.seguridad.service.ISeguridadGeneroService;
import co.gov.metropol.area247.seguridad.service.ISeguridadMunicipioService;
import co.gov.metropol.area247.seguridad.service.ISeguridadNivelEducativoService;
import co.gov.metropol.area247.seguridad.service.ISeguridadSecurityService;
import co.gov.metropol.area247.seguridad.service.ISeguridadUsuarioService;

@Service
public class ISeguridadUsuarioServiceImpl implements ISeguridadUsuarioService {

	private static final String EMPTY_STRING = "";
	private static final String SEMICOLON = ";";

	@Autowired
	ISeguridadUsuarioRepository usuarioDao;

	@Autowired
	ISeguridadRolRepository rolDao;

	@Autowired
	IContenedoraAplicacionService aplicacionService;

	@Autowired
	ISeguridadFuenteRegistroService fuenteRegistroService;

	@Autowired
	ISeguridadNivelEducativoService nivelEducativoService;

	@Autowired
	ISeguridadGeneroService generoService;

	@Autowired
	ISeguridadMunicipioService municipioService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private ISeguridadSecurityService iSeguridadSecurityService;

	@Value("${preferences.default.value}")
	private String DEFAULT_PREFERENCES;

	Usuario usuarioAuxiliar;

	private static final Logger LOGGER = LoggerFactory.getLogger(ISeguridadUsuarioServiceImpl.class);

	@Override
	public Usuario usuarioObtenerPorId(Long id) {
		Usuario usuario = usuarioDao.findOne(id);
		if (usuario != null) {
			return usuario;
		} else {
			LOGGER.info(String.format("El usuario con id %s no existe", id));
			return null;
		}
	}

	@Override
	public boolean usuarioCrear(UsuarioDto usuario) {

		usuarioAuxiliar = new Usuario();

		// Se valida la fuente registro y en base a ella, se asigna el rol
		boolean okFuenteRegistro = false;
		for (FuenteRegistro fuenteRegistro : fuenteRegistroService.fuenteRegistroListarTodas()) {
			if (usuario.getNombreFuenteRegistro().equals(fuenteRegistro.getNombre())) {
				usuarioAuxiliar.setFuenteRegistro(
						fuenteRegistroService.fuenteRegistroObtenerPorNombre(usuario.getNombreFuenteRegistro()));
				usuarioAuxiliar.setRol(rolDao.findByNombre(fuenteRegistro.getRol().getNombre()));
				okFuenteRegistro = true;
				break;
			}
		}

		// Se notifica si fué posible la creación del usuario o no
		// if (okFuenteRegistro && okGenero && okNivelEducativo && okMunicipio)
		if (okFuenteRegistro) {
			if (!usuario.getContrasena().equals("")) {
				try {
					usuarioAuxiliar.setUsername(usuario.getUsername());
					usuarioAuxiliar.setContrasena(bCryptPasswordEncoder.encode(usuario.getContrasena()));
					usuarioAuxiliar.setEmail(usuario.getEmail());
					usuarioAuxiliar.setCelular(usuario.getCelular());
					usuarioAuxiliar.setTerminosCondiciones(usuario.getTerminosCondiciones());

					usuarioAuxiliar.setApellido(usuario.getApellido());
					usuarioAuxiliar.setNombre(usuario.getNombre());

					usuarioAuxiliar.setUltimaActualizacionPreferencias(new Date());
					if (!("FB".equals(usuario.getNombreFuenteRegistro())
							|| "GP".equals(usuario.getNombreFuenteRegistro()))) {
						usuarioAuxiliar.setActivo(false);
					} else {
						usuarioAuxiliar.setActivo(true);
					}
					usuarioAuxiliar.setPreferencias(aplicacionService.getDefaultAppsPreferences());
					usuarioDao.save(usuarioAuxiliar);
					return true;
				} catch (Exception e) {
					LOGGER.error("error al guardar el usuario con email --{}{}", usuario.getEmail(), e);
					return false;
				}
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	@Override
	public Usuario usuarioObtenerPorUsername(String username) {
		try {
			// return usuarioDao.findByNickname(username);
			return usuarioDao.findByUsername(username);
		} catch (Exception e) {
			LOGGER.error("Error al consultar el usuario por el usuario --{}{}", e);
			return null;
		}
	}

	public Usuario obtenerUsuarioPorEmailOUsername(String email, String username) {
		try {
			return usuarioDao.findByEmailOrUsername(email, username).get(0);
		} catch (Exception e) {
			LOGGER.error("Error al consultar el usuario por el email o nickname--{}{}", e);
			return null;
		}
	}

	@Override
	public Usuario obtenerUsuarioPorUsername(String username) {
		try {
			return usuarioDao.findByUsername(username);
		} catch (Exception e) {
			LOGGER.error("Error al al consultar el usuario por el nickname --{}{}", username, e);
			return null;
		}

	}

	@Override
	public boolean actualizarUsuario(UsuarioDto usuario) {
		try {
			// El username NO SE ACTUALIZA
			// No se actualiza la contraseña (fuera del alcance por ahora)
			// El id de usuario no se actualiza
			usuarioAuxiliar = usuarioDao.findByUsername(usuario.getUsername());
			cargarCambios(usuario);
			usuarioAuxiliar.setUltimoIngreso(new Date());
			usuarioDao.save(usuarioAuxiliar);
			return true;
		} catch (Exception e) {
			LOGGER.error("Error al actualizar el usuario por id {}{}", usuario.getUsername(), e);
			return false;
		}
	}

	private void cargarCambios(UsuarioDto usuario) {
		String generoActual = (usuarioAuxiliar.getGenero() != null) ? usuarioAuxiliar.getGenero().getNombre()
				: EMPTY_STRING;
		String generoNuevo = (usuario.getNombreGenero() != null) ? usuario.getNombreGenero() : EMPTY_STRING;

		if (!Utils.isNull(usuario.getContrasena())) {
			usuario.setContrasena(bCryptPasswordEncoder.encode(usuario.getContrasena()));
			if (!usuarioAuxiliar.getContrasena().equals(usuario.getContrasena())) {
				usuarioAuxiliar.setContrasena(usuario.getContrasena());
			}
		}

		usuarioAuxiliar.setCelular(usuario.getCelular());

		if (validarCambios(generoActual, generoNuevo))
			usuarioAuxiliar.setGenero(generoService.generoObtenerPorNombre(usuario.getNombreGenero()));

		String nivelEduActual = (usuarioAuxiliar.getNivelEducativo() != null)
				? usuarioAuxiliar.getNivelEducativo().getNombre()
				: "Ninguno";
		String nivelEduNovo = (usuario.getNombreNivelEducativo() != null) ? usuario.getNombreNivelEducativo()
				: "Ninguno";
		if (validarCambios(nivelEduActual, nivelEduNovo))
			usuarioAuxiliar.setNivelEducativo(nivelEducativoService.nivelEducativoObtenerPorNombre(nivelEduNovo));

		String municipioActual = (usuarioAuxiliar.getMunicipio() != null) ? usuarioAuxiliar.getMunicipio().getNombre()
				: EMPTY_STRING;
		String procedenciaNueva = (usuario.getNombreMunicipio() != null) ? usuario.getNombreMunicipio() : EMPTY_STRING;
		String municipioNuevo;
		if (procedenciaNueva.contains(SEMICOLON)) {
			String[] listProcedencia = procedenciaNueva.split(SEMICOLON);
			municipioNuevo = listProcedencia[2].toUpperCase();
			Municipio municipioEntity = municipioService.getByNombre(municipioNuevo, listProcedencia[1]);
			if (municipioEntity != null) {
				String nomPais = municipioEntity.getDepartamento().getPais().getNombre();
				if (!nomPais.equalsIgnoreCase(listProcedencia[0]))
					municipioEntity = null;
			}
			if (municipioEntity != null) {
				usuarioAuxiliar.setMunicipio(municipioEntity);
				usuarioAuxiliar.setCiudadExtranjera(EMPTY_STRING);
				usuarioAuxiliar.setEstadoExtranjero(EMPTY_STRING);
				usuarioAuxiliar.setOtroPais(EMPTY_STRING);
			} else {
				String extPaisActual = usuarioAuxiliar.getOtroPais() != null ? usuarioAuxiliar.getOtroPais()
						: EMPTY_STRING;
				String extEstadoActual = usuarioAuxiliar.getEstadoExtranjero() != null
						? usuarioAuxiliar.getEstadoExtranjero()
						: EMPTY_STRING;
				String extCiudadActual = usuarioAuxiliar.getCiudadExtranjera() != null
						? usuarioAuxiliar.getCiudadExtranjera()
						: EMPTY_STRING;
				String extPaisNuevo = listProcedencia[0];
				String extEstadoNuevo = listProcedencia[1];
				if (validarCambios(extCiudadActual, municipioNuevo))
					usuarioAuxiliar.setCiudadExtranjera(municipioNuevo);
				if (validarCambios(extEstadoActual, extEstadoNuevo))
					usuarioAuxiliar.setEstadoExtranjero(extEstadoNuevo);
				if (validarCambios(extPaisActual, extPaisNuevo))
					usuarioAuxiliar.setOtroPais(extPaisNuevo);
				usuarioAuxiliar.setMunicipio(null);
			}
		}
		String nombreActual = usuarioAuxiliar.getNombre() != null ? usuarioAuxiliar.getNombre() : EMPTY_STRING;
		String nombreNuevo = usuario.getNombre() != null ? usuario.getNombre() : EMPTY_STRING;
		if (validarCambios(nombreActual, nombreNuevo))
			usuarioAuxiliar.setNombre(nombreNuevo);
		String apellidoActual = usuarioAuxiliar.getApellido() != null ? usuarioAuxiliar.getApellido() : EMPTY_STRING;
		String apellidoNuevo = usuario.getApellido() != null ? usuario.getApellido() : EMPTY_STRING;
		if (validarCambios(apellidoActual, apellidoNuevo))
			usuarioAuxiliar.setApellido(apellidoNuevo);
		String nickNameActual = usuarioAuxiliar.getUsername() != null ? usuarioAuxiliar.getUsername() : EMPTY_STRING;
		String nickNameNuevo = usuario.getUsername() != null ? usuario.getUsername() : EMPTY_STRING;
		if (validarCambios(nickNameActual, nickNameNuevo))
			usuarioAuxiliar.setUsername(nickNameNuevo);
		if (usuario.getPreferencias() != null && !EMPTY_STRING.equals(usuario.getPreferencias())) {
			usuarioAuxiliar.setPreferencias(usuario.getPreferencias());
			usuarioAuxiliar.setUltimaActualizacionPreferencias(new Date());
		}

		if (usuario.getTokenDispositivo() != null && !EMPTY_STRING.equals(usuario.getTokenDispositivo()))
			usuarioAuxiliar.setTokenDispositivo(usuario.getTokenDispositivo());
		Date fechaNacActual = usuarioAuxiliar.getFechaNacimiento();
		Date fechaNacNovo = usuario.getFechaNacimiento();
		if (fechaNacNovo != null && fechaNacActual != fechaNacNovo)
			usuarioAuxiliar.setFechaNacimiento(usuario.getFechaNacimiento());
	}

	private boolean validarCambios(String actual, String nuevo) {
		if (!EMPTY_STRING.equals(nuevo))
			if ((EMPTY_STRING.equals(actual)) || (!EMPTY_STRING.equals(actual) && (!actual.equalsIgnoreCase(nuevo)))) {
				return Boolean.TRUE;
			}
		return Boolean.FALSE;
	}

	@Override
	public boolean actualizarContrasena(UsuarioDto usuario) {
		try {
			usuarioAuxiliar = usuarioDao.findByUsername(usuario.getUsername());
			usuarioAuxiliar.setContrasena(bCryptPasswordEncoder.encode(usuario.getContrasena()));
			usuarioDao.save(usuarioAuxiliar);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean actualizarPsswrdNoCifrada(UsuarioDto usuario) {
		try {
			usuarioAuxiliar = usuarioDao.findByUsername(usuario.getUsername());
			usuarioAuxiliar.setContrasena(usuario.getContrasena());
			usuarioDao.save(usuarioAuxiliar);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public UsuarioDto usuarioDtoObtenerPorId(Long id) {
		return usuarioDao.usuarioDtoObtenerPorId(id);
	}

	@Override
	public boolean confirmarRegistroUsuario(String username, String token) {
		try {
			Usuario usuario = usuarioDao.findByUsernameAndTokenDispositivo(username, token);
			if (usuario != null) {
				usuario.setActivo(Boolean.TRUE);
				usuarioDao.save(usuario);
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	@Override
	public List<UsuarioDto> usuarioDtoListarTodos() {
		return usuarioDao.usuarioDtoListarTodos();
	}

	@Override
	public String cargarProcedencia(Usuario usuario, String nomMunicipio) {

		StringBuilder procedencia = new StringBuilder(EMPTY_STRING);
		if (nomMunicipio != null) {
			String nomDepartamento = usuario.getMunicipio().getDepartamento().getNombre();
			String nomPais = usuario.getMunicipio().getDepartamento().getPais().getNombre();
			procedencia.append(nomPais).append(SEMICOLON).append(nomDepartamento).append(SEMICOLON)
					.append(nomMunicipio);
		} else if (usuario.getOtroPais() != null) {
			procedencia.append(usuario.getOtroPais()).append(SEMICOLON).append(usuario.getEstadoExtranjero())
					.append(SEMICOLON).append(usuario.getCiudadExtranjera());

		}
		return procedencia.toString();
	}

	@Override
	public boolean actualizarRolUsuario(UsuarioDto usuario) {
		try {
			usuarioAuxiliar = usuarioDao.findByUsername(usuario.getUsername());
			usuarioAuxiliar.setUltimoIngreso(new Date());
			usuarioAuxiliar.setRol(rolDao.findById(usuario.getIdRol()));
			usuarioAuxiliar.setActivo(usuario.isActivo());
			usuarioDao.save(usuarioAuxiliar);
			return true;
		} catch (Exception e) {
			LOGGER.error("Error al actualizar el usuario por id {}{}", usuario.getUsername(), e);
			return false;
		}
	}

	@Override
	public boolean actualizarContrasena(UsuarioActualizarContrasenaDto usuarioActualizarContrasenaDto)
			throws Exception {
		try {
			Usuario usuario = usuarioDao.findByUsername(usuarioActualizarContrasenaDto.getUsername());

			if (bCryptPasswordEncoder.matches(usuarioActualizarContrasenaDto.getCurrentPassword(),
					usuario.getContrasena())) {
				usuario.setContrasena(bCryptPasswordEncoder.encode(usuarioActualizarContrasenaDto.getNewPassword()));
				usuarioDao.save(usuario);
				return true;
			}

			return false;
		} catch (Exception e) {
			LOGGER.error("Error al actualizar al usuario por username --{}{}",
					usuarioActualizarContrasenaDto.getUsername(), e);
			throw new Exception("Error al actualizar al usuario por username --{}{}", e);
		}
	}

}
