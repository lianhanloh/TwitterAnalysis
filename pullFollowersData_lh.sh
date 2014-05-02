#!/bin/bash

# this script runs the retrieveFriends_lh.jar to retrieve the next set of followers and following
# this will run parallel to the existing run

cd ~/Documents/Penn/Academics/CIS/NETS\ 150/HW5/TweetAnalysis
java -jar retrieveFriends_lh.jar 2>> errors_lh.txt
cur=$(cat count_lh.txt | tail -1)
echo $((cur + 1)) > count_lh.txt
