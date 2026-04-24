# Practical Assessment
## Table of Contents
- [About](#about)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Installation](#installation)
- [Downloading project](#downloading-project)
- [Launching](#launching)
- [Database Testing](#database-testing)
- [Overall Testing](#overall-testing)
## About
  The system is designed as a drone warehouse/shop management system using SOAP, XML files, and an H2 database.
## Features
- Register/Login (Very basic)
- Manipulating data from and into XML file
- Manipulating data from and into H2 database
- Manipulating data using SOAP.
## Tech Stack
| Technology | Purpose |
|------------|---------|
| Spring Boot 4.0.3(Maven) | System Framework |
| Java 17 | Programming language |
| Swagger | API documentation + JavaDoc |
| H2 | Database |
| SOAP | Components containerization |

## Installation
1. If you want to test provider client, you have to download SOAP UI(https://www.soapui.org/downloads/soapui/)
To test in SoapUI:
  1. Enter the WSDL file address into SOAP UI: http://localhost:8081/services/managers.wsdl
  2. When making a request, use this URL: http://localhost:8081/services/


## Downloading project
To launch the project, you first need to download or clone it from the repository.
#### 1. Download
- Go to this repository: https://github.com/arnoldasstrukcinskas/Antras_PD_PI24SN
- Then press green button Code and and Download Zip.
- Unpack zip file
- Move to [Launching](#launching)
#### 2. Clone(If you have git)
- Open Terminal and go to directory you want to clone project(add your own directory)
  ```bash
  cd D:\example
  ```
- In terminal use this command:
  ```bash
  git clone https://github.com/arnoldasstrukcinskas/Pirmas_PD_PI24SN
  ```
- Move to [Launching](#launching)
#### 3. Launch
- If you want project to work you have to open both projects in base directory -> Antras_PD_PI24SN
- Launch both projects separately.
-  
## Launching (Bash)
If you do not have Maven installed into your computer you can launch it via powershell:
#### 1.Build project:
```bash
./mvnw clean package
```
#### 2. Launch project(with logs)
```bash
./mvnw.cmd spring-boot:run
```
#### 3. Stop project
```bash
ctrl + c
```

## Database Testing
(How to connect for testing)
#### 1. Open browser and go to:
```bash
http://localhost:8080/h2-console
```
#### 2. Connect to database
```bash
JDBC URL: jdbc:h2:mem:testdb
User name: sa
--------------------------------
Password: (Leave blank)
```

## Overall Testing
(How to connect for testing)
### For testing - some demo data is created in XML file
#### 1. Links for testing via front/back
```bash
  Go to: http://localhost:8080/swagger-ui/index.html
```
##### FOR FURTHER TESTING YUO WILL HAVE TO REGISTER(/register) AND THEN LOGIN(/login)
#### 3. Last bot not least
```bash
HAVE FUN!
```
