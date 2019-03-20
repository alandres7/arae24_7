package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.gov.metropol.area247.repository.domain.EmpresaRuta;



public interface EmpresaRutaRepository extends CrudRepository<EmpresaRuta, Long>{

	/**
	 * retorna todos los registros de la empres ruta por el idRuta
	 * @param idRuta - idRuta
	 * @return lista de tipo empresaRuta
	 */
	@Query(name = "findEmpresaRutaByIdRuta", value = "select e from EmpresaRuta e where e.idRuta = :idRuta")
	List<EmpresaRuta> findEmpresaRutaByIdRuta(@Param("idRuta") final Long idRuta);
	
	List<EmpresaRuta> findByIdRutaAndIdEmpresaTransporte(Long idRuta, Long idEmpresaTransporte);
	
}
