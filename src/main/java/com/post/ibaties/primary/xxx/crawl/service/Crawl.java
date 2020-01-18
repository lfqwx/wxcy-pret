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
 */
@Service
public class Crawl {
    public final static String PAGE = "https://10.4.188.1/report-web/a/report/ltgl/orgCountReceiveProduct/result";


    public static Map getData() {
        ChinaPostWeb web = new ChinaPostWeb("43001800a", "xyd123456");
        HttpUtil MHttpUtil = web.MHttpUtil;
        Map map = null;
        boolean login = web.Login("43001800");
        if (login) {
            System.out.println("登陆成功");
            try {
                //爬虫代码
                MHttpUtil.Get("https://10.4.188.1/report-web/a/report/ltgl/orgCountReceiveProduct/list",null);
                map = crawl(MHttpUtil);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("登录失败！");
        }
        return map;
    }

    public static Map crawl(HttpUtil MHttpUtil) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Date end = c.getTime();
        String dqrq= format.format(end);//当前日期
        c.add(Calendar.DATE, -1);
        Date start = c.getTime();
        String time = format.format(start);
        HashMap<String, String> map = new HashMap<String, String>();
        Map<String, BkData> map2 = new HashMap<String, BkData>();
        map.put("orgRemark", "B");
        map.put("provinceOrgCode", "43000600");
        map.put("cityOrgCode", "43001800");
        map.put("tjrqStart",time );
        map.put("tjrqEnd",time );
        map.put("baseProductId", "180");
        map.put("jccp", "11210");
        map.put("jccpName", "180*11210");
        map.put("tjzl", "1");
        map.put("isjccp", "2");
        map.put("senderType", "2");
        map.put("mailType", "5");
        map.put("bblx", "1");
        map.put("ywldw", "1");
        map.put("statisticsDateStart", time.replace("-",""));
        map.put("statisticsDateEnd", time.replace("-",""));
        String res = MHttpUtil.Post(PAGE, 1, ToolUtil.HashToStr(map));
        //System.out.println(res);
        //第一个页面数据
        JXDocument jx = new JXDocument(res);
        String tbody = jx.selOne("//tbody").toString();
        //System.out.println(tbody);
        List<JXNode> xh = jx.selN("//tbody/tr/td[2]");
        List<JXNode> jg = jx.selN("//tbody/tr/td[7]");
        List<JXNode> ryjl = jx.selN("//tbody/tr/td[8]");
        List<JXNode> rsr = jx.selN("//tbody/tr/td[9]");
        List<JXNode> yyjl = jx.selN("//tbody/tr/td[11]");
        List<JXNode> ysr = jx.selN("//tbody/tr/td[12]");
        for (int i = 0; i < jg.size(); i++) {
            String s0 = xh.get(i).toString().replace("<td>", "").replace("</td>", "").trim();
            String s1 = jg.get(i).toString().replace("<td>", "").replace("</td>", "")
                    .replace("\"","").trim();
            String s2 = ryjl.get(i).toString().replace("<td class=\"day\">", "").replace("</td>", "").replace(",","").trim();
            String s3 = rsr.get(i).toString().replace("<td class=\"day\">", "").replace("</td>", "").trim();
            String s4 = yyjl.get(i).toString().replace("<td class=\"month\">", "").replace("</td>", "").replace(",","").trim();
            String s5 = ysr.get(i).toString().replace("<td class=\"month\">", "").replace("</td>", "").trim();

            System.out.println(s0+","+s1 + "," + s2+","+s3+","+s4 + "," + s5);
            BkData bkData = new BkData(s1, Integer.valueOf(s2), Integer.valueOf(s4), Double.valueOf(s3), Double.valueOf(s5));
            map2.put(s1,bkData);
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
        getData();
    }

}
