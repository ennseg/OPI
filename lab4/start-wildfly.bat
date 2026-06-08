@echo off

set "JAVA_HOME=C:\Program Files\Java\jdk-17"

set "JBOSS_JAVA_SIZING=-Xms128M -Xmx256M"

set DB_HOST=localhost
set DB_PORT=5432
set DB_NAME=lab3
set DB_USER=admin
set DB_PASS=1234

call "C:\wildfly-40.0.0.Final\bin\standalone.bat"
