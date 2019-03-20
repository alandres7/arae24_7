package co.gov.metropol.area247.entorno.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.gov.metropol.area247.contenedora.service.IContenedoraCategoriaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraVentanaInformacionService;
import co.gov.metropol.area247.entorno.model.Medicion;
import co.gov.metropol.area247.entorno.model.dto.MedicionDto;
import co.gov.metropol.area247.entorno.service.IEntornoMedicionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@ControllerAdvice
@RequestMapping(value = "/api")
public class MedicionRestController {
	
	@Autowired
	IEntornoMedicionService medicionService;
	
	@Autowired
	IContenedoraCategoriaService categoriaService;
	
	@Autowired
	IContenedoraIconoService iconoService;
	
	@Autowired
	IContenedoraVentanaInformacionService ventanaInformacionService;
	
	@ResponseBody
	@ApiOperation(value = "/medicion", notes = "Lista el total de mediciones presentes en el sistema Área 24/7 del Área Metropolitana del Valle de Aburrá en su aplicación Mi Entorno")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Recuperación de datos exitosa"),
			@ApiResponse(code = 500, message = "El token de seguridad no es válido o existe otro error en el procesamiento a nivel de servidor"),
			@ApiResponse(code = 401, message = "La url a la que se intenta acceder no esta autorizada"),
            @ApiResponse(code = 403, message = "La url a la que se intenta acceder no esta autorizada para el usuario solicitante"),
            @ApiResponse(code = 404, message = "Not Found. La url que se intenta acceder no se encuetra disponible o no existe"),
		    @ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, validar vía Swagger provisto")
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/medicion", method = RequestMethod.GET)
	public ResponseEntity<?> medicionObtenerTodas(){
		try
		{	
			return new ResponseEntity<List<Medicion>>(medicionService.medicionObtenerTodas(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@ResponseBody
	@ApiOperation(value = "/medicion", notes = "Permite la creación de una escala de medición para el sistema Área 24/7 del Área Metropolitana del Valle de Aburrá en su aplicación Mi Entorno")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Creación exitosa"),
			@ApiResponse(code = 500, message = "El token de seguridad no es válido o existe otro error en el procesamiento a nivel de servidor"),
			@ApiResponse(code = 401, message = "La url a la que se intenta acceder no esta autorizada"),
            @ApiResponse(code = 403, message = "La url a la que se intenta acceder no esta autorizada para el usuario solicitante"),
            @ApiResponse(code = 404, message = "Not Found. La url que se intenta acceder no se encuetra disponible o no existe"),
		    @ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, validar vía Swagger provisto")
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/medicion", method = RequestMethod.POST)
	public ResponseEntity<?> medicionCrear( @RequestParam (value = "idMedicion", required = false) Long idMedicion, 
			                                @RequestParam (value = "idCapa", required = false) Long idCapa,	
											@RequestParam (value = "nombre") String nombre,
											@RequestParam (value = "descripcion", required = false) String descripcion,
											@RequestParam (value = "color") String color,
											@RequestParam (value = "significado", required = false) String significado,
											@RequestParam (value = "recomendacion", required = false) String recomendacion,
											@RequestParam (value = "escalaInicial") float escalaInicial,
											@RequestParam (value = "escalaFinal") float escalaFinal,
											@RequestParam (value = "idVentanaInformacion", required = false) Long idVentanaInformacion,
											@RequestParam (value = "icono") MultipartFile icono){
		
		if ((escalaInicial == 0.0) && (escalaFinal == 0.0)) {
			return new ResponseEntity<String>("Las 2 escalas están vacías",HttpStatus.OK);
		} else {
			if (medicionService.verificarSolapamientoIntervalos(idMedicion, idCapa, escalaInicial, escalaFinal)) {
				return new ResponseEntity<String>(
						    "El rango de las escalas se solapa con otro ya existente",HttpStatus.OK);
			} else {
				if (idMedicion == null) {
					try {
						Medicion medicionNew = new Medicion();
						if (icono != null && !icono.isEmpty()) {
							Long idIcono = iconoService.iconoCrear(icono, null);
							if (idIcono != null) {
								medicionNew.setIcono(iconoService.iconoObtenerPorId(idIcono));
							}
						} else {
							return new ResponseEntity<String>(
									"Hubo un problema al intentar guardar el ícono, revisar datos y reintentar",
									HttpStatus.BAD_REQUEST);
						}
						medicionNew.setIdCapa(idCapa);
						medicionNew.setNombre(nombre);
						medicionNew.setDescripcion(descripcion);
						medicionNew.setColor(color);
						medicionNew.setSignificado(significado);
						medicionNew.setRecomendacion(recomendacion);
						medicionNew.setEscalaInicial(escalaInicial);
						medicionNew.setEscalaFinal(escalaFinal);
						medicionNew.setVentanaInformacion(ventanaInformacionService.ventanaInformacionObtenerPorId(
								(medicionService.asignarVentanaInformacion(medicionNew))));
						if (medicionService.medicionCrear(medicionNew)) {
							return new ResponseEntity<String>("Medición creada exitosamente con id: "+medicionNew.getId(), 
									HttpStatus.CREATED);
						} else {
							return new ResponseEntity<String>(
								"No ha sido posible crear la medición, revisar datos y reintentar",HttpStatus.BAD_REQUEST);
						}
					} catch (Exception e) {
						return new ResponseEntity<String>(
						    "No ha sido posible crear la medición, revisar datos y reintentar",HttpStatus.BAD_REQUEST);
					}
				} else {
					Medicion medicion = medicionService.medicionObtenerPorId(idMedicion);
					if (medicion == null) {
						return new ResponseEntity<String>(
						    "No se ha encontrado una medición asociada al id",HttpStatus.NOT_FOUND);
					} else {
						try {
							medicion.setNombre(nombre);
							medicion.setDescripcion(descripcion);
							medicion.setColor(color);
							medicion.setSignificado(significado);
							medicion.setRecomendacion(recomendacion);
							medicion.setEscalaInicial(escalaInicial);
							medicion.setEscalaFinal(escalaFinal);
							if (icono != null && !icono.isEmpty()) {
								Long idIcono = iconoService.iconoCrear(icono, medicion.getIcono().getId());
								if (idIcono != null) {
									medicion.setIcono(iconoService.iconoObtenerPorId(idIcono));
								}
							}
							if (idVentanaInformacion != null) {
								medicion.setVentanaInformacion(
										ventanaInformacionService.ventanaInformacionObtenerPorId(idVentanaInformacion));
							} else {
								medicionService.medicionActualizarVentana(medicion);
							}

							if (medicionService.medicionActualizar(medicion)) {
								return new ResponseEntity<String>("Medición actualizada exitosamente con id: " + idMedicion, HttpStatus.OK);
							} else {
								return new ResponseEntity<String>(
								    "No ha sido posible actualizar la medición, revisar datos y reintentar",HttpStatus.BAD_REQUEST);
							}
						} catch (Exception e) {
							return new ResponseEntity<String>(
								"No se pudo realizar la actualización de la medición, revisar datos y reintentar",HttpStatus.BAD_REQUEST);
						}
					}
				}
			}
		}		
	}
	
	
	@ResponseBody
	@ApiOperation(value = "/medicion", notes = "Permite la actualización de una escala de medición para el sistema Área 24/7 del Área Metropolitana del Valle de Aburrá en su aplicación Mi Entorno")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Actualización exitosa"),
			@ApiResponse(code = 500, message = "El token de seguridad no es válido o existe otro error en el procesamiento a nivel de servidor"),
			@ApiResponse(code = 401, message = "La url a la que se intenta acceder no esta autorizada"),
			@ApiResponse(code = 403, message = "La url a la que se intenta acceder no esta autorizada para el usuario solicitante"),
			@ApiResponse(code = 404, message = "Not Found. La url que se intenta acceder no se encuetra disponible o no existe"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, validar vía Swagger provisto") })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/medicion", method = RequestMethod.PUT)
	public ResponseEntity<?> medicionActualizar(@RequestParam(value = "idMedicion") Long idMedicion,
			@RequestParam(value = "nombre") String nombre,
			@RequestParam(value = "descripcion", required = false) String descripcion,
			@RequestParam(value = "color") String color,
			@RequestParam(value = "significado", required = false) String significado,
			@RequestParam(value = "recomendacion", required = false) String recomendacion,
			@RequestParam(value = "escalaInicial") float escalaInicial,
			@RequestParam(value = "escalaFinal") float escalaFinal,
			@RequestParam(value = "idVentanaInformacion", required = false) Long idVentanaInformacion,
			@RequestParam(value = "icono", required = false) MultipartFile icono) {

		if ((escalaInicial == 0.0) && (escalaFinal == 0.0)) {
			return new ResponseEntity<String>("Las 2 escalas están vacías",HttpStatus.OK);
		} else {
			Medicion medicion = medicionService.medicionObtenerPorId(idMedicion);
			if (medicion == null) {
				return new ResponseEntity<String>("No se ha encontrado una medición asociada al id",HttpStatus.NOT_FOUND);
			} else {
				if (medicionService.verificarSolapamientoIntervalos(
						idMedicion, medicion.getId(), escalaInicial,escalaFinal)) {
					return new ResponseEntity<String>("El rango de las escalas se solapa con otro ya existente",
							                           HttpStatus.OK);
				} else {
					try {
						medicion.setNombre(nombre);
						medicion.setDescripcion(descripcion);
						medicion.setColor(color);
						medicion.setSignificado(significado);
						medicion.setRecomendacion(recomendacion);
						medicion.setEscalaInicial(escalaInicial);
						medicion.setEscalaFinal(escalaFinal);
						if (icono != null && !icono.isEmpty()) {
							Long idIcono = iconoService.iconoCrear(icono, medicion.getIcono().getId());
							if (idIcono != null) {
								medicion.setIcono(iconoService.iconoObtenerPorId(idIcono));
							}
						}
						if (idVentanaInformacion != null) {
							medicion.setVentanaInformacion(
									ventanaInformacionService.ventanaInformacionObtenerPorId(idVentanaInformacion));
						} else {
							medicionService.medicionActualizarVentana(medicion);
						}

						medicionService.medicionActualizar(medicion);
						return new ResponseEntity<String>("Actualización exitosa", HttpStatus.OK);
					} catch (Exception e) {
						return new ResponseEntity<String>(
								"No se pudo realizar la actualización de la medición, revisar datos y reintentar",
								HttpStatus.BAD_REQUEST);
					}
				}
			}
		}
	}
	
	
	@ResponseBody
	@ApiOperation(value = "/medicion/{idMedicion}", notes = "Permite la consulta de de una escala de medición para el sistema Área 24/7 del Área Metropolitana del Valle de Aburrá en su aplicación Mi Entorno / Disfrútame")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 500, message = "El token de seguridad no es válido o existe otro error en el procesamiento a nivel de servidor"),
			@ApiResponse(code = 401, message = "La url a la que se intenta acceder no esta autorizada"),
            @ApiResponse(code = 403, message = "La url a la que se intenta acceder no esta autorizada para el usuario solicitante"),
            @ApiResponse(code = 404, message = "Not Found. La url que se intenta acceder no se encuetra disponible o no existe"),
		    @ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, validar vía Swagger provisto")
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/medicion/{idMedicion}", method = RequestMethod.GET)
	public ResponseEntity<?> medicionObtenerPorId(@PathVariable ("idMedicion") Long idMedicion){
		MedicionDto medicion = medicionService.medicionDtoObtenerPorId(idMedicion);
		if(medicion!=null){
			return new ResponseEntity<MedicionDto>(medicion, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("No se encuentra una medición con id " + idMedicion, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/medicion/capa/{idCapa}", method = RequestMethod.GET)
	public ResponseEntity<?> medicionObtenerPorIdCapa(@PathVariable (name = "idCapa") Long idCapa){
		try {
			List<MedicionDto> mediciones = medicionService.medicionDtoObtenerPorIdCapa(idCapa);
			if(mediciones.isEmpty()) {
				return new ResponseEntity<String>("No se encuentran mediciones asociadas a la capa",HttpStatus.NOT_FOUND);
			}else {
				return new ResponseEntity<List<MedicionDto>>(mediciones,HttpStatus.FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<String>("Error recuperando las mediciones",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/medicion/verificarsolapamiento/{idMedicion}/{idCapa}/{escalaInicial}/{escalaFinal}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> verificarSolapamientoIntervalos(@PathVariable (name = "idMedicion") Long idMedicion,
			                                                       @PathVariable (name = "idCapa") Long idCapa,
			                                                       @PathVariable (name = "escalaInicial") float escalaInicial,
			                                                       @PathVariable (name = "escalaFinal") float escalaFinal){
		try {	
			if(medicionService.verificarSolapamientoIntervalos(idMedicion,idCapa,escalaInicial,escalaFinal)) {
				return new ResponseEntity<Boolean>(true,HttpStatus.NOT_FOUND);
			}else {
				return new ResponseEntity<Boolean>(false,HttpStatus.FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<Boolean>(true,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/medicion/{idMedicion}", method = RequestMethod.DELETE)
	public ResponseEntity<?> medicionBorrarMedicion( @PathVariable(name="idMedicion") Long idMedicion){
		try {
			boolean resultadoBorrado = medicionService.medicionEliminar(idMedicion);
			if(resultadoBorrado) {
				return new ResponseEntity<String>("Medición borrada",HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("La medición no fue borrada",
						HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			return new ResponseEntity<String>("Hubo un error al borrar la medición",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
    @RequestMapping(value = "/medicion/listaNombres", method = RequestMethod.GET)
	public ResponseEntity<?> obtenerNombresMedicion() {
		try {
			List<String> listaNombre = medicionService.obtenerNombresMedicion();
			if (!listaNombre.isEmpty()) {
				return new ResponseEntity<List<String>>(listaNombre, HttpStatus.FOUND);
			} else {
				return new ResponseEntity<String>("No se encuentra el listado de nombres", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Hubo un error al buscar el listado de nombres",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
