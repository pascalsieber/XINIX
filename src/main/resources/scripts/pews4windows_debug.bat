@echo off

cd ..
set pews_home=%CD%

cd %pews_home%/lib
set lib=%CD%

set pews_home_prop=-Dch.zhaw.iwi.cis.pews.pewsHome=%pews_home%

set loggin_prop=-Djava.util.logging.config.file=%pews_home%\conf\logging.properties

set debug=-agentlib:jdwp=transport=dt_socket,address=8080,server=y,suspend=y

setlocal ENABLEDELAYEDEXPANSION
if defined CLASSPATH (set CLASSPATH=%CLASSPATH%;.) else (set CLASSPATH=)
FOR /R %lib% %%G IN (*.jar) DO set CLASSPATH=!CLASSPATH!;%%G

set CLASSPATH=%CLASSPATH:~1%

::echo PEWS_HOME: %pews_home%
::echo LIB: %lib%
::echo PEWS_HOME_PROP: %pews_home_prop%
::echo LOGGING_PROP: %loggin_prop%
::echo CLASSPATH: %CLASSPATH%

java -classpath %classpath% %debug% %pews_home_prop% %loggin_prop% ch.zhaw.iwi.cis.pews.framework.ZhawEngine