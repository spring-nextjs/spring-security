# OAuth2 Resource Server with Username/Password and JWT

## 1 Description
This is a simple example of an OAuth2 Resource Server that uses a Username, Password and JWT (JSON Web Tokens) for authentication and authorization.
[Documentation available here](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html)

## 2 Requirements
- [Git](https://git-scm.com/downloads) 
- [Java 21 or higher](https://adoptium.net)
- [Maven 3.9.9 or higher](https://maven.apache.org/download.cgi)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [Postman](https://www.postman.com/downloads/) (optional, for testing the API)

## 3 Installation
First, clone the repository:
```bash
git clone https://github.com/spring-nextjs/spring-security.git
```
Then, navigate to the project directory:
```bash
cd spring-security/oauth2-resource-server-username-password-jwt
```
Create the certificates
```bash
cd src/main/resources/certs
```
Generate the private key and public certificate using Git Bash or any terminal that supports OpenSSL:
```bash
openssl genrsa -out keypair.pem 2048
```
```bash
openssl rsa -in keypair.pem -pubout -out public.pem
```
```bash
openssl pkcs8 -topk8 -inform PEM -outform PEM -in keypair.pem -out private.pem
```

## 4 Run the Application
Note: The project is using Docker to run the PostgreSQL database. Make sure Docker Desktop is running on your machine.
```bash
mvn spring-boot:run
```

## 5 Test the Application
You can use Postman to test the API.
The example requests are provided in the `src/main/resources/postman` folder. You can import the `collection_to_import.json` file into Postman to test the API endpoints.