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

private[rtm4scala] class Test(override val rtm:RtmApi) extends ApiCall(rtm) {

	def echo(echoValues: (String, String)*): Option[Map[String,String]] =
		rtm.makeRequest("rtm.test.echo", SortedMap.empty[String,Object] ++ echoValues) {
			result => Some(result.map(x => (x.label, x.text)).toMap)
		}
	
	def login(authToken: String): Option[TestLogin] = 
		rtm.makeRequest("rtm.test.login", SortedMap("auth_token" -> authToken)) {
			result => Some(TestLogin((result \ "username").text))
		}
}

case class TestLogin(val username: String)