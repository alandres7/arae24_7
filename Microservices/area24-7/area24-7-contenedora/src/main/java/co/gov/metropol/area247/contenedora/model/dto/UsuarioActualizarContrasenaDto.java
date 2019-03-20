package co.gov.metropol.area247.contenedora.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class UsuarioActualizarContrasenaDto implements Serializable {

	private static final long serialVersionUID = -8782959475171449205L;

	@NotNull
	private String username;

	@NotNull
	private String currentPassword;

	@NotNull
	private String newPassword;

	public UsuarioActualizarContrasenaDto() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
