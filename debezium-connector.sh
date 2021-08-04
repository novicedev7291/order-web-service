#!/usr/bin/env bash

if [ -z "$1" ]
then
	echo "Provide connector json"
fi

curl -H "Accept: application/json" -H "Content-Type: application/json" -X POST http://localhost:8083/connectors -d @$1
