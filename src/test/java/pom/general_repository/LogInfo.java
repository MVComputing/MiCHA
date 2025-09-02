package pom.general_repository;

public class LogInfo {
	public static final String LOG_WEBDRIVER_INIT = "Webdriver inicializado correctamente.";
	public static final String LOG_FIREFOX_CONNECTION = "Realizando conexión con Mozilla Firefox mediante webdriver";
	public static final String LOG_FIREFOX_CUSTOM_CONNECTION =
			"Realizando conexión con Mozilla Firefox mediante webdriver con perfil personalizado";
	public static final String LOG_CHROME_CONNECTION = "Realizando conexión con Google Chrome mediante webdriver";
	public static final String LOG_CHROME_CUSTOM_CONNECTION =
			"Realizando conexión con Google Chrome mediante webdriver con perfil personalizado";
	public static final String LOG_EDGE_CONNECTION = "Realizando conexión con Microsoft Edge mediante webdriver";
	public static final String LOG_EDGE_CUSTOM_CONNECTION =
			"Realizando conexión con Microsoft Edge mediante webdriver con perfil personalizado";
	public static final String LOG_FIND_ELEMENT = "Buscando elemento con el localizador: ";
	public static final String LOG_FIND_ELEMENT_SELECT = "Buscando elemento en dorpdownlist con el localizador: ";
	public static final String LOG_GET_TEXT_BY_LOCATOR = "Obteniendo texto con localizador: ";
	public static final String LOG_FIND_ELEMENT_LIST = "Obteniendo lista de elementos con el localizador: ";
	public static final String LOG_GET_TEXT_BY_ELEMENT = "Obteniendo texto con el elemento: ";
	public static final String LOG_SELECT_DROPDOWNLIST_ELEMENT_BY_VISIBLE_TEXT =
			"Seleccionando un elemento de un dropdownlist por el texto visible";
	public static final String LOG_SELECT_DROPDOWNLIST_ELEMENT_BY_VALUE =
			"Seleccionando un elemento de un dropdownlist por el valor";
	public static final String LOG_TYPE_TEXT = "Escribiendo el texto: ";
	public static final String LOG_TYPE_TEXT_AND_ENTER = "Pulsando 'ENTER' tras escribir el texto";
	public static final String LOG_CLEAR_TEXT = "Borrando texto en el localizador: ";
	public static final String LOG_VISIT_URL = "Redireccionando a la url: ";
	public static final String LOG_QUIT = "Cerrando el navegador ";
	public static final String LOG_CLICK_LOCATOR = "Hacer click en el elemento con localizador: ";
	public static final String LOG_CLICK_ELEMENT = "Hacer click en el elemento: ";
	public static final String LOG_VERTICAL_NAVIGATION = "Iniciando recorrido de la web de forma vertical";
	public static final String LOG_NAVIGATION_FINALIZED = "Recorrido finalizado";
	public static final String LOG_NAVIGATE_TO_X_AND_Y = "Desplazandose en la web con las coordenadas (x/y): ";
	public static final String LOG_GET_X = "Obteniendo coordenada X";
	public static final String LOG_GET_Y = "Obteniendo coordenada Y";
	public static final String LOG_SCREENSHOT_TAKE = "Realizando captura de pantalla";
	public static final String LOG_SCREENSHOT_NAME = "Nombre de la captura de pantalla: ";
	public static final String LOG_SCREENSHOT_SAVED = "Captura de pantalla almacenada en directorio: ";
	public static final String LOG_ELEMENT_DISPLAYED =
			"Validando si se encuentra desplegado el elemento con localizador: ";
	public static final String LOG_WAIT_FOR_ELEMENT_TO_BE_CLICKABLE =
			"Esperando a que se encuentre disponible y se pueda hacer click en el elemento con localizador: ";
	public static final String LOG_ELEMENT_CLICKED = "Elemento clickeado exitosamente en el intento: ";
	public static final String LOG_ERROR_ELEMENT_CLICKED = "Error al intentar interactuar con el elemento en intento ";
	public static final String LOG_ERROR_CANNOT_CLICK_ELEMENT = "No se pudo hacer clic en el elemento: ";
	public static final String LOG_IMPLICIT_WAIT =
			"Realizando una espera implicita de la siguiente cantidad de segundos: ";
	public static final String LOG_SEARCH_FOR_ELEMENT_WITH_VERTICAL_NAVIGATION =
			"Realizando desplazamiento vertical en la web para buscar el elemento: ";
	public static final String LOG_CLICK_WITH_JAVASCRIPT = "Haciendo click con Javascript sobre el elemento: ";
	public static final String LOG_GET_JSON_DATA_FROM_FILE = "Obteniendo dato desde archivo JSON en ruta: ";
	public static final String LOG_JSON_DATA_OBTAINED = "Dato conseguido: ";
	public static final String LOG_UPLOAD = "Realizando carga del archivo: ";
	public static final String LOG_DOWNLOAD_START = "Iniciando descarga de archivo";
	public static final String LOG_DOWNLOAD_FINISH = "Archivo descargado: ";
	public static final String LOG_CLEAR_DIRECTORY = "Realizando limpieza del directorio: ";
	public static final String LOG_ELEMENT_IS_PRESENT = "Validando si se encuentra presente en la web el elemento: ";
	public static final String LOG_ELEMENT_TEXT_IS_PRESENT = "Validando si se encuentra presente en la web el texto: ";
	public static final String LOG_GET_DATE = "Obteniendo fecha actual";
	public static final String LOG_GENERATE_DIRECTORY = "Se genera el directorio de evidencias: ";
	public static final String LOG_LINK_VALIDATION_START = "Iniciando validación presentes en pantalla";
	public static final String LOG_LINK_VALIDATION_FOUND = "Se ha encontrado el siguiente enlace: ";
	public static final String LOG_LINK_VALIDATION_NULL = "El siguiente enlace se encuentra vacío: ";
	public static final String LOG_LINK_VALIDATION_ESPECIAL = "Verificando si es un enlace especial";
	public static final String LOG_LINK_VALIDATION_NORMAL = "El enlace es común, se validara su respuesta";
	public static final String LOG_LINK_VALIDATION_BROKEN = "El siguiente enlace se encuentra roto: ";
	public static final String LOG_LINK_VALIDATION_OK = "El siguiente enlace se encuentra OK: ";
	public static final String LOG_LINK_VALIDATION_IS_ESPECIAL = "El enlace es especial, se validara su respuesta";
	public static final String LOG_LINK_VALIDATION_TOTAL = "Total enlaces: ";
	public static final String LOG_LINK_VALIDATION_TOTAL_OK = "Total enlaces OK: ";
	public static final String LOG_LINK_VALIDATION_TOTAL_BROKEN = "Total enlaces Rotos: ";
	public static final String LOG_LINK_VALIDATION_TOTAL_NULL = "Total enlaces NULOS: ";
	public static final String LOG_LINK_VALIDATION_SERVER_DOWN = "Total enlaces caídos por servidor: ";
	public static final String LOG_LINK_VALIDATION_BROKEN_LIST = "Lista de los enlaces Rotos: ";
	public static final String LOG_LINK_VALIDATION_NULL_LIST = "Lista de los enlaces Nulos: ";
	public static final String LOG_LINK_VALIDATION_SERVER_DOWN_LIST = "Lista de los enlaces caídos por servidor: ";
	public static final String LOG_CHROME_CONFIGURATION = "Aplicando configuración al navegador Google Chrome";
	public static final String LOG_EDGE_CONFIGURATION = "Aplicando configuración al navegador Microsoft Edge";
	public static final String LOG_FIREFOX_CONFIGURATION = "Aplicando configuración al navegador Mozilla Firefox";
	public static final String LOG_SEPARATE = "--------------------------------------------------------------------";
	public static final String LOG_DYNAMIC_ELEMENT_ID_OBTAINED = "Se ha conseguido el id: ";
	public static final String LOG_DYNAMIC_LIST_BOX_ID = "Se configura listbox con id: ";
	public static final String LOG_DYNAMIC_OPTION = "Se configura opción con id: ";
	public static final String LOG_BEFORE_CLASS = "Iniciando configuración de navegador: ";
	public static final String LOG_TEST_START = "Iniciando prueba: ";

	public static final String LOG_DOWNLOAD_ERROR_DYNAMIC_INTERRUPTION =
			"Interrupción durante la espera dinámica de descarga: ";
	public static final String LOG_DOWNLOAD_ERROR_INTERRUPTED_WAITING = "Espera de descarga interrumpida.";
	public static final String LOG_DOWNLOAD_ERROR_CREATE_EVIDENCE_DIRECTORY = "Error creando directorio de evidencias: ";
	public static final String LOG_DOWNLOAD_ERROR_FILES_NOT_FOUND = "No se encontraron archivos descargados en: ";
	public static final String LOG_DOWNLOAD_ERROR_TYPE_FILES_NOT_FOUND = "No se descargaron archivos de tipo ";
	public static final String LOG_DOWNLOAD_ERROR_MOVE_FILES = "Error moviendo o adjuntando el archivo: ";
	public static final String LOG_DOWNLOAD_ERROR_TIMEOUT = "Timeout esperando descarga del archivo en: ";

}
