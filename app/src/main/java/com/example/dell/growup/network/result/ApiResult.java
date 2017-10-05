package com.example.dell.growup.network.result;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;



public class ApiResult<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public ApiResult(){
        this.code = 0;
        this.msg = "成功";
    }

    public ApiResult(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public ApiResult(int code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }

    public T getData(){
        return data;
    }

    public static class JsonAdapter implements JsonDeserializer{

        @Override
        public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                String jsonRoot = json.getAsJsonObject().toString() ;
                ApiResult apiResult = new ApiResult() ;
                JSONObject jsobRespData = new JSONObject(jsonRoot) ;
                apiResult.code = jsobRespData.getInt("code") ;
                apiResult.msg = jsobRespData.getString("msg") ;
                apiResult.data = jsobRespData.get("data") ;
                return apiResult;
            } catch (JSONException e) {
                throw new JsonParseException(e) ;
            }
        }
    }
}
