package com.post.ibaties.primary.xxx.crawl.controller;

import com.post.ibaties.primary.xxx.crawl.entity.BkData;
import com.post.ibaties.secondary.xxx.send.entity.RSendData;
import com.post.ibaties.secondary.xxx.send.entity.SendData;
import com.post.ibaties.primary.xxx.crawl.mapper.CrawlMapper;
import com.post.ibaties.secondary.xxx.send.mapper.SendMapper;
import com.post.ibaties.primary.xxx.crawl.service.Crawl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author: 𝓛.𝓕.𝓠
 */
@Controller
public class CrawlController {
    @Autowired
    private Crawl crawl;
    @Autowired
    private CrawlMapper mapper;
    @Autowired
    private SendMapper sendMapper;

    @ResponseBody
    @GetMapping("/crawl")
    @Scheduled(cron = "0 0 * * * ?")
    public List list() {
        Map map = crawl.getData();//除华为
        BkData huawei = crawl.getHuawei();//华为标快数据
        if (null == map || map.size() == 0) {
            System.out.println("未获取到数据");
            return null;
        }
        //写lfq_crawl_data
        BkData huawei_pre = mapper.find(huawei.getJgName());
        if (huawei_pre != null) {
            mapper.update(huawei);
        }else {
            mapper.insert(huawei);
        }
        map.forEach((key, value) -> {
            String jgName = key.toString();
            BkData cur = (BkData) value;
            //判重
            BkData pre = mapper.find(jgName);
            if (pre != null) {
                mapper.update(cur);
            } else {
                mapper.insert(cur);
            }
        });
        //写lfq_send_data
        List<SendData> list = mapper.findAll();
        System.out.println("list:"+list.size());
        list.forEach(obj -> {
            SendData pre = sendMapper.find(obj.getJgName());

            if (null != pre) {
                sendMapper.update(obj);
            } else {
                sendMapper.insert(obj);
            }
        });
        return list;
    }

    //报表页面
    @Scheduled(cron = "0 2 * * * ?")
    @GetMapping("/data")
    public String data() {
        return "bk_daily";
    }

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "hello world";
    }
}
