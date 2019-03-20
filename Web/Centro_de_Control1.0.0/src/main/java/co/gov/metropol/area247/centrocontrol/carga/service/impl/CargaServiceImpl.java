package co.gov.metropol.area247.centrocontrol.carga.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.carga.helper.ErrorCampoArchivoHelper;
import co.gov.metropol.area247.centrocontrol.carga.service.ICamaraService;
import co.gov.metropol.area247.centrocontrol.carga.service.ICargaService;
import co.gov.metropol.area247.centrocontrol.carga.service.ICirculacionVehiculoService;
import co.gov.metropol.area247.centrocontrol.carga.service.IVehiculoService;
import co.gov.metropol.area247.centrocontrol.common.Area247Exception;
import co.gov.metropol.area247.centrocontrol.common.StringUtils;
import co.gov.metropol.area247.centrocontrol.dto.CargaArchivoDto;
import co.gov.metropol.area247.centrocontrol.dto.ErrorCampoArchivoDto;
import co.gov.metropol.area247.centrocontrol.dto.ErrorLineaArchivoDto;
import co.gov.metropol.area247.centrocontrol.dto.ValidarArchivoDto;

@Service
public class CargaServiceImpl implements ICargaService {

	private static final int TIPO_ARCHIVO_VEHICULO = 1; 
	private static final int TIPO_ARCHIVO_CAMARAS = 2; 
	private static final int TIPO_ARCHIVO_CIRCULACION = 3;
	
	@Value("${area247.carga.bytesmax}")
	private int bytesMax;

	@Value("${area247.carga.erroresmax}")
	private int erroresMax;
	
	@Autowired
	private ICamaraService camaraService;

	@Autowired
	private IVehiculoService vehiculoService;
	
	@Autowired
	private ICirculacionVehiculoService circulacionService;
	
	@Override
	public ValidarArchivoDto validarArchivo (CargaArchivoDto cargaArchivoDTO, Long tipoArchivoId, String separadorCsv, int lineasEncabezado, String formatoFecha) throws IOException {
		int lineas = 0;
		List<ErrorLineaArchivoDto> errores = new ArrayList<>();		
		
		if(null==cargaArchivoDTO || null==cargaArchivoDTO.getArchivo() || null==cargaArchivoDTO.getArchivo().getInputStream()) {
			throw new Area247Exception("El archivo está vacío.");
		}
		
		if(cargaArchivoDTO.getArchivo().getBytes().length > bytesMax) {
			throw new Area247Exception("El archivo tiene más de "+bytesMax+" bytes.");
		}		
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(cargaArchivoDTO.getArchivo().getInputStream()));
		
		for (int i = 1; i <= lineasEncabezado; i++) {
			bufferedReader.readLine(); //Saltarse lineas de encabezado			
		}
		
		String linea;		
		while ((linea = bufferedReader.readLine()) != null) {
			lineas++;
			
			ErrorLineaArchivoDto error = validarLinea(linea, lineas, tipoArchivoId, separadorCsv, formatoFecha);
			if(null!=error) {
				errores.add(error);
			}
			
			if(errores.size() > erroresMax) { 
				List<ErrorCampoArchivoDto> el = new ArrayList<>();
				el.add(new ErrorCampoArchivoDto("El archivo contiene muchos errores, por favor corríjalos y vuélvalo a cargar"));
				errores.add(new ErrorLineaArchivoDto(lineas, "", el));
				break; 
			}
		}
		
		if (lineas==0) {
			throw new Area247Exception("El archivo no tiene filas.");
		}
		
		ValidarArchivoDto dto = new ValidarArchivoDto();
		dto.setErrores(errores);
		dto.setLineas(lineas);
		return dto;
	}
	
	private ErrorLineaArchivoDto validarLinea(String linea, int numeroLinea, Long tipoArchivoId, String separadorCsv, String formatoFecha) {
		
		List<ErrorCampoArchivoDto> el = new ArrayList<>();
		
		if(null==linea || "".equals(linea.trim())) {
			el.add(new ErrorCampoArchivoDto("La línea está en blanco"));
			return new ErrorLineaArchivoDto(numeroLinea, "", el);
		}
		
		ErrorCampoArchivoHelper eah = new ErrorCampoArchivoHelper(linea, separadorCsv);		
		
		switch(tipoArchivoId.intValue()) {
			case TIPO_ARCHIVO_VEHICULO:{
				el = vehiculoService.validarLinea(eah);
			}break;
			case TIPO_ARCHIVO_CAMARAS:{
				el = camaraService.validarLinea(eah, formatoFecha);
			}break;
			case TIPO_ARCHIVO_CIRCULACION:{
				el = circulacionService.validarLineaArchivo(eah, formatoFecha);
			}break;
		}
		
		
		el.removeAll(Collections.singleton(null));
		
		if(el.isEmpty()) {
			return null;
		}
		
		return new ErrorLineaArchivoDto(numeroLinea, StringUtils.truncarString(eah.getId()), el);
	}

}
