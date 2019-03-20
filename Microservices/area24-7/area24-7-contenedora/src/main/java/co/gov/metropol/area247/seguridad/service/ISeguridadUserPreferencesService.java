package co.gov.metropol.area247.seguridad.service;

public interface ISeguridadUserPreferencesService {

	String getUserPreferences(String username);
	
	String updatePreferences(String username, String preferencias);
	
}
