package co.gov.metropol.area247.centrocontrol.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.centrocontrol.model.Formulario;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlFormularioService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api")
public class FormularioRestController {
	
	@Autowired
	ICentroControlFormularioService formularioService;
	
	
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/formulario", method = RequestMethod.POST)
	public ResponseEntity<String> formularioCrear(
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "descripcion", required = false) String descripcion,
			@RequestParam(value = "idAplicacion", required = false) Long idAplicacion) {
		try {
			Formulario formulario = new Formulario();
			formulario.setNombre(nombre);
			formulario.setDescripcion(descripcion);
			formulario.setIdAplicacion(idAplicacion);
			if (formularioService.formularioGuardar(formulario)) {
				return new ResponseEntity<String>("Formulario adicionado correctamente", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("No se ha podido agregar el formulario", HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity<String>("Error al intentar guardar el formulario", HttpStatus.BAD_REQUEST);
		}

	}
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/formulario", method = RequestMethod.PUT)
	public ResponseEntity<String> formularioActualizar(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "descripcion", required = false) String descripcion,
			@RequestParam(value = "idAplicacion", required = false) Long idAplicacion) {
		try {
			Formulario formulario = formularioService.formularioObtenerPorId(id);
			if (formulario != null) {
				formulario.setNombre(nombre);
				formulario.setDescripcion(descripcion);
				formulario.setIdAplicacion(idAplicacion);
				if (formularioService.formularioGuardar(formulario)) {
					return new ResponseEntity<String>("Formulario actualizado correctamente", HttpStatus.OK);
				} else {
					return new ResponseEntity<String>("No se ha podido actualizar el formulario",
							HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<String>("No existen formularios correspondientes al id",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Error al intentar actualizar el formulario", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
	@ResponseBody
	@ApiOperation(value = "/formulario", notes = "Permite la consulta de los diferentes formularios presentes en el sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Recuperación exitosa"),
            @ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"),
	})	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/formulario", method = RequestMethod.GET)
	public ResponseEntity<?> formularioObtenerTodos()
	{
		try {
			return new ResponseEntity<List<Formulario>>(formularioService.formularioObtenerTodos(), HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>("No se pudo recuperar el listado de formularios", HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/formulario/{idFormulario}", method = RequestMethod.GET)
	public ResponseEntity<?> formularioObternerPorId(@PathVariable("idFormulario")Long idFormulario){
		try {
			Formulario formulario = formularioService.formularioObtenerPorId(idFormulario);
			if(formulario!=null){
				return new ResponseEntity<Formulario>(formulario, HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se encuentra formulario con id " + idFormulario, HttpStatus.NOT_FOUND);
			}	
		} catch (Exception e) {
			return new ResponseEntity<String>("Error ; " + e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}	
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/formulario/{idFormulario}", method = RequestMethod.DELETE)
	public ResponseEntity<?> formularioBorrar(@PathVariable("idFormulario")Long idFormulario){
		try{
			boolean formularioBorrado= formularioService.formularioEliminar(idFormulario);
			if(formularioBorrado){
				return new ResponseEntity<String>("Formulario borrado",HttpStatus.OK);
			}else{
				return new ResponseEntity<String>("No se pudo borrar el formulario"
						+ ", revisar los datos",HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<String>("Hubo un error al borrar el formulario"
					+ ", revisar los datos; "+e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
