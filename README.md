# nested-avro-schemas
In Apache Avro we can not re-use the sub schemas to create another schema.
In some application we may need this functinality. This could be achived using simple java trick.
For more details on this, please refer # http://www.treselle.com/blog/advanced-avro-schema-design-reuse/

We can use avro tool to parse avro schema into binary avro format and binary to json format.
For the examples in this guide, download avro-1.8.2.jar and avro-tools-1.8.2.jar. The Avro Java implementation also depends on the Jackson JSON library. From the Jackson download page, download the core-asl and mapper-asl jars. Add avro-1.8.2.jar and the Jackson jars to your project's classpath (avro-tools will be used for code generation).

Here are command to do the same:

java -jar avro-tools-1.8.2.jar fromjson --schema-file ./order.avsc ./order.json > ./order.avro

java -jar avro-tools-1.8.2.jar tojson ./order.avro > ./order_from_avro.json

