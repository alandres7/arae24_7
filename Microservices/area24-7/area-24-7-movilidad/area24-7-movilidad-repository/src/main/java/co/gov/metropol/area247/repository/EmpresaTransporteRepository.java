package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.gov.metropol.area247.repository.domain.EmpresaTransporte;


public interface EmpresaTransporteRepository extends CrudRepository<EmpresaTransporte, Long>{

	/**
	 * Retorna un registro de la tabla T247VIA_EMPRESA_TRANSPORTE
	 * que corresponda con su idItem
	 * @param idItem - idItem
	 * @return EmpresaTransporte
	 */
	EmpresaTransporte findByIdItem(Long idItem);
	
	/**
	 * obtiene las empresas de transporte que coinciden con los identificadores unicos
	 * @param idEmpresa - filtros de busqueda
	 * @return lista de EmpresaTransporte
	 */
	@Query(name = "findByIds", value = "select e from EmpresaTransporte e where e.id in (:idEmpresa)")
	List<EmpresaTransporte> findByIds(@Param("idEmpresa") final List<Long> idEmpresa); 

	/**
	 * Obtiene las empresas de trasnporte que estan asociadas a una ruta
	 * <P>Creado 3/08/2018 12:20 p.m
	 * @param idRuta - filtro de busqueda
	 * @return lista de objetos tipo {@link EmpresaTransporte}
	 */
	@Query(name = "findByIdRuta", value = "SELECT ET.* FROM MOVILIDAD.T247VIA_EMPRESA_TRANSPORTE ET JOIN MOVILIDAD.T247VIA_EMPRESA_RUTA ER ON ER.ID_EMPRESA_TRANSPORTE = ET.ID WHERE ER.ID_RUTA = :idRuta", nativeQuery = true)
	List<EmpresaTransporte> findByIdRuta(@Param("idRuta") final Long idRuta);
	
	/**
	 * Obtiene la empresa que coincida con el nombre de la empresa
	 * <P>Creado 13/08/2018 4:00 p.m
	 * @param nombre - filtro de busqueda
	 * @return objeto tipo {@link EmpresaTransporte}
	 */
	EmpresaTransporte findByNombre(String nombre);
	
	/**
	 * Obtiene todas las empresas de transporte activas
	 * <P>Creado 3/08/2018 4:53 p.m
	 * @return lista de objetos tipo {@link EmpresaTransporte}
	 */
	@Query(name = "findAllActivas", value = "SELECT e FROM EmpresaTransporte e WHERE e.activo = 'S'")
	List<EmpresaTransporte> findAllActivas();
}
