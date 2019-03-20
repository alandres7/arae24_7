package co.gov.metropol.area247.contenedora.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.model.VentanaInformacion;

@Repository
public interface IContenedoraVentanaInformacionRepository extends CrudRepository<VentanaInformacion, Long> {

	VentanaInformacion findByNombre(String nombre);
	
	@Query(name="getCantidadReferenciasMarcador", 
		   value="SELECT COUNT(*) FROM D247CON_MARCADOR WHERE ID_VENTANA_INFO = :idVentana", nativeQuery=true)
	int getCantidadReferenciasMarcador(@Param("idVentana")Long idVentanaInfo);
	
}
