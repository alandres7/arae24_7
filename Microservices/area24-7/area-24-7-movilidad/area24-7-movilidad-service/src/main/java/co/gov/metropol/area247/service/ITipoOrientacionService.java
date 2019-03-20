package co.gov.metropol.area247.service;

import co.gov.metropol.area247.model.TipoOrientacionDTO;

/**
 * Contendra la logica de negocio que procesa la informacion del tipo de
 * orientacion recibida por el servicio METRO
 * 
 * @author Andres Felipe Negret
 *
 */
public interface ITipoOrientacionService {

	/**
	 * Busca el tipo de orientacion que concida con el codigo y la fuente de datos
	 * 
	 * @param nombre
	 *            - filtro de busqueda
	 * @param fuenteDatos
	 *            - filtro de busqueda, posibles valores (1, ENCICLA), (2,
	 *            GTPC), (3, METRO)
	 * @return un objeto de tipo {@link TipoOrientacionDTO}
	 */
	TipoOrientacionDTO findByNombreAndFuenteDatos(String nombre, Integer fuenteDatos);

	/**
	 * Persiste la informacion del tipo orientacion dada el argumento definido
	 * 
	 * @param tipoOrientacionDTO
	 *            - objeto a persistir
	 */
	void saveTipoOrientacion(TipoOrientacionDTO tipoOrientacionDTO);

	/**
	 * Persiste las modificaciones del objeto definido en el argumento, si este
	 * objeto no tiene definido un identificador, el objeto sera persistido como
	 * un nuevo registro.
	 * 
	 * @param tipoOrientacionDTO
	 *            - objeto a modificar
	 */
	void updateTipoOrientacion(TipoOrientacionDTO tipoOrientacionDTO);

	/**
	 * Valida que la información del objeto sea correcta, luego persiste o
	 * actualiza segun su estado en la base de datos.
	 * 
	 * @param tipoOrientacionDTO - objeto a persistir o modificar.
	 */
	void procesarTipoOrientacion(TipoOrientacionDTO tipoOrientacionDTO);
	
	/**
	 * Realiza la busqueda del tipo de orientacion segun el identificador unico
	 * 
	 * @param id
	 *            - filtro de busqudda
	 * @return {@link TipoOrientacionDTO}
	 */
	TipoOrientacionDTO findById(Long id);


}
