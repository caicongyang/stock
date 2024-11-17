#!/bin/bash

#push to server pc
mvn clean install -Dmaven.test.skip=true

cd target
scp app.jar  root@101.43.6.49:/opt/


