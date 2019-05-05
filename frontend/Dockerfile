#NOTE: This file is a copied from version of:
#* https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/Dockerfile

# ONLY used for testing

FROM openjdk:8-alpine

ADD target/frontend-exec.jar .

CMD java -jar frontend-exec.jar --spring.datasource.username="postgres"   --spring.datasource.password  --spring.datasource.url="jdbc:postgresql://postgresql-host:5432/postgres"

