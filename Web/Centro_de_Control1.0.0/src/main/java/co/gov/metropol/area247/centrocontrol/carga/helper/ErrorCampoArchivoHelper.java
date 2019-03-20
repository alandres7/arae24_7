package co.gov.metropol.area247.centrocontrol.carga.helper;

import co.gov.metropol.area247.centrocontrol.common.FormatUtils;
import co.gov.metropol.area247.centrocontrol.common.StringUtils;
import co.gov.metropol.area247.centrocontrol.dto.ErrorCampoArchivoDto;

public class ErrorCampoArchivoHelper {
	private String[] campos;
	
	public ErrorCampoArchivoHelper(String linea, String separador) {
		if(null!=linea && !"".equals(linea.trim())) {
			campos = linea.split(separador, -1);			
		}
	}
	
	public String getId() {
		if(null!=campos && campos.length > 0) {
			return campos[0];
			
		} else {
			return "";
			
		}
	}

	public boolean formatoInvalido(int numeroCampos) {
		return (null==campos || (campos.length < numeroCampos));
	}
	
	public String getValue(Enum<?> fc) {
		return campos[fc.ordinal()];
	}
	
	public ErrorCampoArchivoDto errorFecha(Enum<?> fc, String formato, boolean requerido) {
		if (!FormatUtils.formatoFechaValido(campos[fc.ordinal()], formato, requerido)) {
			return new ErrorCampoArchivoDto(fc.ordinal()+"", fc.name(), StringUtils.truncarString(campos[fc.ordinal()]), "Debe estar en formato " + formato); 
		}
		return null;
	}
	
	public ErrorCampoArchivoDto errorSoN(Enum<?> fc) {
		if (!campos[fc.ordinal()].equals("S") && !campos[fc.ordinal()].equals("N")) {
			return new ErrorCampoArchivoDto(fc.ordinal()+"", fc.name(), StringUtils.truncarString(campos[fc.ordinal()]), "Debe ser S o N");
		}
		return null;
	}
	
	public ErrorCampoArchivoDto errorAlfanumerico(Enum<?> fc, int longitud, boolean requerido) {
		if (!FormatUtils.formatoAlfanumericoValido(campos[fc.ordinal()], longitud, requerido)) {
			if(requerido && (null==campos[fc.ordinal()] || "".equals(campos[fc.ordinal()].trim()) )) {
				return new ErrorCampoArchivoDto(fc.ordinal()+"", fc.name(), StringUtils.truncarString(campos[fc.ordinal()]), "No puede estar vacío");
			}
			return new ErrorCampoArchivoDto(fc.ordinal()+"", fc.name(), StringUtils.truncarString(campos[fc.ordinal()]), "Debe ser de " + longitud + " caracteres máximo");
		}
		return null;
	}

	public ErrorCampoArchivoDto errorNumerico(Enum<?> fc, int longitud, boolean requerido) {
		if (!FormatUtils.formatoNumericoValido(campos[fc.ordinal()], longitud, requerido)) {
			if(requerido && (null==campos[fc.ordinal()] || "".equals(campos[fc.ordinal()].trim()) )) {
				return new ErrorCampoArchivoDto(fc.ordinal()+"", fc.name(), StringUtils.truncarString(campos[fc.ordinal()]), "No puede estar vacío");
			}			
			return new ErrorCampoArchivoDto(fc.ordinal()+"", fc.name(), StringUtils.truncarString(campos[fc.ordinal()]), "Debe ser numérico de " + longitud + " caracteres máximo");
		}
		return null;
	}

	public ErrorCampoArchivoDto errorDecimal(Enum<?> fc, int longitud, boolean requerido) {
		if (!FormatUtils.formatoDecimalValido(campos[fc.ordinal()], longitud, requerido)) {
			if(requerido && (null==campos[fc.ordinal()] || "".equals(campos[fc.ordinal()].trim()) )) {
				return new ErrorCampoArchivoDto(fc.ordinal()+"", fc.name(), StringUtils.truncarString(campos[fc.ordinal()]), "No puede estar vacío");
			}			
			return new ErrorCampoArchivoDto(fc.ordinal()+"", fc.name(), StringUtils.truncarString(campos[fc.ordinal()]), "Debe ser numérico (sin comas) de " + longitud + " caracteres máximo");
		}
		return null;
	}

}
