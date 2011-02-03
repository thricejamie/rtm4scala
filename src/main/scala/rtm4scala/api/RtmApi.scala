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
	
	def makeRequest(method: String, values: SortedMap[String, Object]) =//values: (String, Object)*) =
		get(sign(params(method) ++ values.filter({
				element => 
					val(key, value) = element
					value != null
			}))) {
			result =>
				Full(result.map(x => (x.label, x.text)).toMap)
		}
	
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