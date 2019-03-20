package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.ConDireccionFavoritaDTO;

public interface IConDireccionFavoritaService {

	/**
	 * Metodo que obtiene la direccion favorita de un usuario
	 * 
	 * @param nombre
	 *            - de la direccion
	 * @param idUsuario
	 *            - usuario q solicita
	 * @return ConDireccionFavoritaDTO
	 */
	ConDireccionFavoritaDTO findByNombreAndIdUsuario(String nombre, Long idUsuario);

	/**
	 * Metodo que almacena la Ubicaion Favorita
	 * 
	 * @param nombre
	 *            - nombre de la ubicaion
	 * @param descripcion
	 *            - breve descripcion
	 * @param longitud
	 *            - coordenada
	 * @param latitud
	 *            - corrdenada
	 * @param idUsuario
	 *            - usuario
	 * @param idCategoria
	 * 			  - categoria
	 * @return ConDireccionFavoritaDTO
	 */
	ConDireccionFavoritaDTO saveUbicacionFavorita(String nombre, String descripcion, double longitud, double latitud,
			Long idUsuario, Long idCategoria);

	/**
	 * verifica si existe o no una ubicacion por su id
	 * 
	 * @param id
	 *            - de la ubicacion
	 * @return boolean
	 */
	boolean isCondireccionFavoritaExist(Long id);

	/**
	 * 
	 * @param id
	 *            - identificador
	 * @return ConDireccionFavoritaDTO
	 */
	ConDireccionFavoritaDTO findById(Long id);

	/**
	 * Actualiza la Ubicacion Favorita
	 * 
	 * @param direccion
	 *            - objeto de tipo ConDireccionFavoritaDTO
	 */
	void updateDireccionFavorita(ConDireccionFavoritaDTO direccion);

	/**
	 * Elimina una direccion favorita
	 * 
	 * @param id
	 *            - identificador
	 */
	void deleteDireccionFavoritaById(Long id);

	/**
	 * metodo q obtiene todas las direcciones asociadas a un usuario
	 * 
	 * @param idUsuario
	 *            - id del usuario
	 * @return lista de ConDireccionFavoritaDTO
	 */
	List<ConDireccionFavoritaDTO> findByIdUsuario(Long idUsuario);
	
	/**
	 * metodo que obtiene la cantidad de direcciones favoritas de un usuario en
	 * especifico
	 * 
	 * @param idUsuario
	 *            - identificador unico del usuario por el cual se filtra en la
	 *            consulta
	 * 
	 * @return el numero de direcciones favoritas asociadas al usuario o 0 en
	 *         caso de que no encuentre nada asociado
	 */
	Long countByIdUsuario(Long idUsuario);

}
