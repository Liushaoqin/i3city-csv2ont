package com.Util.OntUtil;

import com.config.Config;
import com.ontology.datapreparation.CorConceptModel;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

/**
 * @ Author     ：YangKai.
 * @ Date       ：Created in 13:54 2018/6/26
 * @ Description：${description}
 * @ Version:     ${version}
 */
public class OntoManagerTest {
    public static void main(String[] args){
        CorConceptModel core = new CorConceptModel();

        //test getconcept
        System.out.println(core.getConcept("basicEntity").getLabel("ch"));

        //test concept list
        for(OntClass oc : core.getConceptList()){
            System.out.println(oc.getLabel("ch"));
        }

        //test relation
        core.getRelationListByConcept(core.getConcept("socialEntity"));

        //test OWL
        //core.WriteOntByOWLDocMgr(Config.owlPath, "coreConceptTest.owl");
        OntoManager om = new OntoManager();
        System.out.println(om);
        om.ReadOntByOWLDocMgr(Config.owlPath, "coreConceptTest.owl");

    }
}
