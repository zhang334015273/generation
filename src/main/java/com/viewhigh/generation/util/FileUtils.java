/**
 * 版权所属：东软望海科技有限公司
 * 作者：张晓明 
 * 版本：V1.0
 * 创建日期：2018年5月11日
 * 修改日期：2018年5月11日
 */
package com.viewhigh.generation.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

/**
 * @Description  
 * @author zhangxm 
 * @version v1.0
 * @since 2018年5月11日
 */
public class FileUtils {
	
	 public static void WriteStringToFile(File file,String content) {
        try {
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.println(content);// 往文件里写入字符串
            ps.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
     }
	 
	 public static void changeFileEncoding(File file,String encoding){
		 try {
			 OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file, true), encoding);
			 osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	 }

}
