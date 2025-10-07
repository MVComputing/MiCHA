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
    public static final By ELM_MICHA_COMBOBOX_MI_PERFIL_REGION = By.id("select-0000000021");
    public static final By ELM_MICHA_COMBOBOX_MI_PERFIL_COMUNA = By.id("select-0000000022");
    public static final By ELM_MICHA_TEXTBOX_MI_PERFIL_FONO = By.id("fono");
    public static final By ELM_MICHA_TEXTBOX_MI_PERFIL_EMAIL = By.id("email");
    public static final By ELM_MICHA_BUTTON_MI_PERFIL_GUARDAR = By.xpath("//button[text()=' Guardar']");
    public static final By ELM_MICHA_LBL_MI_PERFIL_MENSAJE_CAMBIOS_GUARDADOS =
            By.xpath("//button[text()=' Guardar']");
    public static final By ELM_MICHA_RSH_LINK_REGISTRO_SOCIAL =
            By.xpath("//a[text()='Mi Registro Social de Hogares']");
    public static final By ELM_MICHA_BUTTON_RSH_REGISTRO_SOCIAL =
            By.xpath("//button[text()=' Ingresar al RSH ']");
    public static final By ELM_MICHA_BUTTON_RSH_OBTENER_CARTOLA =
            By.xpath("//button[text()=' Obtener cartola ']");
    public static final By ELM_MICHA_LOGO_RSH =
            By.cssSelector("a.navbar-brand[href='https://www.ventanillaunicasocial.gob.cl']");
    public static final By ELM_MICHA_DF_LINK_DEUDAS_FINANCIERAS =
            By.xpath("//a[text()='Mis deudas financieras']");
    public static final By ELM_MICHA_DF_BUTTON_DESCARGAR =
            By.xpath("//button[text()=' Descargar']");
    public static final By ELM_MICHA_DF_BUTTON_CONOCER_DEUDA=
            By.xpath("//button[text()=' Ir a Conocer tu deuda ']");
    public static final By ELM_MICHA_DF_LINK_CONTINUAR =
            By.xpath("//a[text()='Continuar']");
    public static final By ELM_MICHA_DF_IMG_CONOCE_TU_DEUDA =
            By.xpath("//img[@alt='logo CMF']");
    public static final By ELM_MICHA_BS_LINK_BENEFICIOS_SOCIALES =
            By.xpath("//a[text()='Mis pagos de beneficios sociales']");
    public static final By ELM_MICHA_BS_LINK_BENEFICIOS_SOCIALES_INFORMATE =
            By.xpath("//a[text()='infórmate sobre los beneficios del Estado']");
    public static final By ELM_MICHA_BS_LBL_MENSAJE_BONOS =
            By.xpath("//h1[text()='Bonos y beneficios del estado']");
    public static final By ELM_MICHA_AR_LINK_PAGINA_INICIO =
            By.xpath("//a[text()='Página de inicio']");
    public static final By ELM_MICHA_AR_LINK_MI_PERFIL =
            By.xpath("//a[@href='/micha/mi-perfil' and .//h4[text()='Mi perfil']]");
    public static final By ELM_MICHA_AR_LINK_MI_REGISTRO_SOCIAL =
            By.xpath("//a[@href='/micha/rsh' and .//h4[text()='Mi Registro Social de Hogares']]");
    public static final By ELM_MICHA_AR_LINK_MIS_DEUDAS_FINANCIERAS=
            By.xpath("//a[@href='/micha/mis-deudas' and .//h4[text()='Mis deudas financieras']]");
    public static final By ELM_MICHA_AR_LINK_MIS_PAGOS_BENEFICIOS_SOCIALES =
            By.xpath("//a[@href='/micha/mis-pagos' and .//h4[text()='Mis pagos de beneficios sociales']]");
    public static final By ELM_MICHA_AR_LINK_MIS_CAPACITACIONES =
            By.xpath("//a[@href='/micha/sence' and .//h4[text()='Mis capacitaciones']]");
    public static final By ELM_MICHA_AR_LINK_MI_SEGURO_SOCIAL =
            By.xpath("//a[@href='/micha/mi-seguro-previsional' and .//h4[text()='Mi Seguro Social']]");
    public static final By ELM_MICHA_MC_LINK_MIS_CAPACITACIONES =
            By.xpath("//a[text()='Mis capacitaciones']");
    public static final By ELM_MICHA_MC_BUTTON_SENCE =
            By.xpath("//button[text()=' Ir al sitio web de SENCE ']");
    public static final By ELM_MICHA_MC_BUTTON_CONTINUAR = By.xpath("//button[text()=' Continuar ']");
    public static final By ELM_MICHA_MC_LINK_VALIDATION_SENCE =
            By.xpath("//a[text()='Sobre Sence']");
    public static final By ELM_MICHA_MSS_LINK_MI_SEGURO_SOCIAL =
            By.xpath("//a[text()='Mi Seguro Social']");
    public static final By ELM_MICHA_MSS_BUTTON_DESCARGAR_CERTIFICADO =
            By.xpath("//button[text()=' Descargar certificado de cotizaciones']");
    public static final By ELM_MICHA_MSS_BUTTON_REVISAR_INFORMACION =
            By.xpath("//button[text()=' Revisar información ']");
    public static final By ELM_MICHA_MSS_LINK_CONTINUAR = By.xpath("//a[text()='Continuar']");
    public static final By ELM_MICHA_MSS_VALIDATION_SEGURO_SOCIAL =
            By.xpath("//h1[text()='Seguro Social']");
    public static final By ELM_MICHA_CONTACTO_REGION =
            By.xpath("(//select[contains(@class, 'icon-select-cust')])[1]");
    public static final By ELM_MICHA_CONTACTO_REGION_OPCION =
            By.xpath("//option[text()='METROPOLITANA']");
    public static final By ELM_MICHA_CONTACTO_COMUNA =
            By.xpath("(//select[contains(@class, 'icon-select-cust')])[2]");
    public static final By ELM_MICHA_CONTACTO_COMUNA_OPCION = By.xpath("//option[text()='SANTIAGO']");
    public static final By ELM_MICHA_TEXTBOX_IMPERSONALIZADOR =
            By.xpath("//input[contains(@class, 'w-full') and @type='text']");

}