package co.gov.metropol.area247.contenedora.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.contenedora.model.DireccionFavorita;

@Repository
public interface IContenedoraDireccionFavoritaRepository extends CrudRepository<DireccionFavorita, Long> {

}
