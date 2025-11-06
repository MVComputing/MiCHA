package pom.auto.test;

import io.qameta.allure.*;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pom.Base;
import pom.auto.page.Page;
import pom.auto.repository.ExternalData_Auto;
import pom.auto.repository.TestDescription;
import pom.general_repository.BrowserConfiguration;
import pom.general_repository.Data;
import pom.general_repository.ExternalData;
import pom.general_repository.LogInfo;
import utils.Log;

import java.io.IOException;

import static pom.Base.testName;

/**
 * Clase de prueba de Mi Chile Atienede que realiza la ejecución de un flujo automatizado
 * utilizando Selenium WebDriver y el patrón Page Object.
 * <p>
 * Gestiona la configuración inicial del navegador, la ejecución del flujo de prueba
 * y el cierre controlado del driver. Además, implementa soporte para múltiples
 * navegadores y registro de resultados mediante Allure y Log.
 */
public class Test_Micha {
    /** Instancia del controlador del navegador. */
    public WebDriver driver;
    /** Instancia de la clase {@link Page} que contiene los flujos de interacción. */
    public Page page;

    /**
     * Configura el entorno de prueba antes de la ejecución de cada método.
     * <p>
     * - Cierra cualquier instancia previa del navegador.<br>
     * - Inicializa el driver según el tipo de navegador especificado (Chrome, Edge, Firefox).<br>
     * - Carga la configuración personalizada de cada navegador.<br>
     * - Ejecuta el método {@link Page#setup()} para preparar el entorno inicial.
     *
     * @param browserType tipo de navegador a utilizar (por ejemplo: "CHROME", "EDGE", "FIREFOX").
     * @throws IOException si ocurre un error al leer la configuración externa.
     */
    @BeforeMethod
    @Parameters({"BrowserType"})
    public void setUp(String browserType) throws IOException {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                Log.info(e.getMessage());
            } finally {
                driver = null;
            }
        }

        page = new Page(driver);

        switch (browserType) {
            case "CHROME":
                ChromeOptions chromeOptions = page.chromeCustomConfiguration(
                        page.getJsonString(
                                ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                                ExternalData.ED_DOWNLOADS,
                                ExternalData.ED_SOURCE));
                page.chromeDriverConnectionOptions(chromeOptions);
                break;

            case "EDGE":
                break;

            case "FIREFOX":
                break;

            default:
                throw new IllegalArgumentException("Browser desconocido: " + browserType);
        }

        page.setup();
        driver = page.getDriver();
    }


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

        page.iniciarSesion();
        page.impersonalizador(page.getJsonString(ExternalData_Auto.ED_MICHA_OBJECT_IMPERSONALIZACION,
                ExternalData_Auto.ED_MICHA_IMPERSONALIZACION_RUT_F1, ExternalData_Auto.ED_MICHA_SRC));
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

        page.iniciarSesion();
        page.impersonalizador(page.getJsonString(ExternalData_Auto.ED_MICHA_OBJECT_IMPERSONALIZACION,
                ExternalData_Auto.ED_MICHA_IMPERSONALIZACION_RUT_F2, ExternalData_Auto.ED_MICHA_SRC));
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

        page.iniciarSesion();
        page.impersonalizador(page.getJsonString(ExternalData_Auto.ED_MICHA_OBJECT_IMPERSONALIZACION,
                ExternalData_Auto.ED_MICHA_IMPERSONALIZACION_RUT_F3, ExternalData_Auto.ED_MICHA_SRC));
        
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

        page.iniciarSesion();
        page.impersonalizador(page.getJsonString(ExternalData_Auto.ED_MICHA_OBJECT_IMPERSONALIZACION,
                ExternalData_Auto.ED_MICHA_IMPERSONALIZACION_RUT_F4, ExternalData_Auto.ED_MICHA_SRC));
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

        page.iniciarSesion();
        page.impersonalizador(page.getJsonString(ExternalData_Auto.ED_MICHA_OBJECT_IMPERSONALIZACION,
                ExternalData_Auto.ED_MICHA_IMPERSONALIZACION_RUT_F5, ExternalData_Auto.ED_MICHA_SRC));
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

        page.iniciarSesion();
        page.impersonalizador(page.getJsonString(ExternalData_Auto.ED_MICHA_OBJECT_IMPERSONALIZACION,
                ExternalData_Auto.ED_MICHA_IMPERSONALIZACION_RUT_F6, ExternalData_Auto.ED_MICHA_SRC));
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

        page.iniciarSesion();
        page.impersonalizador(page.getJsonString(ExternalData_Auto.ED_MICHA_OBJECT_IMPERSONALIZACION,
                ExternalData_Auto.ED_MICHA_IMPERSONALIZACION_RUT_F7, ExternalData_Auto.ED_MICHA_SRC));
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

        page.iniciarSesion();
        page.impersonalizador(page.getJsonString(ExternalData_Auto.ED_MICHA_OBJECT_IMPERSONALIZACION,
                ExternalData_Auto.ED_MICHA_IMPERSONALIZACION_RUT_F8, ExternalData_Auto.ED_MICHA_SRC));
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

        page.iniciarSesion();
        page.impersonalizador(page.getJsonString(ExternalData_Auto.ED_MICHA_OBJECT_IMPERSONALIZACION,
                ExternalData_Auto.ED_MICHA_IMPERSONALIZACION_RUT_F9, ExternalData_Auto.ED_MICHA_SRC));
        page.flujo9();
    }

    @Test
    @Story(TestDescription.DESC_MICHA_HU_10)
    @Description(TestDescription.DESC_MICHA_FLUJO_10)
    @Severity(SeverityLevel.NORMAL)
    @Owner(Data.DATA_OWNER_MV)
    @Link(name = "Sitio web de pruebas QA", url = "https://mi3.qa.chileatiende.cl/micha/inicio")
    public void flujo10() throws IOException {
        Log.info(LogInfo.LOG_SEPARATE);
        Log.info(LogInfo.LOG_TEST_START + TestDescription.DESC_MICHA_TEST_NAME_F10);
        Base.date = page.getDate(Data.DATA_FECHA_DDMMYYYY);
        testName.set(TestDescription.DESC_MICHA_TEST_NAME_F10);

        ThreadContext.put("testName", testName.get());

        page.iniciarSesion();
        page.impersonalizador(page.getJsonString(ExternalData_Auto.ED_MICHA_OBJECT_IMPERSONALIZACION,
                ExternalData_Auto.ED_MICHA_IMPERSONALIZACION_RUT_F10, ExternalData_Auto.ED_MICHA_SRC));
        page.flujo10();
    }

    @Test
    @Story(TestDescription.DESC_MICHA_HU_12)
    @Description(TestDescription.DESC_MICHA_FLUJO_12)
    @Severity(SeverityLevel.NORMAL)
    @Owner(Data.DATA_OWNER_MV)
    @Link(name = "Sitio web de pruebas QA", url = "https://mi3.qa.chileatiende.cl/micha/inicio")
    public void flujo12() throws IOException {
        Log.info(LogInfo.LOG_SEPARATE);
        Log.info(LogInfo.LOG_TEST_START + TestDescription.DESC_MICHA_TEST_NAME_F12);
        Base.date = page.getDate(Data.DATA_FECHA_DDMMYYYY);
        testName.set(TestDescription.DESC_MICHA_TEST_NAME_F12);

        ThreadContext.put("testName", testName.get());

        page.iniciarSesion();
        page.impersonalizador(page.getJsonString(ExternalData_Auto.ED_MICHA_OBJECT_IMPERSONALIZACION,
                ExternalData_Auto.ED_MICHA_IMPERSONALIZACION_RUT_F12, ExternalData_Auto.ED_MICHA_SRC));
        page.flujo12();
    }

    /**
     * Finaliza la ejecución de la prueba cerrando el navegador.
     * <p>
     * Se ejecuta después de cada método de prueba, garantizando el cierre
     * del {@link WebDriver} aunque la prueba falle.
     *
     * @param result resultado del test ejecutado, proporcionado por TestNG.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            if (driver != null) {
                driver.quit();
                driver = null;
            }
        } catch (Exception e) {
            Log.info(e.getMessage());
        }
    }
}
