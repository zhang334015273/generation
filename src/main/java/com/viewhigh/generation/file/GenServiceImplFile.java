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
public class GenServiceImplFile {
	
	private static final String NAME = "service.impl";
	private GenMain gMain;
	private GenBeanFile beanFile;
	private GenMapperJavaFile mapperFile;
	private GenIServiceFile serviceFile;
	
	private String packageName;
	private String className;
	private String fileName;
	
//	private String classQuaName; //全称
	
	public void generate(){
		//包名
		packageName = GenMain.BASE_PACKAGE_NAME + "." + GenMain.PROJECT_NAME + "." + gMain.getModelName() + "." + NAME;
		className = NameUtils.formatClassName(gMain.getTableName())+"ServiceImpl";
		fileName = className + ".java";
//		classQuaName = packageName+"."+className;
		
		File file = new File(PropertiesUtils.getKey("basepath.prefix") + packageName.replace(".", "/"),fileName);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		System.out.println("生成serviceImpl.java文件的目录"+file.getAbsolutePath());
		
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
		content.append("import org.springframework.beans.factory.annotation.Autowired;").append(lineSeparator);
		content.append("import org.springframework.stereotype.Service;").append(lineSeparator);
		content.append("import org.springframework.transaction.annotation.Isolation;").append(lineSeparator);
		content.append("import org.springframework.transaction.annotation.Propagation;").append(lineSeparator);
		content.append("import org.springframework.transaction.annotation.Transactional;").append(lineSeparator);
		content.append(lineSeparator);
		content.append("import ").append(beanFile.getClassQuaName()).append(";").append(lineSeparator);
		content.append("import ").append(mapperFile.getClassQuaName()).append(";").append(lineSeparator);
		content.append("import ").append(serviceFile.getClassQuaName()).append(";").append(lineSeparator);
		content.append(lineSeparator);
		content.append(lineSeparator);
		
		//写入文件注释
		content.append(NotesUtils.getTypeNotes(gMain.getTableDesc()+"ServiceImpl"));
		
		//写入文件名
		content.append("@Service").append(lineSeparator);
		content.append("@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = true)").append(lineSeparator);
		content.append("public class ").append(className).append(" implements ").append(serviceFile.getClassName()).append(" {").append(lineSeparator);
		
		//注入mapper
		String mapperName = NameUtils.formatName(gMain.getTableName())+"Mapper";
		content.append(lineSeparator);
		content.append("\t").append("@Autowired").append(lineSeparator);
		content.append("\t").append("private ").append(mapperFile.getClassName()).append(" ").append(mapperName).append("; ").append(lineSeparator);
		
		//写入增加方法
		content.append(lineSeparator);
		content.append(lineSeparator);
		content.append(NotesUtils.getMethodNotes("新增"));
		content.append("\t").append("@Transactional(readOnly = false)").append(lineSeparator);
		content.append("\t").append("public int insert(").append(beanFile.getClassName()).append(" ").append(NameUtils.formatName(gMain.getTableName())).append(")").append("{").append(lineSeparator);
		content.append("\t\t").append("return ").append(mapperName).append(".insert(").append(NameUtils.formatName(gMain.getTableName())).append(");").append(lineSeparator);
		content.append("\t").append("}");
		
		//写入修改方法
		content.append(lineSeparator);
		content.append(lineSeparator);
		content.append(NotesUtils.getMethodNotes("修改"));
		content.append("\t").append("@Transactional(readOnly = false)").append(lineSeparator);
		content.append("\t").append("public int update(").append(beanFile.getClassName()).append(" ").append(NameUtils.formatName(gMain.getTableName())).append(")").append("{").append(lineSeparator);
		content.append("\t\t").append("return ").append(mapperName).append(".update(").append(NameUtils.formatName(gMain.getTableName())).append(");").append(lineSeparator);
		content.append("\t").append("}");
		
		//写入删除方法
		content.append(lineSeparator);
		content.append(lineSeparator);
		content.append(NotesUtils.getMethodNotes("根据id删除"));
		content.append("\t").append("@Transactional(readOnly = false)").append(lineSeparator);
		content.append("\t").append("public int deleteById(String ").append(gMain.getStructureList().get(0).getFieldName()).append(")").append("{").append(lineSeparator);
		content.append("\t\t").append("return ").append(mapperName).append(".deleteById(").append(gMain.getStructureList().get(0).getFieldName()).append(");").append(lineSeparator);
		content.append("\t").append("}");
		
		//写入单个查找方法
		content.append(lineSeparator);
		content.append(lineSeparator);
		content.append(NotesUtils.getMethodNotes("根据id查找"));
		content.append("\t").append("public ").append(beanFile.getClassName()).append(" findById(String ").append(gMain.getStructureList().get(0).getFieldName()).append(")").append("{").append(lineSeparator);
		content.append("\t\t").append("return ").append(mapperName).append(".findById(").append(gMain.getStructureList().get(0).getFieldName()).append(");").append(lineSeparator);
		content.append("\t").append("}");
		
		content.append(lineSeparator);
		content.append("}");
		
		return content.toString();
	}

	/**
	 * @param gMain
	 * @param beanFile
	 * @param mapperFile
	 */
	public GenServiceImplFile(GenMain gMain, GenBeanFile beanFile, GenMapperJavaFile mapperFile,GenIServiceFile serviceFile) {
		super();
		this.gMain = gMain;
		this.beanFile = beanFile;
		this.mapperFile = mapperFile;
		this.serviceFile = serviceFile;
	}

	
}
