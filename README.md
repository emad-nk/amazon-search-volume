# README #

The project is based on the following technologies:

* Java 1.8
* Spring MVC with Spring Boot
* Maven
* Intellij as IDE 
* Swagger UI for documentation

### About the solution ###

* The app returns estimated volume of a keyword searched by Amazon API. 
* When keyword is sent to the backend, it is divided into characters. It will try with each character by appending them to find the exact keyword in amazon api response, the sooner it finds the keyword in the response the more score that keyword gets. 
* Response size from amazon also plays a role in the calculation.

### How to run it? ###

* Clone the repository in your local pc, run the AmazonSearchVolumeApplication
* browse http://localhost:8080 
* Open the score-controller link, then "Try it out" button in the UI and insert the keyword 
* controller will return keyword and its score
* for simplicity you can also run the jar file in the jar folder with the command: java -jar amazonsearchvolume.jar

