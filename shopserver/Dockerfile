#FROM jamesdbloom/docker-java8-maven:latest
FROM openjdk:8

# pull base image.
#FROM java:8

# maintainer details
#MAINTAINER James Bloom "jamesdbloom@gmail.com"

# update packages and install maven
RUN  \
  export DEBIAN_FRONTEND=noninteractive && \
  sed -i 's/# \(.*multiverse$\)/\1/g' /etc/apt/sources.list && \
  apt-get update && \
  apt-get -y upgrade && \
  apt-get install -y vim wget curl git maven

# attach volumes
VOLUME /volume/git

# create working directory
RUN mkdir -p /local/git
WORKDIR /local/git

# run terminal
CMD ["/bin/bash"]

COPY . /data/shop-server
WORKDIR /data/shop-server

RUN mvn package
RUN java -jar target/shop-server-1.0-SNAPSHOT.jar db migrate shop-app-config.yml
CMD java -jar target/shop-server-1.0-SNAPSHOT.jar server shop-app-config.yml
EXPOSE 8080