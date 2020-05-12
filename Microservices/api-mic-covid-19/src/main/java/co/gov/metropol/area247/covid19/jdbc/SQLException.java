package co.gov.metropol.area247.covid19.jdbc;

import org.springframework.dao.DataAccessException;

public class SQLException extends DataAccessException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4412587906815197041L;

	public SQLException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public SQLException(String msg) {
		super(msg);
	}

}
