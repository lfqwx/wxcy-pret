package util;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.*;
import java.net.HttpCookie;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

public class ToolUtil {
	/**
	 * 工作空间目录，调试时项目根目录、打包后jar包所在目录
	 */
	public static String Dir_User = System.getProperty("user.dir");

	/**
	 * 换行符
	 */
	public static String LineSeparator = System.getProperty("line.separator");

	/**
	 * get current time，格式“yyyy-MM-dd HH:mm:ss”
	 * 
	 * @param format
	 * @return
	 */
	public static String CurrTime(String format) {
		if (format == null || format == "") {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(new java.util.Date());
	}

	/**
	 * get random string
	 * 
	 * @param length
	 * @return
	 */
	public static String GetRandomStr(int length) {
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(str.length());
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 以文本方式读取文件，从当前工作空间的相对路径读取文件，例如：“config/1.txt”
	 * 
	 * @param relativePathName
	 * @return
	 */
	public static String ReadInTxt(String relativePathName) {
		try {
			// 以行为单位读取文件内容，一次读一整行
			String path = Dir_User + "/" + relativePathName;

			path = path.replace("\\", "/");

			File file = new File(path);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			StringBuilder ret = new StringBuilder();
			while ((tempString = reader.readLine()) != null) {
				ret.append(tempString + LineSeparator);
			}
			reader.close();

			return ret.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 写入内容到指定文件，例如log/log.txt
	 * 
	 * @param relativePathName
	 * @param content
	 * @param append           true=追加，false=覆盖
	 */
	public static void WriteInTxt(String relativePathName, String content, boolean append) {
		try {
			String fileName = Dir_User + "/" + relativePathName;
			fileName = fileName.replace("\\", "/");
			String filePath = fileName.substring(0, fileName.lastIndexOf("/"));

			File tmp = new File(filePath);
			if (!tmp.exists()) {
				tmp.mkdirs();
			}

			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fileWriter = new FileWriter(file, append);

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(content);

			bufferedWriter.write(LineSeparator);

			bufferedWriter.close();

			fileWriter.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * 写入内容到指定文件，例如log/log.txt，默认覆盖
	 * 
	 * @param relativePathName
	 * @param content
	 */
	public static void WriteInTxt(String relativePathName, String content) {
		WriteInTxt(relativePathName, content, false);
	}

	public static void CreateFileIfNotExists(String relativePathName) {
		try {
			String fileName = ToolUtil.Dir_User + "/" + relativePathName;
			fileName = fileName.replace("\\", "/");
			String filePath = fileName.substring(0, fileName.lastIndexOf("/"));

			File file_filePath = new File(filePath);
			if (!file_filePath.exists()) {
				file_filePath.mkdirs();
			}

			File file_fileName = new File(fileName);
			if (!file_fileName.exists()) {
				file_fileName.createNewFile();
			}
		} catch (Exception e) {
			
		}
	}

	/**
	 * 调用js文件并执行函数
	 * 
	 * @param relativePathName
	 * @param funcName
	 * @param args
	 * @return
	 */
	public static Object CallJSFunc(String relativePathName, String funcName, Object[] args) {
		try {
			String jsFile = ReadInTxt(relativePathName);

			ScriptEngine scriptEngine = (new ScriptEngineManager()).getEngineByName("javascript");
			scriptEngine.eval(jsFile);

			Object ret = null;

			if (scriptEngine instanceof Invocable) {
				ret = ((Invocable) scriptEngine).invokeFunction(funcName, args);
			}

			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String HashToStr(HashMap<String, String> hashMap) {
		try {
			String ret = "";

			Iterator<Entry<String, String>> iter = hashMap.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, String> entry = iter.next();
				String key = entry.getKey();
				String val = entry.getValue();
				ret += (key + "=" + val + "&");
			}

			return ret.substring(0, ret.length() - 1);
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	public static String HashToStr(HashMap<String, String> hashMap, boolean encode) {
		try {
			if (encode) {
				String ret = "";

				Iterator<Entry<String, String>> iter = hashMap.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<String, String> entry = iter.next();
					String key = entry.getKey();
					String val = URLEncoder.encode(entry.getValue(), "UTF-8");
					ret += (key + "=" + val + "&");
				}

				return ret.substring(0, ret.length() - 1);
			}

			return HashToStr(hashMap);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 修改模板参数的部分值为用户值，常用语HTTP请求参数构造
	 * 
	 * @param argsDefault
	 * @param args
	 * @return
	 */
	public static HashMap<String, String> FillArgs(HashMap<String, String> argsDefault, HashMap<String, String> args) {
		try {
			if (argsDefault == null) {
				return null;
			}
			if (args == null) {
				return null;
			}

			HashMap<String, String> ret = new HashMap<String, String>();

			Iterator<Entry<String, String>> iter = argsDefault.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, String> entry = iter.next();
				String key = entry.getKey();
				String val = entry.getValue();
				ret.put(key, val);
			}
			// -------------------------------------------------------------------
			iter = args.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, String> entry = iter.next();
				String key = entry.getKey();
				String val = entry.getValue();
				if (ret.containsKey(key)) {
					ret.put(key, val);
				}
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String CookieToStr(List<HttpCookie> cookies) {
		try {
			if (cookies == null || cookies.size() < 1) {
				return "";
			}

			String ret = "";

			for (HttpCookie cookie : cookies) {
				ret += cookie.getName() + "=" + cookie.getValue() + ";";
			}

			return ret.substring(0, ret.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String CookieToStr2(List<String> cookies) {
		try {
			if (cookies == null || cookies.size() < 1) {
				return "";
			}

			String ret = "";

			for (String cookie : cookies) {
				ret += cookie + ";";
			}

			return ret.substring(0, ret.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void StartOnlineThread() {
		while (true) {
			try {
				Thread.sleep(60 * 1000);
				Config.ReadConfig();
			} catch (InterruptedException e1) {
				break;
			} catch (Exception e) {
				
				break;
			}
		}
	}

	public static HashMap<String, String> MapClone(HashMap<String, String> map) {
		HashMap<String, String> ret = new HashMap<String, String>();
		Iterator<Entry<String, String>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			ret.put(key, val);
		}
		return ret;
	}

}
