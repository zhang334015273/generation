/**
 * 版权所属：东软望海科技有限公司
 * 作者：张晓明 
 * 版本：V1.0
 * 创建日期：2018年5月10日
 * 修改日期：2018年5月10日
 */
package com.viewhigh.generation.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * @Description  
 * @author zhangxm 
 * @version v1.0
 * @since 2018年5月10日
 */
public class PropertiesUtils {
	
	private static final String PROPERTIES_PATH = "conf.properties";
	
	private static Properties props = new Properties();  
	
	static{
		try {
			props = PropertiesLoaderUtils.loadAllProperties(PROPERTIES_PATH);
		} catch (IOException e) {
		}
	}
	
	
	public static String getKey(String key){
		return props.get(key).toString();
	}
	
	public static List<String> getListByKey(String key){
	    String str = props.getProperty(key).toString();
	    return Arrays.asList(str.split(","));
	}
	
	public static void main(String[] args) {
		System.out.println(PropertiesUtils.getKey("file.author"));
	}
	
	

}
