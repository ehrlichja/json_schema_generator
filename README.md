# JSON Schema Generator

This project takes JSON documents and generates a schema (in JSON format) for each document. It is not fully compliant with [json-schema.org](json-schema.org), but getting close.

It's useful if you have a large collection of raw data and need to know all the distinct schemas.

Usage:

```
java -jar json_schema-assembly-1.0.jar your_input_file.json
```
