package co.gov.metropol.area247.contenedora.service;

import java.util.List;

import co.gov.metropol.area247.contenedora.model.Categoria;
import co.gov.metropol.area247.contenedora.model.dto.CategoriaDto;

public interface IContenedoraCategoriaService {
	
	boolean categoriaCrear(Categoria categoria, Long idCapa);
	
	boolean categoriaCrear(CategoriaDto categoria, Long idCapa);
	
	boolean categoriaCrear(Categoria categoria);
	
	boolean categoriaCrear(CategoriaDto categoria);
	
	Categoria categoriaObtenerPorId(Long idCategoria);
	
	Categoria categoriaObtenerPorNombre(String nombreCategoria);
	
	boolean categoriaActualizar(Categoria categoria);
	
	boolean categoriaEliminar(Long idCategoria);
	
	List<String> categoriaDtoObtenerNombres() throws Exception;
	
	/**
	 * Metodo para obtener una categoria por id sin listas
	 * @param idCategoria
	 * @return Categoria con el id dado y sin litas
	 * @throws Exception
	 */
	CategoriaDto categoriaDtoObtenerPorId(Long idCategoria)throws Exception;
	
	
	/**
	 * Metodo para obtener una lista de categorias de una capa dada
	 * @param idCapa id de la capa a recuperar
	 * @return
	 * @throws Exception
	 */
	List<CategoriaDto> categoriaDtoObtenerPorIdCapa(Long idCapa)throws Exception;
	
	List<String> categoriaDtoObtenerUrls() throws Exception;
	
}
