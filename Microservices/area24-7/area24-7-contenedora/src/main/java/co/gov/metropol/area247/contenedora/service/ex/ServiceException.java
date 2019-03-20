package co.gov.metropol.area247.contenedora.service.ex;

import co.gov.metropol.area247.contenedora.dao.ex.DaoException;

public class ServiceException extends DaoException{
	
	private static final long serialVersionUID = 4572015399019404588L;

	public ServiceException(String message, Throwable cause){
		super(message, cause);
	}
	
	public ServiceException(String message){
		super(message);
	}

}
