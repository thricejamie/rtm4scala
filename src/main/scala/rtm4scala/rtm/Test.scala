package rtm4scala.rtm

import rtm4scala.api._
import collection.SortedMap

object Test {

	def echo(rtm: RtmApi, echoValues: (String, String)*): Option[Map[String,String]] =
		rtm.makeRequest("rtm.test.echo", SortedMap.empty[String,Object] ++ echoValues) {
			result => Some(result.map(x => (x.label, x.text)).toMap)
		}
	
	def login(rtm: RtmApi, authToken: String): Option[TestLogin] = 
		rtm.makeRequest("rtm.test.login", SortedMap("auth_token" -> authToken)) {
			result => Some(TestLogin((result \ "username").text))
		}
}

case class TestLogin(val username: String)