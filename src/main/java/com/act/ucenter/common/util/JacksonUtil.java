package com.act.ucenter.common.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
* @ClassName: JacksonUtil 
* @Description: json字符串转化为javabean
* @author Administrator 
* @date 2017-7-3 下午4:54:27 
*
 */
public class JacksonUtil {
	
	public static <T>List<T> convertJsonListToObjectList(List<String> jacksons, Class<T> beanclass ) throws JsonParseException, JsonMappingException, IOException{
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<T> list = new ArrayList<>();
		for (String jsonString : jacksons) {
			if ("".equals(jsonString)){
				continue;
			}
			T readValue = objectMapper.readValue(jsonString, beanclass);
			list.add(readValue);
		}
		
		return list;
	}

}
