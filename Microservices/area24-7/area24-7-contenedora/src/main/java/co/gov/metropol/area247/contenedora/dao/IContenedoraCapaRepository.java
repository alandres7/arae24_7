package co.gov.metropol.area247.contenedora.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.model.Capa;
import co.gov.metropol.area247.contenedora.model.dto.CapaDto;

@Transactional
@Repository
public interface IContenedoraCapaRepository extends CrudRepository<Capa, Long> {

	@Query("select new co.gov.metropol.area247.contenedora.model.dto.CapaMenuDto(c.id, c.nombre, c.activo, c.favorito,"
			+ "c.ultimaActualizacion, i.rutaLogo) from Capa as c join c.icono as i where c.id = ?1")
	CapaDto capaDtoObtenerMenu(Long capaId);

	Capa findByNombre(String nombre);

	@Query("SELECT new co.gov.metropol.area247.contenedora.model.dto.CapaDto(c.id, c.nombre, c.descripcion, c.activo,"
			+ "c.favorito, c.contieneHistoria , c.ultimaActualizacion, icon, iconM, c.urlRecurso, c.aliasNombre, c.aliasMunicipio, "
			+ "c.aliasDescripcion, c.aliasCategoria, c.aliasImagen, c.aliasDireccion, c.tipoCapa, c.fichaCaracterizacion, c.poligono) "
			+ "FROM Capa AS c left join c.icono as icon left join c.iconoMarcador as iconM WHERE c.id= ?1")
	CapaDto capaDtoObtenerPorId(Long idCapa);

	@Query("SELECT new co.gov.metropol.area247.contenedora.model.dto.CapaDto(c.id, c.nombre, c.descripcion, c.activo, "
			+ "c.favorito, c.contieneHistoria, c.ultimaActualizacion, icon, iconM, c.urlRecurso, c.aliasNombre, c.aliasMunicipio, "
			+ "c.aliasDescripcion, c.aliasCategoria, c.aliasImagen, c.aliasDireccion, c.tipoCapa, c.fichaCaracterizacion, c.poligono) "
			+ "FROM Capa AS c join c.aplicacion as ap left join c.icono as icon left join c.iconoMarcador as iconM where ap.id= ?1")
	List<CapaDto> capaDtoObtenerPorIdAplicacion(Long idAplicacion);

	@Query("SELECT c.nombre FROM Capa AS c ")
	List<String> capaDtoObtenerNombres();

	@Query("SELECT new co.gov.metropol.area247.contenedora.model.Capa(c.id, c.nombre)"
			+ " FROM Capa c INNER JOIN c.aplicacion a " + " INNER JOIN c.tipoCapa tc WHERE a.id = ?1 AND tc.id IN(5,8)")
	List<Capa> obtenerCapaPorIdAplicacion(Long idAplicacion);

	List<Capa> findByFichaCaracterizacion(Boolean fichaCaracterizacion);
	
	@Query("SELECT c.urlRecurso FROM Capa AS c WHERE c.urlRecurso is not null")
	List<String> capaDtoObtenerUrls();

}
