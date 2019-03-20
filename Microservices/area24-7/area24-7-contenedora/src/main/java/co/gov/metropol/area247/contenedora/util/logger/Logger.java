package co.gov.metropol.area247.contenedora.util.logger;

import co.gov.metropol.area247.contenedora.util.exceptions.Area247Exception;

public final class Logger {
	private org.slf4j.Logger printerLog;
	private static final String tab = "\n\t";

	private Logger instance;

	private Logger() {
		super();
	}

	public static Logger getInstance(Class<?> clazz) {
		Logger log = new Logger(clazz);
		log.instance = log;
		return log;
	}

	private Logger(Class<?> clazz) {
		this.printerLog = LoggerFactory.getLogger(clazz);
	}

	public Logger log() {
		return instance;
	}

	public void info(String msg, Object... arg) {
		String log = log(msg, arg);
		printerLog.info(log);
	}

	public void debug(String msg, Object... arg) {
		String log = log(msg, arg);
		if (!log.isEmpty())
			printerLog.debug(log);
	}

	public void warn(String msg, Object... arg) {
		String log = log(msg, arg);
		if (!log.isEmpty())
			printerLog.warn(log);
	}

	public void error(String msg, Object... arg) {
		String log = log(msg, arg);
		if (!log.isEmpty())
			printerLog.error(log);
	}

	private String log(String msg, Object[] arg) {
		int errorCode = 0;
		if (arg != null && arg.length > 0) {
			errorCode = Math.abs(arg[0].hashCode());
		} else {
			errorCode = Math.abs(msg.hashCode());
		}
		String value = args(arg);
		if (value.isEmpty()) {
			return tab + "Tarea : " + msg;
		}
		return tab + "Hash code del mensaje : " + errorCode + tab + "Tarea : " + msg + value;
	}

	private String args(Object... args) {
		StringBuilder arg = new StringBuilder();
		if (args != null) {
			for (Object s : args) {
				if (s instanceof Area247Exception) {
					arg.append(tab + "Mensaje : '" + ((Exception) s).getMessage() + "' ");
				} else if (s instanceof Exception) {
					arg.append(tab + "Origen : " + s.getClass().getName() + tab + "Mensaje : '"
							+ ((Exception) s).getMessage() + "'" + (((Exception) s).getCause() != null
									? tab + "Causa : '" + ((Exception) s).getCause() + "'" : ""));
				} else {
					arg.append(tab + "Ejecutando : " + s + " ");
				}
			}
		}
		return arg.toString();
	}
}
