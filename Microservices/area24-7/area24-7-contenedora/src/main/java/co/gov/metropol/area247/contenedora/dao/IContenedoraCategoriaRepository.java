package co.gov.metropol.area247.contenedora.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.model.Categoria;
import co.gov.metropol.area247.contenedora.model.dto.CategoriaDto;

@Repository
public interface IContenedoraCategoriaRepository extends CrudRepository<Categoria, Long> {
	
	Categoria findByNombre(String nombre);
	
	
	@Query("select new co.gov.metropol.area247.contenedora.model.dto.CategoriaDto(c.id, "
			+ "c.nombre, c.descripcion, icon, iconM, c.urlRecurso, c.aliasNombre, c.aliasMunicipio, "
			+ "c.aliasDescripcion, c.aliasTipo, c.aliasImagen, c.aliasDireccion, c.fichaCaracterizacion, "
			+ "c.respuestaFichaCarac, c.tipoCategoria, c.tieneHistoria, c.poligono, c.msgOrdenamiento) "
			+ "from Categoria as c left join c.icono as icon left join c.iconoMarcador as iconM "
			+ "where c.id = ?1")
	CategoriaDto categoriaDtoObtenerPorId(Long id);
	
	@Query("select new co.gov.metropol.area247.contenedora.model.dto.CategoriaDto(c.id, "
			+ "c.nombre, c.descripcion, ico, icoM, c.urlRecurso, c.aliasNombre, c.aliasMunicipio, "
			+ "c.aliasDescripcion, c.aliasTipo, c.aliasImagen, c.aliasDireccion, c.fichaCaracterizacion, "
			+ "c.respuestaFichaCarac, c.tipoCategoria, c.tieneHistoria, c.poligono, c.msgOrdenamiento) "
			+ "from Categoria as c join c.capa as cap left join c.icono as ico "
			+ "left join c.iconoMarcador as icoM where cap.id = ?1")
	List<CategoriaDto> categoriaDtoObtenerPorIdCapa(Long idCapa);
	
	@Query("SELECT c.nombre FROM Categoria AS c ")
	List<String> categoriaDtoObtenerNombres();
	
	List<Categoria> findByMsgOrdenamiento(boolean msgOrdenamiento);
	
	@Query("SELECT c.urlRecurso FROM Categoria AS c WHERE c.urlRecurso is not null")
	List<String> categoriaDtoObtenerUrls();
	
}
