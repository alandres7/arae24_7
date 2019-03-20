package co.gov.metropol.area247.seguridad.restcontroller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.contenedora.model.dto.Email;
import co.gov.metropol.area247.contenedora.model.dto.UsuarioActualizarContrasenaDto;
import co.gov.metropol.area247.contenedora.service.IContenedoraEmailService;
import co.gov.metropol.area247.contenedora.service.IContenedoraMensajeService;
import co.gov.metropol.area247.core.util.ContrasenaUtils;
import co.gov.metropol.area247.security.service.TokenAuthenticacionService;
import co.gov.metropol.area247.seguridad.mapper.ISeguridadMapper;
import co.gov.metropol.area247.seguridad.model.Genero;
import co.gov.metropol.area247.seguridad.model.Municipio;
import co.gov.metropol.area247.seguridad.model.NivelEducativo;
import co.gov.metropol.area247.seguridad.model.Usuario;
import co.gov.metropol.area247.seguridad.model.dto.UsuarioDto;
import co.gov.metropol.area247.seguridad.service.ISeguridadSecurityService;
import co.gov.metropol.area247.seguridad.service.ISeguridadUsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Seguridad")
@RestController
@RequestMapping("/api")
public class UsuarioRestController {

	@Autowired
	ISeguridadUsuarioService usuarioService;

	@Autowired
	ISeguridadSecurityService securityService;

	@Autowired
	IContenedoraEmailService emailService;
	
	@Autowired
	IContenedoraMensajeService mensajeService;

	@Autowired
	@Qualifier("usuarioMapper")
	ISeguridadMapper<Usuario, UsuarioDto> usuarioMapper;

	@Value("${confirm.register.url}")
	String urlconfirmRegister;

	private static Logger LOGGER = LoggerFactory.getLogger(UsuarioRestController.class);

	@ResponseBody
	@ApiOperation(value = "/login", notes = "Permite la autenticación de usuarios en el sistema Área 24/7 del Área Metropolitana del Valle de Aburrá retornando un token de seguridad (Json Web Token) en el cuerpo del response")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody UsuarioDto usuario) {
		try {
			Usuario usuarioAuxiliar = usuarioService.obtenerUsuarioPorEmailOUsername(usuario.getUsername(),usuario.getUsername());
			if (usuarioAuxiliar != null) {
				usuario.setUsername(usuarioAuxiliar.getUsername());
				if (usuarioAuxiliar.isActivo()) {
					if (!usuario.getNombreFuenteRegistro().equals(usuarioAuxiliar.getFuenteRegistro().getNombre())) {
						String msgTextoReemplazo = null;
						if ("FB".equals(usuarioAuxiliar.getFuenteRegistro().getNombre())) msgTextoReemplazo = "Facebook";
						else if ("GP".equals(usuarioAuxiliar.getFuenteRegistro().getNombre())) msgTextoReemplazo = "Google Plus";
						else msgTextoReemplazo = "Formulario";
						
						return new ResponseEntity<String>(mensajeService.crearRespuestaJson("REGISTRO_USUARIO_CORREO_YA_REGISTRADO",
								                          msgTextoReemplazo,
								                          "FUENTE_REGISTRO"), HttpStatus.I_AM_A_TEAPOT);
					} else {
						securityService.autologin(usuarioAuxiliar.getUsername(), usuario.getContrasena());
						if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
							try {
								// ultimo ingreso
								usuario.setId(usuarioAuxiliar.getId());
								usuario.setContrasena(usuarioAuxiliar.getContrasena());
								usuario.setNombre(usuarioAuxiliar.getNombre());
								usuario.setApellido(usuarioAuxiliar.getApellido());
								usuario.setUsername(usuarioAuxiliar.getUsername());

								Municipio municipio = usuarioAuxiliar.getMunicipio();
								if (municipio != null) {
									usuario.setNombreMunicipio(usuarioAuxiliar.getMunicipio().getNombre());
								} else {
									usuario.setNombreMunicipio(null);
								}
								usuario.setNombreMunicipio(usuarioService.cargarProcedencia(usuarioAuxiliar,
										usuario.getNombreMunicipio()));

								Genero genero = usuarioAuxiliar.getGenero();
								if (genero != null) {
									usuario.setNombreGenero(genero.getNombre());
								} else {
									usuario.setNombreGenero("");
								}

								NivelEducativo nivelEducativo = usuarioAuxiliar.getNivelEducativo();
								if (nivelEducativo != null) {
									usuario.setNombreNivelEducativo(nivelEducativo.getNombre());
								} else {
									usuario.setNombreNivelEducativo("");
								}

								usuario.setUltimaActualizacionPreferencias(
										usuarioAuxiliar.getUltimaActualizacionPreferencias());
								usuario.setFechaNacimiento(usuarioAuxiliar.getFechaNacimiento());
								usuario.setPreferencias(usuarioAuxiliar.getPreferencias());
								usuario.setRolName(usuarioAuxiliar.getRol().getNombre());
								usuario.setActivo(usuarioAuxiliar.isActivo());
								usuario.setUltimoIngreso(new Date());

								usuario.setCelular(usuarioAuxiliar.getCelular());
								usuarioService.actualizarUsuario(usuario);

								Map<String, Object> response = new HashMap<String, Object>();
								response.put("token",
										TokenAuthenticacionService.agregarAutenticacion(usuario.getUsername()));
								response.put("usuario", usuario);
								return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
							} catch (ParseException e) {
								return new ResponseEntity<String>(
										mensajeService.crearRespuestaJson("Login-> usuario-error-autenticación","",""),
										HttpStatus.I_AM_A_TEAPOT);
							}
						} else {
							return new ResponseEntity<String>(
									mensajeService.crearRespuestaJson("USUARIO_CONTRASENA_INCORRECTA","",""),
									HttpStatus.I_AM_A_TEAPOT);
						}
					}
				} 
			    else {
					return new ResponseEntity<String>(
							mensajeService.crearRespuestaJson("USUARIO_CORREO_NO_VERIFICADO","",""), 
							HttpStatus.I_AM_A_TEAPOT);
				}
			} else {
				LOGGER.debug("Test Usuario no existe borrar log...");
				return new ResponseEntity<String>(mensajeService.crearRespuestaJson("usuario->no-existe","",""), 
						HttpStatus.I_AM_A_TEAPOT);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(
					mensajeService.crearRespuestaJson("USUARIO_CONTRASENA_INCORRECTA","",""),
					HttpStatus.I_AM_A_TEAPOT);
		}
	}

	@ResponseBody
	@ApiOperation(value = "/login-ccontrol", notes = "Permite la autenticación de usuarios en el sistema Área 24/7 del Área Metropolitana del Valle de Aburrá retornando un token de seguridad (Json Web Token) en el cuerpo del response")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Ingreso exitoso"),
			@ApiResponse(code = 404, message = "Not Found. El usuario o contraseña son incorrectos ó la url que se intenta acceder no se encuetra disponible o no existe"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar ejemplo Swagger provisto"),
			@ApiResponse(code = 409, message = "Conflicto. El usuario se encuentra registrado desde otra fuente") })

	@RequestMapping(value = "/login-ccontrol", method = RequestMethod.POST)
	public ResponseEntity<?> loginCControl(@RequestBody UsuarioDto usuario) {
		try {

			Usuario usuarioAuxiliar = usuarioService.obtenerUsuarioPorUsername(usuario.getUsername());
			if (usuarioAuxiliar != null) {
				usuario.setUsername(usuarioAuxiliar.getUsername());
				if (usuarioAuxiliar.isActivo()) {
					if (!usuario.getNombreFuenteRegistro().equals(usuarioAuxiliar.getFuenteRegistro().getNombre())) {
						
						return new ResponseEntity<String>(mensajeService.crearRespuestaJson("Login->fuenteRegistro-incorrecta",
								                          usuarioAuxiliar.getFuenteRegistro().getDescripcion(),
								                          "FUENTE_REGISTRO"), HttpStatus.I_AM_A_TEAPOT);
					} else {
						if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
							try {
								Map<String, Object> response = new HashMap<String, Object>();
								response.put("token",
										TokenAuthenticacionService.agregarAutenticacion(usuario.getUsername()));
								response.put("usuario", usuario);
								return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
							} catch (ParseException e) {
								return new ResponseEntity<String>(										
										mensajeService.crearRespuestaJson("Login-> usuario-error-autenticación","",""),
										HttpStatus.I_AM_A_TEAPOT);
							}
						} else {
							return new ResponseEntity<String>(
									mensajeService.crearRespuestaJson("Login->datos-no-reconocidos","",""),
									HttpStatus.I_AM_A_TEAPOT);
						}
					}
				} else {
					return new ResponseEntity<String>(
							mensajeService.crearRespuestaJson("usuario->inactivo","",""), HttpStatus.I_AM_A_TEAPOT);
				}
			} else {
				return new ResponseEntity<String>(
						mensajeService.crearRespuestaJson("usuario->no-existe","",""), HttpStatus.I_AM_A_TEAPOT);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(
					mensajeService.crearRespuestaJson("Login->datos-no-reconocidos","",""),HttpStatus.I_AM_A_TEAPOT);
		}
	}

	@ResponseBody
	@ApiOperation(value = "/registro", notes = "Permite el registro de nuevos usuarios y administradores al sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@RequestMapping(value = "/registro", method = RequestMethod.POST)
	public ResponseEntity<String> registro(@RequestBody @Valid UsuarioDto usuario, BindingResult result) {
		final String asuntoMailValRegistro = "Confirmación de registro AREA24/7";
		final String plantillaValRegistro = "validar-registro";
		if (!result.hasErrors()) {

			Usuario usuarioAux = null;
			try {
				usuarioAux = usuarioService.obtenerUsuarioPorEmailOUsername(usuario.getEmail(), usuario.getUsername());
			} catch (Exception e) {
				LOGGER.error("Error, el usuario no se encuentra registrado", e);
			}

			if (usuarioAux != null && usuario.getUsername().toLowerCase().equals(usuarioAux.getUsername().toLowerCase())) {			
				return new ResponseEntity<>(
						mensajeService.crearRespuestaJson("Registro-usuario->usuario-ya-registrado","",""),
						HttpStatus.I_AM_A_TEAPOT);
				
			} else if (usuarioAux != null && usuario.getEmail().toLowerCase().equals(usuarioAux.getEmail().toLowerCase())) {
				String msgTextoReemplazo = null;
				if ("FB".equals(usuarioAux.getFuenteRegistro().getNombre())) msgTextoReemplazo = "Facebook";
				else if ("GP".equals(usuarioAux.getFuenteRegistro().getNombre())) msgTextoReemplazo = "Google Plus";
				else msgTextoReemplazo = "Formulario";
				
				return new ResponseEntity<>(
						mensajeService.crearRespuestaJson("REGISTRO_USUARIO_CORREO_YA_REGISTRADO", msgTextoReemplazo, "FUENTE_REGISTRO"),
						HttpStatus.I_AM_A_TEAPOT);
				
			} else {
				if (usuarioService.usuarioCrear(usuario)) {
					if (!("FB".equals(usuario.getNombreFuenteRegistro())
							|| "GP".equals(usuario.getNombreFuenteRegistro()))) {
						StringBuilder urlFinal = new StringBuilder(urlconfirmRegister).append("?user=")
								.append(usuario.getUsername()).append("&token=");
						StringBuilder token = new StringBuilder("");
						try {
							token.append(TokenAuthenticacionService.agregarAutenticacion(usuario.getUsername()));
							final String bearer = "Bearer";
							int indexBearer = token.indexOf(bearer);
							if (indexBearer != -1) {
								token.delete(indexBearer, bearer.length() + 1);
							}
							urlFinal.append(token);
						} catch (ParseException e) {
							token.append(ContrasenaUtils.genContrasenaSystem());
							urlFinal.append(token);
						}
						usuario.setTokenDispositivo(token.toString());
						usuarioService.actualizarUsuario(usuario);
						Email datosMail = new Email();
						List<String> destinatario = new ArrayList<>();
						destinatario.add(usuario.getEmail());
						datosMail.setWhoReceive(destinatario);
						datosMail.setSubject(asuntoMailValRegistro);
						Map<String, Object> datosPlantilla = new HashMap<>();
						datosPlantilla.put("urlConfirmarRegistro", urlFinal.toString());
						envioMail(usuario.getEmail(), usuario.getNombre(), usuario.getApellido(),
								usuario.getNombreGenero(), asuntoMailValRegistro, plantillaValRegistro, datosPlantilla);
					}
										
					String respuestaCreacion = "";
					if ("FB".equals(usuario.getNombreFuenteRegistro()) || "GP".equals(usuario.getNombreFuenteRegistro())) {
						respuestaCreacion = mensajeService.crearRespuestaJson("Registro-usuario->registro-exitoso-por-facebook-o-gmail","","");
					}else {
						respuestaCreacion = mensajeService.crearRespuestaJson("Registro-usuario->registro-exitoso-no-por-facebook-o-gmail","","");
					}
					return new ResponseEntity<>(respuestaCreacion, HttpStatus.OK);
					
				} else {
					return new ResponseEntity<>(
							mensajeService.crearRespuestaJson("Registro-usuario->datos-usuario-con-error","",""),
							HttpStatus.I_AM_A_TEAPOT);
				}
			}
		} else {
			return new ResponseEntity<>(
					mensajeService.crearRespuestaJson("Registro-usuario->datos-usuario-con-error","",""),
					HttpStatus.I_AM_A_TEAPOT);
		}
	}

	@ResponseBody
	@ApiOperation(value = "/usuario", notes = "Lista el total de usuarios registrados en el sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Recuperación de datos exitosa"),
			@ApiResponse(code = 500, message = "El token de seguridad no es válido o existe otro error en el procesamiento a nivel de servidor"),
			@ApiResponse(code = 401, message = "La url a la que se intenta acceder no esta autorizada"),
			@ApiResponse(code = 403, message = "La url a la que se intenta acceder no esta autorizada para el usuario solicitante"),
			@ApiResponse(code = 404, message = "Not Found. La url que se intenta acceder no se encuetra disponible o no existe"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, validar vía Swagger provisto") })
	@RequestMapping(value = "/usuario", method = RequestMethod.GET)
	public ResponseEntity<?> usuarioObtenerTodos() {
		try {
			return new ResponseEntity<List<UsuarioDto>>(usuarioService.usuarioDtoListarTodos(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(mensajeService.crearRespuestaJson("usuario->proceso-solicitud-fallido","",""),
					HttpStatus.I_AM_A_TEAPOT);
		}
	}

	@ResponseBody
	@ApiOperation(value = "/usuario", notes = "Actualiza los datos del usuario")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/usuario", method = RequestMethod.PUT)
	public ResponseEntity<?> usuarioActualizar(@RequestBody UsuarioDto usuario) {
		try {
			Usuario usuarioAuxiliar = usuarioService.obtenerUsuarioPorUsername(usuario.getUsername());
			if (usuarioAuxiliar != null) {
				if (usuarioService.actualizarUsuario(usuario)) {
					return new ResponseEntity<String>(mensajeService.crearRespuestaJson("usuario->actualizado","",""), 
							HttpStatus.OK);
				} else {
					return new ResponseEntity<String>(mensajeService.crearRespuestaJson("usuario->datos-incorrectos","",""),
							HttpStatus.I_AM_A_TEAPOT);
				}
			} else {
				return new ResponseEntity<String>(mensajeService.crearRespuestaJson("usuario->no-existe","",""), 
						HttpStatus.I_AM_A_TEAPOT);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(mensajeService.crearRespuestaJson("usuario->proceso-solicitud-fallido","",""),
					HttpStatus.I_AM_A_TEAPOT);
		}
	}

	@ResponseBody
	@ApiOperation(value = "/usuario-por-username", notes = "Obtiene un usuario a partir de su username")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Recuperación exitosa"),
			@ApiResponse(code = 500, message = "El token de seguridad no es válido o existe otro error en el procesamiento a nivel de servidor"),
			@ApiResponse(code = 401, message = "La url a la que se intenta acceder no esta autorizada"),
			@ApiResponse(code = 403, message = "La url a la que se intenta acceder no esta autorizada para el usuario solicitante"),
			@ApiResponse(code = 404, message = "Not Found. El usuario no existe"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, validar vía Swagger provisto") })
	@RequestMapping(value = "/usuario-por-username", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> usuarioObtenerPorUsername(@RequestParam("username") String username) {
		if (username != null) {
			try {
				Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);
				if (usuario != null) {
					UsuarioDto usuarioDto = usuarioMapper.entityToModel(usuario);
					usuarioDto.setNombreMunicipio(usuarioService.cargarProcedencia(usuario, usuarioDto.getNombreMunicipio()));
					return new ResponseEntity<UsuarioDto>(usuarioDto, HttpStatus.OK);
				} else {
					return new ResponseEntity<String>(mensajeService.crearRespuestaJson("usuario->no-existe","",""), 
							HttpStatus.I_AM_A_TEAPOT);
				}
			} catch (Exception e) {
				return new ResponseEntity<String>(mensajeService.crearRespuestaJson("usuario->proceso-solicitud-fallido","",""),
						HttpStatus.I_AM_A_TEAPOT);
			}
		} else {
			return new ResponseEntity<String>(
					mensajeService.crearRespuestaJson("usuario->proceso-solicitud-fallido","",""),
					HttpStatus.I_AM_A_TEAPOT);
		}

	}

	@ResponseBody
	@ApiOperation(value = "/usuario/{idUsuario}", notes = "Obtiene un usuario a partir de su username")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value = { @ApiResponse(code = 302, message = "Recuperación exitosa"),
			@ApiResponse(code = 500, message = "El token de seguridad no es válido o existe otro error en el procesamiento a nivel de servidor"),
			@ApiResponse(code = 401, message = "La url a la que se intenta acceder no esta autorizada"),
			@ApiResponse(code = 403, message = "La url a la que se intenta acceder no esta autorizada para el usuario solicitante"),
			@ApiResponse(code = 404, message = "Not Found. El usuario no existe"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, validar vía Swagger provisto") })
	@RequestMapping(value = "/usuario/{idUsuario}", method = RequestMethod.GET)
	public ResponseEntity<?> usuarioObtenerPorId(@PathVariable("idUsuario") Long idUsuario) {
		Usuario usuario = usuarioService.usuarioObtenerPorId(idUsuario);
		if (usuario != null) {
			UsuarioDto usuarioDto = usuarioMapper.entityToModel(usuario);
			usuarioDto.setNombreMunicipio(usuarioService.cargarProcedencia(usuario, usuarioDto.getNombreMunicipio()));
			return new ResponseEntity<UsuarioDto>(usuarioDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(mensajeService.crearRespuestaJson("usuario->no-encontrado-por-id",idUsuario.toString(),"ID_USUARIO"), 
					HttpStatus.I_AM_A_TEAPOT);
		}
	}

	@ResponseBody
	@ApiOperation(value = "/usuario-restablecer-pass", notes = "Restablece la contraseña del usuario")
	@RequestMapping(value = "/usuario-restablecer-pass", method = RequestMethod.PUT)
	public ResponseEntity<String> usuarioRestablecerContrasena(String username) {
		final String plantillaMailResetPass = "emailResetPass";
		final String plantillaMailKeyPass = "contrasena";
		final String asuntoMailResetPass = "Restablecimiento de contraseña";
		Usuario usuarioAux = null;
		String psswrdAnterior = "";
		if (username != null) {
			try {
				usuarioAux = usuarioService.obtenerUsuarioPorEmailOUsername(username,username);
				if (usuarioAux == null) {
					return new ResponseEntity<>(mensajeService.crearRespuestaJson("usuario->no-existe","",""), HttpStatus.I_AM_A_TEAPOT);
				} else {
					if (!usuarioAux.isActivo()) {
						return new ResponseEntity<>(mensajeService.crearRespuestaJson("usuario->inactivo","",""), HttpStatus.I_AM_A_TEAPOT);
					}
					if ("FB".equals(usuarioAux.getFuenteRegistro().getNombre()) || 
						"GP".equals(usuarioAux.getFuenteRegistro().getNombre())) {	
						
						return new ResponseEntity<>(mensajeService.crearRespuestaJson("USUARIO_NO_PUEDE_RECUPERAR_CONTRASENA",
								                    usuarioAux.getFuenteRegistro().getDescripcion(),
								                    "NOMBRE_FUENTE_REGISTRO"),HttpStatus.I_AM_A_TEAPOT);
						
					} 
					else if ("AP".equals(usuarioAux.getFuenteRegistro().getNombre())) {
						psswrdAnterior = usuarioAux.getContrasena();
						String claveCandidata = ContrasenaUtils.genContrasenaSystem();
						UsuarioDto dto = new UsuarioDto();
						dto.setUsername(usuarioAux.getUsername());
						dto.setContrasena(claveCandidata);
						usuarioService.actualizarContrasena(dto);
						Map<String, Object> datosPlantilla = new HashMap<>();
						datosPlantilla.put(plantillaMailKeyPass, claveCandidata);
						String genero = "";
						if (usuarioAux.getGenero() != null) {
							genero = usuarioAux.getGenero().getNombre();
						}
						envioMail(usuarioAux.getEmail(), usuarioAux.getNombre(), usuarioAux.getApellido(), genero,
								asuntoMailResetPass, plantillaMailResetPass, datosPlantilla);
						
						return new ResponseEntity<>(mensajeService.crearRespuestaJson("USUARIO_CONTRASENA_REESTABLECIDA","",""), HttpStatus.OK);
					} 
					else {
						return new ResponseEntity<String>("Fuente de registro debe ser FB, GP o AP", HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
			} catch (Exception e) {
				if (usuarioAux != null) {
					UsuarioDto dto = new UsuarioDto();
					dto.setUsername(usuarioAux.getUsername());
					dto.setContrasena(psswrdAnterior);
					usuarioService.actualizarPsswrdNoCifrada(dto);
				}
				return new ResponseEntity<>(mensajeService.crearRespuestaJson("usuario->proceso-solicitud-fallido-por-cuenta-correo-no-asociada",
						                                       "",""), HttpStatus.I_AM_A_TEAPOT);
			}
		} else {
			return new ResponseEntity<>(mensajeService.crearRespuestaJson("usuario->error-solicitud-realizada","",""), HttpStatus.I_AM_A_TEAPOT);
		}
	}

	@ResponseBody
	@ApiOperation(value = "/usuario-pass", notes = "Actualiza la contraseña del usuario")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Actualización de contraseña exitosa"),
			@ApiResponse(code = 500, message = "El token de seguridad no es válido o existe otro error en el procesamiento a nivel de servidor"),
			@ApiResponse(code = 401, message = "La url a la que se intenta acceder no esta autorizada"),
			@ApiResponse(code = 403, message = "La url a la que se intenta acceder no esta autorizada para el usuario solicitante"),
			@ApiResponse(code = 404, message = "Not Found. La url que se intenta acceder no se encuetra disponible o no existe"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, validar vía Swagger provisto") })
	@RequestMapping(value = "/usuario-pass", method = RequestMethod.PUT)
	public ResponseEntity<String> contrasenaActualizar(@RequestBody UsuarioDto usuario) {
		try {
			Usuario usuarioAuxiliar = usuarioService.usuarioObtenerPorUsername(usuario.getUsername());
			if (usuarioAuxiliar != null) {
				if (usuarioService.actualizarContrasena(usuario)) {
					return new ResponseEntity<>(mensajeService.crearRespuestaJson("USUARIO_CONTRASENA_ACTUALIZADA","",""), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(mensajeService.crearRespuestaJson("usuario->datos-incorrectos","",""), HttpStatus.I_AM_A_TEAPOT);
				}
			} else {
				return new ResponseEntity<>(mensajeService.crearRespuestaJson("usuario->no-existe","",""), HttpStatus.I_AM_A_TEAPOT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(mensajeService.crearRespuestaJson("usuario->proceso-solicitud-fallido","",""), HttpStatus.I_AM_A_TEAPOT);
		}

	}

	@ResponseBody
	@ApiOperation(value = "/confirmar-registro", notes = "Permite la activación de la de un usuario de la app móvil al sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Cuenta activada exitosamente"),
			@ApiResponse(code = 500, message = "Revisar validación de campos o existe otro error en el procesamiento a nivel de servidor"),
			@ApiResponse(code = 404, message = "Not Found. La url que se intenta acceder no se encuentra disponible o no existe"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar ejemplo Swagger provisto"),
			@ApiResponse(code = 403, message = "Forbidden. La url a la que se intenta acceder no esta autorizada para el usuario solicitante o el token de usuario no fué provisto"),
			@ApiResponse(code = 401, message = "Unauthorized. La url a la que se intenta acceder no esta autorizada para el usuario solicitante o el token de usuario no fué provisto"),
			@ApiResponse(code = 409, message = "Conflicto. El usuario que se intenta acceder no existe") })
	@GetMapping(value = "/confirmar-registro")
	public ResponseEntity<Boolean> confirmarRegistro(@RequestParam(value = "user") String user,
			@RequestParam(value = "token") String token) {
		try {
			return new ResponseEntity<>(usuarioService.confirmarRegistroUsuario(user, token), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
		}

	}

	@ResponseBody
	@ApiOperation(value = "/existeUsuario", notes = "Valida si existe un usuario determinado")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Recuperación exitosa"),
			@ApiResponse(code = 500, message = "El token de seguridad no es válido o existe otro error en el procesamiento a nivel de servidor"),
			@ApiResponse(code = 401, message = "La url a la que se intenta acceder no esta autorizada"),
			@ApiResponse(code = 403, message = "La url a la que se intenta acceder no esta autorizada para el usuario solicitante"),
			@ApiResponse(code = 404, message = "Not Found. El usuario no existe"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, validar vía Swagger provisto") })
	@RequestMapping(value = "/existeUsuario", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> existeUsuario(@RequestParam("nickname") String username) {
		if (username != null) {
			try {
				Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);
				if (usuario != null) {
					return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
				} else {
					return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
				}
			} catch (Exception e) {
				return new ResponseEntity<String>(mensajeService.crearRespuestaJson("usuario->proceso-solicitud-fallido","",""),
						HttpStatus.I_AM_A_TEAPOT);
			}
		} else {
			return new ResponseEntity<String>(mensajeService.crearRespuestaJson("usuario->error-solicitud-realizada","",""),
					HttpStatus.I_AM_A_TEAPOT);
		}
	}

	private String nombreCompletoConstructor(String nombre, String apellido, String genero, boolean tieneformalismo) {
		String formalismo = "";
		StringBuilder nombreCompleto = new StringBuilder("Estimad@ ");
		if ((tieneformalismo) && genero != null && !(genero.equals(""))) {
			if ("M".equals(genero)) {
				nombreCompleto = new StringBuilder("Estimado ");
				formalismo = "Sr. ";
			} else if ("F".equals(genero)) {
				nombreCompleto = new StringBuilder("Estimada ");
				formalismo = "Sra./ta ";
			}
		}
		boolean tieneNombre = true;
		if (null == nombre || "".equals(nombre)) {
			tieneNombre = false;
		} else {
			nombreCompleto.append(nombre).append(" ");
		}
		if (null == apellido || "".equals(apellido)) {
			if (!tieneNombre) {
				nombreCompleto.append("Usuario");
			}
		} else {
			if (!tieneNombre) {
				nombreCompleto.append(formalismo).append(" ").append(apellido);
			} else {
				nombreCompleto.append(apellido);
			}
		}
		return nombreCompleto.toString();
	}

	private void envioMail(String email, String nombre, String apellido, String genero, String asuntoMail,
			String plantillaMail, Map<String, Object> datosPlantilla) {
		final String plantillaMailKeyAsunto = "asunto";
		final String plantillaMailKeyNombre = "nombreCompleto";
		Email datosMail = new Email();
		List<String> destinatario = new ArrayList<>();
		destinatario.add(email);
		datosMail.setWhoReceive(destinatario);
		datosMail.setSubject(asuntoMail);
		datosPlantilla.put(plantillaMailKeyAsunto, datosMail.getSubject());
		datosPlantilla.put(plantillaMailKeyNombre, nombreCompletoConstructor(nombre, apellido, genero, Boolean.TRUE));
		emailService.enviarMailTemplateThymeleaf(datosMail, plantillaMail, datosPlantilla);
	}

	@ResponseBody
	@ApiOperation(value = "/usuario-actualizar-rol", notes = "Actualiza los datos del usuario")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Recuperación de datos exitosa"),
			@ApiResponse(code = 500, message = "El token de seguridad no es válido o existe otro error en el procesamiento a nivel de servidor"),
			@ApiResponse(code = 401, message = "La url a la que se intenta acceder no esta autorizada"),
			@ApiResponse(code = 403, message = "La url a la que se intenta acceder no esta autorizada para el usuario solicitante"),
			@ApiResponse(code = 404, message = "Not Found. La url que se intenta acceder no se encuetra disponible o no existe"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, validar vía Swagger provisto") })
	@RequestMapping(value = "/usuario-actualizar-rol", method = RequestMethod.PUT)
	public ResponseEntity<?> usuarioActualizarRol(@RequestBody UsuarioDto usuario) {
		try {
			Usuario usuarioAuxiliar = usuarioService.obtenerUsuarioPorUsername(usuario.getUsername());
			if (usuarioAuxiliar != null) {
				if (usuarioService.actualizarRolUsuario(usuario)) {
					return new ResponseEntity<String>(mensajeService.crearRespuestaJson("usuario->actualizado","",""), 
							HttpStatus.OK);
				} else {
					return new ResponseEntity<String>(mensajeService.crearRespuestaJson("usuario->datos-incorrectos","",""),
							HttpStatus.I_AM_A_TEAPOT);
				}
			} else {
				return new ResponseEntity<String>(mensajeService.crearRespuestaJson("usuario->no-existe","",""), 
						HttpStatus.I_AM_A_TEAPOT);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(mensajeService.crearRespuestaJson("usuario->proceso-solicitud-fallido","",""),
					HttpStatus.I_AM_A_TEAPOT);
		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@PutMapping(value = "/usuario/actualizar-password")
	public ResponseEntity<?> putActualziarPasswordUsuario(
			@RequestBody @Valid UsuarioActualizarContrasenaDto usuarioActualizarContrasena, BindingResult result) {
		try {
			if (!result.hasErrors()) {
				if (usuarioService.actualizarContrasena(usuarioActualizarContrasena)) {
					return new ResponseEntity<String>(
							mensajeService.crearRespuestaJson("USUARIO_CONTRASENA_ACTUALIZADA","",""), 
							HttpStatus.OK);
				} else {
					return new ResponseEntity<String>(
							mensajeService.crearRespuestaJson("USUARIO_CONTRASENA_ACTUAL_NO_COINCIDE","",""), 
							HttpStatus.I_AM_A_TEAPOT);
				}
			} else {
				return new ResponseEntity<String>(
						mensajeService.crearRespuestaJson("Registro-usuario->datos-usuario-con-error","",""),
						HttpStatus.I_AM_A_TEAPOT);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(					
					mensajeService.crearRespuestaJson("usuario->proceso-solicitud-fallido","",""),
					HttpStatus.I_AM_A_TEAPOT);
		}
	}
}