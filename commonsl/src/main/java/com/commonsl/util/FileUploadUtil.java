package com.commonsl.util;

import java.io.IOException;
import java.util.UUID;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import org.springframework.beans.factory.annotation.Value;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.UrlSafeBase64;

public class FileUploadUtil {
	public static FileUploadUtil fileUpload;
	private static String accessKey = PropertyUtil.getProperty("qiniu_accessKey");
	private static String secretKey = PropertyUtil.getProperty("qiniu_secretKey");
	private static String bucket = PropertyUtil.getProperty("qiniu_bucketname");
	private static String qiniu_fileDomain = PropertyUtil.getProperty("qiniu_fileDomain");
	private static String upToken;
	private static UploadManager uploadManager;
	private static OkHttpClient client;
	private static Auth auth;
	static {
		fileUpload = new FileUploadUtil();
		 auth =  Auth.create(accessKey, secretKey);
		Configuration cfg = new Configuration(Zone.zone2());
		uploadManager = new UploadManager(cfg);
		client = new OkHttpClient();
	}
	
	/**
	 * 根据key获取图片Urls
	 * @param key 图片key
	 * @return 图片urls
	 */
	public static String[] getFileUrls(String key) {
		String[] urls = new String[3];
		urls[0] = qiniu_fileDomain + key + "?imageView2/0/h/64";
		urls[1] = qiniu_fileDomain + key + "?imageView2/0/h/300";
		urls[2] = qiniu_fileDomain + key + "?imageView2/0/h/600";
		return urls;
	}

	public static String getFileUrl(String key){
		return qiniu_fileDomain + key;
	}
	/**
	 * 上传文件
	 * @param byteArr 文件数组
	 * @return key 文件key
	 */
	public static String uploadFile(byte[] byteArr) {
		try {
			upToken = auth.uploadToken(bucket);
			Response response = uploadManager.put(byteArr, UUID.randomUUID().toString(), upToken);
			// 解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),
					DefaultPutRet.class);

			return putRet.key;
		} catch (QiniuException ex) {
			Response r = ex.response;
			System.err.println(r.toString());
			try {
				System.err.println(r.bodyString());
			} catch (QiniuException ex2) {
				// ignore
			}
		}
		return null;
	}

	/**
	 * 删除文件
	 * 
	 * @param key
	 *            文件key
	 * @return
	 */
	public static boolean deleteFile(String key) {
		try {
			 //指定需要删除的空间和文件，格式为： <bucket>:<key>
	        String entry = bucket+":"+key;
	        //通过安全base64编码方式进行编码处理
	        String encodedEntryURI = UrlSafeBase64.encodeToString(entry);
	        //指定接口
	        String target = "/delete/" + encodedEntryURI + "\n";
	        //获取token，即操作凭证
	        String access_token = auth.sign(target);
	        //指定好请求的delete接口地址
	        String url = "http://rs.qiniu.com/delete/" + encodedEntryURI;
	        //通过Okhttp jar 包封装的对象 发起网络请求
	        RequestBody body = new FormBody.Builder().build();
			Request request = new Request.Builder().url(url).post(body)
					  .addHeader("Content-Type", "application/x-www-form-urlencoded")
					   .addHeader("Authorization", "QBox " + access_token)
					.build();
			okhttp3.Response response;
				response = client.newCall(request).execute();
				System.out.println(response.body().string());
				System.out.println(response.isSuccessful());
				return response.isSuccessful();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
	}
	
	public static void main(String[] args) {
		try {
		 //指定需要删除的空间和文件，格式为： <bucket>:<key>
        String entry = bucket+":"+"Fhz0zFFkavpmKPlJhinRKMrJrh26";
        //通过安全base64编码方式进行编码处理
        String encodedEntryURI = UrlSafeBase64.encodeToString(entry);
        //指定接口
        String target = "/delete/" + encodedEntryURI + "\n";
        //获取token，即操作凭证
        String access_token = auth.sign(target);
        //指定好请求的delete接口地址
        String url = "http://rs.qiniu.com/delete/" + encodedEntryURI;
        //通过Okhttp jar 包封装的对象 发起网络请求
        RequestBody body = new FormBody.Builder().build();
		Request request = new Request.Builder().url(url).post(body)
				  .addHeader("Content-Type", "application/x-www-form-urlencoded")
				   .addHeader("Authorization", "QBox " + access_token)
				.build();
		okhttp3.Response response;
			response = client.newCall(request).execute();
			System.out.println(response.body().string());
			System.out.println(response.isSuccessful());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
