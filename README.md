
## Robot World Project
## Overview
This project involves creating a client/server Robot World consisting of two main components: the server and the client. The server is responsible for managing the world, including obstacles, robots, and other artifacts. The client connects to the server, launches a robot into the world, and sends commands to control the robot.

## Architecture
Client/Server Architecture
The architecture follows a typical client/server model where:

The server program listens on a network port for incoming client connections and manages the world.
The client program connects to the server over the network, sends commands to control the robot, and receives responses from the server.

## Communication Protocol
The communication between the client and the server follows a request/response protocol, similar to the HTTP protocol used in web applications. The client sends requests (commands) to the server, and the server processes these requests and sends back responses.

## Server Program (Server.java)
## Responsibilities
Manage the world, including obstacles, robots, and other elements.
Listen for incoming client connections on a specified network port.
Receive instructions from connected clients and update the world state accordingly.
Handle multiple robots simultaneously, ensuring proper management and synchronization of all robots in the world.
Implementation
The server is implemented as a standalone, console-based Java application.
It initializes the world, including the layout, obstacles, and any additional features.
It uses multithreading to handle multiple client connections concurrently.

## Client Program (Client.java)
## Responsibilities
Connect to the server over the network.
Launch a robot into the world.
Allow the user to send commands to the robot, which are forwarded to the server.
Display the responses from the server, updating the user on the robot's status and interactions with the world.

## Implementation
The client is implemented as a standalone Java application.
It establishes a network connection to the server.
It provides a user interface (console-based or graphical) for sending commands to the robot and receiving responses from the server.
Setup and Running the Project

## Prerequisites
Java Development Kit (JDK) installed on the machine.
An IDE like IntelliJ IDEA, Eclipse, or a text editor with Java support.

## Server
When the server starts, it initializes the world and listens for client connections on a specified port.
The server console will display messages about the status of the world and incoming client connections.

## Client
When the client starts, it connects to the server.
The user can enter commands to control the robot, such as moving forward, turning, shooting, etc.
The client will display the server's responses, indicating the robot's status and interactions with the world.

## Commands
   Client Commands
   MOVE: Move the robot in the current direction.
   TURN_LEFT: Turn the robot left.
   TURN_RIGHT: Turn the robot right.
   SHOOT: Shoot in the current direction.
   LOOK: Get information about the surroundings.
   STATUS: Get the current status of the robot.

## Server Responses
The server sends back responses indicating the result of each command, such as successful movement, collision with an obstacle, encountering another robot, etc.

## Application Protocol
A specific messaging protocol (Robot Worlds Protocol) is implemented to standardize communication between clients and the server.
## Serialization
Data is serialized and deserialized using JSON for transmission over the network.

## Contributing
Contributions are welcome! Please follow these steps to contribute:

Fork the repository.
Create a new branch (git checkout -b feature-branch).
Make your changes.
Commit your changes (git commit -am 'Add new feature').
Push to the branch (git push origin feature-branch).
Create a new Pull Request.

Thank you for using Robot World! We hope you enjoy navigating and exploring the world with your robot.

'
## Installation
1. Git fork then git clone project
```bash
git clone git@gitlab.wethinkco.de:kloggenberg023/cpt9_robot_worlds.git
```
2. Go to directory of folder
```bash
cd ~/cpt9_robot_worlds
```
3. Compile the project
```bash
javac ~/cpt9_robot_worlds/src/main/java/za/co/wethinkcode/robotworlds/ClientMain.java
javac ~/cpt9_robot_worlds/src/main/java/za/co/wethinkcode/robotworlds/ServerMain.java
```
## Usage
To use the java script:
### Running the installed script
```
java /
```
## Contributors
- Khumo -> CPT_kmogotlhe_023
- Legae Madisha -> CPT_lmadisha_023
- Kyle -> CPT_kloggenberg_023
- Max -> CPT_XBooi_023
- Salizwa -> CPT_skraqa_023
'



