# Modularization notes

## Compilation phase

- modularization is enabled by simply dropping `module-info.java` file into `src/main/java` folder
- this works for both IntelliJ and Maven compilation
- a single module for whole app is created as more granulation makes no sense
- module must be declared as `open` - without this Spring & Hibernate will not be able to access our classes by reflection
- `lombok` can be imported as `requires static` as it is not used in runtime
- in `maven-compiler-plugin` one has to directly enumerate annotation processors to be used as this does not work automatically when module path is used - see:

https://stackoverflow.com/questions/60693210/java-11-module-info-how-to-register-an-annotation-processor

## Runtime phase

### With IntelliJ

- IntelliJ runs application automatically using module path if it does detect `module-info.java` file on classpath. It seems that there is no option to disable this behavior. 
- this does also apply to tests execution, so some extra actions must be performed to make tests compiling and running:
- `maven-surefire-plugin` must be updated to `3.0.x` version as `2.22.x` one uses too old version of `asm` library whih does not work with new JDKs (TODO: why this problem occurs when modularization is enabled?)
- another option is to directly force `maven-surefire-plugin` to use the newest version of `asm` library
- `@SpringBootTest` annotation requires direct name of application class as automatic scanning does not work with module path (a bug fixed in 3.1 version of Spring Boot) - see:

https://github.com/spring-projects/spring-framework/issues/21515

### With Maven

- first step is to change build process to copy all dependencies and target JAR to common place (`target\modules` folder)
- to accomplish this two Maven plugins must be configured (`maven-jar-plugin` and `maven-dependency-plugin`)
- after that running application is pretty straightforward:

`java -p target/modules --add-modules=ALL-MODULE-PATH -m eu.chrost.loan/eu.chrost.loan.Application`

- `--add-modules` switch is needed as some modules are not loaded if they are not explicitly requested (e.g. `org.apache.tomcat.embed.core`, `com.fasterxml.jackson.core`)
- lack of these two modules causes strange behaviour of the app (Tomcat server is not being started and JSON is not accepted as request body)  

