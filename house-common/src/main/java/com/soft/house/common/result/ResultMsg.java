package com.soft.house.common.result;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @ClassName: ResultMsg
 * @Description: 返回消息类
 * @Author: Jason Biao
 * @Date: 2019/8/13 15:19
 * @Version: 1.0
 **/
public class ResultMsg {

    /**
     * 错误消息的key
     */
    public static final String errorMsgKey = "errorMsg";

    /**
     * 成功消息的key
     */
    public static final String successMsgKey = "successMsg";

    /**
     * 返回错误消息
     */
    private String errorMsg;

    /**
     * 返回成功消息
     */
    private String successMsg;

    /**
     * 判断验证请求是否成功
     * @return
     */
    public boolean isSuccess(){
        return errorMsg == null;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    /**
     * 返回错误消息
     * @param msg
     * @return
     */
    public static ResultMsg errorMsg(String msg){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setErrorMsg(msg);
        return resultMsg;
    }

    /**
     * 返回成功消息
     * @param msg
     * @return
     */
    public static ResultMsg successMsg(String msg){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setSuccessMsg(msg);
        return resultMsg;
    }

    /**
     * 将 ResultMsg对象中的返回 转换为map
     * @return
     */
    public Map<String,String> asMap(){
        Map<String,String> map = Maps.newHashMap();
        map.put(successMsgKey,successMsg);
        map.put(errorMsgKey,errorMsg);
        return map;
    }

    /**
     * 返回消息拼接URL参数
     * @return
     */
    public String asUrlParams(){
        Map<String,String> map = asMap();
        Map<String,String> newMap = Maps.newHashMap();
        map.forEach((k,v) ->{
            if (v != null){
                try {
                    newMap.put(k, URLEncoder.encode(v,"utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        //如果为null用空串代替，否则用等号分割
        return Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(newMap);
    }

}
