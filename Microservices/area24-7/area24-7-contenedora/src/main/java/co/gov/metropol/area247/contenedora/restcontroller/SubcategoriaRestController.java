package co.gov.metropol.area247.contenedora.restcontroller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.gov.metropol.area247.contenedora.model.Marcador;
import co.gov.metropol.area247.contenedora.model.Subcategoria;
import co.gov.metropol.area247.contenedora.model.dto.SubcategoriaDtoSinListas;
import co.gov.metropol.area247.contenedora.service.IContenedoraCoordenadaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraSubcategoriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "contenedora")
@RequestMapping("/api")
public class SubcategoriaRestController {

	@Autowired
	IContenedoraSubcategoriaService subcategoriaService;

	@Autowired
	IContenedoraIconoService iconoService;

	@Autowired
	IContenedoraCoordenadaService coordenadaService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SubcategoriaRestController.class);
	
	@ResponseBody
	@ApiOperation(value = "/subcategoria/marcadores", notes = "Permite la consulta de las diferentes subcategorias que clasifican la información del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"), })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/subcategoria/marcadores", method = RequestMethod.GET)
	public ResponseEntity<?> subcategoriaObtenerTodas() {
		try {
			return new ResponseEntity<List<Subcategoria>>(subcategoriaService.subcategoriaObtenerTodas(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud, reintentar",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@ResponseBody
	@ApiOperation(value = "/subcategoria/marcadores/{idSubcategoria}", notes = "Permite la consulta de una subcategorias que clasifican la información del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"), })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/subcategoria/marcadores/{idSubcategoria}", method = RequestMethod.GET)
	public ResponseEntity<?> subcategoriaObtenerPorId(@PathVariable(value = "idSubcategoria") Long idSubcategoria) {
		try {
			Subcategoria subcategoriaAuxiliar = subcategoriaService.subcategoriaObtenerPorId(idSubcategoria);
			
			if (subcategoriaAuxiliar != null) {
				return new ResponseEntity<Subcategoria>(subcategoriaAuxiliar, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("No se encuentra una subcategoría asociada al id",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud, reintentar",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ResponseBody
	@ApiOperation(value = "/subcategoria", notes = "Permite la creación de las diferentes subcategorias que clasifican la información del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Creación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"), })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/subcategoria", method = RequestMethod.POST)
	public ResponseEntity<?> subcategoriaCrear(@RequestParam(value = "idCategoria") Long idCategoria,
			@RequestParam(value = "icono", required = false) MultipartFile icono,
			@RequestParam(value = "nombre") String nombre,
			@RequestParam(value = "descripcion", required = false) String descripcion,
			@RequestParam(value = "marcadores", required = false) List<Marcador> marcadores,
			@RequestParam(value = "iconoMarcador", required = false) MultipartFile iconoMarcador) {
		try {
			if (nombre == null) {
				return new ResponseEntity<String>("El nombre de la subcategoría no puede estar en blanco",
						HttpStatus.BAD_REQUEST);
			} else if (subcategoriaService.subcategoriaObtenerPorNombre(nombre) != null) {
				return new ResponseEntity<String>(
						"Conflicto. Ya existe una subcategoría con el mismo nombre, revisar y reintentar",
						HttpStatus.CONFLICT);
			} else {
				try {
					Subcategoria subcategoria = new Subcategoria();
					if (icono != null && !icono.isEmpty()) {
						iconoService.iconoCrear(icono,null);
						subcategoria.setIcono(iconoService.iconoObtenerPorNombre(icono.getOriginalFilename()));
					}
					if (iconoMarcador != null && !iconoMarcador.isEmpty()) {
						iconoService.iconoCrear(iconoMarcador,null);
						subcategoria.setIconoMarcador(iconoService.iconoObtenerPorNombre(icono.getOriginalFilename()));
					}
					subcategoria.setNombre(nombre);
					subcategoria.setDescripcion(descripcion);
					subcategoria.setMarcadores(marcadores);
					subcategoriaService.subcategoriaCrear(subcategoria, idCategoria);
					return new ResponseEntity<String>("subcategoría creada con éxito", HttpStatus.OK);
				} catch (Exception e) {
					return new ResponseEntity<String>(
							"No ha podido crearse la subcategoría, revisar los datos y reintentar",
							HttpStatus.BAD_REQUEST);
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ResponseBody
	@ApiOperation(value = "/subcategoria", notes = "Permite la actualización de las diferentes subcategorias que clasifican la información del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Actualización exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"), })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/subcategoria", method = RequestMethod.PUT)
	public ResponseEntity<?> subcategoriaActualizar(
			@RequestParam(value = "icono", required = false) MultipartFile icono, @RequestParam(value = "id") Long id,
			@RequestParam(value = "nombre") String nombre,
			@RequestParam(value = "descripcion", required = false) String descripcion,
			@RequestParam(value = "marcadores", required = false) List<Marcador> marcadores,
			@RequestParam(value = "iconoMarcador", required = false) MultipartFile iconoMarcador) {
		try {
			if (nombre == null || id == null) {
				return new ResponseEntity<String>("El nombre ni el id de la subcategoría pueden estar en blanco",
						HttpStatus.BAD_REQUEST);
			} else {
				Subcategoria subcategoria = subcategoriaService.subcategoriaObtenerPorId(id);
				if (subcategoria == null) {
					return new ResponseEntity<String>("No existe una subcategoría asociada al id, revisar y reintentar",
							HttpStatus.CONFLICT);
				} else {
					try {
						if (icono != null && !icono.isEmpty()) {
							iconoService.iconoCrear(icono,subcategoria.getIcono().getId());
							subcategoria.setIcono(iconoService.iconoObtenerPorNombre(icono.getOriginalFilename()));
						}
						if (iconoMarcador != null && !iconoMarcador.isEmpty()) {
							iconoService.iconoCrear(iconoMarcador,subcategoria.getIconoMarcador().getId());
							subcategoria.setIconoMarcador(iconoService.iconoObtenerPorNombre(iconoMarcador.getOriginalFilename()));
						}
						subcategoria.setNombre(nombre);
						subcategoria.setDescripcion(descripcion);
						if (marcadores != null) {
							subcategoria.setMarcadores(marcadores);
						}
						subcategoriaService.subcategoriaActualizar(subcategoria);
						return new ResponseEntity<String>("subcategoría actualizada con éxito", HttpStatus.OK);
					} catch (Exception e) {
						return new ResponseEntity<String>(
								"No ha podido actualizarse la subcategoría, revisar los datos y reintentar",
								HttpStatus.BAD_REQUEST);
					}
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ResponseBody
	@ApiOperation(value = "/subcategoria/{idSubcategoria}", notes = "Permite la eliminación de las diferentes subcategorias que clasifican la información del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Eliminación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"), 
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/subcategoria/{idSubcategoria}", method = RequestMethod.DELETE)
	public ResponseEntity<?> subcategoriaELiminar(@PathVariable ("idSubcategoria") Long idSubcategoria){		
		try {
			if (subcategoriaService.subcategoriaObtenerPorId(idSubcategoria) != null) {
				subcategoriaService.subcategoriaEliminar(idSubcategoria);
				return new ResponseEntity<String>("La subcategoría ha sido eliminada exitosamente", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("La subcategoría que intenta borrar no existe", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud, reintentar",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*@ResponseBody
	@ApiOperation(value = "/subcategoria/categoria/{idCategoria}", notes = "Permite la consulta de las diferentes subcategorias que clasifican la información del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá pertenecientes a una categoria asociada")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 204, message = "Sin contenido para mostrar"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"), })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/subcategoria/categoria/{idCategoria}", method = RequestMethod.GET)
	public ResponseEntity<?> subcategoriaObtenerPorIdCategoria(@PathVariable("idCategoria") Long idCategoria) {
		List<Subcategoria> subcategorias = subcategoriaService.subcategoriaObtenerPorIdCategoria(idCategoria);
		if (subcategorias != null && !subcategorias.isEmpty()) {
			return new ResponseEntity<List<Subcategoria>>(subcategorias, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No se han encontrado subcategorias asociadas a la categoria solicitada",
					HttpStatus.NO_CONTENT);
		}
	}*/

	@ResponseBody
	@ApiOperation(value = "/radio/subcategoria/{idSubcategoria}/{latitud}/{longitud}/{radio}", notes = "Permite la consulta de una subcategorias filtrado por radio de acción que clasifican la información del sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Recuperación exitosa"),
			@ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"), })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/radio/subcategoria/{idSubcategoria}/{latitud}/{longitud}/{radio}", method = RequestMethod.GET)
	// @Transactional(readOnly = true)
	public ResponseEntity<?> subcategoriaObtenerPorId(@PathVariable(value = "idSubcategoria") Long idSubcategoria,
			@PathVariable(value = "latitud") double latitud, @PathVariable(value = "longitud") double longitud,
			@PathVariable(value = "radio") int radio) {
		try {
			Subcategoria subcategoriaAuxiliar = subcategoriaService.subcategoriaObtenerPorId(idSubcategoria);
			if (subcategoriaAuxiliar != null) {
				subcategoriaAuxiliar.getMarcadores().forEach(marcador -> {
//					marcador.setCoordenadas(coordenadaService.obtenerMarcadorPorRadio(latitud, longitud, radio, marcador.getId()));
//					System.out.println("marcador ---> id: " + marcador.getId() + ", nombre: " + marcador.getNombre()
//							+ " coordenadas: " + marcador.getCoordenadas());
				});
				return new ResponseEntity<Subcategoria>(subcategoriaAuxiliar, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("No se encuentra una subcategoría asociada al id",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud, reintentar",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/subcategoria/nolistas/{idSubcategoria}", method = RequestMethod.GET)
	public ResponseEntity<?> capaDtoSinListasObtenerPorId(@PathVariable(name="idSubcategoria")Long idSubcategoria){
		try{
			SubcategoriaDtoSinListas subcategoria = subcategoriaService.subcategoriaDtoSinListasObtenerPorId(idSubcategoria);
			if(subcategoria!=null){
				return new ResponseEntity<SubcategoriaDtoSinListas>(subcategoria,
						HttpStatus.FOUND);
			}else{
				return new ResponseEntity<String>("No se encuentra la subcategoria",
						HttpStatus.NOT_FOUND);
			}
		}catch(Exception e){
			return new ResponseEntity<String>("Hubo un error al recuperar la "
					+ "subcategoria",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/subcategoria/nolistas", method = RequestMethod.GET)
	public ResponseEntity<?> capaDtoSinListasObtenerTodos(){
		try{
			List<SubcategoriaDtoSinListas> subcategorias = subcategoriaService
					.subcategoriaDtoSinListasObtenerTodos();
			if(!subcategorias.isEmpty()){
				return new ResponseEntity<List<SubcategoriaDtoSinListas>>(subcategorias,
						HttpStatus.FOUND);
			}else{
				return new ResponseEntity<String>("No se encuentran subcategorias",
						HttpStatus.NOT_FOUND);
			}
		}catch(Exception e){
			return new ResponseEntity<String>("Hubo un error al recuperar las "
					+ "subcategorias",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/subcategoria/nolistas/categoria/{idCategoria}", method = RequestMethod.GET)
	public ResponseEntity<?> capaDtoSinListasObtenerPorIdCategoria(
				@PathVariable(name="idCategoria") Long idCategoria){
		try {
			List<SubcategoriaDtoSinListas> subcategorias = 
						subcategoriaService.subcategoriaDtoSinListasObtenerPorIdCategoria(idCategoria);
			if(!subcategorias.isEmpty()) {
				return new ResponseEntity<List<SubcategoriaDtoSinListas>>(subcategorias,HttpStatus.FOUND);
			}else {
				return new ResponseEntity<String>("No se encontraron las "
						+ "subcategorias",HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<String>("Hubo un error al recuperar las "
					+ "subcategorias: "+e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/subcategoria/marcadores/cantidad/{idSubcategoria}", method = RequestMethod.GET)
	public ResponseEntity<?> subcategoriaObtenerCantidadMarcadores(@PathVariable(name="idSubcategoria")Long idSubcategoria){
		try {
			Long cantidadMarcadores = subcategoriaService.subcategoriaObtenerCantidadMarcadores(idSubcategoria);
			return new ResponseEntity<Long>(cantidadMarcadores,HttpStatus.OK);
		}catch(Exception e) {
			LOGGER.error("Error recuperando la cantidad de marcadores de la subcategoria; "+e);
			return new ResponseEntity<String>("Hubo un error al consultar la cantidad de "
					+ "marcadores asociados a la subcategoria",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
