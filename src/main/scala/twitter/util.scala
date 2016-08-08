package twitter

import akka.actor.ActorRef

/**
  * Created by @buckysballs on 8/6/16.
  */
object util {

  val twitterConfig = new twitter4j.conf.ConfigurationBuilder()
    .setOAuthConsumerKey("3vzdgbbvfaT5ZixKvJdKyQKhd")
    .setOAuthConsumerSecret("kEBNpzi1SHLwQqiA4uMZelXc2yQmgWjOeYIQlZi3WhlT01PRPT")
    .setOAuthAccessToken("357716906-BzesTmOrfyJzFyy0FRZjFc28RzNA1SfFfXd4kGHZ")
    .setOAuthAccessTokenSecret("QyNlRlaxjI5nTMSeJhTZKjrNyuhXpxvwjeNjFvczRvM3z")
    .build

}

case class StartingInfo(director: ActorRef, numWorkers: Int)

case class UpdateInfo(workerId: Int)

case class WorkerInfo(system: ActorRef, id: Int)