package co.gov.metropol.area247.huellas.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.huellas.entity.Encuestado;

@Repository("encuestadoDao")
public interface IHuellasEncuestadoDao extends JpaRepository<Encuestado, Serializable>{	
	
}
