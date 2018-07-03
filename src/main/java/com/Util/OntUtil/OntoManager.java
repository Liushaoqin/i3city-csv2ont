package com.Util.OntUtil;

import com.config.Config;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFWriter;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ Author     ：YangKai.
 * @ Date       ：Created in 16:54 2018/6/25
 * @ Description：${description}
 * @ Version:     ${version}
 */
public class OntoManager {
    public OntModel model;

    public OntoManager() {
    }

    public OntModel CreatNewOntModel() {
        this.model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        return this.model;
    }

    //    not finish
//    public OntModel CreatOntWithTDB(String tdbPath){
//        TDBPersistence tdbp = new TDBPersistence(tdbPath);
//        this.model = tdbp.getDefaultModel();
//        return this.model;
//    }
    //not finish
    public OntModel ReadOntByOWLSimple(String OWLFilePath) {
        this.model.read("file:" + OWLFilePath);
        return this.model;
    }

    //not finish
    public OntModel ReadOntByUrl() {
        this.model.read(Config.ontoUrl);
        return this.model;
    }

    public OntModel ReadOntByOWLDocMgr(String owlFilePath, String owlFileName) {
        //读取本体
        OntDocumentManager ontDocMgr = new OntDocumentManager();
        // set ontDocMgr's properties here
        ontDocMgr.addAltEntry(Config.ontoPrefix, "file:" + owlFilePath + owlFileName);
        OntModelSpec ontModelSpec = new OntModelSpec(OntModelSpec.OWL_MEM);
        ontModelSpec.setDocumentManager(ontDocMgr);
        // asserted ontology
        this.model = ModelFactory.createOntologyModel(ontModelSpec);
        this.model.read(Config.ontoPrefix, "RDF/XML");
        // inferred ontology (after reasoning)
        return this.model;
    }

    public void WriteOntByOWLDocMgr(String OWLFilePath, String owlFileName) {
        //输出owl文件到文件系统
        try {
            FileOutputStream fileOS = new FileOutputStream(OWLFilePath + owlFileName);
            RDFWriter rdfWriter = this.model.getWriter("RDF/XML");
            rdfWriter.setProperty("showXMLDeclaration", "true");
            rdfWriter.setProperty("showDoctypeDeclaration", "true");
            rdfWriter.write(this.model, fileOS, null);
            fileOS.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        //用writer就不需要用下面的方法了
        //baseOnt.write(fileOS, "RDF/XML");
    }


    public OntClass setConcept(String ConceptName) {
        OntClass conceptClass = this.model.createClass(Config.ontoPrefix + ConceptName);
        conceptClass.addComment(Config.conceptClass, "en");
        return conceptClass;
    }

    public OntClass getConcept(String ConceptName) {
        return this.model.getOntClass(Config.ontoPrefix + ConceptName);
    }

    public List<OntClass> getConceptList() {
        List<OntClass> conceptClass = new ArrayList<>();
        // 迭代显示模型中的类，在迭代过程中完成各种操作
        for (Iterator<OntClass> i = this.model.listClasses(); i.hasNext(); ) {
            OntClass c = i.next(); // 返回类型强制转换
            if (!c.isAnon() && c.hasComment(Config.conceptClass, "en")) { // 如果不是匿名类，则打印类的名字
//                System.out.print("Class: ");
                // 获取类的URI并输出，在输出时对URI做了简化（将命名空间前缀省略）
//                System.out.println(c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI()));
                conceptClass.add(c);
            }
        }
        return conceptClass;
    }

    public DatatypeProperty setProperty(OntClass concept, String propertyName, String dataType) {
        Config config = new Config();
        DatatypeProperty dtp = concept.getOntModel().createDatatypeProperty(concept.getURI() + "#" + propertyName);
        dtp.addDomain(concept);
        dtp.addRange(config.dataTypeMap.get(dataType));
        return dtp;
    }

    public boolean hasProperty(OntClass concept, String propertyName) {
        return concept.hasProperty(concept.getOntModel().getDatatypeProperty(concept.getURI() + "#" + propertyName));
    }

    public List<DatatypeProperty> getPropertyListByConcept(OntClass concept) {
        List<DatatypeProperty> dpl = new ArrayList<>();
        for (Iterator ipp = concept.listDeclaredProperties(false); ipp.hasNext(); ) {
            OntProperty p = (OntProperty) ipp.next();
            if (p.isDatatypeProperty()) {
                dpl.add(p.convertToDatatypeProperty());
//                System.out.println("DataTypeProperty: " + p.getLocalName());
            }
        }// property ends
        return dpl;
    }

//    public OntClass addPropertyClass(OntClass ConceptClass, String propertyName){
//        OntClass propertyClass = this.model.createClass(ConceptClass.getURI() + '&' + propertyName);
//        propertyClass.addComment(Config.PropertyClass, "en");
//        return propertyClass;
//    }

//    public List<OntClass> getPropertyClassList() {
//        List<OntClass> propertyClass = new ArrayList<>();
//        // 迭代显示模型中的类，在迭代过程中完成各种操作
//        for (Iterator<OntClass> i = this.model.listClasses(); i.hasNext(); ) {
//            OntClass c = i.next(); // 返回类型强制转换
//            if (!c.isAnon() && c.hasComment(Config.PropertyClass, "en")) { // 如果不是匿名类，则打印类的名字
//                String[] fullName = c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI()).split("&");
//                System.out.println("Class: " + fullName[0]);
//                // 获取类的URI并输出，在输出时对URI做了简化（将命名空间前缀省略）
//                System.out.println("Property: " + fullName[1]);
//                System.out.println();
//                propertyClass.add(c);
//            }
//        }
//        return propertyClass;
//    }

    public ObjectProperty addRelation(String propertyName, OntClass ontDomain, OntClass ontRange, String propertyLabel) {
        ObjectProperty op = this.model.createObjectProperty(Config.ontoPrefix + propertyName);
        op.addDomain(ontDomain);
        op.addRange(ontRange);
        op.addLabel(propertyLabel, "en");
        return op;
    }

    public List<ObjectProperty> getRelationListByConcept(OntClass concept) {
        List<ObjectProperty> propertiesList = new ArrayList<>();
        // 迭代显示模型中的类，在迭代过程中完成各种操作
        for (Iterator<OntProperty> i = concept.listDeclaredProperties(); i.hasNext(); ) {
            OntProperty c = i.next(); // 返回类型强制转换
            if (c.isObjectProperty()) { // 如果不是匿名类，则打印类的名字
                System.out.print("Relation Property: ");
                // 获取类的URI并输出，在输出时对URI做了简化（将命名空间前缀省略）
                System.out.print(c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI()));
                System.out.print(" Target Concept: ");
                System.out.println(c.asObjectProperty().getRange().getLocalName());
                propertiesList.add(c.asObjectProperty());
            }
        }
        return propertiesList;
    }
}
