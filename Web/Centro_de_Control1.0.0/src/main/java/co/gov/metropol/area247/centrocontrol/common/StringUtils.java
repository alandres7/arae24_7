package co.gov.metropol.area247.centrocontrol.common;

public class StringUtils {

	public static String truncarString(String str) {
		return truncarString(str, 7);
	}
	
	public static String truncarString(String str, int maxLength) {
		if(str != null && str.length() > maxLength) {
		    return str.substring(0, maxLength) + "...";
		} else {
		    return str;
		}		
	}

}
