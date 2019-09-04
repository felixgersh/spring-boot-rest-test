#!/bin/bash

docker container stop pccw-test-smtp
docker container rm pccw-test-smtp
docker container stop pccw-test-app
docker container rm pccw-test-app
docker container stop pccw-test-mysqld
docker container rm pccw-test-mysqld
docker network rm pccw-test-network
rm -rf /var/lib/mysql-docker
echo "Application removed."
