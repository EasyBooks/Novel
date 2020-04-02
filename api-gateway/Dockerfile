FROM adoptopenjdk/openjdk11

MAINTAINER lsm1998

ADD api-gateway-v1.jar api-gateway.jar

EXPOSE 8080

ENTRYPOINT ["java","-Xmx64m","-Xms64m","-Duser.timezone=UTC+8","-jar","api-gateway.jar"]