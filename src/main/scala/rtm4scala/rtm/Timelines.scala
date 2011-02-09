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
import net.liftweb.common._

private[rtm4scala] class Timelines(override val rtm:RtmApi) extends ApiCall(rtm) {

	def create(authToken: String): Box[String] =
		rtm.makeRequest("rtm.timelines.create", SortedMap("auth_token" -> authToken)) {
			result => Full((result \\ "timeline").text)
		}
}