package co.gov.metropol.area247.entorno.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.gov.metropol.area247.entorno.model.VentanaPronostico;

public interface IEntornovVentanaPronosticoRepository extends CrudRepository<VentanaPronostico, Long> {
	List<VentanaPronostico> findByCodigoVentana(long codigoVentana);
}
