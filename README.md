# parkinG
Provide realtime reporting on parking garage occupancy in certain part of San Francisco

The program is composed of three layers - divided into three packages:</br>
 SFPARK SERVERS -------  Retriever   ->    Processor   ->    Distributor -------- CLIENTS
  
<h3>RETRIEVER</h3>
In charge of retrieving data from sfpark servers and provide to processor layer when required.

<b>HttpRetriever</b> - Retrieves data from sfpark servers every minute -> updates RetrieverManager's SfpAvailability object. IF SocketTimeOutException occurs continue trying to connect every minute</br>
<b>RetrieverManager</b> - Manages HttpRetriever and holds latest copy of SfpAvailability object.
                   Sends SfpAvailability object to PROCESSOR layer when requested for</br>

<b>Sfpark Data</b></br>
Complete documentation for sfpark api can be found here:</br> http://sfpark.org/wp-content/uploads/2013/12/SFpark_API_Dec2013.pdf</br>

Basic Overview:</br>
 - SfpAvailability - First level - Contains multiple Avl, each represents an unique parking garage</br>
    - Avl - Second level - Contains information about parking garage including occupancy</br>
        - Ophrs - Third level - Contains multiple Ops</br>
            - Ops - Fourth level - Represents a time period in which the parking garage is open - can be hours/days</br>


<h3>PROCESSOR</h3>
-- in progress --

<h3>DISTRIBUTOR</h3>
-- in progress --
