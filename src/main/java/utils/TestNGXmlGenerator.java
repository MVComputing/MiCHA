package utils;

import org.json.JSONTokener;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;

public class TestNGXmlGenerator {
    public static void main(String[] args) {
        try {
            // Ruta del JSON que contiene la configuración y módulos
            String jsonPath = "configuration/config.json";

            // Leer el contenido JSON
            String content = new String(Files.readAllBytes(Paths.get(jsonPath)));
            JSONObject root = new JSONObject(new JSONTokener(content));

            // Obtener configuraciones generales y módulos
            JSONObject configuracion = root.getJSONObject("webDriverConfiguration");
            JSONObject tests = root.getJSONObject("tests");

            // Número de hilos para paralelismo en TestNG
            int threadCount = configuracion.optInt("threadCount", 1);

            // Rutas de directorios
            Path directorioSuites = Paths.get("src", "test", "resources", "suites");
            Path directorioGenerados = directorioSuites.resolve("generados");

            // Crear directorios si no existen
            Files.createDirectories(directorioSuites);
            Files.createDirectories(directorioGenerados);

            // Ruta del archivo suite-master.xml
            Path suiteMasterPath = directorioSuites.resolve("suite-master.xml");

            // Lista para ir guardando módulos activos y construir suite-master.xml
            List<String> suiteFileLines = new ArrayList<>();

            for (String modulo : tests.keySet()) {
                JSONObject seccion = tests.getJSONObject(modulo);
                Path outputFilePath = directorioGenerados.resolve("testng-" + modulo + ".xml");

                if (!seccion.optBoolean("active", false)) {
                    System.out.println("Módulo '" + modulo + "' inactivo. Se omite generación de XML.");

                    if (Files.exists(outputFilePath)) {
                        Files.delete(outputFilePath);
                        System.out.println("Archivo eliminado: " + outputFilePath);
                    }
                    continue;
                }

                suiteFileLines.add("      <suite-file path=\"generados/testng-" + modulo + ".xml\"/>");

                String suiteName = seccion.optString("suiteName", "Suite - " + modulo);

                StringBuilder xml = new StringBuilder();
                xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                xml.append("<!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\">\n");
                xml.append("<suite name=\"").append(suiteName)
                        .append("\" parallel=\"tests\" thread-count=\"").append(threadCount).append("\">\n\n");

                String testSuite = "pom.auto.test.Test_";

                for (String flujo : seccion.keySet()) {
                    if (flujo.equals("active") || flujo.equals("suiteName")) continue;

                    JSONObject datosFlujo = seccion.optJSONObject(flujo);
                    if (datosFlujo != null && datosFlujo.optBoolean("active", false)) {
                        String nombre = datosFlujo.optString("name", flujo);

                        xml.append("    <test name=\"").append(nombre).append("\">\n");
                        xml.append("        <parameter name=\"BrowserType\" value=\"Chrome\"/>\n");
                        xml.append("        <parameter name=\"TestType\" value=\"NormalTest\"></parameter>\n");
                        xml.append("        <classes>\n");
                        xml.append("            <class name=\"").append(testSuite)
                                .append(capitalize(modulo)).append("\">\n");
                        xml.append("                <methods>\n");
                        xml.append("                    <include name=\"").append(flujo).append("\"/>\n");
                        xml.append("                </methods>\n");
                        xml.append("            </class>\n");
                        xml.append("        </classes>\n");
                        xml.append("    </test>\n\n");
                    }
                }

                xml.append("</suite>\n");

                Files.createDirectories(outputFilePath.getParent()); // Asegura que el directorio existe
                Files.write(outputFilePath, xml.toString().getBytes());
                System.out.println("Generado: " + outputFilePath);
            }

            // Construir suite-master.xml con los módulos activos
            StringBuilder suiteMasterXml = new StringBuilder();
            suiteMasterXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            suiteMasterXml.append("<!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\">\n");
            suiteMasterXml.append("<suite name=\"Suite de pruebas\">\n");
            suiteMasterXml.append("    <suite-files>\n");

            for (String line : suiteFileLines) {
                suiteMasterXml.append(line).append("\n");
            }

            suiteMasterXml.append("    </suite-files>\n");
            suiteMasterXml.append("</suite>\n");

            Files.write(suiteMasterPath, suiteMasterXml.toString().getBytes());
            System.out.println("Archivo suite-master.xml actualizado.");

        } catch (Exception e) {
            System.err.println("Error generando archivos testng.xml:");
            e.printStackTrace();
        }
    }

    // Método para capitalizar la primera letra de una cadena
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
