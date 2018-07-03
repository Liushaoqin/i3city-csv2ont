package com.ontology.datapreparation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ Author     ：YangKai.
 * @ Date       ：Created in 2:11 2018/6/19
 * @ Description：${description}
 * @ Version:     ${version}
 */
public class DataType {

    /**
     * @ description: 判断str中是否存在连续的4位数字，来判断是否存在年代数字
     * @ Param: str
     * @ return: boolean
     * @ throw:
     * @ date: 2018/6/19
     */
    private static boolean hasYear(String str) {

        final String regexpPP = "^.*\\d{4}.*$";
        Pattern pattern = Pattern.compile(regexpPP);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


    public static void main(String[] args) {
        System.out.println(hasYear("北京地区海关主要商品出口量及金额(2016年)"));
        ;
    }
}
