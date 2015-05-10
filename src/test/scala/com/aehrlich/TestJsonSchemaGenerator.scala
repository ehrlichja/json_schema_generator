package com.aehrlich

import JsonSchemaGenerator.makeSchema
import org.junit.Test
import org.junit.Assert._

class TestJsonSchemaGenerator {

  @Test
  def testSimple() = {

    val input = "{\"id\": 1,\"name\": \"A green door\",\"price\": 12.50,\"tags\": [\"home\", \"green\"], \"data\": {\"key1\":\"val1\"}}"
    val expectedOutput = "{\"title\":\"A json schema\",\"type\":\"object\",\"properties\":{\"id\":\"integer\",\"name\":\"string\",\"price\":\"decimal\",\"tags\":\"array\",\"data\":{\"key1\":\"string\"}}}"
    val actualOutput = makeSchema(input).toString
    assertEquals(expectedOutput, actualOutput)
  }

  @Test
  def testComplex() = {

    val input = "{\"id\":8329834,\"long_id\":\"This is a bunch of information\",\"data\":{\"basic_attribute\":\"thing\",\"tags\":[{\"name\":\"tag_1\",\"id\":123},{\"name\":\"tag_2\",\"id\":456}],\"messy_array\":[0,\"what\",3,0.2,\"mixed_types\"],\"decimals\":[23.2,234.5,33.4],\"no_reason_array\":[0],\"sub_objects\":{\"obj1\":{\"key1\":\"val1\"},\"obj2\":{\"key2\":{\"key3\":\"val3\"}},\"obj3\":{\"key3\":47}}}}"
    val expectedOutput = "{\"title\":\"A json schema\",\"type\":\"object\",\"properties\":{\"id\":\"integer\",\"long_id\":\"string\",\"data\":{\"basic_attribute\":\"string\",\"tags\":\"array\",\"messy_array\":\"array\",\"decimals\":\"array\",\"no_reason_array\":\"array\",\"sub_objects\":{\"obj1\":{\"key1\":\"string\"},\"obj2\":{\"key2\":{\"key3\":\"string\"}},\"obj3\":{\"key3\":\"integer\"}}}}}"
    val actualOutput = makeSchema(input).toString
    assertEquals(expectedOutput, actualOutput)

  }

}
