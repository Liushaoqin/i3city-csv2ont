package com.Util.TDBUtil;

import java.util.List;
/**
 * @ Author     ：YangKai.
 * @ Date       ：Created in 16:44 2018/6/25
 * @ Description：${description}
 * @ Version:     ${version}
 */
public class TDBPersistenceTest {

    static void listModels() {

        //TDB的数据文件夹地址；
        String TDBPath = "\\TDB";
        TDBPersistence tdbPersistence = new TDBPersistence(TDBPath);
        List<String> models = tdbPersistence.listModels();
        if (models == null || models.isEmpty() || models.size() == 0)
            System.out.println("Dataset中不存在非默认model！");
        else {
            for (String model : models) {
                System.out.println("model: " + model);
            }
        }
        tdbPersistence.closeTDB();
    }



    static void loadModel() {

        //TDB的数据文件夹地址；
        String TDBPath = "\\TDB";
        //在Dataset中存放model的名字；
        String modelName = "TDB_agriculture";
        //表示若有同名model，是否需要覆盖；
        Boolean flag = true;
        //rdf三元组文件的路径；
        String rdfPathName = "NT_triplets.nt";
        //建立对象；
        TDBPersistence tdbPersistence = new TDBPersistence(TDBPath);
        //新建model；
        tdbPersistence.loadModel(modelName, rdfPathName, flag);
        //事务完成后必须关闭Dataset；
        tdbPersistence.closeTDB();
    }

    public static void main(String[] args){
        listModels();

    }
}
