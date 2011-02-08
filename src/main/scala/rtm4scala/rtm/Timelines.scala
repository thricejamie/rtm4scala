package rtm4scala.rtm

import rtm4scala.api._
import collection.SortedMap

object Timelines {

	def create(rtm:RtmApi, authToken: String): Option[Int] =
		rtm.makeRequest("rtm.timelines.create", SortedMap("auth_token" -> authToken)) {
			result => Some((result \\ "timeline").text.toInt)
		}
}