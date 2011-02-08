/**
 * Copyright 2011 Jamie Paulson
 * 
 * See the NOTICE file distributed with this work for additional 
 * information regarding copyright ownership.  This file is licenses
 * to you under the Apache License, Version 2.0 (the "License"); you 
 * may not use this file except in compliance with the License.  You 
 * may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.    
 * 
 */

package rtm4scala.rtm

import rtm4scala.api._
import collection.SortedMap

object Tasks {

	def add(rtm: RtmApi, 
			authToken: String, 
			timeline: String, 
			listId: String, 
			name: String, 
			parse: String): Option[Task] = {
				rtm.makeRequest("rtm.tasks.add", 
					SortedMap("auth_token" -> authToken, 
						"timeline" -> timeline, 
						"list_id" -> listId, 
						"name" -> name,
						"parse" -> parse)) {
					result => println(result);Some(Task((result \\ "list" \\ "taskseries" \\ "task" \ "@id").text,
																							(result \\ "list" \ "@id").text,
																							(result \\ "list" \\ "taskseries" \ "@id").text,
																							(result \\ "list" \\ "taskseries" \ "@name").text,
																							(result \\ "list" \\ "taskseries" \ "@created").text,
																							(result \\ "list" \\ "taskseries" \ "@modified").text,
																							(result \\ "list" \\ "taskseries" \\ "task" \ "@added").text,
																							(result \\ "list" \\ "taskseries" \\ "task" \ "@due").text,
																							(result \\ "list" \\ "taskseries" \\ "task" \ "@has_due_time").text,
																							(result \\ "list" \\ "taskseries" \\ "task" \ "@completed").text,
																							(result \\ "list" \\ "taskseries" \\ "task" \ "@deleted").text,
																							(result \\ "list" \\ "taskseries" \\ "task" \ "@priority").text,
																							(result \\ "list" \\ "taskseries" \\ "task" \ "@postponed").text,
																							(result \\ "list" \\ "taskseries" \\ "task" \ "@estimate").text))
				}
			}			
}

case class Task(id: String, listId: String, taskseriesId: String, name:String, 
	created: String, modified: String, added:String, due:String, has_due_time: String,
	completed: String, deleted: String, priority: String, postponed: String, estimate: String)

