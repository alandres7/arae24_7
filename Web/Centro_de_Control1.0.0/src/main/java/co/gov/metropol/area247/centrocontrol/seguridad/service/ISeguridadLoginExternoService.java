package co.gov.metropol.area247.centrocontrol.seguridad.service;

import co.gov.metropol.area247.centrocontrol.dto.LoginExternoDto;

public interface ISeguridadLoginExternoService {
	String getToken();
	boolean registrarLoginExterno(LoginExternoDto loginExterno);
}
