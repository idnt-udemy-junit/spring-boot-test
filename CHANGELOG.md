# CHANGELOG
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## UNRELEASED

- **`feature` `#07 - Spring Boot: Test de integración de Servicios Rest con WebTestClient` // `#086` - Escribiendo las primeras pruebas de integración parte 2**
  - _The uri in `testTransfer` method of `AccountControllerWebTestClientTest` class has been updated._
  - _The `testTransfer` integration test of `AccountControllerWebTestClientTest` class has been updated._
  

- **`feature` `#07 - Spring Boot: Test de integración de Servicios Rest con WebTestClient` // `#085` - Configuración y escribiendo las primeras pruebas de integración con WebClient**
  - _The `spring-boot-starter-webflux` dependency has been added._
  - _The `AccountControllerWebTestClientTest` class has been created._
  - _The `testTransfer` integration test in `AccountControllerWebTestClientTest` class has been created that tests the endpoint that performs a transfer between 2 accounts via their IDs._

--- 

## `v1.2.0` _14/10/2021_

- **`feature` `#06 - Spring Boot: Test de Controladores con MockMvc (WebMvcTest)` // `#083` - Escribiendo más pruebas para el Service en el método save()**
  - _The `testSave` in `SpringbootTestApplicationTests` class has been created to check that the account service holds an account for the account._
  - _The `save` method in `AccountService` class has been implemented._
  

- **`feature` `#06 - Spring Boot: Test de Controladores con MockMvc (WebMvcTest)` // `#082` - Escribiendo más pruebas para el Service en el método findAll()**
  - _The `testFindAll` in `SpringbootTestApplicationTests` class has been created to check that the account service retrieves the list of accounts._
  - _The `findAll` method in `AccountService` class has been implemented._
  

- **`feature` `#06 - Spring Boot: Test de Controladores con MockMvc (WebMvcTest)` // `#081` - Escribiendo más pruebas con MockMvc para el guardar**
  - _The response status of `save` method of `AccountController` class has been updated by `HttpStatus.CREATED`._
  - _The `testSave` test in `AccountControllerTest` class has been created to test that the endpoint that performs the saving of an account is working correctly_
  - _The verify of the call of the `findAll` method of `AccountService` has been added to the `testList` test of `AccountControllerTest` class._
  - _The `save` method of `AccountController` has been implemented._
  

- **`feature` `#06 - Spring Boot: Test de Controladores con MockMvc (WebMvcTest)` // `#080` - Escribiendo más pruebas con MockMvc para el listar**
  - _The `findAll` and `save` methods in account service has been created._
  - _The `list` and `save` methods (endpoints) in account controller has been created._
  - _The `testList` test in `AccountControllerTest` class has been created to test that the endpoint that performs the account listing is working correctly._
  - _The `list` method in `AccountController` has been implemented._
  

- **`feature` `#06 - Spring Boot: Test de Controladores con MockMvc (WebMvcTest)` // `#079` - Ejecutando tests con Cobertura de código (Code Coverage)**
  - _The `date` parameter that return `transfer` method of `AccountController` class has been updated._
  - _The `testTransfer` test in `AccountControllerTest` class has been updated._
  

- **`feature` `#06 - Spring Boot: Test de Controladores con MockMvc (WebMvcTest)` // `#078` - Escribiendo pruebas para el controlador parte 2**
  - _The `TransactionDTO` class has been updated._
  - _The `testTransfer` test has been created in the `AccountControllerTest` class to test the endpoint that performs the transfer operation between accounts from the account IDs._
  

- **`feature` `#06 - Spring Boot: Test de Controladores con MockMvc (WebMvcTest)` // `#077` - Escribiendo pruebas unitarias para el controlador con @WebMvcTest y MockMvc**
  - _The class `AccountControllerTest` has been created to test the endpoints of our application._
  - _The `testDetail` test has been created in the `AccountControllerTest` class to test the endpoint that retrieves the account details from the account ID._
  

- **`feature` `#06 - Spring Boot: Test de Controladores con MockMvc (WebMvcTest)` // `#076` - Probando los endpoints con Swagger UI**  
  - _The import.sql file has been copied in main context._
  - _The application.yml from main context has been updated._
  - _The swagger endpoints have been tested: `http://localhost:8080/v2/api-docs` and `http://localhost:8080/swagger-ui/`._
  

- **`feature` `#06 - Spring Boot: Test de Controladores con MockMvc (WebMvcTest)` // `#075` - Configurando Swagger**
  - _The controller has been updated to `RestController`._
  - _The `springfox-boot-starter` dependency has been added._
  - _The `SpringFox` configuration has been created._
  - _The application.yml of main context has been updated to avoid show logs messages_
  

- **`feature` `#06 - Spring Boot: Test de Controladores con MockMvc (WebMvcTest)` // `#074` - Creando controller parte 2**
  - _The `TransactionDTO` class has been created to receive transaction data through the body of a POST-type HTTP request._
  - _The `transfer` method has been created in `AccountController` to execute the transfer operation between accounts._
  - _The `AccountService` methods have been transactionals._
  - _The h2 dependency has been established for all contexts, it is no longer exclusive to the test context._
  

- **`feature` `#06 - Spring Boot: Test de Controladores con MockMvc (WebMvcTest)` // `#073` - Creando controller**
  - _Account controller created with `detail` method._

--- 

## `v1.1.0` _13/10/2021_

#ADD
- **`feature` `#05 - Spring Boot: Test de Repositorios (DataJpaTest)` // `#071` - Escribiendo pruebas para el update y el delete**
  - _The test to test the `update` method of `AccountRepository` has been created in `IntegrationJpaTest` class._
  - _The test to test the `delete` method of `AccountRepository` has been created in `IntegrationJpaTest` class._
  

- **`feature` `#05 - Spring Boot: Test de Repositorios (DataJpaTest)` // `#070` - Escribiendo pruebas para el save**
  - _The test to test the `save` method of `AccountRepository` has been created in `IntegrationJpaTest` class._
  

- **`feature` `#05 - Spring Boot: Test de Repositorios (DataJpaTest)` // `#069` - Escribiendo pruebas de integración con @DataJpaTest**
  - _The mapping of the models to the database has been updated._
  - _The import.sql file has been updated to correct some errors._
  - _Some tests have been added in `IntegrationJpaTest` class (`testFindById`, `testFindByIdThrowException`, `testFindByPersona`, `testFindByPersonaThrowException` and `testFindAll`)._
  

- **`feature` `#05 - Spring Boot: Test de Repositorios (DataJpaTest)` // `#068` - Modificando nuestros repositorios con Spring Data JPA parte 2**  
  - _A new method to find an `Account` by `personName` attribute has been created in `AccountRepository` class._
  

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