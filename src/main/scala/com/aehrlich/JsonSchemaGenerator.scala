package com.aehrlich

import com.google.gson._

import scala.collection.JavaConversions
import scala.io.Source


object JsonSchemaGenerator {

  def findObjectValue(o:JsonObject): JsonObject = {

    val schema = new JsonObject()

    val rootSet = JavaConversions.asScalaSet(o.entrySet())

    rootSet.foreach(i => {

      val key = i.getKey

      val value:JsonElement = i.getValue match {
        case e:JsonPrimitive  => new JsonPrimitive(findPrimitiveValue(e))
        case e:JsonObject     => findObjectValue(e)
        case e:JsonArray      => new JsonPrimitive("array")
        case _                => new JsonPrimitive("unknown element")
      }

      schema.add(key, value)
    })

    schema

  }
  
  def findPrimitiveValue(p:JsonPrimitive): String = {
    if (p.isBoolean)  return "boolean"
    if (p.isNumber)   return "number"
    if (p.isString)   return "string"
    "unknown primitive"
  }

  def makeSchema(s:String):String = {
    val o = new JsonParser().parse(s).getAsJsonObject

    val schema = new JsonObject()
    schema.add("title", new JsonPrimitive("A json schema"))
    schema.add("type", new JsonPrimitive("object"))
    schema.add("properties", findObjectValue(o))
    schema.toString
  }
  
  
  def main(args: Array[String]): Unit = {

    require(args.length == 1, "Usage: JsonSchemaGenerator <input json file>")

    val filename = args(0)

    val file = Source.fromFile(filename)

    val inputJson = file.getLines().toList

    val results = inputJson.map(line => makeSchema(line))

    results.foreach(println)

  }

}
