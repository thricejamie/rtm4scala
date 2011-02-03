import sbt._

class Rtm4ScalaProject(info: ProjectInfo) extends DefaultProject(info) {
	val dispatchHttp = "net.databinder" %% ("dispatch-http") % "0.7.8"
	val liftCommon = "net.liftweb" % ("lift-common_" + crossScalaVersionString) % "2.2"
}