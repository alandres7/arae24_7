package co.gov.metropol.area247.contenedora.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import co.gov.metropol.area247.contenedora.model.dto.Email;
import co.gov.metropol.area247.contenedora.service.IContenedoraEmailService;
import co.gov.metropol.area247.contenedora.util.logger.Logger;

@Service
public class IContenedoraEmailServiceImpl implements IContenedoraEmailService {

	public static final Logger LOGGER = Logger.getInstance(IContenedoraEmailServiceImpl.class);
	private static final String PROPERTY_ADMIN_CORREO = "admin.envio.mail";
	private static final String EXITO_ENVIO_MAIL = "Se envió el email satisfactoriamente.";
	private static final String FALLO_ENVIO_MAIL = "Error enviando email...";

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;
	
	@Autowired
	private Environment enVar;
	
	
	public void prepararDatosMail(Email datosEmail){
		
	}

	/**
	 * @param datosMail 
	 * @return void
	 */
	@Override
	public void enviarMailSimple(Email datosMail) {
		String[] listaCorreos = this.getCorreosDestino(datosMail.getWhoReceive());
		MimeMessagePreparator preparadorMsg = mimeMessage -> {
			MimeMessageHelper auxMsg = new MimeMessageHelper(mimeMessage);
			auxMsg.setFrom(datosMail.getWhoSend());
			auxMsg.setTo(listaCorreos[0]);
			auxMsg.setSubject(datosMail.getSubject());
			auxMsg.setText(datosMail.getMessage(), Boolean.TRUE);
		};
		try {
			mailSender.send(preparadorMsg);
			LOGGER.info(EXITO_ENVIO_MAIL);
		} catch (Exception e) {
			LOGGER.info(FALLO_ENVIO_MAIL, e);
			e.getMessage();
		}

	}
			
	/**
	 * @author ageofuentes
	 * @param datosMail
	 * @param plantilla
	 * @param datosPlantilla
	 * @return void
	 * */
	public void enviarMailTemplateThymeleaf(Email datosMail, String plantilla, Map<String, Object> datosPlantilla) {
		MimeMessage mensaje = mailSender.createMimeMessage();
		String[] listaCorreos = this.getCorreosDestino(datosMail.getWhoReceive());
		try {
			MimeMessageHelper ayudante = new MimeMessageHelper(mensaje,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			Context contextoPlantilla = new Context();
			contextoPlantilla.setVariables(datosPlantilla);
			String html = templateEngine.process(plantilla, contextoPlantilla);
			ayudante.setTo(listaCorreos[0]);
			ayudante.setText(html, true);
			ayudante.setSubject(datosMail.getSubject());
			ayudante.setFrom(enVar.getProperty(PROPERTY_ADMIN_CORREO));
			mailSender.send(mensaje);
			LOGGER.info(EXITO_ENVIO_MAIL);
		} catch (MessagingException e) {
			LOGGER.info(FALLO_ENVIO_MAIL, e);
		}catch (MailSendException e) {
			LOGGER.info(FALLO_ENVIO_MAIL, e);
		}
	}

	/**
	 * @author ageofuentes
	 * @param listaDestinatarios
	 * @return correos
	 */
	public String[] getCorreosDestino(List<?> listaDestinatarios) {
		List<String> listaDestino = this.getListCorreos(listaDestinatarios);
		String[] correos = new String[listaDestino.size()];
		correos = listaDestino.toArray(correos);
		return correos;
	}

	/**
	 * @author ageofuentes
	 * @param listaDestinatarios
	 * @return listaCorreos
	 */
	public List<String> getListCorreos(List<?> listaDestinatarios) {
		List<String> listaCorreos = new LinkedList<>();
		for (Object destinatario : listaDestinatarios) {
			if (destinatario instanceof String) {
				String correoE = String.valueOf(destinatario);
				listaCorreos.add(correoE);
			} // Otras condiciones!
		}
		return listaCorreos;
	}

}
