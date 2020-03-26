package com.commonsl.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtil {
	private static String appPath = PropertyUtil.getProperty("appPath");
	private static String imgPath = PropertyUtil.getProperty("imgPath");
	private static String payPicturePath = PropertyUtil.getProperty("payPicturePath");

	public static String getRSAFilePath() {
		return RSAFilePath;
	}

	public static String RSAFilePath =  PropertyUtil.getProperty("RSAFilePath");

	public static String getPayPicturePath() {
		return payPicturePath;
	}

	public static void setPayPicturePath(String payPicturePath) {
		FileUtil.payPicturePath = payPicturePath;
	}

	public static boolean delPayPictureFile( String fileName){
		try {
			File file = new File(payPicturePath+fileName);
			if(file.exists()){
				file.delete();
			}
			return true;
		}catch (Exception e){
			return false;
		}
	}
	public static boolean delImgFile( String fileName){
		try {
			File file = new File(imgPath+fileName);
			if(file.exists()){
				file.delete();
			}
			return true;
		}catch (Exception e){
			return false;
		}
	}

	public static boolean delAppFile( String fileName){
		try {
			File file = new File(appPath+fileName);
			if(file.exists()){
				file.delete();
			}
			return true;
		}catch (Exception e){
			return false;
		}
	}

	public static InputStream getImgInputStream( String fileName){
		try {
			return new FileInputStream(new File(imgPath
					+ fileName));
		}catch (Exception e){
			return null;
		}
	}

	public static InputStream getAppInputStream( String fileName){
		try {
			return new FileInputStream(new File(appPath
					+ fileName));
		}catch (Exception e){
			return null;
		}
	}
	public static InputStream getpayPictureInputStream( String fileName){
		try {
			return new FileInputStream(new File(payPicturePath
					+ fileName));
		}catch (Exception e){
			return null;
		}
	}

	public static InputStream getPublicKeyInputStream( String fileName){
		try {
			return new FileInputStream(new File(payPicturePath
					+ fileName));
		}catch (Exception e){
			return null;
		}
	}

	public static boolean saveImgFile(String fileName ,InputStream inputStream){
		try {
			return saveFile(imgPath+fileName,inputStream);
		}catch (Exception e){
			return false;
		}
	}
	public static boolean savePayPictureFile(String fileName ,InputStream inputStream){
		try {
			return saveFile(payPicturePath+fileName,inputStream);
		}catch (Exception e){
			return false;
		}
	}

	public static boolean saveAppFile(String fileName ,InputStream inputStream){
		try {
			return saveFile(appPath+fileName,inputStream);
		}catch (Exception e){
			return false;
		}
	}

	public static boolean saveClientPublicKeyFile(String filePath ,InputStream inputStream){
		try {
			return saveFile(RSAFilePath+filePath,inputStream);
		}catch (Exception e){
			return false;
		}
	}

	public static boolean saveFile(String path ,MultipartFile file){
		try {
			return saveFile(path,file.getInputStream());
		}catch (Exception e){
			return false;
		}
	}

	public static boolean saveFile(String path, InputStream inputStream) {
		try {
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流保存到本地文件
			File file = new File(path);
			//获取父目录
			File fileParent = file.getParentFile();
			//判断是否存在
			if (!fileParent.exists()) {
				//创建父目录文件
				fileParent.mkdirs();
			}
			FileOutputStream os = new FileOutputStream(file);
			// 开始读取
			while ((len = inputStream.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			 os.close();
             inputStream.close();
             return true;
		} catch (Exception e) {
			 
		}
		return false;
	}

	// 递归方式 计算文件的大小
	public static long getTotalSizeOfFilesInDir(final File file) {
		if (file.isFile())
			return file.length();
		final File[] children = file.listFiles();
		long total = 0;
		if (children != null)
			for (final File child : children)
				total += getTotalSizeOfFilesInDir(child);
		return total;
	}




}
