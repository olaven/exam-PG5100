# Enterprise Exam  
[![Build Status](https://travis-ci.com/olaven/exam-PG5100.svg?token=zTzVh5wrqM89cpyf9qVd&branch=master)](https://travis-ci.com/olaven/exam-PG5100)

## General notes
* I have configured a maven-plugin to build a Docker-image in the "package"-phase. Will be visible in "verify"-logging.  

## Entry points 
* For starting in dev mode: `LocalApplicationRunner.java`
* For running frontend-test with chrome-driver: `SeleniumLocalIT.java`
* For running frontend-test with docker: `SeleniumDockerIT.java`

## Users

When testing the application, the following user is recommended: 
* username: dev@mail.com   
* password: dev
 
An admin-user is also added, both in dev- and prod. mode. 
* username: admin@mail.com
* password: admin  

Several other users and items are added in test-mode. The full list is 
available in `DefaultDataInitializer.java` 

## Tests from R4
* Sorting is tested in: SeleniumTestBase line: 143
* Filtering is tested in: SeleniumTestBase line: 153

## Assumptions 
1. The assignment mentions popular card games as examples. 
My assumption is that we are not supposed to model a copy 
of such a card game, but rather, something _like_ those games
2. It is mentioned that some features are only available to logged-in users. 
My assumption is that other features are available to everyone. For 
example, I am assuming that every page-visitor may view items. 
3. Under R1, it is said that a user may only rank an item once. However, in R3 it is 
said that the user should be able to update his/her rank. As I see it, these 
requirements are conflicting. My assumption is that the user should 
be able to update their ranking-score / comment. I deem this to be more important because it 
is a later requirement.

## Extras
* The app has support for admin-users 
* Ad admin-user may add new items. This is documented in `SeleniumTestBase`, `testCanAddItem`
* The app is running on Travis. The travis-page is private, as this is an exam. However, the link-image in 
the title is hopefully evidence enough.
* The app is deployed to [Heroku](https://enterprise-exam.herokuapp.com)
* I have used Docker and Docker-Compose to run the project in production mode. To start this up, 
run the following in the root-directory: `mvn clean package && docker-compose up`
* I am using bootstrap for some CSS-styles
 
* Selenium-tests can be run in Docker using `SeleniumDockerIT` 



TODO: clean up this part of readme: 
## Notes 
To build and run production-version: 
    'mvn clean package && docker-compose up'

To deploy new version: "cd frontend && mvn clean package heroku:deploy -Dheroku.logProgress=true"
To check dependencies: "mvn verify -P dependency-check"
Start Application with docker: "docker build frontend/ -t docker-frontend && docker run exam-frontend -p 8080:8080"
