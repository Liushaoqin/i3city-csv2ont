package com.ontology.datapreparation;

import com.Util.OntUtil.OntoManager;
import com.config.Config;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.OntClass;

import java.util.*;

/**
 * @ Author     ：YangKai.
 * @ Date       ：Created in 9:26 2018/6/19
 * @ Description：${description}
 * @ Version:     ${version}
 */
public class BJOntology extends OntoManager {
    //public BJOntology(){super();}
    BJDBData bjSourceData;
    CorConceptModel coreConceptModel;
    HashMap<String, ArrayList<String>> concept2Property;
    HashMap<String, String> ch2enURI;
    HashMap<String, String> enURI2ch;
    HashSet<String> propertyURI;

    public BJOntology() {
        ch2enURI = new HashMap<>();
        enURI2ch = new HashMap<>();
        propertyURI = new HashSet<>();
    }

    public static void main(String[] args) {
        BJOntology bjo = new BJOntology();
        HashMap<String, ArrayList<String>> conpro = bjo.getConProFromDescription("name", "description");
        for (String con : conpro.keySet()) {
            System.out.print(con + ":  ");
            for (String pro : conpro.get(con)) {
                System.out.print(pro + " | ");
            }
            System.out.println();
        }
        bjo.convertDataForOntology();
        for (OntClass oc : bjo.getConceptList()) {
            System.out.println(oc.getURI() + ": ");
            for (DatatypeProperty dtp : bjo.getPropertyListByConcept(oc)) {
                System.out.println(dtp.getURI() + " | ");
            }
        }
    }

    private String[] getPropertiesFromDes(String description) {
        int startPos = description.indexOf("包括");
        int endPos = description.indexOf("等属性");
        if (endPos < 0)
            endPos = description.indexOf("属性");
        String[] propertiesList = description.substring(startPos + 2, endPos).split("、");
        return propertiesList;
    }

    private HashMap<String, ArrayList<String>> addPropertyByName() {
        HashMap<String, ArrayList<String>> newConcept2Property = new HashMap<>();
        for (String conceptName : this.concept2Property.keySet()) {
            String newConceptName = conceptName.replace("北京市", "");
            if (newConceptName.endsWith("表"))
                newConceptName.replace("表", "");
            ArrayList<String> properties = new ArrayList<>(this.concept2Property.get(conceptName));
            properties.add("所属城市");
//            System.out.println(conceptName+":::"+newConceptName);
            int yearIndex = newConceptName.indexOf("年");
            int stIndex = yearIndex - 1;
            for (; stIndex >= 0 && !Character.isLetter(newConceptName.charAt(stIndex)); stIndex--) ;
//            System.out.println(String.valueOf(stIndex) + " " + String.valueOf(yearIndex));
            if (yearIndex > 0 && stIndex <= yearIndex - 4) {
//                System.out.println(newConceptName.substring(stIndex+1, yearIndex+1));
                newConceptName = new StringBuilder(newConceptName).replace(stIndex + 1, yearIndex + 1, "").toString();
                properties.add("年份");
            }
//            System.out.println(conceptName+":::"+newConceptName);
            newConcept2Property.put(newConceptName, properties);
        }
        return newConcept2Property;
    }

    public HashMap<String, ArrayList<String>> getConProFromDescription(String nameColumnName, String desColumnName) {
        bjSourceData = new BJDBData("bj_gov_data");
        bjSourceData.TableDiffer(new String[]{"description", "name"});
        this.concept2Property = new HashMap<>();
        for (Map<String, Object> dataItem : bjSourceData.getDataResult()) {
            String tableName = String.valueOf(dataItem.get(nameColumnName));
            String description = String.valueOf(dataItem.get(desColumnName));
            ArrayList<String> properties = new ArrayList<>(Arrays.asList(getPropertiesFromDes(description)));
            this.concept2Property.put(tableName, properties);
        }
        this.concept2Property = addPropertyByName();
        return this.concept2Property;
    }

    private String getConceptURI() {
        String newURI = RandomStringUtils.randomAlphabetic(10);
        System.out.println(newURI);
        while (this.enURI2ch.containsKey(newURI)) {
            newURI = RandomStringUtils.randomAlphabetic(10);
        }
        return newURI;
    }

    private String getPropertyURI() {
        String newURI = RandomStringUtils.randomAlphanumeric(Config.enRandomURILength);
        while (this.propertyURI.contains(newURI)) {
            newURI = RandomStringUtils.randomAlphanumeric(Config.enRandomURILength);
        }
        return newURI;
    }

    public void convertDataForOntology() {
        CreatNewOntModel();
        HashMap<String, ArrayList<String>> con2pro = getConProFromDescription("name", "description");
        for (String conceptName : con2pro.keySet()) {
//            String URI = getConceptURI();
//            this.enURI2ch.put(URI, conceptName);
//            this.ch2enURI.put(conceptName, URI);
//            OntClass concept = setConcept(URI);
//            concept.addLabel(conceptName,"ch");
            System.out.print(conceptName + ":  ");
            OntClass concept = setConcept(conceptName);
            for (String propertyValue : con2pro.get(conceptName)) {
//                String proURI = getPropertyURI();
                DatatypeProperty dp = setProperty(concept, propertyValue, "String");
//                dp.addLabel(propertyValue, "ch");
                System.out.print(propertyValue + " | ");
            }
            System.out.println();
        }
    }
}
