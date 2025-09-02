package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase utilitaria para el manejo de registros de log en la aplicación.
 *
 * Esta clase proporciona métodos estáticos para registrar mensajes
 * de información, advertencia, error y depuración utilizando Log4j.
 */
public class Log {
	/**
	 * Instancia del logger utilizado para registrar los mensajes.
	 */
	private static final Logger logger = LogManager.getLogger();

	/**
	 * Registra un mensaje de tipo informativo.
	 *
	 * @param message El mensaje informativo a registrar.
	 */
	public static void info(String message) {
		logger.info(message);
	}

	/**
	 * Registra un mensaje de advertencia.
	 *
	 * @param message El mensaje de advertencia a registrar.
	 */
	public static void warn(String message) {
		logger.warn(message);
	}

	/**
	 * Registra un mensaje de error.
	 *
	 * @param message El mensaje de error a registrar.
	 */
	public static void error(String message) {
		logger.error(message);
	}

	/**
	 * Registra un mensaje de depuración.
	 *
	 * @param message El mensaje de depuración a registrar.
	 */
	public static void debug(String message) {
		logger.debug(message);
	}
}
