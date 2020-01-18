package com.post.ibaties.common.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class SQLUtil {

	public SQLUtil(){
		
	}

	private String tname;
	private StringBuffer insertField;
	private StringBuffer insertValue;
	private StringBuffer updateField;
	private Map<String,String> columns;
	public SQLUtil(String tname){
		this.tname=tname;
		columns=new HashMap<String,String>();
	}
	public static SQLUtil tname(String tname){
		return new SQLUtil(tname);
	}
	public SQLUtil field(String cname){
		this.field(cname.toUpperCase(), ":"+cname.toUpperCase());
		return this;
	}
	public SQLUtil field(String cname, String value){
		this.columns.put(cname.toUpperCase(), value);
		return this;
	}
	public String insertSQL(){
		insertField=new StringBuffer();
		insertValue=new StringBuffer();
		for(String key:columns.keySet()){
			insertField.append(key).append(",");
			insertValue.append(columns.get(key)).append(",");
		}
		if(columns.size()>0){
			insertField.deleteCharAt(insertField.length()-1);
			insertValue.deleteCharAt(insertValue.length()-1);
		}
		return "insert into "+tname+" ("+insertField.toString()+") values ("+insertValue.toString()+")";
	}
	
	public String updateSQL(String whereClause){
		updateField=new StringBuffer();
		for(String key:columns.keySet()){
			updateField.append(key).append("=").append(columns.get(key)).append(",");
		}
		if(columns.size()>0){
			updateField.deleteCharAt(updateField.length()-1);
		}
		return "update "+tname+" set "+updateField.toString()+" where "+whereClause;
	}
	
	
	protected String getString(Map data, String key){
		String tmp="";
		if(data==null)return "";
		Object d=data.get(key.toUpperCase());
		if(d==null) return "";
		if(d instanceof String){
			tmp=(String)d;
		}else if(d instanceof BigDecimal){
			tmp=((BigDecimal)d).toString();
		}
		return tmp;
	}
	
	public static void main(String[] args) {
		String sql=SQLUtil.tname("t_hyxx").field("name").field("cssj","to_date(:CSSJ,'yyyy-MM-dd')").updateSQL("CODE=:CODE");
		System.out.println(sql);
	}
}
