#!/bin/bash

mvn clean install -Dmaven.test.skip=true

cd target
scp app.jar  root@159.138.152.92:/opt/


