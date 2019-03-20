package co.gov.metropol.area247.contenedora.dao.ex;

import java.sql.SQLException;

public class DaoException extends SQLException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6831739765716228650L;

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DaoException(String message){
		super(message);
	}

}
