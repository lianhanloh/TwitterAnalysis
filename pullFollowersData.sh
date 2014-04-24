#!/bin/bash

# this script runs the .jar to retrieve the next set of followers and following

cd ~/Documents/nets150/TweetAnalysis
java -jar tweetAnalysis_v1.jar 2>> errors.txt
cur=$(cat count.txt | tail -1)
echo $((cur + 1)) >> count.txt
