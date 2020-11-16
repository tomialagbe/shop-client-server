#!/usr/bin/env bash

mvn package
docker build .
docker run -p 8080:8080