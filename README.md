# Spring Boot Kafka Example

This Spring Boot application serves as a supplicant to the following [Spring Boot Application](https://github.com/injunTroubles/kafkaDemo). The prior listed kafkaDemo app should be setup and booted prior to following the instructions for this application, but the `docker-compose.yml` of this project (demo-ext) should be used in lieu of the one in kafkaDemo.

This app uses Gradle for build and dependency management.

## Key Components
* REST Controller
* Kafka Consumer
* Hibernate Repository

## Rest Controller
### GET endpoint: "/persons/{id}"
* Entry point that retrieves a person from the database based on their ID (not to be confused with correlationID).
* Response Pattern: string value of JSON representation of a person.
* Response Status: 200 OK

### GET endpoint: "/persons/correlationId/{correlationId}"
* Entry point that retrieves a group of person entries that share a correlationId.
* Response Pattern: string value of JSON representation of a multiple person entries.
* Response Status: 200 OK

## jENV
1. Documentation: https://www.jenv.be/
2. Installation: https://formulae.brew.sh/formula/jenv

## Docker
This code includes a `docker-compose.yml` in the root directory. This file starts the Kafka and Zookeeper instances, configured the same as the kafkaDemo application, and the MySQL instance. 

## Kafka
Once the Zookeeper and Kafka Docker instances are started, run `sh docker/kafka/createTopics.sh` from the root project directory to create the topics needed for this project.

### Build Project
1. Open terminal and navigate to project directory
2. Execute `./gradlew bootJar`
    1. Generates `.jar` artifact
    2. Location: `build/libs/demoext-0.0.1-SNAPSHOT.jar`

## Run Application from Command Line
1. Open terminal and navigate to project directory
2. Launch
   1. Open terminal and navigate to project directory
   2. Execute `java -jar build/libs/demo-0.0.1-SNAPSHOT.jar`
   3. By default, it will launch the application using port 8081
3. Launch using specific port
   1. When port 8081 is in use, or when executing multiple instances of the application
   2. Execute `java -jar -Dserver.port=[desired port number] build/libs/demo-0.0.1-SNAPSHOT.jar`
4. If code is adjusted the `.jar` file must be regenerated using `./gradlew bootJar`, otherwise you'll feel ashamed after an hour of wondering why your changes did not take effect

### Run Application from IntelliJ/VSCode
Navigate to `com.example.demoext.Application` in the project explorer. Right-click on the file and select `Run 'Application.main()'`
This will launch the application from the IDE. The application can also be launched in debug mode by right-clicking the file and selecting `Debug 'Application.main()'`.  

