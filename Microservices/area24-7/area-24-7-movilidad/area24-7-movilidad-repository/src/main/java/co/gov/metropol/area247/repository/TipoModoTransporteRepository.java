package co.gov.metropol.area247.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.gov.metropol.area247.repository.domain.TipoModoTransporte;


public interface TipoModoTransporteRepository extends CrudRepository<TipoModoTransporte, Long> {
	
	/**
	 * Retorna un registro de la tabla D247VIA_TIPO_MODO_TRANSPORTE
	 * donde corresponda el id
	 * @param id - id de la tabla
	 * @return TipoModoTransporte
	 */
	@Query(name = "findById", value = "Select e From TipoModoTransporte e Where e.id = (:id)")
	TipoModoTransporte findById(@Param("id") final Long id);
	
	/**
	 * Retorna un registro de la tabla D247VIA_TIPO_MODO_TRANSPORTE
	 * donde corresponda el id_item y su fuente de datos
	 * @param idItem - idItem
	 * @param fuenteDatos - fuenteDatos
	 * @return TipoRuta
	 */
	@Query(name = "findByIdItem_idFuenteDatos", value = "Select e From TipoModoTransporte e Where e.idItem = (:idItem) AND e.fuenteDatos = (:fuenteDatos)")
	TipoModoTransporte findByIdItem_idFuenteDatos(@Param("idItem") final Long idItem,@Param("fuenteDatos") final int fuenteDatos);
	
	/**
	 * Retorna un objeto de tipo TipoModoTransporte que coincida con los parametros de busqueda
	 * @param nombre - parametro de busqueda
	 * @param fuenteDatos - parametro de busqueda
	 * @return - {@link TipoModoTransporte}
	 */
	TipoModoTransporte findByNombreAndFuenteDatos(String nombre, Integer fuenteDatos);
}