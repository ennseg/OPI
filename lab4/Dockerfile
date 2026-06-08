FROM quay.io/wildfly/wildfly:latest
COPY lab3.war /opt/jboss/wildfly/standalone/deployments/

ENV JAVA_OPTS_APPEND="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9010 -Dcom.sun.management.jmxremote.rmi.port=9010 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.local.only=false -Djava.rmi.server.hostname=127.0.0.1"

EXPOSE 8080 9990 9010