@echo off
REM Ruta del .bat
SET "BASEDIR=%~dp0"

REM Ejecución del script de powershell
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%BASEDIR%run-test.ps1"

pause
