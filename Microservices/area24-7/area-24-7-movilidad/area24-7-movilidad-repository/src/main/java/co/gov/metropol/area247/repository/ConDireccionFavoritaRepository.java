package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.gov.metropol.area247.repository.domain.ConDireccionFavorita;

public interface ConDireccionFavoritaRepository extends CrudRepository<ConDireccionFavorita, Long> {

	/**
	 * obtener unadireccion
	 * 
	 * @param nombre
	 *            - nom
	 * @param idUsuario
	 *            - usuario
	 * @return ConDireccionFavorita
	 */
	@Query(name = "findByNombreAndUsuario", value = "Select c From ConDireccionFavorita c Where c.nombre like %:nombre% and c.idUsuario = :idUsuario")
	ConDireccionFavorita findByNombreAndUsuario(@Param("nombre") final String nombre,
			@Param("idUsuario") final Long idUsuario);

	/**
	 * Actualizar una Direccion Favorita
	 * @param nombre - nombre de la direccion
	 * @param descripcion - des
	 * @param idCoordenada - idcordenada
	 * @param idUsuario - usuario
	 * @param id - identificador
	 */
	@Query(name = "updateConDireccionFavorita", value = "UPDATE ConDireccionFavorita SET nombre = nombre, descripcion = descripcion, idCoordenada = idCoordenada, idUsuario = idUsuario Where id = id")
	void updateConDireccionFavorita(@Param("nombre") final String nombre,
			@Param("descripcion") final String descripcion, @Param("idCoordenada") final Long idCoordenada,
			@Param("idUsuario") final Long idUsuario, @Param("id") final Long id);
	
	/**
	 * 
	 * @param idUsuario - id del usuario
	 * @return lista de ConDireccionFavorita
	 */
	List<ConDireccionFavorita> findByIdUsuario(Long idUsuario);
	
	/**
	 * Obtiene la cantidad de direcciones asignadas a un usuario
	 * 
	 * @param idUsuario
	 *            - filtro de busqueda
	 * @return la cantidad de direcciones asociadas a un usuario
	 */
	Long countByIdUsuario(Long idUsuario);

}
