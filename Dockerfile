FROM redmic/redmic-server

COPY /dist/*.jar ./

EXPOSE 8082

ENTRYPOINT java $JAVA_OPTS \
	-Djava.security.egd=file:/dev/./urandom \
	-Dlogging.level.org.springframework=${LOG_LEVEL} \
	-Dspring.datasource.username=${SPRING_DATASOURCE_USERNAME} \
	-Dspring.datasource.password=${SPRING_DATASOURCE_PASSWORD} \
	-Doauth.client.id=${OAUTH_CLIENT_ID} \
	-Doauth.client.secret=${OAUTH_CLIENT_SECRET} \
	-jar ${DIRPATH}/user.jar
