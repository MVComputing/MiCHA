package pom;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.restassured.AllureRestAssured;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.*;

import io.qameta.allure.Allure;
import org.testng.Assert;
import pom.general_repository.*;
import utils.Log;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

/**
 * Clase base que contiene todas las funciones y métodos a utilizar en la automatización.
 */
public class Base {

    /**
     * Driver utilizado para interactuar con los navegadores web durante la ejecución de las pruebas.
     *
     * <p>
     * Este objeto es una instancia del WebDriver que controla el navegador subyacente.
     * Se inicializa en métodos específicos y se utiliza para realizar operaciones como navegar,
     * encontrar elementos y automatizar interacciones en páginas web.
     * </p>
     */
    public WebDriver driver;

    /**
     * Fecha actual utilizada en el programa.
     * Esta variable estática puede ser compartida entre todas las instancias de la clase.
     * Es una representación en formato de cadena de caracteres (String).
     *
     * <p>Ejemplo de uso:</p>
     * <pre>
     * {@code
     * Base.date = "2025-03-31";
     * System.out.println(Base.date);
     * }
     * </pre>
     */
    public static String date;

    /**
     * Nombre del caso de prueba en ejecución.
     * Esta variable estática almacena el nombre del test actual para facilitar
     * la identificación y el seguimiento durante la ejecución de las pruebas automatizadas.
     *
     * <p>Ejemplo de uso:</p>
     * <pre>
     * {@code
     * testName.set("Nombre de la prueba");
     * System.out.println("Ejecutando: " + testName.get());
     * }
     * </pre>
     */
    public static ThreadLocal<String> testName = ThreadLocal.withInitial(() -> "Default Test Name");

    /**
     * Nombre del navegador utilizado para la ejecución de pruebas automatizadas.
     * Esta variable estática permite identificar qué navegador está en uso
     * (por ejemplo, Chrome, Firefox o Edge) y facilita la configuración dinámica
     * basada en el navegador seleccionado.
     *
     * <p>Ejemplo de uso:</p>
     * <pre>
     * {@code
     * Base.browser = "Firefox";
     * System.out.println("Navegador en uso: " + Base.browser);
     * }
     * </pre>
     */
    public static String browser;

    /**
     * Constructor de la clase Base.
     * Inicializa el controlador WebDriver utilizado para la automatización de pruebas.
     *
     * @param driver Instancia de {@link WebDriver} que controla el navegador en uso.
     *               <p>
     *               Ejemplo de uso:
     *               <pre>
     *                                           {@code
     *                                           WebDriver driver = new FirefoxDriver();
     *                                           Base baseInstance = new Base(driver);
     *                                           }
     *                                           </pre>
     */
    public Base(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Establece la conexión con el WebDriver de Firefox.
     * <p>
     * Este método inicializa un nuevo objeto {@link FirefoxDriver}.
     * Si la inicialización falla por alguna razón, el método captura las
     * excepciones relevantes y registra el error correspondiente.
     * En caso de que no se pueda crear el driver o se capture una
     * {@link NullPointerException}, el método finaliza la sesión del driver.
     * </p>
     *
     * @throws AssertionError si no se puede crear el driver o si ocurre una excepción.
     *                        <p>
     *                        Registro de excepciones manejadas:
     *                        <ul>
     *                          <li>{@link SessionNotCreatedException}: Error al crear la sesión del driver.</li>
     *                          <li>{@link NullPointerException}: Error inesperado al intentar inicializar el driver.</li>
     *                        </ul>
     */
    public void firefoxDriverConnection() {
        Log.info(LogInfo.LOG_FIREFOX_CONNECTION);

        try {
            driver = new FirefoxDriver();

            Assert.assertNotNull(driver, AssertInfo.ASSERT_DRIVER_NOT_NULL);
            Log.info(LogInfo.LOG_WEBDRIVER_INIT);
        } catch (SessionNotCreatedException sessionNotCreatedException) {
            Log.error(sessionNotCreatedException.getMessage());
            Assert.fail(AssertInfo.ASSERT_DRIVER_SESSION_NOT_CREATED +
                    sessionNotCreatedException.getMessage());
            quitDriver();
        } catch (NullPointerException nullPointerException) {
            Log.error(nullPointerException.getMessage());
            Assert.fail(AssertInfo.ASSERT_DRIVER_NULL_POINTER +
                    nullPointerException.getMessage());
            quitDriver();
        }
    }

    /**
     * Establece la conexión personalizada con el WebDriver de Firefox usando {@link FirefoxOptions}.
     * <p>
     * Este método permite inicializar un nuevo objeto {@link FirefoxDriver}
     * con configuraciones específicas proporcionadas en un objeto de {@link FirefoxOptions}.
     * En caso de que ocurran errores durante la inicialización, se manejan las excepciones y se registra
     * el mensaje de error correspondiente. Si no se puede crear el driver o se captura una
     * {@link NullPointerException}, el método finaliza la sesión del driver.
     * </p>
     *
     * @param options Configuraciones personalizadas para el {@link FirefoxDriver}.
     * @throws AssertionError si no se puede crear el driver o si ocurre una excepción.
     *                        <p>
     *                        Registro de excepciones manejadas:
     *                        <ul>
     *                          <li>{@link SessionNotCreatedException}: Error al crear la sesión del driver.</li>
     *                          <li>{@link NullPointerException}: Error inesperado durante la inicialización del driver.</li>
     *                        </ul>
     */
    public void firefoxDriverConnectionOptions(FirefoxOptions options) {
        Log.info(LogInfo.LOG_FIREFOX_CUSTOM_CONNECTION);

        try {
            driver = new FirefoxDriver(options);
            Assert.assertNotNull(driver, AssertInfo.ASSERT_DRIVER_NOT_NULL);
            Log.info(LogInfo.LOG_WEBDRIVER_INIT);
        } catch (SessionNotCreatedException sessionNotCreatedException) {
            Log.error(sessionNotCreatedException.getMessage());
            Assert.fail(AssertInfo.ASSERT_DRIVER_SESSION_NOT_CREATED +
                    sessionNotCreatedException.getMessage());
            quitDriver();
        } catch (NullPointerException nullPointerException) {
            Log.error(nullPointerException.getMessage());
            Assert.fail(AssertInfo.ASSERT_DRIVER_NULL_POINTER +
                    nullPointerException.getMessage());
            quitDriver();
        }
    }

    /**
     * Establece la conexión con el WebDriver de Chrome.
     * <p>
     * Este método inicializa un nuevo objeto {@link ChromeDriver}.
     * Si la inicialización falla, captura las excepciones relevantes, registra el error
     * correspondiente y, si es necesario, finaliza la sesión del driver.
     * </p>
     *
     * @throws AssertionError si no se puede crear el driver o si ocurre una excepción.
     *                        <p>
     *                        Registro de excepciones manejadas:
     *                        <ul>
     *                          <li>{@link SessionNotCreatedException}: Error al crear la sesión del driver.</li>
     *                          <li>{@link NullPointerException}: Error inesperado durante la inicialización del driver.</li>
     *                        </ul>
     */
    public void chromeDriverConnection() {
        Log.info(LogInfo.LOG_CHROME_CONNECTION);

        try {
            driver = new ChromeDriver();
            Assert.assertNotNull(driver, AssertInfo.ASSERT_DRIVER_NOT_NULL);
            Log.info(LogInfo.LOG_WEBDRIVER_INIT);
        } catch (SessionNotCreatedException sessionNotCreatedException) {
            Log.error(sessionNotCreatedException.getMessage());
            Assert.fail(AssertInfo.ASSERT_DRIVER_SESSION_NOT_CREATED +
                    sessionNotCreatedException.getMessage());
            quitDriver();
        } catch (NullPointerException nullPointerException) {
            Log.error(nullPointerException.getMessage());
            Assert.fail(AssertInfo.ASSERT_DRIVER_NULL_POINTER +
                    nullPointerException.getMessage());
            quitDriver();
        }
    }

    /**
     * Establece la conexión personalizada con el WebDriver de Chrome utilizando {@link ChromeOptions}.
     * <p>
     * Este método inicializa un nuevo objeto {@link ChromeDriver} con las opciones específicas
     * proporcionadas en un objeto de {@link ChromeOptions}.
     * Si la inicialización falla, se manejan las excepciones, se registra el error correspondiente,
     * y el método finaliza la sesión del driver si es necesario.
     * </p>
     *
     * @param options Configuraciones personalizadas para el {@link ChromeDriver}.
     * @throws AssertionError si no se puede crear el driver o si ocurre una excepción.
     *                        <p>
     *                        Registro de excepciones manejadas:
     *                        <ul>
     *                          <li>{@link SessionNotCreatedException}: Error al crear la sesión del driver.</li>
     *                          <li>{@link NullPointerException}: Error inesperado durante la inicialización del driver.</li>
     *                        </ul>
     */
    public void chromeDriverConnectionOptions(ChromeOptions options) {
        Log.info(LogInfo.LOG_CHROME_CUSTOM_CONNECTION);

        try {
            driver = new ChromeDriver(options);
            Assert.assertNotNull(driver, AssertInfo.ASSERT_DRIVER_NOT_NULL);
            Log.info(LogInfo.LOG_WEBDRIVER_INIT);
        } catch (SessionNotCreatedException sessionNotCreatedException) {
            Log.error(sessionNotCreatedException.getMessage());
            Assert.fail(AssertInfo.ASSERT_DRIVER_SESSION_NOT_CREATED +
                    sessionNotCreatedException.getMessage());
            quitDriver();
        } catch (NullPointerException nullPointerException) {
            Log.error(nullPointerException.getMessage());
            Assert.fail(AssertInfo.ASSERT_DRIVER_NULL_POINTER +
                    nullPointerException.getMessage());
            quitDriver();
        }
    }

    /**
     * Establece la conexión con el WebDriver de Microsoft Edge.
     * <p>
     * Este método inicializa un nuevo objeto {@link EdgeDriver}.
     * Si la inicialización falla, captura las excepciones relevantes, registra el error correspondiente
     * y, si es necesario, finaliza la sesión del driver.
     * </p>
     *
     * @throws AssertionError si no se puede crear el driver o si ocurre una excepción.
     *                        <p>
     *                        Registro de excepciones manejadas:
     *                        <ul>
     *                          <li>{@link SessionNotCreatedException}: Error al crear la sesión del driver.</li>
     *                          <li>{@link NullPointerException}: Error inesperado durante la inicialización del driver.</li>
     *                        </ul>
     */
    public void edgeConnection() {
        Log.info(LogInfo.LOG_EDGE_CONNECTION);

        try {
            driver = new EdgeDriver();
            Assert.assertNotNull(driver, AssertInfo.ASSERT_DRIVER_NOT_NULL);
            Log.info(LogInfo.LOG_WEBDRIVER_INIT);
        } catch (SessionNotCreatedException sessionNotCreatedException) {
            Log.error(sessionNotCreatedException.getMessage());
            Assert.fail(AssertInfo.ASSERT_DRIVER_SESSION_NOT_CREATED +
                    sessionNotCreatedException.getMessage());
            quitDriver();
        } catch (NullPointerException nullPointerException) {
            Log.error(nullPointerException.getMessage());
            Assert.fail(AssertInfo.ASSERT_DRIVER_NULL_POINTER +
                    nullPointerException.getMessage());
            quitDriver();
        }
    }

    /**
     * Establece la conexión personalizada con el WebDriver de Microsoft Edge utilizando {@link EdgeOptions}.
     * <p>
     * Este método inicializa un nuevo objeto {@link EdgeDriver} con opciones específicas
     * proporcionadas mediante un objeto de {@link EdgeOptions}.
     * Si ocurre un error durante la inicialización del driver, se captura la excepción correspondiente,
     * se registra el mensaje de error y, si es necesario, se finaliza la sesión del driver.
     * </p>
     *
     * @param options Configuraciones personalizadas para el {@link EdgeDriver}.
     * @throws AssertionError si no se puede crear el driver o si ocurre una excepción.
     *                        <p>
     *                        Registro de excepciones manejadas:
     *                        <ul>
     *                          <li>{@link SessionNotCreatedException}: Error al crear la sesión del driver.</li>
     *                          <li>{@link NullPointerException}: Error inesperado durante la inicialización del driver.</li>
     *                        </ul>
     */
    public void edgeConnectionOptions(EdgeOptions options) {
        Log.info(LogInfo.LOG_EDGE_CUSTOM_CONNECTION);

        try {
            driver = new EdgeDriver(options);
            Assert.assertNotNull(driver, AssertInfo.ASSERT_DRIVER_NOT_NULL);
            Log.info(LogInfo.LOG_WEBDRIVER_INIT);
        } catch (SessionNotCreatedException sessionNotCreatedException) {
            Log.error(sessionNotCreatedException.getMessage());
            Assert.fail(AssertInfo.ASSERT_DRIVER_SESSION_NOT_CREATED +
                    sessionNotCreatedException.getMessage());
            quitDriver();
        } catch (NullPointerException nullPointerException) {
            Log.error(nullPointerException.getMessage());
            Assert.fail(AssertInfo.ASSERT_DRIVER_NULL_POINTER +
                    nullPointerException.getMessage());
            quitDriver();
        }
    }

    /**
     * Retorna un elemento de la web.
     *
     * @param locator localizador de tipo By del elemento que se quiere encontrar.
     * @return Este método retorna una instancia de WebElement, siempre que el elemento exista y pase las
     * verificaciones.
     * Si el elemento no se encuentra o no es válido, el método genera una excepción antes de retornar.
     **/
    public WebElement findElement(By locator) {
        // Encontrar el elemento usando el driver
        WebElement element = this.driver.findElement(locator);

        Log.info(LogInfo.LOG_FIND_ELEMENT + locator);
        // Validar que el elemento no sea null
        Assert.assertNotNull(element, AssertInfo.ASSERT_ELEMENT_NOT_NULL + locator);

        // Validar que el elemento esté visible
        //Assert.assertTrue(element.isDisplayed(), "Error: El elemento encontrado no está visible en el DOM.");

        return element;
    }

    /**
     * Retorna un elemento de la web como tipo Select, comúnmente utilizado para dropdowns.
     *
     * @param locator localizador de tipo By del elemento que se quiere encontrar.
     * @return Este método devuelve una instancia de la clase Select, construida usando el elemento localizado por el
     * método findElement.
     */
    public Select findElementSelect(By locator) {
        Log.info(LogInfo.LOG_FIND_ELEMENT_SELECT + locator);
        return new Select(findElement(locator));
    }

    /**
     * Obtiene el texto de un elemento web especificado por el localizador.
     * Este método busca el elemento utilizando el localizador proporcionado
     * y devuelve el texto contenido en dicho elemento.
     *
     * @param locator el localizador del elemento web (por ejemplo, un By.xpath o By.id).
     * @return el texto del elemento web encontrado, o una cadena vacía si no hay texto.
     */
    public String getTextByLocator(By locator) {
        Log.info(LogInfo.LOG_GET_TEXT_BY_LOCATOR + locator);
        return findElement(locator).getText();
    }

    public boolean elementEnabled(By locator) {
        Log.info(LogInfo.LOG_GET_TEXT_BY_LOCATOR + locator);
        return findElement(locator).isEnabled();
    }

    /**
     * Obtiene el texto de un elemento web especificado.
     * Este método registra información sobre el elemento proporcionado
     * y devuelve el texto contenido en dicho elemento.
     *
     * @param element el elemento web del cual se obtendrá el texto.
     * @return el texto del elemento web encontrado, o una cadena vacía si no hay texto.
     */
    public String getTextByWebElement(WebElement element) {
        Log.info(LogInfo.LOG_GET_TEXT_BY_ELEMENT + element);
        return element.getText();
    }

    /**
     * Encuentra una lista de elementos web utilizando un localizador específico.
     * Este método registra información sobre el localizador proporcionado
     * y devuelve una lista de elementos web encontrados en la página.
     *
     * @param locator el localizador utilizado para encontrar los elementos web
     *                (por ejemplo, By.xpath, By.id, etc.).
     * @return una lista de elementos web que coinciden con el localizador.
     * Si no se encuentran elementos, devuelve una lista vacía.
     */
    public List<WebElement> findElements(By locator) {
        Log.info(LogInfo.LOG_FIND_ELEMENT_LIST + locator);
        return this.driver.findElements(locator);
    }

    /**
     * Selecciona una opción en un elemento desplegable (Select) utilizando el texto visible de la opción.
     * Este método registra información relevante sobre la selección realizada y utiliza el localizador
     * para identificar el elemento desplegable en la página.
     *
     * @param locator   el localizador del elemento desplegable (por ejemplo, By.xpath, By.id, etc.).
     * @param seleccion el texto visible de la opción que se desea seleccionar.
     */
    public void selectElementSelectByVisibleText(By locator, String seleccion) {
        Log.info(LogInfo.LOG_SELECT_DROPDOWNLIST_ELEMENT_BY_VISIBLE_TEXT);
        Select select = new Select(findElement(locator));
        select.selectByVisibleText(seleccion);
    }

    /**
     * Selecciona una opción en un elemento desplegable (Select) utilizando el valor de la opción.
     * Este método registra información relevante sobre el localizador y las opciones seleccionadas
     * antes de realizar la selección, asegurando un seguimiento detallado del proceso.
     *
     * @param locator   el localizador del elemento desplegable (por ejemplo, By.xpath, By.id, etc.).
     * @param selection el valor de la opción que se desea seleccionar.
     */
    public void selectElementSelectByValue(By locator, String selection) {
        Log.info(LogInfo.LOG_SELECT_DROPDOWNLIST_ELEMENT_BY_VALUE);
        Select select = new Select(findElement(locator));
        Log.info(select.getAllSelectedOptions().toString());
        select.selectByValue(selection);
    }

    /**
     * Pausa la ejecución del programa durante 1 segundo.
     * <p>
     * Este método utiliza {@code Thread.sleep()} para suspender el
     * hilo actual por 1000 milisegundos. En caso de que se interrumpa
     * el hilo mientras está en espera, se captura la excepción
     * {@link InterruptedException} y se imprime el stack trace.
     */
    public void threadWait(Long milliSeconds) {
        try {
            Thread.sleep(milliSeconds); // Espera de 1 segundo
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Envía un texto de entrada a un elemento web localizado por el localizador especificado.
     * Este método registra el texto que se enviará y utiliza el localizador proporcionado
     * para encontrar el elemento en el que se ingresará el texto.
     *
     * @param inputText el texto que se desea escribir en el elemento web.
     * @param locator   el localizador del elemento web donde se ingresará el texto
     *                  (por ejemplo, By.xpath, By.id, etc.).
     */
    public void sendInputText(String inputText, By locator) {
        Log.info(LogInfo.LOG_TYPE_TEXT + inputText);
        findElement(locator).sendKeys(inputText);
    }

    /**
     * Envía texto de entrada a un elemento web y simula la acción de presionar la tecla Enter.
     * Este método utiliza un localizador para identificar el elemento, registra información
     * sobre las acciones realizadas y combina el envío del texto con la simulación de la tecla Enter.
     *
     * @param inputText el texto que se desea escribir en el elemento web.
     * @param locator   el localizador del elemento web donde se ingresará el texto
     *                  (por ejemplo, By.xpath, By.id, etc.).
     */
    public void sendInputTextAndKeyEnter(String inputText, By locator) {
        //encontrarElemento(locator).sendKeys(inputText);
        sendInputText(inputText, locator);
        Log.info(LogInfo.LOG_TYPE_TEXT_AND_ENTER);
        findElement(locator).sendKeys(Keys.ENTER);
    }

    /**
     * Envía texto de entrada a un elemento web y simula la acción de presionar la tecla Abajo y luego
     * la tecla Enter.
     * Este método utiliza un localizador para identificar el elemento, registra información
     * sobre las acciones realizadas y combina el envío del texto con la simulación de la tecla Enter.
     *
     * @param inputText el texto que se desea escribir en el elemento web.
     * @param locator   el localizador del elemento web donde se ingresará el texto
     *                  (por ejemplo, By.xpath, By.id, etc.).
     */
    public void sendInputTextAndKeysDownEnter(String inputText, By locator) {
        //encontrarElemento(locator).sendKeys(inputText);
        sendInputText(inputText, locator);
        Log.info(LogInfo.LOG_TYPE_TEXT_AND_ENTER);
        findElement(locator).sendKeys(Keys.DOWN);
        findElement(locator).sendKeys(Keys.ENTER);
    }

    /**
     * Limpia el contenido de un campo de texto ubicado por el localizador especificado.
     * Este método registra información sobre el campo de texto y utiliza el localizador
     * para encontrar el elemento correspondiente, eliminando cualquier texto presente en él.
     *
     * @param locator el localizador del campo de texto a limpiar
     *                (por ejemplo, By.xpath, By.id, etc.).
     */
    public void clearText(By locator) {
        Log.info(LogInfo.LOG_CLEAR_TEXT + locator);
        findElement(locator).clear();
    }

    /**
     * Selecciona y limpia todo el contenido de un campo de texto ubicado por el localizador especificado'.
     * Este método registra información sobre el campo de texto y utiliza el localizador
     * para encontrar el elemento correspondiente, eliminando cualquier texto presente en él.
     *
     * @param locator el localizador del campo de texto a limpiar
     *                (por ejemplo, By.xpath, By.id, etc.).
     */
    public void selectAndClearAllText(By locator) {
        findElement(locator).sendKeys(Keys.CONTROL, "a");
        findElement(locator).sendKeys(Keys.BACK_SPACE);
    }

    /**
     * Navega a una URL específica en el navegador controlado por WebDriver.
     * Este método registra la información de la URL proporcionada y utiliza el controlador
     * de WebDriver para cargar la página correspondiente.
     *
     * @param url la dirección URL de la página web que se desea visitar.
     */
    public void visitUrl(String url) {
        Log.info(LogInfo.LOG_VISIT_URL + url);
        this.driver.get(url);
    }

    /**
     * Cierra y finaliza la sesión del navegador controlado por WebDriver.
     * Este método registra información relevante sobre el navegador en uso
     * y asegura que los recursos asociados al controlador sean liberados adecuadamente.
     */
    public void quitDriver() {
        Log.info(LogInfo.LOG_QUIT + Base.browser);
        Log.info(LogInfo.LOG_SEPARATE);
        this.driver.quit();
    }

    /**
     * Realiza un clic en un elemento web localizado por el localizador especificado.
     * Este método registra información sobre el localizador proporcionado y busca
     * el elemento web para ejecutar la acción de clic.
     *
     * @param locator el localizador del elemento web en el que se desea hacer clic
     *                (por ejemplo, By.xpath, By.id, etc.).
     */
    public void clickLocator(By locator) {
        Log.info(LogInfo.LOG_CLICK_LOCATOR + locator);
        findElement(locator).click();
    }

    /**
     * Realiza un clic en un elemento web especificado.
     * Este método registra información sobre el elemento web proporcionado
     * y ejecuta la acción de clic en él.
     *
     * @param webElement el elemento web en el que se desea hacer clic.
     */
    public void clickWebElement(WebElement webElement) {
        Log.info(LogInfo.LOG_CLICK_ELEMENT + webElement);
        webElement.click();
    }

    /**
     * Navega por una página web simulando un desplazamiento vertical desde un punto inicial
     * hasta un punto final, tomando capturas de pantalla en cada paso.
     * Este método registra información al inicio y al final del proceso, y utiliza los puntos
     * definidos para calcular los pasos de navegación y desplazarse automáticamente.
     *
     * @param start  el localizador del elemento web que define el punto inicial del desplazamiento.
     * @param finish el localizador del elemento web que define el punto final del desplazamiento.
     */
    public void verticalWebNavigation(By start, By finish) throws IOException {
        Log.info(LogInfo.LOG_VERTICAL_NAVIGATION);
        int ini = getY(start);
        int ter = getY(finish);

        do {
            navigate(0, ini);
            screenShot();
            ini += Data.DATA_NAVIGATE_Y;
        }
        while (ini <= ter);

        Log.info(LogInfo.LOG_NAVIGATION_FINALIZED);
    }

    /**
     * Realiza un desplazamiento en la página web con las coordenadas especificadas utilizando JavaScript.
     * Este método registra información sobre las coordenadas de desplazamiento y ejecuta un script
     * de JavaScript para mover la vista en la página.
     *
     * @param x la posición horizontal a la que se desea desplazarse.
     * @param y la posición vertical a la que se desea desplazarse.
     */
    public void navigate(int x, int y) {
        Log.info(LogInfo.LOG_NAVIGATE_TO_X_AND_Y + x + "/" + y);
        JavascriptExecutor jse = (JavascriptExecutor) this.driver;
        jse.executeScript("scroll(" + x + ", " + y + ");");
    }

    /**
     * Obtiene la posición horizontal de un elemento web especificado por el localizador.
     * Este método registra información sobre el elemento y utiliza su posición en la página para
     * devolver la coordenada horizontal.
     *
     * @param locator el localizador del elemento web (por ejemplo, By.xpath, By.id, etc.).
     * @return la coordenada X del elemento web encontrado.
     */
    public int getX(By locator) {
        Log.info(LogInfo.LOG_GET_X);
        WebElement element = findElement(locator);
        Point classname = element.getLocation();
        return classname.getX();
    }

    /**
     * Obtiene la posición vertical de un elemento web especificado por el localizador.
     * Este método registra información sobre el elemento y utiliza su posición en la página para
     * devolver la coordenada vertical.
     *
     * @param locator el localizador del elemento web (por ejemplo, By.xpath, By.id, etc.).
     * @return la coordenada Y del elemento web encontrado.
     */
    public int getY(By locator) {
        Log.info(LogInfo.LOG_GET_Y);
        WebElement element = findElement(locator);
        Point classname = element.getLocation();
        return classname.getY();
    }

    /**
     * Realiza una captura de pantalla de la página actual y la almacena en una ubicación específica.
     * Este método registra información sobre el inicio y fin del proceso de captura, crea las
     * carpetas necesarias para almacenar la evidencia, y adjunta la captura de pantalla a un informe
     * de Allure para propósitos de trazabilidad.
     * En caso de errores, registra el error y marca la prueba como fallida.
     */
    public void screenShot2() {
        Log.info(LogInfo.LOG_SCREENSHOT_TAKE);
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.createDirectories(Paths.get(getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION, ExternalData.ED_EVIDENCES, ExternalData.ED_SOURCE) + generateFolderName()));

            FileUtils.copyFileToDirectory(screenshot, new File(getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION, ExternalData.ED_EVIDENCES, ExternalData.ED_SOURCE) + generateFolderName()));
            String name = screenshot.getName();
            Log.info(LogInfo.LOG_SCREENSHOT_NAME + name);
            InputStream is = Files.newInputStream(Paths.get(getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION, ExternalData.ED_EVIDENCES, ExternalData.ED_SOURCE) + generateFolderName() + "/" + name));
            Log.info(LogInfo.LOG_SCREENSHOT_SAVED + getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION, ExternalData.ED_EVIDENCES, ExternalData.ED_SOURCE) + generateFolderName() + "/" + name);
            Allure.attachment(Data.DATA_ALLURE_EVIDENCE + generateFolderName(), is);
        } catch (Exception e) {
            Log.error(e.toString());
            fail(e.toString());
        }
    }

    public void screenShot() throws IOException {
        //Log.info(LogInfo.LOG_SCREENSHOT_TAKE);

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String folderName = generateFolderName();
        String basePath = getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_EVIDENCES,
                ExternalData.ED_SOURCE);
        Path targetDir = Paths.get(basePath + folderName);

        try {
            Files.createDirectories(targetDir);
            FileUtils.copyFileToDirectory(screenshot, targetDir.toFile());

            String name = screenshot.getName();
            //Log.info(LogInfo.LOG_SCREENSHOT_NAME + name);

            Path screenshotPath = targetDir.resolve(name);

            try (InputStream is = Files.newInputStream(screenshotPath)) {
                Log.info(LogInfo.LOG_SCREENSHOT_SAVED + screenshotPath);
                Allure.attachment(Data.DATA_ALLURE_EVIDENCE + folderName, is);
            } catch (NoSuchFileException nsfe) {
                Log.error("No se encontró el archivo: " + screenshotPath);
                fail("Archivo no encontrado para adjuntar: " + nsfe.getMessage());
            }

        } catch (Exception e) {
            Log.error("Error al tomar screenshot: " + e.toString());
            fail("Excepción general: " + e.toString());
        }
    }

    /**
     * Verifica si un elemento web especificado por el localizador está visible en la página.
     * Este método registra información sobre el elemento proporcionado y maneja posibles
     * excepciones, devolviendo false si ocurre algún error durante la verificación.
     *
     * @param locator el localizador del elemento web (por ejemplo, By.xpath, By.id, etc.).
     * @return true si el elemento está visible, false en caso contrario o si ocurre una excepción.
     */
    public boolean elementDisplayedByLocator(By locator) {
        try {
            Log.info(LogInfo.LOG_ELEMENT_DISPLAYED + locator);
            return findElement(locator).isDisplayed();
        } catch (Exception e) {
            Log.warn(e.toString());
            return false;
        }
    }

    public boolean elementIsVisibleByLocator(By locator) {
        Log.info(LogInfo.LOG_ELEMENT_DISPLAYED + locator);
        boolean visible = !findElements(locator).isEmpty() &&
                findElement(locator).isDisplayed();
        return visible;
    }

    /**
     * Verifica si un elemento web está visible en la página.
     * Este método registra información sobre el elemento proporcionado y maneja posibles excepciones.
     * Si ocurre un error durante la verificación, se registra el error y la prueba se marca como fallida.
     *
     * @param element el elemento web que se desea verificar.
     * @return true si el elemento está visible; false si ocurre una excepción.
     */
    public boolean elementDisplayedByWebElement(WebElement element) {
        try {
            Log.info(LogInfo.LOG_ELEMENT_DISPLAYED + element);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Espera a que un elemento web especificado por el localizador esté disponible para ser clicado.
     * Este método utiliza una espera explícita para verificar la disponibilidad del elemento,
     * realiza múltiples intentos en caso de excepciones, y toma capturas de pantalla durante el proceso
     * para asegurar trazabilidad. En caso de error, registra los detalles y marca la prueba como fallida.
     *
     * @param locator el localizador del elemento web que se espera que sea clicable
     *                (por ejemplo, By.xpath, By.id, etc.).
     */
    public void waitForElementToBeClickable(By locator) throws IOException {
        final int MAX_ATTEMPTS = 2;
        final int TIMEOUT_SECONDS = 15;

        Log.info(LogInfo.LOG_WAIT_FOR_ELEMENT_TO_BE_CLICKABLE + locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));

        int attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

                element.click();
                Log.info(LogInfo.LOG_ELEMENT_CLICKED + (attempts + 1));
                return;
            } catch (StaleElementReferenceException | ElementClickInterceptedException | TimeoutException e) {
                Log.warn(LogInfo.LOG_ERROR_ELEMENT_CLICKED + (attempts + 1));
            }

            attempts++;
            waitShortBetweenAttempts();
        }

        Log.error(LogInfo.LOG_ERROR_CANNOT_CLICK_ELEMENT + locator);
        screenShot();
        fail(Mensajes.MENSAJE_ERROR_ELEMENTO_NO_CLICKEABLE + locator);
    }

    private void waitShortBetweenAttempts() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Espera a que un elemento web especificado por el localizador esté disponible para ser clicado
     * utilizando javascript.
     * Este método utiliza una espera explícita para verificar la disponibilidad del elemento,
     * realiza múltiples intentos en caso de excepciones, y toma capturas de pantalla durante el proceso
     * para asegurar trazabilidad. En caso de error, registra los detalles y marca la prueba como fallida.
     *
     * @param locator el localizador del elemento web que se espera que sea clicable
     *                (por ejemplo, By.xpath, By.id, etc.).
     */
    public void waitForElementToBeClickableJavascript(By locator) {
        final int MAX_ATTEMPTS = 2;
        final int TIMEOUT_SECONDS = 15;

        Log.info(LogInfo.LOG_WAIT_FOR_ELEMENT_TO_BE_CLICKABLE + locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));

        int attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

                element.click();
                Log.info(LogInfo.LOG_ELEMENT_CLICKED + (attempts + 1));

            } catch (StaleElementReferenceException | ElementClickInterceptedException | TimeoutException e) {
                Log.warn(LogInfo.LOG_ERROR_ELEMENT_CLICKED + (attempts + 1));
            }

            attempts++;
            waitShortBetweenAttempts();
        }

        Log.error(LogInfo.LOG_ERROR_CANNOT_CLICK_ELEMENT + locator);
        fail(Mensajes.MENSAJE_ERROR_ELEMENTO_NO_CLICKEABLE + locator);
    }

    /**
     * Espera a que un elemento web especificado por el localizador sea visible en la página.
     * Este método utiliza una espera fluida (FluentWait) para controlar la visibilidad del elemento,
     * realiza múltiples intentos en caso de excepciones y toma capturas de pantalla como evidencia del proceso.
     * En caso de error, registra los detalles y marca la prueba como fallida.
     *
     * @param locator el localizador del elemento web que se espera que sea visible
     *                (por ejemplo, By.xpath, By.id, etc.).
     */
    public void waitForVisibilityOfElementLocated(By locator) throws IOException {
        Log.info(LogInfo.LOG_WAIT_FOR_ELEMENT_TO_BE_CLICKABLE + locator);
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(Data.DATA_TIME_OUT))
                .pollingEvery(Duration.ofSeconds(Data.DATA_EVALUATED_TIME))
                .ignoring(NoSuchElementException.class);

        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

                if (elementDisplayedByWebElement(element)) {
                    implicitWait(10);
                    
                    break;
                }
            } catch (StaleElementReferenceException e) {
                attempts++;
                Log.warn(Mensajes.MENSAJE_INTENTOS + attempts);
            } catch (TimeoutException e) {
                Log.error(e.getMessage());
                screenShot();
                fail(e.getMessage());
            } catch (Exception e) {
                Log.error(e.getMessage());
                screenShot();
                fail(e.getMessage());
            }
        }
    }

    /**
     * Espera a que un elemento dinámico en una lista sea clicable y realiza la acción de clic.
     * Este método utiliza un localizador para identificar un elemento dinámico, localiza su ID
     * y construye selectores dinámicos para encontrar la lista y la opción específica.
     * Realiza múltiples intentos en caso de excepciones, toma capturas de pantalla durante el proceso
     * y utiliza JavaScript para garantizar la visibilidad del elemento antes de hacer clic.
     * En caso de error, registra los detalles y marca la prueba como fallida.
     *
     * @param locator     el localizador del elemento inicial utilizado para identificar la lista dinámica.
     * @param optionValue el identificador de la opción específica dentro de la lista dinámica que se desea seleccionar.
     */
    public void waitForDinamicElementFromListToBeClickable(By locator, String optionValue) throws IOException {
        Log.info(LogInfo.LOG_WAIT_FOR_ELEMENT_TO_BE_CLICKABLE);
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(Data.DATA_TIME_OUT))
                .pollingEvery(Duration.ofSeconds(Data.DATA_EVALUATED_TIME))
                .ignoring(NoSuchElementException.class);

        WebElement element = findElement(locator);
        String elementID = element.getDomAttribute(Data.DATA_ID);
        Log.info(LogInfo.LOG_DYNAMIC_ELEMENT_ID_OBTAINED + elementID);

        By list = getDinamicElement(elementID, Data.DATA_LISTBOX);

        Log.info(LogInfo.LOG_DYNAMIC_LIST_BOX_ID + list);

        wait.until(ExpectedConditions.visibilityOfElementLocated(list));

        Log.info(LogInfo.LOG_WAIT_FOR_ELEMENT_TO_BE_CLICKABLE);

        int attempts = 0;
        while (attempts < 5) {
            try {
                By option = getDinamicElement(elementID, optionValue);
                WebElement firstOption = findElement(option);
                Log.info(LogInfo.LOG_DYNAMIC_OPTION + option);
                ((JavascriptExecutor) this.driver).executeScript(Data.DATA_SCROLL_INTO_VIEW_TRUE_JS, firstOption);

                javascriptClickToWebElement(firstOption);
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
                Log.warn(Mensajes.MENSAJE_INTENTOS + attempts);
            } catch (Exception e) {
                Log.error(e.getMessage());
                screenShot();
                fail(e.getMessage());
            }
        }
    }

    /**
     * Genera un localizador dinámico basado en un identificador único y un tipo específico.
     * Este método concatena el identificador proporcionado con el tipo especificado para
     * construir y devolver un objeto de tipo `By` que localiza el elemento web correspondiente.
     *
     * @param id   el identificador único del elemento base.
     * @param tipo el tipo que se combinará con el identificador para construir el localizador.
     * @return un objeto `By` que representa el localizador dinámico del elemento.
     */
    public By getDinamicElement(String id, String tipo) {
        return By.id(id + tipo);
    }

    /**
     * Configura una espera implícita para el controlador de WebDriver.
     * Este método registra información sobre el tiempo de espera configurado y establece
     * el tiempo máximo que WebDriver esperará para encontrar un elemento antes de lanzar una excepción.
     *
     * @param time el tiempo de espera en segundos.
     */
    public void implicitWait(long time) {
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
    }

    /**
     * Busca un elemento web desplazándose verticalmente dentro de un rango definido por dos elementos.
     * Este método utiliza coordenadas obtenidas de los elementos inicial y final para navegar por la página.
     * Detiene la búsqueda cuando encuentra el elemento o alcanza el límite especificado.
     * Después de localizar el elemento, espera a que sea clicable.
     *
     * @param element el localizador del elemento web que se desea buscar.
     * @param start   el localizador del elemento que define el punto inicial del rango de búsqueda.
     * @param finish  el localizador del elemento que define el punto final del rango de búsqueda.
     */
    public void searchElementByCoordinates(By element, By start, By finish) throws IOException {
        Log.info(LogInfo.LOG_SEARCH_FOR_ELEMENT_WITH_VERTICAL_NAVIGATION + element);
        int startElement = getY(start);
        int finishElement = getY(finish);

        boolean search = true;

        do {
            if (elementDisplayedByLocator(element)) {
                search = false;
                navigate(0, getY(element));
            }

            if (startElement >= finishElement) {
                search = false;
            }

            navigate(0, startElement);
            startElement += Data.DATA_NAVIGATE_Y;
        }
        while (search);

        waitForElementToBeClickable(element);
    }

    /**
     * Realiza la búsqueda de un elemento web desplazándose verticalmente en la página.
     * Este método utiliza las coordenadas inicial y final del elemento para navegar de manera incremental,
     * verificando su visibilidad en cada paso. Detiene la búsqueda cuando encuentra el elemento y realiza un
     * click en el elemento o detiene la busqueda al alcanzar los límites establecidos.
     *
     * @param element el localizador del elemento web que se desea buscar.
     */
    public void searchElement(By element) throws IOException {
        Log.info(LogInfo.LOG_SEARCH_FOR_ELEMENT_WITH_VERTICAL_NAVIGATION + element);
        int ini = getY(element);
        int ter = getY(element);

        boolean search = true;

        do {
            if (elementDisplayedByLocator(element)) {
                search = false;
                navigate(0, getY(element));
            }

            if (ini >= ter) {
                search = false;
            }

            navigate(0, ini);
            ini += Data.DATA_NAVIGATE_Y;
        }
        while (search);

        waitForElementToBeClickable(element);
        javascriptClickToLocator(element);
    }

    /**
     * Realiza un clic en un elemento web utilizando JavaScript.
     * Este método registra información sobre el localizador proporcionado y utiliza un
     * script de JavaScript para ejecutar la acción de clic en el elemento especificado.
     *
     * @param locator el localizador del elemento web en el que se desea hacer clic
     *                (por ejemplo, By.xpath, By.id, etc.).
     */
    public void javascriptClickToLocator(By locator) {
        Log.info(LogInfo.LOG_CLICK_WITH_JAVASCRIPT + locator);
        JavascriptExecutor jse = (JavascriptExecutor) this.driver;
        jse.executeScript(Data.DATA_CLICK_JS, findElement(locator));
    }

    /**
     * Realiza un clic en un elemento web utilizando JavaScript.
     * Este método registra información sobre el elemento proporcionado y utiliza un
     * script de JavaScript para ejecutar la acción de clic en dicho elemento.
     *
     * @param element el elemento web en el que se desea hacer clic.
     */
    public void javascriptClickToWebElement(WebElement element) {
        Log.info(LogInfo.LOG_CLICK_WITH_JAVASCRIPT + element);
        JavascriptExecutor jse = (JavascriptExecutor) this.driver;
        jse.executeScript(Data.DATA_CLICK_JS, element);
    }

    /**
     * Desplaza la vista hacia un elemento web específico utilizando JavaScript.
     * Este método emplea un script de JavaScript para asegurar que el elemento
     * esté visible dentro de la ventana del navegador.
     *
     * @param element el elemento web hacia el cual se desea desplazar la vista.
     */
    public void navigateToWebElementWithJavascript(WebElement element) {
        ((JavascriptExecutor) this.driver).executeScript(Data.DATA_SCROLL_INTO_VIEW_TRUE_JS, element);
    }

    /**
     * Desplaza la vista hacia un elemento web especificado mediante JavaScript.
     * Este método utiliza un script de JavaScript para garantizar que el elemento
     * identificado por el localizador sea visible dentro de la ventana del navegador.
     *
     * @param locator el localizador del elemento web hacia el cual se desea desplazar la vista
     *                (por ejemplo, By.xpath, By.id, etc.).
     */
    public void navigateToLocatorWithJavascript(By locator) {
        ((JavascriptExecutor) this.driver).executeScript(Data.DATA_SCROLL_INTO_VIEW_TRUE_JS, findElement(locator));
    }

    /**
     * Extrae un valor específico de un archivo JSON basado en una clave proporcionada.
     * Este método registra información sobre la fuente del archivo JSON y la clave buscada,
     * analiza el contenido del archivo y devuelve el valor correspondiente a la clave.
     *
     * @param jsonData la clave del dato dentro del archivo JSON que se desea obtener.
     * @param source   la ruta de la fuente del archivo JSON.
     * @return el valor correspondiente a la clave especificada dentro del archivo JSON.
     * @throws FileNotFoundException si el archivo JSON no se encuentra en la ubicación especificada.
     * @throws IOException           si ocurre un error durante la lectura del archivo JSON.
     * @throws ParseException        si ocurre un error al analizar el archivo JSON.
     */
    public String getJsonData(String jsonData, String source) throws FileNotFoundException, IOException, ParseException {
        Log.info(LogInfo.LOG_GET_JSON_DATA_FROM_FILE + source);
        Object obj = new JSONParser().parse(new FileReader(source));
        JSONObject jo = (JSONObject) obj;
        String data = (String) jo.get(jsonData);
        Log.info(LogInfo.LOG_JSON_DATA_OBTAINED + data);
        return data;
    }

    /**
     * Extrae un valor específico de un archivo JSON basado en una clave proporcionada.
     * Este método registra información sobre la fuente del archivo JSON y la clave buscada,
     * analiza el contenido del archivo y devuelve el valor correspondiente a la clave en
     * este caso un valor de tipo String.
     *
     * @param object la clave del objeto que contiene la clave del dato que se quiere obtener.
     * @param data   la clave del dato dentro del archivo JSON que se desea obtener.
     * @param source la ruta de la fuente del archivo JSON.
     * @return el valor correspondiente a la clave especificada dentro del archivo JSON.
     * @throws IOException si ocurre un error durante la lectura del archivo JSON.
     */
    public String getJsonString(String object, String data, String source) throws IOException {
        // Ruta al archivo JSON
        File jsonFile = new File(source);

        // Crear el ObjectMapper y leer el archivo
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonFile);

        // Obtener el valor del campo "parallel"
        String value = root.path(object).path(data).asText();

        return value;
    }

    /**
     * Extrae un valor específico de un archivo JSON basado en una clave proporcionada.
     * Este método registra información sobre la fuente del archivo JSON y la clave buscada,
     * analiza el contenido del archivo y devuelve el valor correspondiente a la clave en
     * este caso un valor de tipo boolean.
     *
     * @param object la clave del objeto que contiene la clave del dato que se quiere obtener.
     * @param data   la clave del dato dentro del archivo JSON que se desea obtener.
     * @param source la ruta de la fuente del archivo JSON.
     * @return el valor correspondiente a la clave especificada dentro del archivo JSON.
     * @throws IOException si ocurre un error durante la lectura del archivo JSON.
     */
    public boolean getJsonBoolean(String object, String data, String source) throws IOException {
        // Ruta al archivo JSON
        File jsonFile = new File(source);

        // Crear el ObjectMapper y leer el archivo
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonFile);

        // Obtener el valor del campo "parallel"
        boolean value = root.path(object).path(data).asBoolean();

        return value;
    }

    /**
     * Extrae un valor específico de un archivo JSON basado en una clave proporcionada.
     * Este método registra información sobre la fuente del archivo JSON y la clave buscada,
     * analiza el contenido del archivo y devuelve el valor correspondiente a la clave en
     * este caso un valor de tipo int.
     *
     * @param object la clave del objeto que contiene la clave del dato que se quiere obtener.
     * @param data   la clave del dato dentro del archivo JSON que se desea obtener.
     * @param source la ruta de la fuente del archivo JSON.
     * @return el valor correspondiente a la clave especificada dentro del archivo JSON.
     * @throws IOException si ocurre un error durante la lectura del archivo JSON.
     */
    public int getJsonInt(String object, String data, String source) throws IOException {
        // Ruta al archivo JSON
        File jsonFile = new File(source);

        // Crear el ObjectMapper y leer el archivo
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonFile);

        int value = root.path(object).path(data).asInt();

        return value;
    }

    /**
     * Sube un archivo al sistema utilizando un campo de carga de archivos localizado por un selector específico.
     * Este método registra información sobre el archivo a cargar y utiliza el localizador proporcionado
     * para encontrar el elemento correspondiente, enviando la ruta del archivo al campo de entrada.
     *
     * @param locator  el localizador del campo de carga de archivos
     *                 (por ejemplo, By.xpath, By.id, etc.).
     * @param filePath la ruta del archivo que se desea cargar.
     */
    public void uploadFiles(By locator, String filePath) {
        Log.info(LogInfo.LOG_UPLOAD + filePath);
        WebElement fileInput = findElement(locator);
        String absolutePath = Paths.get(filePath).toAbsolutePath().toString();
        fileInput.sendKeys(absolutePath);
    }

    public void downloadFiles(String fileType, By locator) throws IOException {
        Log.info(LogInfo.LOG_DOWNLOAD_START);
        clickLocator(locator);

        // Obtener la ruta de descarga configurada
        String downloadPath = getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_DOWNLOADS,
                ExternalData.ED_SOURCE);

        String absolutePath = Paths.get(downloadPath).toAbsolutePath().toString();

        // Esperar dinámicamente a que se descargue al menos un archivo .docx
        try {
            waitForFileDownload(fileType, absolutePath, 30); // Espera hasta 30 segundos
        } catch (InterruptedException e) {
            Log.error(LogInfo.LOG_DOWNLOAD_ERROR_DYNAMIC_INTERRUPTION + e.getMessage());
            Thread.currentThread().interrupt();
            throw new IOException(LogInfo.LOG_DOWNLOAD_ERROR_INTERRUPTED_WAITING);
        } catch (RuntimeException e) {
            Log.error(e.getMessage());
            throw new IOException(e.getMessage());
        }

        // Crear carpeta de evidencias
        String evidencePath = getJsonString(
                ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_EVIDENCES,
                ExternalData.ED_SOURCE
        ) + generateFolderName();

        try {
            Files.createDirectories(Paths.get(evidencePath));
        } catch (IOException e) {
            Log.error(LogInfo.LOG_DOWNLOAD_ERROR_CREATE_EVIDENCE_DIRECTORY + e.getMessage());
            throw e;
        }

        File downloadDir = new File(absolutePath);
        File[] files = downloadDir.listFiles((dir, name) -> name.endsWith(fileType) &&
                !name.endsWith(Data.DATA_FILE_TYPE_CRDOWNLOAD));

        if (files == null || files.length == 0) {
            Log.error(LogInfo.LOG_DOWNLOAD_ERROR_FILES_NOT_FOUND + downloadDir.getAbsolutePath());
            throw new IOException(LogInfo.LOG_DOWNLOAD_ERROR_TYPE_FILES_NOT_FOUND + fileType);
        }

        for (File file : files) {
            if (file.isFile()) {
                try {
                    FileUtils.moveFileToDirectory(file, new File(evidencePath), false);
                    Path movedFilePath = Paths.get(evidencePath, file.getName());

                    try (InputStream is = Files.newInputStream(movedFilePath)) {
                        Allure.attachment(file.getName(), is);
                    }

                    Log.info(LogInfo.LOG_DOWNLOAD_FINISH + file.getName());
                } catch (IOException e) {
                    Log.error(LogInfo.LOG_DOWNLOAD_ERROR_MOVE_FILES + file.getName() + " - " + e.getMessage());
                }
            }
        }
    }

    private void waitForFileDownload(String fileType, String downloadPath, int timeoutInSeconds) throws InterruptedException {
        File dir = new File(downloadPath);
        int waited = 0;
        while (waited < timeoutInSeconds) {
            File[] files = dir.listFiles((d, name) -> name.endsWith(fileType) &&
                    !name.endsWith(Data.DATA_FILE_TYPE_CRDOWNLOAD));
            if (files != null && files.length > 0) return;
            Thread.sleep(1000);
            waited++;
        }
        throw new RuntimeException(LogInfo.LOG_DOWNLOAD_ERROR_TIMEOUT + downloadPath);
    }

    /**
     * Refresca la página web actual en el navegador.
     * Este método utiliza el controlador de WebDriver para recargar la página en curso.
     */
    public void refreshPage() {
        driver.navigate().refresh();
    }

    /**
     * Elimina todas las cookies almacenadas en el navegador actual.
     * Este método utiliza el controlador de WebDriver para borrar todas las cookies
     * asociadas con la sesión del navegador.
     */
    public void clearCookies() {
        driver.manage().deleteAllCookies();
    }

    /**
     * Minimiza la ventana del navegador actual.
     * Este método utiliza el controlador de WebDriver para reducir la ventana a su estado minimizado.
     */
    public void minimizeScreen() {
        driver.manage().window().minimize();
    }

    /**
     * Cierra la ventana actual del navegador.
     * <p>
     * Este método utiliza el objeto {@code driver} para cerrar
     * la ventana activa del navegador. Es parte del proceso de
     * control y manejo de sesiones en la automatización.
     * <p>
     * Nota: Si esta ventana es la única abierta, la sesión del
     * navegador también se cerrará.
     */
    public void closeWindow() {
        driver.close();
    }

    /**
     * Abre una nueva ventana del navegador y navega a la URL especificada.
     * <p>
     * Este método crea una nueva ventana en la sesión actual utilizando
     * {@link WebDriver#switchTo()} y {@link WindowType#WINDOW}. Luego,
     * carga la URL proporcionada en la nueva ventana.
     *
     * @param url La dirección web (URL) que se cargará en la nueva ventana.
     */
    public void openWindow(String url) {
        WebDriver newWindow = driver.switchTo().newWindow(WindowType.WINDOW);
        newWindow.get(url);
    }

    /**
     * Maximiza la ventana del navegador actual.
     * Este método utiliza el controlador de WebDriver para expandir la ventana a su tamaño máximo disponible.
     */
    public void maximizeScreen() {
        driver.manage().window().maximize();
    }

    /**
     * Limpia todos los archivos y subdirectorios dentro de una carpeta especificada.
     * Este método utiliza la clase `FileUtils` para eliminar el contenido del directorio
     * sin borrar la carpeta principal y registra información sobre el proceso.
     *
     * @param src la ruta del directorio que se desea limpiar.
     * @throws IOException si ocurre un error durante la limpieza del directorio.
     */
    public void cleanFolder(String src) throws IOException {
        Log.info(LogInfo.LOG_CLEAR_DIRECTORY + src);
        File folder = new File(src);
        FileUtils.cleanDirectory(folder);
    }

    /**
     * Valida que un elemento web identificado por un localizador esté visible en la página.
     * Este método registra información sobre el localizador proporcionado y utiliza una aserción
     * para verificar que el elemento está presente y visible, generando un mensaje de error si no lo está.
     *
     * @param locator el localizador del elemento web que se desea validar
     *                (por ejemplo, By.xpath, By.id, etc.).
     */
    public void validateElementIsDisplayedByLocator(By locator) {
        Log.info(LogInfo.LOG_ELEMENT_IS_PRESENT + locator);
        //capturarPantalla();
        assertEquals(elementDisplayedByLocator(locator), true, Mensajes.MENSAJE_ERROR_TEXTO +
                locator.toString());
    }

    /**
     * Valida que el texto de un elemento web coincida con el mensaje esperado.
     * Este método registra información sobre el mensaje proporcionado, verifica que el texto
     * del elemento identificado por el localizador coincida con el texto esperado y utiliza una
     * aserción para generar un mensaje de error si no coinciden.
     *
     * @param mensaje el texto esperado que se desea validar.
     * @param locator el localizador del elemento web cuyo texto se debe verificar
     *                (por ejemplo, By.xpath, By.id, etc.).
     */
    public void validateElementText(String mensaje, By locator) {
        Log.info(LogInfo.LOG_ELEMENT_TEXT_IS_PRESENT + mensaje);
        assertEquals(mensaje, getTextByLocator(locator), Mensajes.MENSAJE_ERROR_TEXTO);
    }

    /**
     * Obtiene la fecha actual en un formato especificado.
     * Este método utiliza la clase `SimpleDateFormat` para dar formato a la fecha actual
     * según el patrón proporcionado, y registra información sobre la operación.
     *
     * @param dateFormat el formato deseado para la fecha (por ejemplo, "dd/MM/yyyy").
     * @return una cadena que representa la fecha actual en el formato especificado.
     */
    public String getDate(String dateFormat) {
        Log.info(LogInfo.LOG_GET_DATE);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

        return simpleDateFormat.format(date);
    }

    /**
     * Genera un nombre de carpeta dinámico utilizando los valores actuales de navegador, nombre de prueba y fecha.
     * Este método concatena estas tres propiedades separadas por barras invertidas (`\`)
     * para construir y devolver un nombre de carpeta único.
     *
     * @return una cadena que representa el nombre de la carpeta generada en el formato: "browser\testName\date".
     */
    public String generateFolderName() throws IOException {
        return browser + "/" + testName.get() + "/" + date;
    }

    /**
     * Valida el código de estado y la línea de estado de una respuesta HTTP para una URL específica.
     * Este método utiliza Rest-Assured para enviar una solicitud GET al endpoint proporcionado y
     * verifica que el código de estado y la línea de estado coincidan con los valores esperados.
     * También integra Allure para generar información de trazabilidad en los reportes.
     *
     * @param url        la URL del endpoint al cual se realizará la solicitud GET.
     * @param statusCode el código de estado esperado de la respuesta HTTP.
     * @param statusLine la línea de estado esperada de la respuesta HTTP.
     */
    public void validateStatusCode(String url, int statusCode, String statusLine) {
        // Given
        given()
                .filter(new AllureRestAssured())
                // When
                .when()
                .get(url)
                // Then
                .then()
                .statusCode(statusCode)
                .statusLine(statusLine);
    }

    /**
     * Verifica los enlaces en la página web actual y categoriza su estado en varias listas.
     * Este método identifica todos los enlaces presentes en la página y realiza validaciones
     * para clasificarlos en cuatro categorías: enlaces rotos, enlaces correctos, enlaces nulos
     * y enlaces con errores de servidor. Además, genera registros y adjuntos para su trazabilidad
     * en el reporte de Allure.
     *
     * <p>El proceso incluye validaciones de enlaces comunes, enlaces especiales como API calls
     * y maneja posibles excepciones. Proporciona un informe detallado al final del test.</p>
     */
    public void verifyLinks() throws IOException {
        Log.info(LogInfo.LOG_LINK_VALIDATION_START);
        screenShot();
        List<WebElement> links = findElements(By.tagName(Data.DATA_LINK));

        List<String> brokenLinks = new ArrayList<>();
        List<String> okLinks = new ArrayList<>();
        List<String> nullLinks = new ArrayList<>();
        List<String> serverLinks = new ArrayList<>();

        JavascriptExecutor js = (JavascriptExecutor) driver;

        HttpURLConnection httpURLConnection;

        int responseCode;
        String url;

        for (int i = 0; i <= links.size() - 1; i++) {
            url = links.get(i).getDomProperty(Data.DATA_HREF);
            Log.info(LogInfo.LOG_LINK_VALIDATION_FOUND + url);

            //Verifica si la url no es nula
            if (url == null || url.isEmpty()) {
                Log.warn(LogInfo.LOG_LINK_VALIDATION_NULL + url);
                Log.info(LogInfo.LOG_SEPARATE);
                nullLinks.add(url);
            } else {
                Log.warn(LogInfo.LOG_LINK_VALIDATION_ESPECIAL);
                //Verificación de enlaces comunes
                if (!url.startsWith(Data.DATA_JS)) {
                    Log.warn(LogInfo.LOG_LINK_VALIDATION_NORMAL);

                    try {
                        URI uri = new URI(url);
                        URL urla = uri.toURL();

                        httpURLConnection = (HttpURLConnection) (urla.openConnection());
                        httpURLConnection.setRequestMethod(Data.DATA_HEAD);
                        httpURLConnection.connect();
                        responseCode = httpURLConnection.getResponseCode();

                        if (responseCode > Data.DATA_STATUS_CODE_400) {
                            Log.warn(LogInfo.LOG_LINK_VALIDATION_BROKEN + url);
                            Log.info(LogInfo.LOG_SEPARATE);
                            brokenLinks.add(url);
                        } else {
                            Log.info(LogInfo.LOG_LINK_VALIDATION_OK + url);
                            Log.info(LogInfo.LOG_SEPARATE);
                            okLinks.add(url);
                        }
                    } catch (Exception e) {
                        Log.warn(e.getMessage());
                        Log.info(LogInfo.LOG_SEPARATE);
                        serverLinks.add(url);
                    }
                } else {
                    //Verificación de enlaces especiales como API CALLS

                    Log.info(LogInfo.LOG_LINK_VALIDATION_IS_ESPECIAL);
                    js.executeScript(Data.DATA_SCROLL_INTO_VIEW_JS, links.get(i));
                    clickWebElement(links.get(i));
                    //esperarDisponibilidadDeElemento(Elements.linkResponse);
                    //buscarElemento(links.get(i), Elements.linkinicio, Elements.linkfin);

                    try {
                        //System.out.println("VALID LINK API CALL: " + obtenerTexto(links.get(i)) + " - " + obtenerTexto(Elements.linkResponse));
                        Log.info(LogInfo.LOG_LINK_VALIDATION_OK + getTextByWebElement(links.get(i)));
                        Log.info(LogInfo.LOG_SEPARATE);
                        okLinks.add(url);
                    } catch (NoSuchElementException noSuchElementException) {
                        Log.error(LogInfo.LOG_LINK_VALIDATION_BROKEN + getTextByWebElement(links.get(i)));
                        Log.error(noSuchElementException.getMessage());
                        Log.info(LogInfo.LOG_SEPARATE);
                        brokenLinks.add(url);
                    }
                }
            }
        }

        //Output del test
        Log.info(LogInfo.LOG_LINK_VALIDATION_TOTAL + links.size());
        Allure.attachment(LogInfo.LOG_LINK_VALIDATION_TOTAL, Integer.toString(links.size()));
        Log.info(LogInfo.LOG_LINK_VALIDATION_TOTAL_OK + okLinks.size());
        Allure.attachment(LogInfo.LOG_LINK_VALIDATION_TOTAL_OK, Integer.toString(okLinks.size()));
        Log.info(LogInfo.LOG_LINK_VALIDATION_TOTAL_BROKEN + brokenLinks.size());
        Allure.attachment(LogInfo.LOG_LINK_VALIDATION_TOTAL_BROKEN, Integer.toString(brokenLinks.size()));
        Log.info(LogInfo.LOG_LINK_VALIDATION_TOTAL_NULL + nullLinks.size());
        Allure.attachment(LogInfo.LOG_LINK_VALIDATION_TOTAL_NULL, Integer.toString(nullLinks.size()));
        Log.info(LogInfo.LOG_LINK_VALIDATION_SERVER_DOWN + serverLinks.size());
        Allure.attachment(LogInfo.LOG_LINK_VALIDATION_SERVER_DOWN, Integer.toString(serverLinks.size()));

        if (!brokenLinks.isEmpty()) {
            Log.info(LogInfo.LOG_SEPARATE);
            Log.info(LogInfo.LOG_LINK_VALIDATION_BROKEN_LIST);

            for (String brokenLink : brokenLinks) {
                Log.error(brokenLink);
                Allure.attachment(Data.DATA_ALLURE_BROKEN_LINK, brokenLink);
            }
        }

        if (!nullLinks.isEmpty()) {
            Log.info(LogInfo.LOG_SEPARATE);
            Log.info(LogInfo.LOG_LINK_VALIDATION_NULL_LIST);

            for (String nullLink : nullLinks) {
                Log.error(nullLink);
                Allure.attachment(Data.DATA_ALLURE_NULL_LINK, nullLink);
            }
        }

        if (!serverLinks.isEmpty()) {
            Log.info(LogInfo.LOG_SEPARATE);
            Log.info(LogInfo.LOG_LINK_VALIDATION_SERVER_DOWN_LIST);

            for (String serverLink : serverLinks) {
                Log.error(serverLink);
                Allure.attachment(Data.DATA_SERVER_LINK, serverLink);
            }
        }
    }

    /**
     * Genera un número RUT chileno aleatorio en formato estándar.
     * <p>
     * Este método crea un número aleatorio de 8 dígitos y calcula el
     * dígito verificador correspondiente utilizando el método
     * {@code calculateVerifyDigit}. Luego, devuelve el RUT como una
     * cadena con el formato "XXXXXXXX-Y".
     *
     * @return El número RUT generado, incluyendo el dígito verificador.
     */
    public String generateRUT() {
        Random random = new Random();
        int numero = random.nextInt(100000000); // Genera un número aleatorio de 8 dígitos
        char digitoVerificador = calculateVerifyDigit(numero);
        return String.format("%08d-%c", numero, digitoVerificador);
    }

    /**
     * Calcula el dígito verificador (DV) de un número según el algoritmo chileno de RUT.
     * <p>
     * Este método toma un número entero y aplica una serie de cálculos
     * para determinar su dígito verificador. El dígito verificador puede ser:
     * - Un número entre 0 y 9.
     * - 'K' si el resultado del cálculo es 10.
     *
     * @param numero El número entero cuyo dígito verificador se va a calcular.
     *               Por lo general, representa la parte numérica de un RUT chileno.
     * @return El dígito verificador como un carácter ('0' - '9' o 'K').
     */
    public static char calculateVerifyDigit(int numero) {
        int suma = 0;
        int factor = 2;
        while (numero > 0) {
            int digito = numero % 10;
            suma += digito * factor;
            factor = (factor == 7) ? 2 : factor + 1;
            numero /= 10;
        }
        int dv = 11 - (suma % 11);
        if (dv == 11) {
            return '0';
        } else if (dv == 10) {
            return 'K';
        } else {

            return (char) (dv + '0');
        }
    }

    /**
     * Configura opciones personalizadas para el navegador Chrome.
     * Este método define preferencias específicas para la descarga de archivos, activa el modo headless
     * para ejecutar el navegador sin interfaz gráfica, ajusta el tamaño de la ventana del navegador
     * y asigna el navegador Chrome como predeterminado.
     *
     * @param downloadFilePath la ruta de directorio donde se almacenarán los archivos descargados.
     * @return un objeto `ChromeOptions` con las configuraciones personalizadas aplicadas.
     * @see ChromeOptions
     */
    public ChromeOptions chromeCustomConfiguration(String downloadFilePath) throws IOException {
        Log.info(LogInfo.LOG_CHROME_CONFIGURATION);
        ChromeOptions optionChrome = new ChromeOptions();

        String downloadPath = (downloadFilePath != null && !downloadFilePath.isEmpty())
                ? downloadFilePath
                : System.getProperty(Data.DATA_SYSTEM_PROPERTY_TMPDIR) + Data.DATA_BROWSER_DEFAULT_DOWNLOAD_DIRECTORY;

        String path = getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_DOWNLOADS,
                ExternalData.ED_SOURCE);

        String absolutePath = Paths.get(path).toAbsolutePath().toString();
        new File(downloadPath).mkdirs();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put(Data.DATA_BROWSER_DOWNLOAD_DIRECTORY, absolutePath);
        prefs.put(Data.DATA_BROWSER_DOWNLOAD_PROMPT, false);
        prefs.put(Data.DATA_BROWSER_SETTINGS_POPUPS, 0);
        prefs.put(Data.DATA_BROWSER_OPEN_PDF_EXTERNAL, true);
        prefs.put("profile.password_manager_leak_detection", false);

        optionChrome.setExperimentalOption(Data.DATA_BROWSER_DOWNLOAD_PREFS, prefs);

        if (getJsonBoolean(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_HEADLESS, ExternalData.ED_SOURCE)) {
            optionChrome.addArguments(BrowserConfiguration.BC_PREF_HEADLESS);
        }

        optionChrome.addArguments(getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_BROWSER_SIZE, ExternalData.ED_SOURCE));

        Base.browser = Data.DATA_BROWSER_CHROME;

        return optionChrome;
    }

    /**
     * Configura opciones personalizadas para el navegador Edge.
     * Este método activa el modo headless para ejecutar el navegador sin interfaz gráfica,
     * ajusta el tamaño de la ventana del navegador y asigna Edge como el navegador predeterminado.
     *
     * @return un objeto `EdgeOptions` con las configuraciones personalizadas aplicadas.
     * @see EdgeOptions
     */
    public EdgeOptions edgeCustomConfiguration() {
        Log.info(LogInfo.LOG_EDGE_CONFIGURATION);
        EdgeOptions optionEdge = new EdgeOptions();

        if (BrowserConfiguration.BC_PREF_HEADLESS_ON) {
            //Configurar el navegador en modo headless
            optionEdge.addArguments(BrowserConfiguration.BC_PREF_HEADLESS);
        }

        //Configurar el tamaño de la ventana
        optionEdge.addArguments(BrowserConfiguration.BC_PREF_SIZE);

        Base.browser = Data.DATA_BROWSER_EDGE;

        return optionEdge;
    }

    /**
     * Configura opciones personalizadas para Firefox
     * Este método activa el modo headless para ejecutar el navegador sin interfaz gráfica,
     * ajusta el tamaño de la ventana del navegador y asigna Edge como el navegador predeterminado.
     *
     * @param key              Clave de la preferencia a configurar.
     * @param value            Valor entero de la preferencia.
     * @param directory        Clave de la preferencia para la carpeta de descarga.
     * @param downloadFilePath Ruta de descarga donde se guardarán los archivos.
     * @return Una instancia de {@link FirefoxOptions} configurada con las opciones proporcionadas.
     * @see FirefoxOptions
     * @see FirefoxProfile
     */
    public FirefoxOptions firefoxCustomConfiguration(String key, int value, String directory, String downloadFilePath) {
        Log.info(LogInfo.LOG_FIREFOX_CONFIGURATION);
        FirefoxProfile profile = new FirefoxProfile();
        FirefoxOptions optionFirefox = new FirefoxOptions();

        profile.setPreference(key, value);
        profile.setPreference(directory, downloadFilePath);

        if (BrowserConfiguration.BC_PREF_HEADLESS_ON) {
            //Configurar el navegador en modo headless
            optionFirefox.addArguments(BrowserConfiguration.BC_PREF_HEADLESS);
        }

        //Configurar el tamaño de la ventana
        optionFirefox.addArguments(BrowserConfiguration.BC_PREF_SIZE);

        optionFirefox.setProfile(profile);
        Base.browser = Data.DATA_BROWSER_FIREFOX;

        return optionFirefox;
    }

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException ex) {
            return false;
        }
    }

    public void acceptAlert(){
        if (isAlertPresent()) {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }
    }

    public void dismissAlert(){
        if (isAlertPresent()) {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
        }
    }
}
