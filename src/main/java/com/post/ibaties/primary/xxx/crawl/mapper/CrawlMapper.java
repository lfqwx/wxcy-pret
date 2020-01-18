package com.post.ibaties.primary.xxx.crawl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.post.ibaties.primary.xxx.crawl.entity.BkData;
import com.post.ibaties.secondary.xxx.send.entity.RSendData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author: 𝓛.𝓕.𝓠
 */
public interface CrawlMapper extends BaseMapper<BkData> {
    @Insert({"insert into lfq_crawl_data(id,jg_name,ryjl,rsr,yyjl,ysr,rq) values(SEQ_LFQ_CRAWL.nextval,#{jgName},#{ryjl},#{rsr},#{yyjl},#{ysr},sysdate)"})
    int insert(BkData data);

    @Select({"select * from lfq_crawl_data where jg_name=#{jgName}"})
    BkData find(String jgName);

    @Select({"select t.qj jg_name,jh.jhrywl,jh.jhrsr,t.ryjl,t.rsr from(select qj,sum(ryjl)ryjl,sum(rsr)rsr from(select ryjl,rsr,qj from lfq_crawl_data ,lfq_jg  where jg_name=tdz)group by qj)t,lfq_rjh jh where jh.qj=t.qj"})
    List<RSendData> findAll();

    @Update({"update lfq_crawl_data set ryjl=#{ryjl},rsr=#{rsr},yyjl=#{yyjl},ysr=#{ysr},rq=sysdate where jg_name=#{jgName}"})
    int update(BkData data);
}
