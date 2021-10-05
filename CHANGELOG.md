# CHANGELOG
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## UNRELEASED

##ADD
- **`feature` `#04 - Spring Boot: Test de Servicios (Mockito)` // `#059` - Escribiendo tests assertThrow para afirmar que la excepción lanzada sea correcta.**
  - _The `DATA` class has been updated_
  - _The `setUp` method of `SpringbootTestApplicationTests` class has been updated_
  - _The `testTransferInterAccounts_NotEnoughMoneyException` test has been added in `SpringbootTestApplicationTests` class._
  - _The `transfer` method of `AccountServiceImpl` class has been updated._
  

- **`feature` `#04 - Spring Boot: Test de Servicios (Mockito)` // `#058` - Test verify**  
  - _The `testCorrectTransferInterAccounts` of `SpringbootTestApplicationTests` class has been updated and the `verify` methods have been added._
  

- **`feature` `#04 - Spring Boot: Test de Servicios (Mockito)` // `#057` - Escribiendo nuestros tests con JUnit y mockito**  
  - _The `AccountService` and `AccountServiceImpl` have been updated._
  - _The `BankRepository` have been updated._
  - _A class containing test data has been created._
  - _A test has been created in the `SpringbootTestApplicationTests` class that checks that a transfer between accounts is made correctly._
  

- **`feature` `#04 - Spring Boot: Test de Servicios (Mockito)` // `#056` - Implementando la clase de servicio (Service)**
  - _The `AccountService` class has been implemented._
  

- **`feature` `#04 - Spring Boot: Test de Servicios (Mockito)` // `#055` - Creando los repositorios**  
  - _`Account` and `Bank` repository interfaces have been created._
  - _`Account` service has been created._  
  

- **`feature` `#04 - Spring Boot: Test de Servicios (Mockito)` // `#054` - Creando las clases del modelo**  
  - _An Exception has been created to trigger when there is not enough money in an account._  
  - _`Account` and `Bank` models have been created._  
  - _The test package has been added to the tests folder and the `SpringbootTestApplicationTests` class has been moved there._
  

- **`feature` `#04 - Spring Boot: Test de Servicios (Mockito)` // `#053` - Introducción y creando el proyecto Spring**
  - _The project has been created and initialized._  

---