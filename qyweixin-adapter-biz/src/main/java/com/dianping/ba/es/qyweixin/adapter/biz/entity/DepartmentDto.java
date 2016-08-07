package com.dianping.ba.es.qyweixin.adapter.biz.entity;

import java.io.Serializable;

/**
 * Created by yangyuchen on 7/22/14.
 */
public class DepartmentDto implements Serializable {
    //部门id,在微信创建后由微信给出
    private Integer id;
    //部门名称
    private String name;
    //parentid,父部门id,微信提供,根部门id为1
    private Integer parentId;

    // initial
    public DepartmentDto(){
        id = -1;
        name = "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "{id="+id+", name="+name+", parentId="+parentId+"}";
    }
}
