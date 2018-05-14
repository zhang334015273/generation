/**
 * 版权所属：东软望海科技有限公司
 * 作者：张晓明 
 * 版本：V1.0
 * 创建日期：2018年5月11日
 * 修改日期：2018年5月11日
 */
package com.viewhigh.generation.bean;

/**
 * @Description  
 * @author zhangxm 
 * @version v1.0
 * @since 2018年5月11日
 */
@SuppressWarnings("rawtypes")
public class FieldBean {
	
	private String columnName;
	private Class vhClass;
	private String fieldName;
	private String fieldNameUp;
	private String fieldContent;
	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * @return the vhClass
	 */
	public Class getVhClass() {
		return vhClass;
	}
	/**
	 * @param vhClass the vhClass to set
	 */
	public void setVhClass(Class vhClass) {
		this.vhClass = vhClass;
	}
	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * @return the fieldContent
	 */
	public String getFieldContent() {
		return fieldContent;
	}
	/**
	 * @param fieldContent the fieldContent to set
	 */
	public void setFieldContent(String fieldContent) {
		this.fieldContent = fieldContent;
	}
	/**
	 * @return the fieldNameUp
	 */
	public String getFieldNameUp() {
		return fieldNameUp;
	}
	/**
	 * @param fieldNameUp the fieldNameUp to set
	 */
	public void setFieldNameUp(String fieldNameUp) {
		this.fieldNameUp = fieldNameUp;
	}
	

}
