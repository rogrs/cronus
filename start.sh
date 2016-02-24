#!/bin/bash

shome=`dirname $0`
echo "-jar $shome/target/autobot-0.0.1-SNAPSHOT.war"
java -jar "$shome/target/autobot-0.0.1-SNAPSHOT.war" &
