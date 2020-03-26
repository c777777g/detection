package com.commonsl.util;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import java.util.Date;
import java.util.UUID;

public class CommonUtil {


	public static Integer createIntCode() {
		int i = RandomUtils.nextInt(100000000);
		if (i < 10000000) {
			i += 10000000;
		}
		return i;
	}

	/** 获取一个?位数字的随机码 */
	public static String createRandomNumeric(int count) {
		return RandomStringUtils.randomNumeric(count);
	}

	/***
	 * 
	 * @Title: CommonUtil.java
	 * @Package commons.lander.util
	 * @Description: 把List转为Map
	 * @author chenwenhao
	 * @date 2016-12-10 上午9:48:54
	 */
	/*
	 * public static Map<Serializable,Entity> transformToMap(List<? extends
	 * Entity> list){ Map<Serializable,Entity> map = null; if(list!=null &&
	 * !list.isEmpty()){ map = new HashMap<Serializable, Entity>(); for(Entity
	 * ent : list){ map.put(ent.getId(), ent); } } return map; }
	 */

	/**
	 * 
	 * @Title: CommonUtil.java
	 * @Package commons.lander.util
	 * @Description: 创建用户token值 token = userCode+当前时间戳+uuid随机码
	 * @author chenwenhao
	 * @date 2016-12-1 上午9:39:21
	 */
//	public static String createToken(Integer userCode) {
//		StringBuilder bulider = new StringBuilder("");
//		bulider.append(userCode);
//		bulider.append(";");
//		bulider.append(System.currentTimeMillis());
//		bulider.append(";");
//		bulider.append(UUID.randomUUID().toString());
//		return RSAUtils.encrypt(bulider.toString(), true);
//	}

	public static void main(String[] args) {
		// System.out.println(createRandomNumeric(6));
		// String s = UUID.randomUUID().toString();
		// System.out.println(s);
		// s = UUID.randomUUID().toString();
		// System.out.println(s);
		//
		// System.out.println(createToken(18436399));
//		for (int i = 0; i < 100; i++) {
//			System.out.println(createPayNum());
//		}
		System.out.println(createRandomNumeric(11));
		System.out.println(createOrderNum());
		System.out.println(createPayNum());
		System.out.println(createIntCode());

	}

	/**
	 * 生成订单号 当前年月日 + 随机9位数
	 * 
	 * @return
	 */
	public static String createOrderNum() {
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if (hashCodeV < 0) {// 有可能是负数
			hashCodeV = -hashCodeV;
		}
		return getFromAToZ(4)+String.valueOf(hashCodeV);
	}

	public static String createPayNum(){
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if (hashCodeV < 0) {// 有可能是负数
			hashCodeV = -hashCodeV;
		}
		return getFromAToZ(4)+new Date().getTime()+String.valueOf(hashCodeV);
	}
	public static String getFromAToZ(int num){
		StringBuilder  sb = new StringBuilder();
		for (int i = 0; i < num; i++) {
			sb.append((char)(int)(Math.random()*26+97));
		}
		return sb.toString().toUpperCase();
	}
}
