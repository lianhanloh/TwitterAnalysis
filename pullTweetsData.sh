#!/bin/bash

# this script runs the .jar to retrieve the next set tweets for list of users

cd ~/Desktop/TweetAnalysis
java -jar retrieveTweets.jar 2>> errors.txt
cur=$(cat count.txt | tail -1)
echo $((cur + 1)) > count.txt
