# parkinG
Provide realtime reporting on parking garage occupancy in certain part of San Francisco

The program is composed of three layers - divided into three packages:
  SFPARK SERVERS -------  Retriever   ->    Processor   ->    Distributor -------- CLIENTS
                              |_ _ _ _ _ _ _ parkinG _ _ _ _ _ _ _ _|
  
<b>RETRIEVER</b>
In charge of retrieving data from sfpark servers and provide to processor layer when required.

HttpRetriever - Retrieves data from sfpark servers every minute -> updates RetrieverManager's SfpAvailability object
                IF SocketTimeOutException occurs continue trying to connect every minute
RetrieverManager - Manages HttpRetriever and holds latest copy of SfpAvailability object
                   Sends SfpAvailability object to PROCESSOR layer when requested for

<b>PROCESSOR</b>
-- in progress --

<b>DISTRIBUTOR</b>
-- in progress --
