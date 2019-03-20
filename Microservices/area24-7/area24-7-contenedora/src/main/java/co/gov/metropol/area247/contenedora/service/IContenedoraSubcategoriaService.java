package co.gov.metropol.area247.contenedora.service;

import java.util.List;

import co.gov.metropol.area247.contenedora.model.Subcategoria;
import co.gov.metropol.area247.contenedora.model.dto.SubcategoriaDtoSinListas;

public interface IContenedoraSubcategoriaService {

	boolean subcategoriaCrear(Subcategoria subcategoria, Long idCategoria);
	Subcategoria subcategoriaObtenerPorId(Long idSubcategoria);
	Subcategoria subcategoriaObtenerPorNombre(String nombreSubcategoria);
	List<Subcategoria> subcategoriaObtenerTodas();
	List<Subcategoria> subcategoriaObtenerPorIdCategoria(Long idCategoria);
	boolean subcategoriaActualizar(Subcategoria subcategoria);
	boolean subcategoriaEliminar(Long idSubcategoria);
	/**
	 * Metodo para retornar las subcategorias sin marcadores
	 * @param idSubCategoria
	 * @return Subcategoria sin listas
	 * @throws Exception
	 */
	SubcategoriaDtoSinListas subcategoriaDtoSinListasObtenerPorId
		(Long idSubCategoria)throws Exception; 
	/**
	 * Metodo para retornar una lista de subcategorias sin listas
	 * @return lista de subcategorias sin listas como Marcadores
	 * @throws Exception
	 */
	List<SubcategoriaDtoSinListas> subcategoriaDtoSinListasObtenerTodos()throws Exception;
	/**
	 * Servicio para obtener las categorias sin listas
	 * @param idCategoria de la categoria a retornar
	 * @return Lista con 0 o más elementos sin listas
	 * @throws Exception si no se puede retornar
	 */
	List<SubcategoriaDtoSinListas> subcategoriaDtoSinListasObtenerPorIdCategoria
		(Long idCategoria) throws Exception;
	/**
	 * Servicio para obtener la cantidad de marcadores de la subcategoria
	 * @param idSubcategoria id de la subcategoria
	 * @return Valor numerico entero de la cantidad de subcategorias
	 * @throws Exception si algo sale mal
	 */
	Long subcategoriaObtenerCantidadMarcadores(Long idSubcategoria)throws Exception;
	
	
}
