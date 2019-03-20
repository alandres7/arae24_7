package co.gov.metropol.area247.contenedora.service;

import java.util.Map;

import co.gov.metropol.area247.contenedora.model.dto.Email;

public interface IContenedoraEmailService {
	
	void enviarMailSimple(Email datosMail);
	void enviarMailTemplateThymeleaf(Email datosMail, String plantilla, Map<String, Object> datosPlantilla);
}
