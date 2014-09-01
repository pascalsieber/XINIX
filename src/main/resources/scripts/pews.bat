@echo off
@setlocal enableextensions enabledelayedexpansion

SET PEWS_HOME=%~dp0
set PEWS_HOME=!PEWS_HOME:~0,-4!
SET PEWS_LIB=%PEWS_HOME%lib\
SET PEWS_PLUGIN=%PEWS_HOME%plugin\
SET PEWS_HOME_PROP=-Dch.zhaw.iwi.cis.pews.pewsHome=%PEWS_HOME%
SET LOGGING_PROP=-Djava.util.logging.config.file=%PEWS_HOME%conf\logging.properties

mkdir %PEWS_HOME%log
mkdir %PEWS_HOME%db

if "%1" == "-debug" (
    set DEBUG=-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=y
    shift
)

:loop
if [%1]==[] goto afterloop
set PARAMS=%PARAMS% %1
shift
goto loop
:afterloop

SET CLASSPATH="%PEWS_LIB%*;%PEWS_PLUGIN%*"

java -classpath %CLASSPATH% %DEBUG% %PEWS_HOME_PROP% %LOGGING_PROP% ch.zhaw.iwi.cis.pews.framework.ZhawEngine %PARAMS%