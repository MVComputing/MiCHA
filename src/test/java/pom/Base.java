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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import pom.auto.repository.ExternalData_Auto;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Clase base que contiene las funciones genéricas para la interacción con el navegador.
 * <p>
 * Proporciona métodos comunes para control del {@link org.openqa.selenium.WebDriver},
 * manejo de esperas, navegación, capturas de pantalla y otras acciones reutilizables
 * por las clases que extienden esta base.
 */
public class Base {

    /**
     * Controlador principal del navegador web.
     */
    public WebDriver driver;

    /**
     * Devuelve la instancia actual del navegador.
     *
     * @return el objeto WebDriver en uso
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Fecha actual usada en las pruebas.
     */
    public static String date;

    /**
     * Nombre del test actual, almacenado en un ThreadLocal.
     */
    public static ThreadLocal<String> testName = ThreadLocal.withInitial(() -> "Default Test Name");

    /**
     * Navegador utilizado en la ejecución de las pruebas.
     */
    public static String browser;

    /**
     * Crea una nueva instancia de Base con el WebDriver especificado.
     *
     * @param driver instancia del navegador a usar
     */
    public Base(WebDriver driver) {
        this.driver = driver;
    }

    /*public void firefoxDriverConnection() {
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
    }*/

    /*public void firefoxDriverConnectionOptions(FirefoxOptions options) {
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
    }*/

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
     * Inicializa la conexión con ChromeDriver usando las opciones especificadas.
     * Cierra instancias previas si existen y valida la creación del nuevo WebDriver.
     *
     * @param options configuración personalizada de Chrome
     */
    public void chromeDriverConnectionOptions(ChromeOptions options) {
        Log.info(LogInfo.LOG_CHROME_CUSTOM_CONNECTION);

        try {
            ITestResult result = Reporter.getCurrentTestResult();
            int currentAttempt = result.getMethod().getCurrentInvocationCount();

            if (currentAttempt > 1) {
                Log.info("Cerrando instancia previa de WebDriver...");
                quitDriver();
            }

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

    /*public void edgeConnection() {
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
    }*/

    /*public void edgeConnectionOptions(EdgeOptions options) {
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
    }*/

    /**
     * Busca un elemento visible en la página usando el localizador indicado.
     *
     * @param locator localizador del elemento
     * @return el elemento encontrado o {@code null} si ocurre un error
     */
    public WebElement findElement(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            validateElement(element, locator);
            Log.info(LogInfo.LOG_FIND_ELEMENT + locator);
            return element;
        } catch (Exception e) {
            handleFail(locator, LogInfo.LOG_ERROR_FIND_ELEMENT + locator, e);
            return null;
        }
    }

    /**
     * Busca todos los elementos visibles que coincidan con el localizador indicado.
     *
     * @param locator localizador de los elementos
     * @return lista de elementos encontrados, o una lista vacía si no se encuentran
     */
    public List<WebElement> findElements(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            if (elements == null || elements.isEmpty()) {
                handleFail(locator, LogInfo.LOG_ERROR_FIND_ELEMENTS_NOT_FOUND + locator, null);
            }
            Log.info(LogInfo.LOG_FIND_ELEMENT_LIST + locator);
            return elements;
        } catch (Exception e) {
            handleFail(locator, LogInfo.LOG_ERROR_FIND_ELEMENTS + locator, e);
            return Collections.emptyList();
        }
    }

    /**
     * Busca un elemento <select> en la página y lo devuelve como objeto Select.
     *
     * @param locator localizador del elemento <select>
     * @return objeto Select correspondiente al elemento, o {@code null} si ocurre un error
     */
    public Select findElementSelect(By locator) {
        try {
            WebElement element = findElement(locator);
            validateElement(element, locator);
            Log.info(LogInfo.LOG_FIND_ELEMENT_SELECT + locator);
            return new Select(element);
        } catch (Exception e) {
            handleFail(locator, LogInfo.LOG_ERROR_FIND_ELEMENT_SELECT + locator, e);
            return null;
        }
    }

    /**
     * Selecciona un valor en un elemento <select> usando el texto visible.
     *
     * @param locator  localizador del elemento <select>
     * @param seleccion texto visible a seleccionar
     */
    public void selectElementSelectByVisibleText(By locator, String seleccion) {
        try {
            WebElement element = findElement(locator);
            validateElement(element, locator);
            Log.info(LogInfo.LOG_SELECT_DROPDOWNLIST_ELEMENT_BY_VISIBLE_TEXT + locator + ": " + seleccion);
            Select select = new Select(element);
            select.selectByVisibleText(seleccion);
        } catch (Exception e) {
            handleFail(locator, LogInfo.LOG_ERROR_SELECT_BY_VISIBLE_TEXT + locator, e);
        }
    }

    /**
     * Obtiene el texto de un elemento localizado en la página.
     *
     * @param locator localizador del elemento
     * @return texto del elemento, o {@code null} si ocurre un error
     */
    public String getTextByLocator(By locator) {
        try {
            WebElement element = findElement(locator);
            validateElement(element, locator);
            Log.info(LogInfo.LOG_GET_TEXT_BY_LOCATOR + locator);
            return element.getText();
        } catch (Exception e) {
            handleFail(locator, LogInfo.LOG_ERROR_GET_TEXT_BY_LOCATOR + locator, e);
            return null;
        }
    }

    /**
     * Obtiene el texto de un elemento WebElement dado.
     *
     * @param element elemento del cual se obtendrá el texto
     * @return texto del elemento, o {@code null} si ocurre un error
     */
    public String getTextByWebElement(WebElement element) {
        try {
            validateElement(element, null);
            Log.info(LogInfo.LOG_GET_TEXT_BY_ELEMENT + element);
            return element.getText();
        } catch (Exception e) {
            handleFail(null, LogInfo.LOG_ERROR_GET_TEXT_BY_ELEMENT + element, e);
            return null;
        }
    }

    /**
     * Verifica si un elemento localizado está habilitado.
     *
     * @param locator localizador del elemento a verificar
     * @return {@code true} si el elemento está habilitado, {@code false} si no se encuentra o ocurre un error
     */
    public boolean elementEnabled(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            Log.info("Verificando si el elemento está habilitado: " + locator);
            return element.isEnabled();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            Log.warn("Elemento no encontrado o no disponible: " + locator);
            return false;
        } catch (Exception e) {
            Log.error("Error al verificar si el elemento está habilitado: " + locator + " - " + e.getMessage());
            return false;
        }
    }

    /**
     * Selecciona un valor en un elemento <select> usando el atributo "value".
     *
     * @param locator   localizador del elemento <select>
     * @param selection valor a seleccionar
     */
    public void selectElementSelectByValue(By locator, String selection) {
        try {
            WebElement element = findElement(locator);
            validateElement(element, locator);
            Log.info(LogInfo.LOG_SELECT_DROPDOWNLIST_ELEMENT_BY_VALUE + locator + ": " + selection);
            Select select = new Select(element);
            Log.info("Opciones actualmente seleccionadas: " + select.getAllSelectedOptions());
            select.selectByValue(selection);
        } catch (Exception e) {
            handleFail(locator, "No se pudo seleccionar por valor '" + selection + "' en el elemento Select: " + locator, e);
        }
    }

    /**
     * Pausa la ejecución del hilo actual durante el tiempo especificado.
     *
     * @param milliSeconds duración de la espera en milisegundos
     */
    public void threadWait(Long milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
            Log.info("Esperando " + milliSeconds + " milisegundos.");
        } catch (InterruptedException e) {
            Log.error("Thread interrumpido durante la espera: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Envía texto a un elemento de entrada (input) localizado en la página.
     *
     * @param inputText texto a enviar
     * @param locator   localizador del elemento de entrada
     */
    public void sendInputText(String inputText, By locator) {
        try {
            WebElement element = findElement(locator);
            validateElement(element, locator);
            Log.info(LogInfo.LOG_TYPE_TEXT +  inputText);
            element.clear();
            element.sendKeys(inputText);
        } catch (Exception e) {
            handleFail(locator, "No se pudo enviar texto '" + inputText + "' al elemento: " + locator, e);
        }
    }

    /**
     * Envía texto a un elemento de entrada (input) y presiona la tecla Enter.
     *
     * @param inputText texto a enviar
     * @param locator   localizador del elemento de entrada
     */
    public void sendInputTextAndKeyEnter(String inputText, By locator) {
        try {
            WebElement element = findElement(locator);
            validateElement(element, locator);
            Log.info(LogInfo.LOG_TYPE_TEXT + "Enviando texto: '" + inputText + "' y presionando Enter al elemento: " + locator);
            element.clear();
            element.sendKeys(inputText, Keys.ENTER);
        } catch (Exception e) {
            handleFail(locator, "No se pudo enviar texto '" + inputText + "' y presionar Enter en el elemento: " + locator, e);
        }
    }

    /**
     * Envía texto a un elemento de entrada (input), presiona Enter, luego la tecla Down y nuevamente Enter.
     *
     * @param inputText texto a enviar
     * @param locator   localizador del elemento de entrada
     */
    public void sendInputTextAndKeysDownEnter(String inputText, By locator) {
        WebElement element = findElement(locator);
        validateElement(element, locator);
        element.clear();
        element.sendKeys(inputText, Keys.ENTER);
        Log.info(LogInfo.LOG_TYPE_TEXT_AND_ENTER);
        element.sendKeys(Keys.DOWN);
        element.sendKeys(Keys.ENTER);
    }

    /**
     * Limpia el texto de un elemento de entrada (input) localizado en la página.
     *
     * @param locator localizador del elemento de entrada
     */
    public void clearText(By locator) {
        try {
            WebElement element = findElement(locator);
            validateElement(element, locator);
            Log.info(LogInfo.LOG_CLEAR_TEXT + " Limpiando el texto del elemento: " + locator);
            element.clear();
        } catch (Exception e) {
            handleFail(locator, "No se pudo limpiar el texto del elemento: " + locator, e);
        }
    }

    /**
     * Selecciona todo el texto de un elemento de entrada (input) y lo borra.
     *
     * @param locator localizador del elemento de entrada
     */
    public void selectAndClearAllText(By locator) {
        try {
            WebElement element = findElement(locator);
            validateElement(element, locator);
            Log.info("Seleccionando todo el texto y borrando el contenido del elemento: " + locator);
            element.sendKeys(Keys.CONTROL, "a");
            element.sendKeys(Keys.BACK_SPACE);
        } catch (Exception e) {
            handleFail(locator, "No se pudo seleccionar y borrar todo el texto del elemento: " + locator, e);
        }
    }

    /**
     * Navega a la URL correspondiente según el entorno especificado.
     *
     * @param environment nombre del entorno (por ejemplo, "DEV", "QA", "PROD")
     */
    public void visitUrlIndex(String environment) {
        try {
            String key = switch (environment) {
                case Data.DATA_ENV_DEV -> ExternalData_Auto.ED_URL_DEV;
                case Data.DATA_ENV_PROD -> ExternalData_Auto.ED_URL_PROD;
                default -> ExternalData_Auto.ED_URL_QA;
            };

            String url = getJsonString(ExternalData_Auto.ED_OBJECT_URLS, key, ExternalData_Auto.ED_SRC);
            Log.info(LogInfo.LOG_VISIT_URL + url);
            driver.get(url);
        } catch (Exception e) {
            handleFail(null, "No se pudo visitar la URL para el entorno: " + environment, e);
        }
    }

    /**
     * Navega a la URL especificada.
     *
     * @param url dirección web a visitar
     */
    public void visitUrl(String url) {
        try {
            Log.info(LogInfo.LOG_VISIT_URL + url);
            driver.get(url);
        } catch (Exception e) {
            handleFail(null, "No se pudo visitar la URL: " + url, e);
        }
    }

    /**
     * Cierra la instancia actual de WebDriver si existe y libera los recursos.
     */
    public void quitDriver() {
        Log.info(LogInfo.LOG_QUIT + Base.browser);
        Log.info(LogInfo.LOG_SEPARATE);
        try {
            if (driver != null) {
                driver.quit();
                driver = null;
                Log.info("WebDriver cerrado correctamente.");
            }
        } catch (Exception e) {
            Log.error("Error al cerrar WebDriver: " + e.getMessage());
        }
    }

    /**
     * Hace clic en un elemento localizado en la página.
     *
     * @param locator localizador del elemento a hacer clic
     */
    public void clickLocator(By locator) {
        try {
            WebElement element = findElement(locator);
            validateElement(element, locator);
            Log.info(LogInfo.LOG_CLICK_LOCATOR + " Haciendo click en el elemento: " + locator);
            element.click();
        } catch (Exception e) {
            handleFail(locator, "No se pudo hacer click en el elemento: " + locator, e);
        }
    }

    /**
     * Hace clic en un WebElement dado.
     *
     * @param webElement elemento WebElement sobre el que se realizará el clic
     */
    public void clickWebElement(WebElement webElement) {
        try {
            validateElement(webElement, null);
            Log.info(LogInfo.LOG_CLICK_ELEMENT + " Haciendo click en el elemento: " + webElement);
            webElement.click();
        } catch (Exception e) {
            handleFail(null, "No se pudo hacer click en el WebElement: " + webElement, e);
        }
    }

    /**
     * Realiza una navegación vertical desde un elemento de inicio hasta un elemento de destino,
     * tomando capturas de pantalla en cada paso del recorrido.
     *
     * @param start  localizador del elemento de inicio
     * @param finish localizador del elemento de destino
     */
    public void verticalWebNavigation(By start, By finish) {
        try {
            WebElement startElement = findElement(start);
            WebElement finishElement = findElement(finish);
            validateElement(startElement, start);
            validateElement(finishElement, finish);

            Log.info(LogInfo.LOG_VERTICAL_NAVIGATION);

            int ini = getY(start);
            int ter = getY(finish);

            while (ini <= ter) {
                navigate(0, ini);
                screenShot();
                ini += Data.DATA_NAVIGATE_Y;
            }

            Log.info(LogInfo.LOG_NAVIGATION_FINALIZED);
        } catch (Exception e) {
            handleFail(null, "Error durante la navegación vertical entre los elementos: "
                    + start + " y " + finish, e);
        }
    }

    /**
     * Realiza un desplazamiento (scroll) a las coordenadas X e Y especificadas en la página.
     *
     * @param x coordenada horizontal
     * @param y coordenada vertical
     */
    public void navigate(int x, int y) {
        try {
            Log.info(LogInfo.LOG_NAVIGATE_TO_X_AND_Y + x + "/" + y);
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("scroll(" + x + ", " + y + ");");
        } catch (Exception e) {
            handleFail(null, "Error al navegar a la posición X/Y: " + x + "/" + y, e);
        }
    }

    /**
     * Obtiene la coordenada X de un elemento localizado en la página.
     *
     * @param locator localizador del elemento
     * @return coordenada X del elemento, o -1 si ocurre un error
     */
    public int getX(By locator) {
        try {
            WebElement element = findElement(locator);
            validateElement(element, locator);
            Log.info(LogInfo.LOG_GET_X + " del elemento: " + locator);
            Point location = element.getLocation();
            return location.getX();
        } catch (Exception e) {
            handleFail(locator, "No se pudo obtener la coordenada X del elemento: " + locator, e);
            return -1; // Retorno seguro en caso de fallo
        }
    }

    /**
     * Obtiene la coordenada Y de un elemento localizado en la página.
     *
     * @param locator localizador del elemento
     * @return coordenada Y del elemento, o -1 si ocurre un error
     */
    public int getY(By locator) {
        try {
            WebElement element = findElement(locator);
            validateElement(element, locator);
            Log.info(LogInfo.LOG_GET_Y + " del elemento: " + locator);
            Point location = element.getLocation();
            return location.getY();
        } catch (Exception e) {
            handleFail(locator, "No se pudo obtener la coordenada Y del elemento: " + locator, e);
            return -1; // Retorno seguro en caso de fallo
        }
    }

    /**
     * Toma una captura de pantalla de la página actual, la guarda en la carpeta de evidencias
     * y la adjunta a Allure para reportes.
     */
    public void screenShot() {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String folderName = generateFolderName();
            String basePath = getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                    ExternalData.ED_EVIDENCES,
                    ExternalData.ED_SOURCE);
            Path targetDir = Paths.get(basePath + folderName);

            Files.createDirectories(targetDir);
            FileUtils.copyFileToDirectory(screenshot, targetDir.toFile());

            Path screenshotPath = targetDir.resolve(screenshot.getName());
            try (InputStream is = Files.newInputStream(screenshotPath)) {
                Log.info(LogInfo.LOG_SCREENSHOT_SAVED + screenshotPath);
                Allure.attachment(Data.DATA_ALLURE_EVIDENCE + folderName, is);
            } catch (NoSuchFileException nsfe) {
                handleFailNoScreenshot(null, "No se encontró el archivo: " + screenshotPath, nsfe);
            }
        } catch (Exception e) {
            handleFailNoScreenshot(null, "Error al tomar screenshot: " + e.toString(), e);
        }
    }

    /**
     * Verifica si un elemento localizado es visible en la página.
     *
     * @param locator localizador del elemento
     * @return true si el elemento es visible, false si no lo es o si ocurre un timeout
     */
    public boolean elementDisplayedByLocator(By locator) {
        try {
            Log.info(LogInfo.LOG_ELEMENT_DISPLAYED + locator);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return wait.until(driver -> {
                try {
                    WebElement element = driver.findElement(locator);
                    return element.isDisplayed();
                } catch (NoSuchElementException | StaleElementReferenceException e) {
                    return false; // sigue esperando
                }
            });
        } catch (TimeoutException e) {
            Log.warn("Elemento no visible tras 10 segundos: " + locator);
            return false;
        }
    }

    /**
     * Comprueba si un elemento localizado es visible dentro de un tiempo de espera.
     *
     * @param locator localizador del elemento
     * @return true si el elemento se vuelve visible, false si no lo es o si ocurre un error
     */
    public boolean elementIsVisibleByLocator(By locator) {
        Log.info(LogInfo.LOG_ELEMENT_DISPLAYED + locator);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            boolean visible = element.isDisplayed();
            Log.info(LogInfo.LOG_ELEMENT_DISPLAYED + locator + " - Visible: " + visible);
            return visible;
        } catch (TimeoutException e) {
            Log.info("Elemento no visible dentro de 10 segundos: " + locator);
            return false;
        } catch (Exception e) {
            Log.warn("Error comprobando visibilidad de " + locator + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si un WebElement dado es visible en la página.
     *
     * @param element elemento a verificar
     * @return true si el elemento es visible, false si no lo es o ocurre un error
     */
    public boolean elementDisplayedByWebElement(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(element));
            Log.info("Elemento visible: " + element);
            return element.isDisplayed();
        } catch (TimeoutException e) {
            Log.warn("Elemento no visible tras 10 segundos: " + element);
            return false;
        } catch (StaleElementReferenceException e) {
            Log.warn("Elemento stale: " + element);
            return false;
        } catch (Exception e) {
            Log.error("Error inesperado al verificar visibilidad: " + element + " - " + e.getMessage());
            return false;
        }
    }

    /**
     * Espera a que un elemento sea clickeable, manejando overlays y reintentos en caso de
     * elementos obsoletos o interceptados, y realiza un click sobre él.
     *
     * @param locator localizador del elemento a clicar
     */
    public void waitForElementToBeClickable(By locator) {
        final int MAX_ATTEMPTS = 2;
        final int TIMEOUT_FIRST = 8;   // Tiempo máximo primer intento
        final int TIMEOUT_RETRY = 3;   // Tiempo máximo reintento

        Log.info("Intentando hacer clic en: " + locator);

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                // Verificar overlay
                if (isOverlayPresent()) {
                    waitForOverlayToDisappear();
                }

                // Espera explícita dependiendo del intento
                int timeout = (attempt == 1) ? TIMEOUT_FIRST : TIMEOUT_RETRY;
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

                // Espera que el elemento sea clickeable y recaptura si stale
                WebElement element = wait.until(ExpectedConditions.refreshed(
                        ExpectedConditions.elementToBeClickable(locator)
                ));

                // Scroll hasta el elemento visible
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);

                // Click normal
                element.click();
                Log.info("Clic realizado correctamente en intento: " + attempt);
                return;

            } catch (StaleElementReferenceException e) {
                Log.warn("Elemento obsoleto, recapturando... Intento " + attempt);
            } catch (ElementClickInterceptedException e) {
                Log.warn("Elemento interceptado, reintentando... Intento " + attempt);
            } catch (TimeoutException e) {
                Log.warn("Timeout esperando elemento clickeable: " + locator + " Intento " + attempt);
            } catch (Exception e) {
                Log.error("Error inesperado al hacer clic en: " + locator + " → " + e.getMessage());
            }
        }

        // Último recurso: click mediante JavaScript
        try {
            WebElement element = findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            Log.warn("Clic mediante JavaScript como último recurso en: " + locator);
        } catch (Exception jsEx) {
            handleFail(locator, "Elemento no clickeable: " + locator, jsEx);
        }
    }

    /**
     * Verifica si hay un overlay o loader visible en la página.
     *
     * @return true si hay un overlay visible, false en caso contrario
     */
    public boolean isOverlayPresent() {
        try {
            // Selector del overlay/loader de tu aplicación
            By overlayLocator = By.cssSelector(".overlay, .loader, .MuiBackdrop-root");

            List<WebElement> overlays = driver.findElements(overlayLocator);
            for (WebElement overlay : overlays) {
                if (overlay.isDisplayed()) {
                    return true; // Hay overlay visible
                }
            }
            return false; // No hay overlay
        } catch (Exception e) {
            return false; // En caso de error, asumimos que no hay overlay
        }
    }

    /**
     * Realiza una pausa corta de 2 segundos entre intentos de acciones repetidas.
     */
    private void waitShortBetweenAttempts() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Log.warn("Thread interrumpido durante la espera corta entre intentos.");
        }
    }

    /**
     * Espera a que un elemento sea clickeable y realiza el clic.
     * <p>
     * Si el clic falla debido a StaleElementReference, ElementClickIntercepted o Timeout,
     * reintenta hasta un máximo de 2 intentos. Si sigue fallando, realiza el clic mediante JavaScript.
     *
     * @param locator El {@link By} que identifica el elemento a hacer clic.
     */
    public void waitForElementToBeClickableJavascript(By locator) {
        final int MAX_ATTEMPTS = 2;
        final int TIMEOUT_SECONDS = 15;

        Log.info(LogInfo.LOG_WAIT_FOR_ELEMENT_TO_BE_CLICKABLE + " Elemento: " + locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                validateElement(element, locator);
                element.click();
                Log.info(LogInfo.LOG_ELEMENT_CLICKED + " Intento: " + attempt);
                return;

            } catch (StaleElementReferenceException | ElementClickInterceptedException | TimeoutException e) {
                Log.warn("Error al hacer clic en el elemento. Intento " + attempt + " → " + e.getMessage());
            } catch (Exception e) {
                Log.error("Error inesperado en waitForElementToBeClickableJavascript → " + e.getMessage());
            }

            waitShortBetweenAttempts();
        }

        try {
            WebElement element = findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            Log.warn("Clic mediante JavaScript como último recurso en: " + locator);
        } catch (Exception jsEx) {
            handleFail(locator, Mensajes.MENSAJE_ERROR_ELEMENTO_NO_CLICKEABLE + locator, jsEx);
        }
    }

    /**
     * Espera a que un elemento sea visible en la página web y lo valida.
     * <p>
     * Utiliza {@link FluentWait} con polling para reintentos y maneja elementos obsoletos (stale) hasta 3 intentos.
     * Si no se vuelve visible después de los intentos, registra un fallo.
     *
     * @param locator El {@link By} que identifica el elemento a esperar.
     * @throws IOException Si ocurre un error de E/S durante la espera o validación.
     */
    public void waitForVisibilityOfElementLocated(By locator) throws IOException {
        Log.info(LogInfo.LOG_WAIT_FOR_ELEMENT_TO_BE_CLICKABLE + locator);

        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(Data.DATA_TIME_OUT))
                .pollingEvery(Duration.ofSeconds(Data.DATA_EVALUATED_TIME))
                .ignoring(NoSuchElementException.class);

        final int MAX_ATTEMPTS = 3;

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                validateElement(element, locator);

                if (elementDisplayedByWebElement(element)) {
                    implicitWait(10);
                    Log.info("Elemento visible y validado en intento " + attempt);
                    return;
                }

            } catch (StaleElementReferenceException e) {
                Log.warn(Mensajes.MENSAJE_INTENTOS + attempt);
            } catch (TimeoutException e) {
                handleFail(locator, "Timeout esperando visibilidad del elemento: " + locator, e);
            } catch (Exception e) {
                handleFail(locator, "Error inesperado esperando visibilidad del elemento: " + locator, e);
            }

            waitShortBetweenAttempts();
        }

        handleFail(locator, "No se pudo validar la visibilidad del elemento después de varios intentos: " + locator, null);
    }

    /**
     * Espera y hace clic en un elemento dinámico dentro de una lista asociada a un elemento padre.
     * <p>
     * Primero obtiene el ID del elemento base y construye localizadores dinámicos para la lista y la opción deseada.
     * Utiliza {@link FluentWait} para esperar visibilidad y maneja intentos múltiples ante elementos obsoletos (stale).
     * Si no se logra hacer clic tras varios intentos, se registra un fallo.
     *
     * @param locator     El {@link By} que identifica el elemento padre de la lista dinámica.
     * @param optionValue El valor de la opción dentro de la lista dinámica que se desea seleccionar.
     * @throws IOException Si ocurre un error durante la espera o validación de los elementos.
     */
    public void waitForDinamicElementFromListToBeClickable(By locator, String optionValue) throws IOException {
        Log.info(LogInfo.LOG_WAIT_FOR_ELEMENT_TO_BE_CLICKABLE);

        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(Data.DATA_TIME_OUT))
                .pollingEvery(Duration.ofSeconds(Data.DATA_EVALUATED_TIME))
                .ignoring(NoSuchElementException.class);

        WebElement element = findElement(locator);
        validateElement(element, locator);

        String elementID = element.getDomAttribute(Data.DATA_ID);
        Log.info(LogInfo.LOG_DYNAMIC_ELEMENT_ID_OBTAINED + elementID);

        By listLocator = getDinamicElementByID(elementID, Data.DATA_LISTBOX);
        Log.info(LogInfo.LOG_DYNAMIC_LIST_BOX_ID + listLocator);

        wait.until(ExpectedConditions.visibilityOfElementLocated(listLocator));

        final int MAX_ATTEMPTS = 3;
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                By optionLocator = getDinamicElementByID(elementID, optionValue);
                WebElement optionElement = findElement(optionLocator);
                validateElement(optionElement, optionLocator);

                Log.info(LogInfo.LOG_DYNAMIC_OPTION + optionLocator);
                ((JavascriptExecutor) driver).executeScript(Data.DATA_SCROLL_INTO_VIEW_TRUE_JS, optionElement);
                javascriptClickToWebElement(optionElement);

                Log.info("Elemento dinámico clickeado exitosamente en intento " + attempt);
                return;

            } catch (StaleElementReferenceException e) {
                Log.warn(Mensajes.MENSAJE_INTENTOS + attempt);
            } catch (Exception e) {
                handleFail(locator, "Error al hacer clic en opción dinámica: " + optionValue, e);
            }

            waitShortBetweenAttempts();
        }

        handleFail(locator, "No se pudo hacer clic en la opción dinámica después de varios intentos: " + optionValue, null);
    }

    /**
     * Construye un localizador dinámico {@link By} a partir de un ID base y un sufijo o tipo.
     *
     * @param id   El ID base del elemento.
     * @param tipo El sufijo o tipo que se concatena al ID para formar el ID final.
     * @return Un objeto {@link By} que permite localizar el elemento dinámico por su ID completo.
     */
    public By getDinamicElementByID(String id, String tipo) {
        return By.id(id + tipo);
    }

    /**
     * Construye un localizador dinámico {@link By} a partir de un ID base, un sufijo y un método de localización.
     *
     * @param id     El ID base del elemento.
     * @param tipo   El sufijo o tipo que se concatena al ID para formar el selector final.
     * @param method El método de localización a utilizar ("id", "xpath", "css").
     * @return Un objeto {@link By} que permite localizar el elemento dinámico según el método especificado.
     * @throws IllegalArgumentException Si se proporciona un método no soportado.
     */
    public By getDinamicElementByMethod(String id, String tipo, String method) {
        switch (method.toLowerCase()) {
            case "id":
                return By.id(id + tipo);
            case "xpath":
                return By.xpath(id + tipo);
            case "css":
                return By.cssSelector(id + tipo);
            default:
                throw new IllegalArgumentException("Método no soportado: " + method);
        }
    }

    /**
     * Configura un tiempo de espera implícito para la búsqueda de elementos en el {@link WebDriver}.
     *
     * @param time El tiempo en segundos que el driver esperará al buscar elementos antes de lanzar {@link NoSuchElementException}.
     */
    public void implicitWait(long time) {
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
    }

    /**
     * Busca un elemento realizando una navegación vertical entre dos elementos de referencia y hace scroll hasta él.
     *
     * @param element El {@link By} del elemento que se desea encontrar.
     * @param start   El {@link By} del elemento de inicio para la navegación vertical.
     * @param finish  El {@link By} del elemento final que limita la navegación vertical.
     * @throws IOException Si ocurre un error durante la búsqueda o al interactuar con el elemento.
     */
    public void searchElementByCoordinates(By element, By start, By finish) throws IOException {
        Log.info(LogInfo.LOG_SEARCH_FOR_ELEMENT_WITH_VERTICAL_NAVIGATION + element);
        int startY = getY(start);
        int finishY = getY(finish);
        boolean searching = true;

        while (searching) {
            if (elementDisplayedByLocator(element)) {
                searching = false;
                navigate(0, getY(element));
                break;
            }

            if (startY >= finishY) {
                searching = false;
                break;
            }

            navigate(0, startY);
            startY += Data.DATA_NAVIGATE_Y;
        }

        waitForElementToBeClickable(element);
    }

    /**
     * Busca un elemento realizando scroll vertical de manera incremental hasta un límite máximo y lo interactúa.
     * Si no se encuentra dentro del rango definido, toma un screenshot y falla la ejecución.
     *
     * @param element El {@link By} del elemento que se desea buscar e interactuar.
     * @throws IOException Si ocurre un error durante la búsqueda, el scroll o la interacción con el elemento.
     */
    public void searchElement(By element) throws IOException {
        Log.info(LogInfo.LOG_SEARCH_FOR_ELEMENT_WITH_VERTICAL_NAVIGATION + element);

        int startY = 0;
        int maxY = 2000;

        boolean searching = true;

        while (searching) {
            if (elementDisplayedByLocator(element)) {
                searching = false;
                navigate(0, getY(element));
                break;
            }

            if (startY >= maxY) {
                searching = false;
                Log.warn("Elemento no encontrado dentro del rango de scroll: " + element);
                screenShot();
                fail("No se pudo localizar el elemento: " + element);
            }

            navigate(0, startY);
            startY += Data.DATA_NAVIGATE_Y;
        }

        waitForElementToBeClickable(element);
        javascriptClickToLocator(element);
    }

    /**
     * Realiza un clic sobre un elemento utilizando JavaScript.
     * Si el elemento no es visible, toma un screenshot y falla la ejecución.
     *
     * @param locator El {@link By} del elemento sobre el cual se realizará el clic.
     * @throws IOException Si ocurre un error al localizar el elemento o al interactuar con él mediante JavaScript.
     */
    public void javascriptClickToLocator(By locator) throws IOException {
        Log.info(LogInfo.LOG_CLICK_WITH_JAVASCRIPT + locator);
        try {
            WebElement element = findElement(locator);
            if (elementDisplayedByWebElement(element)) {
                ((JavascriptExecutor) driver).executeScript(Data.DATA_CLICK_JS, element);
                Log.info("Clic realizado con JavaScript: " + locator);
            } else {
                screenShot();
                fail("Elemento no visible para clic con JavaScript: " + locator);
            }
        } catch (Exception e) {
            screenShot();
            Log.error("Error al hacer clic con JavaScript en: " + locator + " → " + e.getMessage());
            fail(e.getMessage());
        }
    }

    /**
     * Realiza un clic sobre un {@link WebElement} utilizando JavaScript.
     * Si el elemento es nulo o no está visible, toma un screenshot y falla la ejecución.
     *
     * @param element El {@link WebElement} sobre el cual se realizará el clic.
     * @throws IOException Si ocurre un error al interactuar con el elemento mediante JavaScript.
     */
    public void javascriptClickToWebElement(WebElement element) throws IOException {
        Log.info(LogInfo.LOG_CLICK_WITH_JAVASCRIPT + element);
        try {
            if (element != null && elementDisplayedByWebElement(element)) {
                ((JavascriptExecutor) driver).executeScript(Data.DATA_CLICK_JS, element);
                Log.info("Clic realizado con JavaScript en WebElement: " + element);
            } else {
                screenShot();
                fail("Elemento no visible o nulo para clic con JavaScript: " + element);
            }
        } catch (Exception e) {
            screenShot();
            Log.error("Error al hacer clic con JavaScript en WebElement: " + element + " → " + e.getMessage());
            fail(e.getMessage());
        }
    }

    /**
     * Desplaza la vista del navegador hacia el {@link WebElement} proporcionado usando JavaScript.
     * Si el elemento es nulo o no está visible, toma un screenshot y falla la ejecución.
     *
     * @param element El {@link WebElement} al que se desea navegar.
     * @throws IOException Si ocurre un error al desplazar la vista mediante JavaScript.
     */
    public void navigateToWebElementWithJavascript(WebElement element) throws IOException {
        Log.info(LogInfo.LOG_NAVIGATE_TO_LOCATOR_WITH_JAVASCRIPT + element);
        try {
            if (element != null && elementDisplayedByWebElement(element)) {
                ((JavascriptExecutor) driver).executeScript(Data.DATA_SCROLL_INTO_VIEW_TRUE_JS, element);
                Log.info("Navegación realizada con JavaScript hacia WebElement: " + element);
            } else {
                screenShot();
                fail("Elemento nulo o no visible para navegación con JavaScript: " + element);
            }
        } catch (Exception e) {
            screenShot();
            Log.error("Error al navegar con JavaScript hacia WebElement: " + element + " → " + e.getMessage());
            fail(e.getMessage());
        }
    }

    /**
     * Desplaza la vista del navegador hacia el elemento identificado por el {@link By} proporcionado usando JavaScript.
     * Incluye una espera breve antes de la navegación. Si el elemento es nulo o no está visible, toma un screenshot y falla la ejecución.
     *
     * @param locator El {@link By} que identifica el elemento al que se desea navegar.
     * @throws IOException Si ocurre un error al desplazar la vista mediante JavaScript.
     */
    public void navigateToLocatorWithJavascript(By locator) throws IOException {
        Log.info(LogInfo.LOG_NAVIGATE_TO_LOCATOR_WITH_JAVASCRIPT + locator);
        threadWait(2000L);
        try {
            WebElement element = findElement(locator);
            if (element != null && elementDisplayedByWebElement(element)) {
                ((JavascriptExecutor) driver).executeScript(Data.DATA_SCROLL_INTO_VIEW_TRUE_JS, element);
                Log.info("Navegación realizada con JavaScript hacia locator: " + locator);
            } else {
                screenShot();
                fail("Elemento nulo o no visible para navegación con JavaScript: " + locator);
            }
        } catch (Exception e) {
            screenShot();
            Log.error("Error al navegar con JavaScript hacia locator: " + locator + " → " + e.getMessage());
            fail(e.getMessage());
        }
    }

    /**
     * Obtiene de manera segura un valor de un archivo JSON dado su clave.
     * Si el dato no existe o hay un error leyendo el archivo, toma un screenshot y falla la ejecución.
     *
     * @param jsonData La clave del dato que se desea obtener del JSON.
     * @param source   La ruta del archivo JSON.
     * @return El valor correspondiente a la clave proporcionada.
     * @throws IOException    Si ocurre un error de entrada/salida al leer el archivo.
     * @throws ParseException Si ocurre un error al parsear el JSON.
     */
    public String getJsonDataSafe(String jsonData, String source) throws IOException, ParseException {
        Log.info(LogInfo.LOG_GET_JSON_DATA_FROM_FILE + source);
        try {
            Object obj = new JSONParser().parse(new FileReader(source));
            JSONObject jo = (JSONObject) obj;
            String data = (String) jo.get(jsonData);
            if (data == null) {
                screenShot();
                fail("No se encontró el dato '" + jsonData + "' en el archivo JSON: " + source);
            }
            Log.info(LogInfo.LOG_JSON_DATA_OBTAINED + data);
            return data;
        } catch (FileNotFoundException e) {
            screenShot();
            Log.error("Archivo JSON no encontrado: " + source + " → " + e.getMessage());
            fail("Archivo JSON no encontrado: " + e.getMessage());
        } catch (IOException | ParseException e) {
            screenShot();
            Log.error("Error leyendo JSON desde: " + source + " → " + e.getMessage());
            fail("Error leyendo JSON: " + e.getMessage());
        }
        return null;
    }

    /**
     * Obtiene un valor de un archivo JSON usando un objeto y una clave específica.
     * Si el archivo no existe o el dato no se encuentra, toma un screenshot y falla la ejecución.
     *
     * @param object El nombre del objeto en el JSON.
     * @param data   La clave dentro del objeto que se desea obtener.
     * @param source La ruta del archivo JSON.
     * @return El valor como String correspondiente al objeto y clave proporcionados.
     * @throws IOException Si ocurre un error de lectura del archivo JSON.
     */
    public String getJsonString(String object, String data, String source) throws IOException {
        Log.info(LogInfo.LOG_GET_JSON_DATA_FROM_FILE + source);
        try {
            File jsonFile = new File(source);
            if (!jsonFile.exists()) {
                screenShot();
                Log.error("Archivo JSON no encontrado: " + source);
                fail("Archivo JSON no encontrado: " + source);
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonFile);
            String value = root.path(object).path(data).asText();

            if (value == null || value.isEmpty()) {
                screenShot();
                Log.error("Dato no encontrado en JSON: object=" + object + ", data=" + data);
                fail("Dato no encontrado en JSON: object=" + object + ", data=" + data);
            }

            Log.info(LogInfo.LOG_JSON_DATA_OBTAINED + value);
            return value;
        } catch (IOException e) {
            screenShot();
            Log.error("Error leyendo JSON desde: " + source + " → " + e.getMessage());
            fail("Error leyendo JSON: " + e.getMessage());
        }
        return null;
    }

    /**
     * Obtiene un valor booleano de un archivo JSON usando un objeto y una clave específica.
     * Si el archivo no existe o el dato booleano no se encuentra, toma un screenshot y falla la ejecución.
     *
     * @param object El nombre del objeto en el JSON.
     * @param data   La clave dentro del objeto cuyo valor booleano se desea obtener.
     * @param source La ruta del archivo JSON.
     * @return El valor booleano correspondiente al objeto y clave proporcionados.
     * @throws IOException Si ocurre un error de lectura del archivo JSON.
     */
    public boolean getJsonBoolean(String object, String data, String source) throws IOException {
        Log.info(LogInfo.LOG_GET_JSON_DATA_FROM_FILE + source);
        try {
            File jsonFile = new File(source);
            if (!jsonFile.exists()) {
                screenShot();
                Log.error("Archivo JSON no encontrado: " + source);
                fail("Archivo JSON no encontrado: " + source);
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonFile);
            JsonNode node = root.path(object).path(data);

            if (node.isMissingNode()) {
                screenShot();
                Log.error("Dato booleano no encontrado en JSON: object=" + object + ", data=" + data);
                fail("Dato booleano no encontrado en JSON: object=" + object + ", data=" + data);
            }

            boolean value = node.asBoolean();
            Log.info(LogInfo.LOG_JSON_DATA_OBTAINED + value);
            return value;
        } catch (IOException e) {
            screenShot();
            Log.error("Error leyendo JSON desde: " + source + " → " + e.getMessage());
            fail("Error leyendo JSON: " + e.getMessage());
        }
        return false;
    }

    /**
     * Obtiene un valor entero de un archivo JSON usando un objeto y una clave específica.
     * Si el archivo no existe o el dato entero no se encuentra, toma un screenshot y falla la ejecución.
     *
     * @param object El nombre del objeto en el JSON.
     * @param data   La clave dentro del objeto cuyo valor entero se desea obtener.
     * @param source La ruta del archivo JSON.
     * @return El valor entero correspondiente al objeto y clave proporcionados.
     * @throws IOException Si ocurre un error de lectura del archivo JSON.
     */
    public int getJsonInt(String object, String data, String source) throws IOException {
        Log.info(LogInfo.LOG_GET_JSON_DATA_FROM_FILE + source);
        try {
            File jsonFile = new File(source);
            if (!jsonFile.exists()) {
                screenShot();
                Log.error("Archivo JSON no encontrado: " + source);
                fail("Archivo JSON no encontrado: " + source);
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonFile);
            JsonNode node = root.path(object).path(data);

            if (node.isMissingNode()) {
                screenShot();
                Log.error("Dato entero no encontrado en JSON: object=" + object + ", data=" + data);
                fail("Dato entero no encontrado en JSON: object=" + object + ", data=" + data);
            }

            int value = node.asInt();
            Log.info(LogInfo.LOG_JSON_DATA_OBTAINED + value);
            return value;
        } catch (IOException e) {
            screenShot();
            Log.error("Error leyendo JSON desde: " + source + " → " + e.getMessage());
            fail("Error leyendo JSON: " + e.getMessage());
        }
        return 0;
    }

    public void uploadFiles(By locator, String filePath) {
        Log.info(LogInfo.LOG_UPLOAD + filePath);
        try {
            WebElement fileInput = driver.findElement(locator);
            String absolutePath = Paths.get(filePath).toAbsolutePath().toString();
            fileInput.sendKeys(absolutePath);
            Log.info("Archivo subido correctamente: " + absolutePath);
        } catch (Exception e) {
            Log.error("Error subiendo archivo: " + filePath + " → " + e.getMessage());
            screenShot();
            fail("No se pudo subir el archivo: " + filePath);
        }
    }

    /**
     * Descarga archivos de la aplicación web mediante un locator, espera a que la descarga
     * finalice, los mueve a un directorio de evidencia y los adjunta a los reportes de Allure.
     *
     * @param fileType la extensión o tipo de archivo a descargar (por ejemplo, ".pdf")
     * @param locator  el By que localiza el elemento que inicia la descarga
     * @throws IOException si ocurre un error durante la descarga, creación de directorios o manejo de archivos
     */
    public void downloadFiles(String fileType, By locator) throws IOException {
        Log.info(LogInfo.LOG_DOWNLOAD_START);
        clickLocator(locator);

        String downloadPath = getJsonString(
                ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_DOWNLOADS,
                ExternalData.ED_SOURCE
        );
        String absolutePath = Paths.get(downloadPath).toAbsolutePath().toString();

        try {
            waitForFileDownload(fileType, absolutePath, 30);
        } catch (InterruptedException e) {
            Log.error(LogInfo.LOG_DOWNLOAD_ERROR_DYNAMIC_INTERRUPTION + e.getMessage());
            Thread.currentThread().interrupt();
            throw new IOException(LogInfo.LOG_DOWNLOAD_ERROR_INTERRUPTED_WAITING);
        } catch (RuntimeException e) {
            Log.error(e.getMessage());
            throw new IOException(e.getMessage());
        }

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

    /**
     * Espera a que un archivo del tipo especificado se descargue en un directorio dado.
     * Ignora archivos parcialmente descargados y lanza excepción si no aparece dentro del tiempo límite.
     *
     * @param fileType         extensión del archivo esperado (ej. ".pdf")
     * @param downloadPath     ruta del directorio de descargas
     * @param timeoutInSeconds tiempo máximo de espera en segundos
     * @throws InterruptedException si el hilo de espera es interrumpido
     * @throws RuntimeException     si no se encuentra el archivo dentro del tiempo límite
     */
    private void waitForFileDownload(String fileType, String downloadPath, int timeoutInSeconds) throws InterruptedException {
        //Log.info(LogInfo.LOG_WAIT_FOR_FILE_DOWNLOAD + downloadPath + " (tipo: " + fileType + ")");
        File dir = new File(downloadPath);
        int waited = 0;

        while (waited < timeoutInSeconds) {
            File[] files = dir.listFiles((d, name) -> name.endsWith(fileType) &&
                    !name.endsWith(Data.DATA_FILE_TYPE_CRDOWNLOAD));
            if (files != null && files.length > 0) {
                //Log.info(LogInfo.LOG_DOWNLOAD_FILE_FOUND + files[0].getName());
                return;
            }
            Thread.sleep(1000);
            waited++;
        }

        String errorMsg = LogInfo.LOG_DOWNLOAD_ERROR_TIMEOUT + downloadPath;
        Log.error(errorMsg);
        throw new RuntimeException(errorMsg);
    }

    /**
     * Refresca la página actual del navegador.
     * Registra un error y falla la ejecución si no se puede realizar el refresco.
     */
    public void refreshPage() {
        try {
            //Log.info(LogInfo.LOG_REFRESH_PAGE);
            driver.navigate().refresh();
            //Log.info(LogInfo.LOG_PAGE_REFRESHED);
        } catch (Exception e) {
            Log.error("Error al refrescar la página: " + e.getMessage());
            fail("No se pudo refrescar la página: " + e.getMessage());
        }
    }

    /**
     * Elimina todas las cookies del navegador.
     * Registra un error y falla la ejecución si no se pueden eliminar las cookies.
     */
    public void clearCookies() {
        try {
            //Log.info(LogInfo.LOG_CLEAR_COOKIES);
            driver.manage().deleteAllCookies();
            //Log.info(LogInfo.LOG_COOKIES_CLEARED);
        } catch (Exception e) {
            Log.error("Error al eliminar cookies: " + e.getMessage());
            fail("No se pudieron eliminar las cookies: " + e.getMessage());
        }
    }

    /**
     * Minimiza la ventana del navegador.
     * Registra un error y falla la ejecución si no se puede minimizar la ventana.
     */
    public void minimizeScreen() {
        try {
            //Log.info(LogInfo.LOG_MINIMIZE_SCREEN);
            driver.manage().window().minimize();
            //Log.info(LogInfo.LOG_SCREEN_MINIMIZED);
        } catch (Exception e) {
            Log.error("Error al minimizar la pantalla: " + e.getMessage());
            fail("No se pudo minimizar la pantalla: " + e.getMessage());
        }
    }

    /**
     * Cierra la ventana actual del navegador.
     * Registra un error y falla la ejecución si no se puede cerrar la ventana.
     */
    public void closeWindow() {
        try {
            //Log.info(LogInfo.LOG_CLOSE_WINDOW);
            driver.close();
            //Log.info(LogInfo.LOG_WINDOW_CLOSED);
        } catch (Exception e) {
            Log.error("Error al cerrar la ventana: " + e.getMessage());
            fail("No se pudo cerrar la ventana: " + e.getMessage());
        }
    }

    /**
     * Abre una nueva ventana del navegador y navega a la URL especificada.
     * Registra un error y falla la ejecución si no se puede abrir la ventana o cargar la URL.
     *
     * @param url La dirección web a la que se desea navegar en la nueva ventana.
     */
    public void openWindow(String url) {
        try {
            //Log.info(LogInfo.LOG_OPEN_NEW_WINDOW + url);
            WebDriver newWindow = driver.switchTo().newWindow(WindowType.WINDOW);
            newWindow.get(url);
            //Log.info(LogInfo.LOG_NEW_WINDOW_OPENED + url);
        } catch (Exception e) {
            Log.error("Error al abrir nueva ventana con URL " + url + ": " + e.getMessage());
            fail("No se pudo abrir nueva ventana: " + e.getMessage());
        }
    }

    /**
     * Maximiza la ventana actual del navegador.
     * Registra un error y falla la ejecución si no se puede maximizar la ventana.
     */
    public void maximizeScreen() {
        try {
            //Log.info(LogInfo.LOG_MAXIMIZE_SCREEN);
            driver.manage().window().maximize();
            //Log.info(LogInfo.LOG_SCREEN_MAXIMIZED);
        } catch (Exception e) {
            Log.error("Error al maximizar la pantalla: " + e.getMessage());
            fail("No se pudo maximizar la pantalla: " + e.getMessage());
        }
    }

    /**
     * Limpia todos los archivos contenidos en el directorio especificado.
     * Si el directorio no existe o no es válido, se registra una advertencia y no se realiza ninguna acción.
     *
     * @param src Ruta del directorio a limpiar.
     * @throws IOException Si ocurre un error al intentar limpiar el directorio.
     */
    public void cleanFolder(String src) throws IOException {
        Log.info(LogInfo.LOG_CLEAR_DIRECTORY + src);
        File folder = new File(src);

        if (!folder.exists() || !folder.isDirectory()) {
            Log.warn("El directorio no existe o no es un directorio válido: " + src);
            return;
        }

        try {
            FileUtils.cleanDirectory(folder);
            Log.info("Directorio limpiado correctamente: " + src);
        } catch (IOException e) {
            Log.error("Error al limpiar el directorio: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Valida que el elemento identificado por el locator proporcionado esté presente y visible en la página.
     * Registra la acción y falla la prueba si el elemento no está visible.
     *
     * @param locator Locator del elemento a validar.
     */
    public void validateElementIsDisplayedByLocator(By locator) {
        Log.info(LogInfo.LOG_ELEMENT_IS_PRESENT + locator);

        boolean isDisplayed = elementDisplayedByLocator(locator);
        assertTrue(isDisplayed, Mensajes.MENSAJE_ERROR_TEXTO + locator.toString());
    }

    /**
     * Valida que el texto del elemento identificado por el locator coincida con el texto esperado.
     * Registra la acción y falla la prueba si el texto real no coincide con el esperado.
     *
     * @param expectedText Texto esperado que debe contener el elemento.
     * @param locator Locator del elemento cuyo texto se va a validar.
     */
    public void validateElementText(String expectedText, By locator) {
        Log.info(LogInfo.LOG_ELEMENT_TEXT_IS_PRESENT + expectedText);

        String actualText = getTextByLocator(locator);
        assertEquals(expectedText, actualText,
                Mensajes.MENSAJE_ERROR_TEXTO + " Locator: " + locator.toString() +
                        ", Expected: " + expectedText + ", Actual: " + actualText);
    }

    /**
     * Devuelve la fecha y hora actual formateada según el patrón especificado.
     *
     * @param dateFormat Formato de fecha y hora siguiendo los patrones de {@link DateTimeFormatter}.
     * @return Fecha y hora actual como cadena formateada.
     */
    public String getDate(String dateFormat) {
        Log.info(LogInfo.LOG_GET_DATE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        LocalDateTime now = LocalDateTime.now();

        return now.format(formatter);
    }

    /**
     * Genera un nombre de carpeta único para almacenar evidencias de prueba,
     * combinando el navegador, el nombre del test actual y la fecha/hora actual.
     *
     * @return Ruta de la carpeta generada como cadena.
     * @throws IOException Si ocurre un error al obtener la fecha actual.
     */
    public String generateFolderName() throws IOException {
        String currentTestName = (testName.get() != null) ? testName.get() : "UnknownTest";
        String currentDate = (date != null) ? date : getDate("yyyy-MM-dd_HH-mm-ss");

        return Paths.get(browser, currentTestName, currentDate).toString();
    }

    /**
     * Valida todos los enlaces presentes en la página actual.
     * <p>
     * Clasifica los enlaces en enlaces válidos, enlaces rotos, enlaces nulos/vacíos
     * y enlaces que generan errores de servidor. Además, captura una captura
     * de pantalla al inicio del proceso y genera un reporte con el estado de cada enlace.
     *
     * @throws IOException Si ocurre un error al procesar la validación o los reportes.
     */
    public void verifyLinks() throws IOException {
        Log.info(LogInfo.LOG_LINK_VALIDATION_START);
        screenShot();

        List<WebElement> links = findElements(By.tagName(Data.DATA_LINK));

        List<String> brokenLinks = new ArrayList<>();
        List<String> okLinks = new ArrayList<>();
        List<String> nullLinks = new ArrayList<>();
        List<String> serverLinks = new ArrayList<>();

        for (WebElement link : links) {
            String url = link.getDomProperty(Data.DATA_HREF);
            Log.info("Verificando link: " + url);

            if (isNullOrEmpty(url)) {
                nullLinks.add(url);
                logAndAttach("Link nulo o vacío: " + url, Data.DATA_ALLURE_NULL_LINK);
                continue;
            }

            if (url.startsWith(Data.DATA_JS)) {
                verifySpecialLink(link, okLinks, brokenLinks);
            } else {
                verifyStandardLink(url, okLinks, brokenLinks, serverLinks);
            }
        }

        // Reporte final
        reportLinkValidation(links.size(), okLinks, brokenLinks, nullLinks, serverLinks);
    }

    /**
     * Verifica si una cadena de texto es nula o está vacía.
     *
     * @param url La cadena de texto a verificar.
     * @return true si la cadena es nula o está vacía; false en caso contrario.
     */
    private boolean isNullOrEmpty(String url) {
        return url == null || url.isEmpty();
    }

    /**
     * Registra un mensaje en los logs y lo adjunta como evidencia en Allure.
     *
     * @param logMessage      El mensaje que se desea registrar y adjuntar.
     * @param attachmentName  El nombre que se usará para la evidencia en Allure.
     */
    private void logAndAttach(String logMessage, String attachmentName) {
        Log.info(logMessage);
        Allure.attachment(attachmentName, logMessage);
    }

    /**
     * Verifica el estado de un enlace estándar (no JavaScript) realizando una solicitud HTTP HEAD.
     * Dependiendo del código de respuesta, clasifica el enlace como válido, roto o con error de servidor.
     *
     * @param url          La URL del enlace a verificar.
     * @param okLinks      Lista donde se agregan los enlaces válidos.
     * @param brokenLinks  Lista donde se agregan los enlaces que devuelven códigos de error HTTP (>= 400).
     * @param serverLinks  Lista donde se agregan los enlaces que no se pueden conectar debido a errores de servidor u otras excepciones.
     */
    private void verifyStandardLink(String url, List<String> okLinks, List<String> brokenLinks, List<String> serverLinks) {
        HttpURLConnection connection = null;
        try {
            URI uri = new URI(url);
            URL urla = uri.toURL();

            connection = (HttpURLConnection) urla.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            int responseCode = connection.getResponseCode();

            if (responseCode >= 400) {
                brokenLinks.add(url);
                logAndAttach("Link roto: " + url + " - Código: " + responseCode, Data.DATA_ALLURE_BROKEN_LINK);
            } else {
                okLinks.add(url);
                Log.info("Link OK: " + url);
            }
        } catch (Exception e) {
            serverLinks.add(url);
            logAndAttach("Error de conexión con el servidor para: " + url + " - " + e.getMessage(), Data.DATA_SERVER_LINK);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Verifica un enlace especial, registrando si está funcionando o roto.
     *
     * @param link el elemento WebElement del enlace
     * @param okLinks lista para los enlaces válidos
     * @param brokenLinks lista para los enlaces rotos
     */
    private void verifySpecialLink(WebElement link, List<String> okLinks, List<String> brokenLinks) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            js.executeScript(Data.DATA_SCROLL_INTO_VIEW_JS, link);
            clickWebElement(link);

            String linkText = getTextByWebElement(link);
            okLinks.add(linkText);
            Log.info("Link especial OK: " + linkText);
        } catch (NoSuchElementException e) {
            String linkText = getTextByWebElement(link);
            brokenLinks.add(linkText);
            Log.error("Link especial roto: " + linkText + " - " + e.getMessage());
        }
    }

    /**
     * Genera un reporte de validación de enlaces, registrando y adjuntando los resultados en Allure.
     *
     * @param totalLinks total de enlaces encontrados
     * @param okLinks lista de enlaces válidos
     * @param brokenLinks lista de enlaces rotos
     * @param nullLinks lista de enlaces nulos
     * @param serverLinks lista de enlaces con error de servidor
     */
    private void reportLinkValidation(int totalLinks, List<String> okLinks, List<String> brokenLinks,
                                      List<String> nullLinks, List<String> serverLinks) {
        Log.info("Total de links encontrados: " + totalLinks);
        Allure.attachment("Total Links", String.valueOf(totalLinks));

        Log.info("Links OK: " + okLinks.size());
        Allure.attachment("Total OK", String.valueOf(okLinks.size()));

        Log.info("Links Rotos: " + brokenLinks.size());
        Allure.attachment("Total Rotos", String.valueOf(brokenLinks.size()));
        brokenLinks.forEach(link -> Allure.attachment("Link Roto", link));

        Log.info("Links Nulos: " + nullLinks.size());
        Allure.attachment("Total Nulos", String.valueOf(nullLinks.size()));
        nullLinks.forEach(link -> Allure.attachment("Link Nulo", link));

        Log.info("Links con error de servidor: " + serverLinks.size());
        Allure.attachment("Total Server Down", String.valueOf(serverLinks.size()));
        serverLinks.forEach(link -> Allure.attachment("Server Down", link));
    }

    /**
     * Genera un RUT chileno aleatorio con su dígito verificador.
     *
     * @return un RUT válido en formato "XXXXXXXX-X"
     */
    public String generateRUT() {
        Random random = new Random();
        int numero = random.nextInt(100000000);
        char digitoVerificador = calculateVerifyDigit(numero);

        String rutBase = String.valueOf(numero).replaceFirst("^0+", "");

        if (rutBase.isEmpty()) {
            rutBase = "0";
        }

        return String.format("%s-%c", rutBase, digitoVerificador);
    }

    /**
     * Calcula el dígito verificador de un RUT chileno a partir de su número base.
     *
     * @param numero el número base del RUT
     * @return el dígito verificador como carácter ('0'-'9' o 'K')
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
     * <p>
     * Ajusta la carpeta de descargas, preferencias del navegador, modo headless,
     * visibilidad del navegador y tamaño de ventana según la configuración.
     *
     * @param downloadFilePath ruta opcional para la carpeta de descargas
     * @return un objeto ChromeOptions configurado
     * @throws IOException si ocurre un error al leer la configuración o crear directorios
     */
    public ChromeOptions chromeCustomConfiguration(String downloadFilePath) throws IOException {
        Log.info(LogInfo.LOG_CHROME_CONFIGURATION);

        ChromeOptions chromeOptions = new ChromeOptions();

        // Determinar la ruta de descarga
        String downloadPath = (downloadFilePath != null && !downloadFilePath.isEmpty())
                ? downloadFilePath
                : System.getProperty(Data.DATA_SYSTEM_PROPERTY_TMPDIR) + Data.DATA_BROWSER_DEFAULT_DOWNLOAD_DIRECTORY;

        // Obtener la ruta absoluta desde el JSON de configuración
        String pathFromJson = getJsonString(
                ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_DOWNLOADS,
                ExternalData.ED_SOURCE
        );

        String absolutePath = Paths.get(pathFromJson).toAbsolutePath().toString();

        // Crear directorio de descarga si no existe
        File downloadDir = new File(absolutePath);
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }

        // Configurar preferencias del navegador
        Map<String, Object> prefs = new HashMap<>();
        prefs.put(Data.DATA_BROWSER_DOWNLOAD_DIRECTORY, absolutePath);
        prefs.put(Data.DATA_BROWSER_DOWNLOAD_PROMPT, false);
        prefs.put(Data.DATA_BROWSER_SETTINGS_POPUPS, 0);
        prefs.put(Data.DATA_BROWSER_OPEN_PDF_EXTERNAL, true);
        prefs.put(Data.DATA_BROWSER_PASSWORD_LEAK_DETECTION, false);

        chromeOptions.setExperimentalOption(Data.DATA_BROWSER_DOWNLOAD_PREFS, prefs);

        // Configurar modo headless si está activado
        if (getJsonBoolean(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_HEADLESS, ExternalData.ED_SOURCE)) {
            chromeOptions.addArguments(Data.BC_PREF_HEADLESS);
        }

        // Configurar navegador oculto si está activado
        if (getJsonBoolean(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_WEBDRIVER_HIDDEN, ExternalData.ED_SOURCE)) {
            chromeOptions.addArguments(Data.DATA_WEBDRIVER_HIDDEN);
        }

        // Configurar tamaño de ventana
        String browserSize = getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_BROWSER_SIZE, ExternalData.ED_SOURCE);
        chromeOptions.addArguments(browserSize);

        Base.browser = Data.DATA_BROWSER_CHROME;

        return chromeOptions;
    }

    /*public EdgeOptions edgeCustomConfiguration() {
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
    }*/

    /*public FirefoxOptions firefoxCustomConfiguration(String key, int value, String directory, String downloadFilePath) {
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
    }*/

    /**
     * Verifica si hay una alerta presente en la página.
     *
     * @return true si hay una alerta, false en caso contrario
     */
    public boolean isAlertPresent() {
        return getAlert() != null;
    }

    /**
     * Acepta la alerta actualmente presente en la página, si existe.
     */
    public void acceptAlert() {
        Alert alert = getAlert();
        if (alert != null) {
            alert.accept();
            Log.info("Alerta aceptada.");
        }
    }

    /**
     * Descarta la alerta actualmente presente en la página, si existe.
     */
    public void dismissAlert() {
        Alert alert = getAlert();
        if (alert != null) {
            alert.dismiss();
            Log.info("Alerta descartada.");
        }
    }

    /**
     * Obtiene la alerta actualmente presente en la página.
     *
     * @return el objeto Alert si existe, o null si no hay ninguna alerta
     */
    private Alert getAlert() {
        try {
            return driver.switchTo().alert();
        } catch (NoAlertPresentException e) {
            return null;
        }
    }

    /**
     * Selecciona una opción de un elemento interactivo (como un menú desplegable) en la página.
     *
     * @param elemento1 localizador del campo principal a interactuar
     * @param elemento2 localizador del elemento clicable para abrir la selección
     * @param seleccion localizador de la opción a seleccionar
     * @throws IOException si ocurre un error durante la interacción
     */
    public void selectInteractiveOption(By elemento1, By elemento2, By seleccion) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(elemento1));
        actions.moveToElement(campo).perform();

        WebElement select = wait.until(ExpectedConditions.elementToBeClickable(elemento2));
        select.click();

        WebElement opcion = wait.until(ExpectedConditions.visibilityOfElementLocated(seleccion));

        js.executeScript("arguments[0].click();", opcion);
    }

    /**
     * Espera a que los elementos de overlay o spinner desaparezcan de la página.
     *
     * @throws IOException si ocurre un error al capturar la pantalla en caso de timeout
     */
    private void waitForOverlayToDisappear() throws IOException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".overlay, .spinner")));
        } catch (TimeoutException ignored) {
            Log.error("Error en la espera de overlay");
            screenShot();
        }
    }

    /**
     * Valida que un elemento web exista, esté visible y habilitado.
     *
     * @param element el WebElement a validar
     * @param locator el localizador del elemento para los mensajes de error
     */
    private void validateElement(WebElement element, By locator) {
        if (element == null) {
            handleFail(locator, AssertInfo.ASSERT_ELEMENT_NOT_NULL + locator, null);
        }

        if (!element.isDisplayed()) {
            handleFail(locator, "Error: El elemento encontrado no está visible en el DOM. Locator: " + locator, null);
        }

        /*if (!element.isEnabled()) {
            handleFail(locator, "Error: El elemento encontrado no está habilitado en el DOM. Locator: " + locator, null);
        }*/
    }

    /**
     * Maneja una falla en la prueba: toma captura, registra el error y falla la aserción.
     *
     * @param locator el localizador asociado al error
     * @param message mensaje de error a registrar
     * @param e excepción opcional que causó la falla
     */
    private void handleFail(By locator, String message, Exception e) {
        screenShot();
        Log.error(message + (e != null ? ", " + e.getMessage() : ""));
        Assert.fail(message);
    }

    /**
     * Maneja una falla en la prueba sin tomar captura de pantalla: registra el error y falla la aserción.
     *
     * @param locator el localizador asociado al error
     * @param message mensaje de error a registrar
     * @param e excepción opcional que causó la falla
     */
    private void handleFailNoScreenshot(By locator, String message, Exception e) {
        Log.error(message + (e != null ? ", " + e.getMessage() : ""));
        Assert.fail(message);
    }

    /**
     * Realiza un clic en un switch de Material Design, usando distintos métodos según sea necesario
     * para garantizar la interacción (clic normal, Actions o evento JS).
     *
     * @param locator el localizador del switch a clicar
     */
    public void clickMaterialSwitch(By locator) {
        final int TIMEOUT_SECONDS = 10;

        Log.info("Intentando hacer clic en switch: " + locator);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));

            // Esperar que el input esté presente
            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            // Scroll al centro
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center', inline: 'center'});", input);

            // Esperar a que sea clickeable
            wait.until(ExpectedConditions.elementToBeClickable(input));

            try {
                // Intentar click normal primero
                input.click();
                Log.info("Click normal realizado en el switch.");
                return;

            } catch (ElementClickInterceptedException e) {
                Log.warn("Click interceptado, reintentando con Actions...");

                Thread.sleep(400); // dar tiempo a posibles animaciones

                // Click con Actions (input invisible pero activo)
                new Actions(driver)
                        .moveToElement(input)
                        .click()
                        .perform();

                Log.info("Click realizado mediante Actions sobre el switch.");
                return;

            } catch (Exception e2) {
                Log.warn("Click normal/Actions falló, intentando MouseEvent JS...");

                // JS: lanza un MouseEvent real para que React lo capture
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));",
                        input);

                Log.info("Click simulado con MouseEvent JS sobre el switch.");
                return;
            }

        } catch (TimeoutException e) {
            Log.error("Timeout: no se encontró el switch: " + locator);
        } catch (Exception e) {
            Log.error("Error al intentar hacer click en el switch: " + locator + " → " + e.getMessage());
        }
    }

    /**
     * Busca un texto en un campo de autocompletado y selecciona la primera opción disponible.
     *
     * @param locator el localizador del trigger o campo del autocompletado
     * @param textToSearch el texto a buscar en el autocompletado
     */
    public void selectFirstFromAutocomplete(By locator, String textToSearch) {
        final int TIMEOUT_SECONDS = 10;

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));

            // Esperar el trigger visible (por ejemplo, <p>---</p>)
            WebElement trigger = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            // Mover el mouse sobre el trigger para que se active el campo
            new Actions(driver).moveToElement(trigger).perform();
            Thread.sleep(400); // pequeño delay para que se renderice el autocomplete

            // Localizar el input del autocomplete (aparece dinámicamente)
            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("auto-complete")));

            // Click en el input y escribir el texto de búsqueda
            input.click();
            input.clear();
            input.sendKeys(textToSearch);

            // Esperar a que aparezcan las opciones en el listbox
            By listOptions = By.cssSelector("ul.MuiAutocomplete-listbox li");
            wait.until(ExpectedConditions.visibilityOfElementLocated(listOptions));

            // Seleccionar la primera opción de la lista
            List<WebElement> options = driver.findElements(listOptions);
            if (!options.isEmpty()) {
                WebElement firstOption = options.get(0);
                new Actions(driver).moveToElement(firstOption).click().perform();
                Log.info("Seleccionada primera opción del autocomplete: " + firstOption.getText());
            } else {
                Log.warn("No se encontraron opciones para: " + textToSearch);
            }

        } catch (Exception e) {
            Log.error("Error al seleccionar la primera opción del autocomplete: " + e.getMessage());
        }
    }

    /**
     * Selecciona una opción específica de un MUI Select (combobox) en la página.
     *
     * @param hoverLocator el localizador del elemento a posicionar el mouse para activar el combobox
     * @param optionText el texto de la opción a seleccionar
     */
    public void selectOptionFromMuiSelect(By hoverLocator, String optionText) {
        final int TIMEOUT_SECONDS = 10;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));

            // Posicionar el mouse sobre el div inicial para generar el combobox
            WebElement hoverElement = wait.until(ExpectedConditions.visibilityOfElementLocated(hoverLocator));
            new Actions(driver).moveToElement(hoverElement).perform();

            // Esperar que aparezca el combobox
            By comboBoxLocator = By.xpath("//div[p[text()='Es procedente']]//div[@role='combobox']");
            WebElement comboBox = wait.until(ExpectedConditions.elementToBeClickable(comboBoxLocator));
            new Actions(driver).moveToElement(comboBox).click().perform();

            // Esperar que aparezca el listado
            By listItemsLocator = By.cssSelector("ul[role='listbox'] li");
            wait.until(ExpectedConditions.visibilityOfElementLocated(listItemsLocator));

            // Seleccionar la opción deseada
            List<WebElement> options = driver.findElements(listItemsLocator);
            boolean selected = false;
            for (WebElement option : options) {
                if (option.getText().trim().equals(optionText)) {
                    new Actions(driver).moveToElement(option).click().perform();
                    Log.info("Seleccionada opción: " + optionText);
                    selected = true;
                    break;
                }
            }
            if (!selected) {
                Log.warn("No se encontró la opción: " + optionText);
            }

        } catch (Exception e) {
            Log.error("Error al seleccionar opción del MUI Select: " + e.getMessage());
        }
    }

    /**
     * Mueve el cursor del mouse hasta el elemento especificado.
     *
     * @param locator el localizador del elemento a donde mover el cursor
     */
    public void moveToElement(By locator){
        WebElement element = findElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    /**
     * Cambia el control del WebDriver a una nueva pestaña del navegador abierta.
     * Espera hasta que haya más de una pestaña y luego selecciona la que no es la original.
     *
     * @param driver el WebDriver que controla el navegador
     */
    public static void switchToNewTab(WebDriver driver) {
        String originalWindow = driver.getWindowHandle();

        // Espera hasta que haya más de una pestaña abierta
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.getWindowHandles().size() > 1);

        // Itera sobre los window handles y cambia a la nueva pestaña
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }
}