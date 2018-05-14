/**
 * 版权所属：东软望海科技有限公司
 * 作者：张晓明 
 * 版本：V1.0
 * 创建日期：2018年5月11日
 * 修改日期：2018年5月11日
 */
package com.viewhigh.generation.file;

import java.io.File;
import java.io.IOException;
import com.viewhigh.generation.bean.FieldBean;
import com.viewhigh.generation.util.FileUtils;
import com.viewhigh.generation.util.NameUtils;
import com.viewhigh.generation.util.PropertiesUtils;

/**
 * @Description  
 * @author zhangxm 
 * @version v1.0
 * @since 2018年5月11日
 */
public class GenMapperXmlFile {
	
	private static final String NAME = "dao";
	private GenMain gMain;
	private GenBeanFile beanFile;
	private GenMapperJavaFile mapperFile;
	
	private String packageName;
	private String className;
	private String fileName;
	
	private String classQuaName; //全称
	
	public void generate(){
		//包名
		packageName = GenMain.BASE_PACKAGE_NAME + "." + GenMain.PROJECT_NAME + "." + gMain.getModelName() + "." + NAME;
		className = "i"+NameUtils.formatClassName(gMain.getTableName())+"Mapper";
		fileName = className + ".xml";
		classQuaName = packageName+"."+className;
		
		File file = new File(PropertiesUtils.getKey("basepath.prefix") + packageName.replace(".", "/"),fileName);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		System.out.println("生成mapper.xml文件的目录"+file.getAbsolutePath());
		
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//修改文件编码
		FileUtils.changeFileEncoding(file, "UTF-8");
		
		//向文件写入内容
		FileUtils.WriteStringToFile(file, genContent());
		
	}
	
	private String genContent(){
		String lineSeparator = System.getProperty("line.separator");
		StringBuffer content = new StringBuffer();
		
		//写入文件头部
		content.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append(lineSeparator);
		content.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >").append(lineSeparator);
		content.append("<mapper namespace=\"").append(mapperFile.getClassQuaName()).append("\">").append(lineSeparator);
		
		//写入共有列sql
		content.append(lineSeparator);
		content.append("\t").append("<!-- 数据库中表名为:"+gMain.getTableName()+"的列名,as前是数据库的列明,as后是列的别名用于映射成实体类中的属性 -->").append(lineSeparator);
		content.append("\t").append("<sql id=\"").append(NameUtils.formatName(gMain.getTableName())).append("Column\">").append(lineSeparator);
		for(int i=0;i<gMain.getStructureList().size();i++){
			FieldBean field = gMain.getStructureList().get(i);
			if(i==gMain.getStructureList().size()-1){
				content.append("\t\t").append(gMain.getTableName()).append(".").append(field.getColumnName()).append(" as ").append(field.getFieldName()).append(lineSeparator);
			}else{
				content.append("\t\t").append(gMain.getTableName()).append(".").append(field.getColumnName()).append(" as ").append(field.getFieldName()).append(",").append(lineSeparator);
			}
		}
		content.append("\t").append("</sql>").append(lineSeparator);
		
		//写入新增sql
		content.append(lineSeparator);
		content.append("\t").append("<insert id=\"insert\" ").append("keyColumn=\"").append(gMain.getStructureList().get(0).getColumnName()).append("\" keyProperty=\"").append(gMain.getStructureList().get(0).getFieldName()).append("\">").append(lineSeparator);
		content.append("\t\t").append("<selectKey ").append("keyColumn=\"").append(gMain.getStructureList().get(0).getColumnName()).append("\" keyProperty=\"").append(gMain.getStructureList().get(0).getFieldName()).append("\"  order=\"BEFORE\" resultType=\"string\">").append(lineSeparator);
		content.append("\t\t\t").append("select uuid()").append(lineSeparator);
		content.append("\t\t").append("</selectKey>").append(lineSeparator);
		content.append("\t\t").append("insert into ").append(gMain.getTableName()).append(lineSeparator);
		content.append("\t\t").append("( ").append(gMain.getStructureList().get(0).getColumnName()).append(lineSeparator);
		for(int i=1;i<gMain.getStructureList().size();i++){
			FieldBean field = gMain.getStructureList().get(i);
			content.append("\t\t\t").append("<if test=\"").append(field.getFieldName()).append(" != null and ").append(field.getFieldName()).append(" != '' \">").append(lineSeparator);
			content.append("\t\t\t\t,").append(field.getColumnName()).append(lineSeparator);
			content.append("\t\t\t").append("</if>").append(lineSeparator);
			
		}
		content.append("\t\t").append(") ").append(lineSeparator);
		content.append("\t\t").append("values (#{").append(gMain.getStructureList().get(0).getFieldName()).append("}").append(lineSeparator);
		for(int i=1;i<gMain.getStructureList().size();i++){
			FieldBean field = gMain.getStructureList().get(i);
			content.append("\t\t\t").append("<if test=\"").append(field.getFieldName()).append(" != null and ").append(field.getFieldName()).append(" != '' \">").append(lineSeparator);
			content.append("\t\t\t\t,#{").append(field.getFieldName()).append("}").append(lineSeparator);
			content.append("\t\t\t").append("</if>").append(lineSeparator);
			
		}
		content.append("\t\t").append(") ").append(lineSeparator);
		content.append("\t").append("</insert>").append(lineSeparator);
		
		//写入修改sql
		content.append(lineSeparator);
		content.append("\t").append("<update id=\"update\">").append(lineSeparator);
		content.append("\t\t").append("update ").append(gMain.getTableName()).append(lineSeparator);
		content.append("\t\t").append("<trim prefix=\"set\" suffixOverrides=\",\">").append(lineSeparator);
		for(int i=1;i<gMain.getStructureList().size();i++){
			FieldBean field = gMain.getStructureList().get(i);
			content.append("\t\t\t").append("<if test=\"").append(field.getFieldName()).append(" != null and ").append(field.getFieldName()).append(" != '' \">").append(lineSeparator);
			content.append("\t\t\t\t").append(field.getColumnName()).append("=#{").append(field.getFieldName()).append("}").append(",").append(lineSeparator);
			content.append("\t\t\t").append("</if>").append(lineSeparator);
		}
		content.append("\t\t").append("</trim>").append(lineSeparator);
		content.append("\t\t").append("WHERE ").append(gMain.getStructureList().get(0).getColumnName()).append("=#{").append(gMain.getStructureList().get(0).getFieldName()).append("}").append(lineSeparator);
		content.append("\t").append("</update>").append(lineSeparator);
		
		//写入删除sql
		content.append(lineSeparator);
		content.append("\t").append("<delete id=\"deleteById\">").append(lineSeparator);
		content.append("\t\t").append("DELETE FROM  ").append(gMain.getTableName()).append(lineSeparator);
		content.append("\t\t").append("WHERE ").append(gMain.getStructureList().get(0).getColumnName()).append("=#{").append(gMain.getStructureList().get(0).getFieldName()).append("}").append(lineSeparator);
		content.append("\t").append("</delete>").append(lineSeparator);
		
		//写入查找sql
		content.append(lineSeparator);
		content.append("\t").append("<select id=\"findById\" resultType=\"").append(beanFile.getClassQuaName()).append("\">").append(lineSeparator);
		content.append("\t\t").append("SELECT <include refid=\"").append(NameUtils.formatName(gMain.getTableName())).append("Column").append("\"/>").append(lineSeparator);
		content.append("\t\t").append("FROM ").append(gMain.getTableName()).append(lineSeparator);
		content.append("\t\t").append("WHERE ").append(gMain.getStructureList().get(0).getColumnName()).append("=#{").append(gMain.getStructureList().get(0).getFieldName()).append("}").append(lineSeparator);
		content.append("\t").append("</select>").append(lineSeparator);
		
		
		content.append("</mapper>").append(lineSeparator);
		
		return content.toString();
	}

	
	/**
	 * @param gMain
	 */
	public GenMapperXmlFile(GenMain gMain,GenBeanFile beanFile,GenMapperJavaFile mapperFile) {
		super();
		this.gMain = gMain;
		this.beanFile = beanFile;
		this.mapperFile = mapperFile;
	}

	/**
	 * @return the classQuaName
	 */
	public String getClassQuaName() {
		return classQuaName;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	
	
	
	
}
