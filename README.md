# Thymeleaf example project
This is a sample Spring Boot project that uses a MVC config to set up Thymeleaf 
and add the Thymeleaf Layout Dialect. When the Layout Dialect is added and you run 
the service on Java 8, it will coredump the JVM. 

## How to Run

From the root of the project run:

```
mvn spring-boot:run
```

## How to not crash

Either run on Java 11 or comment out line 79 in the MVCConfig.java which is:

```
templateEngine.addDialect(layoutDialect());
```

## Testing

You can test the following URLs:
- Load the UI: http://localhost:8083 or http://localhost:8083?name=Grogu

