
# Welcome to Java Academy

Please, use this repository as a base for your training.  
The idea is to fork this project so everyone use the same folder structure for the exercises.

## Objective

This course teaches the basics of Java development and the tools and frameworks that surround it.


## Who Should Attend

The training will start at a low level and does not require in depth knowledge of the platform in question. Desirable participant profile: Globant interns with a basic knowledge on OOP and a lot of energy!


## Technical Assistance

You can contact other participants or any available trainer if you need technical assistance. 

## Pre Requisites

1. Install [Java SDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
2. The IDE to use is [IntellIJ IDEA] (https://www.jetbrains.com/es-es/idea/download/). Community version  is enough for Academy completion.
3. Each Topic Practice should be commited into a separated branch with <*your_name>/topic<#>*. Example: ***juan_perez***

## General Guidelines

The Academy is organized in the following way:

- Each Developer needs to read about the topics and jump into the coding exercise. 
- Slack will be used for technical communications, slack channel name is #java-dojo-ar
- Every person participating in the Academy is present here (trainees and trainners). Here is the place to ask for technical assistance!
- All code and documentation must be in English.
- Until Topic 6, use of Lombok library is prohibited.

# Topic 0: Introduction to Java and Object Oriented Programming Concepts

## Reading:

1. [Github Essentials](https://docs.github.com/en/get-started/start-your-journey/hello-world)
2. [JVM](http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/gc01/index.html#t1s1). See: "Java Technology and the JVM"
3. Java Concepts [basic tutorial](http://docs.oracle.com/javase/tutorial/java/concepts/)
4. Java 8 Streams
    - https://www.baeldung.com/java-8-streams
    - https://java2blog.com/java-8-stream
    - https://www.baeldung.com/java-groupingby-collector

## Topic Practice
Build a basic e-commerce application in Java that allows users to buy products, add products to the shopping cart, view their cart and print some details about the cart. The idea for this part of the topic is to think about how to design the models and business rules without using any frameworks, designing the Object Oriented Model and write it in Java.


### Consider the following information:

- Each Shopping Cart should have a Customer
- For Customer Name, LastName, BirthDate, Email , Phone and Identification Number.
- Products can be of three types, ELECTRONIC, LIBRARY, OTHERS. 
- For Product, price, name, description and type attributes are required
- The shopping cart can have a list of products in different categories and quantities.
- The shopping cart should have a Status of DRAFT, SUBMITTED. 
- Each model needs to have an attribute named “id” and has to be unique


### Building objects:
- Create a Customer named JOHN SMITH with your favourite birth date.
- Create 5 Products of each type.
- Create a shopping cart with 4 ELECTRONIC products
- Create a shopping cart with 3 LIBRARY products
- Create a shopping cart with 1 OTHER products
- Submit a Shopping Cart


### Expected Result

Given a shopping cart with a list of products in different categories (At least 3 for category). Please do the following:
1. Calculate and Print all products with price > 100 and product type is library
2. Calculate and Print sum(price) of all products
3. Calculate and Print sum(price) of all electronic products
4. Calculate and Print all information of the shopping cart ordered by price, as shown in the following table
``` 
Cart ID | ID | PRODUCT TYPE | PRODUCT NAME | PRICE

123123 | 300 | OTHER | T-shirt | 12.4
123123 | 201 | LIBRARY | Harry Potter | 15.4
123123 | 200 | LIBRARY | 1984 | 20.3
123123 | 202 | LIBRARY | Java 8 | 52.4
123123 | 100 | ELECTRONIC | CPU I5 | 200.0
123123 | 101 | ELECTRONIC | CPU I7 | 500.4

```

## Commit:

Commit your practice code, whatever you have accomplished and discuss with your coach.


# Topic 1: Design and Development Principles
## Reading

Explore [Design Patterns](https://refactoring.guru/design-patterns). 
The idea is to know they exist, and when we discuss about them, we'll see why they exist.
Understand that there are zillions, so take it easy but do take the time to have an overview of them.

Ideally, let's make the time to get familiar with the basics of the following:
+ [SOLID] (https://docs.google.com/presentation/d/1T7J_labYuLxOjPtouj6G8ETqO1Mm5cEOJ71mIBqqq1w)
+ [Clean Code] (https://docs.google.com/presentation/d/1tBRtFu7n5dEI82T6SoERnj6LgBoaMpYnfwXO2yd2RWY)
+ [Singleton](https://refactoring.guru/design-patterns/singleton)
+ [Facade](https://refactoring.guru/design-patterns/facade)
+ [Factory](https://refactoring.guru/design-patterns/factory-method)
+ [Abstract Factory](https://refactoring.guru/design-patterns/abstract-factory)
+ [Builder](https://refactoring.guru/design-patterns/builder)
+ [Decorator](https://refactoring.guru/design-patterns/decorator)
+ [Strategy](https://refactoring.guru/design-patterns/strategy)

## Topic Practice:
Improve your Java Application applying these basic design patterns

# Topic 2: Application Building Using Maven

## Reading:
1. [What is Maven?](http://maven.apache.org/what-is-maven.html)
2. Have fun with [Maven in 5 minutes](http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html).
3. Maven: [best practices](http://books.sonatype.com/mvnref-book/reference/pom-relationships-sect-pom-best-practice.html)
4. Logging: [spring-boot-logging](https://www.baeldung.com/spring-boot-logging)

## Topic Practice:

(It is assumed that Maven is already installed and working).


1. Configure logging and add some log statements inside it.
2. Configure loggint to log into a file rolling per day

## Commit:

Commit your practice code, whatever you have accomplished.


# Topic 3: Unit Testing with JUnit and Mockito

## Reading:
1. [Benefits of Unit Testing](https://dzone.com/articles/top-8-benefits-of-unit-testing)
2. [JUnit](https://junit.org/junit5/docs/current/user-guide/)
3. [Code coverage](https://www.codium.ai/glossary/code-coverage/)
       3.1 [Configure JaCoCO in Maven](https://www.baeldung.com/jacoco)
4. [Mockito](https://site.mockito.org/)
5. [Difference Between Mocks and Spies](https://baeldung.com/mockito-spy)
6. Optional, good to know its existence though - [Test Driven Development](http://technologyconversations.com/2013/12/24/test-driven-development-tdd-best-practices-using-java-examples-2/)

## Topic Practice:

1. Implements all unit test cases in JUnit for the methods implemented in the application.
2. Configure your build to have at least 80% of Line and Branch Coverage 

## Commit:

Commit your practice code, whatever you have accomplished and discuss with your coach.

→ [index](#index)

# Topic 4: Persistence Layer

## Reading

In order to create our persistence layer is important to introduce a new concept: JDCBC .
JDBC is a powerful API that simplifies the process of connecting Java applications to databases. By using JDBC, developers can easily execute SQL queries and manage database connections, making it an essential tool for Java developers working with relational databases.

[Introduction to JDBC ](https://www.baeldung.com/java-jdbc)

## Practice

1. Create the database design in MySQL. Create the .sql file for the different models that you created in the topic 1
2. Create repository classes to comply with the following requirements using JDBC

    1. Create a Customer
    2. Update a Customer
    3. Delete a Customer
    4. Create a Product
    5. Create a cart
    6. Find all products given a cart identifier
    7. Find a product given unique identifier
    8. Create a product
    9. Update a product
    10. Add Product to a given Cart
        
3. Generate Unit test to test repositories using H2 in memory databases

# Topic 5: Convert to Spring Boot application


## Reading
In order to create our persistence layer is important to introduce a new concept: Java Persistence
API (JPA). JPA is a java specification for mapping Java objects to the tables of relational
databases. JPA is just a specification and it can't perform persistence or anything else by itself.
JPA is only a set of interfaces and requires an implementation. Implementations of this
specification don't only handle the code to interacts with the database, but also map the data to
objects structures used by the application. You can go further following this link:

Spring Fwk Concepts:
+ [Spring Boot Introduction](https://www.baeldung.com/spring-boot-start)
+ [Spring Framework Fundamentals](https://www.baeldung.com/org-springframework)
+ [Spring MVC](https://www.baeldung.com/spring-mvc-tutorial)

Spring Data concepts
+ [Spring Data JPA](https://spring.io/guides/gs/accessing-data-jpa)
+ [spring-boot-hibernate](https://www.baeldung.com/spring-boot-hibernate)
+ [H2 Quick Start](https://www.h2database.com/html/quickstart.html)

## Practice

This topic is all about persistence and in the practice we want focus on Service Layer and
Repository Layer, so you need to do the following tasks:

+ Create persistence layer for Products , Carts and Customers that allows you to store all information related
  to the ecommerce operations using Spring Data (Convert it from your current application) .
  This persistence layer should have the ability to handle read and
  save operations, with the possibility of extending it in the future by adding as needed.
+ Create the Service Layer to handle all the business logic of your application

# Topic 6: REST Application Design 

1. [What's HTTP?](https://code.tutsplus.com/tutorials/http-the-protocol-every-web-developer-must-know-part-1--net-31177)
2. [Introduction to REST](https://towardsdatascience.com/introduction-to-rest-apis-90b5d9676004)
3. [How to design a good API and why it matters](https://betterprogramming.pub/restful-api-design-step-by-step-guide-2f2c9f9fcdbf?gi=f181e336c8f9)
4. [Building Rest Services With Spring - Practical Tutorial](https://spring.io/guides/tutorials/rest/)
5. [REST API documentation with OpenAPI 3.0](https://www.baeldung.com/spring-rest-openapi-documentation)
6. [Spring Actuator](https://spring.io/guides/gs/actuator-service)

## Topic Practice

Create Shopping API using Spring Boot with the following features.

1. Create a Customer
2. Update a Customer
3. Delete a Customer
4. Create a Product
5. Create a cart
6. Find all products given a cart identifier
7. Find a product given unique identifier
8. Create a product
9. Update a product
10. Add Product to a given Cart
11. Document Spring REST API using OpenAPI 3.0 specification
12. Configure actuator health endpoint

## Commit:
Commit your practice code, whatever you have accomplished.


# Topic 7: Java Caching
## Reading:
This topic is about Caching, so we are going to learn and implement a solution using this feature.
+ [Spring Caching tutorial](https://www.baeldung.com/spring-cache-tutorial)
+ [Spring Boot feature Caching](https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/boot-features-caching.html)
+ [Spring Cache annotations](https://docs.spring.io/spring-framework/reference/integration/cache/annotations.html)

## Topic Practice 
Implement a caching mechanism for the entities of Products and Shopping Cart previously developed in the topic before
1. In your Service implementation choose some method, for example findProductsById and implements Caching feature.
2. Test the caching mechanism by invoking the annotated methods multiple times and verifying that the data 
is retrieved from the cache instead of executing the method that is called generating the results each time is invoked.


# Topic 8: Connecting Services with RabbitMQ
## Reading:

+ [What is RabbitMQ](https://www.tutorialspoint.com/rabbitmq/rabbitmq_overview.htm)
+ [Spring RabbitMQ Guide](https://spring.io/guides/gs/messaging-rabbitmq)

## Topic Practice    
Using RabbitMQ, Spring Boot and Java implement a new Service called "Notification Service" who receive messages with destinations (Can be SMS or emails) and delivers it.

A notification should send to the customer when :
1. Is the customer Birthday
2. A shopping cart pass to SUBMITTED State


Your Architecture should respect the following diagram 

![image](https://github.com/user-attachments/assets/97e3adf0-ee92-45ec-b2e2-8ca5031332c7)


## Commit:
Commit your practice code, whatever you have accomplished.
        
# Topic 9 : Dockerize your architecture
## Reading

+ [Docker for begginers](https://docker-curriculum.com/)
+ [Docker Componse, Dockerfile , Docker](https://www.baeldung.com/ops/docker-dockerfile-docker-compose)
+ [Docker Compose](https://docker-curriculum.com/#docker-compose)
+ [Introduction to Docker Compose](https://www.baeldung.com/ops/docker-compose)
  
## Topic Practice

Create a Docker Compose structure for the architecture created in Topic 7.

## Commit:
Commit your practice code, whatever you have accomplished and disuss with your coach.




# Topic 10 : Cloud Computing & AWS
## Prerequisites

+ Create your AWS account 
+ Use ONLY Free tier eligible products
+ Please turn off all the artifacts you create in order avoid charges.
+ It's your responsability to maintain your account into Free Tier Services layer
     
## Reading

+ [AWS Practitioner Essentials](https://www.youtube.com/watch?v=JsmhEgIV1mQ))
+ [Regions and Availability Zones](https://www.youtube.com/watch?v=NNHz0h5OyU4)
+ [Creating Docker app in EC2 Free tier](https://www.youtube.com/watch?v=qNIniDftAcU)
+ [Security Groups](https://www.youtube.com/watch?v=nA3yN76cNxo)
+ [Elastic IP Address in EC2](https://www.youtube.com/watch?v=4T6fk6UtpsE)
  
  
## Topic Practice

Publish your API's using EC2 to upload your docker architecture, Security Groups and Elastic IP to get a public Url to access.

## Commit:
Commit your practice code, whatever you have accomplished and disuss with your coach.




# Complementary Reading

+ [Training - Upskilling Java](https://university.globant.com/group/656)
+ [The Java Memory Model - The Basics](https://www.youtube.com/watch?v=LCSqZyjBwWA)
+ [Spring Bean Scopes](https://www.baeldung.com/spring-bean-scopes)
+ [Functional programming - Java](https://globant.udemy.com/course/functional-programming-with-java/)
