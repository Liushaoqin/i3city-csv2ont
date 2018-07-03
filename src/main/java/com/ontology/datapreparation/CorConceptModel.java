package com.ontology.datapreparation;

import com.Util.OntUtil.OntoManager;
import org.apache.jena.ontology.OntClass;

/**
 * @ Author     ：YangKai.
 * @ Date       ：Created in 21:52 2018/6/25
 * @ Description：${description}
 * @ Version:     ${version}
 */
public class CorConceptModel extends OntoManager {

    private OntClass CoreConcept;

    private OntClass basicEntity;
    private OntClass creativeEntity;
    private OntClass phisicalEntity;
    private OntClass informationalEntity;
    private OntClass socialEntity;


    private OntClass basicService;
    private OntClass governmentService;
    private OntClass enterpriseService;
    private OntClass citizenEntity;
    private OntClass serviceType;

    private OntClass basicEvent;

    private OntClass basicRole;
    private OntClass eventRole;
    private OntClass cooperationRole;

    private OntClass basicCooperation;
    private OntClass message;
    private OntClass protocol;
    private OntClass process;
    private OntClass constraint;

    private OntClass basicSituation;
    private OntClass time;
    private OntClass geographicalLocation;
    private OntClass informationLocation;
    private OntClass environment;

    private OntClass basicMetric;
    private OntClass keyPerformanceIndicator;
    private OntClass measurement;

    public CorConceptModel(){
        super();

        CreatNewOntModel();
        CreateConcepts();
        CreateRelations();
    }
    public void CreateRelations(){
        this.CoreConcept.addSubClass(this.basicEntity);
        this.CoreConcept.addSubClass(this.basicService);
        this.CoreConcept.addSubClass(this.basicEvent);
        this.CoreConcept.addSubClass(this.basicRole);
        this.CoreConcept.addSubClass(this.basicCooperation);
        this.CoreConcept.addSubClass(this.basicSituation);
        this.CoreConcept.addSubClass(this.basicMetric);

        this.basicEntity.addSubClass(this.creativeEntity);
        this.basicEntity.addSubClass(this.phisicalEntity);
        this.basicEntity.addSubClass(this.informationalEntity);
        this.basicEntity.addSubClass(this.socialEntity);

        this.basicService.addSubClass(this.governmentService);
        this.basicService.addSubClass(this.enterpriseService);
        this.basicService.addSubClass(this.citizenEntity);

        this.basicRole.addSubClass(this.eventRole);
        this.basicRole.addSubClass(this.cooperationRole);

        this.basicCooperation.addSubClass(this.message);
        this.basicCooperation.addSubClass(this.protocol);
        this.basicCooperation.addSubClass(this.process);
        this.basicCooperation.addSubClass(this.constraint);

        this.basicSituation.addSubClass(this.time);
        this.basicSituation.addSubClass(this.geographicalLocation);
        this.basicSituation.addSubClass(this.informationLocation);
        this.basicSituation.addSubClass(this.environment);

        this.basicMetric.addSubClass(this.keyPerformanceIndicator);
        this.basicMetric.addSubClass(this.measurement);

        addRelation("manage", this.socialEntity, this.basicService, "管理");
        addRelation("form", this.serviceType, this.basicService, "组成");
        addRelation("form", this.message, this.basicCooperation, "组成");
        addRelation("form", this.protocol, this.basicCooperation, "组成");
        addRelation("form", this.process, this.basicCooperation, "组成");
        addRelation("form", this.constraint, this.basicCooperation, "组成");
        addRelation("describe", this.basicSituation, this.basicEntity, "描述");
        addRelation("describe", this.basicSituation, this.basicEvent, "描述");
        addRelation("undertake", this.basicEntity, this.basicRole, "承担");
        addRelation("takePartIn", this.eventRole, this.basicEvent, "参与");
        addRelation("takePartIn", this.cooperationRole, this.basicCooperation, "参与");
        addRelation("achieve", this.basicCooperation, this.basicService,"实现");

    }

    private void CreateConcepts(){
        CreatNewOntModel();
        this.CoreConcept = setConcept("CoreConcept");
        this.CoreConcept.addLabel("核心概念", "ch");

        this.basicEntity = setConcept("basicEntity");
        this.basicEntity.addLabel("实体基本类", "ch");
        this.creativeEntity = setConcept("creativeEntity");
        this.creativeEntity.addLabel("创意实体", "ch");
        this.phisicalEntity = setConcept("phisicalEntity");
        this.phisicalEntity.addLabel("物理实体","ch");
        this.informationalEntity = setConcept("informationalEntity");
        this.informationalEntity.addLabel("信息实体","ch");
        this.socialEntity = setConcept("socialEntity");
        this.socialEntity.addLabel("社会实体","ch");

        this.basicService = setConcept("basicService");
        this.basicService.addLabel("服务基本类","ch");
        this.governmentService = setConcept("governmentService");
        this.governmentService.addLabel("政府管理","ch");
        this.enterpriseService = setConcept("enterpriseService");
        this.enterpriseService.addLabel("企业运营","ch");
        this.citizenEntity = setConcept("citizenEntity");
        this.citizenEntity.addLabel("市民生活","ch");
        this.serviceType = setConcept("serviceType");
        this.serviceType.addLabel("服务类型","ch");

        this.basicEvent = setConcept("basicEvent");
        this.basicEvent.addLabel("事件基本类","ch");

        this.basicRole = setConcept("basicRole");
        this.basicRole.addLabel("角色基本类","ch");
        this.eventRole = setConcept("eventRole");
        this.eventRole.addLabel("事件角色","ch");
        this.cooperationRole = setConcept("cooperationRole");
        this.cooperationRole.addLabel("协作角色","ch");

        this.basicCooperation = setConcept("basicCooperation");
        this.basicCooperation.addLabel("协作基本类","ch");
        this.message = setConcept("message");
        this.message.addLabel("消息","ch");
        this.protocol = setConcept("protocol");
        this.protocol.addLabel("协议","ch");
        this.process = setConcept("process");
        this.process.addLabel("流程","ch");
        this.constraint = setConcept("constraint");
        this.constraint.addLabel("约束","ch");

        this.basicSituation = setConcept("basicSituation");
        this.basicSituation.addLabel("情境基本类","ch");
        this.time = setConcept("time");
        this.time.addLabel("时间","ch");
        this.geographicalLocation = setConcept("geographicalLocation");
        this.geographicalLocation.addLabel("地理位置","ch");
        this.informationLocation = setConcept("informationLocation");
        this.informationLocation.addLabel("信息空间位置","ch");
        this.environment = setConcept("environment");
        this.environment.addLabel("环境","ch");

        this.basicMetric = setConcept("basicMetric");
        this.basicMetric.addLabel("度量基本类","ch");
        this.keyPerformanceIndicator = setConcept("keyPerformanceIndicator");
        this.keyPerformanceIndicator.addLabel("关键绩效指标","ch");
        this.measurement = setConcept("measurement");
        this.measurement.addLabel("计量","ch");
    }

}
