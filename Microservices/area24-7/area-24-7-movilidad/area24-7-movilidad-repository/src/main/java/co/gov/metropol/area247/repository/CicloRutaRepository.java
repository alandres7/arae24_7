package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.gov.metropol.area247.repository.domain.Cicloruta;

public interface CicloRutaRepository extends CrudRepository<Cicloruta, Long>{
	/**
	 * retona la cicloRuta mas cercana dada una  ubicacion
	 * @param latitud - latitud
	 * @param longitud - longitud
	 * @param radio - radio
	 * @return lista de tipo Cicloruta
	 */
	@Query(name = "cicloRutaCercana", value = "\r\n" + 
			"select e from Cicloruta e\r\n" + 
			"where  SDO_GEOM.SDO_DISTANCE(MDSYS.SDO_GEOMETRY(2001, 8307, MDSYS.SDO_POINT_TYPE((:latitude), (:longitude), NULL), NULL, NULL),\r\n" + 
			"                                 e.coordenadas, \r\n" + 
			"                                 0.0005 ,'unit=KM') < (:radius)")
	List<Cicloruta> cicloRutaCercana(@Param("latitude") final double latitud,@Param("longitude") final double longitud,@Param("radius") final double radio);
	
	/**
	 * Retorna un resgistro de la tabla CicloRuta por el idItem
	 * Creado el 22/11/2017 a las  8:45:02 p. m. <br>
	 * @param idItem -idItem
	 * @return Cicloruta 
	 */
	@Query(name = "findByIdItem", value = "\r\n" + 
			"select e from Cicloruta e\r\n" + 
			"where  e.idItem= (:iditem)")
	Cicloruta findByIdItem(@Param("iditem") final  Long idItem);
	
}
