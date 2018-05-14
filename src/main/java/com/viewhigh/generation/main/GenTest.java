/**
 * 版权所属：东软望海科技有限公司
 * 作者：张晓明 
 * 版本：V1.0
 * 创建日期：2018年5月11日
 * 修改日期：2018年5月11日
 */
package com.viewhigh.generation.main;

import com.viewhigh.generation.file.GenMain;

/**
 * @Description  
 * @author zhangxm 
 * @version v1.0
 * @since 2018年5月11日
 */
public class GenTest {
	
	public static void main(String[] args) {
		GenMain gMain = new GenMain("TB_SAAS_DICT", "system",false);
		try {
			gMain.generate();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	

}
