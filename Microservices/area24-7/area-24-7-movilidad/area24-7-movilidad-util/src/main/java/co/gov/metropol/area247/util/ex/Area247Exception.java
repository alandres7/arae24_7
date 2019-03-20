package co.gov.metropol.area247.util.ex;

public class Area247Exception extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public Area247Exception(String message) {
        super(message);
    }

    public Area247Exception(String message, Throwable cause) {
        super(message, cause);
    }
}
