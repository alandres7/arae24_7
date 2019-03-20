package co.gov.metropol.area247.centrocontrol.security.service.ex;

public class SecurityServiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SecurityServiceException(String error) {
		super(error);
	}

	public SecurityServiceException(String error, Throwable causa) {
		super(error, causa);
	}
}
