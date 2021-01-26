//package com.zh.ch.bigdata.test.pkmanage.common;
//
//import com.alibaba.fastjson.JSONObject;
//import com.ztesoft.zstream.flink.pkmanage.exception.ProjectException;
//import com.ztesoft.zstream.flink.pkmanage.mysqlutil.MyMysqlOperationImpl;
//
///**
// * @author xzc
// * @description Ogg数据解析工具
// * @date 2021/01/05
// */
//public class OggDataAnalyzeUtil {
//
//    /**
//     * 获取主键数据
//     * @param oggData ogg数据
//     * @return 主键数组
//     * @throws ProjectException 异常
//     */
//    public static String[] getPrimaryKeysNames(String oggData) throws ProjectException {
//        String primaryKeys = JsonAnalyzeUtil.getJsonArray(oggData, "primary_keys").toJSONString();
//        return primaryKeys.split(",");
//    }
//
//    /**
//     * 获取ogg中修改前的数据
//     * @param oggData ogg数据
//     * @return ogg中修改前的数据
//     * @throws ProjectException 异常
//     */
//    public static JSONObject getBeforeData(String oggData) throws ProjectException {
//        return JsonAnalyzeUtil.getJsonObject(oggData, "before");
//    }
//
//    /**
//     * 获取修改后的数据
//     * @param oggData ogg数据
//     * @return ogg中修改后的数据
//     * @throws ProjectException 异常
//     */
//    public static JSONObject getAfterData(String oggData) throws ProjectException {
//        return JsonAnalyzeUtil.getJsonObject(oggData, "after");
//    }
//
//    /**
//     * 对比Ogg中的数据，判断该操作中主键是否修改
//     * @param primaryKeys 主键
//     * @param beforeData 修改前数据
//     * @param afterData 修改后数据
//     */
//    public static void compare(String[] primaryKeys, JSONObject beforeData, JSONObject afterData) throws ProjectException {
//        MyMysqlOperationImpl myMysqlOperationImpl = new MyMysqlOperationImpl();
//        for (String primaryKey : primaryKeys) {
//            String primaryKeyDataBefore = JsonAnalyzeUtil.getString(beforeData, primaryKey);
//            String primaryKeyDataAfter = JsonAnalyzeUtil.getString(afterData, primaryKey);
//            if (!primaryKeyDataBefore.equals(primaryKeyDataAfter)) {
//
//
//            }
//        }
//    }
//
//
//}
