package co.gov.metropol.area247.contenedora.util.logger;

import org.slf4j.Logger;

class LoggerFactory {
	private LoggerFactory(){
		super();
	}
	
	public static Logger getLogger(Class<?> clazz) {
        return org.slf4j.LoggerFactory.getLogger(clazz);
    }

    public static Logger getLogger(String nameClazz) {
        return org.slf4j.LoggerFactory.getLogger(nameClazz);
    }
	
}
