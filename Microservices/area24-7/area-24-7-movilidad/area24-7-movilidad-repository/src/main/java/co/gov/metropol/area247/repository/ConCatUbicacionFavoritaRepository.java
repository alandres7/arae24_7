package co.gov.metropol.area247.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.gov.metropol.area247.repository.domain.ConCatUbicacionFavorita;

@Repository
public interface ConCatUbicacionFavoritaRepository extends CrudRepository<ConCatUbicacionFavorita, Long> {

	@Cacheable("categoriasUbicaciones")
	ConCatUbicacionFavorita findById(Long id);
	
}
