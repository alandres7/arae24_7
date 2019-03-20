package co.gov.metropol.area247.huellas.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.huellas.entity.Consumo;

@Repository("consumoDao")
public interface IHuellasConsumoDao extends JpaRepository<Consumo, Serializable>{
	
}
