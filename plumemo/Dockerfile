FROM java:8

MAINTAINER test

COPY *.jar app.jar

# 运行jar包

EXPOSE 8086

ENTRYPOINT ["java","-jar","/app.jar"]