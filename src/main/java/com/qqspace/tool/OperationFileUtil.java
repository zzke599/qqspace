package com.qqspace.tool;

import java.io.File;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;

/**
 * @author zzke
 * @ClassName
 * @Description 操作文件工具类
 */
public class OperationFileUtil {
	/**
     * 将文件名转变为UUID命名的 ,保留文件后缀
     * 
     * @param filename
     * @return
     */
    public static String changeFilename2UUID(String filename) {
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + FilenameUtils.getExtension(filename);
    }

	
	/**
	 * 删除文件
	 * 
	 * @param filePath
	 */
	public static void deleteFile(String filePath) {
		try {
			File file = new File(filePath);
			if (file.exists() && file.isFile()) {
				file.delete();
				System.out.println("删除成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("删除成功！");
		}
	}
}
