# DRONE DISPATCH HELPER

REST API that allows clients to communicate with the drones (i.e. dispatch controller)

## Basic project objectives
- [ ] registering a drone
- [ ] loading a drone with medication items
- [ ] checking loaded medication items for a given drone
- [ ] checking available drones for loading
- [ ] check drone battery level for a given drone


## TECH STACK
- Spring Boot
- Spring Data JPA
- Postgres
- Docker
- Git

### RUNNING THE PROJECT
.
# Installations
- **Tested on Ubuntu Linux and similar OS**

Follow the following link to install **git**, **docker** and **docker-compose**
- [ ] [Git](https://www.digitalocean.com/community/tutorials/how-to-install-git-on-ubuntu-20-04)
- [ ] [Docker](https://docs.docker.com/engine/install/ubuntu/) 
- [ ] [Docker Compose](https://docs.docker.com/compose/install/)
### Running the app
Then run the following commands:
```
git clone https://gitlab.com/brianbrix/musala-drone.git
```
This downloads the project to local computer in the current directory
```
cd musala-drone
```
This takes you to the project folder
```
docker-compose up --build
```

If run successfully this command the images required for the projects and then spins up the containers. It will create two containers; One for the **postgres** DB and another for the **app**.
Then eventually it will start the application. This can be observed from the logs on the terminal.

**NOTE: The application is also able to persist some initial records into the Database tables**.

[This link](http://localhost:8081/swagger-ui/#/drone-app-controller):
http://localhost:8081/swagger-ui/#/drone-app-controller
will take you to the API Documentation UI where you can find test the various endpoints.

## Assumptions 
- Each medication item will reduce a drone's battery capacity by 10.
- Each medication item image is a url to the image location.
- A new drone is registered with the IDLE state.
- Battery check logs are logged to user's **Documents** folder.
- Battery checks occur every 5 minutes.




-[ ] THE END