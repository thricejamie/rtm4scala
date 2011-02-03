package rtm4scala.rtm

import rtm4scala.api._
import collection.SortedMap

object Test {

	def echo(rtm: RtmApi, echoValues: (String, String)*) =
		rtm.makeRequest("rtm.test.echo", SortedMap.empty[String,Object] ++ echoValues)
	
	def login(rtm: RtmApi, authToken: String) = 
		rtm.makeRequest("rtm.test.login", SortedMap("auth_token" -> authToken))
}