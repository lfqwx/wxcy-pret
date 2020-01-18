package com.post.ibaties.common.core;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.post.ibaties.common.util.StringUtil;
import com.post.ibaties.common.util.TreeParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M,T>{


    public String generatorCode(String seqName, int length){
        SqlRunner runner=SqlRunner.db();
        String _tmp=(String)runner.selectObj("select lpad("+seqName+".nextval,"+length+",'0') from dual");
        return _tmp;
    }

    public PagerResult selectPagerTree(QueryWrapper entityWrapper,QueryParam queryParam){
        PagerResult pagerResult=new PagerResult();
        List<TreeSuportEntity> treeSuportEntities=list(entityWrapper);
        pagerResult.setTotal(treeSuportEntities.size());
        treeSuportEntities= TreeParser.list2Tree(treeSuportEntities);
        pagerResult.setData(treeSuportEntities);
        return pagerResult;
    }

    public PagerResult selectPagerTree(QueryParam queryParam){
        return selectPagerTree(new QueryWrapper(),queryParam);
    }

    public List<T> selectTreeList(QueryWrapper entityWrapper){
        return TreeParser.list2Tree(list(entityWrapper));
    }

    public PagerResult selectPager(QueryWrapper entityWrapper,QueryParam queryParam){
        PagerResult pagerResult=new PagerResult();
        if(queryParam.getTerms().size()>0){
            for(Term term:queryParam.getTerms()){
                String column=term.getColumn();
                if(column.indexOf("$")>0){
                    String[] colParams=column.split("[$]");
                    if(colParams.length==2) {
                        if ("eq".equals(colParams[1])) {
                            entityWrapper.eq(colParams[0],term.getValue());
                        } else if ("like".equals(colParams[1])) {
                            entityWrapper.like(colParams[0],term.getValue().toString());
                        } else if ("start".equals(colParams[1])) {
                            entityWrapper.likeRight(colParams[0],term.getValue().toString());
                        }
                    }else if(colParams.length==3){
                        String fmt="yyyy-MM-dd";
                        SimpleDateFormat sdf=new SimpleDateFormat(fmt);
                        if ("eq".equals(colParams[1])) {
                            try {
                                entityWrapper.eq("trunc("+colParams[0] + ")",sdf.parse(term.getValue().toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else if("gte".equals(colParams[1])){
                            try {
                                entityWrapper.ge("trunc("+colParams[0] + ")",sdf.parse(term.getValue().toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else if("gt".equals(colParams[1])){
                            try {
                                entityWrapper.gt("trunc("+colParams[0] + ")",sdf.parse(term.getValue().toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else if("lte".equals(colParams[1])){
                            try {
                                entityWrapper.le("trunc("+colParams[0] + ")",sdf.parse(term.getValue().toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else if("lte".equals(colParams[1])){
                            try {
                                entityWrapper.lt("trunc("+colParams[0] + ")",sdf.parse(term.getValue().toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        if(queryParam.getSorts().size()>0){
            for(Sort sort:queryParam.getSorts()){
                entityWrapper.orderBy(true,sort.getOrder().equalsIgnoreCase("asc")?true:false, StringUtil.underscoreName(sort.getName()));
            }
        }
        if(queryParam.getIncludes().size()>0){
            String[] columns=new String[queryParam.getIncludes().size()];
            queryParam.getIncludes().toArray(columns);
            entityWrapper.select(columns);
        }
        if(queryParam.isPaging()) {
            Page<T> page=new Page<T>(queryParam.getPosStart(),queryParam.getCount());
            IPage<T> datas=this.baseMapper.selectPage(page,entityWrapper);
            pagerResult.setTotal(count(entityWrapper));
            pagerResult.setData(datas.getRecords());
            pagerResult.setPos(queryParam.getPosStart());
        }else{
            pagerResult.setData(list(entityWrapper));
            pagerResult.setTotal(pagerResult.getData().size());
        }
        return pagerResult;
    }
    public PagerResult selectPager(QueryParam queryParam){
        return selectPager(new QueryWrapper<T>(),queryParam);
    }
}
