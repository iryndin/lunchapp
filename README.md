# lunchapp - Lunch Management Application

## Task

The task is:

Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

## Solution

The software is done with following technologies:

 * Spring Boot
 * Hibernate
 * Database: embedded H2 with included data (see [import.sql](src/main/resources/import.sql)), so no need to configure any 3rd-party database server
 * Testing: JUnit + Mockito

How to build: `mvn clean package`

How to run: `java -jar target/lunchapp-1.0.jar`

Test coverage (jacoco report): overall 67% instructions covered, 71% branches covered.
For most critical `service` package we have far better results: 91% instructions covered, 82% branches covered.

How to manage database:

 * go to `http://localhost:8080/h2console`
 * saved settings should be: `Generic H2 (Embedded)`
 * driver class: `org.h2.Driver`
 * jdbc url should be: `jdbc:h2:mem:~/lunch`
 * username: `sa`
 * password: (no password)

## REST API

### Authentication and authorization

Two HTTP headers are used:

 * `X-LunchApp-Username` - contains user name, e.g. `admin`, `johnr`, etc.
 * `X-LunchApp-AuthKey` - contains auth key which is md5 taken from string username+`:::`+password.
    E.g. username is `user1` and its password is `abcde123`, so to calculate auth key
    you need to take md5 from string `user1:::abcde123`.

Look at [AuthUtils.java](src/main/java/net/iryndin/lunchapp/web/AuthUtils.java) - it contains all necessary auth code.


### Restaurant Management Endpoints

 * GET /restaurant/list - list all restaurants
   Example request: `curl -X GET http://localhost:8080/restaurant/list`
 * PUT /restaurant - create new restaurant with given name
   Required Headers: `X-LunchApp-Username`, `X-LunchApp-AuthKey`.
   Required params: `name`.
   Example request (given that username is `admin2` and its password is `apassword2`):
   `curl -X PUT --data "name=zzz" -H "X-LunchApp-Username: admin2" -H "X-LunchApp-AuthKey:4116e66266f900aa71d44dad86ff58d6" http://localhost:8080/restaurant`
 * POST /restaurant/3 - edit existing restaurant (ID=3).
   Required Headers: `X-LunchApp-Username`, `X-LunchApp-AuthKey`.
   Required params: `name`.
   Example request (given that username is `admin1` and its password is `apassword1`):
   `curl -X POST --data "name=newname" -H "X-LunchApp-Username: admin1" -H "X-LunchApp-AuthKey: 6ece548c3b9c6503d4be776fcf2a2c5" http://localhost:8080/restaurant/3`
 * DELETE /restaurant/3 - delete existing restaurant (ID=3).
   Required Headers: `X-LunchApp-Username`, `X-LunchApp-AuthKey`.
   Example request (given that username is `admin1` and its password is `apassword1`):
   `curl -X DELETE -H "X-LunchApp-Username: admin1" -H "X-LunchApp-AuthKey:6ece548c3b9c6503d4be776fcf2a2c5" http://localhost:8080/restaurant/1`

### Menu items management

 * GET /menu/1 - get menu items for restaurant 1
   Example request: `curl -X GET http://localhost:8080/menu/1`
 * PUT /menu/3 - create new menu item for restaurant ID=3
   Required Headers: `X-LunchApp-Username`, `X-LunchApp-AuthKey`.
   Required params: `name`, `price`.
   Example request (given that username is `admin1` and its password is `apassword1`):
   curl -X PUT --data "name=menuitem1&price=111.11" -H "X-LunchApp-Username: admin1" -H "X-LunchApp-AuthKey:6ece548c3b9c6503d4be776fcf2a2c5" http://localhost:8080/menu/3
 * POST /menu/17 - edit existing menu item ID=17
   Required Headers: `X-LunchApp-Username`, `X-LunchApp-AuthKey`.
   Optional params: `name`, `price`.
   Example request (given that username is `admin1` and its password is `apassword1`):
   `curl -X POST --data "name=zzzz" -H "X-LunchApp-Username: admin1" -H "X-LunchApp-AuthKey:6ece548c3b9c6503d4be776fcf2a2c5" http://localhost:8080/menu/16`
   `curl -X POST --data "price=999" -H "X-LunchApp-Username: admin1" -H "X-LunchApp-AuthKey:6ece548c3b9c6503d4be776fcf2a2c5" http://localhost:8080/menu/17`
 * DELETE /menu/16 - delete existing menu item ID=16
   Required Headers: `X-LunchApp-Username`, `X-LunchApp-AuthKey`.
   Example request (given that username is `admin1` and its password is `apassword1`):
   `curl -X DELETE -H "X-LunchApp-Username: admin1" -H "X-LunchApp-AuthKey:6ece548c3b9c6503d4be776fcf2a2c5" http://localhost:8080/menu/16`

### Vote management

 * POST /vote/1 - vote for restaurant ID=1
   Required Headers: `X-LunchApp-Username`, `X-LunchApp-AuthKey`.
   Example request (given that username is `user1` and its password is `password`):
   `curl -X POST -H "X-LunchApp-Username: user1" -H "X-LunchApp-AuthKey:4d0280f5199720f29a02787fd16285d8" http://localhost:8080/vote/1`
 * GET /vote/1 - get today votes for restaurant ID=1
   Example request: `curl -X GET http://localhost:8080/vote/1`
 * GET /vote - get today votes for all restaurants
   Example request: `curl -X GET http://localhost:8080/vote`
 * GET /vote/result - get results of today voting
   Example request: `curl -X GET http://localhost:8080/vote/result`

