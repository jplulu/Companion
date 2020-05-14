# Companion

## Usage
```
git clone https://github.com/jplulu/Companion.git
cd Companion/
```
### Run Locally
Edit application.properties within Companion/src/main/resources
  * Make sure spring.datasource.username is your mySQL username
  * Make sure spring.datasource.password is your mySQL password
  * Make sure file.upload-dir is a valid path on your machine (this is where images will be stored)
  
Create a database named 'companion' in mySQL

```
# Server
mvn clean install
mvn spring-boot:run
  or
java -jar target/companion.jar

# Client
cd src/main/webapp/companion-frontend/
npm install
npm start
```

