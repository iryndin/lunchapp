# Test CURL scripts

## Restaurants

curl -X GET http://localhost:8080/restaurant/list

curl -X POST --data "name=newname" -H "X-LunchApp-Username: admin1" -H "X-LunchApp-AuthKey:3453455" http://localhost:8080/restaurant/3

curl -X PUT --data "name=zzz" -H "X-LunchApp-Username: admin2" -H "X-LunchApp-AuthKey:3453455" http://localhost:8080/restaurant

curl -X DELETE -H "X-LunchApp-Username: admin1" -H "X-LunchApp-AuthKey:3453455" http://localhost:8080/restaurant/1

## Menu items

curl -X GET http://localhost:8080/menu/1

curl -X PUT --data "name=menuitem1&price=111.11" -H "X-LunchApp-Username: admin1" -H "X-LunchApp-AuthKey:3453455" http://localhost:8080/menu/3
curl -X PUT --data "name=menuitem2&price=222222.22" -H "X-LunchApp-Username: admin1" -H "X-LunchApp-AuthKey:3453455" http://localhost:8080/menu/3

curl -X POST --data "name=zzzz" -H "X-LunchApp-Username: admin1" -H "X-LunchApp-AuthKey:3453455" http://localhost:8080/menu/16
curl -X POST --data "price=999" -H "X-LunchApp-Username: admin1" -H "X-LunchApp-AuthKey:3453455" http://localhost:8080/menu/17

curl -X DELETE -H "X-LunchApp-Username: admin1" -H "X-LunchApp-AuthKey:3453455" http://localhost:8080/menu/1

## Votes

see today votes for restaurant 1
curl -X GET http://localhost:8080/vote/1

see today votes for all restaurants
curl -X GET http://localhost:8080/vote

see results of today voting
curl -X GET http://localhost:8080/vote/result

vote for restaurant 1 as user1
curl -X POST -H "X-LunchApp-Username: user1" -H "X-LunchApp-AuthKey:3453455" http://localhost:8080/vote/1



==========

voting
unit tests for all
logging
one single executable jar
document
graphite monitoring