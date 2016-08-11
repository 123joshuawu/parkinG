# parkinG
Provide realtime reporting on parking garage occupancy in certain part of San Francisco

The program is composed of three layers - divided into three packages:</br>
 SFPARK SERVERS -------  Retriever   ->    Processor   ->    Distributor -------- CLIENTS
  
<h3>RETRIEVER</h3>
In charge of retrieving data from sfpark servers and provide to processor layer when required.

<b>HttpRetriever</b> - Retrieves data from sfpark servers every minute -> updates RetrieverManager's SfpAvailability object. IF SocketTimeOutException occurs continue trying to connect every minute</br>
<b>RetrieverManager</b> - Manages HttpRetriever and holds latest copy of SfpAvailability object.
                   Sends SfpAvailability object to PROCESSOR layer when requested for</br>

<h3>PROCESSOR</h3>
-- in progress --

<h3>DISTRIBUTOR</h3>
-- in progress --
