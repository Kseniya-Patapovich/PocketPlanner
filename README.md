# Pocket Planner
### Descriptions:

This project is a web application for managing financial transactions and goals. It allows users to keep records of their income and expenses, create and track financial goals.

## Database
The in memory database is connected to the project. It contains 5 tables. 
<li>Users - stores information about users, sach as username, age, role, date of created and changed. At the time of creation, it has 1 user.</li>
<li>Accounts - stores information about account, sach as number of account and target balance. At the time of creation, it has 1 account.</li>
<li>Transactions - stores information about transactions, sach as amount of transaction, date of transaction. At the time of creation, it has 2 transactions.</li>
<li>Goals - stores information about user's goals, sach as name, target amount and current amount. At the time of creation, it has 1 goal. And user_security - it stores data the login and password.</li>

## Registration
To register, go to the <http://localhost:8080/security/registration> path and enter the registration data in the body, such as login, password, username and age. 

## Authentication
To authenticate an existing user, you need to follow the path <http://localhost:8080/security/token> and enter the authentication data into the body, then you will receive a response with a JWT token.

## User capabilities
#### USER - regular user
Available endpoints:

1. <http://localhost:8080/user/{id}> - viewing user information

Example: (GET method: <http://localhost:8080/user/1>)

2. <http://localhost:8080/user> - create user

Example: (POST method: <http://localhost:8080/user>)

3. <http://localhost:8080/user> - change user

Example: (PUT method: <http://localhost:8080/user>)

4. <http://localhost:8080/user/{id}> - delete user

Example: (DELETE method: <http://localhost:8080/user/1>)

5. <http://localhost:8080/goal/{id}> - viewing information about goal by id

Example: (GET method: <http://localhost:8080/goal/1>)

6. <http://localhost:8080/goal/userId/{id}> - viewing information about all goals by user id

Example: (GET method: <http://localhost:8080/goal/userId/1>)

7. <http://localhost:8080/goal/{id}> - create new goal for user

Example: (POST method: <http://localhost:8080/goal/1>)

8. <http://localhost:8080/goal/{id}> - delete goal
   
Example: (DELETE method: <http://localhost:8080/goal/1>)

9. <http://localhost:8080/goal/{id}> - change goal

Example: (PUT method: <http://localhost:8080/goal/1>)

11. <http://localhost:8080/transaction/{id}> - viewing information about transaction by id

Example: (GET method: <http://localhost:8080/transaction/1>)

12. <http://localhost:8080/transaction/userId/{id}> - viewing information about transaction by user id

Example: (GET method: <http://localhost:8080/transaction/userId/1>)

13. <http://localhost:8080/transaction/{id}> - create new transaction

Example: (POST method: <http://localhost:8080/transaction/1>)

14. <http://localhost:8080/transaction/{id}> - delete transaction

Example: (DELETE method: <http://localhost:8080/transaction/1>)

15. <http://localhost:8080/account/{id}> - viewing information about user's account

Example: (GET method: <http://localhost:8080/account/1>)

16. <http://localhost:8080/account/userId/{id}> - viewing information about all accounts by user id

Example: (GET method: <http://localhost:8080/account/userId/1>)

17. <http://localhost:8080/account/{id}> - create new account by user id

Example: (POST method: <http://localhost:8080/account/1>)

18. <http://localhost:8080/account/{id}> - delete account

Example: (DELETE method: <http://localhost:8080/account/1>)

19. <http://localhost:8080/security/registration> - for registration new user

Example: (POST method: <http://localhost:8080/security/registration>)

20. <http://localhost:8080/security/token> - for authentication

Example: (POST method: <http://localhost/security/token>)

