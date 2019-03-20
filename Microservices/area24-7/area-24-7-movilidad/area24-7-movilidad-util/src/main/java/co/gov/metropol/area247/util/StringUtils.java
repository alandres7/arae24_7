package co.gov.metropol.area247.util;

import org.apache.commons.lang3.text.WordUtils;

public class StringUtils {

	public static final String UNDERSCORE = "_";

	public static String valueToCamelCase(String value) {
		String result = WordUtils.capitalize(value, new char[] { '_' }).replaceAll(UNDERSCORE, "");

		result = result.substring(0, 1).toLowerCase() + result.substring(1);
		return result;
	}

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
