FROM adoptopenjdk/openjdk11

MAINTAINER lsm1998

ADD im-service.jar im-service.jar

EXPOSE 20080
EXPOSE 8888

ENTRYPOINT ["java","-Xmx64m","-Xms64m","-Duser.timezone=UTC+8","-jar","im-service.jar"]