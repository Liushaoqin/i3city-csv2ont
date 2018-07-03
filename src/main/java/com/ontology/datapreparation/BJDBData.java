package com.ontology.datapreparation;

import com.Util.JDBCUtil.util.DBUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author     ：YangKai.
 * @ Date       ：Created in 9:25 2018/6/19
 * @ Description：${description}
 * @ Version:     ${version}
 */
public class BJDBData {

    //    public int id;
//    public String name;
//    public String source;
//    public String updateTime;
//    public String description;
//    public int visitCount;
//    public int fileID;
//    public String file_url;
//    public int channelID;
//    public String url;
    List<Map<String, Object>> QResult;
    List<Map<String, Object>> OtherResult;
    List<Map<String, Object>> DataResult;

    public BJDBData(String tableName) {
        Map<String, Object> whereMap = new HashMap<>();
        QResult = new ArrayList<>();
        try {
            QResult = DBUtil.query(tableName, whereMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        TableDiffer(new String[]{"description"});
    }

    public static void main(String[] args) {
        BJDBData bjdata = new BJDBData("bj_gov_data");
        bjdata.TableDiffer(new String[]{"description", "name"});
        System.out.println("统计表:");
        List<Map<String, Object>> tongji = bjdata.getOtherResult();
        for (Map<String, Object> item : tongji) {
            for (String key : item.keySet()) {
                System.out.print(String.valueOf(item.get(key)) + "  ");
            }
            System.out.println();
        }
        System.out.println("统计表总数：" + tongji.size());
        System.out.println();
        System.out.println("数据表:");
        List<Map<String, Object>> shuju = bjdata.getDataResult();
        for (Map<String, Object> item : shuju) {
            for (String key : item.keySet()) {
                System.out.print(String.valueOf(item.get(key)) + "  ");
            }
            System.out.println();
        }
        System.out.println("数据表总数：" + shuju.size());
    }

    /**
     * @ description: 筛选有价值的本体数据表，针对北京关联的数据列（description, name）
     * @ Param: tableName
     * @ Param: concernCols
     * @ return: java.util.HashMap<java.lang.String   ,   java.util.List>
     * @ throw:
     * @ date: 2018/6/19
     */
    public void TableDiffer(String[] concernCols) {
        OtherResult = new ArrayList<>();
        DataResult = new ArrayList<>();
        // 按照北京市政府数据表为例，进行分辨
        for (Map<String, Object> dataItem : QResult) {
            if (String.valueOf(dataItem.get(concernCols[0])).contains("统计") ||
                    String.valueOf(dataItem.get(concernCols[1])).contains("情况") ||
                    String.valueOf(dataItem.get(concernCols[1])).contains("总值表") ||
                    String.valueOf(dataItem.get(concernCols[1])).contains("月度") ||
                    String.valueOf(dataItem.get(concernCols[1])).contains("年度") ||
                    !String.valueOf(dataItem.get(concernCols[0])).contains("属性")) {
                OtherResult.add(dataItem);
            } else {
                DataResult.add(dataItem);
            }
        }
    }

    public List<Map<String, Object>> getQResult() {
        return QResult;
    }

    public List<Map<String, Object>> getOtherResult() {
        return OtherResult;
    }

    public List<Map<String, Object>> getDataResult() {
        return DataResult;
    }
}
