# run-test.ps1

# Validar Java
if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
    Write-Host "Java no está instalado o no está en el PATH." -ForegroundColor Red
    exit 1
} else {
    java -version
}

# Validar Maven
if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
    Write-Host "Maven no está instalado o no está en el PATH." -ForegroundColor Red
    exit 1
} else {
    mvn -version
}

# Validar Allure
if (-not (Get-Command allure -ErrorAction SilentlyContinue)) {
    Write-Host "Allure no está instalado o no está en el PATH." -ForegroundColor Red
    exit 1
} else {
    allure --version
}

# Obtener la carpeta del script y del proyecto
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
$projectRoot = Join-Path $scriptDir ".."

# Cambiar al directorio raíz del proyecto
Set-Location $projectRoot
Write-Host "Ubicación actual: $projectRoot" -ForegroundColor Yellow

# Obtener la carpeta donde está este script
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition

# Ruta del archivo de configuración
$jsonPath = Join-Path $scriptDir "..\configuration\config.json"

# Convertir el JSON a objeto de PowerShell
$config = Get-Content $jsonPath | ConvertFrom-Json

Write-Host "Ejecutando pruebas con Maven" -ForegroundColor Cyan

# Función para eliminar carpeta si existe
function Remove-DirIfExists($dirPath) {
    if (Test-Path $dirPath) {
        Remove-Item -Path $dirPath -Recurse -Force
        Write-Host "Eliminado $dirPath" -ForegroundColor Green
    } else {
        Write-Host "No existe $dirPath" -ForegroundColor Yellow
    }
}

# Ejecución de pruebas
if ($config.webDriverConfiguration.cleanProject -eq $true) {
    Write-Host "Realizando limpieza del proyecto" -ForegroundColor Cyan

    Remove-DirIfExists (Join-Path $scriptDir "..\logs")
    Remove-DirIfExists (Join-Path $scriptDir "..\src\test\resources\evidencias")

    & mvn clean compile
    & mvn exec:java "-Dexec.mainClass=utils.TestNGXmlGenerator"
    & mvn clean test -DsuiteXmlFile=testng

} else {
    if ($config.webDriverConfiguration.cleanReport -eq $true) {
        Write-Host "Limpiando carpetas de resultados anteriores..." -ForegroundColor Cyan

        Remove-DirIfExists (Join-Path $scriptDir "..\target\allure-results")
        Remove-DirIfExists (Join-Path $scriptDir "..\target\allure-report")

        & mvn exec:java "-Dexec.mainClass=utils.TestNGXmlGenerator"
        & mvn test -DsuiteXmlFile=testng
    } else {
        & mvn exec:java "-Dexec.mainClass=utils.TestNGXmlGenerator"
        & mvn test -DsuiteXmlFile=testng
    }
}

# Capturar código de salida
$mvnExitCode = $LASTEXITCODE

if ($mvnExitCode -eq 0) {
    Write-Host "Pruebas finalizadas correctamente." -ForegroundColor Green
} else {
    Write-Host "Hubo un error ejecutando las pruebas. Código de salida: $mvnExitCode" -ForegroundColor Red
    exit $mvnExitCode
}

# Generar reporte Allure
Write-Host "Generando reporte Allure..." -ForegroundColor Cyan
Set-Location -Path (Join-Path $scriptDir "..\target")
#& cd target
& allure generate --clean --single-file allure-results
& allure open
