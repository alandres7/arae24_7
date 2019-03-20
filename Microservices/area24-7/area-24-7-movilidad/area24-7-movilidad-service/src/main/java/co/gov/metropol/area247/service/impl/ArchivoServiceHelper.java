package co.gov.metropol.area247.service.impl;

import co.gov.metropol.area247.repository.domain.support.enums.FormatoCamaraEnum;
import co.gov.metropol.area247.util.FormatUtils;

public class ArchivoServiceHelper {
	private String[] campos;
	
	public ArchivoServiceHelper(String linea, String separador) {
		if(null!=linea && !"".equals(linea.trim())) {
			campos = linea.split(separador);			
		}
	}

	public boolean formatoInvalido(int numeroCampos) {
		return (null==campos || (campos.length < numeroCampos));
	}
	
	public String errorFecha(FormatoCamaraEnum fc, String formato, boolean requerido) {
		if (!FormatUtils.formatoFechaValido(campos[fc.ordinal()], formato, requerido)) {
			return "[Columna="+fc.ordinal()+",Nombre="+fc.name()+",Id="+campos[0]+",Valor="+campos[fc.ordinal()]+",Error=Debe estar en formato " + formato + "]";			
		}
		return "";
	}
	
	public String errorSoN(FormatoCamaraEnum fc) {
		if (!campos[fc.ordinal()].equals("S") && !campos[fc.ordinal()].equals("N")) {
			return "[Columna="+fc.ordinal()+",Nombre="+fc.name()+",Id="+campos[0]+",Valor="+campos[fc.ordinal()]+",Error=Debe ser S o N]";
		}
		return "";
	}
	
	public String errorAlfanumerico(FormatoCamaraEnum fc, int longitud, boolean requerido) {
		if (!FormatUtils.formatoAlfanumericoValido(campos[fc.ordinal()], longitud, requerido)) {
			return "[Columna="+fc.ordinal()+",Nombre="+fc.name()+",Id="+campos[0]+",Valor="+campos[fc.ordinal()]+",Error=Debe ser de " + longitud + " caracteres máximo]";
		}
		return "";
	}

	public String errorNumerico(FormatoCamaraEnum fc, int longitud, boolean requerido) {
		if (!FormatUtils.formatoNumericoValido(campos[fc.ordinal()], longitud, requerido)) {
			return "[Columna="+fc.ordinal()+",Nombre="+fc.name()+",Id="+campos[0]+",Valor="+campos[fc.ordinal()]+",Error=Debe ser numérico de " + longitud + " caracteres máximo]";
		}
		return "";
	}
}
