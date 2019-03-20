package co.gov.metropol.area247.vigias.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.gov.metropol.area247.vigias.model.Vigia;

public interface IVigiasVigiaRepository extends CrudRepository<Vigia, Long> {
	
	List<Vigia> findByIdUsuario(Long idUsuario);
	List<Vigia> findByEstado(String estado);
	
}
