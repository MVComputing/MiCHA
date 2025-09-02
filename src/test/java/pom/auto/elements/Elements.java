package pom.auto.elements;

import org.openqa.selenium.By;

public class Elements {
    public static final By ELM_MICHA_BUTTON_INICIAR_SESION =
            By.xpath("//button//span[text()='Iniciar sesión']");
    public static final By ELM_MICHA_TEXTBOX_RUN = By.id("uname");
    public static final By ELM_MICHA_TEXTBOX_PASS = By.id("pword");
    public static final By ELM_MICHA_BUTTON_INGRESA = By.id("login-submit");
    public static final By ELM_MICHA_BUTTON_CONTINUAR =
            By.xpath("//button[text()='Continuar']");
    public static final By ELM_MICHA_BUTTON_MI_PERFIL =
            By.xpath("//a//h4[text()='Mi perfil']");
    public static final By ELM_MICHA_LBL_MI_PERFIL_MENSAJE_BIENVENIDA =
            By.xpath("//h1[text()='Hola, Maria Carmen De los angeles Del rio Gonzalez']");
    public static final By ELM_MICHA_LINK_MI_PERFIL_EDITAR =
            By.xpath("//a//span[text()='Editar']");
    public static final By ELM_MICHA_TEXTBOX_MI_PERFIL_DIRECCION = By.id("address");
    public static final By ELM_MICHA_COMBOBOX_MI_PERFIL_REGION = By.id("select-0000000009");
    public static final By ELM_MICHA_COMBOBOX_MI_PERFIL_COMUNA = By.id("select-0000000010");
    public static final By ELM_MICHA_TEXTBOX_MI_PERFIL_FONO = By.id("fono");
    public static final By ELM_MICHA_TEXTBOX_MI_PERFIL_EMAIL = By.id("email");
    public static final By ELM_MICHA_BUTTON_MI_PERFIL_GUARDAR = By.xpath("//button[text()=' Guardar']");
    public static final By ELM_MICHA_LBL_MI_PERFIL_MENSAJE_CAMBIOS_GUARDADOS = By.xpath("//button[text()=' Guardar']");


}