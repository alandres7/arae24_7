package co.gov.metropol.area247.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.HorarioLinea;

@Repository
public interface HorarioLineaMetroRepository extends CrudRepository<HorarioLinea, Long> {

	HorarioLinea findByIdHorario(Long idHorario);

	List<HorarioLinea> findByIdLinea(Long idLinea);

}
