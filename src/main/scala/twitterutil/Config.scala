package twitterutil

import com.typesafe.config.ConfigFactory
import spray.http.HttpRequest
import streams.{Consumer, OAuth, Token}
import twitter4j.{TwitterFactory, TwitterStreamFactory}
import twitter4j.conf.{Configuration, ConfigurationBuilder}

/**
  * Created by @buckysballs on 2/6/17.
  */
object Config {
  val config = ConfigFactory.load()
  val consumerKey: String = config.getString("consumerKey")
  val consumerSecretKey: String = config.getString("consumerSecretKey")
  val accessToken: String = config.getString("accessToken")
  val accessTokenSecret: String = config.getString("accessTokenSecret")
  
  val twitterSpoutConfig: Configuration = new ConfigurationBuilder()
    .setDebugEnabled(true)
    .setOAuthConsumerKey(Config.consumerKey)
    .setOAuthConsumerSecret(Config.consumerSecretKey)
    .setOAuthAccessToken(Config.accessToken)
    .setOAuthAccessTokenSecret(Config.accessTokenSecret)
    .setUseSSL(true)
    .build()

  def twitterSpout: TwitterStreamFactory = new TwitterStreamFactory(twitterSpoutConfig)

  val consumer = Consumer(consumerKey, consumerSecretKey)
  val token = Token(accessToken, accessTokenSecret)

  val authorize: (HttpRequest) => HttpRequest = OAuth.oAuthAuthorizer(consumer, token)

}
