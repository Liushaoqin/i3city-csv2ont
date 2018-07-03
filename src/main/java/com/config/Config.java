package com.config;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.XSD;

import java.util.HashMap;
import java.util.Map;


/**
 * @ Author     ：YangKai.
 * @ Date       ：Created in 17:06 2018/6/25
 * @ Description：${description}
 * @ Version:     ${version}
 */
public class Config {
    public static String ontoUrl = "http://www.semanticweb.org/pku/ontologies/I3City";
    public static String ontoPrefix = ontoUrl + "#";

    public static String conceptClass = "ConceptClass";
    public static String propertyClass = "PropertyClass";

    public static String bjGovData = "src/main/java/com/ontology/out/bj_gov_data/";
    public static String owlPath = "src/main/java/com/ontology/out/OWL/";
    public static HashMap<String,Resource> dataTypeMap = new HashMap<String, Resource>();
    public static int enRandomURILength = 10;

    public Config(){
        dataTypeMap.put("String", XSD.xstring);
    }
}
