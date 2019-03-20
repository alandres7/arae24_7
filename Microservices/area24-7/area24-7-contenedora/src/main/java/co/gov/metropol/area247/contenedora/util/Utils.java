package co.gov.metropol.area247.contenedora.util;

public final class Utils {
	
	public static boolean isNull(Object objeto){
		boolean res = false; 
		
		if(objeto != null && !objeto.equals(null)){
			res = true;
		}
		return res;
		
	}
}
