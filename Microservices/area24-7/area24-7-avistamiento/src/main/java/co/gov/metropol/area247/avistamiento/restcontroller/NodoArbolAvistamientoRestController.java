package co.gov.metropol.area247.avistamiento.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.gov.metropol.area247.avistamiento.domain.context.app.InfoNodoAvistamiento;
import co.gov.metropol.area247.avistamiento.repository.InfoNodoAvistamientoRepository;
import co.gov.metropol.area247.core.restcontroller.BaseRestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(value = "/api/avistamiento/arbol")
public class NodoArbolAvistamientoRestController extends BaseRestController {

	private List<InfoNodoAvistamiento> infoNodosAvistamientos;

	private HttpStatus status = HttpStatus.OK;

	private static Logger CONSOLE = LoggerFactory.getLogger(NodoArbolAvistamientoRestController.class);

	@Autowired
	InfoNodoAvistamientoRepository infoNodoAvistamientoRepository;

	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping(value = "/{idNodoPadre}")
	public ResponseEntity<List<InfoNodoAvistamiento>> obtenerInfoNodoArbolPorIdNodoPadre(
			@PathVariable Long idNodoPadre) {

		infoNodoAvistamientoRepository.obtenerInformacionDelNodoPorNodoPadre(idNodoPadre)
				.subscribe(data -> infoNodosAvistamientos = data, error -> {
					status = HttpStatus.INTERNAL_SERVER_ERROR;
					CONSOLE.error("Error al obteber los nodos por id nodo padre --{}{}", idNodoPadre, error);
				});
		return new ResponseEntity<List<InfoNodoAvistamiento>>(infoNodosAvistamientos, status);
	}

}
