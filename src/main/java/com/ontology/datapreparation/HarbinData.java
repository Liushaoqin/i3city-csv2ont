package com.ontology.datapreparation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author     ：YangKai.
 * @ Date       ：Created in 3:56 2018/7/3
 * @ Description：${description}
 * @ Version:     ${version}
 */
public class HarbinData extends BJDBData {
    public HarbinData(String tableName){
        super(tableName);
    }

    @Override
    public void TableDiffer(String[] concernCols ){
        OtherResult = new ArrayList<>();
        DataResult = new ArrayList<>();
        // 按照北京市政府数据表为例，进行分辨
        for(Map<String, Object> dataItem : QResult){
            if(String.valueOf(dataItem.get(concernCols[0])).contains("统计") ||
                    String.valueOf(dataItem.get(concernCols[1])).contains("情况") ||
                    String.valueOf(dataItem.get(concernCols[1])).contains("总值表") ||
                    String.valueOf(dataItem.get(concernCols[1])).contains("月度") ||
                    String.valueOf(dataItem.get(concernCols[1])).contains("年度") ||
                    String.valueOf(dataItem.get(concernCols[2])).contains("统计局")){
                OtherResult.add(dataItem);
            }
            else{
                DataResult.add(dataItem);
            }
        }
    }

    private String findOther(String source, ArrayList<String> tar){
        for(String item : tar){
            if(item.contains(source)){
                return item;
            }
        }
        return null;
    }
    public static void main(String[] args){
        HarbinData bjdata = new HarbinData("harbin_gov_data");
        bjdata.TableDiffer(new String[]{"description", "cata_title", "org_name"});
        System.out.println("统计表:");
        List<Map<String, Object>> tongji = bjdata.getOtherResult();
        for(Map<String, Object> item : tongji){
            for(String key : item.keySet()){
                if(key.equals("cata_title") || key.equals("cata_items"))
                System.out.print(String.valueOf(item.get(key)) + "  ");
            }
            System.out.println();
        }
        System.out.println("统计表总数："+tongji.size());
        System.out.println();
        System.out.println("数据表:");
        List<Map<String, Object>> shuju = bjdata.getDataResult();
        ArrayList<String> biao = new ArrayList<>();
        for(Map<String, Object> item : shuju){
            for(String key : item.keySet()){
//                System.out.println(key);
                if(key.equals("cata_title") || key.equals("cata_items"))
                System.out.print(String.valueOf(item.get(key)) + "  ");
                biao.add(String.valueOf(item.get("cata_title")));
            }
            System.out.println();
        }
        System.out.println("数据表总数："+shuju.size());

        BJOntology bjo = new BJOntology();
        HashMap<String, ArrayList<String>> conpro = bjo.getConProFromDescription("name", "description");
        int count =0;
        for(String con : conpro.keySet()){
            if(con.endsWith("信息"))
                con.replace("信息","");
            if(con.endsWith("目录"))
                con.replace("目录","");
            String re = bjdata.findOther(con, biao);
            if(re!=null){
//                System.out.println(count);
                System.out.print(con );
//                for(String pro : conpro.get(con)){
//                    System.out.print(pro + " | ");
//                }
                System.out.println();
//                System.out.print(re + "----");
//
//                System.out.println();
                count++;
            }

        }
        System.out.println(count);
    }
}
