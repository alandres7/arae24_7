package co.gov.metropol.area247.util;

import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Esta clase administra la información contenida en los properties. Pone a
 * disposición la funcionalidad de consultar de un properties por una clave
 * específica sin que el archivo tenga que ser cargado una y otra vez en los
 * diferentes métodos de la aplicación
 * 
 * @author Jonathan Mosquera
 *
 */

public class PropertiesManager {

	// private static Logger logger =
	// LoggerFactory.getLogger(PropertiesManager.class);

	/**
	 * Hashtable para almacenar los archivos, usando como llave el nombre del
	 * archivo
	 */
	private static Map<String, ResourceBundle> table = new Hashtable<String, ResourceBundle>();

	/**
	 * Este método recibe un nombre de archivo y una cadena clave. Del archivo
	 * especificado retorna el valor que corresponde a la cadena.
	 * 
	 * @param nombreArchivo
	 *            Nombre del archivo del que se obtendrá la información
	 * @param nombreClave
	 *            Clave que será usada para identifica la cadena
	 * @return Retorna un string con la cadena correspondiente.
	 */
	public static String obtenerCadena(String nombreArchivo, String nombreClave) {
		// Intento obtener el archivo del hashtable
		ResourceBundle bundle = (ResourceBundle) table.get(nombreArchivo);

		// Si no logró obtener el archivo de la tabla, entonces lo cargo

		bundle = ResourceBundle.getBundle(nombreArchivo);

		return bundle.getString(nombreClave);
	}

}
