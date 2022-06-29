# Introduction
The Twitter Application leverages the Twitter REST API to perform CRUD operations via the command line on a user's Twitter account. Users can POST, READ, and DELETE tweets from the command line.

This Java program uses HTTP Client to connect to the Twitter REST API,. Maven and spring-boot are used to structure the application.

The Data Access Object is the application's design (DAO)

Deployed on DockerHub for simple distribution. 


- JDK 8
- Twitter Rest API
- Maven
- HTTP Client
- Json
- Junit and Mockito
- Springboot
- Docker
- OAuth 1.1
  
<br>

# Quick Start
##Maven
````bash
mvn clean package -Dmaven.test.skip=true
````

````bash
#Edit the config.properites file in the resoruce foloder and add the API keys
# consumerKey=
# consumerSecret=
# accessToken=
# tokenSecret= 
java -jar target/java_apps-1.0-SNAPSHOT.jar "post"|"show"|"delete" [options]
````

##Docker
````bash
docker pull asalaymeh/twitter
docker run --rm \
-e consumerKey= \
-e consumerSecret=\
-e accessToken=\
-e tokenSecret=\
asalaymeh/twitter "post"|"show"|"delete" [options]

````

##Twitter App Usage
  Once the image is downloaded from Docker, The following commands are used to perform the Twitter CRUD operation.


###  POST USAGE
- "post" "status" "latitude:longitude"
  
  - Status = String
  - latituded = long (-180.0 to 180.0)
  - longitutde = long (-90.0 to 90.0)
  
````bash
docker run --rm \
-e consumerKey= \
-e consumerSecret=\
-e accessToken=\
-e tokenSecret=\
asalaymeh/twitter "post" "hello world " "1d:1d"

````


### SHOW USAGE
- "show" "tweet_id" 
  - tweet_id = Numeric String

````bash
docker run --rm \
-e consumerKey= \
-e consumerSecret=\
-e accessToken=\
-e tokenSecret=\
asalaymeh/twitter "show" "123419512359932" 

````

### DELETE USAGE
- "delete" "tweet_id,tweet_id" 
  - tweet_id = Numeric String 

````bash
docker run --rm \
-e consumerKey= \
-e consumerSecret=\
-e accessToken=\
-e tokenSecret=\
asalaymeh/twitter "delete" "123124125,53254235326,1241245262346" 

````

# Design
## UML diagram
<img src="./assest/UML%20Digram.png">

<strong>TwitterCLIApp</strong>
>Handles arguments and passes them to the controller. The outcome will be a JSON object displayed on the console.


<strong>TwitterController</strong>
>Handles arguments and passes them to the service. A Tweet object will be sent to the service layer to be handled


<strong>TwitterService</strong>
>Handles Tweet Object and ensures that the Tweet object is valid. The Tweet object is then passed to the DAO.

<strong>TwitterDAO</strong>
>Creates URI string with the Tweet Object provided by the service layer. The URI string is passed to the TwitterHTTPHelper.

<strong>TwitterDAO</strong>
>Takes URI String and sends a POST or GET request to the Twitter REST API.

## Models
The following classes are used to build a simplifed the Tweet Model 

- **Tweet:** The simplified version of the Tweet Model that Twitter provides.
- **Entites:** List of hashtags and user mentions
- **HashTag:** Hashtag text that is added to the tweet when posting or retrieving 
- **UserMention:** Users that have been mentioned in the tweet posting or retrieving
-  **Coordinates:** The location of the tweet/user as latitude and longitude in the tweet posting or retrieving


## Spring
Dependency management can become time-consuming and error-prone if projects get too big. Spring was utilized to manage the dependencies throughout the project as a defense against this. Spring will be informed that they are Spring Beans and will be in charge of managing them by creating annotations throughout the source code. 


@Component, @Repository, @..service  @..service @..Controller

# Test
JUnit and integration tests were used to test each essential component to guarantee that each component functions independently by utilizing Mockito to ensure each component worked properly.  
Dependencies were mocked to provide desired results. Additionally, integration tests were put in place to ensure that the system's actual functionality was working.

## Deployment
How did you dockerize your app.
````bash
docker_user = docker_hub_account_id

docker login 

cat > Dockerfile << EOF
FROM openjdk:8-alpine
COPY target/java_apps*.jar /usr/local/app/twitter/lib/twitter.jar
ENTRYPOINT ["java","-jar","/usr/local/app/twitter/lib/twitter.jar"]
EOF

#maven package jar
mvn clean compile package

# build the docker image
docker build -t ${docker_user}/twitterApp . 

#verify the docekr image 
docker image ls | grep "twitterApp"

docker push ${docker_user}/twitterApp

docker run --rm \
-e consumerKey= \
-e consumerSecret=\
-e accessToken=\
-e tokenSecret=\
${docker_user}/twitter "post"|"show"|"delete" [options]

````

# Improvements
- Delete or Get All tweets  
- Removing JSON format and providing a user friendly interface for retrieving tweets 
- Login into the application without using API keys rather than using a Twitter user account and password.