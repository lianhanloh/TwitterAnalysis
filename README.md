TweetAnalysis
=============

This program will retrieve public tweets and analyze the data


Usage Instructions
====================

- Twitter developer [authentication] (https://apps.twitter.com/app/new)

Design choices and Documentation
====================
Twitter gives us a maximum of 5000 unique IDs each time we poll a particular user for his/her followers, so we face a limitation in that we will only add at most 5000 followers, "followees" edges each time we visit a user. We believe this should not affect the result as this probably only is relevant for celebrities which have a huge number of "followees". For these cases, we might exclude the edges for a portion of a celebrity's followees, but there is a high probability that we will visit him/her in our traversal, and add that edge between him/her and the celebrity in any case. Thus, the graph we construct should not be affected.

For edgeList.txt, each edge is populated as follows: "A B", where A follows B

Referenced libraries
====================

- Guava, Google Core Library
guava-16.0.1.jar
https://code.google.com/p/guava-libraries/

- Apache HTTP Components
httpclient-4.3.3.jar
httpcore-4.3.2.jar
http://hc.apache.org/index.html

- JOAuth (to authenticate HTTP requests using OAuth)
joauth-6.0.2.jar
http://mvnrepository.com/artifact/com.twitter/joauth

- FindBugs-jsr305
jsr305-1.3.9.jar
http://www.findjar.com/jar/com/google/code/findbugs/jsr305/1.3.9/jsr305-1.3.9.jar.html

- Simple Logging Facade for Java
slf4j-api-1.7.7.jar
http://www.slf4j.org/download.html

- JUnit Testing

- Twitter4j
twitter4j-async-4.0.1.jar
twitter4j-core-4.0.1.jar
twitter4j-examples-4.0.1.jar
twitter4j-media-support-4.0.1.jar
twitter4j-stream-4.0.1.jar
http://twitter4j.org/en/index.html#download

- Mockito
mockito-all-1.9.5.jar
https://code.google.com/p/mockito/downloads/list

