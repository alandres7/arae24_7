package co.gov.metropol.area247.logging;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.util.Utils;

public class LoggingUtil {
	
	private static Logger logger = LoggerFactory.getLogger(LoggingUtil.class);

    public static final String OPEN_BRACKET = "[";
    public static final String CLOSE_BRACKET = "]";
    public static final String NULL_VALUE = "null";
    public static final String KEY_VALUE_SEPERATOR = "=";
    public static final String BASE_PACKAGE_NAME = "co.gov.metropol.Area247";
    public static final String COLLECTION_NAME = "size";
    
    private static StringBuilder fields;

    /**
     * Returns a formatted string of all of the fields of a class
     * that are safe to log
     * 
     * @param object - process object
     */
    public static String getLoggerFields(Object object) {
        if (object == null) {
            return OPEN_BRACKET + NULL_VALUE + CLOSE_BRACKET;
        }

        fields = new StringBuilder();

        try {
            List<Field> objectFields = new ArrayList<>();

            Class<?> current = object.getClass();
            while (!Utils.isNull(current)) {
                objectFields.addAll(Arrays.asList(current.getDeclaredFields()));
                current = current.getSuperclass();
            }

            objectFields.forEach(field ->{
            		try {
                    field.setAccessible(true);

                    // Verificar que se este agregando la annotacion Loggable
                    if (field.getAnnotation(Loggable.class) != null) {
                        Object objectField = field.get(object);

                        String fieldValue;

                        if (objectField == null) {
                            fieldValue = NULL_VALUE;
                        } else if (objectField instanceof Enum) {
                            /*
							 * Verificar si la clase es de tipo ENUM. Si es ENUM,
							 * se imprime el ENUM stringValue
							 */
                            fieldValue = objectField.toString();
                        } else if (objectField.getClass().getPackage().getName().startsWith(BASE_PACKAGE_NAME)) {
                            fieldValue = getLoggerFields(objectField);
                        } else if (objectField instanceof Collection) {
                            if (isAssociatedSomethingLoaded(objectField)) {
                                fieldValue = OPEN_BRACKET + COLLECTION_NAME + KEY_VALUE_SEPERATOR + ((Collection<?>) objectField).size() + CLOSE_BRACKET;
                            } else {
                                fieldValue = OPEN_BRACKET + COLLECTION_NAME + KEY_VALUE_SEPERATOR + "not initialized" + CLOSE_BRACKET;
                            }
                        } else if (objectField instanceof Map) {
                            fieldValue = OPEN_BRACKET + COLLECTION_NAME + KEY_VALUE_SEPERATOR + ((Map<?, ?>) objectField).size() + CLOSE_BRACKET;
                        } else {
                            fieldValue = objectField.toString();
                        }

                        fields.append( OPEN_BRACKET + field.getName() + KEY_VALUE_SEPERATOR + fieldValue + CLOSE_BRACKET);
                    }
                } catch (Exception e) {
                    logger.error("[Error][LoggingUtil][getLoggerFields]Error: Se genero una excepcion con el campo: " + field.getName(), e);
                }
            });
        } catch (Exception e) {
            logger.error("[Error][LoggingUtil][getLoggerFields]Error: Error al obtener los campos.", e);
        }

        return fields.toString();
    }


    /**
     * This method is used to uniformly log any info messages.
     * The actual logging is done by the AuditLogHandler
     * 
     * @param str - nothing
     */
    public static void logInfo(String str) {
        //Nothing to do here
    }

    /**
     * This method is used to uniformly log any warn messages.
     * The actual logging is done by the AuditLogHandler
     * 
     * @param str - nothing
     */
    public static void logWarn(String str) {
        //Nothing to do here
    }

    /**
     * This method is used to uniformly log any debug messages.
     * The actual logging is done by the AuditLogHandler
     * 
     * @param str - nothing
     */
    public static void logDebug(String str) {
        //Nothing to do here
    }

    /**
     * This method is used to uniformly log any trace messages.
     * The actual logging is done by the AuditLogHandler
     * 
     * @param str - nothing
     */
    public static void logTrace(String str) {
        //Nothing to do here
    }

    /**
     * This method is used to uniformly log any exception messages.
     * The actual logging is done by the AuditLogHandler
     * 
     * @param debugMessage - nothing
     * @param t - nothing
     */
    public static void logException(String debugMessage, Throwable t) {
        //Nothing to do here
    }

    /**
     * This method is used to uniformly log any exception messages.
     * The actual logging is done by the AuditLogHandler
     * 
     * @param debugMessage - nothing
     */
    public static void logException(String debugMessage) {
        //Nothing to do here
    }

    /**
     * Check to see if associated hibernate object is initialized
     * This used to prevent the loading of objects that haven't been loaded
     * and are in the session, and to prevent causing exceptions when
     * the session is already closed.
     * 
     * @param object - nothing
     * @return true or false
     */
    private static boolean isAssociatedSomethingLoaded(Object object) {
        return Hibernate.isInitialized(object);
    }
}
