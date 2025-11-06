package pom.auto.page;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import pom.Base;
import pom.auto.elements.Elements;
import pom.auto.repository.ExternalData_Auto;
import pom.auto.repository.TestSteps;
import pom.general_repository.ExternalData;
import pom.retry.RetryAnalyzer;

import java.io.IOException;

/**
 * Clase que centraliza los flujos de navegación y acciones del sistema,
 * utilizando las funciones base que interactúan directamente con el navegador.
 * <p>
 * Extiende la clase {@link Base} para reutilizar sus métodos de control del WebDriver
 * y facilitar la construcción de flujos automatizados.
 */
public class Page extends Base {
    /**
     * Inicializa la página con el controlador del navegador.
     *
     * @param driver instancia de {@link WebDriver}.
     */
    public Page(WebDriver driver) {
        super(driver);
    }

    /**
     * Configura el entorno inicial de ejecución antes de iniciar las pruebas automatizadas.
     * <p>
     * Este método realiza los siguientes pasos:
     * <ul>
     *     <li>Lee la configuración del driver desde un archivo JSON externo.</li>
     *     <li>Activa o desactiva la lógica de reintento según la configuración establecida.</li>
     *     <li>Maximiza la ventana del navegador si no se ejecuta en modo headless.</li>
     *     <li>Navega a la URL base del entorno configurado.</li>
     *     <li>Toma una captura de pantalla inicial del estado de la página.</li>
     * </ul>
     *
     * @throws IOException si ocurre un error al leer los datos de configuración externa.
     */
    public void setup() throws IOException {
        Allure.step(TestSteps.STEP_SETUP, (step) ->
        {
            boolean retry = getJsonBoolean(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                    ExternalData.ED_RETRY, ExternalData.ED_SOURCE);
            int retryCount = getJsonInt(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                    ExternalData.ED_RETRY_COUNT, ExternalData.ED_SOURCE);

            if (retry && retryCount > 0) {
                RetryAnalyzer.setEnableRetry(true);
                RetryAnalyzer.setMaxRetryCount(retryCount);
            } else {
                RetryAnalyzer.setEnableRetry(false);
            }

            if (!getJsonBoolean(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION, ExternalData.ED_HEADLESS,
                    ExternalData.ED_SOURCE)) {
                maximizeScreen();
            }

            visitUrlIndex(getJsonString(ExternalData.ED_OBJECT_WEB_DRIVER_CONFIGURATION,
                    ExternalData.ED_ENVIRONMENT,
                    ExternalData.ED_SOURCE));

            screenShot();
        });
    }

    public void iniciarSesion() {
        Allure.step(TestSteps.TS_MICHA_LOGIN, (step) -> {
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_INICIAR_SESION);
            sendInputText(getJsonString(ExternalData_Auto.ED_MICHA_OBJECT_CREDENCIALES,
                            ExternalData_Auto.ED_MICHA_CREDENCIALES_USER_RUN, ExternalData_Auto.ED_MICHA_SRC),
                    Elements.ELM_MICHA_TEXTBOX_RUN);
            sendInputText(getJsonString(ExternalData_Auto.ED_MICHA_OBJECT_CREDENCIALES,
                            ExternalData_Auto.ED_MICHA_CREDENCIALES_USER_PASS, ExternalData_Auto.ED_MICHA_SRC),
                    Elements.ELM_MICHA_TEXTBOX_PASS);
            screenShot();
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_INGRESA);

        });
    }

    public void impersonalizador(String rut) {
        Allure.step(TestSteps.TS_MICHA_IMPERSONALIZADOR, (step) -> {
            waitForElementToBeClickable(Elements.ELM_MICHA_TEXTBOX_IMPERSONALIZADOR);
            clearText(Elements.ELM_MICHA_TEXTBOX_IMPERSONALIZADOR);
            sendInputText(rut, Elements.ELM_MICHA_TEXTBOX_IMPERSONALIZADOR);
            screenShot();
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_CONTINUAR);
        });
    }

    public void flujo1() {
        Allure.step(TestSteps.TS_MICHA_Flujo_1, (step) -> {
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_MI_PERFIL);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_LBL_MI_PERFIL_MENSAJE_BIENVENIDA);
            waitForElementToBeClickable(Elements.ELM_MICHA_LINK_MI_PERFIL_EDITAR);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_TEXTBOX_MI_PERFIL_DIRECCION);
            clearText(Elements.ELM_MICHA_TEXTBOX_MI_PERFIL_DIRECCION);
            sendInputText(getJsonString(ExternalData_Auto.ED_MICHA_OBJECT_CONTACTO,
                            ExternalData_Auto.ED_MICHA_CONTACTO_DIRECCION, ExternalData_Auto.ED_MICHA_SRC),
                    Elements.ELM_MICHA_TEXTBOX_MI_PERFIL_DIRECCION);

            waitForElementToBeClickable(Elements.ELM_MICHA_CONTACTO_REGION);
            waitForElementToBeClickable(Elements.ELM_MICHA_CONTACTO_REGION_OPCION);
            waitForElementToBeClickable(Elements.ELM_MICHA_CONTACTO_COMUNA);
            waitForElementToBeClickable(Elements.ELM_MICHA_CONTACTO_COMUNA_OPCION);
            clearText(Elements.ELM_MICHA_TEXTBOX_MI_PERFIL_FONO);
            sendInputText(getJsonString(ExternalData_Auto.ED_MICHA_OBJECT_CONTACTO,
                            ExternalData_Auto.ED_MICHA_CONTACTO_FONO, ExternalData_Auto.ED_MICHA_SRC),
                    Elements.ELM_MICHA_TEXTBOX_MI_PERFIL_FONO);
            clearText(Elements.ELM_MICHA_TEXTBOX_MI_PERFIL_EMAIL);
            sendInputText(getJsonString(ExternalData_Auto.ED_MICHA_OBJECT_CONTACTO,
                            ExternalData_Auto.ED_MICHA_CONTACTO_EMAIL, ExternalData_Auto.ED_MICHA_SRC),
                    Elements.ELM_MICHA_TEXTBOX_MI_PERFIL_EMAIL);
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_MI_PERFIL_GUARDAR);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_LBL_MI_PERFIL_MENSAJE_CAMBIOS_GUARDADOS);
            screenShot();
        });
    }

    public void flujo2() {
        Allure.step(TestSteps.TS_MICHA_Flujo_2, (step) -> {
            waitForElementToBeClickable(Elements.ELM_MICHA_RSH_LINK_REGISTRO_SOCIAL);
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_RSH_REGISTRO_SOCIAL);
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_RSH_OBTENER_CARTOLA);
            switchToNewTab(this.driver);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_LOGO_RSH);
            screenShot();
        });
    }

    public void flujo4() {
        Allure.step(TestSteps.TS_MICHA_Flujo_4, (step) -> {
            waitForElementToBeClickable(Elements.ELM_MICHA_DF_LINK_DEUDAS_FINANCIERAS);
            waitForElementToBeClickable(Elements.ELM_MICHA_DF_BUTTON_CONOCER_DEUDA);
            waitForElementToBeClickable(Elements.ELM_MICHA_DF_LINK_CONTINUAR);
            switchToNewTab(this.driver);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_DF_IMG_CONOCE_TU_DEUDA);
            screenShot();
        });
    }

    public void flujo5() {
        Allure.step(TestSteps.TS_MICHA_Flujo_5, (step) -> {
            waitForElementToBeClickable(Elements.ELM_MICHA_BS_LINK_BENEFICIOS_SOCIALES);
            waitForElementToBeClickable(Elements.ELM_MICHA_BS_LINK_BENEFICIOS_SOCIALES_INFORMATE);
            switchToNewTab(this.driver);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_BS_LBL_MENSAJE_BONOS);
            screenShot();
        });
    }

    public void flujo6() {
        Allure.step(TestSteps.TS_MICHA_Flujo_6, (step) -> {
            waitForElementToBeClickable(Elements.ELM_MICHA_MC_LINK_MIS_CAPACITACIONES);
            waitForElementToBeClickable(Elements.ELM_MICHA_MC_BUTTON_SENCE);
            waitForElementToBeClickable(Elements.ELM_MICHA_MC_BUTTON_CONTINUAR);
            switchToNewTab(this.driver);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_MC_LINK_VALIDATION_SENCE);
            screenShot();
        });
    }

    public void flujo7() {
        Allure.step(TestSteps.TS_MICHA_Flujo_7, (step) -> {
            waitForElementToBeClickable(Elements.ELM_MICHA_AR_LINK_PAGINA_INICIO);
            waitForElementToBeClickable(Elements.ELM_MICHA_AR_LINK_MI_PERFIL);
            threadWait(3000L);
            screenShot();
            waitForElementToBeClickable(Elements.ELM_MICHA_AR_LINK_PAGINA_INICIO);
            waitForElementToBeClickable(Elements.ELM_MICHA_AR_LINK_MI_REGISTRO_SOCIAL);
            threadWait(3000L);
            screenShot();
            waitForElementToBeClickable(Elements.ELM_MICHA_AR_LINK_PAGINA_INICIO);
            waitForElementToBeClickable(Elements.ELM_MICHA_AR_LINK_MIS_DEUDAS_FINANCIERAS);
            threadWait(3000L);
            screenShot();
            waitForElementToBeClickable(Elements.ELM_MICHA_AR_LINK_PAGINA_INICIO);
            waitForElementToBeClickable(Elements.ELM_MICHA_AR_LINK_MIS_PAGOS_BENEFICIOS_SOCIALES);
            threadWait(3000L);
            screenShot();
            waitForElementToBeClickable(Elements.ELM_MICHA_AR_LINK_PAGINA_INICIO);
            waitForElementToBeClickable(Elements.ELM_MICHA_AR_LINK_MIS_CAPACITACIONES);
            threadWait(3000L);
            screenShot();
            waitForElementToBeClickable(Elements.ELM_MICHA_AR_LINK_PAGINA_INICIO);
            waitForElementToBeClickable(Elements.ELM_MICHA_AR_LINK_MI_SEGURO_SOCIAL);
            threadWait(3000L);
            screenShot();
        });
    }

    public void flujo8() {
        Allure.step(TestSteps.TS_MICHA_Flujo_8, (step) -> {
            waitForElementToBeClickable(Elements.ELM_MICHA_MSS_LINK_MI_SEGURO_SOCIAL);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_MSS_BUTTON_DESCARGAR_CERTIFICADO);
            screenShot();
            downloadFiles(".pdf", Elements.ELM_MICHA_MSS_BUTTON_DESCARGAR_CERTIFICADO);
            screenShot();
        });
    }

    public void flujo9() {
        Allure.step(TestSteps.TS_MICHA_Flujo_9, (step) -> {
            waitForElementToBeClickable(Elements.ELM_MICHA_MSS_LINK_MI_SEGURO_SOCIAL);
            waitForElementToBeClickable(Elements.ELM_MICHA_MSS_BUTTON_REVISAR_INFORMACION);
            waitForElementToBeClickable(Elements.ELM_MICHA_MSS_LINK_CONTINUAR);
            switchToNewTab(this.driver);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_MSS_VALIDATION_SEGURO_SOCIAL);
            screenShot();
        });
    }

    public void flujo10() {
        Allure.step(TestSteps.TS_MICHA_Flujo_10, (step) -> {
            waitForElementToBeClickable(Elements.ELM_MICHA_MC_LINK_MIS_CAPACITACIONES);
            waitForElementToBeClickable(Elements.ELM_MICHA_MC_BUTTON_REVISAR_CURSOS_SENCE);
            waitForElementToBeClickable(Elements.ELM_MICHA_MC_BUTTON_CONTINUAR);
            switchToNewTab(this.driver);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_MC_LINK_VALIDATION_SENCE);
            screenShot();
        });
    }

    public void flujo12() {
        Allure.step(TestSteps.TS_MICHA_Flujo_2, (step) -> {
            waitForElementToBeClickable(Elements.ELM_MICHA_RSH_LINK_REGISTRO_SOCIAL);
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_RSH_OBTENER_CARTOLA);
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_RSH_OBTENER_CARTOLA_2);
            switchToNewTab(this.driver);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_LOGO_RSH);
            screenShot();
        });
    }
}
