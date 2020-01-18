package common;

import cn.wanghaomiao.xpath.model.JXDocument;
import util.HttpUtil;
import util.ToolUtil;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.regex.Pattern;

@SuppressWarnings("deprecation")
public class Portal {
	private static String Uri_Login = "https://10.4.188.1/cas/login?service=https://10.4.188.1/portal/a/cas";
	private static String Uri_Login2 = "https://10.4.188.1/portal/a/substitutionorg/8524";


	/**
	 * load 'lt' and 'execution',to prepare for login
	 * 
	 * @return
	 */
	public static HashMap<String, String> Get_Lt_Execution(HttpUtil httpUtil) {
		try {
			String rsp = httpUtil.Get(Uri_Login, null);

			if (rsp == null || rsp == "") {
				System.out.println("Get_Lt_Execution: rsp is null.");
				return null;
			}

			JXDocument doc = new JXDocument(rsp);
			String lt = doc.selOne("//*[@id='login-from']/div[6]/input[1]/@value").toString();
			String execution = doc.selOne("//*[@id='login-from']/div[6]/input[2]/@value").toString();

			if (lt == null || lt == "" || execution == null || execution == "") {
				System.out.println("'lt' or 'execution' is null.");
				return null;
			}

			HashMap<String, String> ret = new HashMap<String, String>();
			ret.put("lt", lt);
			ret.put("execution", execution);


			return ret;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	/**
	 * 实际的登录请求
	 * 
	 * @param args username,password,lt,execution,substitutionOrg
	 * @return
	 */
	public static boolean Post_LoginInfo(HttpUtil httpUtil, HashMap<String, String> args) {
		try {
			// region 密码加密
			Object ret_js = ToolUtil.CallJSFunc("config/jdpt_web_aes.js", "third_encrypt", new Object[] { args.get("password") });
			if (ret_js == null) {
				return false;
			}
			args.put("password", ret_js.toString());
			// endregion

			// region 登录

			HashMap<String, String> formData = new HashMap<String, String>();
			formData.put("username", URLEncoder.encode(args.get("username"), "UTF-8"));
			formData.put("password", URLEncoder.encode(args.get("password"), "UTF-8"));
			formData.put("lt", URLEncoder.encode(args.get("lt"), "UTF-8"));
			formData.put("execution", URLEncoder.encode(args.get("execution"), "UTF-8"));
			formData.put("_eventId", "submit");

			String content = ToolUtil.HashToStr(formData);

			String rsp = httpUtil.Post(Uri_Login, 1, content);

			// endregion		

			// region 判断是否到达主界面，并提取“机构简称”，“登录人”，“机构代码”

			String regex = "<title>中国邮政寄递平台</title>";

			if (!Pattern.compile(regex, Pattern.DOTALL).matcher(rsp).find()) {
				String get = httpUtil.Get(Uri_Login2, null);
				if (get == null || get == "") {
					System.out.println("Get_Lt_Execution: get is null.");
					return false;
				}

			}

			return true;

			// endregion
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
}
