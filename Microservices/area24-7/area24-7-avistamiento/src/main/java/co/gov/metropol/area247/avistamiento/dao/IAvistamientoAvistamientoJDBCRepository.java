package co.gov.metropol.area247.avistamiento.dao;

import java.time.LocalDate;
import java.util.List;

import co.gov.metropol.area247.avistamiento.model.Avistamiento;
import co.gov.metropol.area247.avistamiento.model.dto.AvistamientoDto;
import co.gov.metropol.area247.avistamiento.model.dto.FloraDto;
import co.gov.metropol.area247.core.repository.data.CrudRepository;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;
import io.reactivex.Observable;

public interface IAvistamientoAvistamientoJDBCRepository extends CrudRepository<Avistamiento> {

	public List<AvistamientoDto> buscarTodosAvistamientos() throws SQLException;

	public AvistamientoDto buscarAvistamientoPorIdAvistamientoOIdMarcador(Long idAvistamiento, Long idMarcador)
			throws SQLException;

	public List<AvistamientoDto> obtenerAvistamientoPorEstado(int estado) throws SQLException;

	public List<AvistamientoDto> obtenerAvistamientoPorIdUsuario(Long idUsuario) throws SQLException;

	public Integer cantidadAvistamientoPorEstado(int estado) throws SQLException;

	public List<AvistamientoDto> obtenerAvistamientoPorParametros(String nombreComun, String nombreCientifico,
			String municipio, String tipoAvistamiento) throws SQLException;

	public Observable<Integer> obtenerNumeroAvistamientoPorTipoAvistamientoOrEstadoOrFecha(String tipoAvistamiento,
			String idCategoria, Integer estado, LocalDate fechaInicio, LocalDate fechaFin, 
			boolean soloComHis, boolean conComenDeHis) throws SQLException;

	public List<AvistamientoDto> obtenerAvistamientoPorCapaFecha(Long idCapa, LocalDate desde, LocalDate hasta,
			Long idCategoria) throws SQLException;

	public List<FloraDto> getFloraInventory(int limInf, int limSup) throws SQLException;

	int countFloraInventory() throws SQLException;

	int getNumAvistamXCapaFecha(Long idCapa, LocalDate desde, LocalDate hasta, Long idCategoria, 
			String estadosList, boolean soloComHis, boolean conComenDeHis) throws SQLException;

	List<AvistamientoDto> getAvistamientoPaginatedPorCapaFecha(Long idCapa, LocalDate desde, LocalDate hasta, Long idCategoria,
			String whereClause, String orderClause, int pageStart, int pageSize, String estadosList, 
			boolean soloComHis, boolean conComenDeHis) throws SQLException;

	int getNumFilteredAvistamXCapaFecha(Long idCapa, LocalDate desde, LocalDate hasta, Long idCategoria,
			String whereClause, String estadosList, boolean soloComHis, boolean conComenDeHis) throws SQLException;

	int countFloraInventoryRegistered() throws SQLException;
	
}
