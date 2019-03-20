package co.gov.metropol.area247.centrocontrol.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.centrocontrol.model.Fotodeteccion;

@Repository
public interface FotodeteccionRepository extends CrudRepository<Fotodeteccion, Long> {

	List<Fotodeteccion> findByPlacaAndFecha(String placa, String fecha);

	List<Fotodeteccion> findByFecha(String fecha);

}
