package pom.auto.page;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import pom.Base;
import pom.auto.elements.Elements;
import pom.auto.repository.TestSteps;

public class Page extends Base {
    public Page(WebDriver driver) {
        super(driver);
    }

    public void iniciarSesion(){
        Allure.step(TestSteps.TS_MICHA_LOGIN, (step) -> {
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_INICIAR_SESION);
            sendInputText("444444444", Elements.ELM_MICHA_TEXTBOX_RUN);
            sendInputText("testing", Elements.ELM_MICHA_TEXTBOX_PASS);
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_INGRESA);
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_CONTINUAR);
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_MI_PERFIL);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_LBL_MI_PERFIL_MENSAJE_BIENVENIDA);
            waitForElementToBeClickable(Elements.ELM_MICHA_LINK_MI_PERFIL_EDITAR);
            sendInputText("ALAMEDA", Elements.ELM_MICHA_TEXTBOX_MI_PERFIL_DIRECCION);
            selectElementSelectByVisibleText(Elements.ELM_MICHA_COMBOBOX_MI_PERFIL_REGION, "METROPOLITANA");
            selectElementSelectByVisibleText(Elements.ELM_MICHA_COMBOBOX_MI_PERFIL_COMUNA, "SANTIAGO");
            clearText(Elements.ELM_MICHA_TEXTBOX_MI_PERFIL_FONO);
            sendInputText("987654321", Elements.ELM_MICHA_TEXTBOX_MI_PERFIL_FONO);
            clearText(Elements.ELM_MICHA_TEXTBOX_MI_PERFIL_EMAIL);
            sendInputText("rodrigo.mendez.guzman@gmail.com", Elements.ELM_MICHA_TEXTBOX_MI_PERFIL_EMAIL);
            waitForElementToBeClickable(Elements.ELM_MICHA_BUTTON_MI_PERFIL_GUARDAR);
            waitForVisibilityOfElementLocated(Elements.ELM_MICHA_LBL_MI_PERFIL_MENSAJE_CAMBIOS_GUARDADOS);
            threadWait(1000L);
            screenShot();
        });

    }
}
