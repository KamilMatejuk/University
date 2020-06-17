#!/bin/bash

# Kamil Matejuk

url=$1
time=$2

while [ true ]; do
	if [ -f new.html ]; then
		mv new.html old.html
	fi
	curl $url -s > new.html
	if [ -f old.html ]; then
		difference="$(diff new.html old.html)"
	fi
	if [ "0" != "${difference}" ];
	then
		echo "Site ${url:8} has changed"
	fi
	sleep $time
done