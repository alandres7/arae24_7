package co.gov.metropol.area247.contenedora.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.model.Aplicacion;
import co.gov.metropol.area247.contenedora.model.dto.AplicacionDto;
import co.gov.metropol.area247.contenedora.model.dto.AplicacionDtoSinListas;
import co.gov.metropol.area247.core.domain.Icono;

@Transactional
@Repository
public interface IContenedoraAplicacionRepository extends CrudRepository<Aplicacion, Long> {
	
	/* Para Menú de centro de control*/
	@Query("SELECT new co.gov.metropol.area247.contenedora.model.dto.AplicacionDto(a.id, "
			+ "a.nombre, a.descripcion, a.codigoColor, a.codigoToggle, a.activo, a.defaultRadius, a.minRadius, a.maxRadius, a.otherPrefs, i) "
			+ "FROM Aplicacion as a join a.icono as i ")
	List<AplicacionDto> aplicacionDtoObtenerTodos();

	/* Para Menú */
	@Query("SELECT new co.gov.metropol.area247.contenedora.model.dto.AplicacionDto("
			+ "a.id, a.nombre, a.codigoColor, a.codigoToggle, a.activo, a.defaultRadius, a.minRadius, a.maxRadius, a.otherPrefs, "
			+ " a.ultimaActualizacion, i.rutaLogo) FROM Aplicacion as a join a.icono as i WHERE a.id = ?1")
	AplicacionDto aplicacionDtoObtenerMenu(Long aplicacionId);

	/* Para Preferencias de usuario */
	@Query("SELECT new co.gov.metropol.area247.contenedora.model.dto.AplicacionDto("
			+ "a.id, a.nombre, a.activo, a.defaultRadius, a.minRadius, a.maxRadius, a.otherPrefs, "
			+ " a.ultimaActualizacion) FROM Aplicacion as a WHERE a.id = ?1")
	AplicacionDto aplicacionDtoObtenerPreferenciasUsuario(Long aplicacionId);

	Aplicacion findByNombre(String nombre);

	@Query(name = "obtenerAplicacionDtoPorIdCategoria", value = "SELECT new co.gov.metropol.area247.contenedora.model.dto.AplicacionDto(p.id, p.nombre, p.codigoColor,"
			+ " p.codigoToggle, p.activo, p.defaultRadius, p.minRadius, p.maxRadius, p.otherPrefs)" 
			+ " FROM Aplicacion p" 
			+ "    INNER JOIN p.capas c"
			+ "    INNER JOIN  c.categorias ca " + "WHERE ca.id = :idCategoria")
	AplicacionDto obtenerAplicacionDtoPorIdCategoria(@Param("idCategoria") final Long idCategoria);
	
	@Query("SELECT new co.gov.metropol.area247.contenedora.model.dto.AplicacionDtoSinListas( "
			+ "a.id, a.nombre, a.descripcion, a.codigoColor, a.codigoToggle,a.activo,"
			+ "a.defaultRadius, a.minRadius, a.maxRadius, a.otherPrefs, a.ultimaActualizacion, a.icono) FROM Aplicacion as a "
			+ "WHERE a.id = ?1")
	AplicacionDtoSinListas aplicacionDtoSinListasObtenerPorId(Long idAplicacion);
	
	@Query("SELECT a.nombre FROM Aplicacion as a ")
	List<String> aplicacionDtoObtenerNombres();
	
}
