package rtm4scala.rtm

import rtm4scala.api._
import collection.SortedMap

object Tasks {

	def add(rtm: RtmApi, 
			authToken: String, 
			timeline: String, 
			listId: String, 
			name: String, 
			parse: String) {
				rtm.makeRequest("rtm.tasks.add", 
					SortedMap("auth_token" -> authToken, 
						"timeline" -> timeline, 
						"list_id" -> listId, 
						"name" -> name,
						"parse" -> parse))
			}
}