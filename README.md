# cvbird-site

### Install
1. Clone the repo
   ```sh
   git clone https://github.com/EmelyanovKonstantin/TheArcticData.git
   ```
2. Install Java 17
   https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html <br>
   For Linux:
   ```sh 
   apt install openjdk-17-jdk openjdk-17-jre
   ```
   
### Build
From cvbird-site directory<br>
For Windows
```sh
   mvnw.cmd clean install
   ```
For Linux
   ```sh
   ./mvnw clean install
   ```
### Run
3. Run for Windows:
   ```sh
   mvnw.cmd spring-boot:run
   ```
   or for Linux:
   ```sh
   ./mvnw spring-boot:run
   ```