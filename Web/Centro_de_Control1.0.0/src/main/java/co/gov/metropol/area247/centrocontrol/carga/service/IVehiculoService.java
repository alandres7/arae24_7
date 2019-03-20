package co.gov.metropol.area247.centrocontrol.carga.service;

import java.math.BigDecimal;
import java.util.List;

import co.gov.metropol.area247.centrocontrol.carga.helper.ErrorCampoArchivoHelper;
import co.gov.metropol.area247.centrocontrol.dto.CargaArchivoDto;
import co.gov.metropol.area247.centrocontrol.dto.ErrorCampoArchivoDto;
import co.gov.metropol.area247.centrocontrol.dto.HuellaCarbonoDto;
import co.gov.metropol.area247.centrocontrol.model.Fotodeteccion;
import co.gov.metropol.area247.centrocontrol.model.TipoCarroceriaVehiculo;
import co.gov.metropol.area247.centrocontrol.model.TipoClaseVehiculo;
import co.gov.metropol.area247.centrocontrol.model.TipoCombustibleVehiculo;
import co.gov.metropol.area247.centrocontrol.model.TipoMarcaVehiculo;
import co.gov.metropol.area247.centrocontrol.model.Vehiculo;

public interface IVehiculoService {
	
	/**
	 * Validar una linea del archivo a cargar
	 * 
	 * @param eah
	 * @return
	 */
	List<ErrorCampoArchivoDto> validarLinea(ErrorCampoArchivoHelper eah);
	
	/**
	 * Cargar archivo de vehiculos del RUNT
	 * 
	 * @param chunkSize
	 * @param cargaArchivoDTO
	 * @param lineas
	 * @param separadorCsv
	 * @param lineasEncabezado
	 */
	void procesarArchivo (int chunkSize, CargaArchivoDto cargaArchivoDTO, int lineas, String separadorCsv, int lineasEncabezado);

	/**
	 * Consulta para el listbox de carrocerias en el reporte de huella de carbono
	 * 
	 * @return
	 */
	List<TipoCarroceriaVehiculo> consultarCarrocerias();
	
	/**
	 * Consulta para el listbox de clases de vehiculo en el reporte de huella de carbono
	 * 
	 * @return
	 */
	List<TipoClaseVehiculo> consultarClases();
	
	/**
	 * Consulta para el listbox de tipos de combustible en el reporte de huella de carbono
	 * 
	 * @return
	 */
	List<TipoCombustibleVehiculo> consultarCombustibles();
	
	/**
	 * Consulta para el listbox de marcas en el reporte de huella de carbono
	 * 
	 * @return
	 */
	List<TipoMarcaVehiculo> consultarMarcas();

	/**
	 * Consulta de placas para el reporte de huella de carbono
	 * 
	 * @param dto
	 * @param maxRows
	 * @return
	 */
	List<Vehiculo> consultarVehiculos(HuellaCarbonoDto dto, int maxRows);

	/**
	 * Consulta de una placa para el reporte de huella de carbono
	 * 
	 * @param placa
	 * @return
	 */
	Vehiculo findByPlaca(String placa);

	/**
	 * Consulta de detecciones de una placa, en una fecha, para el reporte de huella de carbono
	 * 
	 * @param placa
	 * @param fecha
	 * @return
	 */
	List<Fotodeteccion> consultarDetecciones(String placa, String fecha);

	/**
	 * Calculo de la distancia minima recorrida de una placa, en una fecha, para el reporte de huella de carbono
	 * @param placa
	 * @param fecha
	 * @return
	 */
	BigDecimal distanciaRecorrida(String placa, String fecha);

	/**
	 * Calculo del porcentaje de huella de carbono de una placa, en una fecha, para el reporte de huella de carbono
	 * 
	 * @param placa
	 * @param fecha
	 * @return
	 */
	Double porcentajeHuellaCarbono(String placa, String fecha);

	/**
	 * Calculo del porcentaje de participacion de la carroceria de una placa, en una fecha, para el reporte de huella de carbono
	 * 
	 * @param placa
	 * @param fecha
	 * @return
	 */
	Double porcentajeCarroceria(String placa, String fecha);

	/**
	 * Eliminar version anterior de registros subidos por segunda vez en una carga (efecto de sobre escribirlos)
	 * 
	 */	
	void eliminarDuplicados();
	
	/**
	 * Contar cuantos registros fueron sobre escritos en una carga para informarlo al usuario que hizo la carga
	 * 
	 * @return
	 */	
	int contarVehiculosDuplicados();
}
