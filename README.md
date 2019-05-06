# Enterprise Exam  
[![Build Status](https://travis-ci.com/olaven/exam-PG5100.svg?token=zTzVh5wrqM89cpyf9qVd&branch=master)](https://travis-ci.com/olaven/exam-PG5100)

## Entry points 
For starting in dev mode: `LocalApplicationRunner.java`
For running frontend-test with chrome-driver: `SeleniumLocalIT.java`
For running frontend-test with docker: `SeleniumDockerIT.java`

## Assumptions 
1. The task mentions popular card games as examples. 
My assumption is that we are not supposed to model a copy 
of such a card game, but rather, something _like_ those games
2. It is mentioned that some features are only available to logged-in users. 
My assumption is that other features are available to everyone. For 
example, I am assuming that every page-visitor may view items. 

## Extras
* The app has support for admin-users 
* The app is deployed to [Heroku](https://enterprise-exam.herokuapp.com) 
* The app is running on Travis 
* Selenium-tests can be run in Docker using `SeleniumDockerIT`
* I am using bootstrap for some CSS-styles 


## Notes 
To build and run production-version: 
    'mvn clean package && docker-compose up'

To deploy new version: "cd frontend && mvn clean package heroku:deploy -Dheroku.logProgress=true"
To check dependencies: "mvn verify -P dependency-check"
Start Application with docker: "docker build frontend/ -t docker-frontend && docker run exam-frontend -p 8080:8080"
