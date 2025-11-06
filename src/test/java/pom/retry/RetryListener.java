package pom.retry;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Listener de TestNG que asigna automáticamente la clase {@link RetryAnalyzer}
 * a cada test antes de su ejecución.
 * <p>
 * Permite que todos los métodos de prueba utilicen la lógica de reintento definida
 * en {@link RetryAnalyzer} sin necesidad de configurarlo manualmente en cada test.
 */
public class RetryListener implements IAnnotationTransformer {

    /**
     * Transforma las anotaciones de TestNG antes de la ejecución del test,
     * estableciendo el {@link RetryAnalyzer} para habilitar reintentos automáticos.
     *
     * @param annotation la anotación del test que se va a transformar.
     * @param testClass clase del test.
     * @param testConstructor constructor del test (si aplica).
     * @param testMethod método del test que se va a ejecutar.
     */
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}

