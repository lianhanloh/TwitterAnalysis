/* Copyright 2014 Twitter, Inc. */
package com.twitter.hbc.twitter4j;

import twitter4j.DirectMessage;
import twitter4j.JSONObject;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;

/**
 * This saddens me, but we need a way to get at the JSONObject to Model
 * classes in twitter4j. All of them are package protected, so this was the best
 * we could do.
 */
public class PublicObjectFactory extends JSONImplFactory {

  public PublicObjectFactory(Configuration conf) {
    super(conf);
  }

  public DirectMessage newDirectMessage(JSONObject json) throws TwitterException {
    return new DirectMessageJSONImpl(json);
  }

}
