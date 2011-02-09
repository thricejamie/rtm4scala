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

package rtm4scala.api

import dispatch.{Logger => DLogger, _}
import collection.SortedMap
import net.liftweb.common._
import org.apache.commons.codec.digest.DigestUtils

class RtmApi(val apiKey: String, val apiSecret: String) extends Logger {
	val endpoint = :/("api.rememberthemilk.com") / "services" / "rest"
	
	val http = new Http
	
	val params = new {
    def apply(method: String) = SortedMap("api_key" -> apiKey, "method" -> method)
  }
	
	def makeRequest[T](method: String, values: SortedMap[String, Object])(block: Seq[xml.Elem] => Box[T]): Box[T] =
		get(sign(params(method) ++ values.filter({
				element => 
					val(key, value) = element
					value != null
			})))(block)
	
	def get[T](query: SortedMap[String,Any])(block: Seq[xml.Elem] => Box[T]): Box[T] =
		http(endpoint <<? query <> {rsp => handleResponse(rsp)(block)})
	
	def handleResponse[T](rsp: xml.Elem)(block: Seq[xml.Elem] => Box[T]): Box[T] = rsp match {
    case rsp if (rsp \ "@stat").text == "ok" =>
      block(rsp.child.collect{case x: xml.Elem => x})
    case rsp if (rsp \ "@stat").text == "fail" =>
      Failure("Remember the Milk API Error "+(rsp \ "err" \ "@code").text+": "+(rsp \ "err" \ "@msg").text)
    case rsp =>
      Failure("Remember the Milk API Unknown Error: "+rsp)
  }
	
	def sign(query: SortedMap[String,Any]) =
    query + ("api_sig" -> DigestUtils.md5Hex(apiSecret+query.map{case (k,v) => k+v}.mkString))
}