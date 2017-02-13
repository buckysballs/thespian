package twitterutil

import akka.actor.ActorRef
import akka.io.IO
import spray.can.Http
import spray.client.pipelining.sendTo
import spray.http._
import streams.{HTTPSource, ChunkBuilder}
import spray.client.pipelining._

/**
  * Created by @buckysballs on 2/12/17.
  */

/**
  *
  * @param params
  */
class TwitterSource(val params: String) extends HTTPSource[Tweet] with OAuthTwitterAuthorization {
  private val tweetBuilder = new TweetBuilder()

  val uri: Uri = TwitterSource.twitterUri
  val io: ActorRef = IO(Http)(context.system)
  val request: HttpRequest = TwitterSource.formRequest(params)(oAuthCredentials)
  sendTo(io).withResponsesReceivedBy(self)(request)

  def receive: Receive = {
    case ChunkedResponseStart(_) => tweetBuilder.reset()
    case MessageChunk(body, _) =>
      val tweets = tweetBuilder.toTweets(body)
      tweets foreach { tweet =>
        log.info(tweet.toString)
        onNext(tweet)
      }
  }
}

object TwitterSource {
  val twitterUri: Uri = Uri("https://stream.twitter.com/1.1/statuses/filter.json")

  /**
    *
    * @param params
    * @param oAuth
    * @return
    */
  def formRequest(params: String)(oAuth: (HttpRequest) => HttpRequest): HttpRequest = {
    val body: HttpEntity = HttpEntity(ContentType(MediaTypes.`application/x-www-form-urlencoded`), s"track=$params")
    HttpRequest(HttpMethods.POST, uri = twitterUri , entity = body) ~> oAuth
  }
}

/**
  *
  */
class TweetBuilder extends ChunkBuilder with TweetMarshaller {
  /**
    *
    * @param body
    * @return
    */
  def toTweets(body: HttpData): Iterator[Tweet] = {
    val bodyStr = body.asString(HttpCharsets.`UTF-8`)
    append(bodyStr)
    toIterator.flatMap { tweetString => TweetUnmarshaller(tweetString, "").right.toOption }
  }
}
