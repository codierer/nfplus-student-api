package com.nfplus.vo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * @author zac
 * @description:pojo 封装返回给客户段数据,以及相关状态
 * @data 2020 2020/3/12 17:05
 */
public class Result {

    private static final ObjectMapper MAPPER = new ObjectMapper();


    private Integer status;

    private String msg;

    private Object data;

    private String ok;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public Result(){
    }

    /**
     * 封装为Result对象
     * @param status
     * @param msg
     * @param data
     */
    public Result(Integer status, String msg, Object data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static Result build(Integer status, String msg, Object data){
        return new Result(status,msg,data);
    }

    /**
     * 返回200状态
     * @param data
     */
    public Result(Object data){
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public static Result ok(Object data){
        return new Result(data);
    }

    public static Result error(String msg){
        return new Result(500, msg, null);
    }

    public static Result errorMap(Object data){
        return new Result(501,"error",data);
    }

    public static Result errorTokenMsg(String msg){
        return new Result(502, msg, null);
    }

    public static Result errorException(String msg){
        return new Result(555, msg,null);
    }

    /**
     * 格式化pojo格式
     * @param jsonData
     * @param clazz
     * @return
     */
    public static Result formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, Result.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * json转化Result对象
     * @param json
     * @return
     */
    public static Result format(String json) {
        try {
            return MAPPER.readValue(json, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json对象转化为list
     * @param jsonData
     * @param clazz
     * @return
     */
    public static Result formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }



}
