package co.gov.metropol.area247.centrocontrol.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.centrocontrol.model.Vehiculo;

@Repository
public interface VehiculoRepository extends CrudRepository<Vehiculo, Long> {

	@Query("select t from Vehiculo t where "
			+ " (:placa is null or :placa=t.placa) "
			+ " and (:clase is null or :clase=t.clase) "
			+ " and (:carroceria is null or :carroceria=t.carroceria) "
			+ " and (:marca is null or :marca=t.marca) "
			+ " and (:modelo is null or :modelo=t.modelo) "
			+ " and (:combustible is null or :combustible=t.combustible) "
			+ " and (:toneladas is null or :toneladas=t.toneladas) "
			+ "order by t.placa")
	List<Vehiculo> consultarVehiculos(
			@Param("placa") String placa,
			@Param("clase") String clase,
			@Param("carroceria") String carroceria,
			@Param("marca") String marca,
			@Param("modelo") Long modelo,
			@Param("combustible") String combustible,
			@Param("toneladas") BigDecimal toneladas,
			Pageable pageable
			);

	Vehiculo findByPlaca(String placa);

}
