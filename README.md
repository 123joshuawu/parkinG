# parkinG
<b>GOAL:</b> Provide realtime reporting on parking garage occupancy and be able to make informed decisions with the information</br>
<b>Current -</b> Using sfpark data for a specified part of San Francisco, CA</br>
<b>TO KEEP IN MIND</b> (just for my poor memory sake)
- Incorporate Google Maps (both UI and API)
- Figure out the rest of the Processor Layer
  - Need higher object to consolidate data? (e.g. ParkingObject) 
  - Two Siddhi Threads or one?             
  - Database w/ Siddhi? or just an object
- Fuzz out details of inter-layer communication (push/pull)
- Figure out Distribution Layer

<h3>OVERVIEW</h3>
The program is logically divided into three layers: Retriever, Processor, and Distributor</br>
 SFPARK SERVERS -------  Retriever   ->    Processor   ->    Distributor -------- CLIENTS
 
In each layer there is a [layer]Manager class (e.g in RETRIEVER layer there is a RetrieverManager) </br>
  - The Manager class is effectively in charge of all the other classes in its layer. It will coordinate intra and inter layer communication, in fact, inter-layer communication should ONLY happen through the Manager classes. </br>
  - The goal is to make all the other classes as generic as possible, so if an implementation for a new data source
  or new queries/streams, only the Manager class will have to be changed
  
<h3>RETRIEVER</h3>
In charge of retrieving data from sfpark servers and provide to processor layer when required.

<b>HttpRetriever</b> - Retrieves data from sfpark servers every minute -> updates RetrieverManager's SfpAvailability object. Even if SocketTimeOutException occurs continue trying to connect every minute</br>
<b>RetrieverManager</b> - Manages HttpRetriever and holds latest copy of SfpAvailability object.
                   Pushes data to PROCESSOR layer when sfp is updated</br>

<b>Sfpark Data</b></br>
Complete documentation for sfpark api can be found here:</br> http://sfpark.org/wp-content/uploads/2013/12/SFpark_API_Dec2013.pdf</br>

Basic Overview:</br>
 - SfpAvailability - First level - Contains multiple Avl, each represents an unique parking garage</br>
    - Avl - Second level - Contains information about parking garage including occupancy</br>
        - Ophrs - Third level - Contains multiple Ops</br>
            - Ops - Fourth level - Represents a time period in which the parking garage is open - can be hours/days</br>


<h3>PROCESSOR</h3>
-- in progress -- </br>
Complex Event Processing done using Siddhi Engine</br>
Siddhi Language Documentation: https://docs.wso2.com/display/CEP410/SiddhiQL+Guide+3.0</br>
Will have two main functions:</br>
1. Perform aggregate functions on data received from RETRIEVER layer and do other computations (tbd) - store results (in database? tbd)</br>
2. Respond to client requests from DISTRIBUTOR layer - retrieve correct data and if necessary, compute new values, according to user request variables</br>

<h3>DISTRIBUTOR</h3>
-- to be determined --
