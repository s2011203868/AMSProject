package com.purple.ams.ssm.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import com.purple.ams.ssm.util.FunctionUtil;
import com.purple.ams.ssm.util.MacAddressApi;

public class TestJunt {

	@Test
	public void testObjectStream() throws IOException{
		FileOutputStream filestream = new FileOutputStream("d://machineCode.license");
		 ObjectOutputStream os = new ObjectOutputStream(filestream);
		 os.writeObject("qwertyuiopasdfghjkl");
	}
	
	@Test
	public void testTime() throws ParseException{
	    long time1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-09-10 09:10:00").getTime();
	    long time2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-08-10 09:10:00").getTime();
	
	    System.out.println("time1:"+time1);
	    System.out.println("time2:"+time2);
	}
	
	@Test
	public void testgenImageName(){
	   System.out.println(FunctionUtil.genImageName());
	}
	
	@Test
    public void testgetItemid(){
       System.out.println(FunctionUtil.genItemId());
    }
	
	@Test
	public void testGetLocalIp() throws Exception{
		InetAddress ip = MacAddressApi.getLocalHostLANAddress();
		//InetAddress addr = InetAddress.getLocalHost();  
        //String ip=addr.getHostAddress().toString();
		System.out.println(ip);
	}
	
}
