# REST API using Spring Boot
The project consists of HOW-TOs tutorial on building REST API using spring boot.

# Travis CI
[![Build Status](https://travis-ci.org/sanjeevghimire/helloworld-rest-api-springboot.svg?branch=master)](https://travis-ci.org/sanjeevghimire/helloworld-rest-api-springboot)

# Pre-Requisites
1. Spring Boot 1.5.0
2. Hibernate, JPA
3. Liquibase to manage data
4. H2 in memory database

# Running Application

1. Run `mvn spring-boot:run` to start the server

# Curl commnads

## Get All users - GET
Command:

`curl -H "Accept:application/json" http://localhost:8080/api/users` 

Sample Output: 

```[{"id":1,"username":"sanjeevghimire","firstName":"Sanjeev","lastName":"Ghimire","email":"gsanjeev7@gmail.com"},{"id":2,"username":"johndoe","firstName":"John","lastName":"doe","email":"johndoe@fakemail.com"}]```

## Create User - POST 
Command: 

```curl -i -X POST -H "Content-Type:application/json" http://localhost:8080/api/users -d '{"username":"sg","firstName":"S","lastName":"G","email":"sg@gmail.com"}'```

Sample Output: 

``` HTTP/1.1 201
Location: /api/users/
X-helloWorld-alert: A comcast user is created with identifier sg
X-helloWorld-params: sg
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sat, 03 Mar 2018 17:29:32 GMT
```

## Delete User - DELETE 

Command: 

`curl -v -X DELETE -H "Content-Type:application/json" http://localhost:8080/api/users/3` 

Sample Output:  

```< HTTP/1.1 200
< X-helloWorld-alert: A user is deleted with identifier 2
< X-helloWorld-params: 2
< Content-Length: 0
< Date: Sat, 03 Mar 2018 17:28:52 GMT
<
```

## Get User Posts - GET 

Command: 

`curl -H "Accept:application/json" http://localhost:8080/api/users/posts` 

Sample output: partial data  

```[{"id":1,"userId":1,"title":"sunt aut facere repellat provident occaecati excepturi optio reprehenderit","body":"quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"},{"id":2,"userId":1,"title":"qui est esse","body":"est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"},{"id":3,"userId":1,"title":"ea molestias quasi exercitationem repellat qui ipsa sit aut","body":"et iusto sed quo iure\nvoluptatem occaecati omnis eligendi aut ad\nvoluptatem doloribus vel accusantium quis pariatur\nmolestiae porro eius odio et labore et velit aut"},{"id":4,"userId":1,"title":"eum et est occaecati","body":"ullam et saepe reiciendis voluptatem adipisci\nsit amet autem assumenda provident rerum culpa\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\nquis sunt voluptatem rerum illo velit"},{"id":5,"userId":1,"title":"nesciunt quas odio","body":"repudiandae veniam quaerat sunt sed\nalias aut fugiat sit autem sed est\nvoluptatem omnis possimus esse voluptatibus quis\nest aut tenetur dolor neque"},.....```


## Deadlock simulation API - GET 

command: 

`curl -H "Accept:application/json" http://localhost:8080/api/thread/deadlock` 

Output: success 


# Running Tests

There are two kind of test written. 

1. Unit Tests
2. Integration Tests

Run: `mvn clean test`

# Swagger API definition
The API UI can be accessed using URL: `http://localhost:8080/swagger-ui.html`

![Swagger UI](https://github.com/sanjeevghimire/helloworld-rest-api-springboot/blob/master/swaggerUI.png)

# License

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)



