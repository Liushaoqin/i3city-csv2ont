package com.Util.JDBCUtil;

import com.Util.JDBCUtil.util.DBUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @ Author     ：YangKai.
 * @ Date       ：Created in 14:58 2018/6/18
 * @ Description：测试JDBC
 * @ Version:
 */
public class JDBCTest {
    public static void main(String[] args) {

        System.out.println("带条件的查询1");
        testQuery1();
    }

    /**
     * 查询方式一
     */
    public static void testQuery1() {
        Map<String, Object> whereMap = new HashMap<>();
        try {
            DBUtil.query("bj_gov_data", whereMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
