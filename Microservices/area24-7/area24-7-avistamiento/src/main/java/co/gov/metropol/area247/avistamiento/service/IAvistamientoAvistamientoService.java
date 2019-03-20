package co.gov.metropol.area247.avistamiento.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import co.gov.metropol.area247.avistamiento.model.Avistamiento;
import co.gov.metropol.area247.avistamiento.model.dto.AvistamientoDto;
import co.gov.metropol.area247.contenedora.model.Capa;
import co.gov.metropol.area247.contenedora.model.Icono;
import io.reactivex.Observable;

public interface IAvistamientoAvistamientoService {

	boolean avistamientoReportar(Avistamiento avistamiento);

	boolean avistamientoActualizar(Avistamiento avistamiento);

	boolean avistamientoEliminar(Long idAvistamiento);

	List<AvistamientoDto> avistamientoObtenerTodos();

	List<AvistamientoDto> avistamientoPorIdUsuario(Long idUsuario);

	List<AvistamientoDto> avistamientoPorEstado(int estado);

	AvistamientoDto buscarAvistamientoPorIdAvistamientoOIdMarcador(Long idAvistamiento, Long idMarcador);

	Avistamiento avistamientoPorId(Long idAvistamiento);

	Integer cantidadAvistamientoPorEstado(int estado);

	List<AvistamientoDto> obtenerAvistamientoPorParametros(String nombreComun, String nombreCientifico,
			String municipio, String tipoAvistamiento);

	Observable<Integer> obtenerNumeroAvistamientoPorTipoAvistamientoOrEstadoOrFecha(String tipoAvistamiento, 
			String idCategoria, Integer estado, LocalDate fechaInicio, LocalDate fechaFin, 
			boolean soloComHis, boolean conComenDeHis);

	List<AvistamientoDto> obtenerAvistamientoPorCapaFecha(Long idCapa, LocalDate desde, LocalDate hasta,
			Long idCategoria);

	String reportSighting(String nombreComun, String descripcion, String nombreCientifico, MultipartFile multimedia,
			String username, String nivelCapa, Long idCapaCategoria, Float latitud, Float longitud);

	boolean registerFloraInventoryAsSighting();

	int getFloraInventorySize() throws Exception;

	boolean registerFloraAsSighting(int limInf, int limSup, Long superAdminID, Capa capa, Icono  iconoEstado);

	int getNumAvistamXCapaFecha(Long idCapa, LocalDate desde, LocalDate hasta, Long idCategoria, String estadosList, 
			boolean soloComHis, boolean conComenDeHis);

	List<AvistamientoDto> getPaginatedAvistamientoPorCapaFecha(Long idCapa, LocalDate fechaDesde, LocalDate fechaHasta,
			Long idCategoria, String whereClause, String orderClause, int pageStart, int pageSize, String estadosList,
			boolean soloComHis, boolean conComenDeHis);

	int getFilteredAvistamientoPorCapaFecha(Long idCapa, LocalDate fechaDesde, LocalDate fechaHasta,
			Long idCategoria, String whereClause, String estadosList, 
			boolean soloComHis, boolean conComenDeHis);

	int getFloraInventoryRegisteredSize() throws Exception;

}
