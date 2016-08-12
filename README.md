# parkinG
Provide realtime reporting on parking garage occupancy in certain part of San Francisco

The program is composed of three layers - divided into three packages:</br>
 SFPARK SERVERS -------  Retriever   ->    Processor   ->    Distributor -------- CLIENTS
  
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
</br>
<b>SiddhiThread</b> - Class representing a single Siddhi execution plan with methods to add queries and streams</br>
<b>SiddhiDefinitionsReader</b> - Tool to read query and stream definitions from SiddhiDefinitions.txt and adds them to inputted SiddhiThread</br>


<h3>DISTRIBUTOR</h3>
-- to be determined --
