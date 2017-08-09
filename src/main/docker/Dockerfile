FROM java:8
MAINTAINER Mahima Agarwal  <mahima.agarwal@gmail.com>

VOLUME /tmp
ADD emp_timerecords-2.0.jar  app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 8095
ENV JAVA_OPTS="-Xmx256m"

#COPY entrypoint.sh entrypoint.sh
#RUN chmod +x entrypoint.sh
#ENTRYPOINT ["./entrypoint.sh"]
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]