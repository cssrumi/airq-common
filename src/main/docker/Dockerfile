ARG PARENT_POM_TAG=latest
FROM airq/parent-pom:$PARENT_POM_TAG
COPY pom.xml /usr/src/lib/common/
RUN mvn -f /usr/src/lib/common/pom.xml -B de.qaware.maven:go-offline-maven-plugin:1.2.5:resolve-dependencies
COPY src /usr/src/lib/common/src
USER root
RUN chown -R quarkus /usr/src/lib/common
USER quarkus
RUN mvn -f /usr/src/lib/common/pom.xml clean install
