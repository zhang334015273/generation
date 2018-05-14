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
public class GenMapperJavaFile {
	
	private static final String NAME = "dao";
	private GenMain gMain;
	private GenBeanFile beanFile;
	
	private String packageName;
	private String className;
	private String fileName;
	
	private String classQuaName; //全称
	
	public void generate(){
		//包名
		packageName = GenMain.BASE_PACKAGE_NAME + "." + GenMain.PROJECT_NAME + "." + gMain.getModelName() + "." + NAME;
		className = "i"+NameUtils.formatClassName(gMain.getTableName())+"Mapper";
		fileName = className + ".java";
		classQuaName = packageName+"."+className;
		
		File file = new File(PropertiesUtils.getKey("basepath.prefix") + packageName.replace(".", "/"),fileName);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		System.out.println("生成mapper.java文件的目录"+file.getAbsolutePath());
		
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
		content.append("import ").append(beanFile.getClassQuaName()).append(";").append(lineSeparator);
		content.append("import org.apache.ibatis.annotations.Param;").append(lineSeparator);
		content.append(lineSeparator);
		content.append(lineSeparator);
		
		//写入文件注释
		content.append(NotesUtils.getTypeNotes(gMain.getTableDesc()+"iMapper"));
		
		//写入文件名
		content.append("public interface ").append(className).append("{").append(lineSeparator);
		
		//写入增加方法
		content.append(lineSeparator);
		content.append(NotesUtils.getMethodNotes("新增"));
		content.append("\t").append("public int insert(").append(beanFile.getClassName()).append(" ").append(NameUtils.formatName(gMain.getTableName())).append(")").append(";").append(lineSeparator);
		
		//写入修改方法
		content.append(lineSeparator);
		content.append(NotesUtils.getMethodNotes("修改"));
		content.append("\t").append("public int update(").append(beanFile.getClassName()).append(" ").append(NameUtils.formatName(gMain.getTableName())).append(")").append(";").append(lineSeparator);
		
		//写入删除方法
		content.append(lineSeparator);
		content.append(NotesUtils.getMethodNotes("根据id删除"));
		content.append("\t").append("public int deleteById(@Param(\"").append(gMain.getStructureList().get(0).getFieldName()).append("\") String ").append(gMain.getStructureList().get(0).getFieldName()).append(")").append(";").append(lineSeparator);
		
		//写入单个查找方法
		content.append(lineSeparator);
		content.append(NotesUtils.getMethodNotes("根据id查找"));
		content.append("\t").append("public ").append(beanFile.getClassName()).append(" findById(@Param(\"").append(gMain.getStructureList().get(0).getFieldName()).append("\") String ").append(gMain.getStructureList().get(0).getFieldName()).append(")").append(";").append(lineSeparator);
		
		content.append(lineSeparator);
		content.append("}");
		
		return content.toString();
	}

	
	/**
	 * @param gMain
	 */
	public GenMapperJavaFile(GenMain gMain,GenBeanFile beanFile) {
		super();
		this.gMain = gMain;
		this.beanFile = beanFile;
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
