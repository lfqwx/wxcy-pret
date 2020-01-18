package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;


public class DataBeanConfig {
	public static String databaseDriver;
	public static String databaseUrl;
	public static String username;
	public static String password;
	static {
		Properties p = new Properties();
		String path=DataBeanConfig.class.getResource("/config.properties").toString();
		InputStream in = DataBeanConfig.class.getResourceAsStream("/config.properties");
		try {
			p.load(in);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		databaseDriver=p.getProperty("databaseDriver").trim();
		databaseUrl=p.getProperty("networkDatabaseUrl").trim();//localDatabaseUrl��networkDatabaseUrl
		username=p.getProperty("username").trim();
		password=p.getProperty("password").trim();

	}


	/**
	 * 生成随机字符串
	 * */
	public static String getRandomString(int length) {
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	}   
	
//	public static void main(String[] args) {
//		System.out.print(Config.filePath);
//		//System.out.println(System.getProperties().getProperty("CATALINA_HOME"));
//	}
}
