package co.gov.metropol.area247.jdbc.util;

import java.util.Collection;
import java.util.function.Function;

public class Utils {

	public static Function<Object, Boolean> isNull = Utils::isNull;
	
	private static final String ACTIVE = "1";

	private static boolean isNull(Object object) {
		if (object != null) {
			if ((object instanceof String) && "".equals(object)) {
				return true;
			} else if (((object instanceof Object[])) && ((Object[]) object).length <= 0) {
				return true;
			} else if (((object instanceof Collection)) && ((Collection<?>) object).isEmpty()) {
				return true;
			}
			return false;
		}
		return true;
	}

	public static boolean isZero(Long value) {
		return isNull(value) || value.longValue() == 0;
	}
	
	public static boolean isActive(String value){
		return  value.equals(ACTIVE);
	}

}
