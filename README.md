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
 * Database: embedded H2 with included data (see import.sql in resources), so no need to configure any 3rd-party database server
 * Testing: JUnit + Mockito

How to compile: `mvn clean package`

How to run: `java -jar target/lunchapp-1.0.jar`

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

GET /restaurant/list - list all restaurants

POST /restaurant/3 - edit existing restaurant (ID=3).

Required Headers: `X-LunchApp-Username`, `X-LunchApp-AuthKey`


