package co.gov.metropol.area247.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.geometry.GeometryUtil;
import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.mapper.request.UbicacionFavoritRequest;
import co.gov.metropol.area247.mapper.request.UbicacionFavoritaRequest;
import co.gov.metropol.area247.mapper.response.CatUbicacionFavoritaResponse;
import co.gov.metropol.area247.mapper.response.UbicacionFavoritaResponse;
import co.gov.metropol.area247.model.ConCatUbicacionFavoritaDTO;
import co.gov.metropol.area247.model.ConDireccionFavoritaDTO;
import co.gov.metropol.area247.service.ICatUbicacionFavoritaService;
import co.gov.metropol.area247.service.IConDireccionFavoritaService;
import co.gov.metropol.area247.service.ITipoParametroService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.constantes.Constantes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "permite el almacenamiento de la información de las ubicaciones origen y destino consultadas por el usuario como favoritos para su posterior consulta", produces = MediaType.APPLICATION_JSON_VALUE, tags = {
		"ubicaciones" })
@RestController
@RequestMapping("/ubicaciones")
public class UbicacionesFavoritasController {

	@Autowired
	private IConDireccionFavoritaService direccionFavoritaService;

	@Autowired
	private ITipoParametroService tipoParametroService;

	@Autowired
	private ICatUbicacionFavoritaService catUbicacionFavoritaService;

	@ApiOperation(value = "Obtener la ubicaion favorita", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = UbicacionFavoritaResponse.class),
			@ApiResponse(code = 204, message = "No la informacion de la ubicaion"),
			@ApiResponse(code = 500, message = "Intentalo mas tarde") })
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
@RequestMapping(value = "/{nombre}/{id-usuario}", method = RequestMethod.GET)
	public ResponseEntity<UbicacionFavoritaResponse> informacionDeUbicacionFavorita(
			@PathVariable(value = "nombre") String nombre, @PathVariable(value = "id-usuario") Long idUsuario) {

		ConDireccionFavoritaDTO direccionFavorita = direccionFavoritaService.findByNombreAndIdUsuario(nombre,
				idUsuario);

		if (!Utils.isNull(direccionFavorita)) {
			return new ResponseEntity<UbicacionFavoritaResponse>(new UbicacionFavoritaResponse(direccionFavorita),
					HttpStatus.OK);
		}
		return new ResponseEntity<UbicacionFavoritaResponse>(new UbicacionFavoritaResponse(), HttpStatus.NO_CONTENT);

	}

	@ApiOperation(value = "Obtener las ubicaiones favoritas", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = UbicacionFavoritaResponse.class),
			@ApiResponse(code = 204, message = "No la informacion de la ubicaion"),
			@ApiResponse(code = 500, message = "Intentalo mas tarde") })
	@RequestMapping(value = "/{id-usuario}", method = RequestMethod.GET)
	public ResponseEntity<List<UbicacionFavoritaResponse>> informacionDeUbicacionesFavoritas(
			@PathVariable(value = "id-usuario") Long idUsuario) {

		List<ConDireccionFavoritaDTO> direccionesFavoritas = direccionFavoritaService.findByIdUsuario(idUsuario);
		List<UbicacionFavoritaResponse> ubicaciones = new ArrayList<>();

		if (!Utils.isNull(direccionesFavoritas)) {
			direccionesFavoritas.iterator().forEachRemaining(ubicacion -> {
				UbicacionFavoritaResponse ubicacionR = new UbicacionFavoritaResponse(ubicacion);
				ubicaciones.add(ubicacionR);
			});
			return new ResponseEntity<List<UbicacionFavoritaResponse>>(ubicaciones, HttpStatus.OK);
		}
		return new ResponseEntity<List<UbicacionFavoritaResponse>>(ubicaciones, HttpStatus.NO_CONTENT);

	}

	@ApiOperation(value = "Almacenar una ubicaion favorita", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<UbicacionFavoritaResponse> createDireccionFavorita(
			@ModelAttribute UbicacionFavoritRequest ubicacion) {

		try {

			ConDireccionFavoritaDTO direccion = direccionFavoritaService.findByNombreAndIdUsuario(ubicacion.getNombre(),
					ubicacion.getIdUsuario());
			Long numDirecciones = direccionFavoritaService.countByIdUsuario(ubicacion.getIdUsuario());
			Long maxNumDirecciones = tipoParametroService
					.obtenerValorEntero(Constantes.TipoParametro.MAX_DIRECCIONES_FAVORITAS);

			if (direccion == null && numDirecciones <= maxNumDirecciones) {
				direccion = direccionFavoritaService.saveUbicacionFavorita(ubicacion.getNombre(),
						ubicacion.getDescripcion(), ubicacion.getLatitud(), ubicacion.getLongitud(),
						ubicacion.getIdUsuario(), ubicacion.getIdCategoria());

				return new ResponseEntity<>(new UbicacionFavoritaResponse(direccion),
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(new UbicacionFavoritaResponse(direccion),
						HttpStatus.CONFLICT);
			}

		} catch (Exception e) {

			LoggingUtil.logException("Error al crear direccion favorita ", e);
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	@ApiOperation(value = "Actualizar una ubicaion favorita", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<UbicacionFavoritaResponse> updateUbicacionFavorita(
			@ModelAttribute UbicacionFavoritaRequest ubicacion) {

		if (direccionFavoritaService.isCondireccionFavoritaExist(ubicacion.getId())) {
			ConDireccionFavoritaDTO direccion = direccionFavoritaService.findById(ubicacion.getId());
			direccion.setDescripcion(ubicacion.getDescripcion());
			direccion.setCoordenada(GeometryUtil.obtenerPunto(ubicacion.getLatitud(), ubicacion.getLongitud()));
			direccion.setIdUsuario(ubicacion.getIdUsuario());
			direccion.setNombre(ubicacion.getNombre());
				ConCatUbicacionFavoritaDTO ubicaFav = new ConCatUbicacionFavoritaDTO();
				ubicaFav.setId(ubicacion.getIdCategoria());
			direccion.setCategoriaDTO(ubicaFav);

			direccionFavoritaService.updateDireccionFavorita(direccion);
			return new ResponseEntity<UbicacionFavoritaResponse>(new UbicacionFavoritaResponse(direccion),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<UbicacionFavoritaResponse>(new UbicacionFavoritaResponse(), HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Eliminar una ubicaion favorita", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<UbicacionFavoritaResponse> deleteUser(@PathVariable("id") Long id) {
		if (direccionFavoritaService.isCondireccionFavoritaExist(id)) {
			direccionFavoritaService.deleteDireccionFavoritaById(id);
			return new ResponseEntity<UbicacionFavoritaResponse>(HttpStatus.OK);
		} else {
			return new ResponseEntity<UbicacionFavoritaResponse>(HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Obtener todas las categorias de las ubicaciones favoritas", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@RequestMapping(value = "/categorias", method = RequestMethod.POST)
	public ResponseEntity<List<CatUbicacionFavoritaResponse>> getAllCategorias() {
		try {
			List<ConCatUbicacionFavoritaDTO> dtos = catUbicacionFavoritaService.getAll();
			if (Utils.isNotEmpty(dtos)) {
				List<CatUbicacionFavoritaResponse> categorias = new ArrayList<>();
				dtos.iterator().forEachRemaining(dto -> {
					categorias.add(new CatUbicacionFavoritaResponse(dto));
				});
				return new ResponseEntity<>(categorias, HttpStatus.OK);
			} else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LoggingUtil.logDebug("Error al obtener las categorias de las ubicaciones favoritas");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
}
