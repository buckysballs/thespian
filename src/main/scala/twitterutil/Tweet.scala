package twitterutil

/**
  * Created by @buckysballs on 2/12/17.
  */
case class Tweet(id: String, user: User, text: String, place: Option[Place], json: String)

case class User(id: String, lang: String, followersCount: Int)

case class Place(country: String, name: String) {
  override lazy val toString = s"$name, $country"
}
