package com.post.ibaties.common.util;



import com.post.ibaties.common.core.TreeSuportEntity;

import java.util.*;

/**
 * 解析树形数据工具类 
 *  
 * @author jianda 
 * @date 2017年5月29日 
 */  
public class TreeParser{

    public static List<TreeSuportEntity> list2Tree(List<TreeSuportEntity> list){
        List<TreeSuportEntity> trees = new ArrayList<TreeSuportEntity>();
        for(TreeSuportEntity treeSuportEntity:list){
            if (treeSuportEntity.getParentId() == null || "0".equals(treeSuportEntity.getParentId().toString())) {
                trees.add(findChildren(treeSuportEntity,list));
            }
        }
        return trees;
    }

    public static TreeSuportEntity findChildren(TreeSuportEntity treeNode,List<TreeSuportEntity> treeNodes) {
        for (TreeSuportEntity it : treeNodes) {
            if(treeNode.getId().equals(it.getParentId())) {
                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }


    /** 
     * 解析树形数据 
     * @param topId 
     * @param entityList 
     * @return 
     * @author jianda 
     * @date 2017年5月29日 
     */  
    public static List<Map<String,Object>> getTreeList(String topId, List<Map<String,Object>> entityList,String idField,String pidField) {
        List<Map<String,Object>> resultList=new ArrayList<>();
        Set<Object> keySets=new HashSet<Object>();
        //获取顶层元素集合  
        Object parentId;
        for (Map entity : entityList) {
            parentId=entity.get(pidField);
            entity.put("pid",parentId);
            entity.put("id",entity.get(idField));
            if(parentId==null||topId.equals(parentId)){
                if(!keySets.contains(entity.get("id"))){
                    resultList.add(entity);
                    keySets.add(entity.get("id"));
                }
            }
        }
        if(resultList.size()==0 && entityList.size()>0){
            resultList.add(entityList.get(0));
        }
        //获取每个顶层元素的子数据集合  
        for (Map entity : resultList) {
            entity.put("rows",getSubList(entity.get(idField),entityList,idField,pidField));
        }  
          
        return resultList;  
    }  
      
    /** 
     * 获取子数据集合 
     * @param id 
     * @param entityList 
     * @return 
     * @author jianda 
     * @date 2017年5月29日 
     */  
    private  static  List<Map> getSubList(Object id, List<Map<String,Object>> entityList,String idField,String pidField) {
        List<Map> childList=new ArrayList<Map>();
        Object parentId;
        Set<Object> keySets=new HashSet<Object>();
        //子集的直接子对象  
        for (Map entity : entityList) {
            parentId=entity.get(pidField);
            entity.put("pid",parentId);
            entity.put("id",entity.get(idField));
            if(id.equals(parentId)){  
                if(!keySets.contains(entity.get("id"))){
                    childList.add(entity);
                    keySets.add(entity.get("id"));
                }
            }  
        }  
          
        //子集的间接子对象  
        for (Map entity : childList) {
            entity.put("rows",getSubList(entity.get(idField),entityList,idField,pidField));
        }  
          
        //递归退出条件  
        if(childList.size()==0){  
            return Collections.EMPTY_LIST;
        }  
          
        return childList;  
    }

    /**
     * 解析树形数据
     * @param topId
     * @param entityList
     * @return
     * @author jianda
     * @date 2017年5月29日
     */
    public static List<Map<String,Object>> getTreeList(String topId, List<Map<String,Object>> entityList,String idField,String pidField,List<Map<String, Object>> checkedItems,String itemField) {
        List<Map<String,Object>> resultList=new ArrayList<>();
        Set<Object> keySets=new HashSet<Object>();
        //获取顶层元素集合
        Object parentId;
        for (Map entity : entityList) {
            parentId=entity.get(pidField);
            entity.put("pid",parentId);
            entity.put("id",entity.get(idField));
            if(checkedItems!=null){
                for(Map checkedItem:checkedItems){
                    if(entity.get(idField).toString().equals(checkedItem.get(itemField).toString())){
                        entity.put("checked", true);
                        break;
                    }
                }
            }
            if(parentId==null||entity.get(idField).toString().equals(topId)){
                entity.put("open", 1);
                if(!keySets.contains(entity.get("id"))){
                    resultList.add(entity);
                    keySets.add(entity.get("id"));
                }
            }
        }

        //获取每个顶层元素的子数据集合
        for (Map entity : resultList) {
            entity.put("rows",getSubList(entity.get(idField),entityList,idField,pidField,checkedItems,itemField));
        }

        return resultList;
    }

    /**
     * 获取子数据集合
     * @param id
     * @param entityList
     * @return
     * @author jianda
     * @date 2017年5月29日
     */
    private  static  List<Map> getSubList(Object id, List<Map<String,Object>> entityList,String idField,String pidField,List<Map<String, Object>> checkedItems,String itemField) {
        List<Map> childList=new ArrayList<Map>();
        Object parentId;
        Set<Object> keySets=new HashSet<Object>();
        //子集的直接子对象
        for (Map entity : entityList) {
            parentId=entity.get(pidField);
            entity.put("pid",parentId);
            entity.put("id",entity.get(idField));
            if(checkedItems!=null){
                for(Map checkedItem:checkedItems){
                    if(entity.get(idField).toString().equals(checkedItem.get(itemField).toString())){
                        entity.put("checked", true);
                        break;
                    }
                }
            }
            if(id.equals(parentId)){
                if(!keySets.contains(entity.get("id"))){
                    childList.add(entity);
                    keySets.add(entity.get("id"));
                }
            }
        }

        //子集的间接子对象
        for (Map entity : childList) {
            entity.put("rows",getSubList(entity.get(idField),entityList,idField,pidField));
        }

        //递归退出条件
        if(childList.size()==0){
            return null;
        }

        return childList;
    }
}  