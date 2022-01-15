package net.anke.books_sales_platform.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonData {
    private int code;
    private Map<String,Object> data;
    private String msg;
    public static JsonData buildSuccess(){
        return new JsonData(0,null,null);
    }
    public static JsonData buildError(){
        return new JsonData(-1,null,null);
    }
    public  JsonData code(int code){
        this.code=code;
        return this;
    }
    public JsonData msg(String msg){
        this.msg=msg;
        return this;
    }
    public JsonData put(String key,Object value){
        if(this.data==null) this.data=new HashMap<>();
        this.data.put(key, value);
        return this;
    }
    public JsonData map(Map<String,Object> map){
        this.data=map;
        return this;
    }
}
