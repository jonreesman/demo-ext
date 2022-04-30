#!/usr/bin/env bash

# kafka-demo topics
docker-compose exec kafka kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 10 --topic secondary-public
