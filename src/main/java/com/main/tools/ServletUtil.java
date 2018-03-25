package com.main.tools;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

public class ServletUtil {
	
	//响应的ContentType内容
	private static final String RESPONSE_CONTENTTYPE = "application/json";

	//响应编码
	private static final String RESPONSE_CHARACTERENCODING = "utf-8";
	
	
	/**
	 * 生成成功报文
	 * @param httpCode
	 * @param result
	 * @param response
	 */
	public static String createSuccessResponse(Integer httpCode, Object result, HttpServletResponse response){

		return createSuccessResponse(httpCode, result, SerializerFeature.WriteMapNullValue,null,response);

	}

	public static String createSuccessResponse(Integer httpCode,String message,Object result,HttpServletResponse response){

		return createSuccessResponse(httpCode,message,result, SerializerFeature.WriteMapNullValue,null,response);

	}
	
	public static String createSuccessResponse(Integer httpCode, Object result, SerializerFeature serializerFeature, SerializeFilter filter, HttpServletResponse response){
		PrintWriter printWriter = null;
		String jsonString = "";
		try {
			response.setCharacterEncoding(RESPONSE_CHARACTERENCODING);
			response.setContentType(RESPONSE_CONTENTTYPE);
			response.setStatus(httpCode);
			printWriter = response.getWriter();
			if(null != result){
				if(null!=filter){
					jsonString = JSONObject.toJSONString(result,filter,serializerFeature);
				}else{
					jsonString = JSONObject.toJSONStringWithDateFormat(result,"yyyy-MM-dd HH:ss:mm",serializerFeature);
				}
				printWriter.write(jsonString); 
			}
			printWriter.flush();

		} catch (Exception e) {
			//log.error("createResponse failed", e);
		} finally {
			if(null!=printWriter)printWriter.close();
		}
		return jsonString;
	}

	public static String createSuccessResponse(Integer httpCode, String message, Object result, SerializerFeature serializerFeature, SerializeFilter filter, HttpServletResponse response){
		PrintWriter printWriter = null;
		String jsonString = "";
		try {
		
			response.setCharacterEncoding(RESPONSE_CHARACTERENCODING);
			response.setContentType(RESPONSE_CONTENTTYPE);
			response.setStatus(httpCode);
			printWriter = response.getWriter();
			SerializeConfig config = new SerializeConfig();
			config.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
			Map<String, Object> map = new HashMap<String, Object>();
			if(null != result){
				map.put("res_code", httpCode);
				map.put("message", message);
				map.put("data",result);
				if(null!=filter){					
					jsonString = JSONObject.toJSONString(map,filter,serializerFeature);
				}else{
					jsonString = JSONObject.toJSONStringWithDateFormat(map,"yyyy-MM-dd");
				
				}
				printWriter.write(jsonString);
			}
			printWriter.flush();

		} catch (Exception e) {
			//log.error("createResponse failed", e);
		} finally {
			if(null!=printWriter)printWriter.close();
		}
		return jsonString;
	}

}
