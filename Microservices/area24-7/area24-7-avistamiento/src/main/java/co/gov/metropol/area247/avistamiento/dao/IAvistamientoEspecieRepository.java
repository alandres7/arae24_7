package co.gov.metropol.area247.avistamiento.dao;


import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.avistamiento.model.Especie;

@Transactional
@Repository
public interface IAvistamientoEspecieRepository extends CrudRepository<Especie, Long> {

	List<Especie> findByIdCategoria(Long idCategoria);

}
