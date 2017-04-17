package org.webservice.common;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.uws.core.util.MD5;

public class Test {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub

		String xn = "2015";
		String xq_m = "0";
		String xsxh = "2015010127";
		String time = "201609041840";
		//String sign = "AF4840D95A4B6E2EC11E3E6E65B3589A";
		String signkey = MD5.crypt("2015#0#2015010127#HZKJ201613026#201609041840").toUpperCase();
		System.out.println(signkey);
		WsScore ws = new WsScore();
		WsScoreSoap wss = ws.getWsScoreSoap();
		//System.out.println("11");
		
		ArrayOfString ret;
		ret = wss.getStudentAverageScore(xn, xq_m, xsxh, time, signkey);
		List<String> p = ret.getString();
		if( p != null){
			for(String str : p){
				System.out.println("===:"+str);
			}
		}
		
		//System.out.println((MD5.crypt("2006#0#06011003#HZKJ201613026#201609041840")).toUpperCase());
		//System.out.println("AF4840D95A4B6E2EC11E3E6E65B3589A");
		
		//System.out.println(MD5.crypt("2015#0#2015305101#HZKJ201613026#201609061512").toUpperCase());
		
	}

}
