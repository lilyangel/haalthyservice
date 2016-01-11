package com.haalthy.service.common;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ken on 2016-01-11.
 */
public class StringUtils {
    public static boolean IsEmpty(String str){
        boolean isEmpty = true;
        if(str != null || (str.trim()).length() > 0)
            isEmpty = false;
        return isEmpty;
    }

    public static String IntToString(int i){
        return Integer.toString(i);
    }

    public static String FloatToString(float f){
        return  Float.toString(f);
    }

    public static String LongToString(long l){
        return  Long.toString(l);
    }

    public static String DoubleToString(double d){
        return Double.toString(d);
    }
    public static String DateToString(Date date, String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static int StringToInt(String str){
        try {
              return Integer.parseInt(str,10);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static long StringToLong(String str){
        try {
            return Long.parseLong(str,10);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static float StringToFloat(String str){
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.00F;
        }
    }

    public static double StringToDouble(String str){
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.00D;
        }
    }

    public static byte[] StringToByte(String str){
        return SerializeUtil.serialize(str);
    }

    public static String StringToByte(byte[] bty){
        return bty.toString();
    }

    /**
     * 封装将json对象转换为java集合对象
     *
     * @param <T>
     * @param clazz
     * @param jsons
     * @return
     */
    public static <T> List<T> getJavaCollection(T clazz, String jsons) {
        List<T> objs=null;
        JSONArray jsonArray=(JSONArray) JSONSerializer.toJSON(jsons);
        if(jsonArray!=null){
            objs=new ArrayList<T>();
            List list=(List)JSONSerializer.toJava(jsonArray);
            for(Object o:list){
                JSONObject jsonObject=JSONObject.fromObject(o);
                T obj=(T)JSONObject.toBean(jsonObject, clazz.getClass());
                objs.add(obj);
            }
        }
        return objs;
    }


    /**
     * 封装将json对象转换为java集合对象
     *
     * @param <T>
     * @param jsons
     * @return
     */
    public static <T>T getJava(String jsons) {
        T obj = null;
        if(!StringUtils.IsEmpty(jsons)){
            JSONArray jarr = (JSONArray) JSONSerializer.toJSON(jsons);
            JSONObject jobj = JSONObject.fromObject(jarr.toArray());
            obj = (T)JSONObject.toBean(jobj);
        }
        return obj;
    }

    /**
     * 封装将java集合对象转换为JSON串
     *
     * @param <T>
     * @param clazz
     * @param
     * @return jsons
     */
    public static <T>String getJson(List<T> clazz)
    {
        JSONArray array = JSONArray.fromObject(clazz);
        String json = array.toString();
        return json;
    }


    /**
     * 封装将java集合对象转换为JSON串
     *
     * @param <T>
     * @param clazz
     * @param
     * @return jsons
     */
    public static <T>String getJson(T clazz)
    {
        JSONArray array = JSONArray.fromObject(clazz);
        String json = array.toString();
        return json;
    }
}
