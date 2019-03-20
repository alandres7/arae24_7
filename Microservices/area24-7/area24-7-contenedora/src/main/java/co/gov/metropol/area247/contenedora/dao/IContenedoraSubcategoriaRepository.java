package co.gov.metropol.area247.contenedora.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.model.Subcategoria;
import co.gov.metropol.area247.contenedora.model.dto.SubcategoriaDtoSinListas;

@Repository
public interface IContenedoraSubcategoriaRepository extends CrudRepository<Subcategoria, Long> {
	
	Subcategoria findByNombre(String nombre);
	
	@Query("SELECT new co.gov.metropol.area247.contenedora.model.dto.SubcategoriaDtoSinListas(s) "
			+ "FROM Subcategoria AS s WHERE s.id = ?1")
	SubcategoriaDtoSinListas subcategoriaDtoSinListasObtenerPorId(Long idSubcategoria); 
	
	@Query("SELECT new co.gov.metropol.area247.contenedora.model.dto.SubcategoriaDtoSinListas(s) "
			+ "FROM Subcategoria AS s")
	List<SubcategoriaDtoSinListas> subcategoriaDtoSinListasObtenerTodos();
	
	@Query("SELECT new co.gov.metropol.area247.contenedora.model.dto.SubcategoriaDtoSinListas(s) "
			+ "FROM Subcategoria AS s join s.categoria AS c WHERE c.id = ?1")
	List<SubcategoriaDtoSinListas> subcategoriaDtoSinListasObtenerPorIdCategoria(Long idCategoria);
	
	@Query("SELECT COUNT(m) FROM Subcategoria AS s JOIN s.marcadores AS m WHERE s.id= ?1")
	Long subcategoriaContarMarcadores(Long idSubcategoria);
}
