# ABC Banking

A system to manage tokens in a bank.

## Getting Started

### Prerequesites

* Java 8
* Maven
* Mysql

### Usage

1. Import as existing Maven project and set configuration in "application.properties" file e.g Databse name or Mysql configuration. Create a local database *banking* (or choose any name).

2. Run as spring boot app. The tables will be created by spring hibernate.

Data Model

![ABC Bank data model](/ERdiagram.png?raw=true)

Tables:

**user** - Stores info about all users.

**bank_service** - Stores services offered by bank.
For multi counter or multi step service, *next_service_name* column contains the name of service that should be executed after current service.

**counter** - Stores unique counters with numbers.

**service_counter** - Stores services counters mapping.

**token** - This stores the info of the newly created tokens.

**job**: For all the services selected by user, a job is created and stored in job table. A token is marked *COMPLETED* after all the jobs are finished.


3. Open swagger UI (http://localhost:8080/swagger-ui.html#/) to see list of available APIs.

4. Basic authorization is added to the APIs by user phone number and password. A new user can be created via:

```
POST /signup
```

5. If you are running for the first time, first you need to add counters and bank services using:

```
POST /counters
POST /services
```

But these are authorised to *MANAGER* and *OPERATOR* roles. For that you have to create a manual entry once into user table with column type = 2 or 3 (MANAGER or OPERATOR) after the tables are created.

6. Create new tokens by adding bank services names in the body via:

```
POST /tokens
```

7. To see all counters with assigned tokens hit:

```
GET /tokens
```

8. Refer documentation in banking.abc/doc folder and class diagrams placed in banking.abc/classdiagram folder.

## Token Management

### Allocation

At the time of new token creation, for each bank service selected under that token a new *job* is created and assigned to that token under *tokenJobs* list.
Then a counter is assigned based on minimum queue size, out of all the counters whichever serves the first service selected under that token.
So that token is added to the queue of tokens of that counter in ascending order of time of creation.

### Completion

To complete the current job of a token hit url:

```
PUT /tokens/{id}/complete
```
If a token has only one job then it's status is changed to *COMPLETED*.

In a multi service (or multi counter service) scenario, next job becomes current job and new counter is assigned as following:

*if* next service is served on same counter, the current counter remains same and only current job is changed,
*else* new counter is assigned and token is moved to another counter queue.

The token is marked *COMPLETED* when all the jobs under it are completed.


