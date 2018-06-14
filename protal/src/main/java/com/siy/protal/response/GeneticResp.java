package com.siy.protal.response;

import java.io.Serializable;
import java.util.Map;

/**
 *Created by chengliang on 2018/6/14.
 */
public class GeneticResp<T> implements Serializable{
    private static final long serialVersionUID = 4227833010077730477L;
    /**
     * 返回CODE
     */
    private int Code;
    /**
     * 返回成功 失败
     */
    private String msg;
    /**
     * 返回封装类型
     */
    private T data;
    /**
     * 返回额外键值对
     */
    private Map<Object,Object> params;

    public GeneticResp() {
        this.Code = 1000;
        this.msg = "操作失败";
    }

    public GeneticResp(int code, String msg, T data, Map<Object, Object> params) {
        this.Code = code;
        this.msg = msg;
        this.data = data;
        this.params = params;
    }

    public  GeneticResp success(T data,Map params){
        GeneticResp<T> geneticResp = new GeneticResp<>(200,"成功",data,params);
        return geneticResp;
    }
    
    public GeneticResp success(T data){
        GeneticResp<T> geneticResp = new GeneticResp<>();
        geneticResp.setCode(200);
        geneticResp.setMsg("操作成功");
        geneticResp.setData(data);
        return geneticResp;
    }
    
    public GeneticResp error(int code,String msg){
        GeneticResp<T> geneticResp = new GeneticResp<>();
        geneticResp.setCode(code);
        geneticResp.setMsg(msg);
        return geneticResp;
    }
    public GeneticResp error(){
        return new GeneticResp<>();
    }
    

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<Object, Object> getParams() {
        return params;
    }

    public void setParams(Map<Object, Object> params) {
        this.params = params;
    }
}
