#!/bin/bash

echo 'Starting service'
cp /opt/apps/target/yuzee-institute.jar ~/yuzee/yuzee-institute.jar
sudo service yuzee-institute  restart
