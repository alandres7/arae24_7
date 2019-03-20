package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.EmpresaRutaDTO;
import co.gov.metropol.area247.model.EmpresaTransporteDTO;

public interface IEmpresaTransporteService {

	/**
	 * Obtiene las empresas de trasnporte que estan asociadas a una ruta
	 * <P>Creado 3/08/2018 3:30 p.m
	 * @param idRuta - filtro de busqueda
	 * @return lista de objetos tipo {@link EmpresaTransporteDTO}
	 */
	List<EmpresaTransporteDTO> findByIdRuta(final Long idRuta);
	
	/**
	 * Obtiene la empresa que coincida totalmete con el nombre
	 * <P>Creado 13/08/2018 3:59 p.m
	 * @param nombre - filtro de busqueda
	 * @return la empresa de trasnporte
	 */
	EmpresaTransporteDTO findByNombre(String nombre);
	
	/**
	 * Obtiene todas las empresas que esten activas
	 * <P>Creado 3/08/2018 4:53 p.m
	 * @return una lista de objetos tipo {@link EmpresaTransporteDTO}
	 */
	List<EmpresaTransporteDTO> findAllActivas();
	
	/**
	 * Guarda o actualiza toda la informacion de las empresas de transporte y su 
	 * relacion con la ruta, este compara la informacion ya existente y la reemplaza 
	 * con la informacion que se encuentra en los argumentos de entrada, puede haber 
	 * el caso de que se elimine informacion existente.
	 * <P>Creado 13/08/2018 12:00 p.m
	 * @param empresasTransporteDTO - lista que contiene la informacion a procesar
	 * @param idRuta - id de la ruta que esta asociada con la empresa
	 */
	void procesarEmpresasTransporte(List<EmpresaTransporteDTO> empresasTransporteDTO, Long idRuta);
	
	/**
	 * Persiste la informacion en base de datos
	 * @param empresaTransporteDTO - objeto a persistir
	 */
	void save(EmpresaTransporteDTO empresaTransporteDTO);
	
	/**
	 * Perisiste el objeto {@link EmpresaRutaDTO} a bd
	 * @param empresaRutaDTO - objeto a persistir
	 */
	void save(EmpresaRutaDTO empresaRutaDTO);
	
	/**
	 * Perisiste una lista de objetos {@link EmpresaRutaDTO} a bd
	 * @param empresasRutaDTO - objeto a persistir
	 */
	void saveAll(List<EmpresaRutaDTO> empresasRutaDTO);
	
	/**
	 * Borra registro de base de datos que coincida con el identificador unico
	 * @param id - filtro
	 */
	void delete(Long id);
	
	/**
	 * Borra registros a partir de una lista de objetos
	 * @param empresasRutaDTO - objetos a eliminar
	 */
	void deleteAll(List<EmpresaRutaDTO> empresasRutaDTO);
}
