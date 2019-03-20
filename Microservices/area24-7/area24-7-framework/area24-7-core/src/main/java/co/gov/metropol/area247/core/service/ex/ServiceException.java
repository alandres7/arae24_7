package co.gov.metropol.area247.core.service.ex;

import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

public class ServiceException extends SQLException{

	private static final long serialVersionUID = -4369949567147657078L;

	public ServiceException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public ServiceException(String msg) {
		super(msg);
	}

}
