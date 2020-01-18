package com.post.ibaties.secondary.xxx.send.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.post.ibaties.primary.xxx.crawl.entity.BkData;
import com.post.ibaties.secondary.xxx.send.entity.SendData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author: 𝓛.𝓕.𝓠
 */
public interface SendMapper extends BaseMapper<BkData> {

    @Insert({"insert into lfq_send_data(id,jg_name,ryjl,rsr,yyjl,ysr,rq) values(SEQ_LFQ_SEND.nextval,#{jgName},#{ryjl},#{rsr},#{yyjl},#{ysr},sysdate)"})
    int insert(SendData data);

    @Select({"select * from lfq_send_data where jg_name=#{jgName}"})
    SendData find(String jgName);

    @Update({"update lfq_send_data set ryjl=#{ryjl},rsr=#{rsr},yyjl=#{yyjl},ysr=#{ysr},rq=sysdate where jg_name=#{jgName}"})
    int update(SendData data);
}
