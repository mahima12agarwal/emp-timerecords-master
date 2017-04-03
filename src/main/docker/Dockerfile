FROM java:8
MAINTAINER Mahima Agarwal  <mahima.agarwal@gmail.com>
VOLUME /tmp
ADD emp_timerecords_v-0.1.2.jar  app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 8096
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
