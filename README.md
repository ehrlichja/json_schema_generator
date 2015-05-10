# JSON Schema Generator

This project takes JSON documents and generates a schema (in JSON format) for each document. It is not fully compliant with [json-schema.org](json-schema.org), but getting close.

It's useful if you have a large collection of raw data and need to know all the distinct schemas.

Usage:

```
java -jar json_schema-assembly-1.0.jar your_input_file.json
```

Example schema:

Input:
```
{
  "id": 8329834,
  "long_id": "This is a bunch of information",
  "data": {
    "basic_attribute": "thing",
    "tags": [
      {
        "name": "tag_1",
        "id": 123
      },
      {
        "name": "tag_2",
        "id": 456
      }
    ],
    "messy_array": [
      0,
      "what",
      3,
      0.2,
      "mixed_types"
    ],
    "decimals": [
      23.2,
      234.5,
      33.4
    ],
    "no_reason_array": [
      0
    ],
    "sub_objects": {
      "obj1": {
        "key1": "val1"
      },
      "obj2": {
        "key2": {
          "key3": "val3"
        }
      },
      "obj3": {
        "key3": 47
      }
    }
  }
}
```

Output:

```
{
  "title": "A json schema",
  "type": "object",
  "properties": {
    "id": "number",
    "long_id": "string",
    "data": {
      "basic_attribute": "string",
      "tags": "array",
      "messy_array": "array",
      "decimals": "array",
      "no_reason_array": "array",
      "sub_objects": {
        "obj1": {
          "key1": "string"
        },
        "obj2": {
          "key2": {
            "key3": "string"
          }
        },
        "obj3": {
          "key3": "number"
        }
      }
    }
  }
}
```