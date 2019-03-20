package co.gov.metropol.area247.entorno.service;

import java.util.List;

import co.gov.metropol.area247.entorno.model.Medicion;
import co.gov.metropol.area247.entorno.model.dto.MedicionDto;

public interface IEntornoMedicionService {

	boolean medicionCrear(Medicion medicion);
	boolean medicionActualizar(Medicion medicion);
	boolean medicionEliminar(Long idMedicion);
	List<Medicion> medicionObtenerTodas();
	Medicion medicionObtenerPorId(Long idMedicion);
	MedicionDto medicionDtoObtenerPorId(Long idMedicion);
	List<Medicion> medicionObtenerPorIdCapa(Long idCapa);
	List<MedicionDto> medicionDtoObtenerPorIdCapa(Long idCapa);
	Long asignarVentanaInformacion(Medicion medicion);
	boolean verificarSolapamientoIntervalos(Long idMedicion, Long idCapa, float escalaInicial, float escalaFinal);
	boolean medicionActualizarVentana(Medicion medicion);
	List<String> obtenerNombresMedicion();
}
