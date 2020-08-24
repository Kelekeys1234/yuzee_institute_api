#!/bin/bash

echo 'Starting service'
cp /opt/apps/target/yuzee-app-storage.jar ~/yuzee/yuzee-app-storage.jar 
sudo service yuzee-storage-api restart
