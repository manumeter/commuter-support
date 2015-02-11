# Traffic light for commuters

## Project
This application was written as a project thesis at ZHAW (Zurich University of Applied Sciences). The name of that thesis is "Pendlersupport".

## Intention
It should help commuters to catch their best connection avoiding long idle times.

## Description
Users store their commute route and all relevant time offsets. The application then always displays the time of the next possible connection including a status:

 - *Unknown*: No connection found (yet) due to several possible reasons (no internet connection, impossible route, no configuration data stored, etc.).
 - *Green*: There is a connection soon, the commuter has enough time but does not need to wait more than specified.
 - *Orange*: There is a connection soon, but the commuter needs to hurry to catch it.
 - *Red*: The last connection is not reachable anymore and the idle time for the next connection is too much.
 
## Implementation
The whole project is written in Java 7. We've used OpenData (http://transport.opendata.ch/) as source for the connection data. But the application design should make it easy to add/use other sources. As user interface (UI) we've implemented a graphical UI with Swing and an interactive console UI.

## Start parameters
Start the application with the following command to get the graphical user interface:

  `java -jar Pendlersupport.jar`

To start the console user interface, type:

  `java -jar Pendlersupport.jar --console`

For logging, the following options are available (log goes to *STDERR*):

  `java -jar Pendlersupport.jar [--console] --log [debug|warning|error]`
