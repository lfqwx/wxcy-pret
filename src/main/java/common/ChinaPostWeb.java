package common;

import cn.wanghaomiao.xpath.model.JXDocument;
import util.HttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("deprecation")
public class ChinaPostWeb {
	/**
	 * 已经完成跳转的子系统
	 */
	private ArrayList<String> LoginedSubSystem = new ArrayList<String>();
	public HttpUtil MHttpUtil = new HttpUtil();
	private List<Map<String, String>> SubstitutionOrg = new ArrayList<Map<String, String>>();

	public String UserName = "";
	public String PassWord = "";
	public String OrgCode = "";
	public String OrgName = "";
	public String User = "";// 登录人

	public ChinaPostWeb(String userName, String passWord) {
		UserName = userName;
		PassWord = passWord;
	}

	/**
	 * 选择替班机构、登录，默认登录第一个替班机构
	 * @param substitutionOrg 替班机构代码
	 * @return
	 */
	public boolean Login(String substitutionOrg) {
		try {
			HashMap<String, String> loginInfo = Portal.Get_Lt_Execution(MHttpUtil);
			loginInfo.put("username", UserName);
			loginInfo.put("password", PassWord);
			loginInfo.put("substitutionOrg", substitutionOrg);

			boolean fn = Portal.Post_LoginInfo(MHttpUtil, loginInfo);

			if (!fn ) {
				return false;
			}


			LoginedSubSystem = new ArrayList<String>();		

			return true;
		} catch (Exception e) {			
			return false;
		}
	}
	

	public boolean LoginSubSystem(String urlSubSystem) {
		try {
			if (LoginedSubSystem.contains(urlSubSystem)) {
				return true;
			}

			String rsp = MHttpUtil.Get(urlSubSystem, null);
			JXDocument doc = new JXDocument(rsp);
			String title = doc.selOne("//title/text()").toString();
			if (title.equals("欢迎使用中国邮政新一代寄递平台")) {
				throw new Exception();
			}
			LoginedSubSystem.add(urlSubSystem);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	

	

	

	

}
