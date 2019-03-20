package co.gov.metropol.area247.contenedora.model.dto;

import java.util.List;

/**
 * <h1>POJO para manipular los datos asociados a un Email</h1>
 * 
 * @author ageofuentes
 * @version 1.0
 * @since 30-11-2017
 * */
public class Email {
	
	private String whoSend;
	private List<?> whoReceive;
	private List<?> withCopyToWho;
	private List<?> withCopyHideToWho;
	private String subject;
	private String message;
	private String attachments;
	/**
	 * Constructor por default
	 */
	public Email() {}
	/**
	 * @param whoSend Es quien envía el Email
	 * @param whoReceive Quienes que recibirán el Email
	 * @param withCopyToWho Quienes recibián copia del Email
	 * @param withCopyHideToWho Quienes recibirán copia oculta del Email
	 * @param subject El asunto del Email
	 * @param message El mensaje del Email
	 * @param attachments Los archivos adjuntos al Email
	 */
	public Email(String whoSend, List<?> whoReceive, List<?> withCopyToWho, List<?> withCopyHideToWho, String subject,
			String message, String attachments) {
		this.whoSend = whoSend;
		this.whoReceive = whoReceive;
		this.withCopyToWho = withCopyToWho;
		this.withCopyHideToWho = withCopyHideToWho;
		this.subject = subject;
		this.message = message;
		this.attachments = attachments;
	}
	/**
	 * @return the whoSend
	 */
	public String getWhoSend() {
		return whoSend;
	}
	/**
	 * @param whoSend the whoSend to set
	 */
	public void setWhoSend(String whoSend) {
		this.whoSend = whoSend;
	}
	/**
	 * @return the whoReceive
	 */
	public List<?> getWhoReceive() {
		return whoReceive;
	}
	/**
	 * @param whoReceive the whoReceive to set
	 */
	public void setWhoReceive(List<?> whoReceive) {
		this.whoReceive = whoReceive;
	}
	/**
	 * @return the withCopyToWho
	 */
	public List<?> getWithCopyToWho() {
		return withCopyToWho;
	}
	/**
	 * @param withCopyToWho the withCopyToWho to set
	 */
	public void setWithCopyToWho(List<?> withCopyToWho) {
		this.withCopyToWho = withCopyToWho;
	}
	/**
	 * @return the withCopyHideToWho
	 */
	public List<?> getWithCopyHideToWho() {
		return withCopyHideToWho;
	}
	/**
	 * @param withCopyHideToWho the withCopyHideToWho to set
	 */
	public void setWithCopyHideToWho(List<?> withCopyHideToWho) {
		this.withCopyHideToWho = withCopyHideToWho;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the attachments
	 */
	public String getAttachments() {
		return attachments;
	}
	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}
	
	
}
