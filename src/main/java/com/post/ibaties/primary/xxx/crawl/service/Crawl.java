package com.post.ibaties.primary.xxx.crawl.service;

import cn.wanghaomiao.xpath.model.JXDocument;
import cn.wanghaomiao.xpath.model.JXNode;
import com.post.ibaties.primary.xxx.crawl.entity.BkData;
import common.ChinaPostWeb;
import org.springframework.stereotype.Service;
import util.HttpUtil;
import util.ToolUtil;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: 𝓛.𝓕.𝓠
 * mvn clean package -Dmaven.test.skip=true
 */
@Service
public class Crawl {
    public final static String PAGE = "https://10.4.188.1/report-web/a/report/ltgl/orgCountReceiveProduct/result";

    //除华为
    public static Map getData() {
        ChinaPostWeb web = new ChinaPostWeb("43001800a", "xyd123456");
        HttpUtil MHttpUtil = web.MHttpUtil;
        Map map = null;
        boolean login = web.Login("43001800");
        if (login) {
            System.out.println("登陆成功");
            try {
                //爬虫代码
                MHttpUtil.Get("https://10.4.188.1/report-web/a/report/ltgl/orgCountReceiveProduct/list", null);
                map = crawl(MHttpUtil);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("登录失败！");
        }
        return map;
    }
    //华为
    public static BkData getHuawei(){
        ChinaPostWeb web = new ChinaPostWeb("43004018admin", "xyd123654");
        HttpUtil MHttpUtil = web.MHttpUtil;
        BkData bkData = null;
        boolean login = web.Login("43004018");
        if (login) {
            try {
                //爬虫代码
                MHttpUtil.Get("https://10.4.188.1/report-web/a/report/ltgl/orgCountReceiveProduct/list", null);
                bkData = crawlHuawei(MHttpUtil);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("登录失败！");
        }
        return bkData;
    }
    public static BkData crawlHuawei(HttpUtil MHttpUtil) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Date end = c.getTime();
        String dqrq = format.format(end);//当前日期
        c.add(Calendar.DATE, -1);
        Date start = c.getTime();
        String time = format.format(start);
        HashMap<String, String> map = new HashMap<String, String>();
        BkData bkData = new BkData();
        map.put("orgRemark", "B");
        map.put("provinceOrgCode", "43000600");
        map.put("cityOrgCode", "43004018");
        map.put("tjrqStart", time);
        map.put("tjrqEnd", time);
        map.put("baseProductId", "180");
        map.put("jccp", "11210");
        map.put("jccpName", "180*11210");
        map.put("tjzl", "1");
        map.put("isjccp", "2");
        map.put("senderType", "2");
        map.put("mailType", "5");
        map.put("bblx", "1");
        map.put("ywldw", "1");
        map.put("statisticsDateStart", time.replace("-", ""));
        map.put("statisticsDateEnd", time.replace("-", ""));
        String res = MHttpUtil.Post(PAGE, 1, ToolUtil.HashToStr(map));
        //System.out.println(res);
        //第一个页面数据
        JXDocument jx = new JXDocument(res);
        //System.out.println(tbody);
        String s1 = jx.selOne("//tbody/tr/td[8]").toString();
        String s2 = jx.selOne("//tbody/tr/td[9]").toString();
        String s3 = jx.selOne("//tbody/tr/td[11]").toString();
        String s4 = jx.selOne("//tbody/tr/td[12]").toString();

        String ryjl = s1.replace("<td class=\"day\">", "").replace("</td>", "").replace(",", "").trim();
        String rsr = s2.replace("<td class=\"day\">", "").replace("</td>", "").replace(",", "").trim();
        String yyjl = s3.replace("<td class=\"month\">", "").replace("</td>", "").replace(",", "").trim();
        String ysr = s4.replace("<td class=\"month\">", "").replace("</td>", "").trim();
        //System.out.println("ryjl:"+ryjl+",rsr:"+rsr+",yyjl:"+yyjl+",ysr:"+ysr);
        bkData.setJgName("华为项目");
        bkData.setRyjl(Integer.valueOf(ryjl));
        bkData.setRsr(Double.valueOf(rsr));
        bkData.setYyjl(Integer.valueOf(yyjl));
        bkData.setYsr(Double.valueOf(ysr));

        return bkData;
    }
    public static Map crawl(HttpUtil MHttpUtil) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Date end = c.getTime();
        String dqrq = format.format(end);//当前日期
        c.add(Calendar.DATE, -1);
        Date start = c.getTime();
        String time = format.format(start);
        HashMap<String, String> map = new HashMap<String, String>();
        Map<String, BkData> map2 = new HashMap<String, BkData>();
        map.put("orgRemark", "B");
        map.put("provinceOrgCode", "43000600");
        map.put("cityOrgCode", "43001800");
        map.put("tjrqStart", time);
        map.put("tjrqEnd", time);
        map.put("baseProductId", "180");
        map.put("jccp", "11210");
        map.put("jccpName", "180*11210");
        map.put("tjzl", "1");
        map.put("isjccp", "2");
        map.put("senderType", "2");
        map.put("mailType", "5");
        map.put("bblx", "1");
        map.put("ywldw", "1");
        map.put("statisticsDateStart", time.replace("-", ""));
        map.put("statisticsDateEnd", time.replace("-", ""));
        String res = MHttpUtil.Post(PAGE, 1, ToolUtil.HashToStr(map));
        //System.out.println(res);
        //第一个页面数据
        JXDocument jx = new JXDocument(res);
        String tbody = jx.selOne("//tbody").toString();
        //System.out.println(tbody);
        List<JXNode> xzjg = jx.selN("//tbody/tr/td[5]");
        List<JXNode> xh = jx.selN("//tbody/tr/td[2]");
        List<JXNode> jg = jx.selN("//tbody/tr/td[7]");
        List<JXNode> ryjl = jx.selN("//tbody/tr/td[8]");
        List<JXNode> rsr = jx.selN("//tbody/tr/td[9]");
        List<JXNode> yyjl = jx.selN("//tbody/tr/td[11]");
        List<JXNode> ysr = jx.selN("//tbody/tr/td[12]");
        for (int i = 0; i < jg.size(); i++) {
            String trim = xzjg.get(i).toString().replace("<td>", "").replace("</td>", "").trim();
            //System.out.println("trim:" + trim + ",长度" + trim.length());
            if ((trim.length() <= 3 && trim.length() != 0)|| trim.length() == 4) continue;
            String s0 = xh.get(i).toString().replace("<td>", "").replace("</td>", "").trim();
            String s1 = jg.get(i).toString().replace("<td>", "").replace("</td>", "")
                    .replace("\"", "").trim();
            String s2 = ryjl.get(i).toString().replace("<td class=\"day\">", "").replace("</td>", "").replace(",", "").trim();
            String s3 = rsr.get(i).toString().replace("<td class=\"day\">", "").replace("</td>", "").trim();
            String s4 = yyjl.get(i).toString().replace("<td class=\"month\">", "").replace("</td>", "").replace(",", "").trim();
            String s5 = ysr.get(i).toString().replace("<td class=\"month\">", "").replace("</td>", "").trim();

            //System.out.println(s0 + "," + s1 + "," + s2 + "," + s3 + "," + s4 + "," + s5);
            BkData bkData = new BkData(s1, Integer.valueOf(s2), Integer.valueOf(s4), Double.valueOf(s3), Double.valueOf(s5));
            map2.put(s1, bkData);
        }
        return map2;
    }
    //TODO

    /**
     * Regex Function,取得url
     */
    public static String[] getUrl(String content) {
        String[] urls = new String[0];
        String regex = "href=\"/report-web/a/report/postsizefreq/orglist?[^>]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher urlMatcher = pattern.matcher(content);
        while (urlMatcher.find()) {
            String group = urlMatcher.group(0);
            urls = Arrays.copyOf(urls, urls.length + 1);
            urls[urls.length - 1] = group;
        }
        return urls;
    }

    public static void main(String[] args) {
        //getData();
        getHuawei();
    }

}
