/**
 * 版权所属：东软望海科技有限公司
 * 作者：张晓明 
 * 版本：V1.0
 * 创建日期：2018年5月11日
 * 修改日期：2018年5月11日
 */
package com.viewhigh.generation.file;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.viewhigh.generation.bean.FieldBean;
import com.viewhigh.generation.util.NameUtils;
import com.viewhigh.generation.util.PropertiesUtils;

/**
 * @Description  代码生成总入口
 * @author zhangxm 
 * @version v1.0
 * @since 2018年5月11日
 */
public class GenMain {
	
	public static final String BASE_PACKAGE_NAME = "com.viewhigh"; 
	
	public static final String PROJECT_NAME = "saas";
	
	private List<FieldBean> structureList = null ;
	
	private Set<String> classNameSet = new HashSet<String>();
	
	private boolean isGenBeanOnly;
	
	private String tableName;
	
	private String tableDesc;
	
	private String modelName;
	
	
	/**
	 * 代码生成入口
	 * @param modelName 模块名
	 * @throws Exception 
	 */
	public void generate() throws Exception{
		
		//初始化连接
		initConn();
		
		//生成bean文件
		GenBeanFile beanFile = new GenBeanFile(this);
		beanFile.generate();
		
		if(!isGenBeanOnly){
			GenMapperJavaFile mapperFile = new GenMapperJavaFile(this, beanFile);
			mapperFile.generate();
			GenMapperXmlFile mapperXmlFile = new GenMapperXmlFile(this, beanFile, mapperFile);
			mapperXmlFile.generate();
			
			GenIServiceFile serviceFile = new GenIServiceFile(this, beanFile);
			serviceFile.generate();
			
			GenServiceImplFile serviceImplFile = new GenServiceImplFile(this, beanFile, mapperFile, serviceFile);
			serviceImplFile.generate();
		}
		
	}
	
	private void initConn() throws Exception{
		Class.forName(PropertiesUtils.getKey("jdbc.driverClassName"));//指定连接类型  
		String mysqlUrl = PropertiesUtils.getKey("jdbc.url");
		String userName = PropertiesUtils.getKey("jdbc.username");
		String password = PropertiesUtils.getKey("jdbc.password");
				
		Connection connection = DriverManager.getConnection(mysqlUrl, userName, password);//获取连接  
		String sql = "SELECT COLUMN_NAME,COLUMN_COMMENT,DATA_TYPE FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = '"+PROJECT_NAME+"' AND TABLE_NAME = '"+tableName+"' order by ORDINAL_POSITION";
		PreparedStatement pst = connection.prepareStatement(sql);//准备执行语句
		ResultSet rSet = pst.executeQuery();
		structureList = convertList(rSet);
		sql = "select TABLE_NAME,TABLE_COMMENT from information_schema.TABLES where TABLE_SCHEMA = '"+PROJECT_NAME+"' AND TABLE_NAME = '"+tableName+"'";
		pst = connection.prepareStatement(sql);//准备执行语句
		rSet = pst.executeQuery();
		tableDesc = getTableComment(rSet);
		pst.close();
		connection.close();
		if(structureList.size() == 0){
			throw new Exception("转换失败，请确认表名是否存在");
		}
	}
	
	private String getTableComment(ResultSet rs) throws SQLException{
		if(rs.next()){
			return rs.getObject(2).toString();
		}
		return "";
	}
	
	private List<FieldBean> convertList(ResultSet rs) throws SQLException{
		List<FieldBean> list = new ArrayList<FieldBean>();
		while (rs.next()) {
			FieldBean field = new FieldBean();
			field.setColumnName(rs.getObject(1).toString());
			field.setFieldContent(rs.getObject(2).toString());
			field.setFieldName(NameUtils.formatName(field.getColumnName()));
			field.setFieldNameUp(NameUtils.formatClassName(field.getColumnName()));
			switch (rs.getObject(3).toString()) {
				case "varchar":
				case "text":
					//字符串
					field.setVhClass(String.class);
					break;
					
				case "datetime":
				case "date":
					//日期
					field.setVhClass(Date.class);
					classNameSet.add("java.util.Date");
					break;
				case "decimal":
					field.setVhClass(BigDecimal.class);
					classNameSet.add("java.math.BigDecimal");
					break;
				case "timestamp":
					//时间搓
					field.setVhClass(Long.class);
					break;
	
				default:
					//默认int
					field.setVhClass(Integer.class);
					break;
			}
			list.add(field);
		}
		return list;
	}
	
	

	/**
	 * @param tableName
	 * @param modelName
	 */
	public GenMain(String tableName, String modelName,boolean isGenBeanOnly) {
		super();
		this.tableName = tableName;
		this.modelName = modelName;
		this.isGenBeanOnly = isGenBeanOnly;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @param modelName the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * @return the structureList
	 */
	public List<FieldBean> getStructureList() {
		return structureList;
	}

	/**
	 * @param structureList the structureList to set
	 */
	public void setStructureList(List<FieldBean> structureList) {
		this.structureList = structureList;
	}

	/**
	 * @return the isGenBeanOnly
	 */
	public boolean isGenBeanOnly() {
		return isGenBeanOnly;
	}

	/**
	 * @param isGenBeanOnly the isGenBeanOnly to set
	 */
	public void setGenBeanOnly(boolean isGenBeanOnly) {
		this.isGenBeanOnly = isGenBeanOnly;
	}

	/**
	 * @return the classNameSet
	 */
	public Set<String> getClassNameSet() {
		return classNameSet;
	}

	/**
	 * @param classNameSet the classNameSet to set
	 */
	public void setClassNameSet(Set<String> classNameSet) {
		this.classNameSet = classNameSet;
	}

	/**
	 * @return the tableDesc
	 */
	public String getTableDesc() {
		return tableDesc;
	}
	
	
	
}
