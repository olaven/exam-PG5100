# Enterprise Exam  
[![Build Status](https://travis-ci.com/olaven/exam-PG5100.svg?token=zTzVh5wrqM89cpyf9qVd&branch=master)](https://travis-ci.com/olaven/exam-PG5100)

## Extras
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
