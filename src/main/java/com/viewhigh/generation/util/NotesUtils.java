/**
 * 版权所属：东软望海科技有限公司
 * 作者：张晓明 
 * 版本：V1.0
 * 创建日期：2018年5月11日
 * 修改日期：2018年5月11日
 */
package com.viewhigh.generation.util;

import java.util.Date;

/**
 * @Description  
 * @author zhangxm 
 * @version v1.0
 * @since 2018年5月11日
 */
public class NotesUtils {
	
	public static String getFileNotes(){
		String lineSeparator = System.getProperty("line.separator");
		StringBuffer content = new StringBuffer();
		content.append("/**").append(lineSeparator);
		content.append(" * 版权所属：东软望海科技有限公司").append(lineSeparator);
		content.append(" * 作者：").append(PropertiesUtils.getKey("file.author")).append(lineSeparator);
		content.append(" * 版本：V1.0").append(lineSeparator);
		content.append(" * 创建日期：").append(TimeUtils.formatDate(new Date(), "yyyy年MM月dd日")).append(lineSeparator);
		content.append(" * 修改日期：").append(TimeUtils.formatDate(new Date(), "yyyy年MM月dd日")).append(lineSeparator);
		content.append(" */").append(lineSeparator);
		return content.toString();
	}
	
	public static String getTypeNotes(){
		return getTypeNotes("");
	}
	
	public static String getTypeNotes(String desc){
		String lineSeparator = System.getProperty("line.separator");
		StringBuffer content = new StringBuffer();
		content.append("/**").append(lineSeparator);
		content.append(" * @Description ").append(desc).append(lineSeparator);
		content.append(" * @author：").append(PropertiesUtils.getKey("class.author")).append(lineSeparator);
		content.append(" * @version：V1.0").append(lineSeparator);
		content.append(" * @since：").append(TimeUtils.formatDate(new Date(), "yyyy年MM月dd日")).append(lineSeparator);
		content.append(" */").append(lineSeparator);
		return content.toString();
	}
	
	public static String getMethodNotes(String str){
		String lineSeparator = System.getProperty("line.separator");
		StringBuffer content = new StringBuffer();
		content.append("\t").append("/**").append(lineSeparator);
		content.append("\t").append(" * @Description ").append(str).append(lineSeparator);
		content.append("\t").append(" */").append(lineSeparator);
		return content.toString();
	}
	

}
