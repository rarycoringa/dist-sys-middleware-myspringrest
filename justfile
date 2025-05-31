install:
    mvn clean compile

build:
    mvn compile

test:
    mvn test

run: build
    mvn exec:java -Dexec.mainClass="br.edu.ufrn.myspringrest.App"
