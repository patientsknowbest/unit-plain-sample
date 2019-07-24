unit-plain-sample
=================
Sample application using the [unit](https://github.com/patientsknowbest/unit)
library for an http server that can dynamically change ports.

build and run:
```bash
mvn clean install 
java -jar target/unit-plain-1.0-SNAPSHOT-shaded.jar
```

The application will run an http server on port 8080. You can enter a
new port on the command line to restart the server on the new port.