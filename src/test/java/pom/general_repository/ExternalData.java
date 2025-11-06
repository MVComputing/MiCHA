package pom.general_repository;

/**
 * Clase que centraliza las claves y rutas de configuración externa para los tests automatizados.
 * <p>
 * Contiene constantes que representan los nombres de propiedades dentro del archivo
 * JSON de configuración, como opciones del WebDriver, rutas de descargas, evidencias,
 * parámetros de ejecución en paralelo y entorno de pruebas.
 * <p>
 * Permite un acceso consistente a la configuración sin hardcodear strings en múltiples clases.
 */
public class ExternalData {
    /** Ruta al archivo JSON principal de configuración. */
    public static String ED_SOURCE = "./configuration/config.json";
    /** Clave de la sección de configuración del WebDriver en el JSON. */
    public static String ED_OBJECT_WEB_DRIVER_CONFIGURATION = "webDriverConfiguration";
    /** Clave para habilitar/deshabilitar ejecución headless. */
    public static String ED_HEADLESS = "headless";
    /** Clave para habilitar/deshabilitar reintentos automáticos. */
    public static String ED_RETRY = "retry";
    /** Clave para definir el número máximo de reintentos. */
    public static String ED_RETRY_COUNT = "retryCount";
    /** Clave para la carpeta de descargas configurada. */
    public static String ED_DOWNLOADS = "downloads";
    /** Clave para la carpeta de subidas configurada. */
    public static String ED_UPLOADS = "uploads";
    /** Clave para la carpeta donde se almacenan evidencias (screenshots, logs). */
    public static String ED_EVIDENCES = "evidences";
    /** Clave para habilitar ejecución de tests en paralelo. */
    public static String ED_PARALLEL = "parallel";
    /** Clave para definir el número de hilos en ejecución paralela. */
    public static String ED_THREAD_COUNT = "threadCount";
    /** Clave para definir el entorno de ejecución (DEV, QA, PROD). */
    public static String ED_ENVIRONMENT = "environment";
    /** Clave para definir el tamaño del navegador al iniciar el WebDriver. */
    public static String ED_BROWSER_SIZE = "browserSize";
    /** Clave para ocultar la ventana del WebDriver si aplica. */
    public static String ED_WEBDRIVER_HIDDEN = "webdriverHidden";
}
