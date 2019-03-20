
package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.EmpresaTransporteDTO;
import co.gov.metropol.area247.model.FrecuenciaRutaDTO;
import co.gov.metropol.area247.model.HorarioRutaDTO;
import co.gov.metropol.area247.model.OperadorDTO;
import co.gov.metropol.area247.model.OperadorEmpresaDTO;
import co.gov.metropol.area247.model.ParaderoRutaDTO;
import co.gov.metropol.area247.model.RutaGtpcDTO;
import co.gov.metropol.area247.model.RutaTipoSistemaRutaDTO;
import co.gov.metropol.area247.model.TipoModoTransporteDTO;
import co.gov.metropol.area247.model.TipoOrientacionDTO;
import co.gov.metropol.area247.model.TipoParaderoDTO;
import co.gov.metropol.area247.model.TipoRutaDTO;
import co.gov.metropol.area247.model.TipoSistemaRutaDTO;

public interface IGtpcService {

	/**
	 * Cargar la informacion de las rutas de transporte colectivo metropolitano
	 */
	void cargarInformacionDeRutas();
	
	/**
	 * Retorn un registro de la tabla TIPO_RUTA por su id
	 * @param id - id
	 * @return TipoRutaDTO
	 */
	TipoRutaDTO findTipoRutaById(Long id);
	
	/**
	 * Retorn un registro de la tabla TIPO_RUTA
	 * @param idItem - idItem
	 * @param fuenteDatos - Fuente de datos
	 * @return TipoRutaDTO
	 */
	TipoRutaDTO findTipoRutaByIdItem_idFuenteDatos(Long idItem, int fuenteDatos);
	
	/**
	 * Almacena un registro en la tabla D246VIA_TIPO_RUTA
	 * @param tipoRutaDTO - tipoRutaDTO 
	 */
	void updateTipoRuta(TipoRutaDTO tipoRutaDTO);
	
	/**
	 * Actualiza un registro en la tabla D246VIA_TIPO_RUTA
	 * @param tipoRutaDTO - tipoRutaDTO
	 */
	void saveTipoRUta(TipoRutaDTO tipoRutaDTO);
	
	/**
	 * Retorna un registro de la tabla TIPO_MODO_TRANSPORTE por su id
	 * @param id - id
	 * @return TipoModoTransporteDTO
	 */
	TipoModoTransporteDTO findTipoModoTransById(Long id);
	
	/**
	 * Retorna un registro de la tabla TIPO_MODO_TRANSPORTE
	 * @param idItem - idItem
	 * @param fuenteDatos - fuenteDatos
	 * @return TipoModoTransporteDTO
	 */
	TipoModoTransporteDTO findTipoModoTransByIdItem_idFuenteDatos(Long idItem, int fuenteDatos);
	
	/**
	 * Almacena un registro en la tabla D247VIA_TIPO_MODO_TRANSPORTE
	 * @param tipoModoTransporteDTO - tipoModoTransporte
	 */
	void updateTipoModoTransporte(TipoModoTransporteDTO tipoModoTransporteDTO);
	
	/**
	 * Actualiza un registro en la tabla D247VIA_TIPO_MODO_TRANSPORTE
	 * @param tipoModoTransporteDTO - tipoModoTransporte
	 */
	void saveTipoModoTransporte(TipoModoTransporteDTO tipoModoTransporteDTO);
	
	/**
	 * Retorna un registro de la tabla D247VIA_TIPO_SISTEMA_RUTA por id
	 * @param id - id
	 * @return TipoSistemaRutaDTO - TipoSistemaRutaDTO
	 */
	TipoSistemaRutaDTO findTipoSistRutaById(Long id);
	
	/**
	 * Retorna un registro de la tabla D247VIA_TIPO_SISTEMA_RUTA
	 * @param idItem - idItem
	 * @param fuenteDatos - fuenteDatos
	 * @return TipoSistemaRutaDTO - TipoSistemaRutaDTO
	 */
	TipoSistemaRutaDTO findTipoSistRutaByIdItem_idFuenteDatos(Long idItem, int fuenteDatos);
	
	/**
	 * Almacena un registro en la tabla D247VIA_TIPO_SISTEMA_RUTA
	 * @param tipoSistemaRutaDTO - tipoSistemaRutaDTO
	 */
	void updateTipoSistemaRuta(TipoSistemaRutaDTO tipoSistemaRutaDTO);
	
	/**
	 * Actualiza un registro en la tabla D247VIA_TIPO_SISTEMA_RUTA
	 * @param tipoSistemaRutaDTO - tipoSistemaRutaDTO
	 */
	void saveTipoSistemaRuta(TipoSistemaRutaDTO tipoSistemaRutaDTO);
	
	/**
	 * Retorna un registro de la tabla T247VIA_RUTA
	 * @param idItem - idItem
	 * @param fuenteDatos - fuenteDatos
	 * @return TipoSistemaRutaDTO - TipoSistemaRutaDTO
	 */
	RutaGtpcDTO findRutaGtpcByIdItem_idFuenteDatos(Long idItem, int fuenteDatos);
	
	/**
	 * Almacena un registro en la tabla T247VIA_RUTA
	 * @param rutaGtpcDTO - rutaGtpcDTO
	 */
	void updateRutaGtpc(RutaGtpcDTO rutaGtpcDTO);
	
	/**
	 * Actualiza un registro en la tabla T247VIA_RUTA
	 * @param rutaGtpcDTO - tipoSistemaRrutaGtpcDTOutaDTO
	 */
	void saveRutaGtpc(RutaGtpcDTO rutaGtpcDTO);
	
	/**
	 * Retorna un registro de la tabla T247VIA_TIPO_PARADERO
	 * @param idItem - idItem
	 * @param fuenteDatos - fuenteDatos
	 * @return TipoParaderoDTO - TipoParaderoDTO
	 */
	TipoParaderoDTO findTipoParaderoByIdItem_idFuenteDatos(Long idItem, int fuenteDatos);
	
	/**
	 * Almacena un registro en la tabla T247VIA_TIPO_PARADERO
	 * @param tipoParaderoDTO - tipoParaderoDTO
	 */
	void updateTipoParaderGtpc(TipoParaderoDTO tipoParaderoDTO);
	
	/**
	 * Actualiza un registro en la tabla T247VIA_TIPO_PARADERO
	 * @param tipoParaderoDTO - tipoParaderoDTO
	 */
	void saveTipoParadertoGtpc(TipoParaderoDTO tipoParaderoDTO);
	
	/**
	 * Retorna un registro de la tabla D247VIA_TIPO_ORIENTACION
	 * @param idItem - idItem
	 * @param fuenteDatos - fuenteDatos
	 * @return TipoOrientacionDTO - TipoOrientacionDTO
	 */
	TipoOrientacionDTO findTipoOrientacionByIdItem_idFuenteDatos(Long idItem, int fuenteDatos);
	
	/**
	 * Almacena un registro en la tabla D247VIA_TIPO_ORIENTACION
	 * @param tipoOrientacionDTO - tipoOrientacionDTO
	 */
	void updateTipoOrientacionGtpc(TipoOrientacionDTO tipoOrientacionDTO);
	
	/**
	 * Actualiza un registro en la tabla D247VIA_TIPO_ORIENTACION
	 * @param tipoOrientacionDTO - tipoOrientacionDTO
	 */
	void saveTipoOrientacionGtpc(TipoOrientacionDTO tipoOrientacionDTO);
	
	/**
	 * Retorna un registro de la tabla T247VIA_PARADERO_RUTA
	 * @param idItem - idItem
	 * @param fuenteDatos - fuenteDatos
	 * @return ParaderoRutaDTO - ParaderoRutaDTO
	 */
	ParaderoRutaDTO findParaderoRutaByIdItem_idFuenteDatos(Long idItem, int fuenteDatos);
	
	/**
	 * Almacena un registro en la tabla T247VIA_PARADERO_RUTA
	 * @param paraderoRutaDTO - paraderoRutaDTO
	 */
	void updateParaderoRutaGtpc(ParaderoRutaDTO paraderoRutaDTO);
	
	/**
	 * Actualiza un registro en la tabla T247VIA_PARADERO_RUTA
	 * @param paraderoRutaDTO - paraderoRutaDTO
	 */
	void saveParaderoRutaGtpc(ParaderoRutaDTO paraderoRutaDTO);
	
	/**
	 * Retorna los paraderos mas cecanos un punto determinado y un radio
	 * @param latitud - latitud
	 * @param longitud - longitud
	 * @param radio -  radio
	 * @return ParaderoRutaDTO
	 */
	List<ParaderoRutaDTO> obtenerParaderosCercanos(Double latitud, Double longitud, Float radio);
	
	/**
	 * Retorna las rutas de gtpc mas cecanos un punto determinado y un radio
	 * @param latitud - latitud
	 * @param longitud - longitud
	 * @param radio -  radio
	 * @return lista de tipo RutaGtpcDTO
	 */
	List<RutaGtpcDTO> obtenerRutasCercanasGtpc(Double latitud, Double longitud, Float radio);
	
	/**
	 * Retorna un registro de la tabla D247VIA_TIPO_PARADERO 
	 * por su id
	 * @param id - id
	 * @return TipoParaderoDTO
	 */
	TipoParaderoDTO findTipoParaderoById(Long id);
	
	/**
	 * Retorna un registro de la tabla D247VIA_TIPO_ORIENTACION 
	 * por su id
	 * @param id - id
	 * @return TipoOrientacionDTO
	 */
	TipoOrientacionDTO findTipoOrientacionParaderoById(Long id);
	
	/**
	 * Retorna un registro de la tabla T247VIA_FRECUENCIA_RUTA
	 * por id
	 * @param id- - id
	 * @return lista de tipo FrecuenciaRutaDTO - lista de tipo  FrecuenciaRutaDTO
	 */
	List<FrecuenciaRutaDTO> findFrecuenciaRutaByIdRuta(Long id);
	
	/**
	 * Retorna un registro de la tabla T247VIA_FRECUENCIA_RUTA
	 * @param idItem - idItem
	 * @param fuenteDatos - fuenteDatos
	 * @return FrecuenciaRutaDTO - FrecuenciaRutaDTO
	 */
	FrecuenciaRutaDTO findFrecuenciaRutaByIdItem_idFuenteDatos(Long idItem, int fuenteDatos);
	
	/**
	 * Almacena un registro en la tabla T247VIA_FRECUENCIA_RUTA
	 * @param frecuenciaRutaDTO - frecuenciaRutaDTO
	 */
	void updateParaderoRutaGtpc(FrecuenciaRutaDTO frecuenciaRutaDTO);
	
	/**
	 * Actualiza un registro en la tabla T247VIA_FRECUENCIA_RUTA
	 * @param frecuenciaRutaDTO - frecuenciaRutaDTO
	 */
	void saveParaderoRutaGtpc(FrecuenciaRutaDTO frecuenciaRutaDTO);
	
	/**
	 * Retorna una lista de la tabla T247VIA_HORARIO_RUTA por idRuta
	 * @param id - id
	 * @return lista de tipo HorarioRutaDTO - lista de tipo HorarioRutaDTO
	 */
	List<HorarioRutaDTO> findHorarioRutaByIdRuta(Long id);
	
	/**
	 * Retorna un registro de la tabla T247VIA_HORARIO_RUTA
	 * @param idItem - idItem
	 * @param fuenteDatos - fuenteDatos
	 * @return HorarioRutaDTO - HorarioRutaDTO
	 */
	HorarioRutaDTO findHorarioRutaByIdItem_idFuenteDatos(Long idItem, int fuenteDatos);
	
	/**
	 * Almacena un registro en la tabla T247VIA_HORARIO_RUTA
	 * @param horarioRutaDTO - horarioRutaDTO
	 */
	void updateHorarioRutaGtpc(HorarioRutaDTO horarioRutaDTO);
	
	/**
	 * Actualiza un registro en la tabla T247VIA_HORARIO_RUTA
	 * @param horarioRutaDTO - horarioRutaDTO
	 */
	void saveHorarioRutaGtpc(HorarioRutaDTO horarioRutaDTO);
	
	/**
	 * Retorna un registro de la tabla T247VIA_OPERADOR
	 * @param idItem - idItem
	 * @return OperadorDTO 
	 */
	OperadorDTO findOperadorByIdItem_idFuenteDatos(Long idItem);
	
	/**
	 * Almacena un registro en la tabla T247VIA_OPERADOR
	 * @param operadorDTO - operadorDTO
	 */
	void updateOperador(OperadorDTO operadorDTO);
	
	/**
	 * Actualiza un registro en la tabla T247VIA_OPERADOR
	 * @param operadorDTO - operadorDTO
	 */
	void saveOperador(OperadorDTO operadorDTO);
	
	/**
	 * Retorna un registro de la tabla T247VIA_EMPRESA_TRANSPORTE
	 * @param idItem - idItem
	 * @return EmpresaTransporteDTO 
	 */
	EmpresaTransporteDTO findEmpresaTransporteByIdItem_idFuenteDatos(Long idItem);
	
	/**
	 * Retorna un registro de la tabla T247VIA_EMPRESA_TRANSPORTE
	 * por su id de ruta
	 * @param idRuta - idRuta
	 * @return lista de tipo EmpresaTransporteDTO 
	 */
	List<EmpresaTransporteDTO> findEmpresaTransporteByIdRuta(Long idRuta);
	
	/**
	 * Almacena un registro en la tabla T247VIA_EMPRESA_TRANSPORTE
	 * @param empresaTransporteDTO - empresaTransporteDTO
	 */
	void updateEmpresaTransporte(EmpresaTransporteDTO empresaTransporteDTO);
	
	/**
	 * Actualiza un registro en la tabla T247VIA_EMPRESA_TRANSPORTE
	 * @param empresaTransporteDTO - empresaTransporteDTO
	 */
	void saveEmpresaTransporte(EmpresaTransporteDTO empresaTransporteDTO);
	
	/**
	 * Retorna un registro de la tabla T247VIA_OPERADOR_EMPRESA
	 * @param idItem - idItem
	 * @return OperadorEmpresaDTO 
	 */
	OperadorEmpresaDTO findOperadorEmpresaByIdItem_idFuenteDatos(Long idItem);
	
	/**
	 * Almacena un registro en la tabla T247VIA_OPERADOR_EMPRESA
	 * @param operadorEmpresaDTO - operadorEmpresaDTO
	 */
	void updateOperadorEmpresa(OperadorEmpresaDTO operadorEmpresaDTO);
	
	/**
	 * Actualiza un registro en la tabla T247VIA_OPERADOR_EMPRESA
	 * @param operadorEmpresaDTO - operadorEmpresaDTO
	 */
	void saveOperadorEmpresa(OperadorEmpresaDTO operadorEmpresaDTO);

	/**
	 * 
	 * @param idRuta nombre o codigo
	 * @return lista de ParaderoRutaDTO
	 */
	List<ParaderoRutaDTO> obtenerParaderos(Long idRuta);

	/**
	 * 
	 * @param parametro nombre o descripcion de la ruta
	 * @return lista de rutas
	 */
	List<RutaGtpcDTO> findByCodigoOrDescripcion(String parametro);
	
	/**
	 * Retorna un registro de la tabla T247VIA_RUTA_TIPO_SISTEMA_RUTA
	 * @param idRuta - idRuta
	 * @param idTipoSistemaRuta - idTipoSistemaRuta
	 * @return RutaTipoSistemaRutaDTO - RutaTipoSistemaRutaDTO
	 */
	RutaTipoSistemaRutaDTO findRutaTipoSistRutaByIdRuta_idTipoSis(Long idRuta, Long idTipoSistemaRuta);
	
	/**
	 * Almacena un registro en la tabla T247VIA_RUTA_TIPO_SISTEMA_RUTA
	 * @param rutaTipoSistemaRutaDTO - rutaTipoSistemaRutaDTO
	 */
	void updateRutaTipoSistemaRuta(RutaTipoSistemaRutaDTO rutaTipoSistemaRutaDTO);
	
	/**
	 * Almacena un registro en la tabla T247VIA_RUTA_TIPO_SISTEMA_RUTA
	 * @param rutaTipoSistemaRutaDTO - rutaTipoSistemaRutaDTO
	 */
	void saveRutaTipoSistemaRuta(RutaTipoSistemaRutaDTO rutaTipoSistemaRutaDTO);
}
