package co.gov.metropol.area247.service;

import co.gov.metropol.area247.model.TipoParaderoDTO;

/**
 * Contendra la logica de negocio que procesa la informacion del tipo de
 * paradero recibida por el servicio METRO
 * 
 * @author Andres Felipe Negret
 *
 */
public interface ITipoParaderoService {

	/**
	 * Busca el tipo de paradero que concida con el codigo y la fuente de datos
	 * 
	 * @param nombre
	 *            - filtro de busqueda
	 * @param fuenteDatos
	 *            - filtro de busqueda, posibles valores (1, ENCICLA), (2,
	 *            GTPC), (3, METRO)
	 * @return un objeto de tipo {@link TipoParaderoDTO}
	 */
	TipoParaderoDTO findByNombreAndFuenteDatos(String nombre, Integer fuenteDatos);

	/**
	 * Persiste la informacion del tipo paradero dada el argumento definido
	 * 
	 * @param tipoParaderoDTO
	 *            - objeto a persistir
	 */
	void saveTipoParadero(TipoParaderoDTO tipoParaderoDTO);

	/**
	 * Persiste las modificaciones del objeto definido en el argumento, si este
	 * objeto no tiene definido un identificador, el objeto sera persistido como
	 * un nuevo registro.
	 * 
	 * @param tipoParaderoDTO
	 *            - objeto a modificar
	 */
	void updateTipoParadero(TipoParaderoDTO tipoParaderoDTO);

	/**
	 * Valida que la información del objeto sea correcta, luego persiste o
	 * actualiza segun su estado en la base de datos.
	 * 
	 * @param tipoParaderoDTO - objeto a persistir o modificar.
	 */
	void procesarTipoParadero(TipoParaderoDTO tipoParaderoDTO);
	
	/**
	 * Realiza la busqueda de tipo paradero por el identificador unico
	 * 
	 * @param id
	 *            - filtro de busqueda
	 * @return {@link TipoParaderoDTO}
	 */
	TipoParaderoDTO findById(Long id);


}
