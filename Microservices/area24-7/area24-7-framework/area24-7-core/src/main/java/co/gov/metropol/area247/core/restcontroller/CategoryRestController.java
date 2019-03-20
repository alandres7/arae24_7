package co.gov.metropol.area247.core.restcontroller;

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

import co.gov.metropol.area247.core.domain.context.web.Categoria;
import co.gov.metropol.area247.core.repository.CategoriaRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RequestMapping(value = "/core/category")
@RestController
public class CategoryRestController {

	private static Logger LOGGER = LoggerFactory.getLogger(CategoryRestController.class);

	@Autowired
	private CategoriaRepository categoriaRepository;

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", dataType = "string", paramType = "header") })
	@GetMapping(value = "/{idCapa}/{tipoCategorias}")
	public ResponseEntity<List<Categoria>> obtenerCategoriasPorIdCapaYTipoCategorias(@PathVariable Long idCapa,
			@PathVariable String tipoCategorias) {
		try {
			return new ResponseEntity<List<Categoria>>(
					categoriaRepository.obtenerCategoriaPorCapaYTipoCategorias(idCapa, tipoCategorias),
					HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error al consultar las categorias por el id capa --{}{}", e);
			return new ResponseEntity<List<Categoria>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
