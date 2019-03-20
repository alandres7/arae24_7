package co.gov.metropol.area247.avistamiento.repository;

import java.util.List;

import co.gov.metropol.area247.avistamiento.domain.context.app.InfoNodoAvistamiento;
import co.gov.metropol.area247.core.repository.data.CrudRepository;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;
import io.reactivex.Observable;

public interface InfoNodoAvistamientoRepository extends CrudRepository<InfoNodoAvistamiento> {

	public Observable<List<InfoNodoAvistamiento>> obtenerInformacionDelNodoPorNodoPadre(Long idNodoPadre) throws SQLException;

}
