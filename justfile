install:
    mvn clean compile

build:
    mvn compile

test:
    mvn test

run: 
    mvn exec:java -Dexec.mainClass="br.edu.ufrn.myspringrest.App"
