package co.gov.metropol.area247.centrocontrol.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.centrocontrol.model.NodoArbol;
import co.gov.metropol.area247.centrocontrol.model.Objeto;
import co.gov.metropol.area247.centrocontrol.model.dto.NodoArbolDto;

@Repository
public interface ICentroControlNodoArbolRepository extends CrudRepository<NodoArbol, Long> {

	List<NodoArbol> findByIdPadre(Long idPadre);
	List<NodoArbol> findByIdArbol(Long idArbol);
	
	@Query("SELECT new co.gov.metropol.area247.centrocontrol.model.dto.NodoArbolDto(nod.id, nod.nombre, "
			+ "nod.descripcion, nod.alias, nod.instrucciones, nod.instruccionesDetalladas, nod.orden, "
			+ "nod.idMultimedia, nod.idPadre, nod.flagHijosDropdown, nod.urlMediaVideoAudio, "
			+ "nod.formatoMedia, nod.idAutoridadCompetente) "
			+ "FROM NodoArbol AS nod where nod.idArbol = ?1 and nod.idPadre is null ")
	List<NodoArbolDto> nodosRaizDtoPorIdArbol(Long idArbol);
	
	@Query("SELECT new co.gov.metropol.area247.centrocontrol.model.dto.NodoArbolDto(nod.id, nod.nombre, "
			+ "nod.descripcion, nod.alias, nod.instrucciones, nod.instruccionesDetalladas, nod.orden, "
			+ "nod.idMultimedia, nod.idPadre, nod.flagHijosDropdown, nod.urlMediaVideoAudio, "
			+ "nod.formatoMedia, nod.idAutoridadCompetente) "
			+ "FROM NodoArbol AS nod where nod.idPadre = ?1 ")
	List<NodoArbolDto> nodosArbolDtoPorIdPadre(Long idPadre);
	
	@Query("SELECT new co.gov.metropol.area247.centrocontrol.model.dto.NodoArbolDto(nod.id, nod.nombre, "
			+ "nod.descripcion, nod.alias, nod.instrucciones, nod.instruccionesDetalladas, nod.orden, "
			+ "nod.idMultimedia, nod.idPadre, nod.flagHijosDropdown, nod.urlMediaVideoAudio, "
			+ "nod.formatoMedia, nod.idAutoridadCompetente) "
			+ "FROM NodoArbol AS nod where nod.id = ?1 ")
	NodoArbolDto nodoArbolDtoPorId(Long id);
	
	
	@Query("SELECT COUNT(*) FROM NodoArbol AS nod where nod.idPadre = ?1 ")
	Integer cantidadNodosHijos(Long idPadre);
		
}
