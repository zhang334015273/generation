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
import com.viewhigh.generation.util.NotesUtils;
import com.viewhigh.generation.util.PropertiesUtils;

/**
 * @Description  
 * @author zhangxm 
 * @version v1.0
 * @since 2018年5月11日
 */
public class GenBeanFile {
	
	private static final String NAME = "bean";
	private GenMain gMain;
	private String packageName;
	private String className;
	private String fileName;
	
	private String classQuaName; //全称
	
	public void generate(){
		//包名
		packageName = GenMain.BASE_PACKAGE_NAME + "." + GenMain.PROJECT_NAME + "." + gMain.getModelName() + "." + NAME;
		className = NameUtils.formatClassName(gMain.getTableName());
		fileName = className + ".java";
		classQuaName = packageName+"."+className;
		
		File file = new File(PropertiesUtils.getKey("basepath.prefix") + packageName.replace(".", "/"),fileName);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		System.out.println("生成bean文件的目录"+file.getAbsolutePath());
		
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
		//写入文件注释
		content.append(NotesUtils.getFileNotes());
		
		//写入文件包名
		content.append("package ").append(packageName).append(";").append(lineSeparator);
		content.append(lineSeparator);
		content.append(lineSeparator);
		
		//写入导入的包名
		for(String str: gMain.getClassNameSet()){
			content.append("import ").append(str).append(lineSeparator);
		}
		
		//写入文件注释
		content.append(NotesUtils.getTypeNotes(gMain.getTableDesc()+"Bean"));
		
		//写入文件名
		content.append("@SuppressWarnings(\"serial\")").append(lineSeparator);
		content.append("public class ").append(className).append(" implements java.io.Serializable").append("{").append(lineSeparator);
		
		//写入字段名
		content.append(lineSeparator);
		for(FieldBean field : gMain.getStructureList()){
			content.append("\t").append("private ").append(field.getVhClass().getSimpleName()).append(" ").append(field.getFieldName()).append("; ").append("//").append(field.getFieldContent()).append(lineSeparator);
		}
		
		//写入get与set
		content.append(lineSeparator);
		content.append("\t").append("/*==================== gen setter and getter begin=====================*/").append(lineSeparator);
		for(FieldBean field : gMain.getStructureList()){
			content.append("\t").append("public ").append(field.getVhClass().getSimpleName()).append(" get").append(field.getFieldNameUp()).append("() {").append(lineSeparator);
			content.append("\t\t").append("return ").append(field.getFieldName()).append(";").append(lineSeparator);
			content.append("\t").append("}").append(lineSeparator);
			
			content.append("\t").append("public void set").append(field.getFieldNameUp()).append("(").append(field.getVhClass().getSimpleName()).append(" ").append(field.getFieldName()).append(") {").append(lineSeparator);
			content.append("\t\t").append("this.").append(field.getFieldName()).append(" = ").append(field.getFieldName()).append(";").append(lineSeparator);
			content.append("\t").append("}").append(lineSeparator);
			
			content.append(lineSeparator);
		}
		content.append("\t").append("/*==================== gen setter and getter end  =====================*/").append(lineSeparator);
		
		content.append(lineSeparator);
		content.append("}");
		
		return content.toString();
	}
	

	/**
	 * @param gMain
	 */
	public GenBeanFile(GenMain gMain) {
		super();
		this.gMain = gMain;
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

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
	

	
	

}
