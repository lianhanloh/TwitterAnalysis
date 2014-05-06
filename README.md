TwitterAnalysis
=============

This program will retrieve Twitter user data (followers and following IDs) and analyze the data

Usage Instructions
====================

- Twitter developer [authentication] (https://apps.twitter.com/app/new)

Design choices and Documentation
====================
Twitter gives us a maximum of 5000 unique IDs each time we poll a particular user for his/her followers, so we face a limitation in that we will only add at most 5000 followers edges each time we visit a user. We believe this should not affect the result as this probably only is relevant for celebrities which have a huge number of followers. For these cases, we might exclude the edges for a portion of a celebrity's followees, but there is a high probability that we will visit him/her in our traversal, and add that edge between him/her and the celebrity in any case. Thus, the graph we construct should not be affected.

Referenced libraries
====================

- [Apache HTTP Components] (http://hc.apache.org/index.html)
- [Twitter4j] (http://twitter4j.org/en/index.html#download)
