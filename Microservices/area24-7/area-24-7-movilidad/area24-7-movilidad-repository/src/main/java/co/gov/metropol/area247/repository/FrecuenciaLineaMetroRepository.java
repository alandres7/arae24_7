package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.FrecuenciaLinea;

@Repository
public interface FrecuenciaLineaMetroRepository extends CrudRepository<FrecuenciaLinea, Long> {

	FrecuenciaLinea findByIdFrecuencia(Long idFrecuencia);

	List<FrecuenciaLinea> findByIdLinea(Long idLinea);

}
