package co.gov.metropol.area247.centrocontrol.restcontroller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.centrocontrol.model.Objeto;
import co.gov.metropol.area247.centrocontrol.model.PermisoUsuario;
import co.gov.metropol.area247.centrocontrol.model.dto.PermisoUsuarioDto;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlObjetoService;
import co.gov.metropol.area247.centrocontrol.service.ICentroControlPermisoUsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@Api(value = "Permiso Usuario Objetos")
@RequestMapping(value = "/api")
public class PermisoUsuarioRestController {
	@Autowired
	ICentroControlPermisoUsuarioService permisoUsuarioService;
	@Autowired
	ICentroControlObjetoService objetoService;
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/permisousuario/", method = RequestMethod.POST)
	public ResponseEntity<?> permisoUsuarioGuardar(@RequestBody PermisoUsuario permiso){
		try{
			PermisoUsuario permisoAux = permisoUsuarioService.permisoUsuarioGuardar(permiso);
			if(permisoAux != null) {
				return new ResponseEntity<PermisoUsuario>(permisoAux, HttpStatus.OK);
			}
			return new ResponseEntity<String>("No se pudo crear el objeto, revisar datos y reintentar", HttpStatus.BAD_REQUEST);
		}catch(Exception e){
			return new ResponseEntity<String>("No se pudo crear el objeto, revisar datos y reintentar", HttpStatus.BAD_REQUEST);
		}
	}
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/permisousuario/usuario/{idUsuario}", method = RequestMethod.GET)
	public ResponseEntity<?> permisoUsuarioObtenerPorIdUsuario(@PathVariable ("idUsuario") Long idUsuario){
		List<PermisoUsuario> permisos = permisoUsuarioService.permisoUsuarioObtenerPorIdUsuario(idUsuario);
		if(permisos!=null){
			return new ResponseEntity<List<PermisoUsuario>>(permisos, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(String.format("No se encuentran permisos de objetos asociados al usuario id %s"
					+ " , revisar y reintentar", idUsuario), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/permisousuario/lote", method = RequestMethod.POST)
	public ResponseEntity<?> permisoUsuarioGuardarLote(@RequestBody List<PermisoUsuario> permisos){
		try{
			List<PermisoUsuario> permisosAux = permisoUsuarioService.permisoUsuarioGuardarPorLote(permisos);
			if(permisosAux != null) {
				return new ResponseEntity<List<PermisoUsuario>>(permisos, HttpStatus.OK);
			}
			return new ResponseEntity<List<PermisoUsuario>>(permisos, HttpStatus.BAD_REQUEST);
		}catch(Exception e){
			return new ResponseEntity<String>("No se pudo crear los objetos, revisar datos y reintentar", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/permisousuario/objetos/usuario/{idUsuario}", method = RequestMethod.GET)
	public ResponseEntity<?> permisoUsuarioDtoObtenerPorIdUsuario(@PathVariable ("idUsuario") Long idUsuario){
		List<PermisoUsuario> permisos = permisoUsuarioService.permisoUsuarioObtenerPorIdUsuario(idUsuario);
		if(permisos!=null){
			List<PermisoUsuarioDto> permisosUsuarioDto = new ArrayList<PermisoUsuarioDto>();
			for(PermisoUsuario permiso:permisos){
				if(permiso!=null){
					PermisoUsuarioDto permisoDto = new PermisoUsuarioDto(permiso.getId(),permiso.getIdUsuario(),
							permiso.getIdObjeto(),permiso.isPuedeAdicionar(),permiso.isPuedeEditar(),
							permiso.isPuedeBorrar(),permiso.isPuedeImprimir(),permiso.isPuedeConsultar(),"");	
						Objeto objetoTemporal = objetoService.objetoObtenerPorId(permiso.getIdObjeto());
						if(objetoTemporal != null){
							permisoDto.setNombreObjeto(objetoTemporal.getNombre());
						}
						permisosUsuarioDto.add(permisoDto);
				}
			}
			return new ResponseEntity<List<PermisoUsuarioDto>>(permisosUsuarioDto, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(String.format("No se encuentran permisos de objetos "
					+ "asociados al usuario con id %s, revisar y reintentar", idUsuario), HttpStatus.BAD_REQUEST);
		}
	}
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/permisousuario/{idPermisoUsuario}", method = RequestMethod.DELETE)
	public ResponseEntity<String> permisoUsuarioEliminar(@PathVariable ("idPermisoUsuario") Long idPermisoUsuario){
			try{
				boolean resultadoBorrado =permisoUsuarioService.permisoUsuarioEliminar(idPermisoUsuario);
				if(resultadoBorrado) {
					return new ResponseEntity<String>(String.format("Objeto con id %s eliminado correctamente", idPermisoUsuario) , HttpStatus.OK);
				}
				return new ResponseEntity<String>("No ha sido posible la eliminación del objeto con id " + idPermisoUsuario, HttpStatus.INTERNAL_SERVER_ERROR);
			}catch(Exception e){
				return new ResponseEntity<String>("No ha sido posible la eliminación del objeto con id " + idPermisoUsuario, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}	
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header")
	})
	@RequestMapping(value = "/permisousuario/usuario/objeto", method = RequestMethod.GET)
	public ResponseEntity<?> permisoUsuarioObtenerPorUsuarioPorObjeto(@RequestParam("idUsuario") Long idUsuario,
			@RequestParam("idObjeto")Long idObjeto){
		try{
			PermisoUsuario permisos = permisoUsuarioService.permisoUsuarioObtenerPermisoPorUsuarioPorObjeto(idUsuario,idObjeto);
			if(permisos!=null){
				return new ResponseEntity<PermisoUsuario>(permisos, HttpStatus.OK);
			}else{
				return new ResponseEntity<String>(String.format("No se encuentran permisos del objeto con id %s"
						+ " asociados al usuario con id %s, revisar y reintentar",  idObjeto,idUsuario), HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<String>("Revisar que los ids de objeto o de usuario no estén nulos"
					+ " asociados al usuario con id %s, revisar y reintentar", HttpStatus.BAD_REQUEST);

		}
	}
}
