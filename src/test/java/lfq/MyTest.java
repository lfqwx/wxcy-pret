package lfq;

import com.post.ibaties.primary.xxx.crawl.entity.BkData;
import com.post.ibaties.secondary.xxx.send.entity.RSendData;
import com.post.ibaties.primary.xxx.crawl.mapper.CrawlMapper;
import com.post.ibaties.secondary.xxx.send.mapper.SendMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: 𝓛.𝓕.𝓠
 */
public class MyTest extends ApplicationTests {
    @Autowired
    private CrawlMapper mapper;
    @Autowired
    private SendMapper sendMapper;
    @Test
    public void insert() {
        /*BkData bkData = new BkData("韶关邮局", 122,2000,22.0,220.0);
        int insert = mapper.insert(bkData);
        System.out.println(insert);*/
    }
    @Test
    public void find(){
        BkData bkData = mapper.find("长岭邮政支局");
        System.out.println(bkData);
    }
    @Test
    public void update(){
       /* BkData data = new BkData("韶关邮局", 122,2000,22.0,220.0);
        int update = mapper.update(data);
        System.out.println(update);*/
    }
    @Test
    public void findAll(){
        List<RSendData> list = mapper.findAll();
        list.forEach(value->{
            System.out.println(value);
        });
    }
}
