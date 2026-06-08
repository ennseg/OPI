@echo off

set "JBOSS_HOME=C:\wildfly-40.0.0.Final"
set "JAVA_HOME=C:\Program Files\Java\jdk-17"

set "CLASSPATH=%JBOSS_HOME%\bin\client\jboss-cli-client.jar"

"%JAVA_HOME%\bin\jconsole.exe" ^
  "-J--add-modules=jdk.unsupported" ^
  "-J-Djava.class.path=%CLASSPATH%" ^
  "service:jmx:remote+http://localhost:9990"