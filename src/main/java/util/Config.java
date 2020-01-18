package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.locks.ReentrantLock;

public class Config {
	private static String RPath_Config = "config/config.json";

	private static JSONObject _Config = new JSONObject();

	private static ReentrantLock lock = new ReentrantLock(false);

	static {
		try {
			ReadConfig();
		} catch (Exception e) {
			
		}
	}

	public static void ReadConfig() {
		lock.lock();
		try {
			ToolUtil.CreateFileIfNotExists(RPath_Config);

			String tmp = ToolUtil.ReadInTxt(RPath_Config);

			if (tmp == null || tmp.equals("")) {
				return;
			}

			_Config = JSON.parseObject(tmp);
		} catch (Exception e) {
			
		} finally {
			lock.unlock();
		}
	}

	public static String GetVal(Object key, String defVal) {
		lock.lock();
		try {
			if (_Config.containsKey(key)) {
				return _Config.get(key).toString();
			}
			return defVal;
		} catch (Exception e) {
			
			return defVal;
		} finally {
			lock.unlock();
		}
	}
}
