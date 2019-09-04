#!/bin/bash
set -e

mvn package -DskipTests
cp target/testtask.pccw-0.0.1-SNAPSHOT.jar docker

cd docker

docker network create pccw-test-network

mkdir /var/lib/mysql-docker

docker image build -t pccw-test-mysqld -f Dockerfile.mysqld .
docker container run --name pccw-test-mysqld --network pccw-test-network \
  -v /var/lib/mysql-docker:/var/lib/mysql -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=masterkey -e MYSQL_DATABASE=users_pccw_test -d pccw-test-mysqld

wget http://nilhcem.github.com/FakeSMTP/downloads/fakeSMTP-latest.zip
unzip fakeSMTP-latest.zip
rm fakeSMTP-latest.zip
docker image build -t pccw-test-smtp -f Dockerfile.smtp .
rm fakeSMTP-2.0.jar
docker container run --name pccw-test-smtp --network pccw-test-network -p 25:25 -d pccw-test-smtp

docker image build -t pccw-test-app -f Dockerfile.app .
rm testtask.pccw-0.0.1-SNAPSHOT.jar
docker container run --name pccw-test-app --network pccw-test-network -p 8080:8080 -d pccw-test-app

echo "Application started."
