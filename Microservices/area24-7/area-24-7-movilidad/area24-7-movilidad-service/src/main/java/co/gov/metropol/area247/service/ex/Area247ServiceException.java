package co.gov.metropol.area247.service.ex;

public class Area247ServiceException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public Area247ServiceException(String message) {
        super(message);
    }

    public Area247ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
