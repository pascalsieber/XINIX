# Pink Elefant

## Requirements
* Java 8
* Postgres Database (see JDBC_DATABASE_URL)

## Running
    ./gradlew run

## Deploying
Build with

    ./gradlew shadowJar
    
generates a fat-jar at `./build/libs/pink-elefant-backend-all.jar`

Run with

    java -jar pink-elefant-backend-all.jar

## Configuration
All config parameters can be changed via environment variables.
------------------------------------------------------------------------------------------
| Parameter                          | Example                                           |
|------------------------------------|---------------------------------------------------|
| JDBC_DATABASE_URL                  | jdbc:postgresql://localhost/pews?user=pinkelefant |
| EXPOSED_SERVICE_URL                | https://localhost:8888/pews                       |
| MAIL_SMTP_HOST                     | smtp.office365.com                                |
| MAIL_SMTP_PORT                     | 587                                               |
| MAIL_ACCOUNT_USER                  | info@pinkelefant.ch                               |
| MAIL_ACCOUNT_PASSWORD              | mailpassword                                      |
| APPLICATION_PORT                   | 8888                                              |
| APPLICATION_HOSTNAME               | 0.0.0.0                                           |
| BOOTSTRAP_ROOT_PW                  | pink2017!                                         |
------------------------------------------------------------------------------------------
