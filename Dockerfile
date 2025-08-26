FROM	tomcat:10.1-jdk17-temurin

RUN rm -rf /usr/local/tomcat/webapps/ROOT

COPY target/School-Management.war /usr/local/tomcat/webapps/School-Management.war

EXPOSE 8080

CMD ["catalina.sh", "run"]