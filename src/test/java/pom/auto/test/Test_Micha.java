package pom.auto.test;

import io.qameta.allure.*;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pom.Base;
import pom.auto.page.Page;
import pom.auto.repository.TestDescription;
import pom.general_repository.BrowserConfiguration;
import pom.general_repository.Data;
import pom.general_repository.ExternalData;
import pom.general_repository.LogInfo;
import utils.Log;

import java.io.IOException;

import static pom.Base.testName;

public class Test_Micha {

    /**
     * Objeto WebDriver utilizado para controlar el navegador en las pruebas automatizadas.
     * <p>
     * Este objeto se inicializa según el tipo de navegador configurado en el método de preparación.
     * Permite interactuar con el navegador durante la ejecución de las pruebas.
     * </p>
     */
    public WebDriver driver;

    /**
     * Objeto de la clase {@link Page} que representa las páginas del sistema CRM.
     * <p>
     * Esta variable se utiliza para realizar operaciones específicas en las páginas
     * del sistema durante la ejecución de pruebas automatizadas. Se inicializa y configura
     * según las necesidades del flujo de prueba.
     * </p>
     */
    public Page page;

    /**
     * Configura el entorno inicial para la ejecución de pruebas basado en el tipo de navegador proporcionado.
     * <p>
     * Este método está marcado con {@link BeforeClass} y se ejecuta antes de que inicie cualquier clase de prueba.
     * Utiliza el parámetro {@code BrowserType} para determinar el navegador a configurar. Según el navegador
     * especificado, inicializa el WebDriver correspondiente y aplica configuraciones personalizadas.
     * </p>
     *
     * <p>Caso de uso de navegadores:</p>
     * <ul>
     *   <li><b>Chrome:</b> Configuración personalizada usando {@code chromeDriverConnectionOptions}.</li>
     *   <li><b>Edge:</b> Configuración personalizada usando {@code edgeConnectionOptions}.</li>
     *   <li><b>Firefox:</b> Configuración personalizada usando {@code firefoxDriverConnectionOptions}.</li>
     * </ul>
     *
     * @param browserType Tipo de navegador que se va a configurar (por ejemplo, "Chrome", "Edge" o "Firefox").
     * @throws IOException si ocurre un problema al aplicar configuraciones específicas del navegador.
     */
    @BeforeClass
    @Parameters({"BrowserType"})
    public void setUp(String browserType) throws IOException {
        switch (browserType) {
            case "Chrome":
                page = new Page(driver);
                page.chromeDriverConnectionOptions(page.chromeCustomConfiguration(page.getJsonString(
                        ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                        ExternalData.ED_DOWNLOADS,
                        ExternalData.ED_SOURCE)));
                break;
            case "Edge":
                page = new Page(driver);
                page.edgeConnectionOptions(page.edgeCustomConfiguration());
                break;
            case "Firefox":
                page = new Page(driver);
                page.firefoxDriverConnectionOptions(page.firefoxCustomConfiguration(
                        BrowserConfiguration.BC_PREF_DOWNLOAD_DIRECTORY_FOLDER_LIST_KEY_FIREFOX,
                        BrowserConfiguration.BC_PREF_DOWNLOAD_DIRECTORY_FOLDER_LIST_VALUE_FIREFOX,
                        BrowserConfiguration.BC_PREF_DOWNLOAD_DIRECTORY_FIREFOX, page.getJsonString(
                                ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION, ExternalData.ED_DOWNLOADS,
                                ExternalData.ED_SOURCE)));
                break;
            default:
        }
    }

    /**
     * Realiza el flujo de prueba para la consulta presencial de trámites en el sistema CRM.
     * <p>
     * Este método está marcado con {@link Test} y contiene múltiples anotaciones para
     * describir la prueba, incluyendo {@link Story}, {@link Description}, {@link Severity},
     * y otras relacionadas con el sistema de pruebas.
     * El flujo de prueba incluye acciones como visitar la URL especificada, limpiar cookies,
     * y ejecutar el método correspondiente en la página.
     * </p>
     *
     * <ul>
     *   <li><b>Historia:</b> {@code TestDescription.DESC_CRM_HU_01}</li>
     *   <li><b>Descripción:</b> {@code TestDescription.DESC_CRM_CREAR_CASO}</li>
     *   <li><b>Severidad:</b> {@code SeverityLevel.NORMAL}</li>
     *   <li><b>Propietario:</b> {@code TestDescription_Example.DESC_OWNER_MV}</li>
     *   <li><b>Enlace:</b> {@code Data_CRM.DATA_CRM_URL_CREAR_CASO}</li>
     *   <li><b>Problema relacionado:</b> {@code AUTH-123}</li>
     *   <li><b>Referencia TMS:</b> {@code TMS-456}</li>
     * </ul>
     * <p>
     * Detalle de las acciones realizadas:
     * <ol>
     *   <li>Registro de fecha y nombre de la prueba.</li>
     *   <li>Visita a la URL especificada en el sistema CRM.</li>
     *   <li>Limpieza de cookies antes de ejecutar el flujo de prueba.</li>
     *   <li>Ejecución de la consulta presencial de trámites.</li>
     * </ol>
     */
    @Test
    @Story(TestDescription.DESC_MICHA_HU_01)
    @Description(TestDescription.DESC_MICHA_FLUJO_1)
    @Severity(SeverityLevel.NORMAL)
    @Owner(Data.DATA_OWNER_MV)
    @Link(name = "Sitio web de pruebas QA", url = "https://mi3.qa.chileatiende.cl/micha/inicio")
    public void flujo1() throws IOException {
        Log.info(LogInfo.LOG_SEPARATE);
        Log.info(LogInfo.LOG_TEST_START + TestDescription.DESC_MICHA_TEST_NAME_F1);
        Base.date = page.getDate(Data.DATA_FECHA_DDMMYYYY);
        testName.set(TestDescription.DESC_MICHA_TEST_NAME_F1);

        ThreadContext.put("testName", testName.get());

        if (!page.getJsonBoolean(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION, ExternalData.ED_HEADLESS,
                ExternalData.ED_SOURCE)) {
            page.maximizeScreen();
        }

        page.visitUrl(page.getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_URL_TEST,
                ExternalData.ED_SOURCE));
        page.iniciarSesion();
        page.flujo1();
    }

    @Test
    @Story(TestDescription.DESC_MICHA_HU_02)
    @Description(TestDescription.DESC_MICHA_FLUJO_2)
    @Severity(SeverityLevel.NORMAL)
    @Owner(Data.DATA_OWNER_MV)
    @Link(name = "Sitio web de pruebas QA", url = "https://mi3.qa.chileatiende.cl/micha/inicio")
    public void flujo2() throws IOException {
        Log.info(LogInfo.LOG_SEPARATE);
        Log.info(LogInfo.LOG_TEST_START + TestDescription.DESC_MICHA_TEST_NAME_F2);
        Base.date = page.getDate(Data.DATA_FECHA_DDMMYYYY);
        testName.set(TestDescription.DESC_MICHA_TEST_NAME_F2);

        ThreadContext.put("testName", testName.get());

        if (!page.getJsonBoolean(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION, ExternalData.ED_HEADLESS,
                ExternalData.ED_SOURCE)) {
            page.maximizeScreen();
        }

        page.visitUrl(page.getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_URL_TEST,
                ExternalData.ED_SOURCE));
        page.iniciarSesion();
        page.flujo2();
    }

    @Test
    @Story(TestDescription.DESC_MICHA_HU_03)
    @Description(TestDescription.DESC_MICHA_FLUJO_3)
    @Severity(SeverityLevel.NORMAL)
    @Owner(Data.DATA_OWNER_MV)
    @Link(name = "Sitio web de pruebas QA", url = "https://mi3.qa.chileatiende.cl/micha/inicio")
    public void flujo3() throws IOException {
        Log.info(LogInfo.LOG_SEPARATE);
        Log.info(LogInfo.LOG_TEST_START + TestDescription.DESC_MICHA_TEST_NAME_F3);
        Base.date = page.getDate(Data.DATA_FECHA_DDMMYYYY);
        testName.set(TestDescription.DESC_MICHA_TEST_NAME_F3);

        ThreadContext.put("testName", testName.get());

        if (!page.getJsonBoolean(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION, ExternalData.ED_HEADLESS,
                ExternalData.ED_SOURCE)) {
            page.maximizeScreen();
        }

        page.visitUrl(page.getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_URL_TEST,
                ExternalData.ED_SOURCE));
        page.iniciarSesion();
    }

    @Test
    @Story(TestDescription.DESC_MICHA_HU_04)
    @Description(TestDescription.DESC_MICHA_FLUJO_4)
    @Severity(SeverityLevel.NORMAL)
    @Owner(Data.DATA_OWNER_MV)
    @Link(name = "Sitio web de pruebas QA", url = "https://mi3.qa.chileatiende.cl/micha/inicio")
    public void flujo4() throws IOException {
        Log.info(LogInfo.LOG_SEPARATE);
        Log.info(LogInfo.LOG_TEST_START + TestDescription.DESC_MICHA_TEST_NAME_F4);
        Base.date = page.getDate(Data.DATA_FECHA_DDMMYYYY);
        testName.set(TestDescription.DESC_MICHA_TEST_NAME_F4);

        ThreadContext.put("testName", testName.get());

        if (!page.getJsonBoolean(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION, ExternalData.ED_HEADLESS,
                ExternalData.ED_SOURCE)) {
            page.maximizeScreen();
        }

        page.visitUrl(page.getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_URL_TEST,
                ExternalData.ED_SOURCE));
        page.iniciarSesion();
        page.flujo4();
    }

    @Test
    @Story(TestDescription.DESC_MICHA_HU_05)
    @Description(TestDescription.DESC_MICHA_FLUJO_5)
    @Severity(SeverityLevel.NORMAL)
    @Owner(Data.DATA_OWNER_MV)
    @Link(name = "Sitio web de pruebas QA", url = "https://mi3.qa.chileatiende.cl/micha/inicio")
    public void flujo5() throws IOException {
        Log.info(LogInfo.LOG_SEPARATE);
        Log.info(LogInfo.LOG_TEST_START + TestDescription.DESC_MICHA_TEST_NAME_F5);
        Base.date = page.getDate(Data.DATA_FECHA_DDMMYYYY);
        testName.set(TestDescription.DESC_MICHA_TEST_NAME_F5);

        ThreadContext.put("testName", testName.get());

        if (!page.getJsonBoolean(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION, ExternalData.ED_HEADLESS,
                ExternalData.ED_SOURCE)) {
            page.maximizeScreen();
        }

        page.visitUrl(page.getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_URL_TEST,
                ExternalData.ED_SOURCE));
        page.iniciarSesion();
        page.flujo5();
    }

    @Test
    @Story(TestDescription.DESC_MICHA_HU_06)
    @Description(TestDescription.DESC_MICHA_FLUJO_6)
    @Severity(SeverityLevel.NORMAL)
    @Owner(Data.DATA_OWNER_MV)
    @Link(name = "Sitio web de pruebas QA", url = "https://mi3.qa.chileatiende.cl/micha/inicio")
    public void flujo6() throws IOException {
        Log.info(LogInfo.LOG_SEPARATE);
        Log.info(LogInfo.LOG_TEST_START + TestDescription.DESC_MICHA_TEST_NAME_F6);
        Base.date = page.getDate(Data.DATA_FECHA_DDMMYYYY);
        testName.set(TestDescription.DESC_MICHA_TEST_NAME_F6);

        ThreadContext.put("testName", testName.get());

        if (!page.getJsonBoolean(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION, ExternalData.ED_HEADLESS,
                ExternalData.ED_SOURCE)) {
            page.maximizeScreen();
        }

        page.visitUrl(page.getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_URL_TEST,
                ExternalData.ED_SOURCE));
        page.iniciarSesion();
        page.flujo6();
    }

    @Test
    @Story(TestDescription.DESC_MICHA_HU_07)
    @Description(TestDescription.DESC_MICHA_FLUJO_7)
    @Severity(SeverityLevel.NORMAL)
    @Owner(Data.DATA_OWNER_MV)
    @Link(name = "Sitio web de pruebas QA", url = "https://mi3.qa.chileatiende.cl/micha/inicio")
    public void flujo7() throws IOException {
        Log.info(LogInfo.LOG_SEPARATE);
        Log.info(LogInfo.LOG_TEST_START + TestDescription.DESC_MICHA_TEST_NAME_F7);
        Base.date = page.getDate(Data.DATA_FECHA_DDMMYYYY);
        testName.set(TestDescription.DESC_MICHA_TEST_NAME_F7);

        ThreadContext.put("testName", testName.get());

        if (!page.getJsonBoolean(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION, ExternalData.ED_HEADLESS,
                ExternalData.ED_SOURCE)) {
            page.maximizeScreen();
        }

        page.visitUrl(page.getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_URL_TEST,
                ExternalData.ED_SOURCE));
        page.iniciarSesion();
        page.flujo7();
    }

    @Test
    @Story(TestDescription.DESC_MICHA_HU_08)
    @Description(TestDescription.DESC_MICHA_FLUJO_8)
    @Severity(SeverityLevel.NORMAL)
    @Owner(Data.DATA_OWNER_MV)
    @Link(name = "Sitio web de pruebas QA", url = "https://mi3.qa.chileatiende.cl/micha/inicio")
    public void flujo8() throws IOException {
        Log.info(LogInfo.LOG_SEPARATE);
        Log.info(LogInfo.LOG_TEST_START + TestDescription.DESC_MICHA_TEST_NAME_F8);
        Base.date = page.getDate(Data.DATA_FECHA_DDMMYYYY);
        testName.set(TestDescription.DESC_MICHA_TEST_NAME_F8);

        ThreadContext.put("testName", testName.get());

        if (!page.getJsonBoolean(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION, ExternalData.ED_HEADLESS,
                ExternalData.ED_SOURCE)) {
            page.maximizeScreen();
        }

        page.visitUrl(page.getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_URL_TEST,
                ExternalData.ED_SOURCE));
        page.iniciarSesion();
        page.flujo8();
    }

    @Test
    @Story(TestDescription.DESC_MICHA_HU_09)
    @Description(TestDescription.DESC_MICHA_FLUJO_9)
    @Severity(SeverityLevel.NORMAL)
    @Owner(Data.DATA_OWNER_MV)
    @Link(name = "Sitio web de pruebas QA", url = "https://mi3.qa.chileatiende.cl/micha/inicio")
    public void flujo9() throws IOException {
        Log.info(LogInfo.LOG_SEPARATE);
        Log.info(LogInfo.LOG_TEST_START + TestDescription.DESC_MICHA_TEST_NAME_F9);
        Base.date = page.getDate(Data.DATA_FECHA_DDMMYYYY);
        testName.set(TestDescription.DESC_MICHA_TEST_NAME_F9);

        ThreadContext.put("testName", testName.get());

        if (!page.getJsonBoolean(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION, ExternalData.ED_HEADLESS,
                ExternalData.ED_SOURCE)) {
            page.maximizeScreen();
        }

        page.visitUrl(page.getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                ExternalData.ED_URL_TEST,
                ExternalData.ED_SOURCE));
        page.iniciarSesion();
        page.flujo9();
    }

    /**
     * Finaliza la sesión del WebDriver después de que se ejecuten las pruebas.
     * <p>
     * Este método está marcado con {@link AfterTest} y se encarga de liberar
     * los recursos utilizados por el WebDriver. Se ejecuta después de la finalización
     * de todas las pruebas en la clase.
     * </p>
     */
    @AfterTest
    public void afterClass() {
        page.quitDriver();
    }
}
