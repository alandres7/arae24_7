package co.gov.metropol.area247.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import co.gov.metropol.area247.repository.domain.RutaTipoSistemaRuta;

public interface RutaTipoSistemaRutaRepository  extends CrudRepository<RutaTipoSistemaRuta, Long>{
	
	/**
	 * Retorna un registro de la tabla T247VIA_RUTA_TIPO_SISTEMA_RUTA 
	 * @param idRuta - idRuta
	 * @param idTipoSistemaRuta - idTipoSistemaRuta
	 * @return RutaTipoSistemaRuta
	 */
	@Query(name = "findByidRuta_idTipoSis", value = "Select e From RutaTipoSistemaRuta e Where e.idRuta = (:idRuta) AND e.idTipoSistemaRuta = (:idTipoSistemaRuta)")
	RutaTipoSistemaRuta findByidRuta_idTipoSis(@Param("idRuta") final Long idRuta,@Param("idTipoSistemaRuta") final Long idTipoSistemaRuta);
}
