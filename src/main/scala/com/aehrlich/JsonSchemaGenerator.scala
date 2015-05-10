package com.aehrlich

import com.google.gson._

import scala.collection.JavaConversions
import scala.io.Source


object JsonSchemaGenerator {

  /**
   * Basic types of JsonElement: primitive, object, array.
   * @param o Raw data input
   * @return Piece of schema
   */
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

  /**
   * Basic types of JsonPrimitive: boolean, number, string.
   * @param p Raw primitive
   * @return String describing data type
   */
  def findPrimitiveValue(p:JsonPrimitive): String = {
    if (p.isBoolean)  return "boolean"
    if (p.isNumber)   if (p.getAsString.contains(".")) return "decimal" else return "integer"
    if (p.isString)   return "string"
    "unknown primitive"
  }

  /**
   * Entry point for schema generation
   * @param s Root of the raw data
   * @return A complete schema (also as JSON)
   */
  def makeSchema(s:String):JsonObject = {
    val o = new JsonParser().parse(s).getAsJsonObject

    val schema = new JsonObject()
    schema.add("title", new JsonPrimitive("A json schema"))
    schema.add("type", new JsonPrimitive("object"))
    schema.add("properties", findObjectValue(o))
    schema
  }
  
  
  def main(args: Array[String]): Unit = {

    require(args.length == 1, "Usage: JsonSchemaGenerator <input json file>")

    val filename = args(0)

    val file = Source.fromFile(filename)

    val inputJson = file.getLines().toList

    val results = inputJson.map(line => makeSchema(line).toString)

    results.foreach(println)

  }

}
