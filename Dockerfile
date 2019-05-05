
#NOTE: This file is a modified version of:
#* https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/Dockerfile

# Used for running production build

FROM openjdk:8-alpine

ADD frontend/target/frontend-exec.jar .

CMD java -jar frontend-exec.jar

# --spring.datasource.username="postgres"   --spring.datasource.password  --spring.datasource.url="jdbc:postgresql://postgresql:5432/postgres"

