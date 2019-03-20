package co.gov.metropol.area247.contenedora.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.gov.metropol.area247.contenedora.model.VentanaInformacion;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraVentanaInformacionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api")
@Api(value = "Contenedora") 
public class VentanaInformacionRestController {
	
	@Autowired
	IContenedoraVentanaInformacionService ventanaInformacionService;
	
	@Autowired
	IContenedoraIconoService iconoService;
	
	
	@ResponseBody
	@ApiOperation(value = "/ventana-informacion", notes = "Permite la consulta de las diferentes ventanas de información que aparecen al hacer clic sobre los elementos del mapa en el sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Recuperación exitosa"),
            @ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"),
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/ventanaInformacion", method = RequestMethod.GET)
	public ResponseEntity<?> ventanaInformacionObtenertodas()
	{
		try 
		{
			return new ResponseEntity<List<VentanaInformacion>>(ventanaInformacionService.ventanaInformacionObtenerTodas(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud, reintentar", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@ResponseBody
	@ApiOperation(value = "/ventana-informacion", notes = "Permite la creación de una ventana de información que aparece al hacer clic sobre un elemento(s) del mapa en el sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = {
			@ApiResponse(code = 300, message = "Creación exitosa"),
            @ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"),
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/ventana-informacion", method = RequestMethod.POST)
	public ResponseEntity<?> ventanaInformacionCrear(@RequestParam(value = "icono", required = false) MultipartFile icono,
													@RequestParam(value="nombre") String nombre,
													@RequestParam(value="descripcion") String descripcion,
													@RequestParam(value="rutaImagen", required = false) String rutaImagen,
													@RequestParam(value="enlaceWeb", required = false) String enlaceWeb,
													@RequestParam(value="color") String color)
	{
		try
		{
			VentanaInformacion ventanaInformacion = ventanaInformacionService.ventanaInformacionObtenerPorNombre(nombre);
			if(ventanaInformacion!=null){
				return new ResponseEntity<String>("Conflicto. Ya existe una ventana de información creada con el mismo nombre", HttpStatus.CONFLICT);
			}else {
				ventanaInformacion = new VentanaInformacion();
				if(icono!=null) {
					Long idIcono = iconoService.iconoCrear(icono,null);
					if(idIcono != null){
						ventanaInformacion.setIcono(iconoService.iconoObtenerPorId(idIcono));
					}
				}
				ventanaInformacion.setNombre(nombre);
				ventanaInformacion.setDescripcion(descripcion);
				ventanaInformacion.setEnlaceWeb(enlaceWeb);
				ventanaInformacion.setColor(color);
				try{
					ventanaInformacionService.ventanaInformacionCrear(ventanaInformacion);
					return new ResponseEntity<Long>(ventanaInformacionService.ventanaInformacionObtenerPorNombre(nombre).getId(), HttpStatus.CREATED);
				}catch(Exception e) {
					return new ResponseEntity<String>("No se ha podido crear la ventana de informacion, revisar datos y reintentar", HttpStatus.BAD_REQUEST);
				}
			}
			
		}catch(Exception e) {
			return new ResponseEntity<String>("No se ha podido procesar la solicitud, reintentar", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ResponseBody
	@ApiOperation(value = "/ventana-informacion", notes = "Permite la creación de una ventana de información que aparece al hacer clic sobre un elemento(s) del mapa en el sistema Área 24/7 del Área Metropolitana del Valle de Aburrá")
	@ApiResponses(value = {
			@ApiResponse(code = 300, message = "Creación exitosa"),
            @ApiResponse(code = 404, message = "Not Found. No se encuentra el recurso solicitado"),
			@ApiResponse(code = 400, message = "Bad Request. El request solicitado tiene problemas sintácticos o semánticos, revisar el ejemplo Swagger provisto"),
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/ventana-informacion", method = RequestMethod.PUT)
	public ResponseEntity<?> ventanaInformacionActualizar(@RequestBody VentanaInformacion ventanaInformacion)
	{
		try
		{
			ventanaInformacionService.ventanaInformacionCrear(ventanaInformacion);
			return new ResponseEntity<String>("Ventana de información creada exitosamente", HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<String>("No se ha podido crear la ventana de información, revisar datos y reintentar", HttpStatus.BAD_REQUEST);
		}
	}

}
