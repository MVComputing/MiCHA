package pom.auto.page;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import pom.Base;
import pom.auto.elements.Elements;
import pom.auto.repository.ExternalData;
import pom.auto.repository.TestSteps;

public class Page extends Base {
    public Page(WebDriver driver) {
        super(driver);
    }

    public void iniciarSesion(){
        Allure.step(TestSteps.TS_MICHA_LOGIN, (step) -> {
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_INICIAR_SESION);
            sendInputText(getJsonString(ExternalData.ED_MICHA_OBJECT_CREDENCIALES,
                    ExternalData.ED_MICHA_CREDENCIALES_USER_RUN, ExternalData.ED_MICHA_SRC),
                    Elements.ELM_MICHA_TEXTBOX_RUN);
            sendInputText(getJsonString(ExternalData.ED_MICHA_OBJECT_CREDENCIALES,
                    ExternalData.ED_MICHA_CREDENCIALES_USER_PASS, ExternalData.ED_MICHA_SRC),
                    Elements.ELM_MICHA_TEXTBOX_PASS);
            screenShot();
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_INGRESA);
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_CONTINUAR);
        });
    }

    public void flujo1(){
        Allure.step(TestSteps.TS_MICHA_Flujo_1, (step) -> {
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_MI_PERFIL);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_LBL_MI_PERFIL_MENSAJE_BIENVENIDA);
            waitForElementToBeClickable(Elements.ELM_MICHA_LINK_MI_PERFIL_EDITAR);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_TEXTBOX_MI_PERFIL_DIRECCION);
            clearText(Elements.ELM_MICHA_TEXTBOX_MI_PERFIL_DIRECCION);
            sendInputText(getJsonString(ExternalData.ED_MICHA_OBJECT_CONTACTO,
                            ExternalData.ED_MICHA_CONTACTO_DIRECCION, ExternalData.ED_MICHA_SRC),
                    Elements.ELM_MICHA_TEXTBOX_MI_PERFIL_DIRECCION);
            selectElementSelectByVisibleText(Elements.ELM_MICHA_COMBOBOX_MI_PERFIL_REGION,
                    getJsonString(ExternalData.ED_MICHA_OBJECT_CONTACTO,
                            ExternalData.ED_MICHA_CONTACTO_REGION, ExternalData.ED_MICHA_SRC));
            selectElementSelectByVisibleText(Elements.ELM_MICHA_COMBOBOX_MI_PERFIL_COMUNA,
                    getJsonString(ExternalData.ED_MICHA_OBJECT_CONTACTO,
                            ExternalData.ED_MICHA_CONTACTO_COMUNA, ExternalData.ED_MICHA_SRC));
            clearText(Elements.ELM_MICHA_TEXTBOX_MI_PERFIL_FONO);
            sendInputText(getJsonString(ExternalData.ED_MICHA_OBJECT_CONTACTO,
                            ExternalData.ED_MICHA_CONTACTO_FONO, ExternalData.ED_MICHA_SRC),
                    Elements.ELM_MICHA_TEXTBOX_MI_PERFIL_FONO);
            clearText(Elements.ELM_MICHA_TEXTBOX_MI_PERFIL_EMAIL);
            sendInputText(getJsonString(ExternalData.ED_MICHA_OBJECT_CONTACTO,
                            ExternalData.ED_MICHA_CONTACTO_EMAIL, ExternalData.ED_MICHA_SRC),
                    Elements.ELM_MICHA_TEXTBOX_MI_PERFIL_EMAIL);
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_MI_PERFIL_GUARDAR);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_LBL_MI_PERFIL_MENSAJE_CAMBIOS_GUARDADOS);
            screenShot();
        });
    }

    public void flujo2(){
        Allure.step(TestSteps.TS_MICHA_Flujo_2, (step) -> {
            waitForElementToBeClickable(Elements.ELM_MICHA_RSH_LINK_REGISTRO_SOCIAL);
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_RSH_REGISTRO_SOCIAL);
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_RSH_OBTENER_CARTOLA);
            switchToNewTab(this.driver);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_LOGO_RSH);
            screenShot();
        });
    }

    public void flujo4(){
        Allure.step(TestSteps.TS_MICHA_Flujo_4, (step) -> {
            waitForElementToBeClickable(Elements.ELM_MICHA_DF_LINK_DEUDAS_FINANCIERAS);
            waitForElementToBeClickable(Elements.ELM_MICHA_DF_BUTTON_CONOCER_DEUDA);
            waitForElementToBeClickable(Elements.ELM_MICHA_DF_LINK_CONTINUAR);
            switchToNewTab(this.driver);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_DF_IMG_CONOCE_TU_DEUDA);
            screenShot();
        });
    }

    public void flujo5(){
        Allure.step(TestSteps.TS_MICHA_Flujo_5, (step) -> {
            waitForElementToBeClickable(Elements.ELM_MICHA_BS_LINK_BENEFICIOS_SOCIALES);
            waitForElementToBeClickable(Elements.ELM_MICHA_BS_LINK_BENEFICIOS_SOCIALES_INFORMATE);
            switchToNewTab(this.driver);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_BS_LBL_MENSAJE_BONOS);
            screenShot();
        });
    }

    public void flujo6(){
        Allure.step(TestSteps.TS_MICHA_Flujo_6, (step) -> {
            waitForElementToBeClickable(Elements.ELM_MICHA_MC_LINK_MIS_CAPACITACIONES);
            waitForElementToBeClickable(Elements.ELM_MICHA_MC_BUTTON_SENCE);
            waitForElementToBeClickable(Elements.ELM_MICHA_MC_BUTTON_CONTINUAR);
            switchToNewTab(this.driver);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_MC_LINK_VALIDATION_SENCE);
            screenShot();
        });
    }

    public void flujo7(){
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
}
