#!/bin/bash

TESTS="/home/gsd/gsql/tests"
CONFIGS="/home/gsd/gsql/configs"

LIBRARY_PATH="/home/gsd/gsql/jRAPL/"

sudo modprobe msr

sudo mvn exec:java -Dexec.mainClass="com.app.gsql.Main" -Dexec.args="$CONFIGS/mysql.json $TESTS/mysql_dashboard 10 15" -Djava.library.path=$LIBRARY_PATH -Dexec.cleanupDaemonThreads=false

sudo mvn exec:java -Dexec.mainClass="com.app.gsql.Main" -Dexec.args="$CONFIGS/neo4j.json $TESTS/neo4j_dashboard 10 15" -Djava.library.path=$LIBRARY_PATH -Dexec.cleanupDaemonThreads=false

echo "Complete" | /usr/sbin/ssmtp hmtlguimaraes@gmail.com
