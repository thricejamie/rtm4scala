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

package rtm4scala

import rtm4scala._, rtm._, api._

trait RtmMethods {
	val api:RtmApi
	
	val test = new Test(api:RtmApi)
	val tasks = new Tasks(api:RtmApi)
	val timelines = new Timelines(api:RtmApi)
}

class Rtm(val sharedSecret: String, val apiKey: String) extends RtmMethods {
	lazy val api = new RtmCaller(sharedSecret, apiKey)
}