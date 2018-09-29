package com.examples.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;

public class AvroSchemaUtils {
	 private static Map<String, Schema> schemas = new HashMap<String, Schema>();
	 
	    private AvroSchemaUtils(){}
	 
	    public static void addSchema(String name, Schema schema){
	        schemas.put(name, schema);
	    }
	 
	    public static Schema getSchema(String name){
	        return schemas.get(name);
	    }
	 
	    public static Map<String, Schema> getSchemas() {
	        return schemas;
	    }
	 
	    public static String resolveSchema(String sc){
	        String result = sc;
	 
	        for(Map.Entry<String, Schema> entry : schemas.entrySet()){
	            result = replace(result, entry.getKey(), entry.getValue().toString());
	        }
	 
	        return result;
	    }
	 
	    static String replace(String str, String pattern, String replace) {
	        int s = 0;
	        int e = 0;
	        StringBuilder result = new StringBuilder();
	 
	        while ((e = str.indexOf(pattern, s)) >= 0) {
	            result.append(str.substring(s, e));
	            result.append(replace);
	            s = e+pattern.length();
	        }
	 
	        result.append(str.substring(s));
	        return result.toString();
	    }
	 
	    public static Schema parseSchema(String fileName) throws Exception{
	        String completeSchema = resolveSchema(readFile(fileName));
	        Schema schema = new Schema.Parser().parse(completeSchema);
	        String name = schema.getFullName();
	        System.out.println("Schema Name is "+name);
	        schemas.put(name, schema);
	        return schema;
	    }
	 
	    /*
	     * Convert the file content into String
	     */
	    
	    private static String readFile(String fileName) throws IOException {
	    	String filePath="D://advanced-avro//nested-avro-schemas//src//main//resources//schema//";
	    	//"D://advanced-avro//nested-avro-schemas//src//main//resources//schema//product.avsc"
	        BufferedReader br = new BufferedReader(new FileReader(filePath+fileName));
	        try {
	            StringBuilder sb = new StringBuilder();
	            String line = br.readLine();
	 
	            while (line != null) {
	                sb.append(line);
	                line = br.readLine();
	            }
	            return sb.toString();
	        } finally {
	            br.close();
	        }
	    }
	 
	    public static GenericDatumWriter<GenericRecord> getWriter(Schema name){
	        return new GenericDatumWriter<GenericRecord>(name);
	    }
	 
	    public static GenericData.Record getGenericRecord(String name){
	        return new GenericData.Record(schemas.get(name));
	    }
	 
	    public static void main(String[] args){
	        try{
	           /* AvroSchemaUtils.parseSchema("schemas"+File.separator+"product.avsc");
	            AvroSchemaUtils.parseSchema("schemas"+File.separator+"order_details.avsc");*/
	 
	   
	        	AvroSchemaUtils.parseSchema("product.avsc");
	        	AvroSchemaUtils.parseSchema("order_details.avsc");
	        	
	            Schema finalSchema = AvroSchemaUtils.parseSchema("order.avsc");
	            System.out.println(finalSchema.toString(true));
	        }
	        catch(Exception e){
	            System.err.println("Exception thrown in combineSchema "+e);
	        }
	    }

}
