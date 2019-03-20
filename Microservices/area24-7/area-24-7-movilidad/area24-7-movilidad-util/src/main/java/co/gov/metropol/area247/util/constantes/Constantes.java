package co.gov.metropol.area247.util.constantes;

public final class Constantes {

	public static final String ACTIVO = "S";
	public static final String MOVILIDAD = "MOVILIDAD";
	public static final String COMA = ",";
	public static final String ACENTOS = "ñáéíóúàèìòùãõâêîôôäëïöüçÑÁÉÍÓÚÀÈÌÒÙÃÕÂÊÎÔÛÄËÏÖÜÇ";
	public static final String SIN_ACENTOS = "naeiouaeiouaoaeiooaeioucNAEIOUAEIOUAOAEIOOAEIOUC";
	public static final String TIME_ZONE = "America/Bogota";
	public static final String LANGUAJE = "es";
	
	public static final int TIPO_TRANSPORTE_PIE = 0;
	public static final int TIPO_TRANSPORTE_LINEA = 1;
	public static final int TIPO_TRANSPORTE_RUTA = 2;
	public static final int TIPO_TRANSPORTE_CICLA = 3;
	
	public static class  TipoParametro {
		
		public static final long MAX_DIRECCIONES_FAVORITAS = 1;
		public static final long URL_FILE_GTFS_METRO = 2;
		public static final long URL_FILES_GBFS_ENCICLA = 	3;
		public static final long URL_FOLDER_OTP = 4;
		public static final long SECRETARIAS_HABILITADAS = 5;
		public static final long NAME_JAR_OTP = 6;
		public static final long NAME_GTFS_GENERAL = 7;
		public static final long URL_FOLDER_GTFS_GENERAL = 8;
		public static final long DISTANCIA_MAX_CAMINANDO = 9;
		
	}

	public static class Recursos {
		public static final String ROUTE_SERVICES = "routeServices";
		public static final String TIME_TASK = "timeTask";
		public static final String MENSAJES = "messages";
	}

	public static class Metro {
		public static final String METRO = "METRO";
		// CODIGO RESULTADO DE OPERACIONES WS
		public static final int ERROR_DATOS_INCONSISTENTES = 4;
		public static final int ERROR_AUTENTICACION = 5;
		public static final int ERROR_TECNICO_DESCIFRADO = 6;
		// CODIGO TIPOS DE RUTA
		public static final int TIPO_RUTA_TRANVIA = 0;
		public static final int TIPO_RUTA_TREN = 2;
		public static final int TIPO_RUTA_AUTOBUS = 1;
		public static final int TIPO_RUTA_ALIMENTADOR = 4;
		public static final int TIPO_RUTA_INTEGRADO = 7;
		public static final int TIPO_RUTA_PLUS = 3;
		public static final int TIPO_RUTA_TELEFERICO = 6;

		public static final int TAM_MAX_CODIGO_LINEA = 4;
		public static final int FUENTE_DATOS = 3;

		

	}

	public static class PosibilidadViaje {
		// Codigos del resultado del consumo del OTP
		public static final int EXITO = 1;
		public static final int NO_ENCONTRO_VIAJES = 2;
		public static final int OTP_ERROR_INTERNO = 3;
		public static final int MODO_TRANSPORTE_SUGERIDO = 4;

	}
	
	public static class Gtpc {
		
		public static final int FUENTE_DATOS = 2;
		public static final String GTPC = "GTPC";
		
	} 

}
