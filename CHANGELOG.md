# CHANGELOG
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## UNRELEASED

#ADD
- **`feature` `#05 - Spring Boot: Test de Repositorios (DataJpaTest)` // `#067` - Modificando nuestros repositorios con Spring Data JPA**
  - _The `SpringBootTestApplicationTests` and `DATA` class has been updated to solve some errors caused that update of repositories and service._ 
  - _The `AccountServiceImpl` class has been updated to solve some errors caused that update of repositories._
  - _The repositories has been converted to `JpaRepository`._
  

- **`feature` `#05 - Spring Boot: Test de Repositorios (DataJpaTest)` // `#065` - Configurando el contexto de persistencia JPA y clases entities para test**
  - _The `import.sql` file has been added to aggregate test data to h2 database._
  - _The models have been mapped to database tables with JPA annotations._
  - _The configuration to disable Hibernate Log traces from the test context has been added._
  - _The JPA and h2 database dependencies have been added in pom file._
---

## `v1.0.0` _05/10/2021_

##ADD
- **`feature` `#04 - Spring Boot: Test de Servicios (Mockito)` // `#063` - Deshabilitando la traza del logs de Spring en el contexto test**
  - _The configuration to disable the banner and Spring Log traces from the test context has been added._
  

- **`feature` `#04 - Spring Boot: Test de Servicios (Mockito)` // `#061` - Uso de anotaciones de spring @MockBean y @Autowired**
  - _The `setUp` method of the `SpringbootTestApplicationTests` class has been removed._
  - _Mocks are set through Spring annotations._
  

- **`feature` `#04 - Spring Boot: Test de Servicios (Mockito)` // `#060` - Escribiendo tests con assertSame**
  - _The `verify` methods of tests of `SpringbootTestApplicationTests` class have been updated and several `verify` methods have been added in all tests._
  - _The `findByid` method in `AccountRepository` class has been renamed by `findById`._
  - _The `testCheckAccountSameReference_assertSame` test has been added in `SpringbootTestApplicationTests` class to test `assertSame` method._
  

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