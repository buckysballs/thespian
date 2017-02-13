package twitterutil

import spray.http.HttpRequest
import streams.{Consumer, OAuth, Token}

/**
  * Created by @buckysballs on 2/12/17.
  */
trait OAuthTwitterAuthorization {
  val plz = Config
  val consumer = Consumer(plz.consumerKey, plz.consumerSecretKey)
  val token = Token(plz.accessToken, plz.accessTokenSecret)
  val oAuthCredentials: (HttpRequest) => HttpRequest = OAuth.oAuthAuthorizer(consumer, token)
}
