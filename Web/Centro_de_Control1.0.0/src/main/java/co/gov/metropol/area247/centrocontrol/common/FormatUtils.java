package co.gov.metropol.area247.centrocontrol.common;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtils {
	
	private static final String FORMATO_FECHA = "yyyy/MM/dd";
	
	public static Long toLong(String number) {
		try {
			return Long.parseLong(number);
		} catch (Exception e) {
			return null;
		}		
	}

	public static BigDecimal toBigDecimal(String number) {
		try {
			return new BigDecimal(number);
		} catch (Exception e) {
			return null;
		}		
	}
	
	public static Date toDate(String date, String format) {
		try {
			return (new SimpleDateFormat(format)).parse(date);
		} catch (Exception e) {
			return null;
		}		
	}
	
	public static Date toDate(String date) {
		return toDate(date, FORMATO_FECHA);
	}
	
	public static boolean formatoFechaValido(String date, String format, boolean requerido) {
		if(requerido) {
			return null!=toDate(date, format);
			
		} else if(null!=date && !"".equals(date.trim())) {
			return null!=toDate(date, format);
			
		} else {
			return true;
		}
	}
		
	public static boolean formatoAlfanumericoValido(String valor, int longitudMaxima, boolean requerido) {
		if(requerido && (null==valor || "".equals(valor.trim()) )) {
			return false;
		}
		if(valor.length() > longitudMaxima) {
			return false;
		}			
		return true;
	}
	
	public static boolean formatoNumericoValido(String valor, int longitudMaxima, boolean requerido) {
		if(null==valor || "".equals(valor.trim())) {
			return !requerido;
		} else {
			if(valor.length() > longitudMaxima) {
				return false;
			}			
			
			return formatoNumerico(valor);
		}		
	}

	public static boolean formatoNumerico(String valor) {
		try {
			Integer.parseInt(valor);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean formatoDecimalValido(String valor, int longitudMaxima, boolean requerido) {
		if(null==valor || "".equals(valor.trim())) {
			return !requerido;
		} else {
			if(valor.length() > longitudMaxima) {
				return false;
			}			
			
			return formatoDecimal(valor);
		}		
	}

	public static boolean formatoDecimal(String valor) {
		try {
			Double.parseDouble(valor);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static String getVal(String formVal) {
		if(null!=formVal && !"".equals(formVal.trim())) {
			return formVal;
		} else {
			return null;
		}
	}
	
	public static String getNumericVal(String formVal) {
		if(null!=formVal && !"".equals(formVal.trim())) {
			try {
				new BigDecimal(formVal);
				return formVal;				
			} catch (Exception e) {
				return null;
			}		
		} else {
			return null;
		}
	}
	
	public static String getDateVal(String formVal) {
		if(null!=formVal && !"".equals(formVal.trim())) {
			try {
				new SimpleDateFormat(FORMATO_FECHA).parse(formVal);
				return formVal;				
			} catch (Exception e) {
				return null;
			}		
		} else {
			return null;
		}
	}

	public static String dateToString(Date date, String format) {
		try {
			return  (new SimpleDateFormat(format)).format(date);
		} catch (Exception e) {
			return null;
		}		
	}
	
	public static boolean fromDateIsAfterToDateWithoutTime(Date fromDate, Date toDate) {
		Date fromNoTimeDate = dateWithoutTime(fromDate);
		Date toNoTimeDate = dateWithoutTime(toDate);
		return fromNoTimeDate.after(toNoTimeDate);
	}

	public static Date dateWithoutTime(Date date) {
		try {
			String strDate = (new SimpleDateFormat("yyyyMMdd")).format(date);
			return (new SimpleDateFormat("yyyyMMdd")).parse(strDate);					
		} catch (Exception e) {
			return date;
		}
	}
	
	public static Long toLongAvoidingNulls(String number) {
		try {
			return Long.parseLong(number);
		} catch (Exception e) {
			return (long) 0;
		}		
	}
	
}
