#!/bin/bash
# JPDomain2 起動スクリプト

TOMCAT_HOME=/opt/tomcat
CATALINA_PID=$TOMCAT_HOME/tomcat.pid

export JAVA_OPTS="-Xms512m -Xmx1024m"

echo "Starting Tomcat..."
$TOMCAT_HOME/bin/startup.sh

echo "Tomcat started."
