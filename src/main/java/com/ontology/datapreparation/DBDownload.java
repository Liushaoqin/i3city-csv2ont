package com.ontology.datapreparation;

import com.config.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author     ：YangKai.
 * @ Date       ：Created in 16:15 2018/6/18
 * @ Description：${description}
 * @ Version:     ${version}
 */
public class DBDownload {

    public static void main(String[] args) {
        DBDownload dbDownLoad = new DBDownload();
        String outPath = Config.bjGovData;
        dbDownLoad.downloadBJData(outPath);
    }

    /**
     * @ description: 由（表名，URL）下载数据文件到output路径中
     * @ Param: dbName2Url
     * @ Param: outPath
     * @ return: void
     * @ throw:
     * @ date: 2018/6/19
     */
    public void downloadData(HashMap<String, String> dbName2Url, String outPath) {

        try {
            for (String dbName : dbName2Url.keySet()) {
                DataUrlDownloader.downLoadFromUrl(dbName2Url.get(dbName), dbName + ".csv", outPath);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * @ description: 由北京政府部门的查询数据中得到（name， url）map
     * @ Param:
     * @ return: java.util.HashMap<java.lang.String   ,   java.lang.String>
     * @ throw:
     * @ date: 2018/6/19
     */
    private HashMap<String, String> getBJDbNameUrlMap() {

        BJDBData bjData = new BJDBData("bj_gov_data");
        List<Map<String, Object>> bjQResult = bjData.getQResult();
        HashMap<String, String> bjName2Url = new HashMap<>();
        for (Map<String, Object> bjItem : bjQResult) {
            bjName2Url.put(String.valueOf(bjItem.get("name")), String.valueOf(bjItem.get("file_url")));
        }
        return bjName2Url;
    }

    /**
     * @ description: 下载北京政府部门数据
     * @ Param: outPath
     * @ return: void
     * @ throw:
     * @ date: 2018/6/19
     */
    public void downloadBJData(String outPath) {

        downloadData(getBJDbNameUrlMap(), outPath);
    }
}

