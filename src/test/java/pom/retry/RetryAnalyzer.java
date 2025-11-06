package pom.retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Clase que implementa la interfaz {@link IRetryAnalyzer} de TestNG para permitir
 * la re-ejecución automática de tests fallidos.
 * <p>
 * Permite habilitar o deshabilitar los reintentos y definir el número máximo
 * de veces que un test puede volver a ejecutarse.
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    /** Número máximo de reintentos permitidos. */
    private static int maxRetryCount = 1;
    /** Indica si la lógica de reintento está habilitada. */
    private static boolean enableRetry = true;
    /**
     * Determina si un test fallido debe volver a ejecutarse.
     *
     * @param result resultado del test ejecutado.
     * @return {@code true} si se debe reintentar, {@code false} de lo contrario.
     */
    @Override
    public boolean retry(ITestResult result) {
        if (!enableRetry) {
            return false;
        }

        int currentAttempt = result.getMethod().getCurrentInvocationCount();
        return currentAttempt <= maxRetryCount-1;
    }
    /**
     * Habilita o deshabilita la funcionalidad de reintento.
     *
     * @param enableRetry {@code true} para habilitar, {@code false} para deshabilitar.
     */
    public static void setEnableRetry(boolean enableRetry) {
        RetryAnalyzer.enableRetry = enableRetry;
    }
    /**
     * Define el número máximo de reintentos permitidos por test.
     *
     * @param maxRetryCount número máximo de reintentos.
     */
    public static void setMaxRetryCount(int maxRetryCount) {
        RetryAnalyzer.maxRetryCount = maxRetryCount;
    }
}

