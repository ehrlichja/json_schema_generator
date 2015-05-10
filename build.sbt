name := "json_schema"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies += "com.google.code.gson" % "gson" % "2.3.1"
libraryDependencies += "junit" % "junit" % "4.12"

mainClass in Compile := Some("com.aehrlich.JsonSchemaGenerator")