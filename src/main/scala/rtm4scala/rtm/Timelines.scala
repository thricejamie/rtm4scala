package rtm4scala.rtm

import rtm4scala.api._
import collection.SortedMap

object Timelines {

	def create(rtm:RtmApi, authToken: String) =
		rtm.makeRequest("rtm.timelines.create", SortedMap("auth_token" -> authToken))
}