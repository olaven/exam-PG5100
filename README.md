# Enterprise Exam  
[![Build Status](https://travis-ci.com/olaven/exam-PG5100.svg?token=zTzVh5wrqM89cpyf9qVd&branch=master)](https://travis-ci.com/olaven/exam-PG5100)

## Application topic 
This application is a site where users can rank cards in a card-game - the made-up game "GwentStone". Each card has a title and a description. 
Users may rank them, and leave small comments about the rank they gave. Cards are (in my application) administrated 
by admin-users. Parts of the site is available to both registered users and non-users. 
Only users are able to contribute (i.e. vote/rank). 

## General notes
* I have configured a maven-plugin to build a Docker-image in the "package"-phase. Will be visible in "verify"-logging.  
* `mvn clean verify` will run frontend-tests locally _and_ in docker. Normally, this is would be unnecessary, 
but I am leaving it that way because this is an exam, and I want to show as much as possible.  

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

Several other users and items are added in test-mode. Full details are
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
* An admin-user may add new items. This is documented in `testCanAddItem`
* An admin-user may remove items. This is documented in `testCanRemoveItem`
* A user may delete their ranking on an item. This is documented in `testCanRemoveRank`
* A user may updated his/her name on the profile page. This is documented in `testCanUpdateUserDetails`
* The app is running on Travis. The travis-page is private, as this is an exam. However, the image-link in 
the title of this document is hopefully evidence enough.
* I have used Docker and Docker-Compose to run the project in production mode. To start this up, 
run the following in the root-directory: `mvn clean package && docker-compose up`
* Selenium-tests can be run in Docker using `SeleniumDockerIT`, as well as locally
* I am using bootstrap for some CSS-styles 



TODO: clean up this part of readme: 
## Notes 
To build and run production-version: 
    'mvn clean package && docker-compose up'
* The app is deployed to [Heroku](https://enterprise-exam.herokuapp.com)
To deploy new version: "cd frontend && mvn clean package heroku:deploy -Dheroku.logProgress=true"
