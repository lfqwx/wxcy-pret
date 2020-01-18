package com.post.ibaties.common.core;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/5/14.
 */
public class QueryParam implements Cloneable {
    protected List<Term> terms = new LinkedList();
    protected Set<String> includes = new LinkedHashSet();
    protected Set<String> excludes = new LinkedHashSet();

    private Map<String,Object> termMap=new HashMap<String,Object>();

    private boolean paging = true;
    private Integer posStart = 0;
    private Integer count = 20;
    private List<Sort> sorts = new LinkedList();

    public boolean isPaging() {
        return paging;
    }

    public void setPaging(boolean paging) {
        this.paging = paging;
    }

    public Integer getPosStart() {
        return posStart;
    }

    public void setPosStart(Integer posStart) {
        this.posStart = posStart;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }

    public QueryParam() {
    }

    public QueryParam  or(String column, Object value) {
        return this.or(column, "eq", value);
    }

    public QueryParam and(String column, Object value) {
        return this.and(column, "eq", value);
    }

    public QueryParam or(String column, String termType, Object value) {
        Term term = new Term();
        term.setTermType(termType);
        term.setColumn(column);
        term.setValue(value);
        term.setType(Term.Type.or);
        this.terms.add(term);
        return this;
    }

    public QueryParam and(String column, String termType, Object value) {
        Term term = new Term();
        term.setTermType(termType);
        term.setColumn(column);
        term.setValue(value);
        term.setType(Term.Type.and);
        this.terms.add(term);
        return this;
    }

    public Term nest() {
        return this.nest((String)null, (Object)null);
    }

    public Term orNest() {
        return this.orNest((String)null, (Object)null);
    }

    public Term nest(String termString, Object value) {
        Term term = new Term();
        term.setColumn(termString);
        term.setValue(value);
        term.setType(Term.Type.and);
        this.terms.add(term);
        return term;
    }

    public Term orNest(String termString, Object value) {
        Term term = new Term();
        term.setColumn(termString);
        term.setValue(value);
        term.setType(Term.Type.or);
        this.terms.add(term);
        return term;
    }

    public QueryParam includes(String... fields) {
        this.includes.addAll(Arrays.asList(fields));
        return this;
    }

    public QueryParam excludes(String... fields) {
        this.excludes.addAll(Arrays.asList(fields));
        this.includes.removeAll(Arrays.asList(fields));
        return this;
    }

    public QueryParam where(String key, Object value) {
        this.and(key, value);
        return this;
    }

    public QueryParam where(String key, String termType, Object value) {
        this.and(key, termType, value);
        return this;
    }

    public Set<String> getIncludes() {
        if(this.includes == null) {
            this.includes = new LinkedHashSet();
        }

        return this.includes;
    }

    public Set<String> getExcludes() {
        if(this.excludes == null) {
            this.excludes = new LinkedHashSet();
        }

        return this.excludes;
    }

    public void setIncludes(Set<String> includes) {
        this.includes = includes;
    }

    public void setExcludes(Set<String> excludes) {
        this.excludes = excludes;
    }

    public List<Term> getTerms() {
        return this.terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public QueryParam addTerm(Term term) {
        this.terms.add(term);
        return this;
    }

    public boolean containsTerm(String key){
        boolean flag=false;
        if(terms.size()==0)return false;
        if(terms.size()>0 && termMap.size()>0){
            return termMap.containsKey(key);
        }
        for(Term term:terms){
            termMap.put(term.getColumn(),term.getValue());
        }
        return termMap.containsKey(key);
    }

    public Object getTermValue(String key){
        if(terms.size()>0 && termMap.size()==0){
            for(Term term:terms){
                termMap.put(term.getColumn(),term.getValue());
            }
        }
        return termMap.get(key);
    }

    public QueryParam clone() {
        QueryParam param = new QueryParam();
        param.setExcludes(new LinkedHashSet(this.excludes));
        param.setIncludes(new LinkedHashSet(this.includes));
        List<Term> terms = (List)this.terms.stream().map((term) -> {
            return term.clone();
        }).collect(Collectors.toList());
        param.setTerms(terms);
        return param;
    }
}
