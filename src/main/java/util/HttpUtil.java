package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtil {
	static {
		try {
			SSLUtil.IgnoreSSL();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private CookieManager CKManager = new CookieManager();

	private String _Get(String urlStr, HashMap<String, String> query) {
		try {
			if (query != null) {
				urlStr += "?";
				urlStr += ToolUtil.HashToStr(query);
				// System.out.println(urlStr);
			}

			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");
			conn.setConnectTimeout(30000);// 设置连接主机服务器的超时时间
			conn.setReadTimeout(60000);// 设置读取远程返回的数据时间
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36");

			// redirect到Location的地址
			conn.setInstanceFollowRedirects(false);

			// region cookie manage

			Map<String, List<String>> cookies1 = CKManager.get(new URI(urlStr), conn.getRequestProperties());
			String t1 = ToolUtil.CookieToStr2(cookies1.get("Cookie"));

			conn.setRequestProperty("Cookie", t1);

			// endregion



			conn.connect();// get



			CKManager.put(new URI(urlStr), conn.getHeaderFields());



			if (conn.getResponseCode() == 302) {
				String location = conn.getHeaderField("Location");
				return _Get(location, null);
			}

			String ret = null;

			if (conn.getResponseCode() == 200) {
				InputStream inputStream = conn.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				StringBuffer sbf = new StringBuffer();
				String temp = null;
				while ((temp = bufferedReader.readLine()) != null) {
					sbf.append(temp + ToolUtil.LineSeparator);
				}
				ret = sbf.toString();
				bufferedReader.close();
				inputStream.close();
			}

			conn.disconnect();

			return ret;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	private String _Get(String urlStr, HashMap<String, String> query, boolean queryEncode) {
		try {
			if (query != null) {
				urlStr += "?";
				urlStr += ToolUtil.HashToStr(query, queryEncode);
				System.out.println(urlStr);
			}

			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");
			conn.setConnectTimeout(30000);// 设置连接主机服务器的超时时间
			conn.setReadTimeout(60000);// 设置读取远程返回的数据时间
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36");

			// redirect到Location的地址
			conn.setInstanceFollowRedirects(false);

			// region cookie manage

			Map<String, List<String>> cookies1 = CKManager.get(new URI(urlStr), conn.getRequestProperties());
			String t1 = ToolUtil.CookieToStr2(cookies1.get("Cookie"));

			conn.setRequestProperty("Cookie", t1);

			// endregion



			conn.connect();// get



			CKManager.put(new URI(urlStr), conn.getHeaderFields());



			if (conn.getResponseCode() == 302) {
				String location = conn.getHeaderField("Location");
				return _Get(location, null);
			}

			String ret = null;

			if (conn.getResponseCode() == 200) {
				InputStream inputStream = conn.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				StringBuffer sbf = new StringBuffer();
				String temp = null;
				while ((temp = bufferedReader.readLine()) != null) {
					sbf.append(temp + ToolUtil.LineSeparator);
				}
				ret = sbf.toString();
				bufferedReader.close();
				inputStream.close();
			}

			conn.disconnect();

			return ret;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	/**
	 * HTTP请求，注意：需在外部实现编码，该方法允许自动重定向
	 * 
	 * @param urlStr 请求的网址
	 * @param query  get请求的参数，键值对格式
	 * @param cookie get请求携带的cookie
	 */
	public String Get(String urlStr, HashMap<String, String> query) {
		String rsp = _Get(urlStr, query);
		if (rsp == null || rsp == "") {
			rsp = _Get(urlStr, query);
		}
		if (rsp == null || rsp == "") {
			System.out.println("HttpGet: rsp is null.");
		}
		return rsp;
	}

	public String Get(String urlStr, HashMap<String, String> query, boolean queryEncode) {
		try {
			if (queryEncode) {
				return _Get(urlStr, query, queryEncode);
			}

			return Get(urlStr, query);
		} catch (Exception e) {
			return null;
		}
	}

	private String _Post(String urlStr, int contentType, String content) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setConnectTimeout(30000);// 设置连接主机服务器的超时时间
			conn.setReadTimeout(60000);// 设置读取远程返回的数据时间
//			conn.setRequestProperty("User-Agent",
//					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36");
			conn.setRequestProperty("User-Agent"," Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
			conn.setDoInput(true);

			switch (contentType) {
			case 0:
				conn.setRequestProperty("Content-Type", "application/json");
				break;
			case 1:
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				break;
			default:
				System.out.println("HttpPost: contentType参数错误...");
				return null;
			}

			// redirect到Location的地址
			conn.setInstanceFollowRedirects(false);

			// region cookie manage

			Map<String, List<String>> cookies1 = CKManager.get(new URI(urlStr), conn.getRequestProperties());
			String t1 = ToolUtil.CookieToStr2(cookies1.get("Cookie"));

			conn.setRequestProperty("Cookie", t1);

			// endregion

//			Object settings_request = conn.getRequestProperties();

			if (content != null && content != "") {
				conn.setDoOutput(true);
				conn.getOutputStream().write(content.getBytes("UTF-8"));
			}

			conn.connect();// post



			CKManager.put(new URI(urlStr), conn.getHeaderFields());



			// endregion

			if (conn.getResponseCode() == 302) {
				String location = conn.getHeaderField("Location");
				return _Get(location, null);
			}

			String ret = null;

			if (conn.getResponseCode() == 200) {
				InputStream inputStream = conn.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				StringBuffer sbf = new StringBuffer();
				String temp = null;
				while ((temp = bufferedReader.readLine()) != null) {
					sbf.append(temp + ToolUtil.LineSeparator);
				}
				ret = sbf.toString();
				bufferedReader.close();
				inputStream.close();
			}

			conn.disconnect();

			return ret;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

	/**
	 * HTTP请求，注意：需在外部实现编码，该方法允许自动重定向
	 * 
	 * @param urlStr      请求的网址
	 * @param contentType 0="application/json",1="application/x-www-form-urlencoded"
	 * @param content     内容
	 * @param cookie      请求携带的cookie
	 */
	public String Post(String urlStr, int contentType, String content) {
		String rsp = _Post(urlStr, contentType, content);
		if (rsp == null || rsp == "") {
			rsp = _Post(urlStr, contentType, content);
		}
		if (rsp == null || rsp == "") {
			System.out.println("HttpPost: rsp is null.");
		}
		return rsp;
	}

}
