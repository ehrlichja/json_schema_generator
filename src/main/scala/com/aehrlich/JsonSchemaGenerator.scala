package com.aehrlich

import com.google.gson._

import scala.collection.JavaConversions


object JsonSchemaGenerator {

  def findObjectValue(o:JsonObject): JsonObject = {

    val s = new JsonObject()

    val rootSet = JavaConversions.asScalaSet(o.entrySet())

    rootSet.foreach(i => {

      val key = i.getKey

      val value:JsonElement = i.getValue match {
        case e:JsonPrimitive  => new JsonPrimitive(findPrimitiveValue(e))
        case e:JsonObject     => findObjectValue(e)
        case e:JsonArray      => new JsonPrimitive("array")
        case _                => new JsonPrimitive("unknown element")
      }

      s.add(key, value)
    })

    s

  }
  
  def findPrimitiveValue(p:JsonPrimitive): String = {
    if (p.isBoolean)  return "boolean"
    if (p.isNumber)   return "number"
    if (p.isString)   return "string"
    "unknown primitive"
  }

  def makeSchema(s:String):String = {
    val o = new JsonParser().parse(s).getAsJsonObject
    findObjectValue(o).toString
  }
  
  
  def main(args: Array[String]): Unit = {

  }

}
