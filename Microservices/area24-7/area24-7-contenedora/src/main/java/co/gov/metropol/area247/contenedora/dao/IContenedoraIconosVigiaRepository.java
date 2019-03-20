package co.gov.metropol.area247.contenedora.dao;


import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.model.IconosVigia;


@Transactional
@Repository
public interface IContenedoraIconosVigiaRepository extends CrudRepository<IconosVigia, Long> {
		
	IconosVigia findByIdNodoArbol(Long idNodoArbol);

}
