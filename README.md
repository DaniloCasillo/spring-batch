# Spring Batch: polling and on-demand mode

This repository contains an example about Spring Batch execution with 2 jobs. Each job reads data from an Http Endpoint and produce data on a Kafka topic.
The first job has a reader and a writer step, the second jab has also an intermediate processor step. 

# Pre-requisites 
For the correct execution of the application, a Kafka broker must be available and reachable. For this reason, if a remote Kafka is not available, you must use Docker desktop and build the docker-compose file located in the kafka directory. 

# How to run 
At beginning, start the python server. This server export two APIs, used by the spring batch application for getting the data. 
Be sure that Kafka broker and Zookeper is running!
After, run the spring batch application. This operation run a first execution of each spring batch job; then, while the first one is stopped waiting an on-demand request, the second job run in scheduled mode. Anyway, using the APIs implemented in "BatchResource" class, an external user can start another jub. 
